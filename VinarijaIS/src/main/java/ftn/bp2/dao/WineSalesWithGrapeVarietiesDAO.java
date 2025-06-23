package ftn.bp2.dao;

import ftn.bp2.dto.WineSalesWithGrapeVarietiesDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WineSalesWithGrapeVarietiesDAO {

    // Complex query: Wine sales analysis with grape varieties and revenue
    // Report: Wine sales performance with grape variety details
    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesWithGrapeVarieties() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = new ArrayList<>();

        String sql = """
            SELECT 
                v.NazVina AS NazivVina,
                STRING_AGG(DISTINCT sg.NazSrt, ', ') AS SorteGrozdja,
                COUNT(b.SerBr) AS UkupnoBoca,
                ROUND(CAST(SUM(b.KapBoc * 2) AS NUMERIC), 2) AS UkupanPrihod
            FROM 
                Vino v
            JOIN 
                ucestvuje u ON v.IdVina = u.Vino_IdVina
            JOIN 
                Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            LEFT JOIN 
                Boca b ON v.IdVina = b.Vino_IdVina
            LEFT JOIN 
                Narudzba n ON b.Narudzba_IdNar = n.IdNar
            LEFT JOIN 
                Kupac k ON n.Kupac_IdKup = k.IdKup
            GROUP BY 
                v.IdVina, v.NazVina
            HAVING 
                COUNT(b.SerBr) > 0
            ORDER BY 
                UkupanPrihod DESC
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

    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesByGrapeVariety(String grapeVariety) throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = new ArrayList<>();

        String sql = """
            SELECT 
                v.NazVina AS NazivVina,
                STRING_AGG(DISTINCT sg.NazSrt, ', ') AS SorteGrozdja,
                COUNT(b.SerBr) AS UkupnoBoca,
                ROUND(CAST(SUM(b.KapBoc * 2) AS NUMERIC), 2) AS UkupanPrihod
            FROM 
                Vino v
            JOIN 
                ucestvuje u ON v.IdVina = u.Vino_IdVina
            JOIN 
                Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            LEFT JOIN 
                Boca b ON v.IdVina = b.Vino_IdVina
            LEFT JOIN 
                Narudzba n ON b.Narudzba_IdNar = n.IdNar
            LEFT JOIN 
                Kupac k ON n.Kupac_IdKup = k.IdKup
            WHERE 
                sg.NazSrt ILIKE ?
            GROUP BY 
                v.IdVina, v.NazVina
            HAVING 
                COUNT(b.SerBr) > 0
            ORDER BY 
                UkupanPrihod DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + grapeVariety + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
            }
        }

        return results;
    }

    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesByWineName(String wineName) throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = new ArrayList<>();

        String sql = """
            SELECT 
                v.NazVina AS NazivVina,
                STRING_AGG(DISTINCT sg.NazSrt, ', ') AS SorteGrozdja,
                COUNT(b.SerBr) AS UkupnoBoca,
                ROUND(CAST(SUM(b.KapBoc * 2) AS NUMERIC), 2) AS UkupanPrihod
            FROM 
                Vino v
            JOIN 
                ucestvuje u ON v.IdVina = u.Vino_IdVina
            JOIN 
                Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            LEFT JOIN 
                Boca b ON v.IdVina = b.Vino_IdVina
            LEFT JOIN 
                Narudzba n ON b.Narudzba_IdNar = n.IdNar
            LEFT JOIN 
                Kupac k ON n.Kupac_IdKup = k.IdKup
            WHERE 
                v.NazVina ILIKE ?
            GROUP BY 
                v.IdVina, v.NazVina
            HAVING 
                COUNT(b.SerBr) > 0
            ORDER BY 
                UkupanPrihod DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + wineName + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
            }
        }

        return results;
    }

    public List<WineSalesWithGrapeVarietiesDTO> getTopSellingWines(int limit) throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = new ArrayList<>();

        String sql = """
            SELECT 
                v.NazVina AS NazivVina,
                STRING_AGG(DISTINCT sg.NazSrt, ', ') AS SorteGrozdja,
                COUNT(b.SerBr) AS UkupnoBoca,
                ROUND(CAST(SUM(b.KapBoc * 2) AS NUMERIC), 2) AS UkupanPrihod
            FROM 
                Vino v
            JOIN 
                ucestvuje u ON v.IdVina = u.Vino_IdVina
            JOIN 
                Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            LEFT JOIN 
                Boca b ON v.IdVina = b.Vino_IdVina
            LEFT JOIN 
                Narudzba n ON b.Narudzba_IdNar = n.IdNar
            LEFT JOIN 
                Kupac k ON n.Kupac_IdKup = k.IdKup
            GROUP BY 
                v.IdVina, v.NazVina
            HAVING 
                COUNT(b.SerBr) > 0
            ORDER BY 
                UkupanPrihod DESC
            LIMIT ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
            }
        }

        return results;
    }

    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesByRevenueRange(Double minRevenue, Double maxRevenue) throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> results = new ArrayList<>();

        String sql = """
            SELECT 
                v.NazVina AS NazivVina,
                STRING_AGG(DISTINCT sg.NazSrt, ', ') AS SorteGrozdja,
                COUNT(b.SerBr) AS UkupnoBoca,
                ROUND(CAST(SUM(b.KapBoc * 2) AS NUMERIC), 2) AS UkupanPrihod
            FROM 
                Vino v
            JOIN 
                ucestvuje u ON v.IdVina = u.Vino_IdVina
            JOIN 
                Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            LEFT JOIN 
                Boca b ON v.IdVina = b.Vino_IdVina
            LEFT JOIN 
                Narudzba n ON b.Narudzba_IdNar = n.IdNar
            LEFT JOIN 
                Kupac k ON n.Kupac_IdKup = k.IdKup
            GROUP BY 
                v.IdVina, v.NazVina
            HAVING 
                COUNT(b.SerBr) > 0
                AND ROUND(CAST(SUM(b.KapBoc * 2) AS NUMERIC), 2) BETWEEN ? AND ?
            ORDER BY 
                UkupanPrihod DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, minRevenue);
            stmt.setDouble(2, maxRevenue);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
            }
        }

        return results;
    }

    private WineSalesWithGrapeVarietiesDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        Object ukupanPrihodObj = rs.getObject("UkupanPrihod");
        Double ukupanPrihod = ukupanPrihodObj != null ? ((Number) ukupanPrihodObj).doubleValue() : null;

        return new WineSalesWithGrapeVarietiesDTO(
                rs.getString("NazivVina"),
                rs.getString("SorteGrozdja"),
                rs.getInt("UkupnoBoca"),
                ukupanPrihod
        );
    }
} 