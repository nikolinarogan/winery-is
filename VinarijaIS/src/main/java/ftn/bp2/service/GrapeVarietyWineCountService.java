package ftn.bp2.service;

import ftn.bp2.dao.GrapeVarietyWineCountDAO;
import ftn.bp2.dto.GrapeVarietyWineCountDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GrapeVarietyWineCountService {
    private final GrapeVarietyWineCountDAO grapeVarietyWineCountDAO;

    public GrapeVarietyWineCountService() {
        this.grapeVarietyWineCountDAO = new GrapeVarietyWineCountDAO();
    }

    public List<GrapeVarietyWineCountDTO> getGrapeVarietyWineCount() throws SQLException {
        List<Map<String, Object>> rawData = grapeVarietyWineCountDAO.getGrapeVarietyWineCountData();
        return rawData.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private GrapeVarietyWineCountDTO mapToDTO(Map<String, Object> row) {
        return new GrapeVarietyWineCountDTO(
                (String) row.get("Grape_Variety"),
                (Integer) row.get("Number_of_Wines"),
                (Integer) row.get("oldest_wine_year")
        );
    }

    public GrapeVarietyWineCountDAO getGrapeVarietyWineCountDAO() {
        return grapeVarietyWineCountDAO;
    }
} 