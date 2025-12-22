package ma.prodenta.service.modules.certificat.baseImplementation;

import ma.prodenta.entities.En.Certificat;
import ma.prodenta.repository.modules.certificat.impl.CertificatDaoimpl;
import ma.prodenta.service.modules.certificat.api.CertifSE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CertifSEimpl implements CertifSE {

    private CertificatDaoimpl cs = new CertificatDaoimpl();

    @Override
    public boolean create(Certificat objet) throws Exception {
        if (objet.getDateDebut() == null || objet.getDateFin() == null) {
            return false;
        }
        if (objet.getDateDebut().before(objet.getDateFin())) {
            cs.create(objet);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Certificat objet) throws Exception {
        if (objet.getDateDebut().before(objet.getDateFin())) {
            cs.update(objet);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Certificat objet) throws Exception {
        cs.delete(objet);
    }

    @Override
    public Certificat find(Integer id) throws Exception {
        return cs.findById(id);
    }



    public List<Certificat> findExpire() throws Exception {
        Date today = new Date();
        return cs.findAll().stream()
                .filter(c -> c.getDateFin().before(today))
                .collect(Collectors.toList());
    }


    public List<Certificat> findValides() throws Exception {
        Date today = new Date();
        return cs.findAll().stream()
                .filter(c -> !c.getDateDebut().after(today) && !c.getDateFin().before(today))
                .collect(Collectors.toList());
    }

    public List<Certificat> findByPatient(int idDossier) throws Exception {
        return cs.findAll().stream()
                .filter(c -> c.getIdDossier() == idDossier)
                .collect(Collectors.toList());
    }


    public List<Certificat> findValidesDansPeriode(Date debut, Date fin) throws Exception {
        return cs.findAll().stream()
                .filter(c -> !c.getDateDebut().after(fin) && !c.getDateFin().before(debut))
                .collect(Collectors.toList());
    }


    public boolean isValid(Certificat c) {
        Date today = new Date();
        return !c.getDateDebut().after(today) && !c.getDateFin().before(today);
    }

    public List<Certificat> findExpireDepuis(int jours) throws Exception {
        Date today = new Date();
        long millis = jours * 24L * 60L * 60L * 1000L;
        Date seuil = new Date(today.getTime() - millis);

        return cs.findAll().stream()
                .filter(c -> c.getDateFin().before(today) && !c.getDateFin().before(seuil))
                .collect(Collectors.toList());
    }


    public List<Certificat> trierParDateDebut(boolean asc) throws Exception {
        List<Certificat> t = new ArrayList<>(cs.findAll());
        t.sort((c1, c2) -> asc ? c1.getDateDebut().compareTo(c2.getDateDebut())
                : c2.getDateDebut().compareTo(c1.getDateDebut()));
        return t;
    }

    public List<Certificat> trierParDateFin(boolean asc) throws Exception {
        List<Certificat> t = new ArrayList<>(cs.findAll());
        t.sort((c1, c2) -> asc ? c1.getDateFin().compareTo(c2.getDateFin())
                : c2.getDateFin().compareTo(c1.getDateFin()));
        return t;
    }
}
