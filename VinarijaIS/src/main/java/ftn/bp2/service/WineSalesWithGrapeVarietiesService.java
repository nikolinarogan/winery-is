package ftn.bp2.service;

import ftn.bp2.dao.WineSalesWithGrapeVarietiesDAO;
import ftn.bp2.dto.WineSalesWithGrapeVarietiesDTO;

import java.sql.SQLException;
import java.util.List;

public class WineSalesWithGrapeVarietiesService {
    private final WineSalesWithGrapeVarietiesDAO wineSalesWithGrapeVarietiesDAO;

    public WineSalesWithGrapeVarietiesService() {
        this.wineSalesWithGrapeVarietiesDAO = new WineSalesWithGrapeVarietiesDAO();
    }

    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesWithGrapeVarieties() throws SQLException {
        return wineSalesWithGrapeVarietiesDAO.getWineSalesWithGrapeVarieties();
    }

    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesByGrapeVariety(String grapeVariety) throws SQLException {
        if (grapeVariety == null || grapeVariety.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv sorte grožđa ne može biti prazan");
        }
        return wineSalesWithGrapeVarietiesDAO.getWineSalesByGrapeVariety(grapeVariety);
    }

    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesByWineName(String wineName) throws SQLException {
        if (wineName == null || wineName.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv vina ne može biti prazan");
        }
        return wineSalesWithGrapeVarietiesDAO.getWineSalesByWineName(wineName);
    }

    public List<WineSalesWithGrapeVarietiesDTO> getTopSellingWines(int limit) throws SQLException {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit mora biti veći od 0");
        }
        if (limit > 100) {
            throw new IllegalArgumentException("Limit ne može biti veći od 100");
        }
        return wineSalesWithGrapeVarietiesDAO.getTopSellingWines(limit);
    }

    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesByRevenueRange(Double minRevenue, Double maxRevenue) throws SQLException {
        if (minRevenue == null || maxRevenue == null) {
            throw new IllegalArgumentException("Minimalni i maksimalni prihod ne mogu biti null");
        }
        if (minRevenue < 0 || maxRevenue < 0) {
            throw new IllegalArgumentException("Prihod ne može biti negativan");
        }
        if (minRevenue > maxRevenue) {
            throw new IllegalArgumentException("Minimalni prihod ne može biti veći od maksimalnog");
        }
        return wineSalesWithGrapeVarietiesDAO.getWineSalesByRevenueRange(minRevenue, maxRevenue);
    }

    public List<WineSalesWithGrapeVarietiesDTO> getTop5SellingWines() throws SQLException {
        return wineSalesWithGrapeVarietiesDAO.getTopSellingWines(5);
    }

    public List<WineSalesWithGrapeVarietiesDTO> getTop10SellingWines() throws SQLException {
        return wineSalesWithGrapeVarietiesDAO.getTopSellingWines(10);
    }

    public List<WineSalesWithGrapeVarietiesDTO> getHighRevenueWines() throws SQLException {
        return wineSalesWithGrapeVarietiesDAO.getWineSalesByRevenueRange(1000.0, Double.MAX_VALUE);
    }

    public List<WineSalesWithGrapeVarietiesDTO> getMediumRevenueWines() throws SQLException {
        return wineSalesWithGrapeVarietiesDAO.getWineSalesByRevenueRange(500.0, 999.99);
    }

    public List<WineSalesWithGrapeVarietiesDTO> getLowRevenueWines() throws SQLException {
        return wineSalesWithGrapeVarietiesDAO.getWineSalesByRevenueRange(0.0, 499.99);
    }

    public WineSalesWithGrapeVarietiesDTO getBestSellingWine() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> topWines = wineSalesWithGrapeVarietiesDAO.getTopSellingWines(1);
        if (topWines.isEmpty()) {
            return null;
        }
        return topWines.get(0);
    }

    public Double getTotalRevenue() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> allSales = wineSalesWithGrapeVarietiesDAO.getWineSalesWithGrapeVarieties();
        return allSales.stream()
                .mapToDouble(wine -> wine.getUkupanPrihod() != null ? wine.getUkupanPrihod() : 0.0)
                .sum();
    }

    public Integer getTotalBottlesSold() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> allSales = wineSalesWithGrapeVarietiesDAO.getWineSalesWithGrapeVarieties();
        return allSales.stream()
                .mapToInt(wine -> wine.getUkupnoBoca() != null ? wine.getUkupnoBoca() : 0)
                .sum();
    }

    public Double getAverageRevenuePerWine() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> allSales = wineSalesWithGrapeVarietiesDAO.getWineSalesWithGrapeVarieties();
        if (allSales.isEmpty()) {
            return 0.0;
        }
        
        double totalRevenue = allSales.stream()
                .mapToDouble(wine -> wine.getUkupanPrihod() != null ? wine.getUkupanPrihod() : 0.0)
                .sum();
        
        return totalRevenue / allSales.size();
    }

    public Double getAverageBottlesPerWine() throws SQLException {
        List<WineSalesWithGrapeVarietiesDTO> allSales = wineSalesWithGrapeVarietiesDAO.getWineSalesWithGrapeVarieties();
        if (allSales.isEmpty()) {
            return 0.0;
        }
        
        double totalBottles = allSales.stream()
                .mapToDouble(wine -> wine.getUkupnoBoca() != null ? wine.getUkupnoBoca() : 0)
                .sum();
        
        return totalBottles / allSales.size();
    }

    public WineSalesWithGrapeVarietiesDAO getWineSalesWithGrapeVarietiesDAO() {
        return wineSalesWithGrapeVarietiesDAO;
    }
} 