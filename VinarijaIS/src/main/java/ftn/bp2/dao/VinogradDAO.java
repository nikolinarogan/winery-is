package ftn.bp2.dao;

import ftn.bp2.dto.VinogradDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VinogradDAO {

    public List<VinogradDTO> findAll() throws SQLException {
        List<VinogradDTO> vinogradi = new ArrayList<>();
        String sql = "SELECT * FROM Vinograd ORDER BY ImeV";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                vinogradi.add(mapResultSetToDTO(rs));
            }
        }

        return vinogradi;
    }

    public VinogradDTO findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM Vinograd WHERE IdV = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDTO(rs);
                }
            }
        }

        return null;
    }

    public List<VinogradDTO> findByParentId(Integer parentId) throws SQLException {
        List<VinogradDTO> vinogradi = new ArrayList<>();
        String sql = "SELECT * FROM Vinograd WHERE Vinograd_IdV = ? ORDER BY ImeV";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, parentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    vinogradi.add(mapResultSetToDTO(rs));
                }
            }
        }

        return vinogradi;
    }

    public Integer insert(VinogradDTO vinograd) throws SQLException {
        String sql = "INSERT INTO Vinograd (ImeV, PoV, DatOsn, VKap, Vinograd_IdV) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING IdV";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vinograd.getImeV());
            stmt.setObject(2, vinograd.getPoV());
            stmt.setObject(3, vinograd.getDatOsn());
            stmt.setObject(4, vinograd.getVKap());

            if (vinograd.getVinogradIdV() != null) {
                stmt.setInt(5, vinograd.getVinogradIdV());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IdV");
                }
            }
        }

        return null;
    }

    public boolean update(VinogradDTO vinograd) throws SQLException {
        String sql = "UPDATE Vinograd SET ImeV = ?, PoV = ?, DatOsn = ?, " +
                "VKap = ?, Vinograd_IdV = ? WHERE IdV = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vinograd.getImeV());
            stmt.setObject(2, vinograd.getPoV());
            stmt.setObject(3, vinograd.getDatOsn());
            stmt.setObject(4, vinograd.getVKap());

            if (vinograd.getVinogradIdV() != null) {
                stmt.setInt(5, vinograd.getVinogradIdV());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, vinograd.getIdV());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(Integer id) throws SQLException {
        // First check if there are any related records that would prevent deletion
        String checkBerbaSql = "SELECT COUNT(*) FROM Berba WHERE Vinograd_IdV = ?";
        String checkSeUzgajaSql = "SELECT COUNT(*) FROM se_uzgaja WHERE Vinograd_IdV = ?";
        String checkRadiSql = "SELECT COUNT(*) FROM Radi WHERE Vinograd_IdV = ?";
        String checkSeAngazujeSql = "SELECT COUNT(*) FROM se_angazuje WHERE Berba_Vinograd_IdV = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check Berba table
            try (PreparedStatement stmt = conn.prepareStatement(checkBerbaSql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("Ne možete obrisati vinograd koji ima berbe");
                    }
                }
            }

            // Check se_uzgaja table
            try (PreparedStatement stmt = conn.prepareStatement(checkSeUzgajaSql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("Ne možete obrisati vinograd koji ima sorti grožđa");
                    }
                }
            }

            // Check Radi table
            try (PreparedStatement stmt = conn.prepareStatement(checkRadiSql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("Ne možete obrisati vinograd koji ima zaposlene");
                    }
                }
            }

            // Check se_angazuje table
            try (PreparedStatement stmt = conn.prepareStatement(checkSeAngazujeSql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("Ne možete obrisati vinograd koji ima angazovane zaposlene");
                    }
                }
            }

            // If no related records, proceed with deletion
            String sql = "DELETE FROM Vinograd WHERE IdV = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        }
    }

    private VinogradDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        Float poV = null;
        Float vKap = null;

        Object poVObj = rs.getObject("PoV");
        if (poVObj != null) {
            poV = ((Number) poVObj).floatValue();
        }

        Object vKapObj = rs.getObject("VKap");
        if (vKapObj != null) {
            vKap = ((Number) vKapObj).floatValue();
        }

        return new VinogradDTO(
                rs.getInt("IdV"),
                rs.getString("ImeV"),
                poV,
                rs.getObject("DatOsn", java.sql.Date.class) != null ?
                        rs.getObject("DatOsn", java.sql.Date.class).toLocalDate() : null,
                vKap,
                rs.getObject("Vinograd_IdV", Integer.class)
        );
    }

}