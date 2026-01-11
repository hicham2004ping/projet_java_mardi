package ma.prodenta.mvc.ui.caisse;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Charges;
import ma.prodenta.repository.modules.Caisse.implementation.ChargesRepositoryImpl;
import ma.prodenta.service.modules.caisse.impl.ChargesServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * CaissePanel - Panel for managing cash register and charges (expenses)
 * Allows creation, modification, and deletion of charges from the database
 */
public class CaissePanel extends JPanel {
    
    private JTable chargesTable;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JTextField searchField;
    private JLabel totalChargesLabel;
    private JLabel totalRevenusLabel;
    private ChargesServiceImpl chargesService;
    private SimpleDateFormat dateFormat;

    public CaissePanel() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        initializeService();
        setupUI();
    }

    private void initializeService() {
        try {
            Connection conn = SessionFactory.getInstance().getConnection();
            ChargesRepositoryImpl chargesRepository = new ChargesRepositoryImpl(conn);
            this.chargesService = new ChargesServiceImpl(chargesRepository);
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

        add(createSummaryPanel(), BorderLayout.NORTH);
        add(createSearchPanel(), BorderLayout.PAGE_START);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadDataFromDatabase();
    }

    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        summaryPanel.setBackground(new Color(240, 240, 240));
        summaryPanel.setBorder(new TitledBorder("Résumé Caisse"));

        JLabel revenuLabel = new JLabel("Revenus:");
        revenuLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalRevenusLabel = new JLabel("0.00 DH");
        totalRevenusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalRevenusLabel.setForeground(new Color(0, 150, 0));

        JLabel chargesLabel = new JLabel("Dépenses:");
        chargesLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        totalChargesLabel = new JLabel("0.00 DH");
        totalChargesLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalChargesLabel.setForeground(new Color(200, 0, 0));

        summaryPanel.add(revenuLabel);
        summaryPanel.add(totalRevenusLabel);
        summaryPanel.add(new JSeparator(JSeparator.VERTICAL));
        summaryPanel.add(chargesLabel);
        summaryPanel.add(totalChargesLabel);

        return summaryPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(new TitledBorder("Recherche"));

        searchField = new JTextField("Rechercher...", 20);
        searchPanel.add(new JLabel("Recherche:"));
        searchPanel.add(searchField);

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
        tablePanel.setBorder(new TitledBorder("Liste des Charges"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Titre", "Description", "Montant", "Date", "Cabinet ID"};
        tableModel = new DefaultTableModel(new Object[0][], columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        chargesTable = new JTable(tableModel);
        chargesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chargesTable.setRowHeight(25);
        chargesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(chargesTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);

        createButton = new JButton("Ajouter Charge");
        createButton.addActionListener(e -> openCreateChargeDialog());

        editButton = new JButton("Modifier");
        editButton.addActionListener(e -> openEditChargeDialog());

        deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> deleteCharge());

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private void loadDataFromDatabase() {
        try {
            List<Charges> chargesList = chargesService.findAll();
            tableModel.setRowCount(0);

            for (Charges charge : chargesList) {
                String dateStr = charge.getDateCharge() != null ?
                    dateFormat.format(charge.getDateCharge()) : "N/A";

                Object[] row = {
                    charge.getIdCharge(),
                    charge.getTitre(),
                    charge.getDescription(),
                    String.format("%.2f DH", charge.getMontant() != null ? charge.getMontant() : 0.0),
                    dateStr,
                    charge.getIdCabinet() != null ? charge.getIdCabinet() : "N/A"
                };
                tableModel.addRow(row);
            }

            updateSummary(chargesList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des données: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateSummary(List<Charges> chargesList) {
        double totalCharges = 0;
        for (Charges charge : chargesList) {
            if (charge.getMontant() != null) {
                totalCharges += charge.getMontant();
            }
        }
        
        totalChargesLabel.setText(String.format("%.2f DH", totalCharges));
        totalRevenusLabel.setText("0.00 DH");
    }

    private void openCreateChargeDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Ajouter une Nouvelle Charge");
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titreLabel = new JLabel("Titre:");
        JTextField titreField = new JTextField();
        panel.add(titreLabel);
        panel.add(titreField);

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();
        panel.add(descriptionLabel);
        panel.add(descriptionField);

        JLabel montantLabel = new JLabel("Montant:");
        JTextField montantField = new JTextField();
        panel.add(montantLabel);
        panel.add(montantField);

        JLabel cabinetLabel = new JLabel("Cabinet ID:");
        JTextField cabinetField = new JTextField("1");
        panel.add(cabinetLabel);
        panel.add(cabinetField);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            try {
                Charges charge = Charges.builder()
                    .titre(titreField.getText())
                    .description(descriptionField.getText())
                    .montant(Double.parseDouble(montantField.getText()))
                    .dateCharge(new Date())
                    .idCabinet(Integer.parseInt(cabinetField.getText()))
                    .build();

                chargesService.create(charge);
                JOptionPane.showMessageDialog(dialog, "Charge créée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
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
    }

    private void openEditChargeDialog() {
        int selectedRow = chargesTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une charge", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long idCharge = (Long) tableModel.getValueAt(selectedRow, 0);
        JDialog dialog = new JDialog();
        dialog.setTitle("Modifier la Charge");
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        try {
            Charges charge = chargesService.findById(Math.toIntExact(idCharge));

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel titreLabel = new JLabel("Titre:");
            JTextField titreField = new JTextField(charge.getTitre());
            panel.add(titreLabel);
            panel.add(titreField);

            JLabel descriptionLabel = new JLabel("Description:");
            JTextField descriptionField = new JTextField(charge.getDescription());
            panel.add(descriptionLabel);
            panel.add(descriptionField);

            JLabel montantLabel = new JLabel("Montant:");
            JTextField montantField = new JTextField(charge.getMontant().toString());
            panel.add(montantLabel);
            panel.add(montantField);

            JLabel cabinetLabel = new JLabel("Cabinet ID:");
            JTextField cabinetField = new JTextField(charge.getIdCabinet() != null ? charge.getIdCabinet().toString() : "1");
            panel.add(cabinetLabel);
            panel.add(cabinetField);

            JButton saveButton = new JButton("Enregistrer");
            saveButton.addActionListener(e -> {
                try {
                    charge.setTitre(titreField.getText());
                    charge.setDescription(descriptionField.getText());
                    charge.setMontant(Double.parseDouble(montantField.getText()));
                    charge.setIdCabinet(Integer.parseInt(cabinetField.getText()));

                    chargesService.update(charge);
                    JOptionPane.showMessageDialog(dialog, "Charge mise à jour avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
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

    private void deleteCharge() {
        int selectedRow = chargesTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une charge", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette charge?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Long idCharge = (Long) tableModel.getValueAt(selectedRow, 0);
                chargesService.delete(Math.toIntExact(idCharge));
                JOptionPane.showMessageDialog(this, "Charge supprimée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadDataFromDatabase();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
