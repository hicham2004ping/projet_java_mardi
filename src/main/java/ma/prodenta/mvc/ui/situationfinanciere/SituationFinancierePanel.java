package ma.prodenta.mvc.ui.situationfinanciere;

import ma.prodenta.entities.En.SituationFinanciere;
import ma.prodenta.repository.modules.SituationFinanciere.impl.SituationFinanciereDaoImpl;
import ma.prodenta.service.modules.situationfinanciere.impl.SituationFinanciereServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * SituationFinancierePanel - Panel for displaying and managing financial situations
 * Displays all situations with search, filter, and full CRUD capabilities
 */
public class SituationFinancierePanel extends JPanel {
    
    private JTable situationTable;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private SituationFinanciereServiceImpl situationService;
    private Integer patientId; // Pour filtrer par patient

    public SituationFinancierePanel(Integer patientId) {
        this.patientId = patientId;
        initializeService();
        setupUI();
    }

    private void initializeService() {
        try {
            this.situationService = new SituationFinanciereServiceImpl(new SituationFinanciereDaoImpl());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur lors de l'initialisation du service: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        add(createSearchPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadDataFromDatabase();
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new TitledBorder("Recherche et Filtrage"));

        searchField = new JTextField("Rechercher par ID patient...", 15);
        searchPanel.add(new JLabel("Recherche:"));
        searchPanel.add(searchField);

        statusFilter = new JComboBox<>(new String[]{"Tous", "payee", "non payee", "en attente", "annulé"});
        searchPanel.add(new JLabel("Statut:"));
        searchPanel.add(statusFilter);

        refreshButton = new JButton("Rafraîchir");
        refreshButton.addActionListener(e -> {
            loadDataFromDatabase();
            JOptionPane.showMessageDialog(this, "Données actualisées", "Succès", JOptionPane.INFORMATION_MESSAGE);
        });
        searchPanel.add(refreshButton);

        return searchPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Liste des Situations Financières"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Total Actes", "Total Payé", "Crédit", "Statut", "En Promo", "Patient ID"};
        tableModel = new DefaultTableModel(new Object[0][], columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        situationTable = new JTable(tableModel);
        situationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        situationTable.setRowHeight(25);
        situationTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(situationTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);

        createButton = new JButton("➕ Ajouter");
        createButton.addActionListener(e -> openCreateDialog());

        editButton = new JButton("✏️ Modifier");
        editButton.addActionListener(e -> openEditDialog());

        deleteButton = new JButton("🗑️ Supprimer");
        deleteButton.addActionListener(e -> deleteSituation());

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private void loadDataFromDatabase() {
        try {
            List<SituationFinanciere> situations;
            
            // Si un patient est spécifié, charger uniquement ses situations
            if (patientId != null) {
                situations = situationService.findByPatient(patientId);
            } else {
                situations = situationService.findAll();
            }
            
            tableModel.setRowCount(0);

            for (SituationFinanciere sf : situations) {
                Object[] row = {
                    sf.getIdSF(),
                    String.format("%.2f DH", sf.getTotalActes() != null ? sf.getTotalActes() : 0.0),
                    String.format("%.2f DH", sf.getTotalPaye() != null ? sf.getTotalPaye() : 0.0),
                    String.format("%.2f DH", sf.getCredit() != null ? sf.getCredit() : 0.0),
                    sf.getStatut() != null ? sf.getStatut() : "N/A",
                    sf.getEnPromo() != null ? sf.getEnPromo() : "Non",
                    sf.getIdPatient() != null ? sf.getIdPatient() : "N/A"
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des données: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void openCreateDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Créer une Nouvelle Situation Financière");
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel patientLabel = new JLabel("Patient ID:");
        JTextField patientField = new JTextField(patientId != null ? patientId.toString() : "");
        if (patientId != null) patientField.setEditable(false);
        panel.add(patientLabel);
        panel.add(patientField);

        JLabel totalActesLabel = new JLabel("Total Actes:");
        JTextField totalActesField = new JTextField();
        panel.add(totalActesLabel);
        panel.add(totalActesField);

        JLabel totalPayeLabel = new JLabel("Total Payé:");
        JTextField totalPayeField = new JTextField();
        panel.add(totalPayeLabel);
        panel.add(totalPayeField);

        JLabel creditLabel = new JLabel("Crédit (automatique):");
        JTextField creditField = new JTextField();
        creditField.setEditable(false);
        panel.add(creditLabel);
        panel.add(creditField);

        JLabel statutLabel = new JLabel("Statut:");
        JComboBox<String> statutCombo = new JComboBox<>(new String[]{"payee", "non payee", "en attente", "annulé"});
        panel.add(statutLabel);
        panel.add(statutCombo);

        JLabel promoLabel = new JLabel("En Promo:");
        JComboBox<String> promoCombo = new JComboBox<>(new String[]{"Non", "Oui"});
        panel.add(promoLabel);
        panel.add(promoCombo);

        // Calculer le crédit automatiquement
        totalActesField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                calculerCredit(totalActesField, totalPayeField, creditField);
            }
        });

        totalPayeField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                calculerCredit(totalActesField, totalPayeField, creditField);
            }
        });

        JButton saveButton = new JButton("💾 Enregistrer");
        saveButton.addActionListener(e -> {
            try {
                if (patientField.getText().trim().isEmpty() || totalActesField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Veuillez remplir tous les champs obligatoires", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idPatient = Integer.parseInt(patientField.getText().trim());
                double totalActes = Double.parseDouble(totalActesField.getText().trim());
                double totalPaye = Double.parseDouble(totalPayeField.getText().trim());
                String statut = (String) statutCombo.getSelectedItem();
                String enPromo = (String) promoCombo.getSelectedItem();

                SituationFinanciere situation = SituationFinanciere.builder()
                    .totalActes(totalActes)
                    .totalPaye(totalPaye)
                    .statut(statut)
                    .enPromo(enPromo)
                    .idPatient(idPatient)
                    .build();

                if (situationService.create(situation)) {
                    JOptionPane.showMessageDialog(dialog, "Situation financière créée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                    dialog.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Veuillez entrer des nombres valides", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> dialog.dispose());

        panel.add(saveButton);
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void openEditDialog() {
        int selectedRow = situationTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une situation financière", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idSF = (Integer) tableModel.getValueAt(selectedRow, 0);
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Modifier la Situation Financière");
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);

        try {
            SituationFinanciere situation = situationService.findById(idSF);
            
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(8, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel patientLabel = new JLabel("Patient ID:");
            JTextField patientField = new JTextField(situation.getIdPatient() != null ? situation.getIdPatient().toString() : "");
            patientField.setEditable(false);
            panel.add(patientLabel);
            panel.add(patientField);

            JLabel totalActesLabel = new JLabel("Total Actes:");
            JTextField totalActesField = new JTextField(situation.getTotalActes() != null ? situation.getTotalActes().toString() : "0");
            panel.add(totalActesLabel);
            panel.add(totalActesField);

            JLabel totalPayeLabel = new JLabel("Total Payé:");
            JTextField totalPayeField = new JTextField(situation.getTotalPaye() != null ? situation.getTotalPaye().toString() : "0");
            panel.add(totalPayeLabel);
            panel.add(totalPayeField);

            JLabel creditLabel = new JLabel("Crédit (automatique):");
            JTextField creditField = new JTextField(situation.getCredit() != null ? situation.getCredit().toString() : "0");
            creditField.setEditable(false);
            panel.add(creditLabel);
            panel.add(creditField);

            JLabel statutLabel = new JLabel("Statut:");
            JComboBox<String> statutCombo = new JComboBox<>(new String[]{"payee", "non payee", "en attente", "annulé"});
            statutCombo.setSelectedItem(situation.getStatut());
            panel.add(statutLabel);
            panel.add(statutCombo);

            JLabel promoLabel = new JLabel("En Promo:");
            JComboBox<String> promoCombo = new JComboBox<>(new String[]{"Non", "Oui"});
            promoCombo.setSelectedItem(situation.getEnPromo());
            panel.add(promoLabel);
            panel.add(promoCombo);

            // Calculer le crédit automatiquement
            totalActesField.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent e) {
                    calculerCredit(totalActesField, totalPayeField, creditField);
                }
            });

            totalPayeField.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent e) {
                    calculerCredit(totalActesField, totalPayeField, creditField);
                }
            });

            JButton saveButton = new JButton("💾 Enregistrer");
            saveButton.addActionListener(e -> {
                try {
                    situation.setTotalActes(Double.parseDouble(totalActesField.getText().trim()));
                    situation.setTotalPaye(Double.parseDouble(totalPayeField.getText().trim()));
                    situation.setStatut((String) statutCombo.getSelectedItem());
                    situation.setEnPromo((String) promoCombo.getSelectedItem());

                    situationService.update(situation);
                    JOptionPane.showMessageDialog(dialog, "Situation financière mise à jour avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Veuillez entrer des nombres valides", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            JButton cancelButton = new JButton("Annuler");
            cancelButton.addActionListener(e -> dialog.dispose());

            panel.add(saveButton);
            panel.add(cancelButton);

            dialog.add(panel);
            dialog.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSituation() {
        int selectedRow = situationTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une situation financière", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer cette situation financière?\nCeci peut affecter les factures associées.", 
            "Confirmation de suppression", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer idSF = (Integer) tableModel.getValueAt(selectedRow, 0);
                if (situationService.deleteById(idSF)) {
                    JOptionPane.showMessageDialog(this, "Situation financière supprimée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void calculerCredit(JTextField totalActesField, JTextField totalPayeField, JTextField creditField) {
        try {
            if (!totalActesField.getText().trim().isEmpty() && !totalPayeField.getText().trim().isEmpty()) {
                double totalActes = Double.parseDouble(totalActesField.getText().trim());
                double totalPaye = Double.parseDouble(totalPayeField.getText().trim());
                double credit = totalActes - totalPaye;
                creditField.setText(String.format("%.2f", credit));
            }
        } catch (NumberFormatException e) {
            // Ignorer les erreurs de parsing
        }
    }
}
