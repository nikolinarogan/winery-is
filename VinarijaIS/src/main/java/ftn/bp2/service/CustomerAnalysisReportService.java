package ftn.bp2.service;

import ftn.bp2.dao.CustomerAnalysisReportDAO;
import ftn.bp2.dto.CustomerAnalysisReportDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerAnalysisReportService {
    private final CustomerAnalysisReportDAO customerAnalysisReportDAO;

    public CustomerAnalysisReportService() {
        this.customerAnalysisReportDAO = new CustomerAnalysisReportDAO();
    }

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisReport() throws SQLException {
        List<Map<String, Object>> rawData = customerAnalysisReportDAO.getCustomerAnalysisReportData();
        return rawData.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CustomerAnalysisReportDTO mapToDTO(Map<String, Object> row) {
        return new CustomerAnalysisReportDTO(
                (String) row.get("Email"),
                (Integer) row.get("RazlicitaVina"),
                (String) row.get("OmiljeneSorte"),
                (Integer) row.get("BrojNarduzbi")
        );
    }

    public CustomerAnalysisReportDAO getCustomerAnalysisReportDAO() {
        return customerAnalysisReportDAO;
    }
} 