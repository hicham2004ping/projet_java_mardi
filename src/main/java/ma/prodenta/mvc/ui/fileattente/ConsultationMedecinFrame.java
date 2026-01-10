package ma.prodenta.mvc.ui.fileattente;

import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.mvc.ui.dossier.DossierMedicalView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsultationMedecinFrame extends JPanel {

    private Dashboard_view dashboard;
    private FileAttenteFrame fileAttenteFrame;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnProchainPatient, btnActualiser;
    private JLabel lblAucunPatient;

    public ConsultationMedecinFrame(Dashboard_view dashboard, FileAttenteFrame fileAttenteFrame) {
        this.dashboard = dashboard;
        this.fileAttenteFrame = fileAttenteFrame;

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
        FileAttenteFrame.FileAttenteItem prochain = fileAttenteFrame.getProchainPatient();

        if (prochain == null) {
            JOptionPane.showMessageDialog(this,
                    "Aucun patient en attente",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Marquer comme "En consultation"
        fileAttenteFrame.marquerEnConsultation(prochain.getIdDossier());

        // Ouvrir le dossier médical
        ouvrirDossierPatient(prochain.getIdDossier());
    }

    private void ouvrirDossierPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer idDossier = (Integer) model.getValueAt(selectedRow, 0);
            ouvrirDossierPatient(idDossier);
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

            // Marquer comme "En consultation" si pas déjà fait
            fileAttenteFrame.marquerEnConsultation(idDossier);
            actualiser();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'ouverture du dossier: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void actualiser() {
        model.setRowCount(0);

        if (fileAttenteFrame == null) {
            lblAucunPatient.setVisible(true);
            table.setVisible(false);
            return;
        }

        // Récupérer tous les patients de la file d'attente
        java.util.List<FileAttenteFrame.FileAttenteItem> patients =
                new java.util.ArrayList<>(fileAttenteFrame.getFileAttente().values());

        if (patients.isEmpty()) {
            lblAucunPatient.setVisible(true);
            table.setVisible(false);
        } else {
            lblAucunPatient.setVisible(false);
            table.setVisible(true);

            int position = 1;
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("HH:mm:ss");

            for (FileAttenteFrame.FileAttenteItem item : patients) {
                model.addRow(new Object[]{
                        item.getIdDossier(), // Stocker l'ID dans la première colonne (cachée)
                        position++,
                        item.getNom(),
                        item.getPrenom(),
                        df.format(item.getDateArrivee()),
                        item.getStatut()
                });
            }
        }
    }

    /**
     * Démarre l'actualisation automatique toutes les 5 secondes
     */
    private void startAutoRefresh() {
        Timer timer = new Timer(5000, e -> actualiser());
        timer.start();
    }


    public void terminerConsultation(Integer idDossier) {
        fileAttenteFrame.terminerConsultation(idDossier);
        actualiser();
    }
}

