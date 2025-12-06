package ma.prodenta.config;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.controllers.modules.patient.api.PatientController;
import ma.prodenta.repository.modules.patient.api.PatientDao;
import ma.prodenta.service.modules.patient.api.PatientService;

import ma.prodenta.repository.common.AdminRepository;
import ma.prodenta.repository.modules.admin.implementation.Admin_impl;
import ma.prodenta.service.common.AdminService;
import ma.prodenta.service.modules.admin.AdminServiceImpl;
import ma.prodenta.repository.modules.dossierMedical.implementation.DossierMedicalRepositoryImpl;
import ma.prodenta.repository.common.DossierMedicalRepository;
import ma.prodenta.service.common.DossierMedicalService;
import ma.prodenta.service.modules.dossierMedical.DossierMedicalServiceImpl;

public class ApplicationContext {

    private static final Map<Class<?>, Object> context       = new HashMap<>();
    private static final Map<String, Object>   contextByName = new HashMap<>();

    // ================= INITIALISATION PATIENT ==================
    static {
        var configFile = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("config/beans.properties");

        if (configFile != null) {
            Properties properties = new Properties();
            try {
                properties.load(configFile);

                String daoClassName  = properties.getProperty("patientRepo");
                String servClassName = properties.getProperty("patientService");
                String ctrlClassName = properties.getProperty("patientController");

                Class<?> cRepository = Class.forName(daoClassName);
                PatientDao repository =
                        (PatientDao) cRepository.getDeclaredConstructor().newInstance();

                Class<?> cService = Class.forName(servClassName);
                PatientService service =
                        (PatientService) cService
                                .getDeclaredConstructor(PatientDao.class)
                                .newInstance(repository);

                Class<?> cController = Class.forName(ctrlClassName);
                PatientController controller =
                        (PatientController) cController
                                .getDeclaredConstructor(PatientService.class)
                                .newInstance(service);

                context.put(PatientDao.class, repository);
                context.put(PatientService.class, service);
                context.put(PatientController.class, controller);

                contextByName.put("patientDao", repository);
                contextByName.put("patientService", service);
                contextByName.put("patientController", controller);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Erreur : beans.properties introuvable !");
        }
    }

    // ================== GETTERS GENERIQUES ==================
    public static Object getBean(String beanName) {
        return contextByName.get(beanName);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return beanClass.cast(context.get(beanClass));
    }

    // ================== SESSION FACTORY ==================
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new SessionFactory();
    }

    //public static SessionFactory getSessionFactory() {
      //  return sessionFactory;
    //}

    // ================== ADMIN BEANS ==================
    //public static AdminRepository getAdminRepository() {
      //  return new AdminRepositoryImpl(getSessionFactory());
   // }

    //public static AdminService getAdminService() {
       // return new AdminServiceImpl(getAdminRepository());
    //}

    //public static AdminAuthController getAdminAuthController() {
    //    return new AdminAuthController(getAdminService());
    //}

    // ================== DOSSIER MEDICAL BEANS ==================
  //  public static DossierMedicalRepository getDossierMedicalRepository() {
    //    return new DossierMedicalRepositoryImpl(getSessionFactory());
   // }

    //public static DossierMedicalService getDossierMedicalService() {
      //  return new DossierMedicalServiceImpl(getDossierMedicalRepository());
    //}

    //public static DossierMedicalController getDossierMedicalController() {
     //   return new DossierMedicalController(getDossierMedicalService());
    //}
}
