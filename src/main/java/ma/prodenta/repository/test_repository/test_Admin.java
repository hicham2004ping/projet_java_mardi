package ma.prodenta.test_repository;

import ma.prodenta.entities.En.Admin;
import ma.prodenta.repository.modules.admin.api.AdminDao;
import ma.prodenta.repository.modules.admin.implementation.Admin_impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class test_Admin {

    public static void main(String[] args) {
        AdminDao adminDao = new Admin_impl();

        try {
            // 1) Création d'un admin de test
            Admin admin = Admin.builder()
                    .username("admin_test_repo")
                    .nom("Admin Test")
                    .password_hash("test_password")  // à adapter si tu hashes plus tard
                    .email("admin.test@local.com")
                    .role("ADMIN")
                    .lastLoginDate(LocalDateTime.now())
                    .idRole(1)
                    .build();

            System.out.println("=== TEST CREATE ===");
            boolean created = adminDao.create(admin);
            System.out.println("Admin créé ? " + created);

            // 2) Récupérer tous les admins
            System.out.println("\n=== TEST FIND ALL ===");
            List<Admin> all = adminDao.findAll();
            all.forEach(a -> System.out.println(
                    "ID=" + a.getId() +
                            ", username=" + a.getUsername() +
                            ", email=" + a.getEmail()
            ));

            // 3) Récupérer par username
            System.out.println("\n=== TEST FIND BY USERNAME ===");
            Optional<Admin> optByUsername = adminDao.findByUsername("admin_test_repo");
            if (optByUsername.isEmpty()) {
                System.out.println("Aucun admin trouvé avec username=admin_test_repo");
                return;
            }
            Admin saved = optByUsername.get();
            System.out.println("Trouvé : ID=" + saved.getId() + ", nom=" + saved.getNom());

            int id = saved.getId();

            // 4) Récupérer par ID
            System.out.println("\n=== TEST FIND BY ID ===");
            Admin byId = adminDao.findById(id);
            if (byId != null) {
                System.out.println("Admin trouvé par ID : " + byId.getUsername());
            } else {
                System.out.println("Admin introuvable par ID !");
            }

            // 5) Update
            System.out.println("\n=== TEST UPDATE ===");
            saved.setNom("Admin Test Modifié");
            saved.setEmail("admin.test.modifie@local.com");
            adminDao.update(saved);
            Admin updated = adminDao.findById(id);
            System.out.println("Nom après update : " + updated.getNom());
            System.out.println("Email après update : " + updated.getEmail());

            // 6) existsById & count
            System.out.println("\n=== TEST EXISTS BY ID & COUNT ===");
            boolean exists = adminDao.existsById(id);
            long count = adminDao.count();
            System.out.println("existsById(" + id + ") = " + exists);
            System.out.println("count() = " + count);

            // 7) Pagination
            System.out.println("\n=== TEST FIND PAGE (limit=5, offset=0) ===");
            List<Admin> page = adminDao.findPage(5, 0);
            page.forEach(a -> System.out.println(
                    "Page -> ID=" + a.getId() + ", username=" + a.getUsername()
            ));

            // 8) Login
            System.out.println("\n=== TEST LOGIN ===");
            Optional<Admin> loginOk = adminDao.login("admin_test_repo", "test_password");
            System.out.println("Login correct ? " + loginOk.isPresent());

            Optional<Admin> loginFail = adminDao.login("admin_test_repo", "mauvais_mdp");
            System.out.println("Login avec mauvais mdp doit être vide -> " + loginFail.isEmpty());

            // 9) DeleteById
            System.out.println("\n=== TEST DELETE BY ID ===");
            boolean deleted = adminDao.deleteById(id);
            System.out.println("Admin supprimé ? " + deleted);

            Admin afterDelete = adminDao.findById(id);
            System.out.println("Admin après suppression (doit être null) -> " + afterDelete);

            System.out.println("\n=== FIN TEST REPOSITORY ADMIN ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
