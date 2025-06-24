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