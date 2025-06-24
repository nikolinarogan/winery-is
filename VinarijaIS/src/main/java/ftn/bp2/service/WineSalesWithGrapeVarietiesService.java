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


    public WineSalesWithGrapeVarietiesDAO getWineSalesWithGrapeVarietiesDAO() {
        return wineSalesWithGrapeVarietiesDAO;
    }
} 