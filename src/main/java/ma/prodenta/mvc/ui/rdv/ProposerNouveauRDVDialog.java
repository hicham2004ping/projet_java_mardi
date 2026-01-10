package ma.prodenta.mvc.ui.rdv;

import ma.prodenta.mvc.controllers.modules.rdv.RDVController;
import ma.prodenta.mvc.dto.rdv.RDVDTO;
import ma.prodenta.entities.En.Patient;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Dialogue pour proposer un nouveau rendez-vous après une consultation
 * Visible uniquement pour le médecin après avoir terminé une consultation
 */
public class ProposerNouveauRDVDialog extends JDialog {

    private RDVController controller;
    private Integer idDossier;
    private Patient patient;
    private boolean rdvCree = false;

    private JLabel lblPatientInfo;
    private JDateChooser dateChooser;
    private JComboBox<Integer> heureCombo;
    private JTextField txtMotif;
    private JTextArea txtNote;
    private JButton btnProposer, btnAnnuler;

    public ProposerNouveauRDVDialog(Frame parent, Integer idDossier, Patient patient) {
        super(parent, "Proposer un nouveau rendez-vous", true);
        this.idDossier = idDossier;
        this.patient = patient;
        this.controller = new RDVController();

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Informations patient
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        String patientInfo = patient != null
                ? "Patient: " + patient.getNom() + " " + patient.getPrenom()
                : "Dossier médical: " + idDossier;
        lblPatientInfo = new JLabel(patientInfo);
        lblPatientInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mainPanel.add(lblPatientInfo, gbc);

        gbc.gridwidth = 1;

        // Date
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(new JLabel("Date du RDV:"), gbc);
        dateChooser = new JDateChooser();
        // Date minimum = aujourd'hui
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        dateChooser.setMinSelectableDate(cal.getTime());
        // Date par défaut = dans 1 semaine
        cal.add(Calendar.DAY_OF_MONTH, 7);
        dateChooser.setDate(cal.getTime());
        dateChooser.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        mainPanel.add(dateChooser, gbc);

        // Heure
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(new JLabel("Heure:"), gbc);
        Integer[] heures = {6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 18, 19, 20, 21, 22};
        heureCombo = new JComboBox<>(heures);
        heureCombo.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        mainPanel.add(heureCombo, gbc);

        // Motif
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(new JLabel("Motif:"), gbc);
        txtMotif = new JTextField(20);
        txtMotif.setText("Contrôle de suivi");
        gbc.gridx = 1;
        mainPanel.add(txtMotif, gbc);

        // Note
        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(new JLabel("Note (optionnel):"), gbc);
        txtNote = new JTextArea(3, 20);
        txtNote.setLineWrap(true);
        txtNote.setWrapStyleWord(true);
        JScrollPane scrollNote = new JScrollPane(txtNote);
        scrollNote.setPreferredSize(new Dimension(200, 60));
        gbc.gridx = 1;
        mainPanel.add(scrollNote, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnProposer = new JButton("Proposer ce RDV");
        btnAnnuler = new JButton("Annuler");
        buttonPanel.add(btnProposer);
        buttonPanel.add(btnAnnuler);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        btnProposer.addActionListener(e -> proposerRDV());
        btnAnnuler.addActionListener(e -> dispose());

        // Mettre à jour les créneaux disponibles quand la date change
        dateChooser.addPropertyChangeListener("date", evt -> {
            updateCreneauxDisponibles();
        });

        // Initialiser les créneaux disponibles
        updateCreneauxDisponibles();
    }

    private void updateCreneauxDisponibles() {
        Date date = dateChooser.getDate();
        if (date != null) {
            try {
                List<Integer> creneauxLibres = controller.trouverCrenauxLibres(date);
                Integer[] heures = {6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 18, 19, 20, 21, 22};

                heureCombo.removeAllItems();
                for (Integer h : heures) {
                    if (creneauxLibres.contains(h)) {
                        heureCombo.addItem(h);
                    }
                }

                if (heureCombo.getItemCount() == 0) {
                    JOptionPane.showMessageDialog(this,
                            "Aucun créneau disponible pour cette date. Veuillez choisir une autre date.",
                            "Aucun créneau", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e) {
                System.err.println("Erreur lors de la récupération des créneaux: " + e.getMessage());
            }
        }
    }

    private void proposerRDV() {
        try {
            Date date = dateChooser.getDate();
            if (date == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une date",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer heure = (Integer) heureCombo.getSelectedItem();
            if (heure == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une heure disponible",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idDossier == null) {
                JOptionPane.showMessageDialog(this, "ID dossier manquant",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier à nouveau la disponibilité
            if (!controller.estCreneauDisponible(date, heure)) {
                JOptionPane.showMessageDialog(this,
                        "Ce créneau n'est plus disponible. Veuillez en choisir un autre.",
                        "Créneau occupé", JOptionPane.WARNING_MESSAGE);
                updateCreneauxDisponibles();
                return;
            }

            RDVDTO dto = new RDVDTO();
            dto.setDateRDV(date);
            dto.setHeure(new Time(heure, 0, 0));
            dto.setMotif(txtMotif.getText().trim());
            dto.setNoteMedecin(txtNote.getText().trim());
            dto.setIdDossier(idDossier);

            boolean success = controller.ajouterRDV(dto);

            if (success) {
                rdvCree = true;
                JOptionPane.showMessageDialog(this,
                        "Le rendez-vous a été proposé et enregistré avec succès!",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la création du RDV",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Indique si un RDV a été créé
     */
    public boolean isRDVCree() {
        return rdvCree;
    }
}




