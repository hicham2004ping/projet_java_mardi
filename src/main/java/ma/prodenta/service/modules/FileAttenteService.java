package ma.prodenta.service.modules;

import ma.prodenta.entities.En.FileAttente;
import ma.prodenta.repository.modules.fieldattente.FileAttenteDaoImpl;
import ma.prodenta.common.exceptions.ServiceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FileAttenteService {
    private final FileAttenteDaoImpl fileAttenteDao;

    public FileAttenteService() {
        this.fileAttenteDao = new FileAttenteDaoImpl();
    }

    public FileAttente findById(Integer idFileAttente) throws ServiceException {
        try {
            return fileAttenteDao.findById(idFileAttente);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de file d'attente", e);
        }
    }

    public List<FileAttente> getQueueByDate(LocalDate dateFile) throws ServiceException {
        try {
            return fileAttenteDao.findByDateFile(dateFile);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la file d'attente", e);
        }
    }

    public List<FileAttente> getTodayQueue() throws ServiceException {
        try {
            return fileAttenteDao.findTodayQueue();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la file d'attente d'aujourd'hui", e);
        }
    }

    public List<FileAttente> getPatientQueues(Integer idDossier) throws ServiceException {
        try {
            return fileAttenteDao.findByIdDossier(idDossier);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des files du patient", e);
        }
    }

    public void addPatientToQueue(Integer idDossier, LocalDate dateFile) throws ServiceException {
        try {
            // Check if patient already in today's queue
            List<FileAttente> todayQueue = fileAttenteDao.findByDateFile(dateFile);
            for (FileAttente item : todayQueue) {
                if (item.getIdDossier().equals(idDossier)) {
                    throw new ServiceException("Le patient est déjà en file d'attente pour cette date");
                }
            }
            
            Integer nextPosition = fileAttenteDao.getLastPosition(dateFile) + 1;
            
            FileAttente fileAttente = new FileAttente();
            fileAttente.setIdDossier(idDossier);
            fileAttente.setDateFile(dateFile);
            fileAttente.setStatut("En attente");
            fileAttente.setPosition(nextPosition);
            fileAttente.setDateArrivee(LocalDateTime.now());
            
            fileAttenteDao.create(fileAttente);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'ajout du patient à la file d'attente", e);
        }
    }

    public void removePatientFromQueue(Integer idFileAttente) throws ServiceException {
        try {
            fileAttenteDao.delete(idFileAttente);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du patient de la file d'attente", e);
        }
    }

    public void markAsInConsultation(Integer idFileAttente) throws ServiceException {
        try {
            FileAttente fileAttente = fileAttenteDao.findById(idFileAttente);
            if (fileAttente != null) {
                fileAttente.setStatut("En consultation");
                fileAttenteDao.update(fileAttente);
            }
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du marquage du patient comme en consultation", e);
        }
    }

    public void markAsFinished(Integer idFileAttente) throws ServiceException {
        try {
            FileAttente fileAttente = fileAttenteDao.findById(idFileAttente);
            if (fileAttente != null) {
                fileAttente.setStatut("Terminé");
                fileAttenteDao.update(fileAttente);
            }
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du marquage du patient comme terminé", e);
        }
    }

    public FileAttente getFirstPatientInWaiting(LocalDate dateFile) throws ServiceException {
        try {
            List<FileAttente> queue = fileAttenteDao.findByDateFile(dateFile);
            for (FileAttente patient : queue) {
                if ("En attente".equals(patient.getStatut())) {
                    return patient;
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du prochain patient", e);
        }
        return null;
    }

    public void clearQueueByDate(LocalDate dateFile) throws ServiceException {
        try {
            fileAttenteDao.deleteByDateFile(dateFile);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la file d'attente de la date", e);
        }
    }
}
