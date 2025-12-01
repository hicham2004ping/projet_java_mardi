package ma.prodenta.mvc.ui.dossier;

import ma.prodenta.config.ApplicationContext;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.dto.dossiermedical.DossierMedicalDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 gestion des dossiers médicaux.
 */
public class DossierMedicalFrame extends JFrame {

    private final DossierMedicalController controller;

    private JTextField patientIdField;
    private JTextField dossierIdField;
    private JTextArea allergiesArea;
    private JTextArea antecedentsArea;
    private JTextArea notesArea;

    private JTable table;

    public DossierMedicalFrame(JFrame parentDashboard) {
        this.controller = ApplicationContext.getDossierMedicalController(); // À ajouter dans ApplicationContext
        initComponents();
        setLocationRelativeTo(parentDashboard);
    }

    private void initComponents() {
        setTitle("Gestion des dossiers médicaux");
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Top panel (filtre par patient)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("ID Patient :"));
        patientIdField = new JTextField(10);
        topPanel.add(patientIdField);

        JButton loadButton = new JButton("Charger les dossiers");
        loadButton.addActionListener(this::onLoadDossiers);
        topPanel.add(loadButton);

        add(topPanel, BorderLayout.NORTH);

        // Centre : tableau des dossiers
        table = new JTable(new DefaultTableModel(
                new Object[]{"ID", "Allergies", "Antécédents", "Notes"}, 0));
        table.getSelectionModel().addListSelectionListener(e -> onTableSelectionChanged());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel de formulaire à droite
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        dossierIdField = new JTextField(10);
        dossierIdField.setEditable(false);

        allergiesArea = new JTextArea(3, 20);
        antecedentsArea = new JTextArea(3, 20);
        notesArea = new JTextArea(5, 20);

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("ID Dossier :"), gbc);
        gbc.gridx = 1; formPanel.add(dossierIdField, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Allergies :"), gbc);
        gbc.gridx = 1; formPanel.add(new JScrollPane(allergiesArea), gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Antécédents :"), gbc);
        gbc.gridx = 1; formPanel.add(new JScrollPane(antecedentsArea), gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y; formPanel.add(new JLabel("Notes :"), gbc);
        gbc.gridx = 1; formPanel.add(new JScrollPane(notesArea), gbc);

        // Boutons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(this::onSaveDossier);
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(this::onDeleteDossier);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(deleteButton);

        y++;
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        formPanel.add(buttonsPanel, gbc);

        add(formPanel, BorderLayout.EAST);
    }

    private void onLoadDossiers(ActionEvent e) {
        try {
            Long patientId = Long.parseLong(patientIdField.getText());
            List<DossierMedicalDto> dossiers = controller.loadDossiersForPatient(patientId, this);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (DossierMedicalDto d : dossiers) {
                model.addRow(new Object[]{
                        d.getId(),
                        d.getAllergies(),
                        d.getAntecedents(),
                        d.getNotes()
                });
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez saisir un ID patient valide.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onSaveDossier(ActionEvent e) {
        try {
            Long patientId = Long.parseLong(patientIdField.getText());

            DossierMedicalDto dto = new DossierMedicalDto();
            dto.setPatientId(patientId);

            if (!dossierIdField.getText().isEmpty()) {
                dto.setId(Long.parseLong(dossierIdField.getText()));
            }

            dto.setAllergies(allergiesArea.getText());
            dto.setAntecedents(antecedentsArea.getText());
            dto.setNotes(notesArea.getText());

            controller.saveOrUpdateDossier(dto, this);
            onLoadDossiers(null); // refresh
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez saisir un ID patient valide.",
                    "Erreur de saisie",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onDeleteDossier(ActionEvent e) {
        if (dossierIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un dossier à supprimer.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Long id = Long.parseLong(dossierIdField.getText());
        controller.deleteDossier(id, this);
        onLoadDossiers(null);
        clearForm();
    }

    private void onTableSelectionChanged() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            dossierIdField.setText(String.valueOf(model.getValueAt(row, 0)));
            allergiesArea.setText(String.valueOf(model.getValueAt(row, 1)));
            antecedentsArea.setText(String.valueOf(model.getValueAt(row, 2)));
            notesArea.setText(String.valueOf(model.getValueAt(row, 3)));
        }
    }

    private void clearForm() {
        dossierIdField.setText("");
        allergiesArea.setText("");
        antecedentsArea.setText("");
        notesArea.setText("");
    }
}

