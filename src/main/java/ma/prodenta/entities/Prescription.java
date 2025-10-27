package ma.prodenta.entities;

import java.util.ArrayList;

public class Prescription {
    private int id;
    private int qtr;
    private String frequence;
    private int durre_en_jours;
    private ArrayList<Medicament> liste_medicaments;
    private Ordonance ordonance;
    public void setId(int id) {
        this.id = id;
    }

    public void setQtr(int qtr) {
        this.qtr = qtr;
    }

    public void setFrequence(String frequence) {
        this.frequence = frequence;
    }

    public void setDurre_en_jours(int durre_en_jours) {
        this.durre_en_jours = durre_en_jours;
    }

    public int getId() {
        return id;
    }

    public int getQtr() {
        return qtr;
    }

    public String getFrequence() {
        return frequence;
    }

    public int getDurre_en_jours() {
        return durre_en_jours;
    }

    public ArrayList<Medicament> getListe_medicaments() {
        return liste_medicaments;
    }

    public Prescription(int id, int qtr, String frequence, int durre_en_jours, ArrayList<Medicament> liste_medicaments,Ordonance ordonance) {
        this.id = id;
        this.qtr = qtr;
        this.frequence = frequence;
        this.durre_en_jours = durre_en_jours;
        this.liste_medicaments = liste_medicaments;
        this.ordonance = ordonance;
    }
}
