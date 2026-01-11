package ma.prodenta.mvc.ui.facture;

import ma.prodenta.entities.En.Facture;
import ma.prodenta.repository.modules.Facture.api.FactureDao;
import ma.prodenta.repository.modules.Facture.impl.FactureDaoimpl;
import ma.prodenta.service.modules.facture.impl.FactureServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * FacturePanel - Panel for displaying and managing invoices (Factures)
 * Displays all factures from the database with search, filter, and action capabilities
 */
public class FacturePanel extends JPanel {
    
    private JTable factureTable;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JTextField searchField;
    private JComboBox<String> statusFilter;
    private FactureServiceImpl factureService;
    private SimpleDateFormat dateFormat;

    public FacturePanel() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        initializeService();
        setupUI();
    }

    private void initializeService() {
        try {
            FactureDao factureDao = new FactureDaoimpl();
            this.factureService = new FactureServiceImpl(factureDao);
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

        searchField = new JTextField("Rechercher par ID...", 15);
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
        tablePanel.setBorder(new TitledBorder("Liste des Factures"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Total", "Payé", "Reste", "Statut", "Date", "ID Situation"};
        tableModel = new DefaultTableModel(new Object[0][], columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        factureTable = new JTable(tableModel);
        factureTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        factureTable.setRowHeight(25);
        factureTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(factureTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);

        createButton = new JButton("Créer Facture");
        createButton.addActionListener(e -> openCreateFactureDialog());

        editButton = new JButton("Modifier");
        editButton.addActionListener(e -> openEditFactureDialog());

        deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> deleteFacture());

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private void loadDataFromDatabase() {
        try {
            List<Facture> factures = factureService.findAll();
            tableModel.setRowCount(0);

            for (Facture facture : factures) {
                String statut = facture.getStatut() != null ? facture.getStatut() : "N/A";
                String dateStr = facture.getDateFact() != null ? 
                    dateFormat.format(facture.getDateFact()) : "N/A";
                
                Object[] row = {
                    facture.getIdFact(),
                    String.format("%.2f DH", facture.getTotal() != null ? facture.getTotal() : 0.0),
                    String.format("%.2f DH", facture.getTotalpaye() != null ? facture.getTotalpaye() : 0.0),
                    String.format("%.2f DH", facture.getReste() != null ? facture.getReste() : 0.0),
                    statut,
                    dateStr,
                    facture.getIdSF() != null ? facture.getIdSF() : "N/A"
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

    private void openCreateFactureDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Créer une Nouvelle Facture");
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel totalLabel = new JLabel("Total:");
        JTextField totalField = new JTextField();
        panel.add(totalLabel);
        panel.add(totalField);

        JLabel payeLabel = new JLabel("Montant Payé:");
        JTextField payeField = new JTextField();
        panel.add(payeLabel);
        panel.add(payeField);

        JLabel statutLabel = new JLabel("Statut:");
        JComboBox<String> statutCombo = new JComboBox<>(new String[]{"payee", "non payee", "en attente", "annulé"});
        panel.add(statutLabel);
        panel.add(statutCombo);

        JLabel idSFLabel = new JLabel("ID Situation Financière:");
        JTextField idSFField = new JTextField();
        panel.add(idSFLabel);
        panel.add(idSFField);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            try {
                double total = Double.parseDouble(totalField.getText());
                double paye = Double.parseDouble(payeField.getText());
                String statut = (String) statutCombo.getSelectedItem();
                int idSF = Integer.parseInt(idSFField.getText());

                Facture facture = Facture.builder()
                    .total(total)
                    .totalpaye(paye)
                    .statut(statut)
                    .dateFact(new Date())
                    .idSF(idSF)
                    .build();

                if (factureService.create(facture)) {
                    JOptionPane.showMessageDialog(dialog, "Facture créée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                    dialog.dispose();
                }
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

    private void openEditFactureDialog() {
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une facture", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idFact = (Integer) tableModel.getValueAt(selectedRow, 0);
        JDialog dialog = new JDialog();
        dialog.setTitle("Modifier la Facture");
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        try {
            Facture facture = factureService.findById(idFact);
            
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(7, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel totalLabel = new JLabel("Total:");
            JTextField totalField = new JTextField(facture.getTotal() != null ? facture.getTotal().toString() : "");
            panel.add(totalLabel);
            panel.add(totalField);

            JLabel payeLabel = new JLabel("Montant Payé:");
            JTextField payeField = new JTextField(facture.getTotalpaye() != null ? facture.getTotalpaye().toString() : "");
            panel.add(payeLabel);
            panel.add(payeField);

            JLabel statutLabel = new JLabel("Statut:");
            JComboBox<String> statutCombo = new JComboBox<>(new String[]{"payee", "non payee", "en attente", "annulé"});
            statutCombo.setSelectedItem(facture.getStatut());
            panel.add(statutLabel);
            panel.add(statutCombo);

            JLabel idSFLabel = new JLabel("ID Situation Financière:");
            JTextField idSFField = new JTextField(facture.getIdSF() != null ? facture.getIdSF().toString() : "");
            panel.add(idSFLabel);
            panel.add(idSFField);

            JButton saveButton = new JButton("Enregistrer");
            saveButton.addActionListener(e -> {
                try {
                    facture.setTotal(Double.parseDouble(totalField.getText()));
                    facture.setTotalpaye(Double.parseDouble(payeField.getText()));
                    facture.setStatut((String) statutCombo.getSelectedItem());
                    facture.setIdSF(Integer.parseInt(idSFField.getText()));

                    factureService.update(facture);
                    JOptionPane.showMessageDialog(dialog, "Facture mise à jour avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                    dialog.dispose();
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

    private void deleteFacture() {
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une facture", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette facture?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Integer idFact = (Integer) tableModel.getValueAt(selectedRow, 0);
                if (factureService.deleteById(idFact)) {
                    JOptionPane.showMessageDialog(this, "Facture supprimée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
