package ma.prodenta.entities;

import java.util.Date;

public class Facture {
    private int id;
    private double total_paye;
    private double total_facture;
    private double reste ;
    private String statut;
    private Date date_facture;
    private Situation_financiere situation;
    public Facture(int id, double total_paye, double total_facture, double reste, String statut, Date date_facture,Situation_financiere situation) {
        this.id = id;
        this.total_paye = total_paye;
        this.total_facture = total_facture;
        this.reste = reste;
        this.statut = statut;
        this.situation = situation;
        this.date_facture = date_facture;
    }

    public int getId() {
        return id;
    }

    public double getTotal_paye() {
        return total_paye;
    }

    public double getTotal_facture() {
        return total_facture;
    }

    public double getReste() {
        return reste;
    }

    public String getStatut() {
        return statut;
    }

    public Date getDate_facture() {
        return date_facture;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal_paye(double total_paye) {
        this.total_paye = total_paye;
    }

    public void setTotal_facture(double total_facture) {
        this.total_facture = total_facture;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setDate_facture(Date date_facture) {
        this.date_facture = date_facture;
    }
}
