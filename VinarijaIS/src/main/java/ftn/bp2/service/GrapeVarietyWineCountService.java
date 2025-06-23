package ftn.bp2.service;

import ftn.bp2.dao.GrapeVarietyWineCountDAO;
import ftn.bp2.dto.GrapeVarietyWineCountDTO;

import java.sql.SQLException;
import java.util.List;

public class GrapeVarietyWineCountService {
    private final GrapeVarietyWineCountDAO grapeVarietyWineCountDAO;

    public GrapeVarietyWineCountService() {
        this.grapeVarietyWineCountDAO = new GrapeVarietyWineCountDAO();
    }

    public List<GrapeVarietyWineCountDTO> getGrapeVarietyWineCount() throws SQLException {
        return grapeVarietyWineCountDAO.getGrapeVarietyWineCount();
    }

    public List<GrapeVarietyWineCountDTO> getGrapeVarietyWineCountByColor(String color) throws SQLException {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Boja ne može biti prazna");
        }
        return grapeVarietyWineCountDAO.getGrapeVarietyWineCountByColor(color);
    }

    public List<GrapeVarietyWineCountDTO> getGrapeVarietyWineCountByName(String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv sorte grožđa ne može biti prazan");
        }
        return grapeVarietyWineCountDAO.getGrapeVarietyWineCountByName(name);
    }

    public List<GrapeVarietyWineCountDTO> getRedGrapeVarietyWineCount() throws SQLException {
        return grapeVarietyWineCountDAO.getGrapeVarietyWineCountByColor("crvena");
    }

    public List<GrapeVarietyWineCountDTO> getWhiteGrapeVarietyWineCount() throws SQLException {
        return grapeVarietyWineCountDAO.getGrapeVarietyWineCountByColor("bela");
    }

    public List<GrapeVarietyWineCountDTO> getRoseGrapeVarietyWineCount() throws SQLException {
        return grapeVarietyWineCountDAO.getGrapeVarietyWineCountByColor("roze");
    }

    public GrapeVarietyWineCountDTO getMostUsedGrapeVariety() throws SQLException {
        List<GrapeVarietyWineCountDTO> allVarieties = grapeVarietyWineCountDAO.getGrapeVarietyWineCount();
        if (allVarieties.isEmpty()) {
            return null;
        }
        return allVarieties.get(0);
    }

    public GrapeVarietyWineCountDTO getLeastUsedGrapeVariety() throws SQLException {
        List<GrapeVarietyWineCountDTO> allVarieties = grapeVarietyWineCountDAO.getGrapeVarietyWineCount();
        if (allVarieties.isEmpty()) {
            return null;
        }
        return allVarieties.get(allVarieties.size() - 1); // Last element has least count
    }

    public Integer getTotalWineCount() throws SQLException {
        List<GrapeVarietyWineCountDTO> allVarieties = grapeVarietyWineCountDAO.getGrapeVarietyWineCount();
        return allVarieties.stream()
                .mapToInt(GrapeVarietyWineCountDTO::getNumberOfWines)
                .sum();
    }

    public Double getAverageWinesPerVariety() throws SQLException {
        List<GrapeVarietyWineCountDTO> allVarieties = grapeVarietyWineCountDAO.getGrapeVarietyWineCount();
        if (allVarieties.isEmpty()) {
            return 0.0;
        }
        
        double totalWines = allVarieties.stream()
                .mapToInt(GrapeVarietyWineCountDTO::getNumberOfWines)
                .sum();
        
        return totalWines / allVarieties.size();
    }

    public GrapeVarietyWineCountDAO getGrapeVarietyWineCountDAO() {
        return grapeVarietyWineCountDAO;
    }
} 