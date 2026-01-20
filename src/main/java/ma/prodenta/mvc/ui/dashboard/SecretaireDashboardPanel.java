package ma.prodenta.mvc.ui.dashboard;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.FileAttente;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.ui.fileattente.FileAttenteFrame;
import ma.prodenta.service.modules.FileAttenteService;
import ma.prodenta.common.exceptions.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Dashboard professionnel et premium pour les secrétaires
 * Affiche les statistiques complètes, la file d'attente et les activités du jour
 */
public class SecretaireDashboardPanel extends JPanel {

    private Dashboard_view dashboard;
    private FileAttenteFrame fileAttenteFrame;
    private FileAttenteService fileAttenteService;
    private DossierMedicalController dossierController;
    
    private JTable fileAttenteTable;
    private JTable statistiquesTable;
    private DefaultTableModel fileAttenteModel;
    private DefaultTableModel statistiquesModel;
    private Timer autoRefreshTimer;
    private JLabel lblTotalRDV;
    private JLabel lblTotalPatients;
    private JLabel lblRecetteJour;

    public SecretaireDashboardPanel(Dashboard_view dashboard, FileAttenteFrame fileAttenteFrame) {
        this.dashboard = dashboard;
        this.fileAttenteFrame = fileAttenteFrame;
        this.fileAttenteService = Application_contexte.getFileAttenteService();
        this.dossierController = Application_contexte.getDossierMedicalController();
        
        initializeUI();
        startAutoRefresh();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel supérieur avec titre, date et 3 stat cards
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Panel central: 2x2 grid avec tables et sections
        add(createMainContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        mainPanel.setBackground(new Color(245, 248, 250));
        mainPanel.setOpaque(false);

        // Haut gauche: File d'attente
        mainPanel.add(createFileAttenteTablePanel());

        // Haut droite: Statistiques complètes
        mainPanel.add(createStatistiquesPanel());

        // Bas gauche: Activités récentes
        mainPanel.add(createActivitesPanel());

        // Bas droite: À faire
        mainPanel.add(createRappelsPanel());

        return mainPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 10));
        headerPanel.setBackground(new Color(245, 248, 250));

