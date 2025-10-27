package ma.prodenta.entities;

public class Medicament {
    private int id;
    private String nom;
    private String laboratoire;
    private String type;
    private String forme;
    private boolean remboursable;
    private double prix_unitaire;
    private String description;
    private Prescription prescription;

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLaboratoire(String laboratoire) {
        this.laboratoire = laboratoire;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setForme(String forme) {
        this.forme = forme;
    }

    public void setRemboursable(boolean remboursable) {
        this.remboursable = remboursable;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getLaboratoire() {
        return laboratoire;
    }

    public String getType() {
        return type;
    }

    public String getForme() {
        return forme;
    }

    public boolean isRemboursable() {
        return remboursable;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public String getDescription() {
        return description;
    }

    public Medicament(int id, String nom, String laboratoire, String type, String forme, boolean remboursable, double prix_unitaire, String description,Prescription prescription) {
        this.id = id;
        this.nom = nom;
        this.laboratoire = laboratoire;
        this.type = type;
        this.forme = forme;
        this.remboursable = remboursable;
        this.prix_unitaire = prix_unitaire;
        this.description = description;
        this.prescription = prescription;
    }
}
