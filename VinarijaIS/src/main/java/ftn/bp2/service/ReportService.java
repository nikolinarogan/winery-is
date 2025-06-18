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
}
