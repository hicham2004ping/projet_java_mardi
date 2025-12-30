package ma.prodenta.mvc.ui.dossier;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.DossierMedical;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.service.modules.dossierMedical.impl.DossierMedicalServiceImpl;

import javax.swing.*;

public class Detailles_DossierMedical extends JPanel {
    private Dashboard_view view;
    int idDossier;
    DossierMedicalController controlleur;
    public Detailles_DossierMedical(int idDossier) {
            controlleur = Application_contexte.getDossierMedicalController();
            DossierMedical dossierMedical=new DossierMedical();
            Patient patient=new Patient();
            try{
                patient=controlleur.find_patient(idDossier);
                dossierMedical=controlleur.find_by_id(idDossier);
                int total_consultations=controlleur.nombre_consultations(patient);
                int total_ordonnances=controlleur.total_ordonnance_patient(patient);
                JLabel id_dossier_label=new JLabel("ID dossier :"+idDossier);
                JLabel id_patient_label=new JLabel("ID patient :"+dossierMedical.getIdPatient());
                JLabel date_creation_label=new JLabel("Date Creation:"+dossierMedical.getDateCreation());
                JLabel nomPatientLabel=new JLabel("Nom Patient :"+patient.getNom());
                JLabel prenomPatientLabel=new JLabel("Prenom Patient :"+patient.getPrenom());
                JLabel emailPatientLabel=new JLabel("Email Patient :"+patient.getEmail());
                JLabel adressePatientLabel=new JLabel("Adresse Patient:"+patient.getAdresse());
                JLabel totalConsultationsPatient=new JLabel("Total Consultations:"+total_consultations);
                JLabel totalOrdonnancePatient=new JLabel("Total Ordonnances:"+total_ordonnances);
                add(id_dossier_label);
                add(id_patient_label);
                add(date_creation_label);
                add(nomPatientLabel);
                add(prenomPatientLabel);
                add(emailPatientLabel);
                add(adressePatientLabel);
                add(totalConsultationsPatient);
                add(totalOrdonnancePatient);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(this,"impossible de lire les detailles de ce dossier","erreur de lectrue",JOptionPane.ERROR_MESSAGE);
            }
        }
}
