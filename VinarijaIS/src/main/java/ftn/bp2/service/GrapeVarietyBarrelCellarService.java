package ftn.bp2.service;

import ftn.bp2.dao.GrapeVarietyBarrelCellarDAO;
import ftn.bp2.dto.GrapeVarietyBarrelCellarDTO;

import java.sql.SQLException;
import java.util.List;

public class GrapeVarietyBarrelCellarService {
    private final GrapeVarietyBarrelCellarDAO dao = new GrapeVarietyBarrelCellarDAO();

    public List<GrapeVarietyBarrelCellarDTO> getGrapeVarietyBarrelCellarStats() throws SQLException {
        return dao.getGrapeVarietyBarrelCellarStats();
    }
} 