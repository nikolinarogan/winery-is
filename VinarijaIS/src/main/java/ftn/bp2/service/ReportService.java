package ftn.bp2.service;

import ftn.bp2.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    // Jednostavan upit: 2 tabele + agregaciona funkcija
    // Za sortu grozdja ispisati u koliko vina ucestvuje
    public List<Map<String, Object>> getGrapeVarietyWineCount() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

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
                Map<String, Object> row = new HashMap<>();
                row.put("grapeVariety", rs.getString("Grape_Variety"));
                row.put("numberOfWines", rs.getInt("Number_of_Wines"));
                results.add(row);
            }
        }

        return results;
    }

    // Complex query: Wine sales analysis with grape varieties and revenue
    // Report: Wine sales performance with grape variety details
    public List<Map<String, Object>> getWineSalesWithGrapeVarieties() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

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
                Map<String, Object> row = new HashMap<>();
                row.put("nazivVina", rs.getString("NazivVina"));
                row.put("sorteGrozdja", rs.getString("SorteGrozdja"));
                row.put("ukupnoBoca", rs.getInt("UkupnoBoca"));

                Object ukupanPrihodObj = rs.getObject("UkupanPrihod");
                row.put("ukupanPrihod", ukupanPrihodObj != null ? ((Number) ukupanPrihodObj).doubleValue() : null);

                results.add(row);
            }
        }

        return results;
    }

    // Complex query: Customer analysis with wine preferences and order count
    // Report: Customer purchasing behavior and grape variety preferences
    public List<Map<String, Object>> getCustomerAnalysisReport() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

        String sql = """
            SELECT 
                k.Email,
                COUNT(DISTINCT v.IdVina) AS RazlicitaVina,
                STRING_AGG(DISTINCT sg.NazSrt, ', ') AS OmiljeneSorte,
                COUNT(DISTINCT n.IdNar) AS BrojNarduzbi
            FROM 
                Kupac k
            JOIN 
                Narudzba n ON k.IdKup = n.Kupac_IdKup
            JOIN 
                Boca b ON n.IdNar = b.Narudzba_IdNar
            JOIN 
                Vino v ON b.Vino_IdVina = v.IdVina
            JOIN 
                ucestvuje u ON v.IdVina = u.Vino_IdVina
            JOIN 
                Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            GROUP BY 
                k.IdKup, k.Email
            HAVING 
                COUNT(DISTINCT n.IdNar) > 0
            ORDER BY 
                BrojNarduzbi DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("email", rs.getString("Email"));
                row.put("razlicitaVina", rs.getInt("RazlicitaVina"));
                row.put("omiljeneSorte", rs.getString("OmiljeneSorte"));
                row.put("brojNarduzbi", rs.getInt("BrojNarduzbi"));

                results.add(row);
            }
        }

        return results;
    }


}
