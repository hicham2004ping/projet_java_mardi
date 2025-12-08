package ma.prodenta.test_repository;

import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.repository.modules.auth.api.AuthDao;
import ma.prodenta.repository.modules.auth.implementation.AuthDaoImpl;
import ma.prodenta.tools.GenerateHash;
import ma.prodenta.common.util.PasswordUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class test_Auth {

    public static void main(String[] args) {

        AuthDao authDao = new AuthDaoImpl();

        try {

            System.out.println("\n===== TEST REPOSITORY AUTH =====\n");

            // ============================
            // 1) Création d'un utilisateur
            // ============================
            System.out.println("=== TEST CREATE ===");

            String rawPwd = "test123"; // mot de passe normal
            String hashedPwd = PasswordUtil.hashPassword(rawPwd); // Hash réel

            Utilisateur user = Utilisateur.builder()
                    .nom("User Test")
                    .email("test.user@local.com")
                    .adresse("Casablanca")
                    .cin("EE123456")
                    .tel("0600000000")
                    .idSexe(1)
                    .login("login_test")
                    .motdepasse(hashedPwd)
                    .dateNaissance(LocalDate.of(1999, 1, 1))
                    .lastLoginDate(LocalDateTime.now())
                    .idRole(2)
                    .build();

            boolean created = authDao.create(user);
            System.out.println("Utilisateur créé ? " + created);


            // ============================
            // 2) Recherche par login
            // ============================
            System.out.println("\n=== TEST FIND BY LOGIN ===");

            Optional<Utilisateur> optLogin = authDao.findByLogin("login_test");
            if (optLogin.isEmpty()) {
                System.out.println("Utilisateur introuvable !");
                return;
            }
            Utilisateur saved = optLogin.get();
            System.out.println("Trouvé : ID=" + saved.getIdUser() + ", nom=" + saved.getNom());

            int idUser = saved.getIdUser();


            // ============================
            // 3) Recherche par email
            // ============================
            System.out.println("\n=== TEST FIND BY EMAIL ===");
            Optional<Utilisateur> optEmail = authDao.findByEmail("test.user@local.com");
            System.out.println("FindByEmail OK ? " + optEmail.isPresent());


            // ============================
            // 4) Test existsByLogin / existsByEmail
            // ============================
            System.out.println("\n=== TEST EXISTS ===");
            System.out.println("existsByLogin('login_test') = " + authDao.existsByLogin("login_test"));
            System.out.println("existsByEmail('test.user@local.com') = " + authDao.existsByEmail("test.user@local.com"));


            // ============================
            // 5) Test login (authentification)
            // ============================
            System.out.println("\n=== TEST LOGIN ===");

            Optional<Utilisateur> loginOK = authDao.login("login_test", hashedPwd);
            System.out.println("Login correct ? " + loginOK.isPresent());

            String badHash = PasswordUtil.hashPassword("wrongpass");
            Optional<Utilisateur> loginFail = authDao.login("login_test", badHash);
            System.out.println("Login incorrect (doit être false) = " + loginFail.isEmpty());


            // ============================
            // 6) Test update
            // ============================
            System.out.println("\n=== TEST UPDATE ===");

            saved.setTel("0611111111");
            saved.setNom("User Test Modifié");
            authDao.update(saved);

            Utilisateur updated = authDao.findById(idUser);
            System.out.println("Nouveau nom = " + updated.getNom());
            System.out.println("Nouveau tel = " + updated.getTel());


            // ============================
            // 7) Test count
            // ============================
            System.out.println("\n=== TEST COUNT ===");
            System.out.println("Nombre total utilisateurs = " + authDao.count());


            // ============================
            // 8) Test delete
            // ============================
            System.out.println("\n=== TEST DELETE ===");

            boolean deleted = authDao.deleteById(idUser);
            System.out.println("Utilisateur supprimé ? " + deleted);

            Utilisateur afterDelete = authDao.findById(idUser);
            System.out.println("findById après deletion = " + afterDelete);


            System.out.println("\n===== FIN TEST AUTH =====\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
