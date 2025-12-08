package ma.prodenta.service.modules.certificat.api;

import ma.prodenta.entities.En.Certificat;
import ma.prodenta.service.common.Service;

import java.util.List;

public interface CertifSE extends Service<Certificat,Integer> {
    public List<Certificat> findPermime() throws Exception;
    public List<Certificat> findMazal() throws Exception;
}
