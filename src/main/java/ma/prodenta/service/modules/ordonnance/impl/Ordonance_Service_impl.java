package ma.prodenta.service.modules.ordonnance.impl;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Prescription;
import ma.prodenta.repository.modules.ordonnance.impl.OrdonnanceDaoImpl;
import ma.prodenta.service.modules.dossierMedical.impl.DossierMedicalServiceImpl;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.service.modules.ordonnance.api.Ordonnance_Service_api;
import java.time.LocalDate;
import java.util.List;

public class Ordonance_Service_impl  implements Ordonnance_Service_api {
    @Override
    public List<Ordonnance> consulterOrdonnancesParDossier(Integer idDossier) throws Exception {
        if (idDossier<=0){
            throw new Exception ("veuillez saisir des parametres qui sont valides ");
        }
        OrdonnanceDaoImpl ordonnanceDao = Application_contexte.getOrdonnanceRepository();
        DossierMedical dossier=new DossierMedical();
        List<Ordonnance>ordonnances=ordonnanceDao.list_ordonances_dossier(idDossier);
        if(ordonnances==null|| ordonnances.isEmpty()){
            throw new Exception("il y'a aucun ordonancess qui s'appartient a ce dossier medical");
        }
        return ordonnances;
    }

    @Override
    public List<Ordonnance> consulterOrdonnancesParConsultation(Integer idConsultation) throws Exception {
        OrdonnanceDaoImpl ordonnanceDao=Application_contexte.getOrdonnanceRepository();
        if(idConsultation<=0){
            throw new Exception("vous devez saisir des donnees qui sont valides ");
        }
        if(ordonnanceDao.consulterOrdonnancesParConsultation(idConsultation)==null){
            throw new Exception ("impossible de lire les ordonances de cette consultation");
        }
        return ordonnanceDao.consulterOrdonnancesParConsultation(idConsultation);
    }

    @Override
    public List<Medicament> listerMedicamentsPrescrits(Ordonnance ordonnance) throws Exception {
        if(ordonnance==null ||ordonnance.getIdOrd()<=0){
            throw new Exception("veuillez saisir des parametres qui sont valides ");
        }
        OrdonnanceDaoImpl ordonnanceDao=Application_contexte.getOrdonnanceRepository();
       List<Medicament>medicaments = ordonnanceDao.find_all_medicament_in_ordonance(ordonnance);
       if(medicaments==null||medicaments.isEmpty()){
           throw new Exception ("cette ordonance ne contient aucun medicament ");
       }
       return medicaments;
    }

    @Override
    public double calculerCoutTotal(Ordonnance ordonnance) throws Exception {
        OrdonnanceDaoImpl ordonnanceDao=Application_contexte.getOrdonnanceRepository();
        if(ordonnance==null||ordonnance.getIdOrd()<=0){
            throw new Exception("veuillez saisir des parametres qui sont valides");
        }
        double total= ordonnanceDao.calculerCoutTotal(ordonnance);
        if(total==0) throw new Exception("le total de cette ordonnace est 0 !!");
        return total;
    }

    @Override
    public List<Prescription> list_Prescriptions(Ordonnance ordonnance) throws Exception {
        if (ordonnance==null || ordonnance.getIdOrd()<=0) {
            throw new Exception("veuillez saisir des parametres qui sont valides");
        }
        OrdonnanceDaoImpl ordonnanceDao=Application_contexte.getOrdonnanceRepository();
        List<Prescription> prescriptions=ordonnanceDao.list_Prescriptions(ordonnance);
        if(prescriptions==null||prescriptions.isEmpty()){
            throw new Exception("cette consultation ne contient aucune prescription");
        }
        return prescriptions;
    }
}
