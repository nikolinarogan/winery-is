package ftn.bp2.dao;

import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAnalysisReportDAO {

    // Complex query:  Analiza kupaca sa preferencijama vina i brojem narudzbi na osnovu kojih se radi analiza
    public List<Map<String, Object>> getCustomerAnalysisReportData() throws SQLException {
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
                row.put("Email", rs.getString("Email"));
                row.put("RazlicitaVina", rs.getInt("RazlicitaVina"));
                row.put("OmiljeneSorte", rs.getString("OmiljeneSorte"));
                row.put("BrojNarduzbi", rs.getInt("BrojNarduzbi"));
                results.add(row);
            }
        }

        return results;
    }
} 