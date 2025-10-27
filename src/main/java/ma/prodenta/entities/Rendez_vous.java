package ma.prodenta.entities;
import java.util.Date;

public class Rendez_vous {
    private int id;
    private Date date_debut;
    private String motif;
    private String statut;
    private String note_medcin;
    private Consultation consultation;
    private Dossier_medical dossier_medical;
    public void setId(int id) {
        this.id = id;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setNote_medcin(String note_medcin) {
        this.note_medcin = note_medcin;
    }

    public int getId() {
        return id;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public String getMotif() {
        return motif;
    }

    public String getStatut() {
        return statut;
    }

    public String getNote_medcin() {
        return note_medcin;
    }

    public Rendez_vous(int id, Date date_debut, String motif, String statut, String note_medcin,Consultation consultation,Dossier_medical dossier_medical) {
        this.id = id;
        this.date_debut = date_debut;
        this.motif = motif;
        this.dossier_medical = dossier_medical;
        this.statut = statut;
        this.note_medcin = note_medcin;
        this.consultation = consultation;
    }
}
