package ftn.bp2.service;

import ftn.bp2.dao.CustomerAnalysisReportDAO;
import ftn.bp2.dto.CustomerAnalysisReportDTO;

import java.sql.SQLException;
import java.util.List;

public class CustomerAnalysisReportService {
    private final CustomerAnalysisReportDAO customerAnalysisReportDAO;

    public CustomerAnalysisReportService() {
        this.customerAnalysisReportDAO = new CustomerAnalysisReportDAO();
    }

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisReport() throws SQLException {
        return customerAnalysisReportDAO.getCustomerAnalysisReport();
    }

    public CustomerAnalysisReportDAO getCustomerAnalysisReportDAO() {
        return customerAnalysisReportDAO;
    }
} 