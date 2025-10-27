package ma.prodenta.entities;

import java.util.Date;

public class Revenues {
    private int id;
    private String description;
    private String titre;
    private double montant;
    private Date date;
    private Cabinet_medical cabinet_medical;

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCabinet_medical(Cabinet_medical cabinet_medical) {
        this.cabinet_medical = cabinet_medical;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitre() {
        return titre;
    }

    public double getMontant() {
        return montant;
    }

    public Date getDate() {
        return date;
    }

    public Cabinet_medical getCabinet_medical() {
        return cabinet_medical;
    }

    public Revenues(int id, String description, String titre, double montant, Date date, Cabinet_medical cabinet_medical) {
        this.id = id;
        this.description = description;
        this.titre = titre;
        this.montant = montant;
        this.date = date;
        this.cabinet_medical = cabinet_medical;
    }
}
