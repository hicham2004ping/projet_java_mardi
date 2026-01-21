package ma.prodenta.mvc.ui.dashboard;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.FileAttente;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.ui.fileattente.FileAttenteFrame;
import ma.prodenta.service.modules.filedattente.FileAttenteService;
import ma.prodenta.common.exceptions.ServiceException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.Timer;

/**
 * Dashboard professionnel pour les médecins avec agenda factice
 */
public class MedecinDashboardPanel extends JPanel {

    private Dashboard_view dashboard;
    private FileAttenteFrame fileAttenteFrame;
    private FileAttenteService fileAttenteService;
    private DossierMedicalController dossierController;

    private JTable fileAttenteTable;
    private JTable agendaTable;
    private DefaultTableModel fileAttenteModel;
    private DefaultTableModel agendaModel;
    private Timer autoRefreshTimer;
    private JLabel lblTotalRDV;
    private JLabel lblPatientsJour;
    private JLabel lblTempsConsult;

    // Données factices pour l'agenda
    private static class RendezVousFactice {
        String heure;
        String patient;
        String type;
        String statut;
        Color couleur;

        RendezVousFactice(String heure, String patient, String type, String statut) {
            this.heure = heure;
            this.patient = patient;
            this.type = type;
            this.statut = statut;
            this.couleur = getCouleurParStatut(statut);
        }

        private Color getCouleurParStatut(String statut) {
            switch (statut) {
                case "Confirmé": return new Color(0, 153, 0);
                case "En attente": return new Color(255, 153, 0);
                case "Urgence": return new Color(204, 0, 0);
                case "Terminé": return new Color(100, 100, 100);
                default: return new Color(0, 102, 204);
            }
        }
    }

    private List<RendezVousFactice> rendezVousFactices;

    public MedecinDashboardPanel(Dashboard_view dashboard, FileAttenteFrame fileAttenteFrame) {
        this.dashboard = dashboard;
        this.fileAttenteFrame = fileAttenteFrame;
        this.fileAttenteService = Application_contexte.getFileAttenteService();
        this.dossierController = Application_contexte.getDossierMedicalController();

        // Initialiser les données factices
        initializeDonneesFactices();

        initializeUI();
        startAutoRefresh();
    }

