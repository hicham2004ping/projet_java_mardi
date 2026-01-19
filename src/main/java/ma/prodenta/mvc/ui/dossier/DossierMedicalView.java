package ma.prodenta.mvc.ui.dossier;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.*;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.controllers.modules.rdv.RDVController;
import ma.prodenta.mvc.dto.rdv.RDVDTO;
import ma.prodenta.repository.modules.actes.impl.Acte_impl;
import ma.prodenta.service.modules.consultation.impl.Consultation_service_impl;
import ma.prodenta.service.modules.intervention.impl.Intervention_Service_impl;
import ma.prodenta.service.modules.ordonnance.impl.OrdonnanceImpl;
import ma.prodenta.repository.modules.certificat.impl.CertificatDaoimpl;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DossierMedicalView extends JPanel {

    private Integer idDossier;
    private Patient patient;
    private DossierMedical dossier;
    private DossierMedicalController dossierController;
    private RDVController rdvController;
    private Consultation_service_impl consultationService;
    private Intervention_Service_impl interventionService;
    private OrdonnanceImpl ordonnanceService;
    private CertificatDaoimpl certificatService;
    private Acte_impl acteRepository;

    private JTabbedPane tabbedPane;
    private JPanel consultationPanel, rdvPanel, certificatsPanel, ordonnancesPanel, situationFinancierePanel;

    // Consultation Panel Components
    private JTable consultationsTable;
    private DefaultTableModel consultationsModel;
    private JDateChooser dateConsultationChooser;
    private JComboBox<ActeComboItem> acteCombo;
    private JTextField dentField, prixBaseField, prixPatientField;
    private JTextArea noteArea;
    private JButton btnCreerConsultation;
    private Integer idMedecin = 1; // À récupérer depuis la session

    public DossierMedicalView(Integer idDossier) throws Exception {
        this.idDossier = idDossier;
        this.dossierController = Application_contexte.getDossierMedicalController();
        this.rdvController = new RDVController();
        this.consultationService = Application_contexte.getconsultationService();
        this.interventionService = Application_contexte.getinterventionService();
        this.ordonnanceService = new OrdonnanceImpl();
        this.certificatService = Application_contexte.getCertificatRepository();
        this.acteRepository = Application_contexte.getActeRepository();

        // Charger les données
        loadDossierData();

        initializeUI();
    }

    private void loadDossierData() throws Exception {
        dossier = dossierController.find_by_id(idDossier);
        patient = dossierController.find_patient(idDossier);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header avec informations patient
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Onglets
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        consultationPanel = createConsultationPanel();
        rdvPanel = createRDVPanel();
        certificatsPanel = createCertificatsPanel();
        ordonnancesPanel = createOrdonnancesPanel();
        situationFinancierePanel = createSituationFinancierePanel();

        tabbedPane.addTab("Consultation", consultationPanel);
        tabbedPane.addTab("RDV", rdvPanel);
        tabbedPane.addTab("Certificats", certificatsPanel);
        tabbedPane.addTab("Ordonnances", ordonnancesPanel);
        tabbedPane.addTab("Situation Financière", situationFinancierePanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(20, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 250));

        // Informations patient (gauche)
        JPanel patientInfoPanel = new JPanel(new GridBagLayout());
        patientInfoPanel.setBackground(new Color(245, 245, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 20);
        gbc.anchor = GridBagConstraints.WEST;

        // Icône patient (circulaire)
        JLabel patientIcon = new JLabel("👤");
        patientIcon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridheight = 6;
        patientInfoPanel.add(patientIcon, gbc);

        gbc.gridheight = 1;
        gbc.gridx = 1;

        if (patient != null) {
            addLabelValue(patientInfoPanel, gbc, "Nom:", patient.getNom() + " " + patient.getPrenom(), 0);
            addLabelValue(patientInfoPanel, gbc, "Âge:", calculateAge(patient.getDateNaissance()) + " ans", 1);
            addLabelValue(patientInfoPanel, gbc, "Téléphone:", patient.getTelephone() != null ? patient.getTelephone() : "-", 2);
            addLabelValue(patientInfoPanel, gbc, "Date naissance:", formatDate(patient.getDateNaissance()), 3);
            addLabelValue(patientInfoPanel, gbc, "Sexe:", patient.getSexe() != null ? patient.getSexe().name() : "-", 4);
            addLabelValue(patientInfoPanel, gbc, "Email:", patient.getEmail() != null ? patient.getEmail() : "-", 5);
            addLabelValue(patientInfoPanel, gbc, "Adresse:", patient.getAdresse() != null ? patient.getAdresse() : "-", 6);
        }

        panel.add(patientInfoPanel, BorderLayout.WEST);

        return panel;
    }

    private void addLabelValue(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridy = row;
        gbc.gridx = 1;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl, gbc);

        gbc.gridx = 2;
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(val, gbc);
    }

    private String calculateAge(LocalDate dateNaissance) {
        if (dateNaissance == null) return "N/A";
        return String.valueOf(LocalDate.now().getYear() - dateNaissance.getYear());
    }

    private String formatDate(LocalDate date) {
        if (date == null) return "-";
        return date.toString();
    }

    private JPanel createConsultationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel supérieur avec tableau des consultations
        JPanel topPanel = new JPanel(new BorderLayout());

        // Tableau des consultations précédentes
        String[] colonnes = {"Date", "Acte", "Dents", "Prix", "Actions"};
        consultationsModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Seule la colonne Actions est éditable
            }
        };

        consultationsTable = new JTable(consultationsModel);
        consultationsTable.setRowHeight(35);
        consultationsTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Masquer la colonne ID si nécessaire
        JScrollPane scrollTable = new JScrollPane(consultationsTable);
        scrollTable.setPreferredSize(new Dimension(600, 300));
        scrollTable.setBorder(BorderFactory.createTitledBorder("Historique des consultations"));

        topPanel.add(scrollTable, BorderLayout.CENTER);

        // Panel inférieur avec formulaire et note
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        // Formulaire créer consultation
        JPanel formPanel = createConsultationForm();
        bottomPanel.add(formPanel);

        // Section Note
        JPanel notePanel = new JPanel(new BorderLayout());
        notePanel.setBorder(BorderFactory.createTitledBorder("Note"));
        noteArea = new JTextArea(8, 20);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JScrollPane noteScroll = new JScrollPane(noteArea);
        notePanel.add(noteScroll, BorderLayout.CENTER);
        bottomPanel.add(notePanel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);

        // Charger les consultations
        loadConsultations();

        return panel;
    }

    private JPanel createConsultationForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Créer consultation"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Date
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("📅 Date:"), gbc);
        dateConsultationChooser = new JDateChooser();
        dateConsultationChooser.setDate(new Date());
        dateConsultationChooser.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        panel.add(dateConsultationChooser, gbc);

        // Acte
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("✏️ Acte:"), gbc);
        acteCombo = new JComboBox<>();
        loadActes();
        acteCombo.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        panel.add(acteCombo, gbc);

        // Dent
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("🦷 Dent:"), gbc);
        dentField = new JTextField(15);
        dentField.setToolTipText("Numéro de dent (1-32) ou 'tout' pour toutes");
        gbc.gridx = 1;
        panel.add(dentField, gbc);

        // Prix de base
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("💰 Prix de base:"), gbc);
        prixBaseField = new JTextField(15);
        prixBaseField.setEditable(false);
        gbc.gridx = 1;
        panel.add(prixBaseField, gbc);

        // Prix patient
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("💰 Prix patient:"), gbc);
        prixPatientField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(prixPatientField, gbc);

        // Bouton Créer
        btnCreerConsultation = new JButton("Créer");
        btnCreerConsultation.setPreferredSize(new Dimension(150, 35));
        btnCreerConsultation.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnCreerConsultation, gbc);

        // Actions
        acteCombo.addActionListener(e -> {
            ActeComboItem selected = (ActeComboItem) acteCombo.getSelectedItem();
            if (selected != null) {
                prixBaseField.setText(String.valueOf(selected.getPrix()));
            }
        });

        btnCreerConsultation.addActionListener(e -> creerConsultation());

        return panel;
    }

    private void loadActes() {
        try {
            List<Acte> actes = acteRepository.findAll();
            for (Acte acte : actes) {
                acteCombo.addItem(new ActeComboItem(acte.getId(), acte.getLibelle(), acte.getPrix_de_base()));
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement actes: " + e.getMessage());
        }
    }

    private void loadConsultations() {
        consultationsModel.setRowCount(0);
        try {
            List<Consultation> consultations = Application_contexte.getConsultationRepository()
                    .findByDossier(idDossier);

            for (Consultation consultation : consultations) {
                // Récupérer les interventions
                List<Intervention> interventions = Application_contexte.getInterventionRepository()
                        .interventions_par_consultation(consultation);

                if (interventions.isEmpty()) {
                    // Consultation sans intervention
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    consultationsModel.addRow(new Object[]{
                            df.format(consultation.getDateConsult()),
                            "Consultation",
                            "-",
                            "0",
                            createActionButtons(consultation.getIdConsult())
                    });
                } else {
                    // Afficher chaque intervention
                    for (Intervention intervention : interventions) {
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        String dents = intervention.getNumero_dent() == 0 ? "tout" : String.valueOf(intervention.getNumero_dent());
                        consultationsModel.addRow(new Object[]{
                                df.format(consultation.getDateConsult()),
                                intervention.getActe().getLibelle(),
                                dents,
                                String.valueOf(intervention.getPrix_patient()),
                                createActionButtons(consultation.getIdConsult())
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement consultations: " + e.getMessage());
        }
    }

    private JPanel createActionButtons(Integer idConsultation) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.setOpaque(false);

        JButton btnPayer = new JButton("💰");
        JButton btnEdit = new JButton("✏️");
        JButton btnDelete = new JButton("🗑️");

        btnPayer.setPreferredSize(new Dimension(30, 30));
        btnEdit.setPreferredSize(new Dimension(30, 30));
        btnDelete.setPreferredSize(new Dimension(30, 30));

        btnDelete.addActionListener(e -> {
            // TODO: Supprimer consultation
        });

        panel.add(btnPayer);
        panel.add(btnEdit);
        panel.add(btnDelete);

        return panel;
    }

    private void creerConsultation() {
        try {
            // Vérifier s'il y a une consultation en cours
            Consultation consultationActive = null;
            try {
                consultationActive = consultationService.getConsultationActiveDuPatient(patient.getId());
            } catch (Exception e) {
                // Pas de consultation active, c'est bon
            }

            if (consultationActive != null && consultationActive.getIdConsult() != null) {
                // Il y a une consultation en cours, proposer de la terminer
                int option = JOptionPane.showConfirmDialog(this,
                        "Une consultation est déjà en cours pour ce patient.\n" +
                                "Voulez-vous la terminer avant de créer une nouvelle consultation?",
                        "Consultation en cours",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {
                    // Terminer la consultation en cours
                    try {
                        consultationService.cloturerConsultation(consultationActive.getIdConsult());
                        JOptionPane.showMessageDialog(this,
                                "Consultation terminée avec succès",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                                "Erreur lors de la fermeture de la consultation: " + ex.getMessage(),
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    // L'utilisateur ne veut pas terminer, on annule
                    return;
                }
            }

            Date date = dateConsultationChooser.getDate();
            if (date == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une date", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ActeComboItem selectedActe = (ActeComboItem) acteCombo.getSelectedItem();
            if (selectedActe == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un acte", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String dentText = dentField.getText().trim();
            int numeroDent = 0; // 0 = toutes les dents
            if (!dentText.isEmpty() && !dentText.equalsIgnoreCase("tout")) {
                try {
                    numeroDent = Integer.parseInt(dentText);
                    if (numeroDent < 1 || numeroDent > 32) {
                        JOptionPane.showMessageDialog(this, "Le numéro de dent doit être entre 1 et 32", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Numéro de dent invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            int prixPatient;
            if (prixPatientField.getText().trim().isEmpty()) {
                prixPatient = -1; // Utiliser le prix de base
            } else {
                prixPatient = Integer.parseInt(prixPatientField.getText().trim());
            }

            // Créer un RDV d'abord
            RDV rdv = new RDV();
            rdv.setDateRDV(date);
            rdv.setHeure(new Time(new Date().getTime()));
            rdv.setMotif("Consultation");
            rdv.setNoteMedecin(noteArea.getText());
            rdv.setIddossier(idDossier);

            Application_contexte.getRDVRepository().create(rdv);

            // Démarrer la consultation
            consultationService.demarerConsultation(idDossier, idMedecin, rdv.getIdRDV(), noteArea.getText());

            // Récupérer la consultation créée (réutiliser la variable existante)
            consultationActive = consultationService.getConsultationActiveDuPatient(patient.getId());

            if (consultationActive == null || consultationActive.getIdConsult() == null) {
                throw new Exception("Erreur lors de la récupération de la consultation créée");
            }

            // Ajouter l'intervention
            interventionService.ajouterInterventionAConsultation(
                    consultationActive.getIdConsult(),
                    selectedActe.getId(),
                    numeroDent,
                    prixPatient
            );

            // Clôturer la consultation
            consultationService.cloturerConsultation(consultationActive.getIdConsult());

            JOptionPane.showMessageDialog(this, "Consultation créée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);

            // Réinitialiser le formulaire
            dateConsultationChooser.setDate(new Date());
            dentField.setText("");
            prixPatientField.setText("");
            noteArea.setText("");

            // Recharger les consultations
            loadConsultations();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Classe interne pour Acte ComboBox
    private static class ActeComboItem {
        private int id;
        private String libelle;
        private double prix;

        public ActeComboItem(int id, String libelle, double prix) {
            this.id = id;
            this.libelle = libelle;
            this.prix = prix;
        }

        public int getId() { return id; }
        public double getPrix() { return prix; }

        @Override
        public String toString() {
            return libelle;
        }
    }

    // Panels pour les autres onglets
    private JPanel createRDVPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tableau des RDV
        String[] colonnes = {"Date", "Heure", "Motif", "Note", "Actions"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Rendez-vous"));

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Créer un RDV"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JDateChooser dateRDV = new JDateChooser();
        dateRDV.setDate(new Date());
        JComboBox<Integer> heureCombo = new JComboBox<>(new Integer[]{6,7,8,9,10,11,12,15,16,17,18,19,20,21,22});
        JTextField motifField = new JTextField(20);
        JTextArea noteField = new JTextArea(3, 20);
        JButton btnCreer = new JButton("Créer RDV");

        addField(formPanel, gbc, "Date:", dateRDV, 0);
        addField(formPanel, gbc, "Heure:", heureCombo, 1);
        addField(formPanel, gbc, "Motif:", motifField, 2);
        addField(formPanel, gbc, "Note:", new JScrollPane(noteField), 3);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnCreer, gbc);

        btnCreer.addActionListener(e -> {
            try {
                RDVDTO dto = new RDVDTO();
                dto.setDateRDV(dateRDV.getDate());
                dto.setHeure(new Time(((Integer)heureCombo.getSelectedItem()), 0, 0));
                dto.setMotif(motifField.getText());
                dto.setNoteMedecin(noteField.getText());
                dto.setIdDossier(idDossier);

                if (rdvController.ajouterRDV(dto)) {
                    JOptionPane.showMessageDialog(this, "RDV créé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadRDVs(table, model);
                    motifField.setText("");
                    noteField.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        loadRDVs(table, model);
        return panel;
    }

    private void loadRDVs(JTable table, DefaultTableModel model) {
        model.setRowCount(0);
        try {
            List<RDVDTO> rdvs = rdvController.afficherParDossier(idDossier);
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
            for (RDVDTO rdv : rdvs) {
                model.addRow(new Object[]{
                        df.format(rdv.getDateRDV()),
                        tf.format(rdv.getHeure()),
                        rdv.getMotif(),
                        rdv.getNoteMedecin(),
                        "Actions"
                });
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement RDV: " + e.getMessage());
        }
    }

    private JPanel createCertificatsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tableau
        String[] colonnes = {"Date début", "Date fin", "Nature", "Note", "Actions"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Certificats"));

        // Formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Créer un certificat"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JDateChooser dateDebut = new JDateChooser();
        JDateChooser dateFin = new JDateChooser();
        JTextField natureField = new JTextField(20);
        JTextArea noteField = new JTextArea(3, 20);
        JButton btnCreer = new JButton("Créer Certificat");

        addField(formPanel, gbc, "Date début:", dateDebut, 0);
        addField(formPanel, gbc, "Date fin:", dateFin, 1);
        addField(formPanel, gbc, "Nature:", natureField, 2);
        addField(formPanel, gbc, "Note:", new JScrollPane(noteField), 3);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnCreer, gbc);

        btnCreer.addActionListener(e -> {
            try {
                Certificat cert = new Certificat();
                cert.setDateDebut(dateDebut.getDate());
                cert.setDateFin(dateFin.getDate());
                cert.setNature(natureField.getText());
                cert.setNoteMedecin(noteField.getText());
                cert.setIdDossier(idDossier);
                Integer lastConsultId = getLastConsultationId();
                cert.setIdConsult(lastConsultId != null ? lastConsultId : null);

                if (certificatService.create(cert)) {
                    JOptionPane.showMessageDialog(this, "Certificat créé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadCertificats(table, model);
                    natureField.setText("");
                    noteField.setText("");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        loadCertificats(table, model);
        return panel;
    }

    private void loadCertificats(JTable table, DefaultTableModel model) {
        model.setRowCount(0);
        try {
            List<Certificat> certificats = certificatService.findByDossier(idDossier);
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            for (Certificat cert : certificats) {
                model.addRow(new Object[]{
                        df.format(cert.getDateDebut()),
                        df.format(cert.getDateFin()),
                        cert.getNature(),
                        cert.getNoteMedecin(),
                        "Actions"
                });
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement certificats: " + e.getMessage());
        }
    }

    private JPanel createOrdonnancesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Tableau avec colonnes pour médicaments
        String[] colonnes = {"Date", "Médicament", "Laboratoire", "Quantité", "Fréquence", "Durée (jours)", "Prix unitaire", "Type", "Remboursable", "Actions"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        // Ajuster la largeur des colonnes
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Date
        table.getColumnModel().getColumn(1).setPreferredWidth(150); // Médicament
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Laboratoire
        table.getColumnModel().getColumn(3).setPreferredWidth(70);  // Quantité
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Fréquence
        table.getColumnModel().getColumn(5).setPreferredWidth(90);   // Durée
        table.getColumnModel().getColumn(6).setPreferredWidth(90);  // Prix
        table.getColumnModel().getColumn(7).setPreferredWidth(100);  // Type
        table.getColumnModel().getColumn(8).setPreferredWidth(100); // Remboursable

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Ordonnances"));

        // Panel pour créer une ordonnance avec médicaments
        JPanel formPanel = createOrdonnanceFormPanel(table, model);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(formPanel, BorderLayout.SOUTH);

        loadOrdonnances(table, model);
        return panel;
    }

    /**
     * Crée le formulaire pour créer une ordonnance avec médicaments
     */
    private JPanel createOrdonnanceFormPanel(JTable table, DefaultTableModel model) {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createTitledBorder("Créer une ordonnance"));

        // Liste temporaire des médicaments à ajouter
        java.util.List<PrescriptionItem> medicamentsList = new java.util.ArrayList<>();

        // Tableau temporaire pour afficher les médicaments ajoutés
        String[] tempColonnes = {"Médicament", "Quantité", "Fréquence", "Durée (jours)", "Actions"};
        DefaultTableModel tempModel = new DefaultTableModel(tempColonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tempTable = new JTable(tempModel);
        tempTable.setRowHeight(25);
        JScrollPane tempScroll = new JScrollPane(tempTable);
        tempScroll.setPreferredSize(new Dimension(0, 150));
        tempScroll.setBorder(BorderFactory.createTitledBorder("Médicaments à prescrire"));

        // Formulaire pour ajouter un médicament
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Date de l'ordonnance
        JDateChooser dateOrd = new JDateChooser();
        dateOrd.setDate(new Date());
        dateOrd.setPreferredSize(new Dimension(200, 30));

        // ComboBox pour sélectionner le médicament
        JComboBox<MedicamentComboItem> medicamentCombo = new JComboBox<>();
        try {
            List<Medicament> medicaments = Application_contexte.getMedicamentRepository().findAll();
            for (Medicament med : medicaments) {
                medicamentCombo.addItem(new MedicamentComboItem(med.getIdMed(), med.getNom()));
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement médicaments: " + e.getMessage());
        }
        medicamentCombo.setPreferredSize(new Dimension(250, 30));

        // Champs pour les détails de la prescription
        JTextField quantiteField = new JTextField(10);
        quantiteField.setToolTipText("Quantité (ex: 1, 2, 10)");

        JTextField frequenceField = new JTextField(15);
        frequenceField.setToolTipText("Fréquence (ex: 3 fois par jour, matin et soir)");

        JTextField dureeField = new JTextField(10);
        dureeField.setToolTipText("Durée en jours (ex: 7, 10, 15)");

        // Boutons
        JButton btnAjouterMedicament = new JButton("➕ Ajouter médicament");
        btnAjouterMedicament.setBackground(new Color(76, 175, 80));
        btnAjouterMedicament.setForeground(Color.WHITE);

        JButton btnCreerOrdonnance = new JButton("💾 Créer Ordonnance");
        btnCreerOrdonnance.setBackground(new Color(33, 150, 243));
        btnCreerOrdonnance.setForeground(Color.WHITE);
        btnCreerOrdonnance.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Layout du formulaire
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("📅 Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(dateOrd, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("💊 Médicament:"), gbc);
        gbc.gridx = 1;
        formPanel.add(medicamentCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("📦 Quantité:"), gbc);
        gbc.gridx = 1;
        formPanel.add(quantiteField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("🔄 Fréquence:"), gbc);
        gbc.gridx = 1;
        formPanel.add(frequenceField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("⏱️ Durée (jours):"), gbc);
        gbc.gridx = 1;
        formPanel.add(dureeField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnAjouterMedicament, gbc);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnCreerOrdonnance);

        // Actions
        btnAjouterMedicament.addActionListener(e -> {
            MedicamentComboItem selected = (MedicamentComboItem) medicamentCombo.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament",
                        "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String quantiteStr = quantiteField.getText().trim();
            String frequence = frequenceField.getText().trim();
            String dureeStr = dureeField.getText().trim();

            if (quantiteStr.isEmpty() || frequence.isEmpty() || dureeStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs",
                        "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int quantite = Integer.parseInt(quantiteStr);
                int duree = Integer.parseInt(dureeStr);

                // Ajouter à la liste temporaire
                PrescriptionItem item = new PrescriptionItem();
                item.idMed = selected.getId();
                item.nomMedicament = selected.getNom();
                item.quantite = quantite;
                item.frequence = frequence;
                item.dureeEnJours = duree;

                medicamentsList.add(item);

                // Ajouter au tableau temporaire
                tempModel.addRow(new Object[]{
                        item.nomMedicament,
                        item.quantite,
                        item.frequence,
                        item.dureeEnJours,
                        "Supprimer"
                });

                // Réinitialiser les champs
                quantiteField.setText("");
                frequenceField.setText("");
                dureeField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "La quantité et la durée doivent être des nombres",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Supprimer un médicament du tableau temporaire
        tempTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = tempTable.getSelectedRow();
                    if (row >= 0) {
                        medicamentsList.remove(row);
                        tempModel.removeRow(row);
                    }
                }
            }
        });

        // Créer l'ordonnance avec tous les médicaments
        btnCreerOrdonnance.addActionListener(e -> {
            if (medicamentsList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez ajouter au moins un médicament",
                        "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Créer l'ordonnance
                Ordonnance ord = new Ordonnance();
                Date selectedDate = dateOrd.getDate();
                if (selectedDate != null) {
                    // Convertir java.util.Date en LocalDate
                    java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
                    ord.setDateOrd(sqlDate.toLocalDate());
                } else {
                    ord.setDateOrd(LocalDate.now());
                }
                ord.setIdDossier(idDossier);
                Integer lastConsultId = getLastConsultationId();
                // Si pas de consultation, laisser null
                ord.setIdConsultation(lastConsultId);

                // Créer l'ordonnance dans la base
                if (!ordonnanceService.create(ord)) {
                    throw new Exception("Erreur lors de la création de l'ordonnance");
                }

                // Récupérer l'ID de l'ordonnance créée
                int lastIdInt = ordonnanceService.lastId();
                Long idOrd = (long) lastIdInt;

                // Vérifier que l'ordonnance existe bien
                if (idOrd == null || idOrd == 0) {
                    // Fallback: chercher dans toutes les ordonnances
                    List<Ordonnance> allOrds = ordonnanceService.findAll();
                    for (Ordonnance o : allOrds) {
                        if (o.getIdDossier() != null && o.getIdDossier().equals(idDossier) &&
                                o.getDateOrd() != null && o.getDateOrd().equals(ord.getDateOrd())) {
                            idOrd = o.getIdOrd();
                            break;
                        }
                    }
                }

                if (idOrd == null || idOrd == 0) {
                    throw new Exception("Impossible de récupérer l'ID de l'ordonnance créée");
                }

                // Créer les prescriptions pour chaque médicament
                var prescriptionRepo = Application_contexte.getPrescriptionRepository();
                for (PrescriptionItem item : medicamentsList) {
                    Prescription presc = new Prescription();
                    presc.setQuantite(item.quantite);
                    presc.setFrequence(item.frequence);
                    presc.setDureeEnJours(item.dureeEnJours);
                    presc.setIdOrd(idOrd.intValue());
                    presc.setIdMed(item.idMed);

                    prescriptionRepo.create(presc);
                }

                JOptionPane.showMessageDialog(this,
                        "Ordonnance créée avec succès avec " + medicamentsList.size() + " médicament(s)!",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);

                // Réinitialiser
                medicamentsList.clear();
                tempModel.setRowCount(0);
                dateOrd.setDate(new Date());

                // Recharger les ordonnances
                loadOrdonnances(table, model);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur: " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Assembler le panel
        JPanel topFormPanel = new JPanel(new BorderLayout());
        topFormPanel.add(formPanel, BorderLayout.CENTER);
        topFormPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(tempScroll, BorderLayout.NORTH);
        mainPanel.add(topFormPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    // Classe interne pour stocker temporairement les médicaments
    private static class PrescriptionItem {
        int idMed;
        String nomMedicament;
        int quantite;
        String frequence;
        int dureeEnJours;
    }

    // Classe interne pour ComboBox médicament
    private static class MedicamentComboItem {
        private int id;
        private String nom;

        public MedicamentComboItem(int id, String nom) {
            this.id = id;
            this.nom = nom;
        }

        public int getId() { return id; }
        public String getNom() { return nom; }

        @Override
        public String toString() {
            return nom;
        }
    }

    private void loadOrdonnances(JTable table, DefaultTableModel model) {
        model.setRowCount(0);
        try {
            // Récupérer toutes les ordonnances du dossier
            List<Ordonnance> ordonnances = ordonnanceService.findAll();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            // Récupérer les repositories
            var prescriptionRepo = Application_contexte.getPrescriptionRepository();
            var medicamentRepo = Application_contexte.getMedicamentRepository();

            for (Ordonnance ord : ordonnances) {
                if (ord.getIdDossier() != null && ord.getIdDossier().equals(idDossier)) {
                    String dateOrd = df.format(java.sql.Date.valueOf(ord.getDateOrd()));

                    // Récupérer les prescriptions de cette ordonnance
                    List<Prescription> prescriptions = prescriptionRepo.findAll();
                    boolean hasPrescriptions = false;

                    for (Prescription presc : prescriptions) {
                        if (presc.getIdOrd() != null && ord.getIdOrd() != null &&
                                presc.getIdOrd().equals(ord.getIdOrd().intValue())) {
                            hasPrescriptions = true;

                            // Récupérer le médicament
                            Medicament medicament = null;
                            try {
                                medicament = medicamentRepo.findById(presc.getIdMed());
                            } catch (Exception ex) {
                                System.err.println("Erreur récupération médicament: " + ex.getMessage());
                            }

                            // Afficher la ligne avec les détails du médicament
                            String nomMed = medicament != null ? medicament.getNom() : "N/A";
                            String laboratoire = medicament != null ? (medicament.getLaboratoire() != null ? medicament.getLaboratoire() : "-") : "-";
                            String type = medicament != null ? (medicament.getType() != null ? medicament.getType() : "-") : "-";
                            String remboursable = medicament != null && medicament.getRemboursable() != null && medicament.getRemboursable() ? "Oui" : "Non";
                            String prixUnit = medicament != null && medicament.getPrixUnit() != null ?
                                    String.format("%.2f", medicament.getPrixUnit()) + " DH" : "-";

                            model.addRow(new Object[]{
                                    dateOrd,
                                    nomMed,
                                    laboratoire,
                                    presc.getQuantite() != null ? presc.getQuantite() : "-",
                                    presc.getFrequence() != null ? presc.getFrequence() : "-",
                                    presc.getDureeEnJours() != null ? presc.getDureeEnJours() : "-",
                                    prixUnit,
                                    type,
                                    remboursable,
                                    "Actions"
                            });
                        }
                    }

                    // Si aucune prescription, afficher juste la date
                    if (!hasPrescriptions) {
                        model.addRow(new Object[]{
                                dateOrd,
                                "-",
                                "-",
                                "-",
                                "-",
                                "-",
                                "-",
                                "-",
                                "-",
                                "Actions"
                        });
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement ordonnances: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private JPanel createSituationFinancierePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Situation Financière"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Calculer les totaux
        try {
            double totalActes = calculerTotalActes();
            double totalPaye = 0; // À implémenter
            double credit = totalActes - totalPaye;

            addLabelValue(infoPanel, gbc, "Total Actes:", String.valueOf(totalActes) + " DH", 0);
            addLabelValue(infoPanel, gbc, "Total Payé:", String.valueOf(totalPaye) + " DH", 1);
            addLabelValue(infoPanel, gbc, "Crédit:", String.valueOf(credit) + " DH", 2);
        } catch (Exception e) {
            System.err.println("Erreur calcul situation financière: " + e.getMessage());
        }

        panel.add(infoPanel, BorderLayout.CENTER);
        return panel;
    }

    private double calculerTotalActes() throws Exception {
        double total = 0;
        List<Consultation> consultations = Application_contexte.getConsultationRepository().findByDossier(idDossier);
        for (Consultation consultation : consultations) {
            List<Intervention> interventions = Application_contexte.getInterventionRepository()
                    .interventions_par_consultation(consultation);
            for (Intervention intervention : interventions) {
                total += intervention.getPrix_patient();
            }
        }
        return total;
    }

    private Integer getLastConsultationId() {
        try {
            List<Consultation> consultations = Application_contexte.getConsultationRepository().findByDossier(idDossier);
            if (!consultations.isEmpty()) {
                return consultations.get(consultations.size() - 1).getIdConsult();
            }
        } catch (Exception e) {
            System.err.println("Erreur récupération consultation: " + e.getMessage());
        }
        return null;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
}

