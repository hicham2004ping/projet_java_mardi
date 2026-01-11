package ma.prodenta.service.modules.rendezvous.baseImplementation;

import ma.prodenta.entities.En.Consultation;
import ma.prodenta.entities.En.Intervention;
import ma.prodenta.entities.En.RDV;
import ma.prodenta.repository.modules.RendezVous.api.RDVDAO;
import ma.prodenta.repository.modules.RendezVous.fileBase_implementation.RDVDAOImpl;
import ma.prodenta.repository.modules.consultation.impl.ConsultationDaoimpl;
import ma.prodenta.repository.modules.intervention_medcin.impl.Intervention_impl;
import ma.prodenta.service.modules.rendezvous.api.RDVI;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RDVimple implements RDVI {

    private RDVDAO rdvDAO = new RDVDAOImpl();
    private ConsultationDaoimpl consultationDAO = new ConsultationDaoimpl();
    private Intervention_impl interventionDAO = new Intervention_impl();

    private int toMinutes(Time time) {
        return time.getHours() * 60 + time.getMinutes();
    }

    private boolean chevauche(int debut1, int fin1, int debut2, int fin2) {
        return debut1 < fin2 && debut2 < fin1;
    }


    private int calculerDureeRDV(RDV rdv) throws Exception {
        int dureeTotale = 0;

        Consultation consultation = consultationDAO.findById(rdv.getIdRDV());
        if (consultation == null) return 0;

        List<Intervention> interventions =
                interventionDAO.interventions_par_consultation(consultation);

        for (Intervention i : interventions) {
            dureeTotale += i.getDuree();
        }

        return dureeTotale;
    }

    @Override
    public List<RDV> TrouveCrenau(Date date) {
        try {
            return rdvDAO.FindByDay(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> TrouveCrenauLibre(Date date) throws Exception {

        List<RDV> rdvs = TrouveCrenau(date);

        int[] creneaux = {6,7,8,9,10,11,12,15,16,17,18,19,20,21,22};
        List<Integer> libres = new ArrayList<>();

        for (int heure : creneaux) {

            int debutCreneau = heure * 60;
            int finCreneau = debutCreneau + 60;
            boolean occupe = false;

            for (RDV rdv : rdvs) {

                int debutRDV = toMinutes(rdv.getHeure());
                int dureeRDV = calculerDureeRDV(rdv);
                int finRDV = debutRDV + dureeRDV;

                if (chevauche(debutCreneau, finCreneau, debutRDV, finRDV)) {
                    occupe = true;
                    break;
                }
            }

            if (!occupe) {
                libres.add(heure);
            }
        }

        return libres;
    }
    @Override
    public boolean create(RDV rdv) throws Exception {

        LocalDate today = LocalDate.now();
        LocalDate dateRDV = rdv.getDateRDV().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int heure = rdv.getHeure().getHours();
        List<Integer> creneauxLibres = TrouveCrenauLibre(rdv.getDateRDV());

        if (!dateRDV.isBefore(today) && creneauxLibres.contains(heure)) {
            rdvDAO.create(rdv);
            return true;
        }

        return false;
    }

    @Override
    public boolean update(RDV rdv) {
        try {
            rdvDAO.update(rdv);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(RDV rdv) {
        try {
            rdvDAO.delete(rdv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public RDV find(Integer id) throws Exception {
        return rdvDAO.findById(id);
    }
}
