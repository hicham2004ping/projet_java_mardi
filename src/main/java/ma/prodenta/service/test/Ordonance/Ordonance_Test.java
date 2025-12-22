package ma.prodenta.service.test.Ordonance;
import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.entities.En.Prescription;
import ma.prodenta.service.modules.ordonnance.impl.Ordonance_Service_impl;
import java.util.List;
public class Ordonance_Test {

    public static void main(String[] args) {

        Ordonance_Service_impl ordonnanceService = new Ordonance_Service_impl();

        try {
            int idDossier = 45;
            int idConsultation = 25;

            List<Ordonnance> ordonnancesDossier = ordonnanceService.consulterOrdonnancesParDossier(idDossier);

            System.out.println("Ordonnances par dossier : " + ordonnancesDossier.size());

            Ordonnance ordonnance = ordonnancesDossier.get(0);

            List<Ordonnance> ordonnancesConsultation = ordonnanceService.consulterOrdonnancesParConsultation(idConsultation);

            System.out.println("Ordonnances par consultation : " + ordonnancesConsultation.size());

            List<Medicament> medicaments = ordonnanceService.listerMedicamentsPrescrits(ordonnance);

            System.out.println("Medicaments prescrits : " + medicaments.size());

            double coutTotal = ordonnanceService.calculerCoutTotal(ordonnance);

            System.out.println("Cout total ordonnance : " + coutTotal);

            List<Prescription> prescriptions = ordonnanceService.list_Prescriptions(ordonnance);

            System.out.println("Prescriptions : " + prescriptions.size());

            System.out.println("TEST ORDONNANCE TERMINÉ AVEC SUCCÈS");

        } catch (Exception e) {
            System.err.println("ERREUR TEST ORDONNANCE");
            e.printStackTrace();
        }
    }

}

