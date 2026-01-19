package ma.prodenta.mvc.ui.fileattente;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.FileAttente;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.mvc.ui.dossier.DossierMedicalView;
import ma.prodenta.service.modules.FileAttenteService;
import ma.prodenta.common.exceptions.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ConsultationMedecinFrame extends JPanel {

    private Dashboard_view dashboard;
    private FileAttenteFrame fileAttenteFrame;
    private FileAttenteService fileAttenteService;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnProchainPatient, btnActualiser;
    private JLabel lblAucunPatient;

    public ConsultationMedecinFrame(Dashboard_view dashboard, FileAttenteFrame fileAttenteFrame) {
        this.dashboard = dashboard;
        this.fileAttenteFrame = fileAttenteFrame;
        this.fileAttenteService = Application_contexte.getFileAttenteService();

        initializeUI();
        startAutoRefresh();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel supérieur
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnProchainPatient = new JButton("👤 Prendre le prochain patient");
        btnProchainPatient.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnProchainPatient.setPreferredSize(new Dimension(250, 40));
        btnProchainPatient.setBackground(new Color(76, 175, 80));
        btnProchainPatient.setForeground(Color.WHITE);

        btnActualiser = new JButton("🔄 Actualiser");
        btnActualiser.setPreferredSize(new Dimension(150, 40));

        topPanel.add(btnProchainPatient);
        topPanel.add(btnActualiser);

        // Tableau des patients en attente
        String[] colonnes = {"ID", "#", "Nom", "Prénom", "Heure arrivée", "Statut"};
        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Double-clic pour ouvrir le dossier
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    ouvrirDossierPatient();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Patients en attente"));
        scrollPane.setPreferredSize(new Dimension(900, 350));

        // Masquer la colonne ID
        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setResizable(false);

        // Message si aucun patient
        lblAucunPatient = new JLabel("Aucun patient en attente", SwingConstants.CENTER);
        lblAucunPatient.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblAucunPatient.setForeground(Color.GRAY);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(lblAucunPatient, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Actions
        btnProchainPatient.addActionListener(e -> prendreProchainPatient());
        btnActualiser.addActionListener(e -> actualiser());

        actualiser();
    }

    private void prendreProchainPatient() {
        try {
            FileAttente prochain = fileAttenteService.getFirstPatientInWaiting(LocalDate.now());

            if (prochain == null) {
                JOptionPane.showMessageDialog(this,
                        "Aucun patient en attente",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Marquer comme "En consultation"
            fileAttenteService.markAsInConsultation(prochain.getIdFileAttente());
            fileAttenteFrame.refresh();

            // Ouvrir le dossier médical
            ouvrirDossierPatient(prochain.getIdDossier());
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ouvrirDossierPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer idFileAttente = (Integer) model.getValueAt(selectedRow, 0);
            try {
                FileAttente fileAttente = fileAttenteService.findById(idFileAttente);
                if (fileAttente != null) {
                    ouvrirDossierPatient(fileAttente.getIdDossier());
                }
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur: " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un patient",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void ouvrirDossierPatient(Integer idDossier) {
        try {
            // Utiliser la méthode du dashboard pour afficher le dossier
            dashboard.afficherDetailsDossier(idDossier);
            actualiser();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'ouverture du dossier: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void actualiser() {
        try {
            model.setRowCount(0);

            if (fileAttenteService == null) {
                lblAucunPatient.setVisible(true);
                table.setVisible(false);
                return;
            }

            // Récupérer tous les patients de la file d'attente pour aujourd'hui
            java.util.List<FileAttente> patients = fileAttenteService.getQueueByDate(LocalDate.now());

            if (patients.isEmpty()) {
                lblAucunPatient.setVisible(true);
                table.setVisible(false);
            } else {
                lblAucunPatient.setVisible(false);
                table.setVisible(true);

                int position = 1;
                java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("HH:mm:ss");

                for (FileAttente item : patients) {
                    model.addRow(new Object[]{
                            item.getIdFileAttente(),
                            position++,
                            getDossierNom(item.getIdDossier()),
                            getDossierPrenom(item.getIdDossier()),
                            df.format(java.sql.Timestamp.valueOf(item.getDateArrivee())),
                            item.getStatut()
                    });
                }
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
            var dossierController = Application_contexte.getDossierMedicalController();
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
            var dossierController = Application_contexte.getDossierMedicalController();
            var dossier = dossierController.find_view(idDossier);
            return dossier != null ? dossier.getPatient_prenom() : "?";
        } catch (Exception e) {
            return "?";
        }
    }

    /**
     * Démarre l'actualisation automatique toutes les 5 secondes
     */
    private void startAutoRefresh() {
        Timer timer = new Timer(5000, e -> actualiser());
        timer.start();
    }

    public void terminerConsultation(Integer idFileAttente) {
        try {
            fileAttenteService.removePatientFromQueue(idFileAttente);
            fileAttenteFrame.refresh();
            actualiser();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

