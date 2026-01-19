package ma.prodenta.repository.modules.fieldattente;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.FileAttente;
import ma.prodenta.common.exceptions.RepositoryException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileAttenteDaoImpl implements FileAttenteDao {

    @Override
    public FileAttente findById(Integer idFileAttente) {
        String sql = "SELECT * FROM file_attente WHERE idFileAttente = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFileAttente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFileAttente(rs);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erreur lors de la recherche de file d'attente");
        }
        return null;
    }

    @Override
    public List<FileAttente> findByDateFile(LocalDate dateFile) {
        String sql = "SELECT * FROM file_attente WHERE dateFile = ? ORDER BY position ASC";
        List<FileAttente> fileAttenteList = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dateFile));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    fileAttenteList.add(mapResultSetToFileAttente(rs));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erreur lors de la récupération de la file d'attente pour la date");
        }
        return fileAttenteList;
    }

    @Override
    public List<FileAttente> findByIdDossier(Integer idDossier) throws RepositoryException {
        String sql = "SELECT * FROM file_attente WHERE idDossier = ? ORDER BY dateFile DESC, dateArrivee DESC";
        List<FileAttente> fileAttenteList = new ArrayList<>();
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDossier);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    fileAttenteList.add(mapResultSetToFileAttente(rs));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erreur lors de la récupération de la file d'attente par dossier");
        }
        return fileAttenteList;
    }

    @Override
    public List<FileAttente> findTodayQueue() {
        return findByDateFile(LocalDate.now());
    }

    @Override
    public void create(FileAttente fileAttente) {
        String sql = "INSERT INTO file_attente (idDossier, dateFile, statut, position, dateArrivee) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, fileAttente.getIdDossier());
            stmt.setDate(2, Date.valueOf(fileAttente.getDateFile()));
            stmt.setString(3, fileAttente.getStatut() != null ? fileAttente.getStatut() : "En attente");
            stmt.setInt(4, fileAttente.getPosition() != null ? fileAttente.getPosition() : 1);
            stmt.setTimestamp(5, Timestamp.valueOf(fileAttente.getDateArrivee() != null ? fileAttente.getDateArrivee() : LocalDateTime.now()));
            
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    fileAttente.setIdFileAttente(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erreur lors de la création de file d'attente");
        }
    }

    @Override
    public void update(FileAttente fileAttente) {
        String sql = "UPDATE file_attente SET idDossier = ?, dateFile = ?, statut = ?, position = ?, dateArrivee = ? WHERE idFileAttente = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fileAttente.getIdDossier());
            stmt.setDate(2, Date.valueOf(fileAttente.getDateFile()));
            stmt.setString(3, fileAttente.getStatut());
            stmt.setInt(4, fileAttente.getPosition());
            stmt.setTimestamp(5, Timestamp.valueOf(fileAttente.getDateArrivee()));
            stmt.setInt(6, fileAttente.getIdFileAttente());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erreur lors de la mise à jour de file d'attente");
        }
    }

    @Override
    public void delete(Integer idFileAttente) {
        String sql = "DELETE FROM file_attente WHERE idFileAttente = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFileAttente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erreur lors de la suppression de file d'attente");
        }
    }

    @Override
    public void deleteByDateFile(LocalDate dateFile) {
        String sql = "DELETE FROM file_attente WHERE dateFile = ?";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dateFile));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erreur lors de la suppression de la file d'attente de la date");
        }
    }

    @Override
    public Integer getLastPosition(LocalDate dateFile) {
        String sql = "SELECT MAX(position) as maxPos FROM file_attente WHERE dateFile = ? AND statut = 'En attente'";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(dateFile));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Integer maxPos = (Integer) rs.getObject("maxPos", Integer.class);
                    return maxPos != null ? maxPos : 0;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Erreur lors de la récupération de la dernière position");
        }
        return 0;
    }

    private FileAttente mapResultSetToFileAttente(ResultSet rs) throws SQLException {
        FileAttente fileAttente = new FileAttente();
        fileAttente.setIdFileAttente(rs.getInt("idFileAttente"));
        fileAttente.setIdDossier(rs.getInt("idDossier"));
        fileAttente.setDateFile(rs.getDate("dateFile").toLocalDate());
        fileAttente.setStatut(rs.getString("statut"));
        fileAttente.setPosition(rs.getInt("position"));
        fileAttente.setDateArrivee(rs.getTimestamp("dateArrivee").toLocalDateTime());
        return fileAttente;
    }
}
