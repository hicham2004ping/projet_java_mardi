package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.Medecin;
import ma.prodenta.repository.modules.medcin.medcin_impl.MedecinDaoimpl;

import java.util.List;

public class TestMedecin {

    public static void main(String[] args) throws Exception {
        MedecinDaoimpl medDao = new MedecinDaoimpl();

        // 1️⃣ Test get_last_id()
        Integer lastId = medDao.get_last_id();
        System.out.println("Dernier idUser existant : " + lastId);

        // 2️⃣ Test create()
        int newId = (lastId != null ? lastId + 1 : 1);
        Medecin medecin = Medecin.builder()
                .idUser(newId)
                .specialite("Cardiologie")
                .agendaMensuel("Lundi: 9-12, Mardi: 14-18")
                .build();

        boolean created = medDao.create(medecin);
        System.out.println("Création effectuée : " + created);

        // 3️⃣ Test findById()
        Medecin found = medDao.findById(newId);
        System.out.println("Medecin trouvé : " + found);

        // 4️⃣ Test findAll()
        List<Medecin> allMedecins = medDao.findAll();
        System.out.println("Tous les médecins : " + allMedecins);

        // 5️⃣ Test update()
        medecin.setAgendaMensuel("Lundi: 10-12, Mardi: 15-18");
        medDao.update(medecin);
        Medecin updated = medDao.findById(newId);
        System.out.println("Medecin mis à jour : " + updated);

        // 6️⃣ Test delete()
        boolean deleted = medDao.delete(medecin);
        System.out.println("Medecin supprimé : " + deleted);
    }
}
