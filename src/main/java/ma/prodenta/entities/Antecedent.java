package ma.prodenta.entities;

import java.util.ArrayList;

public class Antecedent {
    private int id;
    private String nom;
    private String categorie;
    private String niveau_de_risque;
    private ArrayList<Patient>liste_patient;
    public Antecedent(int id, String nom, String categorie, String niveau_de_risque,ArrayList<Patient> liste_patient) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.niveau_de_risque = niveau_de_risque;
        this.liste_patient = liste_patient;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setNiveau_de_risque(String niveau_de_risque) {
        this.niveau_de_risque = niveau_de_risque;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getNiveau_de_risque() {
        return niveau_de_risque;
    }
}
