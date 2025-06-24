package ftn.bp2.dao;

import ftn.bp2.dto.WineSalesWithGrapeVarietiesDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WineSalesWithGrapeVarietiesDAO {

    // Complex query: Analiza prodaje vina
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