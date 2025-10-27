package ma.prodenta.entities;
import java.util.ArrayList;
import java.util.Date;
public class Consultation {
    private int id;
    private Date date_consultation;
    private String statut;
    private String observation_medcin;
    private ArrayList<Intervention_medcin> liste_interventions;
    private Certificat certificat;
    private Rendez_vous rendez_vous;
    private Dossier_medical dossier_medical;
    public Consultation(int id, Date date_consultation, String statut, String observation_medcin, ArrayList<Intervention_medcin> liste_interventions,Certificat certificat,Rendez_vous rendez_vous,Dossier_medical dossier_medical) {
        this.id = id;
        this.dossier_medical = dossier_medical;
        this.rendez_vous=rendez_vous;
        this.date_consultation = date_consultation;
        this.statut = statut;
        this.observation_medcin = observation_medcin;
        this.certificat=certificat;
        this.liste_interventions = liste_interventions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate_consultation(Date date_consultation) {
        this.date_consultation = date_consultation;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setObservation_medcin(String observation_medcin) {
        this.observation_medcin = observation_medcin;
    }

    public void setListe_interventions(ArrayList<Intervention_medcin> liste_interventions) {
        this.liste_interventions = liste_interventions;
    }

    public int getId() {
        return id;
    }

    public Date getDate_consultation() {
        return date_consultation;
    }

    public String getStatut() {
        return statut;
    }

    public String getObservation_medcin() {
        return observation_medcin;
    }

    public ArrayList<Intervention_medcin> getListe_interventions() {
        return liste_interventions;
    }
}
