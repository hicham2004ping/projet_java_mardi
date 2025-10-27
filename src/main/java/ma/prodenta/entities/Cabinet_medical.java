package ma.prodenta.entities;

import java.util.ArrayList;

public class Cabinet_medical  {
    private int id;
    private String nom;
    private String email;
    private String adresse;
    private String cin;
    private String adresse_1;
    private String adresse_2;
    private String siteweb;
    private String description;
    private String instagram;
    private String Facebook;
    private ArrayList<Charges> charges;
    private ArrayList<Revenues>revenues;
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setAdresse_1(String adresse_1) {
        this.adresse_1 = adresse_1;
    }

    public void setAdresse_2(String adresse_2) {
        this.adresse_2 = adresse_2;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public void setCharges(ArrayList<Charges> charges) {
        this.charges = charges;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCin() {
        return cin;
    }

    public String getAdresse_1() {
        return adresse_1;
    }

    public String getAdresse_2() {
        return adresse_2;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public String getDescription() {
        return description;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getFacebook() {
        return Facebook;
    }

    public ArrayList<Charges> getCharges() {
        return charges;
    }

    public Cabinet_medical(int id, String nom, String email, String adresse, String cin, String adresse_1, String adresse_2, String siteweb, String description, String instagram, String facebook, ArrayList<Charges> charges,ArrayList<Revenues> revenues) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.adresse = adresse;
        this.cin = cin;
        this.adresse_1 = adresse_1;
        this.adresse_2 = adresse_2;
        this.siteweb = siteweb;
        this.description = description;
        this.instagram = instagram;
        Facebook = facebook;
        this.charges = charges;
        this.revenues = revenues;
    }
}
