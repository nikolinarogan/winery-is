package ftn.bp2.dao;

import ftn.bp2.dto.CustomerOrderTransactionDTO;
import ftn.bp2.dto.TransactionResultDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class CustomerOrderTransactionDAO {


    // Tables: Kupac (INSERT) + Narudzba (INSERT) + Boca (INSERT)
    public TransactionResultDTO executeCustomerOrderTransaction(CustomerOrderTransactionDTO transaction) throws SQLException {
        Connection conn = null;
        TransactionResultDTO result = new TransactionResultDTO();

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            Integer customerId = insertCustomer(conn, transaction);
            if (customerId == null) {
                conn.rollback();
                return new TransactionResultDTO(false, "Failed to create customer", "Failed to create customer");
            }

            Integer orderId = insertOrder(conn, transaction, customerId);
            if (orderId == null) {
                conn.rollback();
                return new TransactionResultDTO(false, "Failed to create order", "Failed to create order");
            }

            Integer bottleId = insertBottle(conn, transaction, orderId);
            if (bottleId == null) {
                conn.rollback();
                return new TransactionResultDTO(false, "Failed to add bottle to order", "Failed to add bottle to order");
            }

            conn.commit();

            return new TransactionResultDTO(true, "Customer order transaction completed successfully", 
                                          customerId, orderId);

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            return new TransactionResultDTO(false, "Transaction failed", e.getMessage());
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                DatabaseConnection.closeConnection(conn);
            }
        }
    }

    private Integer insertCustomer(Connection conn, CustomerOrderTransactionDTO transaction) throws SQLException {
        String insertCustomerSql = """
            INSERT INTO Kupac (Email, BrTel) 
            VALUES (?, ?) 
            RETURNING IdKup
            """;

        try (PreparedStatement stmt = conn.prepareStatement(insertCustomerSql)) {
            stmt.setString(1, transaction.getEmail());
            stmt.setString(2, transaction.getPhoneNumber());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdKup");
                }
            }
        } catch (SQLException e) {
            // Check if this is a unique constraint violation
            if (e.getMessage().toLowerCase().contains("unique") || 
                e.getMessage().toLowerCase().contains("duplicate") ||
                e.getSQLState().equals("23505")) { // PostgreSQL unique constraint violation
                throw new SQLException("Customer with email " + transaction.getEmail() + " already exists. Email must be unique.", e);
            }
            throw e;
        }
        return null;
    }

    private Integer insertOrder(Connection conn, CustomerOrderTransactionDTO transaction, Integer customerId) throws SQLException {
        String insertOrderSql = """
            INSERT INTO Narudzba (DatNar, PltMtd, Kupac_IdKup) 
            VALUES (?, ?, ?) 
            RETURNING IdNar
            """;

        try (PreparedStatement stmt = conn.prepareStatement(insertOrderSql)) {
            stmt.setObject(1, transaction.getOrderDate());
            stmt.setString(2, transaction.getPaymentMethod());
            stmt.setInt(3, customerId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdNar");
                }
            }
        }
        return null;
    }

    private Integer insertBottle(Connection conn, CustomerOrderTransactionDTO transaction, Integer orderId) throws SQLException {
        String insertBottleSql = """
            INSERT INTO Boca (KapBoc, Vino_IdVina, Narudzba_IdNar) 
            VALUES (?, ?, ?)
            RETURNING SerBr
            """;

        try (PreparedStatement stmt = conn.prepareStatement(insertBottleSql)) {
            stmt.setFloat(1, transaction.getBottleCapacity());
            stmt.setInt(2, transaction.getWineId());
            stmt.setInt(3, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("SerBr");
                }
            }
        }
        return null;
    }

    public boolean validateWineExists(Integer wineId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Vino WHERE IdVina = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wineId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean validateCustomerEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Kupac WHERE Email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public Integer getCustomerIdByEmail(String email) throws SQLException {
        String sql = "SELECT IdKup FROM Kupac WHERE Email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdKup");
                }
            }
        }
        return null;
    }
} 