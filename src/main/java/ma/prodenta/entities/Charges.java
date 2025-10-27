package ma.prodenta.entities;

import java.util.Date;

public class Charges {
    private int id;
    private String description;
    private double montant;
    private Date date_charge;
    private Cabinet_medical cabinet_medical;

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDate_charge(Date date_charge) {
        this.date_charge = date_charge;
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

    public double getMontant() {
        return montant;
    }

    public Date getDate_charge() {
        return date_charge;
    }

    public Cabinet_medical getCabinet_medical() {
        return cabinet_medical;
    }

    public Charges(int id, String description, double montant, Date date_charge, Cabinet_medical cabinet_medical) {
        this.id = id;
        this.description = description;
        this.montant = montant;
        this.date_charge = date_charge;
        this.cabinet_medical = cabinet_medical;
    }
}
