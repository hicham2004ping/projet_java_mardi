package ma.prodenta.entities;

import java.util.ArrayList;
import java.util.Date;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private Date date_naissance;
    private String cin;
    private String sexe;
    private String adresse;
    private String telephone;
    private String assurance;
    private ArrayList<Antecedent> list_antecedents;
    private Dossier_medical dossier_medical;
    public Patient(int id, String nom, String prenom, Date date_naissance, String cin, String sexe, String adresse, String telephone, String assurance, ArrayList<Antecedent> list_antecedents,Dossier_medical dossier_medical) {
        this.id = id;
        this.dossier_medical = dossier_medical;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.cin = cin;
        this.sexe = sexe;
        this.adresse = adresse;
        this.telephone = telephone;
        this.assurance = assurance;
        this.list_antecedents = list_antecedents;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public String getCin() {
        return cin;
    }

    public String getSexe() {
        return sexe;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAssurance() {
        return assurance;
    }

    public ArrayList<Antecedent> getList_antecedents() {
        return list_antecedents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setAssurance(String assurance) {
        this.assurance = assurance;
    }

    public void setList_antecedents(ArrayList<Antecedent> list_antecedents) {
        this.list_antecedents = list_antecedents;
    }
}
