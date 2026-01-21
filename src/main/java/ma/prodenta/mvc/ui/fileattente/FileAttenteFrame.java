package ma.prodenta.mvc.ui.fileattente;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.*;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.service.modules.filedattente.FileAttenteService;
import ma.prodenta.common.exceptions.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;


public class FileAttenteFrame extends JPanel {

    private Dashboard_view dashboard;
    private DossierMedicalController dossierController;
    private FileAttenteService fileAttenteService;
    private DefaultTableModel model;
    private JTable table;
    private LocalDate currentDate; // Groupe par date

    /**
     * Getter pour accéder au service de file d'attente
     */
    public FileAttenteService getFileAttenteService() {
        return fileAttenteService;
    }

    public FileAttenteFrame(Dashboard_view dashboard) {
        this.dashboard = dashboard;
        this.dossierController = Application_contexte.getDossierMedicalController();
        this.fileAttenteService = Application_contexte.getFileAttenteService();
        this.currentDate = LocalDate.now(); // Initialiser avec la date du jour

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
        scrollPane.setBorder(BorderFactory.createTitledBorder("File d'attente - " + currentDate));
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
                        // Ajouter à la base de données pour la date du jour
                        fileAttenteService.addPatientToQueue(idDossier, currentDate);
                        actualiser();
                        dialog.dispose();

                        JOptionPane.showMessageDialog(this,
                                "Patient ajouté à la file d'attente",
                                "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ServiceException ex) {
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
            try {
                Integer idFileAttente = (Integer) model.getValueAt(selectedRow, 0);
                fileAttenteService.removePatientFromQueue(idFileAttente);
                actualiser();
                JOptionPane.showMessageDialog(this,
                        "Patient retiré de la file",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur: " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un patient à retirer",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void actualiser() {
        try {
            model.setRowCount(0);
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

            // Charger la file du jour depuis la base de données
            List<FileAttente> fileAttenteList = fileAttenteService.getQueueByDate(currentDate);

            int position = 1;
            for (FileAttente fileAttente : fileAttenteList) {
                model.addRow(new Object[]{
                        fileAttente.getIdFileAttente(),
                        position++,
                        getDossierNom(fileAttente.getIdDossier()),
                        getDossierPrenom(fileAttente.getIdDossier()),
                        df.format(java.sql.Timestamp.valueOf(fileAttente.getDateArrivee())),
                        fileAttente.getStatut(),
                        "Actions"
                });
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'actualisation: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Récupère le nom du patient par idDossier
     */
    private String getDossierNom(Integer idDossier) {
        try {
            var dossier = dossierController.find_view(idDossier);
            return dossier != null ? dossier.getPatient_nom() : "?";
        } catch (Exception e) {
            return "?";
        }
    }

    /**
     * Récupère le prénom du patient par idDossier
     */
    private String getDossierPrenom(Integer idDossier) {
        try {
            var dossier = dossierController.find_view(idDossier);
            return dossier != null ? dossier.getPatient_prenom() : "?";
        } catch (Exception e) {
            return "?";
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
    public FileAttente getProchainPatient() {
        try {
            return fileAttenteService.getFirstPatientInWaiting(currentDate);
        } catch (ServiceException e) {
            return null;
        }
    }

    /**
     * Marque un patient comme "En consultation"
     */
    public void marquerEnConsultation(Integer idFileAttente) {
        try {
            fileAttenteService.markAsInConsultation(idFileAttente);
            actualiser();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Marque un patient comme "Terminé" et le retire de la file
     */
    public void terminerConsultation(Integer idFileAttente) {
        try {
            fileAttenteService.removePatientFromQueue(idFileAttente);
            actualiser();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Définit la date de la file d'attente (pour changer de jour)
     */
    public void setCurrentDate(LocalDate date) {
        this.currentDate = date;
        actualiser();
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }


}

