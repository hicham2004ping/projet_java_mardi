package ma.prodenta.service.modules.cabinetMedical.impl;

import ma.prodenta.entities.En.CabinetMedical;
import ma.prodenta.repository.modules.CabinetMedical.impl.CabinetMedicaleImpl;
import ma.prodenta.service.modules.cabinetMedical.api.cabinetMedicalService;

import java.util.List;
import java.util.stream.Collectors;

public class cabinetMedicalServiceImpl implements cabinetMedicalService {
    private final CabinetMedicaleImpl cabinetDao = new CabinetMedicaleImpl();
    @Override
    public boolean create(CabinetMedical cab) throws Exception {
        if (cab.getEmail() == null || cab.getEmail().isEmpty()) return false;
        if (existByEmail(cab.getEmail())) return false; // pas de doublon email
        return cabinetDao.create(cab);
    }

    @Override
    public boolean update(CabinetMedical cab) throws Exception {
        if (cab.getIdCabinet() == null) return false;
        cabinetDao.update(cab);
        return true;
    }

    @Override
    public boolean delete(CabinetMedical cab) throws Exception {
        cabinetDao.delete(cab);
        return false;
    }

    @Override
    public CabinetMedical find(Integer id) throws Exception {
        return cabinetDao.findById(id);
    }

    @Override
    public List<CabinetMedical> findAll() throws Exception {
        return cabinetDao.findAll();
    }

    @Override
    public List<CabinetMedical> findByNom(String nom) throws Exception {
        return cabinetDao.findAll()
                .stream()
                .filter(c -> c.getNom().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CabinetMedical> findByVille(String ville) throws Exception {
        return cabinetDao.findAll()
                .stream()
                .filter(c -> c.getAdresse().toLowerCase().contains(ville.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existByEmail(String email) throws Exception {
        return cabinetDao.findAll()
                .stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public CabinetMedical findLastCreated() throws Exception {
        int lastId = cabinetDao.get_last_id();
        if (lastId == 0) return null;
        return cabinetDao.findById(lastId);
    }
}
