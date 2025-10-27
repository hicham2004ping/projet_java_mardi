package ma.prodenta.entities;

import java.util.ArrayList;

public class Situation_financiere {
    private int id;
    private double total_des_actes;
    private double total_paye;
    private double credit;
    private String statut;
    private String en_promo;
    private ArrayList<Facture> factures;

    public void setId(int id) {
        this.id = id;
    }

    public void setTotal_des_actes(double total_des_actes) {
        this.total_des_actes = total_des_actes;
    }

    public void setTotal_paye(double total_paye) {
        this.total_paye = total_paye;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setEn_promo(String en_promo) {
        this.en_promo = en_promo;
    }

    public Situation_financiere(int id, double total_des_actes, double total_paye, double credit, String statut, String en_promo,ArrayList<Facture> factures) {
        this.id = id;
        this.factures=factures;
        this.total_des_actes = total_des_actes;
        this.total_paye = total_paye;
        this.credit = credit;
        this.statut = statut;
        this.en_promo = en_promo;
    }

    public int getId() {
        return id;
    }

    public double getTotal_des_actes() {
        return total_des_actes;
    }

    public double getTotal_paye() {
        return total_paye;
    }

    public double getCredit() {
        return credit;
    }

    public String getStatut() {
        return statut;
    }

    public String getEn_promo() {
        return en_promo;
    }
}
