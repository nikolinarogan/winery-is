package ftn.bp2.service;

import ftn.bp2.dao.WineSalesWithGrapeVarietiesDAO;
import ftn.bp2.dto.WineSalesWithGrapeVarietiesDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WineSalesWithGrapeVarietiesService {
    private final WineSalesWithGrapeVarietiesDAO wineSalesWithGrapeVarietiesDAO;

    public WineSalesWithGrapeVarietiesService() {
        this.wineSalesWithGrapeVarietiesDAO = new WineSalesWithGrapeVarietiesDAO();
    }

    public List<WineSalesWithGrapeVarietiesDTO> getWineSalesWithGrapeVarieties() throws SQLException {
        List<Map<String, Object>> rawData = wineSalesWithGrapeVarietiesDAO.getWineSalesWithGrapeVarietiesData();
        return rawData.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private WineSalesWithGrapeVarietiesDTO mapToDTO(Map<String, Object> row) {
        return new WineSalesWithGrapeVarietiesDTO(
                (String) row.get("NazivVina"),
                (String) row.get("SorteGrozdja"),
                (Integer) row.get("UkupnoBoca"),
                (Integer) row.get("UniqueCustomerCount")
        );
    }

    public WineSalesWithGrapeVarietiesDAO getWineSalesWithGrapeVarietiesDAO() {
        return wineSalesWithGrapeVarietiesDAO;
    }
} 