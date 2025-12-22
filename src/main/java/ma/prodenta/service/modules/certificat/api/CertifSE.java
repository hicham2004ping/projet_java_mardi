package ma.prodenta.service.modules.certificat.api;

import ma.prodenta.entities.En.Certificat;
import ma.prodenta.service.common.Service;

import java.util.Date;
import java.util.List;

public interface CertifSE extends Service<Certificat,Integer> {
    public List<Certificat> findExpireDepuis(int jours) throws Exception;
    public List<Certificat> trierParDateDebut(boolean asc) throws Exception;
    public List<Certificat> trierParDateFin(boolean asc) throws Exception;
    public boolean isValid(Certificat c);
    public List<Certificat> findValidesDansPeriode(Date debut, Date fin) throws Exception;
    public List<Certificat> findByPatient(int idPatient) throws Exception;
    public List<Certificat> findExpire() throws Exception;
    public List<Certificat> findValides() throws Exception;
}
