package ma.prodenta.service.modules.ordonnance.impl;

import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.repository.modules.ordonnance.impl.OrdonnanceDaoImpl;
import ma.prodenta.service.modules.ordonnance.api.OrdonnanceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdonnanceImpl implements OrdonnanceService {

    private OrdonnanceDaoImpl ordonnanceDAO = new OrdonnanceDaoImpl();

    @Override
    public boolean create(Ordonnance ord) throws Exception {
        try {
            if (ord.getDateOrd() == null) {
                ord.setDateOrd(LocalDate.now());
            }
            return ordonnanceDAO.create(ord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean update(Ordonnance ord) {
        try {
            ordonnanceDAO.update(ord);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean delete(Ordonnance ord) throws Exception {
        try {
            ordonnanceDAO.delete(ord);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false; // reje3tha boolean 7it katl3 bl 7mr :)
    }

    @Override
    public Ordonnance find(Integer integer) throws Exception {
        return null;
    }

    public Ordonnance findById(Long idOrd) {
        try {
            return ordonnanceDAO.findById(Math.toIntExact(idOrd));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ordonnance> findAll() {
        try {
            return ordonnanceDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public int totalOrdonnance(Ordonnance ord) {
        return ordonnanceDAO.Total_ordonance(ord);
    }


    public List<Medicament> medicamentsOrdonnance(Ordonnance ord) {
        return ordonnanceDAO.find_all_medicament_in_ordonance(ord);
    }


    public int lastId() {
        return ordonnanceDAO.last_id();
    }


    public List<Ordonnance> findByPatient(int idPatient) {
        List<Ordonnance> all;
        try {
            all = ordonnanceDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Ordonnance> result = new ArrayList<>();
        for (Ordonnance o : all) {
            if (o.getIdDossier() == idPatient) { // on suppose idDossier correspond au patient
                result.add(o);
            }
        }
        return result;
    }


    public List<Ordonnance> findByDate(LocalDate date) {
        List<Ordonnance> all;
        try {
            all = ordonnanceDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Ordonnance> result = new ArrayList<>();
        for (Ordonnance o : all) {
            if (o.getDateOrd().equals(date)) {
                result.add(o);
            }
        }
        return result;
    }

























}