        // Titre
        JLabel titleLabel = new JLabel("Bienvenue secrétaire");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(40, 120, 160));

        // Panel de date
        LocalDate today = LocalDate.now();
        JLabel dateLabel = new JLabel(today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(120, 120, 120));

        JPanel titleDatePanel = new JPanel(new BorderLayout());
        titleDatePanel.setBackground(new Color(245, 248, 250));
        titleDatePanel.add(titleLabel, BorderLayout.NORTH);
        titleDatePanel.add(dateLabel, BorderLayout.SOUTH);

        headerPanel.add(titleDatePanel, BorderLayout.WEST);

        // Panel des statistiques
        JPanel statsPanel = createStatsPanel();
        headerPanel.add(statsPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBackground(new Color(245, 248, 250));
        statsPanel.setOpaque(false);

        // Charger les statistiques réelles
        loadStatistics();

        // Stat 1: Total RDV
        JPanel statRDV = createStatCard(
            "Total rendez-vous",
            "812",
            new Color(100, 180, 220)
        );
        lblTotalRDV = (JLabel) ((JPanel) statRDV).getComponent(1);
        statsPanel.add(statRDV);

        // Stat 2: Total Patients
        JPanel statPatients = createStatCard(
            "Total patients",
            "1658",
            new Color(200, 220, 100)
        );
        lblTotalPatients = (JLabel) ((JPanel) statPatients).getComponent(1);
        statsPanel.add(statPatients);

        // Stat 3: Recette du jour
        JPanel statRecette = createStatCard(
            "Recette du jour",
            "4500dh",
            new Color(100, 200, 100)
        );
        lblRecetteJour = (JLabel) ((JPanel) statRecette).getComponent(1);
        statsPanel.add(statRecette);

        return statsPanel;
    }

    private void loadStatistics() {
        // Ces valeurs peuvent être chargées depuis le service
        // Pour maintenant, on utilise des valeurs fictives qui peuvent être mises à jour
        SwingUtilities.invokeLater(() -> {
            try {
                // TODO: Charger les vraies statistiques depuis le service
                // lblTotalRDV.setText(String.valueOf(rdvService.getTotalRDV()));
                // lblTotalPatients.setText(String.valueOf(patientService.getTotalPatients()));
                // lblRecetteJour.setText(String.valueOf(factureService.getRecetteJour()) + "dh");
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement des statistiques: " + e.getMessage());
            }
        });
    }

    private JPanel createStatCard(String label, String value, Color bgColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                super.paintComponent(g);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        
        card.setLayout(new GridBagLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(200, 110));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelComponent.setForeground(new Color(255, 255, 255, 200));
        card.add(labelComponent, gbc);

        gbc.gridy = 1;
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);
        if (label.equals("Total rendez-vous")) {
            lblTotalRDV = valueLabel;
        } else if (label.equals("Total patients")) {
            lblTotalPatients = valueLabel;
        } else if (label.equals("Recette du jour")) {
            lblRecetteJour = valueLabel;
        }
        card.add(valueLabel, gbc);

        return card;
    }

    private JPanel createFileAttenteTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 220), 1));
        
        // Header
        JLabel headerLabel = new JLabel("👥 File d'attente - Aujourd'hui");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(30, 120, 170));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(12, 15, 8, 15));
        headerLabel.setBackground(new Color(240, 245, 250));
        headerLabel.setOpaque(true);
        panel.add(headerLabel, BorderLayout.NORTH);

        // Table
        fileAttenteModel = new DefaultTableModel(
            new Object[]{"Position", "Patient", "Arrivée", "Statut"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        fileAttenteTable = new JTable(fileAttenteModel);
        fileAttenteTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        fileAttenteTable.setRowHeight(28);
        fileAttenteTable.setSelectionBackground(new Color(200, 220, 240));
        fileAttenteTable.setSelectionForeground(Color.BLACK);
        fileAttenteTable.setGridColor(new Color(220, 225, 230));
        
        // Formatage des headers
        JTableHeader header = fileAttenteTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(30, 120, 170));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        header.setBorder(BorderFactory.createLineBorder(new Color(30, 120, 170)));
        
        // Largeurs des colonnes
        fileAttenteTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        fileAttenteTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        fileAttenteTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        fileAttenteTable.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(fileAttenteTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        refreshFileAttenteTable();

        return panel;
    }

    private void refreshFileAttenteTable() {
        fileAttenteModel.setRowCount(0);
        try {
            List<FileAttente> patients = fileAttenteService.getQueueByDate(LocalDate.now());
            if (patients != null) {
                int position = 1;
                for (FileAttente patient : patients) {
                    String nom = getDossierNom(patient.getIdDossier());
                    String arrivee = patient.getDateArrivee() != null ? 
                        patient.getDateArrivee().format(DateTimeFormatter.ofPattern("HH:mm")) : "?";
                    fileAttenteModel.addRow(new Object[]{
                        position,
                        nom,
                        arrivee,
                        patient.getStatut()
                    });
                    position++;
                }
            }
        } catch (ServiceException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private JPanel createStatistiquesPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 220), 1));
        
        // Header
        JLabel headerLabel = new JLabel("📊 Statistiques Quotidiennes");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(30, 120, 170));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(12, 15, 8, 15));
        headerLabel.setBackground(new Color(240, 245, 250));
        headerLabel.setOpaque(true);
        panel.add(headerLabel, BorderLayout.NORTH);

        // Table
        statistiquesModel = new DefaultTableModel(
            new Object[]{"Métrique", "Valeur"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Ajouter les statistiques
        statistiquesModel.addRow(new Object[]{"Consultations réalisées", "12"});
        statistiquesModel.addRow(new Object[]{"Patients vus", "12"});
        statistiquesModel.addRow(new Object[]{"Ordonnances créées", "8"});
        statistiquesModel.addRow(new Object[]{"Certificats générés", "3"});
        statistiquesModel.addRow(new Object[]{"Temps moyen/consultation", "18 min"});
        statistiquesModel.addRow(new Object[]{"Taux de satisfaction", "96%"});
        
        statistiquesTable = new JTable(statistiquesModel);
        statistiquesTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statistiquesTable.setRowHeight(26);
        statistiquesTable.setSelectionBackground(new Color(200, 220, 240));
        statistiquesTable.setSelectionForeground(Color.BLACK);
        statistiquesTable.setGridColor(new Color(220, 225, 230));
        statistiquesTable.setShowGrid(true);
        
        // Alternance de couleurs pour les lignes
        statistiquesTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    if (row % 2 == 0) {
                        c.setBackground(Color.WHITE);
                    } else {
                        c.setBackground(new Color(250, 252, 255));
                    }
                }
                if (column == 1) {
                    ((JLabel) c).setHorizontalAlignment(JLabel.RIGHT);
                    ((JLabel) c).setFont(new Font("Segoe UI", Font.BOLD, 11));
                    ((JLabel) c).setForeground(new Color(30, 120, 170));
                }
                return c;
            }
        });
        
        // Formatage des headers
        JTableHeader header = statistiquesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(30, 120, 170));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(Integer.MAX_VALUE, 32));
        
        statistiquesTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        statistiquesTable.getColumnModel().getColumn(1).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(statistiquesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActivitesPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 220), 1));
        
        // Header
        JLabel headerLabel = new JLabel("📝 Activités Récentes");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(30, 120, 170));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(12, 15, 8, 15));
        headerLabel.setBackground(new Color(240, 245, 250));
        headerLabel.setOpaque(true);
        panel.add(headerLabel, BorderLayout.NORTH);

        // Panel avec les activités
        JPanel activitiesContentPanel = new JPanel();
        activitiesContentPanel.setLayout(new BoxLayout(activitiesContentPanel, BoxLayout.Y_AXIS));
        activitiesContentPanel.setBackground(Color.WHITE);
        activitiesContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Ajouter les activités
        String[] activities = {
            "✓ Consultation complétée pour M. Ahmed",
            "✓ Ordonnance créée et imprimée",
            "✓ Rendez-vous confirmé pour demain 14:30",
            "✓ Certificat médical généré pour Mlle Fatima",
            "✓ Dossier médical mise à jour",
            "✓ RDV reporté de 2 heures confirmé"
        };

        String[] times = {
            "10:35", "10:48", "11:02", "11:15", "11:28", "11:42"
        };

        for (int i = 0; i < activities.length; i++) {
            JPanel activityItem = createActivityItem(activities[i], times[i]);
            activitiesContentPanel.add(activityItem);
        }

        JScrollPane scrollPane = new JScrollPane(activitiesContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActivityItem(String activity, String time) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 235, 240)));
        item.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel activityLabel = new JLabel(activity);
        activityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        activityLabel.setForeground(new Color(50, 50, 50));

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        timeLabel.setForeground(new Color(150, 150, 150));

        item.add(activityLabel, BorderLayout.CENTER);
        item.add(timeLabel, BorderLayout.EAST);

        return item;
    }

    private JPanel createRappelsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(new Color(255, 250, 240));
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 180), 1));
        
        // Header
        JLabel headerLabel = new JLabel("📋 À Faire Aujourd'hui");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(180, 100, 50));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(12, 15, 8, 15));
        headerLabel.setBackground(new Color(255, 245, 235));
        headerLabel.setOpaque(true);
        panel.add(headerLabel, BorderLayout.NORTH);

        // Panel avec les rappels
        JPanel remindersContentPanel = new JPanel();
        remindersContentPanel.setLayout(new BoxLayout(remindersContentPanel, BoxLayout.Y_AXIS));
        remindersContentPanel.setBackground(new Color(255, 250, 240));
        remindersContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Ajouter les rappels
        String[] reminders = {
            "☐ Vérifier stocks de médicaments courants",
            "☐ Appeler patients pour confirmations RDV",
            "☐ Mettre à jour données patients urgentes",
            "☐ Générer factures en attente",
            "☐ Archiver dossiers complétés",
            "☐ Audit des consultations"
        };

        for (String reminder : reminders) {
            JPanel reminderItem = createReminderItem(reminder);
            remindersContentPanel.add(reminderItem);
        }

        JScrollPane scrollPane = new JScrollPane(remindersContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(255, 250, 240));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReminderItem(String reminder) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        item.setBackground(new Color(255, 250, 240));
        item.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 220, 200)));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JCheckBox checkbox = new JCheckBox(reminder);
        checkbox.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        checkbox.setForeground(new Color(80, 60, 40));
        checkbox.setBackground(new Color(255, 250, 240));
        checkbox.setFocusPainted(false);

        item.add(checkbox);
        return item;
    }

    private String getDossierNom(Integer idDossier) {
        try {
            var dossier = dossierController.find_view(idDossier);
            return dossier != null ? dossier.getPatient_nom() : "?";
        } catch (Exception e) {
            return "?";
        }
    }

    private String getDossierPrenom(Integer idDossier) {
        try {
            var dossier = dossierController.find_view(idDossier);
            return dossier != null ? dossier.getPatient_prenom() : "?";
        } catch (Exception e) {
            return "?";
        }
    }

    private void startAutoRefresh() {
        autoRefreshTimer = new Timer();
        autoRefreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> refreshFileAttenteTable());
            }
        }, 0, 5000); // Actualise toutes les 5 secondes
    }

    public void stopAutoRefresh() {
        if (autoRefreshTimer != null) {
            autoRefreshTimer.cancel();
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        stopAutoRefresh();
    }
}
