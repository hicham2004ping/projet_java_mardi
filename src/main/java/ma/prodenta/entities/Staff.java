package ma.prodenta.entities;

import java.util.Date;

public class Staff extends Utilisateur{
    protected int staff_id;
    protected double salaire;
    protected double prime;
    protected Date date_recutrement;
    protected int solde_conge;

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public void setPrime(double prime) {
        this.prime = prime;
    }

    public void setDate_recutrement(Date date_recutrement) {
        this.date_recutrement = date_recutrement;
    }

    public void setSolde_conge(int solde_conge) {
        this.solde_conge = solde_conge;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public double getSalaire() {
        return salaire;
    }

    public double getPrime() {
        return prime;
    }

    public Date getDate_recutrement() {
        return date_recutrement;
    }

    public int getSolde_conge() {
        return solde_conge;
    }

    public Staff(String nom, String prenom, String adresse, String telephone, String email, String password, String cin, String sexe, Date last_login, int id, String role, double salaire, double prime, int solde_conge, Date date_recutrement) {
        super(nom,prenom,adresse,telephone,email,password,cin,sexe,last_login,id,role);
        this.salaire=salaire;
        this.prime=prime;
        this.date_recutrement=date_recutrement;
        this.solde_conge=solde_conge;
    }
    public Staff() {}
}
