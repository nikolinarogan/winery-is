package ftn.bp2.dao;

import ftn.bp2.dto.GrapeVarietyWineCountDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrapeVarietyWineCountDAO {

    public List<GrapeVarietyWineCountDTO> getGrapeVarietyWineCount() throws SQLException {
        List<GrapeVarietyWineCountDTO> results = new ArrayList<>();

        String sql = """
            SELECT sg.NazSrt AS Grape_Variety, 
                   COUNT(u.Vino_IdVina) AS Number_of_Wines
            FROM ucestvuje u
            JOIN Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            GROUP BY sg.NazSrt
            ORDER BY Number_of_Wines DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                results.add(mapResultSetToDTO(rs));
            }
        }

        return results;
    }

    public List<GrapeVarietyWineCountDTO> getGrapeVarietyWineCountByColor(String color) throws SQLException {
        List<GrapeVarietyWineCountDTO> results = new ArrayList<>();

        String sql = """
            SELECT sg.NazSrt AS Grape_Variety, 
                   COUNT(u.Vino_IdVina) AS Number_of_Wines
            FROM ucestvuje u
            JOIN Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            WHERE sg.Boja ILIKE ?
            GROUP BY sg.NazSrt
            ORDER BY Number_of_Wines DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + color + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
            }
        }

        return results;
    }

    public List<GrapeVarietyWineCountDTO> getGrapeVarietyWineCountByName(String name) throws SQLException {
        List<GrapeVarietyWineCountDTO> results = new ArrayList<>();

        String sql = """
            SELECT sg.NazSrt AS Grape_Variety, 
                   COUNT(u.Vino_IdVina) AS Number_of_Wines
            FROM ucestvuje u
            JOIN Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            WHERE sg.NazSrt ILIKE ?
            GROUP BY sg.NazSrt
            ORDER BY Number_of_Wines DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
            }
        }

        return results;
    }

    private GrapeVarietyWineCountDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new GrapeVarietyWineCountDTO(
                rs.getString("Grape_Variety"),
                rs.getInt("Number_of_Wines")
        );
    }
} 