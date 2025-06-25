package ftn.bp2.dao;

import ftn.bp2.dto.BottleInfoDTO;
import ftn.bp2.dto.CustomerOrderTransactionDTO;
import ftn.bp2.dto.TransactionResultDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderTransactionDAO {

    // Tables: Kupac (INSERT) + Narudzba (INSERT) + Boca (UPDATE)
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

            boolean bottlesAssigned = assignBottlesToOrder(conn, transaction, orderId);
            if (!bottlesAssigned) {
                conn.rollback();
                return new TransactionResultDTO(false, "Failed to assign bottles to order", "Failed to assign bottles to order");
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
            if (e.getMessage().toLowerCase().contains("unique") || 
                e.getMessage().toLowerCase().contains("duplicate") ||
                e.getSQLState().equals("23505")) {
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

    private boolean assignBottlesToOrder(Connection conn, CustomerOrderTransactionDTO transaction, Integer orderId) throws SQLException {
        if (transaction.getBottleSerialNumbers() == null || transaction.getBottleSerialNumbers().isEmpty()) {
            throw new SQLException("No bottles selected for purchase");
        }

        String updateBottleSql = """
            UPDATE Boca 
            SET Narudzba_IdNar = ? 
            WHERE SerBr = ? AND Narudzba_IdNar IS NULL
            """;

        try (PreparedStatement stmt = conn.prepareStatement(updateBottleSql)) {
            for (Integer serialNumber : transaction.getBottleSerialNumbers()) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, serialNumber);
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Bottle with serial number " + serialNumber + " is not available (already sold or doesn't exist)");
                }
            }
        }
        return true;
    }

    public List<BottleInfoDTO> getAvailableBottles() throws SQLException {
        String sql = """
            SELECT b.SerBr, b.KapBoc, b.Vino_IdVina, v.NazVina, b.Narudzba_IdNar
            FROM Boca b
            LEFT JOIN Vino v ON b.Vino_IdVina = v.IdVina
            WHERE b.Narudzba_IdNar IS NULL
            ORDER BY b.SerBr
            """;

        List<BottleInfoDTO> bottles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BottleInfoDTO bottle = new BottleInfoDTO(
                    rs.getInt("SerBr"),
                    rs.getFloat("KapBoc"),
                    rs.getInt("Vino_IdVina"),
                    rs.getString("NazVina"),
                    rs.getObject("Narudzba_IdNar", Integer.class)
                );
                bottles.add(bottle);
            }
        }
        return bottles;
    }

    public List<BottleInfoDTO> getBottlesByWineId(Integer wineId) throws SQLException {
        String sql = """
            SELECT b.SerBr, b.KapBoc, b.Vino_IdVina, v.NazVina, b.Narudzba_IdNar
            FROM Boca b
            LEFT JOIN Vino v ON b.Vino_IdVina = v.IdVina
            WHERE b.Vino_IdVina = ? AND b.Narudzba_IdNar IS NULL
            ORDER BY b.SerBr
            """;

        List<BottleInfoDTO> bottles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wineId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    BottleInfoDTO bottle = new BottleInfoDTO(
                        rs.getInt("SerBr"),
                        rs.getFloat("KapBoc"),
                        rs.getInt("Vino_IdVina"),
                        rs.getString("NazVina"),
                        rs.getObject("Narudzba_IdNar", Integer.class)
                    );
                    bottles.add(bottle);
                }
            }
        }
        return bottles;
    }

    public boolean validateBottleExists(Integer serialNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Boca WHERE SerBr = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, serialNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean validateBottleAvailable(Integer serialNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Boca WHERE SerBr = ? AND Narudzba_IdNar IS NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, serialNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
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