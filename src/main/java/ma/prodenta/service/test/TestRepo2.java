package ma.prodenta.service.test;

import ma.prodenta.entities.En.UserManager;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.service.modules.auth.impl.AuthServiceImpl;
import ma.prodenta.service.modules.usermanager.impl.UserManagerServiceImpl;

import java.time.LocalDate;
import java.util.List;

public class TestRepo2 {

    public static void testAuthService() throws Exception {
        System.out.println("\n========================================");
        System.out.println("   TEST DU SERVICE AUTHENTICATION      ");
        System.out.println("========================================");

        AuthServiceImpl authService = new AuthServiceImpl();

        // Test 1: Inscription d'un utilisateur
        System.out.println("\n--- Test 1: Inscription utilisateur ---");
        Utilisateur newUser = new Utilisateur();
        newUser.setNom("Service Test");
        newUser.setEmail("servicetest@example.com");
        newUser.setAdresse("123 Service St");
        newUser.setCin("SRV123");
        newUser.setTel("0611223344");
        newUser.setLogin("serviceuser");
        newUser.setMotdepasse("securepass123");
        newUser.setDateNaissance(LocalDate.of(1995, 5, 15));
        newUser.setIdSexe(1);
        newUser.setIdRole(1);

        try {
            Utilisateur registered = authService.register(newUser);
            System.out.println("✓ Utilisateur inscrit avec succès: " + registered.getNom());
            System.out.println("  Login: " + registered.getLogin());
            System.out.println("  Email: " + registered.getEmail());
        } catch (Exception e) {
            System.out.println("✗ Erreur lors de l'inscription: " + e.getMessage());
        }

        // Test 2: Authentification avec login et mot de passe
        System.out.println("\n--- Test 2: Authentification ---");
        try {
            Utilisateur authenticated = authService.authenticate("serviceuser", "securepass123");
            System.out.println("✓ Authentification réussie");
            System.out.println("  Utilisateur: " + authenticated.getNom());
            System.out.println("  Email: " + authenticated.getEmail());
            System.out.println("  Dernière connexion: " + authenticated.getLastLoginDate());
        } catch (Exception e) {
            System.out.println("✗ Erreur d'authentification: " + e.getMessage());
        }

        // Test 3: Tentative avec mauvais mot de passe
        System.out.println("\n--- Test 3: Authentification avec mauvais mot de passe ---");
        try {
            authService.authenticate("serviceuser", "wrongpassword");
            System.out.println("✗ ERREUR: Authentification réussie avec mauvais mot de passe!");
        } catch (Exception e) {
            System.out.println("✓ Authentification échouée comme prévu: " + e.getMessage());
        }

        // Test 4: Recherche par login
        System.out.println("\n--- Test 4: Recherche par login ---");
        try {
            Utilisateur foundByLogin = authService.findByLogin("serviceuser");
            System.out.println("✓ Utilisateur trouvé par login: " + foundByLogin.getNom());
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 5: Recherche par email
        System.out.println("\n--- Test 5: Recherche par email ---");
        try {
            Utilisateur foundByEmail = authService.findByEmail("servicetest@example.com");
            System.out.println("✓ Utilisateur trouvé par email: " + foundByEmail.getNom());
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 6: Vérifier existence par login
        System.out.println("\n--- Test 6: Vérifier existence par login ---");
        boolean exists = authService.existsByLogin("serviceuser");
        System.out.println((exists ? "✓" : "✗") + " Login existe: " + exists);

        // Test 7: Vérifier existence par email
        System.out.println("\n--- Test 7: Vérifier existence par email ---");
        boolean emailExists = authService.existsByEmail("servicetest@example.com");
        System.out.println((emailExists ? "✓" : "✗") + " Email existe: " + emailExists);

        // Test 8: Compter les utilisateurs
        System.out.println("\n--- Test 8: Compter les utilisateurs ---");
        long count = authService.countUsers();
        System.out.println("✓ Nombre total d'utilisateurs: " + count);

        // Test 9: Obtenir tous les utilisateurs
        System.out.println("\n--- Test 9: Obtenir tous les utilisateurs ---");
        try {
            List<Utilisateur> allUsers = authService.getAllUsers();
            System.out.println("✓ Nombre d'utilisateurs récupérés: " + allUsers.size());
            for (Utilisateur u : allUsers) {
                System.out.println("  - " + u.getNom() + " (" + u.getLogin() + ")");
            }
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 10: Mise à jour utilisateur
        System.out.println("\n--- Test 10: Mise à jour utilisateur ---");
        try {
            Utilisateur userToUpdate = authService.findByLogin("serviceuser");
            userToUpdate.setNom("Service Test Updated");
            userToUpdate.setTel("0699887766");
            authService.updateUser(userToUpdate);
            
            Utilisateur updated = authService.findByLogin("serviceuser");
            System.out.println("✓ Utilisateur mis à jour");
            System.out.println("  Nouveau nom: " + updated.getNom());
            System.out.println("  Nouveau tél: " + updated.getTel());
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 11: Suppression utilisateur
        System.out.println("\n--- Test 11: Suppression utilisateur ---");
        try {
            Utilisateur userToDelete = authService.findByLogin("serviceuser");
            boolean deleted = authService.deleteUser(userToDelete.getIdUser());
            System.out.println((deleted ? "✓" : "✗") + " Utilisateur supprimé: " + deleted);
            
            // Vérifier que l'utilisateur n'existe plus
            try {
                authService.findByLogin("serviceuser");
                System.out.println("✗ ERREUR: L'utilisateur existe encore après suppression!");
            } catch (Exception e) {
                System.out.println("✓ Confirmation: L'utilisateur n'existe plus");
            }
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        System.out.println("\n========================================");
        System.out.println("   FIN TEST SERVICE AUTHENTICATION     ");
        System.out.println("========================================");
    }

    public static void testUserManagerService() throws Exception {
        System.out.println("\n========================================");
        System.out.println("     TEST DU SERVICE USERMANAGER       ");
        System.out.println("========================================");

        UserManagerServiceImpl userManagerService = new UserManagerServiceImpl();

        // Test 1: Création d'un UserManager
        System.out.println("\n--- Test 1: Création UserManager ---");
        UserManager newManager = new UserManager();
        newManager.setUsername("manager_test");
        newManager.setPasswordHash("managerpass123");
        newManager.setRole("ADMIN");
        newManager.setActif(true);

        try {
            UserManager created = userManagerService.createUser(newManager);
            System.out.println("✓ UserManager créé avec succès");
            System.out.println("  ID: " + created.getIdUser());
            System.out.println("  Username: " + created.getUsername());
            System.out.println("  Role: " + created.getRole());
            System.out.println("  Actif: " + created.getActif());
        } catch (Exception e) {
            System.out.println("✗ Erreur lors de la création: " + e.getMessage());
        }

        // Test 2: Recherche par ID
        System.out.println("\n--- Test 2: Recherche par ID ---");
        try {
            UserManager foundManager = userManagerService.findByUsername("manager_test");
            if (foundManager != null) {
                UserManager foundById = userManagerService.findById(foundManager.getIdUser());
                System.out.println("✓ UserManager trouvé par ID: " + foundById.getUsername());
            }
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 3: Recherche par username
        System.out.println("\n--- Test 3: Recherche par username ---");
        try {
            UserManager foundByUsername = userManagerService.findByUsername("manager_test");
            System.out.println("✓ UserManager trouvé par username");
            System.out.println("  ID: " + foundByUsername.getIdUser());
            System.out.println("  Role: " + foundByUsername.getRole());
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 4: Authentification UserManager
        System.out.println("\n--- Test 4: Authentification UserManager ---");
        try {
            UserManager authenticated = userManagerService.authenticateUser("manager_test", "managerpass123");
            System.out.println("✓ Authentification réussie");
            System.out.println("  Username: " + authenticated.getUsername());
            System.out.println("  Role: " + authenticated.getRole());
        } catch (Exception e) {
            System.out.println("✗ Erreur d'authentification: " + e.getMessage());
        }

        // Test 5: Authentification avec mauvais mot de passe
        System.out.println("\n--- Test 5: Authentification avec mauvais mot de passe ---");
        try {
            userManagerService.authenticateUser("manager_test", "wrongpass");
            System.out.println("✗ ERREUR: Authentification réussie avec mauvais mot de passe!");
        } catch (Exception e) {
            System.out.println("✓ Authentification échouée comme prévu: " + e.getMessage());
        }

        // Test 6: Obtenir tous les UserManagers
        System.out.println("\n--- Test 6: Obtenir tous les UserManagers ---");
        try {
            List<UserManager> allManagers = userManagerService.findAll();
            System.out.println("✓ Nombre de UserManagers: " + allManagers.size());
            for (UserManager um : allManagers) {
                System.out.println("  - " + um.getUsername() + " [" + um.getRole() + "] Actif: " + um.getActif());
            }
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 7: Mise à jour UserManager
        System.out.println("\n--- Test 7: Mise à jour UserManager ---");
        try {
            UserManager managerToUpdate = userManagerService.findByUsername("manager_test");
            managerToUpdate.setRole("SUPER_ADMIN");
            managerToUpdate.setActif(true);
            
            UserManager updated = userManagerService.updateUser(managerToUpdate);
            System.out.println("✓ UserManager mis à jour");
            System.out.println("  Nouveau role: " + updated.getRole());
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 8: Désactiver un utilisateur
        System.out.println("\n--- Test 8: Désactiver un utilisateur ---");
        try {
            UserManager managerToDeactivate = userManagerService.findByUsername("manager_test");
            boolean deactivated = userManagerService.deactivateUser(managerToDeactivate.getIdUser());
            System.out.println((deactivated ? "✓" : "✗") + " Utilisateur désactivé");
            
            UserManager deactivatedUser = userManagerService.findById(managerToDeactivate.getIdUser());
            System.out.println("  Statut actif: " + deactivatedUser.getActif());
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 9: Réactiver un utilisateur
        System.out.println("\n--- Test 9: Réactiver un utilisateur ---");
        try {
            UserManager managerToActivate = userManagerService.findByUsername("manager_test");
            boolean activated = userManagerService.activateUser(managerToActivate.getIdUser());
            System.out.println((activated ? "✓" : "✗") + " Utilisateur réactivé");
            
            UserManager activatedUser = userManagerService.findById(managerToActivate.getIdUser());
            System.out.println("  Statut actif: " + activatedUser.getActif());
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 10: Compter les utilisateurs actifs
        System.out.println("\n--- Test 10: Compter les utilisateurs actifs ---");
        try {
            long activeCount = userManagerService.countActiveUsers();
            System.out.println("✓ Nombre d'utilisateurs actifs: " + activeCount);
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        // Test 11: Suppression UserManager
        System.out.println("\n--- Test 11: Suppression UserManager ---");
        try {
            UserManager managerToDelete = userManagerService.findByUsername("manager_test");
            userManagerService.deleteUser(managerToDelete.getIdUser());
            System.out.println("✓ UserManager supprimé");
            
            // Vérifier que le manager n'existe plus
            try {
                userManagerService.findByUsername("manager_test");
                System.out.println("✗ ERREUR: Le UserManager existe encore après suppression!");
            } catch (Exception e) {
                System.out.println("✓ Confirmation: Le UserManager n'existe plus");
            }
        } catch (Exception e) {
            System.out.println("✗ Erreur: " + e.getMessage());
        }

        System.out.println("\n========================================");
        System.out.println("    FIN TEST SERVICE USERMANAGER        ");
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        try {
            System.out.println("\n");
            System.out.println("================================================");
            System.out.println("   TESTS COMPLETS DES SERVICES (LAYER SERVICE)  ");
            System.out.println("================================================");

            // Test du service d'authentification
            testAuthService();

            System.out.println("\n\n");

            // Test du service UserManager
            testUserManagerService();

            System.out.println("\n\n================================================");
            System.out.println("         TOUS LES TESTS SONT TERMINÉS          ");
            System.out.println("================================================\n");

        } catch (Exception e) {
            System.err.println("\n❌ ERREUR CRITIQUE LORS DES TESTS:");
            e.printStackTrace();
        }
    }
}
