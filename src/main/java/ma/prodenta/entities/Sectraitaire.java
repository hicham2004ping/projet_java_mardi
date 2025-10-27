package ma.prodenta.entities;

import java.util.Date;
public class Sectraitaire extends Staff{
    private String num_cnss;
    private double commission;
    public Sectraitaire(String nom, String prenom, String adresse, String telephone, String email, String password, String cin, String sexe, Date last_login, int id, String role, double salaire, double prime, int solde_conge, Date date_recutrement) {
        super(nom, prenom, adresse, telephone, email, password, cin, sexe, last_login, id, role, salaire, prime, solde_conge, date_recutrement);
        this.num_cnss = num_cnss;
        this.commission = commission;
    }
}
