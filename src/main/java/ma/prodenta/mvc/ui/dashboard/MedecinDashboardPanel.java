package ma.prodenta.mvc.ui.dashboard;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.FileAttente;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.ui.fileattente.FileAttenteFrame;
import ma.prodenta.service.modules.FileAttenteService;
import ma.prodenta.common.exceptions.ServiceException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Dashboard d'accueil pour les médecins
 * Affiche les statistiques et les consultations du jour
 */
public class MedecinDashboardPanel extends JPanel {

    private Dashboard_view dashboard;
    private FileAttenteFrame fileAttenteFrame;
    private FileAttenteService fileAttenteService;
    private DossierMedicalController dossierController;
    
    private JPanel fileAttenteCardsPanel;
    private JLabel lblTotalConsultations;
    private JLabel lblPatientsAujourdhui;
    private JLabel lblTempsAttente;
    private Timer autoRefreshTimer;

    public MedecinDashboardPanel(Dashboard_view dashboard, FileAttenteFrame fileAttenteFrame) {
        this.dashboard = dashboard;
        this.fileAttenteFrame = fileAttenteFrame;
        this.fileAttenteService = Application_contexte.getFileAttenteService();
        this.dossierController = Application_contexte.getDossierMedicalController();
        
        initializeUI();
        startAutoRefresh();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel supérieur avec titre et stats
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Panel central avec file d'attente et patients du jour
        add(createConsultationPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 10));
        headerPanel.setBackground(new Color(245, 248, 250));

        // Titre
        JLabel titleLabel = new JLabel("Bienvenue Dr.");
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

        // Stat 1: Total Consultations
        JPanel stat1 = createStatCard(
            "Consultations",
            "24",
            new Color(100, 180, 220)
        );
        lblTotalConsultations = (JLabel) ((JPanel) stat1).getComponent(1);
        statsPanel.add(stat1);

        // Stat 2: Patients aujourd'hui
        JPanel stat2 = createStatCard(
            "Patients aujourd'hui",
            "8",
            new Color(200, 220, 100)
        );
        lblPatientsAujourdhui = (JLabel) ((JPanel) stat2).getComponent(1);
        statsPanel.add(stat2);

        // Stat 3: Temps d'attente moyen
        JPanel stat3 = createStatCard(
            "Temps d'attente moyen",
            "15 min",
            new Color(100, 200, 100)
        );
        lblTempsAttente = (JLabel) ((JPanel) stat3).getComponent(1);
        statsPanel.add(stat3);

        return statsPanel;
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
        card.add(valueLabel, gbc);

        return card;
    }

    private JPanel createConsultationPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 248, 250));

        // Titre
        JLabel titleLabel = new JLabel("Patients en attente");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(40, 120, 160));

        // Panel scrollable pour les cartes
        fileAttenteCardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        fileAttenteCardsPanel.setBackground(new Color(245, 248, 250));
        fileAttenteCardsPanel.setOpaque(true);

        JScrollPane scrollPane = new JScrollPane(fileAttenteCardsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(new Color(245, 248, 250));

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        refreshFileAttente();

        return mainPanel;
    }

    private void refreshFileAttente() {
        try {
            fileAttenteCardsPanel.removeAll();
            List<FileAttente> patients = fileAttenteService.getQueueByDate(LocalDate.now());

            if (patients == null || patients.isEmpty()) {
                JLabel emptyLabel = new JLabel("Aucun patient en attente");
                emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
                emptyLabel.setForeground(new Color(150, 150, 150));
                fileAttenteCardsPanel.add(emptyLabel);
            } else {
                int position = 1;
                for (FileAttente patient : patients) {
                    fileAttenteCardsPanel.add(createPatientCard(patient, position));
                    position++;
                }
            }

            fileAttenteCardsPanel.revalidate();
            fileAttenteCardsPanel.repaint();
        } catch (ServiceException e) {
            JLabel errorLabel = new JLabel("Erreur: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            fileAttenteCardsPanel.add(errorLabel);
        }
    }

    private JPanel createPatientCard(FileAttente patient, int position) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                super.paintComponent(g);
            }

            @Override
            public boolean isOpaque() {
                return false;
            }
        };
        
        card.setLayout(new GridBagLayout());
        card.setBackground(new Color(150, 100, 180));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        card.setPreferredSize(new Dimension(150, 120));
        card.setMaximumSize(new Dimension(150, 120));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.gridx = 0;
        gbc.weightx = 1;

        // Position
        gbc.gridy = 0;
        JLabel posLabel = new JLabel("Position: #" + position);
        posLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        posLabel.setForeground(Color.WHITE);
        card.add(posLabel, gbc);

        // Nom du patient
        gbc.gridy = 1;
        String nomPatient = getDossierNom(patient.getIdDossier());
        JLabel nomLabel = new JLabel(nomPatient);
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nomLabel.setForeground(Color.WHITE);
        card.add(nomLabel, gbc);

        // Prénom
        gbc.gridy = 2;
        String prenomPatient = getDossierPrenom(patient.getIdDossier());
        JLabel prenomLabel = new JLabel(prenomPatient);
        prenomLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        prenomLabel.setForeground(new Color(255, 255, 255, 200));
        card.add(prenomLabel, gbc);

        // Statut
        gbc.gridy = 3;
        JLabel statutLabel = new JLabel("Statut: " + patient.getStatut());
        statutLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        statutLabel.setForeground(new Color(255, 255, 255, 180));
        card.add(statutLabel, gbc);

        return card;
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
                SwingUtilities.invokeLater(() -> refreshFileAttente());
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
