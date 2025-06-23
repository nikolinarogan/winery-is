package ftn.bp2.dao;

import ftn.bp2.dto.CustomerAnalysisReportDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerAnalysisReportDAO {

    // Complex query: Customer analysis with wine preferences and order count
    // Report: Customer purchasing behavior and grape variety preferences
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

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisByEmail(String email) throws SQLException {
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
            WHERE 
                k.Email ILIKE ?
            GROUP BY 
                k.IdKup, k.Email
            HAVING 
                COUNT(DISTINCT n.IdNar) > 0
            ORDER BY 
                BrojNarduzbi DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + email + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
            }
        }

        return results;
    }

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisByGrapeVariety(String grapeVariety) throws SQLException {
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
            WHERE 
                sg.NazSrt ILIKE ?
            GROUP BY 
                k.IdKup, k.Email
            HAVING 
                COUNT(DISTINCT n.IdNar) > 0
            ORDER BY 
                BrojNarduzbi DESC
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

    public List<CustomerAnalysisReportDTO> getTopCustomers(int limit) throws SQLException {
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

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisByOrderCount(int minOrders) throws SQLException {
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
                COUNT(DISTINCT n.IdNar) >= ?
            ORDER BY 
                BrojNarduzbi DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, minOrders);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
            }
        }

        return results;
    }

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisByWineCount(int minWines) throws SQLException {
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
                AND COUNT(DISTINCT v.IdVina) >= ?
            ORDER BY 
                BrojNarduzbi DESC
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, minWines);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToDTO(rs));
                }
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