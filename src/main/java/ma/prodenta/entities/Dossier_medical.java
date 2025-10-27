package ma.prodenta.entities;

import java.util.ArrayList;

public class Dossier_medical {
    private String id_medcin;
    private String nom_medcin;
    private ArrayList<Rendez_vous>liste_rendez_vous;
    private ArrayList<Consultation>liste_consultations;
    private Patient patient;
    private ArrayList<Ordonance>liste_ordonances;
    private ArrayList<Certificat>liste_certificats;
    private Situation_financiere situation_financiere;
    private Medcin medcin;

    public void setId_medcin(String id_medcin) {
        this.id_medcin = id_medcin;
    }

    public void setNom_medcin(String nom_medcin) {
        this.nom_medcin = nom_medcin;
    }

    public void setListe_rendez_vous(ArrayList<Rendez_vous> liste_rendez_vous) {
        this.liste_rendez_vous = liste_rendez_vous;
    }

    public void setListe_consultations(ArrayList<Consultation> liste_consultations) {
        this.liste_consultations = liste_consultations;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setListe_ordonances(ArrayList<Ordonance> liste_ordonances) {
        this.liste_ordonances = liste_ordonances;
    }

    public void setListe_certificats(ArrayList<Certificat> liste_certificats) {
        this.liste_certificats = liste_certificats;
    }

    public void setSituation_financiere(Situation_financiere situation_financiere) {
        this.situation_financiere = situation_financiere;
    }

    public void setMedcin(Medcin medcin) {
        this.medcin = medcin;
    }

    public String getId_medcin() {
        return id_medcin;
    }

    public String getNom_medcin() {
        return nom_medcin;
    }

    public ArrayList<Rendez_vous> getListe_rendez_vous() {
        return liste_rendez_vous;
    }

    public ArrayList<Consultation> getListe_consultations() {
        return liste_consultations;
    }

    public Patient getPatient() {
        return patient;
    }

    public ArrayList<Ordonance> getListe_ordonances() {
        return liste_ordonances;
    }

    public ArrayList<Certificat> getListe_certificats() {
        return liste_certificats;
    }

    public Situation_financiere getSituation_financiere() {
        return situation_financiere;
    }

    public Medcin getMedcin() {
        return medcin;
    }

    public Dossier_medical(String id_medcin, String nom_medcin, ArrayList<Rendez_vous> liste_rendez_vous, ArrayList<Consultation> liste_consultations, Patient patient, ArrayList<Ordonance> liste_ordonances, ArrayList<Certificat> liste_certificats, Situation_financiere situation_financiere, Medcin medcin) {
        this.id_medcin = id_medcin;
        this.nom_medcin = nom_medcin;
        this.liste_rendez_vous = liste_rendez_vous;
        this.liste_consultations = liste_consultations;
        this.patient = patient;
        this.liste_ordonances = liste_ordonances;
        this.liste_certificats = liste_certificats;
        this.situation_financiere = situation_financiere;
        this.medcin = medcin;
    }
}
