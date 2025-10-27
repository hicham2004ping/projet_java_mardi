package ma.prodenta.entities;

import java.util.ArrayList;
import java.util.Date;

public class Ordonance {
    private int id;
    private Date date_redaction;
    private ArrayList<Medicament> liste_medicaments;
    private Dossier_medical dossier_medical;
    public void setId(int id) {
        this.id = id;
    }

    public void setDate_redaction(Date date_redaction) {
        this.date_redaction = date_redaction;
    }

    public void setListe_medicaments(ArrayList<Medicament> liste_medicaments) {
        this.liste_medicaments = liste_medicaments;
    }

    public int getId() {
        return id;
    }

    public Date getDate_redaction() {
        return date_redaction;
    }

    public ArrayList<Medicament> getListe_medicaments() {
        return liste_medicaments;
    }

    public Ordonance(int id, Date date_redaction, ArrayList<Medicament> liste_medicaments,Dossier_medical dossier_medical) {
        this.id = id;
        this.date_redaction = date_redaction;
        this.liste_medicaments = liste_medicaments;
        this.dossier_medical = dossier_medical;
    }
}
