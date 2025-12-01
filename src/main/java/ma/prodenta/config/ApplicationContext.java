package ma.prodenta.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.controllers.modules.patient.api.PatientController;
import ma.prodenta.repository.modules.patient.api.PatientDao;
import ma.prodenta.service.modules.patient.api.PatientService;
import ma.prodenta.mvc.controllers.modules.admin.AdminAuthController;
import ma.prodenta.repository.common.AdminRepository;
import ma.prodenta.repository.modules.admin.AdminRepositoryImpl;
import ma.prodenta.service.common.AdminService;
import ma.prodenta.service.modules.admin.AdminServiceImpl;

//
import ma.prodenta.repository.common.DossierMedicalRepository;
import ma.prodenta.repository.modules.dossierMedical.DossierMedicalRepositoryImpl;
import ma.prodenta.service.common.DossierMedicalService;
import ma.prodenta.service.modules.dossierMedical.DossierMedicalServiceImpl;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;


// Fabrique
public class ApplicationContext {

    private static final Map<Class<?>, Object> context       = new HashMap<>();
    private static final Map<String, Object>   contextByName = new HashMap<>(); // Ajout d'une deuxième map

    static {
        var configFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/beans.properties");

        if (configFile != null) {
            Properties properties = new Properties();
            try {
                properties.load(configFile);
                String daoClassName = properties.getProperty("patientRepo");
                String servClassName = properties.getProperty("patientService");
                String ctrlClassName = properties.getProperty("patientController");

                Class<?> cRepository = Class.forName(daoClassName);
                PatientDao repository = (PatientDao) cRepository.getDeclaredConstructor().newInstance();

                Class<?> cService = Class.forName(servClassName);
                PatientService service = (PatientService) cService.getDeclaredConstructor(PatientDao.class).newInstance(repository);

                Class<?> cController = Class.forName(ctrlClassName);
                PatientController controller = (PatientController) cController.getDeclaredConstructor(PatientService.class).newInstance(service);

                // Stockage des beans dans le contexte
                context.put(PatientDao.class, repository);
                context.put(PatientService.class, service);
                context.put(PatientController.class, controller);

                // Enregistrement des beans aussi avec des noms explicites
                contextByName.put("patientDao", repository);
                contextByName.put("patientService", service);
                contextByName.put("patientController", controller);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Erreur : Le fichier beans.properties est introuvable !");
        }
    }

    /**
     * Retourne un composant bean en fonction de son nom (String).
     */
    public static Object getBean(String beanName) {
        return contextByName.get(beanName);
    }

    /**
     * Retourne un composant bean en fonction de sa classe.
     */
    public static <T> T getBean(Class<T> beanClass) {
        return beanClass.cast(context.get(beanClass));
    }
    //travail au dessous de othmane (auth)
    private static SessionFactory sessionFactory;

    static {
        // TODO: initialiser sessionFactory (URL, user, password) si ce n'est pas déjà fait.
        sessionFactory = new SessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // ==== Beans Admin ====

    public static AdminRepository getAdminRepository() {
        return new AdminRepositoryImpl(getSessionFactory());
    }

    public static AdminService getAdminService() {
        return new AdminServiceImpl(getAdminRepository());
    }

    public static AdminAuthController getAdminAuthController() {
        return new AdminAuthController(getAdminService());
    }

    public static DossierMedicalController getDossierMedicalController() {
        return null;
    }
}
//youssef

        public static DossierMedicalRepository getDossierMedicalRepository() {
            return new DossierMedicalRepositoryImpl(ApplicationContext.getSessionFactory());
        }

        public static DossierMedicalService getDossierMedicalService() {
            return new DossierMedicalServiceImpl(getDossierMedicalRepository());
        }

        public static DossierMedicalController getDossierMedicalController() {
            return new DossierMedicalController(getDossierMedicalService());
        }










