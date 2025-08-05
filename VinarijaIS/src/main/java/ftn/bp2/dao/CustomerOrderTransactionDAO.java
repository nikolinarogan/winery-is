package ftn.bp2.dao;

import ftn.bp2.dto.CustomerOrderTransactionDTO;
import ftn.bp2.dto.TransactionResultDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerOrderTransactionDAO {

    // Tables: Kupac (INSERT) + Narudzba (INSERT) + Boca (UPDATE), skroz novi kupac!!!
    public TransactionResultDTO executeCustomerOrderTransaction(CustomerOrderTransactionDTO transaction) throws SQLException {
        Connection conn = null;
        TransactionResultDTO result = new TransactionResultDTO();

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            Integer customerId = getOrCreateCustomer(conn, transaction);//create
            if (customerId == null) {
                conn.rollback();
                return new TransactionResultDTO(false, "Failed to create or find customer", "Failed to create or find customer");
            }

            Integer orderId = insertOrder(conn, transaction, customerId);//create
            if (orderId == null) {
                conn.rollback();
                return new TransactionResultDTO(false, "Failed to create order", "Failed to create order");
            }

            boolean bottlesAssigned = assignBottlesToOrder(conn, transaction, orderId);//update
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

    private Integer getCustomerIdByEmail(Connection conn, String email) throws SQLException {
        String sql = "SELECT IdKup FROM Kupac WHERE Email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim().toLowerCase());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdKup");
                }
            }
        }
        return null;
    }

    private Integer getOrCreateCustomer(Connection conn, CustomerOrderTransactionDTO transaction) throws SQLException {
        String normalizedEmail = transaction.getEmail().trim().toLowerCase();
        Integer customerId = getCustomerIdByEmail(conn, normalizedEmail);
        if (customerId != null) {
            return customerId; 
        }
        return insertCustomer(conn, transaction);
    }

    private Integer insertCustomer(Connection conn, CustomerOrderTransactionDTO transaction) throws SQLException {
        Integer nextId = getNextCustomerId(conn);
        String insertCustomerSql = "INSERT INTO Kupac (IdKup, Email, BrTel) VALUES (?, ?, ?) RETURNING IdKup";
        try (PreparedStatement stmt = conn.prepareStatement(insertCustomerSql)) {
            stmt.setInt(1, nextId);
            stmt.setString(2, transaction.getEmail().trim().toLowerCase());
            stmt.setString(3, transaction.getPhoneNumber());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdKup");
                }
            }
        }
        return null;
    }

    private Integer getNextCustomerId(Connection conn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(IdKup), 0) + 1 FROM Kupac";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 1;
    }

    private Integer insertOrder(Connection conn, CustomerOrderTransactionDTO transaction, Integer customerId) throws SQLException {
        Integer nextOrderId = getNextOrderId(conn);
        
        String normalizedPaymentMethod = normalizePaymentMethod(transaction.getPaymentMethod());
        
        String insertOrderSql = "INSERT INTO Narudzba (IdNar, DatNar, PltMtd, Kupac_IdKup) VALUES (?, ?, ?, ?) RETURNING IdNar";
        
        try (PreparedStatement stmt = conn.prepareStatement(insertOrderSql)) {
            stmt.setInt(1, nextOrderId);
            stmt.setObject(2, transaction.getOrderDate());
            stmt.setString(3, normalizedPaymentMethod);
            stmt.setInt(4, customerId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdNar");
                }
            }
        }
        return null;
    }

    private String normalizePaymentMethod(String paymentMethod) {
        if (paymentMethod == null) {
            return "Gotovinsko placanje"; // Default
        }
        
        String normalized = paymentMethod.toLowerCase().trim();
        
        if (normalized.equals("karticno placanje") || normalized.equals("kartično plaćanje")) {
            return "Karticno placanje";
        } else if (normalized.equals("gotovinsko placanje") || normalized.equals("gotovinsko plaćanje")) {
            return "Gotovinsko placanje";
        } else {
            return "Gotovinsko placanje"; // Default
        }
    }

    private Integer getNextOrderId(Connection conn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(IdNar), 0) + 1 FROM Narudzba";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 1;
    }

    //UPDATE BOCE
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

    public List<Map<String, Object>> getAvailableBottlesData() throws SQLException {
        String sql = """
            SELECT b.SerBr, b.KapBoc, b.Vino_IdVina, v.NazVina, b.Narudzba_IdNar
            FROM Boca b
            LEFT JOIN Vino v ON b.Vino_IdVina = v.IdVina
            WHERE b.Narudzba_IdNar IS NULL
            ORDER BY b.SerBr
            """;

        List<Map<String, Object>> bottles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("SerBr", rs.getInt("SerBr"));
                row.put("KapBoc", rs.getFloat("KapBoc"));
                row.put("Vino_IdVina", rs.getInt("Vino_IdVina"));
                row.put("NazVina", rs.getString("NazVina"));
                row.put("Narudzba_IdNar", rs.getObject("Narudzba_IdNar", Integer.class));
                bottles.add(row);
            }
        }
        return bottles;
    }

    public List<Map<String, Object>> getBottlesByWineIdData(Integer wineId) throws SQLException {
        String sql = """
            SELECT b.SerBr, b.KapBoc, b.Vino_IdVina, v.NazVina, b.Narudzba_IdNar
            FROM Boca b
            LEFT JOIN Vino v ON b.Vino_IdVina = v.IdVina
            WHERE b.Vino_IdVina = ? AND b.Narudzba_IdNar IS NULL
            ORDER BY b.SerBr
            """;

        List<Map<String, Object>> bottles = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wineId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("SerBr", rs.getInt("SerBr"));
                    row.put("KapBoc", rs.getFloat("KapBoc"));
                    row.put("Vino_IdVina", rs.getInt("Vino_IdVina"));
                    row.put("NazVina", rs.getString("NazVina"));
                    row.put("Narudzba_IdNar", rs.getObject("Narudzba_IdNar", Integer.class));
                    bottles.add(row);
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

            stmt.setString(1, email.trim().toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdKup");
                }
            }
        }
        return null;
    }
} 