package ftn.bp2.service;

import ftn.bp2.dao.VinogradDAO;
import ftn.bp2.dto.VinogradDTO;
import ftn.bp2.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VinogradService {
    private final VinogradDAO vinogradDAO;

    public VinogradService() {
        this.vinogradDAO = new VinogradDAO();
    }

    public List<VinogradDTO> getAllVinogradi() throws SQLException {
        return vinogradDAO.findAll();
    }

    public VinogradDTO getVinogradById(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("ID vinograda ne može biti null");
        }
        return vinogradDAO.findById(id);
    }

    public List<VinogradDTO> getVinogradiByParentId(Integer parentId) throws SQLException {
        if (parentId == null) {
            throw new IllegalArgumentException("Parent ID ne može biti null");
        }
        return vinogradDAO.findByParentId(parentId);
    }

    public Integer createVinograd(VinogradDTO vinograd) throws SQLException {
        validateVinograd(vinograd);

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            Integer newId = vinogradDAO.insert(vinograd);

            if (newId != null) {
                conn.commit();
                return newId;
            } else {
                conn.rollback();
                throw new SQLException("Greška pri kreiranju vinograda");
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                DatabaseConnection.closeConnection(conn);
            }
        }
    }

    public boolean updateVinograd(VinogradDTO vinograd) throws SQLException {
        validateVinograd(vinograd);

        if (vinograd.getIdV() == null) {
            throw new IllegalArgumentException("ID vinograda je obavezan za ažuriranje");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            boolean updated = vinogradDAO.update(vinograd);

            if (updated) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                DatabaseConnection.closeConnection(conn);
            }
        }
    }

    public boolean deleteVinograd(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("ID vinograda ne može biti null");
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            boolean deleted = vinogradDAO.delete(id);

            if (deleted) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                DatabaseConnection.closeConnection(conn);
            }
        }
    }

    private void validateVinograd(VinogradDTO vinograd) {
        if (vinograd == null) {
            throw new IllegalArgumentException("Vinograd ne može biti null");
        }

        if (vinograd.getImeV() == null || vinograd.getImeV().trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv vinograda je obavezan");
        }

        if (vinograd.getPoV() == null || vinograd.getPoV() <= 0) {
            throw new IllegalArgumentException("Površina vinograda mora biti veća od 0");
        }

        if (vinograd.getDatOsn() != null && vinograd.getDatOsn().getYear() < 1900) {
            throw new IllegalArgumentException("Godina osnivanja mora biti nakon 1900");
        }
    }

    public VinogradDAO getVinogradDAO() {
        return vinogradDAO;
    }
}