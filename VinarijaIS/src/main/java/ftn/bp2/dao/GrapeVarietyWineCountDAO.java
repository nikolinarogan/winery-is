package ftn.bp2.dao;

import ftn.bp2.dto.GrapeVarietyWineCountDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrapeVarietyWineCountDAO {


    //Za svaku sortu ispisujem broj vina u kojima se ona nalazi kao i najstarije proizvedeno vino u kome je sorta ucestvovala, kako bi se stekao uvid u tradiciju najpopularnijih sorti
    public List<GrapeVarietyWineCountDTO> getGrapeVarietyWineCount() throws SQLException {
        List<GrapeVarietyWineCountDTO> results = new ArrayList<>();

        String sql = """
            SELECT sg.NazSrt AS Grape_Variety, 
                   COUNT(u.Vino_IdVina) AS Number_of_Wines,
                   MIN(EXTRACT(YEAR FROM v.God)) as oldest_wine_year
            FROM ucestvuje u
            JOIN Sorta_Grozdja sg ON u.Sorta_Grozdja_IdSrt = sg.IdSrt
            JOIN Vino v ON u.Vino_IdVina = v.IdVina
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


    private GrapeVarietyWineCountDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new GrapeVarietyWineCountDTO(
                rs.getString("Grape_Variety"),
                rs.getInt("Number_of_Wines"),
                rs.getInt("oldest_wine_year")
        );
    }
} 