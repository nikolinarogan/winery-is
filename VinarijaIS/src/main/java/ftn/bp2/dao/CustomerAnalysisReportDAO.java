package ftn.bp2.dao;

import ftn.bp2.dto.CustomerAnalysisReportDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerAnalysisReportDAO {

    // Complex query:  Analiza kupaca sa preferencijama vina i brojem narudzbi na osnovu kojih se radi analiza
    public List<CustomerAnalysisReportDTO> getCustomerAnalysisReport() throws SQLException {
        List<CustomerAnalysisReportDTO> results = new ArrayList<>();

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
                results.add(mapResultSetToDTO(rs));
            }
        }

        return results;
    }


    private CustomerAnalysisReportDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        return new CustomerAnalysisReportDTO(
                rs.getString("Email"),
                rs.getInt("RazlicitaVina"),
                rs.getString("OmiljeneSorte"),
                rs.getInt("BrojNarduzbi")
        );
    }
} 