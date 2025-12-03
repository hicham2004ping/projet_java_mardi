package ma.prodenta.service.modules.rendezvous.baseImplementation;

import ma.prodenta.entities.En.RDV;
import ma.prodenta.repository.modules.RendezVous.api.RDVDAO;
import ma.prodenta.repository.modules.RendezVous.fileBase_implementation.RDVDAOImpl;
import ma.prodenta.service.modules.rendezvous.api.RDVI;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RDVimple implements RDVI {
    private RDVDAO rdvDAO = new RDVDAOImpl();
    @Override
    public List<RDV> TrouveCrenau(Date date) {
        try {
            List<RDV> rdv = new ArrayList<>(rdvDAO.FindByDay(date));
            List<RDV> rdv2 = new ArrayList<>();
            int[] a = {6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 18, 19, 20, 21, 22};
            int i, j;
            for (i = 0; i < rdv.size(); i++) {
                int heure = rdv.get(i).getHeure().getHours();
                for (j = 0; j < a.length; j++) {
                    if (heure == a[j]) {
                        rdv2.add(rdv.get(i));
                    }
                }

            }
            return rdv2;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }//List<RDV> rdv2 = rdv.stream()
    //.filter(r -> Arrays.stream(a).anyMatch(cr -> cr == r.getHeure().getHours()))
    //     .collect(Collectors.toList());

    @Override
    public List<Integer> TrouveCrenauLibre(Date date) {
        List<RDV> rdvPris = TrouveCrenau(date); // RDV déjà pris
        int[] tousLesCreneaux = {6,7,8,9,10,11,12,15,16,17,18,19,20,21,22};
        List<Integer> libres = new ArrayList<>();

        for (int creneau : tousLesCreneaux) {
            boolean pris = false;
            for (RDV r : rdvPris) {
                if (r.getHeure().getHours() == creneau) {
                    pris = true;
                    break;
                }
            }
            if (!pris) {
                libres.add(creneau);
            }
        }

        return libres;
    }

    @Override
    public void createRDV(RDV rdv) {
        LocalDate ld = LocalDate.now();
        LocalDate dateRDV = rdv.getDateRDV().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        int heure = rdv.getHeure().getHours();
        List<Integer> rdvPris = new ArrayList<>(TrouveCrenauLibre(rdv.getDateRDV()));
        if (dateRDV.isBefore(ld))
            if (rdvPris.contains(heure)) {
                try {
                    rdvDAO.create(rdv);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
    }

    @Override
    public void updateRDV(RDV rdv) {
        try {
            rdvDAO.update(rdv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRDV(RDV rdv) {
        try {
            rdvDAO.delete(rdv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
