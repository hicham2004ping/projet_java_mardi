package ma.prodenta.service.modules.certificat.baseImplementation;

import ma.prodenta.entities.En.Certificat;
import ma.prodenta.repository.modules.ordonnance.api.Ordonance_api;
import ma.prodenta.service.modules.certificat.api.CertifSE;

public class CertifSEimpl implements CertifSE {

    @Override
    public boolean create(Certificat objet) throws Exception {
        return false;
    }

    @Override
    public boolean update(Certificat objet) throws Exception {
        return false;
    }

    @Override
    public void delete(Certificat objet) throws Exception {

    }

    @Override
    public Certificat find(Integer integer) {
        return null;
    }
}
