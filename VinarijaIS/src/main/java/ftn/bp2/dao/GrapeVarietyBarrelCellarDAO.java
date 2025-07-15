package ftn.bp2.dao;

import ftn.bp2.dto.GrapeVarietyBarrelCellarDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrapeVarietyBarrelCellarDAO {
    public List<GrapeVarietyBarrelCellarDTO> getGrapeVarietyBarrelCellarStats() throws SQLException {
        List<GrapeVarietyBarrelCellarDTO> results = new ArrayList<>();
        String sql = """
            SELECT 
                sg.NazSrt AS GrapeVariety,
                COUNT(DISTINCT v.IdVina) AS UniqueWines,
                COALESCE(SUM(b.KapBur), 0) AS TotalBarrelCapacity,
                COALESCE(COUNT(DISTINCT p.IdPod), 0) AS UniqueCellars,
                COALESCE(COUNT(b.IdBur), 0) AS NumberOfBarrels
            FROM Sorta_Grozdja sg
            LEFT JOIN ucestvuje u ON sg.IdSrt = u.Sorta_Grozdja_IdSrt
            LEFT JOIN Vino v ON u.Vino_IdVina = v.IdVina
            LEFT JOIN Bure b ON v.IdVina = b.Vino_IdVina
            LEFT JOIN Podrum p ON b.Podrum_IdPod = p.IdPod
            GROUP BY sg.NazSrt
            ORDER BY TotalBarrelCapacity DESC;
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                GrapeVarietyBarrelCellarDTO dto = new GrapeVarietyBarrelCellarDTO(
                        rs.getString("GrapeVariety"),
                        rs.getInt("UniqueWines"),
                        rs.getDouble("TotalBarrelCapacity"),
                        rs.getInt("UniqueCellars"),
                        rs.getInt("NumberOfBarrels")
                );
                results.add(dto);
            }
        }
        return results;
    }
} 