package ma.prodenta.mvc.ui.rdv;

import com.toedter.calendar.JDateChooser;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.controllers.modules.rdv.RDVController;
import ma.prodenta.mvc.dto.rdv.RDVDTO;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.Date;

public class RDVForm extends JDialog {

    private RDVController controller;
    private DossierMedicalController dossierController;
    private RDVFrame parent;
    private RDVDTO rdvDTO;

    private JDateChooser dateChooser;
    private JComboBox<Integer> heureCombo;
    private JTextField txtMotif;
    private JTextArea txtNoteMedecin;
    private JComboBox<DossierComboItem> dossierCombo;
    private JButton btnEnregistrer, btnAnnuler;

    public RDVForm(RDVFrame parent, RDVDTO rdvDTO) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), true);
        this.parent = parent;
        this.rdvDTO = rdvDTO;
        this.controller = new RDVController();
        this.dossierController = Application_contexte.getDossierMedicalController();

        setTitle(rdvDTO == null ? "Ajouter un RDV" : "Modifier un RDV");
        setSize(500, 450);
        setLocationRelativeTo(parent);

        initializeUI();
        if (rdvDTO != null) {
            loadData();
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations du RDV"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Date
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Date RDV:"), gbc);
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(dateChooser, gbc);

        // Heure
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Heure:"), gbc);
        Integer[] heures = {6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 18, 19, 20, 21, 22};
        heureCombo = new JComboBox<>(heures);
        heureCombo.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        formPanel.add(heureCombo, gbc);

        // Dossier médical
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Dossier médical:"), gbc);
        dossierCombo = new JComboBox<>();
        dossierCombo.setPreferredSize(new Dimension(200, 30));
        loadDossiers();
        gbc.gridx = 1;
        formPanel.add(dossierCombo, gbc);

        // Motif
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Motif:"), gbc);
        txtMotif = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(txtMotif, gbc);

        // Note médecin
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Note médecin:"), gbc);
        txtNoteMedecin = new JTextArea(4, 20);
        txtNoteMedecin.setLineWrap(true);
        txtNoteMedecin.setWrapStyleWord(true);
        JScrollPane scrollNote = new JScrollPane(txtNoteMedecin);
        scrollNote.setPreferredSize(new Dimension(200, 80));
        gbc.gridx = 1;
        formPanel.add(scrollNote, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnEnregistrer = new JButton("Enregistrer");
        btnAnnuler = new JButton("Annuler");
        buttonPanel.add(btnEnregistrer);
        buttonPanel.add(btnAnnuler);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        btnEnregistrer.addActionListener(e -> save());
        btnAnnuler.addActionListener(e -> dispose());
    }

    private void loadDossiers() {
        try {
            dossierCombo.removeAllItems();
            var dossiers = dossierController.find_all_view();
            for (var dossier : dossiers) {
                String displayText = "Dossier #" + dossier.getIdDossier() +
                        " - " + dossier.getPatient_nom() + " " + dossier.getPatient_prenom();
                dossierCombo.addItem(new DossierComboItem(dossier.getIdDossier(), displayText));
            }
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des dossiers: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Erreur lors du chargement des dossiers: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Classe interne pour stocker l'ID et l'affichage du dossier
     */
    private static class DossierComboItem {
        private Integer id;
        private String display;

        public DossierComboItem(Integer id, String display) {
            this.id = id;
            this.display = display;
        }

        public Integer getId() {
            return id;
        }

        @Override
        public String toString() {
            return display;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DossierComboItem) {
                return this.id.equals(((DossierComboItem) obj).id);
            }
            return false;
        }
    }

    private void loadData() {
        if (rdvDTO != null) {
            dateChooser.setDate(rdvDTO.getDateRDV());
            if (rdvDTO.getHeure() != null) {
                heureCombo.setSelectedItem(rdvDTO.getHeure().getHours());
            }
            txtMotif.setText(rdvDTO.getMotif());
            txtNoteMedecin.setText(rdvDTO.getNoteMedecin());
            if (rdvDTO.getIdDossier() != null) {
                // Trouver et sélectionner le dossier correspondant
                for (int i = 0; i < dossierCombo.getItemCount(); i++) {
                    DossierComboItem item = (DossierComboItem) dossierCombo.getItemAt(i);
                    if (item.getId().equals(rdvDTO.getIdDossier())) {
                        dossierCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }

    private void save() {
        try {
            Date date = dateChooser.getDate();
            if (date == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une date",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer heure = (Integer) heureCombo.getSelectedItem();
            if (heure == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une heure",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DossierComboItem selectedDossier = (DossierComboItem) dossierCombo.getSelectedItem();
            if (selectedDossier == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un dossier médical",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Integer idDossier = selectedDossier.getId();

            // Vérifier la disponibilité du créneau
            if (!controller.estCreneauDisponible(date, heure)) {
                JOptionPane.showMessageDialog(this,
                        "Ce créneau n'est pas disponible. Veuillez choisir un autre créneau.",
                        "Créneau occupé", JOptionPane.WARNING_MESSAGE);
                return;
            }

            RDVDTO dto = rdvDTO != null ? rdvDTO : new RDVDTO();
            dto.setDateRDV(date);
            dto.setHeure(new Time(heure, 0, 0));
            dto.setMotif(txtMotif.getText().trim());
            dto.setNoteMedecin(txtNoteMedecin.getText().trim());
            dto.setIdDossier(idDossier);

            boolean success;
            if (rdvDTO == null) {
                success = controller.ajouterRDV(dto);
            } else {
                success = controller.modifierRDV(dto);
            }

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "RDV " + (rdvDTO == null ? "ajouté" : "modifié") + " avec succès",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                parent.refresh();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'enregistrement du RDV",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}

