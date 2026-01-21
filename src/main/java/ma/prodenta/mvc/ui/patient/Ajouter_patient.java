package ma.prodenta.mvc.ui.patient;

import ma.prodenta.common.exceptions.*;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.mvc.controllers.modules.patient.impl.Patient_Controlleur;
import ma.prodenta.mvc.dto.patient.PatientDTO;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.service.modules.antecedent.impl.Antecedent_Service_ServiceImpl;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ajouter_patient extends JPanel {

    private Dashboard_view view;
    private Antecedent_Service_ServiceImpl antecedentService;
    private JDateChooser dateChooser;
    private JTextField nom, prenom, email, telephone, adresse;
    private JComboBox<String> sexeComboBox, assuranceComboBox;
    private JTable antecedentTable;
    private JButton enregistrer, clear;

    public Ajouter_patient(Dashboard_view view) throws Exception {
        this.view = view;
        this.antecedentService = Application_contexte.getAntecedent_Service();

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout(20, 20));

        // Titre principal
        JPanel titlePanel = createTitlePanel();

        // Panel principal avec formulaire et antécédents
        JPanel mainPanel = createMainPanel();

        add(titlePanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Ajouter un Nouveau Patient");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));

        titlePanel.add(titleLabel);
        return titlePanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Formulaire des informations patient
        JPanel formPanel = createFormPanel();

        // Table des antécédents
        JPanel antecedentPanel = createAntecedentPanel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.weighty = 1;
        mainPanel.add(formPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.55;
        mainPanel.add(antecedentPanel, gbc);

        return mainPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout(10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                        "Informations du Patient",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 16),
                        new Color(0, 102, 204)
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Panel des champs
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Initialisation des champs
        nom = createStyledTextField();
        prenom = createStyledTextField();
        email = createStyledTextField();
        telephone = createStyledTextField();
        adresse = createStyledTextField();
        dateChooser = createStyledDateChooser();

        // Création des combobox pour sexe et assurance
        sexeComboBox = createStyledComboBox();
        assuranceComboBox = createStyledComboBox();

        // Remplissage des combobox
        sexeComboBox.addItem("-- Sélectionnez le sexe --");
        for (Sexe s : Sexe.values()) {
            sexeComboBox.addItem(s.name());
        }

        assuranceComboBox.addItem("-- Sans assurance --");
        for (Assurance a : Assurance.values()) {
            assuranceComboBox.addItem(a.name());
        }

        int row = 0;

        // Ajout des champs avec des labels stylisés
        addStyledField(fieldsPanel, gbc, row++, "Nom *:", nom);
        addStyledField(fieldsPanel, gbc, row++, "Prénom *:", prenom);
        addStyledField(fieldsPanel, gbc, row++, "Date de naissance *:", dateChooser);
        addStyledField(fieldsPanel, gbc, row++, "Email:", email);
        addStyledField(fieldsPanel, gbc, row++, "Téléphone *:", telephone);
        addStyledField(fieldsPanel, gbc, row++, "Adresse:", adresse);
        addStyledField(fieldsPanel, gbc, row++, "Sexe *:", sexeComboBox);
        addStyledField(fieldsPanel, gbc, row++, "Assurance:", assuranceComboBox);

        // Boutons
        JPanel buttonPanel = createButtonPanel();

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        fieldsPanel.add(buttonPanel, gbc);

        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        return formPanel;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, 35));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == 0) {
                    setFont(getFont().deriveFont(Font.ITALIC));
                    setForeground(Color.GRAY);
                } else {
                    setFont(getFont().deriveFont(Font.PLAIN));
                    setForeground(Color.BLACK);
                }
                return c;
            }
        });
        return comboBox;
    }

    private JPanel createAntecedentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(102, 153, 0), 2),
                        "Antécédents Médicaux",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI", Font.BOLD, 16),
                        new Color(102, 153, 0)
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Id", "Nom", "Catégorie", "Niveau Risque"}, 0);

        antecedentTable = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                } else {
                    c.setBackground(new Color(220, 240, 220)); // Vert clair pour la sélection
                    c.setForeground(Color.BLACK);
                }

                // Coloration par niveau de risque
                String risque = getValueAt(row, 3).toString().toLowerCase();
                if (risque.contains("trésdancreux") || risque.contains("dangereux")) {
                    c.setForeground(new Color(204, 0, 0)); // Rouge pour les risques élevés
                } else if (risque.contains("modéré")) {
                    c.setForeground(new Color(255, 153, 0)); // Orange pour modéré
                }

                return c;
            }
        };

        antecedentTable.setRowHeight(30);
        antecedentTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        antecedentTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        antecedentTable.setGridColor(new Color(230, 230, 230));

        // Style de l'en-tête
        antecedentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        antecedentTable.getTableHeader().setBackground(new Color(102, 153, 0));
        antecedentTable.getTableHeader().setForeground(Color.WHITE);
        antecedentTable.getTableHeader().setReorderingAllowed(false);

        try {
            for (Antecedent a : antecedentService.findAll()) {
                model.addRow(new Object[]{
                        a.getIdAntecedent(),
                        a.getNom(),
                        a.getCategorie(),
                        a.getNiveauRisque().name()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des antécédents",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        // Masquer colonne ID
        TableColumn idColumn = antecedentTable.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setPreferredWidth(0);

        JScrollPane tableScroll = new JScrollPane(antecedentTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Instructions
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);

        JLabel infoLabel = new JLabel("<html><b>Instructions :</b> Sélectionnez un ou plusieurs antécédents</html>");
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        infoLabel.setForeground(new Color(60, 60, 60));

        JLabel shortcutLabel = new JLabel("(Ctrl+Click pour sélection multiple)");
        shortcutLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        shortcutLabel.setForeground(new Color(100, 100, 100));

        infoPanel.add(infoLabel, BorderLayout.NORTH);
        infoPanel.add(shortcutLabel, BorderLayout.SOUTH);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        enregistrer = createStyledButton("Enregistrer", new Color(0, 102, 204));
        clear = createStyledButton("Effacer", new Color(153, 153, 153));

        // Ajout des écouteurs
        setupEventListeners();

        buttonPanel.add(enregistrer);
        buttonPanel.add(clear);

        return buttonPanel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 35));
        return field;
    }

    private JDateChooser createStyledDateChooser() {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateChooser.getDateEditor().getUiComponent().setBackground(Color.WHITE);
        dateChooser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(3, 5, 3, 5)
        ));
        dateChooser.setPreferredSize(new Dimension(dateChooser.getPreferredSize().width, 35));
        return dateChooser;
    }

    private void addStyledField(JPanel panel, GridBagConstraints gbc, int row,
                                String label, JComponent field) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel.setForeground(new Color(60, 60, 60));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void setupEventListeners() {
        enregistrer.addActionListener(e -> handleSave());
        clear.addActionListener(e -> handleClear());
    }

    private void handleSave() {
        Patient_Controlleur controller = Application_contexte.getPatientControlleur();
        List<Antecedent> list = new ArrayList<>();

        // Récupération des antécédents sélectionnés
        for (int rowIndex : antecedentTable.getSelectedRows()) {
            int id = Integer.parseInt(antecedentTable.getValueAt(rowIndex, 0).toString());
            try {
                list.add(antecedentService.findById(id));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors du chargement des antécédents",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Récupération du sexe
        Sexe sexe1 = null;
        String selectedSexe = (String) sexeComboBox.getSelectedItem();
        if (selectedSexe != null && !selectedSexe.equals("-- Sélectionnez le sexe --")) {
            sexe1 = Sexe.valueOf(selectedSexe);
        }

        // Récupération de l'assurance
        Assurance assurance1 = null;
        String selectedAssurance = (String) assuranceComboBox.getSelectedItem();
        if (selectedAssurance != null && !selectedAssurance.equals("-- Sans assurance --")) {
            assurance1 = Assurance.valueOf(selectedAssurance);
        }

        try {
            // Validation des champs obligatoires
            if (nom.getText().trim().isEmpty() || prenom.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Les champs Nom et Prénom sont obligatoires",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (sexe1 == null) {
                JOptionPane.showMessageDialog(this,
                        "Le sexe est obligatoire",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Date date = dateChooser.getDate();
            if (date == null) {
                JOptionPane.showMessageDialog(this,
                        "La date de naissance est obligatoire",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            controller.creation_patient(
                    1,
                    nom.getText().trim(),
                    prenom.getText().trim(),
                    localDate,
                    adresse.getText().trim(),
                    email.getText().trim(),
                    telephone.getText().trim(),
                    sexe1.name(),
                    assurance1 != null ? assurance1.name() : null,
                    list
            );

            Patient patient = controller.find_by_email(email.getText().trim());
            PatientDTO patientDTO = PatientDTO.patientParseDto(patient);
            view.getAfficherPatient().ajouter_patient_ligne(patientDTO);

            JOptionPane.showMessageDialog(this,
                    "Le patient a été ajouté avec succès",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

            // Réinitialisation du formulaire
            handleClear();

        } catch (ArgumentException | EmailInvalideException | EmailExisteException |
                 Date_Naissance_Exception | ErreurLectureException | SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de la création: " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleClear() {
        nom.setText("");
        prenom.setText("");
        email.setText("");
        telephone.setText("");
        adresse.setText("");
        dateChooser.setDate(null);
        antecedentTable.clearSelection();
        sexeComboBox.setSelectedIndex(0);
        assuranceComboBox.setSelectedIndex(0);
    }
}