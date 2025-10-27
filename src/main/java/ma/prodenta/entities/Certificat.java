package ma.prodenta.entities;

import java.util.Date;

public class Certificat {
    private int id;
    private Date date_debut;
    private Date Date_fin;
    private int dure;
    private String note_medcin;
    private Consultation consultation;
    private Dossier_medical dossier_medical;
    public void setId(int id) {
        this.id = id;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(Date date_fin) {
        Date_fin = date_fin;
    }

    public void setDure(int dure) {
        this.dure = dure;
    }

    public void setNote_medcin(String note_medcin) {
        this.note_medcin = note_medcin;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public int getId() {
        return id;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public Date getDate_fin() {
        return Date_fin;
    }

    public int getDure() {
        return dure;
    }

    public String getNote_medcin() {
        return note_medcin;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public Certificat(int id, Date date_debut, Date date_fin, int dure, String note_medcin, Consultation consultation,Dossier_medical dossier_medical) {
        this.id = id;
        this.date_debut = date_debut;
        Date_fin = date_fin;
        this.dure = dure;
        this.dossier_medical=dossier_medical;
        this.note_medcin = note_medcin;
        this.consultation = consultation;
    }
}
