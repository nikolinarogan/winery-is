package ftn.bp2.dao;

import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WineSalesWithGrapeVarietiesDAO {

    // Complex query: Analiza prodaje vina za svako vino ispise koje sorte ucestvuju u njemu, koliko boca tog vina postoji i koliko je kupaca u sklopu svoje narudzbe kupilo to vino
    public List<Map<String, Object>> getWineSalesWithGrapeVarietiesData() throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

        String sql = """
              
              WITH bottle_counts AS (
                SELECT
                  Vino_IdVina,
                  COUNT(*) AS broj_boca
                FROM Boca
                GROUP BY Vino_IdVina
              )
              
              SELECT
                  v.NazVina AS NazivVina,
                  STRING_AGG(DISTINCT sg.NazSrt, ', ') AS SorteGrozdja,
                  COALESCE(bc.broj_boca, 0) AS UkupnoBoca,
                  COUNT(DISTINCT k.IdKup) AS UniqueCustomerCount
              FROM
                  Vino v
              JOIN
                  ucestvuje u ON v.IdVina = u.Vino_IdVina
              JOIN
                  Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
              LEFT JOIN
                  bottle_counts bc ON v.IdVina = bc.Vino_IdVina
              LEFT JOIN
                  Boca b ON v.IdVina = b.Vino_IdVina
              LEFT JOIN
                  Narudzba n ON b.Narudzba_IdNar = n.IdNar
              LEFT JOIN
                  Kupac k ON n.Kupac_IdKup = k.IdKup
              GROUP BY
                  v.IdVina, v.NazVina, bc.broj_boca
              HAVING
                  COALESCE(bc.broj_boca, 0) > 0
              ORDER BY
                  UniqueCustomerCount DESC;
              
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("NazivVina", rs.getString("NazivVina"));
                row.put("SorteGrozdja", rs.getString("SorteGrozdja"));
                row.put("UkupnoBoca", rs.getInt("UkupnoBoca"));
                row.put("UniqueCustomerCount", rs.getInt("UniqueCustomerCount"));
                results.add(row);
            }
        }

        return results;
    }
} 