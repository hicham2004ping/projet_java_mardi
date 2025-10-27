package ma.prodenta.entities;

import java.util.ArrayList;

public class Acte {
    private int id;
    private String libelle;
    private String categorie;
    private double prix_de_base;
    private ArrayList<Intervention_medcin> liste_actes;
    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setPrix_de_base(double prix_de_base) {
        this.prix_de_base = prix_de_base;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getCategorie() {
        return categorie;
    }

    public double getPrix_de_base() {
        return prix_de_base;
    }

    public Acte(int id, String libelle, String categorie, double prix_de_base,ArrayList<Intervention_medcin> list) {
        this.id = id;
        this.libelle = libelle;
        this.categorie = categorie;
        this.prix_de_base = prix_de_base;
        this.liste_actes = list;
    }
}
