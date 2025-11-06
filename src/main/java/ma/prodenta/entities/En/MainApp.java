package ma.prodenta.entities.En;


import ma.prodenta.config.ApplicationContext;
import ma.prodenta.mvc.controllers.modules.patient.api.PatientController;

public class MainApp
{
    public static void main( String[] args )
    {
        ApplicationContext.getBean(PatientController.class).showRecentPatients();
    }
}
