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

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisByEmail(String email) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email ne može biti prazan");
        }
        return customerAnalysisReportDAO.getCustomerAnalysisByEmail(email);
    }

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisByGrapeVariety(String grapeVariety) throws SQLException {
        if (grapeVariety == null || grapeVariety.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv sorte grožđa ne može biti prazan");
        }
        return customerAnalysisReportDAO.getCustomerAnalysisByGrapeVariety(grapeVariety);
    }

    public List<CustomerAnalysisReportDTO> getTopCustomers(int limit) throws SQLException {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit mora biti veći od 0");
        }
        if (limit > 100) {
            throw new IllegalArgumentException("Limit ne može biti veći od 100");
        }
        return customerAnalysisReportDAO.getTopCustomers(limit);
    }

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisByOrderCount(int minOrders) throws SQLException {
        if (minOrders < 0) {
            throw new IllegalArgumentException("Minimalni broj narudžbi ne može biti negativan");
        }
        return customerAnalysisReportDAO.getCustomerAnalysisByOrderCount(minOrders);
    }

    public List<CustomerAnalysisReportDTO> getCustomerAnalysisByWineCount(int minWines) throws SQLException {
        if (minWines < 0) {
            throw new IllegalArgumentException("Minimalni broj vina ne može biti negativan");
        }
        return customerAnalysisReportDAO.getCustomerAnalysisByWineCount(minWines);
    }

    public List<CustomerAnalysisReportDTO> getTop5Customers() throws SQLException {
        return customerAnalysisReportDAO.getTopCustomers(5);
    }

    public List<CustomerAnalysisReportDTO> getTop10Customers() throws SQLException {
        return customerAnalysisReportDAO.getTopCustomers(10);
    }

    public List<CustomerAnalysisReportDTO> getHighValueCustomers() throws SQLException {
        return customerAnalysisReportDAO.getCustomerAnalysisByOrderCount(5);
    }

    public List<CustomerAnalysisReportDTO> getMediumValueCustomers() throws SQLException {
        List<CustomerAnalysisReportDTO> allCustomers = customerAnalysisReportDAO.getCustomerAnalysisReport();
        return allCustomers.stream()
                .filter(customer -> customer.getBrojNarduzbi() >= 2 && customer.getBrojNarduzbi() <= 4)
                .toList();
    }

    public List<CustomerAnalysisReportDTO> getLowValueCustomers() throws SQLException {
        List<CustomerAnalysisReportDTO> allCustomers = customerAnalysisReportDAO.getCustomerAnalysisReport();
        return allCustomers.stream()
                .filter(customer -> customer.getBrojNarduzbi() == 1)
                .toList();
    }

    public List<CustomerAnalysisReportDTO> getWineEnthusiasts() throws SQLException {
        return customerAnalysisReportDAO.getCustomerAnalysisByWineCount(3);
    }

    public CustomerAnalysisReportDTO getBestCustomer() throws SQLException {
        List<CustomerAnalysisReportDTO> topCustomers = customerAnalysisReportDAO.getTopCustomers(1);
        if (topCustomers.isEmpty()) {
            return null;
        }
        return topCustomers.get(0);
    }

    public Integer getTotalCustomers() throws SQLException {
        List<CustomerAnalysisReportDTO> allCustomers = customerAnalysisReportDAO.getCustomerAnalysisReport();
        return allCustomers.size();
    }

    public Integer getTotalOrders() throws SQLException {
        List<CustomerAnalysisReportDTO> allCustomers = customerAnalysisReportDAO.getCustomerAnalysisReport();
        return allCustomers.stream()
                .mapToInt(CustomerAnalysisReportDTO::getBrojNarduzbi)
                .sum();
    }

    public Integer getTotalUniqueWines() throws SQLException {
        List<CustomerAnalysisReportDTO> allCustomers = customerAnalysisReportDAO.getCustomerAnalysisReport();
        return allCustomers.stream()
                .mapToInt(CustomerAnalysisReportDTO::getRazlicitaVina)
                .sum();
    }

    public Double getAverageOrdersPerCustomer() throws SQLException {
        List<CustomerAnalysisReportDTO> allCustomers = customerAnalysisReportDAO.getCustomerAnalysisReport();
        if (allCustomers.isEmpty()) {
            return 0.0;
        }
        
        double totalOrders = allCustomers.stream()
                .mapToDouble(customer -> customer.getBrojNarduzbi())
                .sum();
        
        return totalOrders / allCustomers.size();
    }

    public Double getAverageWinesPerCustomer() throws SQLException {
        List<CustomerAnalysisReportDTO> allCustomers = customerAnalysisReportDAO.getCustomerAnalysisReport();
        if (allCustomers.isEmpty()) {
            return 0.0;
        }
        
        double totalWines = allCustomers.stream()
                .mapToDouble(customer -> customer.getRazlicitaVina())
                .sum();
        
        return totalWines / allCustomers.size();
    }

    public CustomerAnalysisReportDAO getCustomerAnalysisReportDAO() {
        return customerAnalysisReportDAO;
    }
} 