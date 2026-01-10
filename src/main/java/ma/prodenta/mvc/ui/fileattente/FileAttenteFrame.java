package ma.prodenta.mvc.ui.fileattente;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.*;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.mvc.ui.dossier.DossierMedicalView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Interface pour la secrétaire - Gérer la file d'attente
 */
public class FileAttenteFrame extends JPanel {

    private Dashboard_view dashboard;
    private DossierMedicalController dossierController;
    private DefaultTableModel model;
    private JTable table;
    private Map<Integer, FileAttenteItem> fileAttente; // En mémoire pour l'instant

    /**
     * Getter pour accéder à la file d'attente depuis l'extérieur
     */
    public Map<Integer, FileAttenteItem> getFileAttente() {
        return fileAttente;
    }

    public FileAttenteFrame(Dashboard_view dashboard) {
        this.dashboard = dashboard;
        this.dossierController = Application_contexte.getDossierMedicalController();
        this.fileAttente = new LinkedHashMap<>(); // Maintient l'ordre d'insertion

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        // Panel supérieur avec boutons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton btnAjouter = new JButton("➕ Ajouter Patient");
        JButton btnRetirer = new JButton("➖ Retirer de la file");
        JButton btnActualiser = new JButton("🔄 Actualiser");

        topPanel.add(btnAjouter);
        topPanel.add(btnRetirer);
        topPanel.add(btnActualiser);

        // Tableau de la file d'attente
        String[] colonnes = {"ID", "#", "Nom Patient", "Prénom", "Heure arrivée", "Statut", "Actions"};
        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("File d'attente"));
        scrollPane.setPreferredSize(new Dimension(900, 400));

        // Masquer la colonne ID
        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setResizable(false);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        btnAjouter.addActionListener(e -> ajouterPatient());
        btnRetirer.addActionListener(e -> retirerPatient());
        btnActualiser.addActionListener(e -> actualiser());

        actualiser();
    }

    private void ajouterPatient() {
        try {
            // Dialogue pour sélectionner un patient
            var dossiers = dossierController.find_all_view();

            if (dossiers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun dossier médical disponible",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Créer un dialogue de sélection
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ajouter à la file", true);
            dialog.setSize(500, 400);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            // Liste des dossiers
            String[] colonnes = {"ID", "Nom", "Prénom", "Date création"};
            DefaultTableModel dossierModel = new DefaultTableModel(colonnes, 0);
            JTable dossierTable = new JTable(dossierModel);
            dossierTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            for (var dossier : dossiers) {
                dossierModel.addRow(new Object[]{
                        dossier.getIdDossier(),
                        dossier.getPatient_nom(),
                        dossier.getPatient_prenom(),
                        dossier.getDate_creation()
                });
            }

            JScrollPane scroll = new JScrollPane(dossierTable);
            panel.add(scroll, BorderLayout.CENTER);

            JButton btnValider = new JButton("Ajouter à la file");
            JButton btnAnnuler = new JButton("Annuler");
            JPanel btnPanel = new JPanel(new FlowLayout());
            btnPanel.add(btnValider);
            btnPanel.add(btnAnnuler);
            panel.add(btnPanel, BorderLayout.SOUTH);

            dialog.add(panel);

            btnValider.addActionListener(e -> {
                int selectedRow = dossierTable.getSelectedRow();
                if (selectedRow != -1) {
                    Integer idDossier = (Integer) dossierModel.getValueAt(selectedRow, 0);
                    try {
                        Patient patient = dossierController.find_patient(idDossier);

                        // Vérifier si déjà dans la file
                        if (fileAttente.containsKey(idDossier)) {
                            JOptionPane.showMessageDialog(dialog,
                                    "Ce patient est déjà dans la file d'attente",
                                    "Information", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        // Ajouter à la file
                        FileAttenteItem item = new FileAttenteItem();
                        item.setIdDossier(idDossier);
                        item.setIdPatient(patient.getId());
                        item.setNom(patient.getNom());
                        item.setPrenom(patient.getPrenom());
                        item.setDateArrivee(new Date());
                        item.setStatut("En attente");

                        fileAttente.put(idDossier, item);
                        actualiser();
                        dialog.dispose();

                        JOptionPane.showMessageDialog(this,
                                "Patient ajouté à la file d'attente",
                                "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog,
                                "Erreur: " + ex.getMessage(),
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Veuillez sélectionner un patient",
                            "Aucune sélection", JOptionPane.WARNING_MESSAGE);
                }
            });

            btnAnnuler.addActionListener(e -> dialog.dispose());

            dialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retirerPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer idDossier = (Integer) model.getValueAt(selectedRow, 0);
            fileAttente.remove(idDossier);
            actualiser();
            JOptionPane.showMessageDialog(this,
                    "Patient retiré de la file",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un patient à retirer",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualiser() {
        model.setRowCount(0);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        int position = 1;
        for (FileAttenteItem item : fileAttente.values()) {
            model.addRow(new Object[]{
                    item.getIdDossier(), // ID caché
                    position++,
                    item.getNom(),
                    item.getPrenom(),
                    df.format(item.getDateArrivee()),
                    item.getStatut(),
                    "Actions"
            });
        }
    }

    /**
     * Actualise l'affichage (méthode publique pour être appelée depuis l'extérieur)
     */
    public void refresh() {
        actualiser();
    }

    /**
     * Récupère le prochain patient en attente
     */
    public FileAttenteItem getProchainPatient() {
        for (FileAttenteItem item : fileAttente.values()) {
            if ("En attente".equals(item.getStatut())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Marque un patient comme "En consultation"
     */
    public void marquerEnConsultation(Integer idDossier) {
        FileAttenteItem item = fileAttente.get(idDossier);
        if (item != null) {
            item.setStatut("En consultation");
            actualiser();
        }
    }

    /**
     * Marque un patient comme "Terminé" et le retire de la file
     */
    public void terminerConsultation(Integer idDossier) {
        fileAttente.remove(idDossier);
        actualiser();
    }

    /**
     * Classe interne pour représenter un item de la file d'attente
     */
    public static class FileAttenteItem {
        private Integer idDossier;
        private Integer idPatient;
        private String nom;
        private String prenom;
        private Date dateArrivee;
        private String statut;

        // Getters et setters
        public Integer getIdDossier() { return idDossier; }
        public void setIdDossier(Integer idDossier) { this.idDossier = idDossier; }

        public Integer getIdPatient() { return idPatient; }
        public void setIdPatient(Integer idPatient) { this.idPatient = idPatient; }

        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }

        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }

        public Date getDateArrivee() { return dateArrivee; }
        public void setDateArrivee(Date dateArrivee) { this.dateArrivee = dateArrivee; }

        public String getStatut() { return statut; }
        public void setStatut(String statut) { this.statut = statut; }
    }
}

