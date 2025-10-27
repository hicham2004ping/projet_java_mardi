package ma.prodenta.entities;

import java.util.Date;

public class Utilisateur {
    protected int  id;

    public int getId() {
        return id;
    }
    protected String nom;
    protected String prenom;
    protected String adresse;
    protected String telephone;
    protected String email;
    protected String password;
    protected String cin;
    protected String sexe;
    protected Date last_login;
    protected String role;
    public Utilisateur(String nom, String prenom, String adresse, String telephone, String email, String password,String cin, String sexe, Date last_login,int id,String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.sexe=sexe;
        this.last_login=last_login;
        this.cin=cin;
        this.id=id;
        this.role=role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCin() {
        return cin;
    }

    public String getSexe() {
        return sexe;
    }

    public Date getLast_login() {
        return last_login;
    }
    public Utilisateur() {

    }
}
