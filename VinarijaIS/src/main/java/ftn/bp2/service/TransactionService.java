package ftn.bp2.service;

import ftn.bp2.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TransactionService {

    // Transaction: Complete customer order workflow
    // Purpose: Create new customer, order, and add wine bottles in a single transaction
    // Tables: Kupac (INSERT) + Narudzba (INSERT) + Boca (INSERT) - 3 tables minimum
    public Map<String, Object> executeCustomerOrderTransaction(String email, String phoneNumber,
                                                               String paymentMethod, Integer wineId,
                                                               Float bottleCapacity) throws SQLException {
        Connection conn = null;
        Map<String, Object> result = new HashMap<>();

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Step 1: Insert a new customer
            String insertCustomerSql = """
                INSERT INTO Kupac (Email, BrTel) 
                VALUES (?, ?) 
                RETURNING IdKup
                """;

            Integer customerId = null;
            try (PreparedStatement stmt = conn.prepareStatement(insertCustomerSql)) {
                stmt.setString(1, email);
                stmt.setString(2, phoneNumber);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        customerId = rs.getInt("IdKup");
                    }
                }
            }

            if (customerId == null) {
                conn.rollback();
                result.put("success", false);
                result.put("error", "Failed to create customer");
                return result;
            }

            // Step 2: Insert a new order for that customer
            String insertOrderSql = """
                INSERT INTO Narudzba (DatNar, PltMtd, Kupac_IdKup) 
                VALUES (CURRENT_DATE, ?, ?) 
                RETURNING IdNar
                """;

            Integer orderId = null;
            try (PreparedStatement stmt = conn.prepareStatement(insertOrderSql)) {
                stmt.setString(1, paymentMethod);
                stmt.setInt(2, customerId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        orderId = rs.getInt("IdNar");
                    }
                }
            }

            if (orderId == null) {
                conn.rollback();
                result.put("success", false);
                result.put("error", "Failed to create order");
                return result;
            }

            // Step 3: Insert a new bottle of wine into the order
            String insertBottleSql = """
                INSERT INTO Boca (KapBoc, Vino_IdVina, Narudzba_IdNar) 
                VALUES (?, ?, ?)
                """;

            try (PreparedStatement stmt = conn.prepareStatement(insertBottleSql)) {
                stmt.setFloat(1, bottleCapacity);
                stmt.setInt(2, wineId);
                stmt.setInt(3, orderId);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    conn.rollback();
                    result.put("success", false);
                    result.put("error", "Failed to add bottle to order");
                    return result;
                }
            }

            conn.commit();

            result.put("success", true);
            result.put("customerId", customerId);
            result.put("orderId", orderId);
            result.put("message", "Customer order transaction completed successfully");

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            result.put("success", false);
            result.put("error", "Transaction failed: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                DatabaseConnection.closeConnection(conn);
            }
        }

        return result;
    }
}