    private void initializeDonneesFactices() {
        rendezVousFactices = new ArrayList<>();

        // Rendez-vous pour aujourd'hui
        rendezVousFactices.add(new RendezVousFactice("08:30", "Jean Dupont", "Consultation", "Confirmé"));
        rendezVousFactices.add(new RendezVousFactice("09:15", "Marie Lambert", "Suivi", "Confirmé"));
        rendezVousFactices.add(new RendezVousFactice("10:00", "Pierre Martin", "Contrôle", "En attente"));
        rendezVousFactices.add(new RendezVousFactice("11:00", "Sophie Bernard", "Urgence", "Urgence"));
        rendezVousFactices.add(new RendezVousFactice("14:30", "Thomas Leroy", "Bilan", "Confirmé"));
        rendezVousFactices.add(new RendezVousFactice("15:45", "Julie Moreau", "Consultation", "Confirmé"));
        rendezVousFactices.add(new RendezVousFactice("16:30", "David Girard", "Contrôle", "Terminé"));
        rendezVousFactices.add(new RendezVousFactice("17:15", "Isabelle Roy", "Suivi", "En attente"));
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

        // Haut droite: Agenda du jour
        mainPanel.add(createAgendaPanel());

        // Bas gauche: Statistiques médicales
        mainPanel.add(createStatistiquesPanel());

        // Bas droite: Alertes et rappels
        mainPanel.add(createAlertesPanel());

        return mainPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 10));
        headerPanel.setBackground(new Color(245, 248, 250));

        // Titre
        JLabel titleLabel = new JLabel("Bienvenue Dr. Martin");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(40, 120, 160));

        // Panel de date et heure
        JPanel timePanel = createTimePanel();

        JPanel titleDatePanel = new JPanel(new BorderLayout());
        titleDatePanel.setBackground(new Color(245, 248, 250));
        titleDatePanel.add(titleLabel, BorderLayout.NORTH);
        titleDatePanel.add(timePanel, BorderLayout.SOUTH);

        headerPanel.add(titleDatePanel, BorderLayout.WEST);

        // Panel des statistiques
        JPanel statsPanel = createStatsPanel();
        headerPanel.add(statsPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createTimePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(new Color(245, 248, 250));

        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        JLabel dateLabel = new JLabel(today.format(dateFormatter));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(120, 120, 120));

        JLabel timeLabel = new JLabel(LocalTime.now().format(timeFormatter));
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        timeLabel.setForeground(new Color(40, 120, 160));

        panel.add(dateLabel);
        panel.add(new JLabel("•"));
        panel.add(timeLabel);

        // Mettre à jour l'heure en temps réel
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLabel.setText(LocalTime.now().format(timeFormatter));
                });
            }
        }, 0, 60000); // Mise à jour chaque minute

        return panel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statsPanel.setBackground(new Color(245, 248, 250));
        statsPanel.setOpaque(false);

        // Charger les statistiques
        loadStatistics();

        // Stat 1: RDV du jour
        JPanel statRDV = createStatCard(
                "Rendez-vous aujourd'hui",
                "8",
                new Color(100, 180, 220),
                "icon-calendar"
        );
        lblTotalRDV = (JLabel) statRDV.getComponent(1);
        statsPanel.add(statRDV);

        // Stat 2: Patients vus
        JPanel statPatients = createStatCard(
                "Patients vus",
                "5",
                new Color(200, 220, 100),
                "icon-patient"
        );
        lblPatientsJour = (JLabel) statPatients.getComponent(1);
        statsPanel.add(statPatients);

        // Stat 3: Temps moyen consultation
        JPanel statTemps = createStatCard(
                "Temps moyen/consultation",
                "22 min",
                new Color(100, 200, 100),
                "icon-time"
        );
        lblTempsConsult = (JLabel) statTemps.getComponent(1);
        statsPanel.add(statTemps);

        return statsPanel;
    }

    private JPanel createStatCard(String label, String value, Color bgColor, String iconName) {
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
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Icône (simulée par un label)
        JLabel iconLabel = new JLabel(getIconForName(iconName));
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        iconLabel.setForeground(new Color(255, 255, 255, 200));
        card.add(iconLabel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        labelComponent.setForeground(new Color(255, 255, 255, 200));
        card.add(labelComponent, gbc);

        gbc.gridy = 2;
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);
        card.add(valueLabel, gbc);

        return card;
    }

    private String getIconForName(String iconName) {
        switch (iconName) {
            case "icon-calendar": return "📅";
            case "icon-patient": return "👤";
            case "icon-time": return "⏱️";
            default: return "•";
        }
    }

    private void loadStatistics() {
        // Simuler la mise à jour des statistiques
        SwingUtilities.invokeLater(() -> {
            try {
                // Ici on pourrait charger les vraies statistiques
                int totalRDV = rendezVousFactices.size();
                int patientsVus = (int) rendezVousFactices.stream()
                        .filter(rdv -> rdv.statut.equals("Terminé"))
                        .count();
                int tempsMoyen = calculerTempsMoyen();

                lblTotalRDV.setText(String.valueOf(totalRDV));
                lblPatientsJour.setText(String.valueOf(patientsVus));
                lblTempsConsult.setText(tempsMoyen + " min");
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement des statistiques: " + e.getMessage());
            }
        });
    }

    private int calculerTempsMoyen() {
        // Simuler le calcul du temps moyen (factice)
        Random rand = new Random();
        return 15 + rand.nextInt(15); // Entre 15 et 30 minutes
    }

    private JPanel createFileAttenteTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));

        // Header avec bouton d'action
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 245, 250));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel headerLabel = new JLabel("👥 File d'attente actuelle");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(30, 120, 170));

        JButton refreshBtn = new JButton("Actualiser");
        refreshBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        refreshBtn.setBackground(new Color(30, 120, 170));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        refreshBtn.setFocusPainted(false);
        refreshBtn.addActionListener(e -> refreshFileAttenteTable());

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(refreshBtn, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Table
        fileAttenteModel = new DefaultTableModel(
                new Object[]{"Position", "Patient", "Arrivée", "Priorité", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Seule la colonne Action est éditable
            }
        };

        fileAttenteTable = new JTable(fileAttenteModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }

                // Coloration de la colonne Priorité
                if (column == 3) {
                    String priorite = getValueAt(row, column).toString();
                    if (priorite.equals("Haute")) {
                        c.setForeground(new Color(204, 0, 0));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (priorite.equals("Moyenne")) {
                        c.setForeground(new Color(255, 153, 0));
                    } else {
                        c.setForeground(new Color(0, 153, 0));
                    }
                }

                return c;
            }
        };

        fileAttenteTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        fileAttenteTable.setRowHeight(32);
        fileAttenteTable.setSelectionBackground(new Color(200, 220, 240));
        fileAttenteTable.setSelectionForeground(Color.BLACK);
        fileAttenteTable.setGridColor(new Color(220, 225, 230));

        // Formatage des headers
        JTableHeader header = fileAttenteTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(30, 120, 170));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(Integer.MAX_VALUE, 36));

        // Largeurs des colonnes
        fileAttenteTable.getColumnModel().getColumn(0).setPreferredWidth(70);
        fileAttenteTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        fileAttenteTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        fileAttenteTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        fileAttenteTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        // Renderer pour la colonne Action
        fileAttenteTable.getColumnModel().getColumn(4).setCellRenderer(new ActionRenderer());
        fileAttenteTable.getColumnModel().getColumn(4).setCellEditor(new ActionEditor());

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
            if (patients != null && !patients.isEmpty()) {
                int position = 1;
                for (FileAttente patient : patients) {
                    String nom = getDossierNom(patient.getIdDossier());
                    String arrivee = patient.getDateArrivee() != null ?
                            patient.getDateArrivee().format(DateTimeFormatter.ofPattern("HH:mm")) : "?";
                    String priorite = determinerPriorite(patient.getStatut());

                    fileAttenteModel.addRow(new Object[]{
                            position,
                            nom,
                            arrivee,
                            priorite,
                            "Commencer"
                    });
                    position++;
                }
            } else {
                // Afficher un message si pas de patients
                fileAttenteModel.addRow(new Object[]{
                        "-", "Aucun patient en attente", "", "", ""
                });
            }
        } catch (ServiceException e) {
            fileAttenteModel.addRow(new Object[]{
                    "!", "Erreur: " + e.getMessage(), "", "", ""
            });
        }
    }

    private String determinerPriorite(String statut) {
        if (statut == null) return "Normale";
        if (statut.toLowerCase().contains("urgent")) return "Haute";
        if (statut.toLowerCase().contains("prioritaire")) return "Moyenne";
        return "Normale";
    }

    private JPanel createAgendaPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));

        // Header avec boutons
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 245, 250));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel headerLabel = new JLabel("📅 Agenda du jour");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(30, 120, 170));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setBackground(new Color(240, 245, 250));

        JButton prevBtn = new JButton("◀");
        JButton nextBtn = new JButton("▶");
        JButton todayBtn = new JButton("Aujourd'hui");

        for (JButton btn : new JButton[]{prevBtn, nextBtn, todayBtn}) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            btn.setBackground(new Color(30, 120, 170));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            btn.setFocusPainted(false);
        }

        buttonPanel.add(prevBtn);
        buttonPanel.add(todayBtn);
        buttonPanel.add(nextBtn);

        headerPanel.add(headerLabel, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Table de l'agenda
        agendaModel = new DefaultTableModel(
                new Object[]{"Heure", "Patient", "Type", "Statut", "Action"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Seule la colonne Action est éditable
            }
        };

        agendaTable = new JTable(agendaModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }

                // Coloration par statut
                if (column == 3) {
                    String statut = getValueAt(row, column).toString();
                    for (RendezVousFactice rdv : rendezVousFactices) {
                        if (rdv.statut.equals(statut)) {
                            c.setForeground(rdv.couleur);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                            break;
                        }
                    }
                }

                // Mise en évidence des RDV urgents
                if (column == 2 && getValueAt(row, column).toString().equals("Urgence")) {
                    c.setBackground(new Color(255, 230, 230));
                }

                return c;
            }
        };

        agendaTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        agendaTable.setRowHeight(32);
        agendaTable.setSelectionBackground(new Color(200, 220, 240));
        agendaTable.setGridColor(new Color(220, 225, 230));

        // Formatage des headers
        JTableHeader header = agendaTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(30, 120, 170));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(Integer.MAX_VALUE, 36));

        // Largeurs des colonnes
        agendaTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        agendaTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        agendaTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        agendaTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        agendaTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        // Renderer pour la colonne Action
        agendaTable.getColumnModel().getColumn(4).setCellRenderer(new AgendaActionRenderer());
        agendaTable.getColumnModel().getColumn(4).setCellEditor(new AgendaActionEditor());

        JScrollPane scrollPane = new JScrollPane(agendaTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Remplir l'agenda
        refreshAgenda();

        return panel;
    }

    private void refreshAgenda() {
        agendaModel.setRowCount(0);

        // Trier par heure
        rendezVousFactices.sort((a, b) -> a.heure.compareTo(b.heure));

        for (RendezVousFactice rdv : rendezVousFactices) {
            String action = "Commencer";
            if (rdv.statut.equals("Terminé")) {
                action = "Voir dossier";
            } else if (rdv.statut.equals("En attente")) {
                action = "Confirmer";
            }

            agendaModel.addRow(new Object[]{
                    rdv.heure,
                    rdv.patient,
                    rdv.type,
                    rdv.statut,
                    action
            });
        }
    }

    private JPanel createStatistiquesPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));

        // Header
        JLabel headerLabel = new JLabel("📊 Statistiques Médicales");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(30, 120, 170));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(12, 15, 8, 15));
        headerLabel.setBackground(new Color(240, 245, 250));
        headerLabel.setOpaque(true);
        panel.add(headerLabel, BorderLayout.NORTH);

        // Panel avec les statistiques
        JPanel statsContentPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        statsContentPanel.setBackground(Color.WHITE);
        statsContentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Statistiques factices
        String[][] stats = {
                {"Consultations/mois", "128"},
                {"Patients nouveaux", "24"},
                {"Prescriptions", "89"},
                {"Examens demandés", "45"},
                {"Hospitalisations", "3"},
                {"Visites à domicile", "7"},
                {"Taux d'occupation", "78%"},
                {"Satisfaction patients", "94%"}
        };

        for (String[] stat : stats) {
            JPanel statItem = createStatItem(stat[0], stat[1]);
            statsContentPanel.add(statItem);
        }

        JScrollPane scrollPane = new JScrollPane(statsContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatItem(String label, String value) {
        JPanel item = new JPanel(new BorderLayout(5, 0));
        item.setBackground(Color.WHITE);
        item.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 235, 240)));
        item.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        labelComp.setForeground(new Color(100, 100, 100));

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        valueComp.setForeground(new Color(30, 120, 170));
        valueComp.setHorizontalAlignment(SwingConstants.RIGHT);

        item.add(labelComp, BorderLayout.WEST);
        item.add(valueComp, BorderLayout.EAST);

        return item;
    }

    private JPanel createAlertesPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(new Color(255, 250, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 180)),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));

        // Header
        JLabel headerLabel = new JLabel("⚠ Alertes & Rappels");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        headerLabel.setForeground(new Color(180, 100, 50));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(12, 15, 8, 15));
        headerLabel.setBackground(new Color(255, 245, 235));
        headerLabel.setOpaque(true);
        panel.add(headerLabel, BorderLayout.NORTH);

        // Panel avec les alertes
        JPanel alertsContentPanel = new JPanel();
        alertsContentPanel.setLayout(new BoxLayout(alertsContentPanel, BoxLayout.Y_AXIS));
        alertsContentPanel.setBackground(new Color(255, 250, 240));
        alertsContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Alertes factices
        String[][] alertes = {
                {"Rouge", "Résultats labo en attente - M. Ahmed"},
                {"Orange", "Suivi post-opératoire - Mlle Fatima"},
                {"Jaune", "Renouvellement ordonnance - Mme Bernard"},
                {"Bleue", "Bilan annuel programmé"},
                {"Verte", "Vaccination rappel - Enfants"}
        };

        Color[] couleurs = {
                new Color(255, 200, 200),
                new Color(255, 220, 180),
                new Color(255, 255, 200),
                new Color(200, 220, 255),
                new Color(200, 255, 200)
        };

        for (int i = 0; i < alertes.length; i++) {
            JPanel alerteItem = createAlerteItem(alertes[i][0], alertes[i][1], couleurs[i]);
            alertsContentPanel.add(alerteItem);
        }

        JScrollPane scrollPane = new JScrollPane(alertsContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(255, 250, 240));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAlerteItem(String niveau, String message, Color bgColor) {
        JPanel item = new JPanel(new BorderLayout(10, 5));
        item.setBackground(bgColor);
        item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel niveauLabel = new JLabel("• " + niveau);
        niveauLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        niveauLabel.setForeground(bgColor.darker().darker());
        niveauLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        messageLabel.setForeground(Color.BLACK);

        item.add(niveauLabel, BorderLayout.WEST);
        item.add(messageLabel, BorderLayout.CENTER);

        return item;
    }

    private String getDossierNom(Integer idDossier) {
        try {
            var dossier = dossierController.find_view(idDossier);
            return dossier != null ? dossier.getPatient_nom() : "Patient #" + idDossier;
        } catch (Exception e) {
            return "Patient inconnu";
        }
    }

    private String getDossierPrenom(Integer idDossier) {
        try {
            var dossier = dossierController.find_view(idDossier);
            return dossier != null ? dossier.getPatient_prenom() : "";
        } catch (Exception e) {
            return "";
        }
    }

    // Classes internes pour les renderers et editors
    private class ActionRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = new JButton(value.toString());
            button.setFont(new Font("Segoe UI", Font.BOLD, 11));
            button.setBackground(new Color(0, 102, 204));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return button;
        }
    }

    private class ActionEditor extends DefaultCellEditor {
        private JButton button;

        public ActionEditor() {
            super(new JTextField());
            setClickCountToStart(1);

            button = new JButton();
            button.setFont(new Font("Segoe UI", Font.BOLD, 11));
            button.setBackground(new Color(0, 102, 204));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            button.setFocusPainted(false);

            button.addActionListener(e -> {
                int row = fileAttenteTable.getSelectedRow();
                if (row != -1) {
                    String patient = (String) fileAttenteTable.getValueAt(row, 1);
                    JOptionPane.showMessageDialog(MedecinDashboardPanel.this,
                            "Début de consultation pour " + patient,
                            "Consultation", JOptionPane.INFORMATION_MESSAGE);
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            button.setText(value.toString());
            return button;
        }
    }

    private class AgendaActionRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = new JButton(value.toString());
            button.setFont(new Font("Segoe UI", Font.PLAIN, 11));

            String statut = (String) table.getValueAt(row, 3);
            if (value.toString().equals("Commencer")) {
                button.setBackground(new Color(0, 153, 0));
            } else if (value.toString().equals("Confirmer")) {
                button.setBackground(new Color(255, 153, 0));
            } else {
                button.setBackground(new Color(100, 100, 100));
            }

            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return button;
        }
    }

    private class AgendaActionEditor extends DefaultCellEditor {
        private JButton button;

        public AgendaActionEditor() {
            super(new JTextField());
            setClickCountToStart(1);

            button = new JButton();
            button.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            button.setFocusPainted(false);

            button.addActionListener(e -> {
                int row = agendaTable.getSelectedRow();
                if (row != -1) {
                    String patient = (String) agendaTable.getValueAt(row, 1);
                    String heure = (String) agendaTable.getValueAt(row, 0);
                    String action = button.getText();

                    String message = "";
                    if (action.equals("Commencer")) {
                        message = "Début de la consultation pour " + patient + " à " + heure;
                    } else if (action.equals("Confirmer")) {
                        message = "Confirmation du rendez-vous pour " + patient;
                    } else {
                        message = "Ouverture du dossier médical de " + patient;
                    }

                    JOptionPane.showMessageDialog(MedecinDashboardPanel.this,
                            message, "Agenda", JOptionPane.INFORMATION_MESSAGE);
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            button.setText(value.toString());

            String statut = (String) table.getValueAt(row, 3);
            if (value.toString().equals("Commencer")) {
                button.setBackground(new Color(0, 153, 0));
            } else if (value.toString().equals("Confirmer")) {
                button.setBackground(new Color(255, 153, 0));
            } else {
                button.setBackground(new Color(100, 100, 100));
            }

            return button;
        }
    }

    private void startAutoRefresh() {
        autoRefreshTimer = new Timer();
        autoRefreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    refreshFileAttenteTable();
                    refreshAgenda();
                    loadStatistics();
                });
            }
        }, 0, 30000); // Actualise toutes les 30 secondes
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