package ma.prodenta.entities;

import java.util.Date;

public class Medcin extends Staff{
    private String specialite;

    public String getSpecialite()
    {
        return specialite;
    }

    public Medcin(String nom, String prenom, String adresse, String telephone, String email, String password, String cin, String sexe, Date last_login, int id, String role, double salaire, double prime, int solde_conge, Date date_recutrement, String specialite) {
        super(nom, prenom, adresse, telephone, email, password, cin, sexe, last_login, id, role, salaire, prime, solde_conge, date_recutrement);
        this.specialite = specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
