package ma.prodenta.entities;

public class Intervention_medcin {
    private int id;
    private double prix_patient;
    private int numero_dent;
    private Consultation consultation;
    private Acte acte;
    public int getId() {
        return id;
    }

    public double getPrix_patient() {
        return prix_patient;
    }

    public int getNumero_dent() {
        return numero_dent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrix_patient(double prix_patient) {
        this.prix_patient = prix_patient;
    }

    public void setNumero_dent(int numero_dent) {
        this.numero_dent = numero_dent;
    }

    public Intervention_medcin(int id, double prix_patient, int numero_dent,Consultation consultation ,Acte acte) {
        this.id = id;
        this.prix_patient = prix_patient;
        this.numero_dent = numero_dent;
        this.consultation = consultation;
        this.acte=acte;
    }
}
