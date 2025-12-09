package ma.prodenta.repository.test_repository;

import ma.prodenta.entities.En.Role;
import ma.prodenta.repository.modules.role.impl.Role_impl;

public class RoleTest {

    public static void main(String[] args) {

        Role_impl dao = new Role_impl();

        try {
            System.out.println("===== TEST ROLE =====");

            // 1 - CREATE
            Role r1 = Role.builder()
                    .libelle("Administrateur")
                    .build();

            boolean created = dao.create(r1);
            System.out.println("Insertion : " + created);

            // 2 - FIND ALL
            System.out.println("\nListe des rôles : ");
            for(Role r : dao.findAll()) {
                System.out.println(r.getIdRole() + " - " + r.getLibelle());
            }

            // 3 - FIND BY ID (ici on prend le dernier ID inséré)
            System.out.println("\nRecherche par ID : ");
            Role last = dao.find_by_nom("Administrateur"); // on récupère Administrateur
            Role found = dao.findById(last.getIdRole());
            System.out.println(found);

            // 4 - UPDATE
            System.out.println("\nMise à jour : ");
            found.setLibelle("Admin Modifié");
            dao.update(found);
            System.out.println("Rôle mis à jour.");

            // 5 - DELETE
            System.out.println("\nSuppression : ");
            boolean deleted = dao.delete(found);
            System.out.println("Supprimé : " + deleted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
