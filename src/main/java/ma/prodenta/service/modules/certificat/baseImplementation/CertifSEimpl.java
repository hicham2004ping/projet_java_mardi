package ma.prodenta.service.modules.certificat.baseImplementation;

import ma.prodenta.entities.En.Certificat;
import ma.prodenta.repository.modules.certificat.impl.CertificatDaoimpl;
import ma.prodenta.repository.modules.ordonnance.api.Ordonance_api;
import ma.prodenta.service.modules.certificat.api.CertifSE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CertifSEimpl implements CertifSE {

    @Override
    public boolean create(Certificat objet) throws Exception {
        CertificatDaoimpl  cs = new CertificatDaoimpl();
        Date test=objet.getDateDebut();
        Date test2=objet.getDateFin();
        if (test == null || test2 == null) {
            return false;
        }
        if (test.before(test2)) {
            try {
                cs.create(objet);
                return true;
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
        return false;
    }

    @Override
    public boolean update(Certificat objet) throws Exception {
        CertificatDaoimpl  cs = new CertificatDaoimpl();
        Date test=objet.getDateDebut();
        Date test2=objet.getDateFin();
        if (test.before(test2)) {
            try {
                cs.update(objet);
            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }
        return true;
    }

    @Override
    public void delete(Certificat objet) throws Exception {
        CertificatDaoimpl  cs = new CertificatDaoimpl();
        try {
            cs.delete(objet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Certificat find(Integer integer) throws Exception {
        CertificatDaoimpl  cs = new CertificatDaoimpl();
        Certificat t=cs.findById(integer);
        return  t;
    }
    public List<Certificat> findPermime() throws Exception {
        CertificatDaoimpl  cs = new CertificatDaoimpl();
        Date aujourdHui = new Date();
        List <Certificat> t=new ArrayList<Certificat>(cs.findAll());
        List <Certificat> t2=new ArrayList<Certificat>();
        for (int i=0;i<t.size();i++) {
            Certificat t1=t.get(i);
            if (t1.getDateFin().before(aujourdHui)) {
                t2.add(t1);
            }
        }
        return t2;
    }

    @Override
    public List<Certificat> findMazal() throws Exception {
        CertificatDaoimpl  cs = new CertificatDaoimpl();
        Date aujourdHui = new Date();
        List <Certificat> t=new ArrayList<Certificat>(cs.findAll());
        List <Certificat> t2=new ArrayList<Certificat>();
        for (int i=0;i<t.size();i++) {
            Certificat t1=t.get(i);
            if (t1.getDateDebut()==aujourdHui) {
                t2.add(t1);
            }
        }
        return t2;
    }

}
