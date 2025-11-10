package ma.prodenta.entities.En;

import ma.prodenta.config.ApplicationContext;
import ma.prodenta.mvc.controllers.modules.patient.api.PatientController;

public class MainApp
{
    public static void main( String[] args )
    {
        try {
            ApplicationContext.getBean(PatientController.class).showRecentPatients();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
