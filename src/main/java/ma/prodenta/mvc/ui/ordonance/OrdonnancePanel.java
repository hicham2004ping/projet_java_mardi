package ma.prodenta.mvc.ui.ordonance;

import ma.prodenta.entities.En.Medicament;
import ma.prodenta.entities.En.Ordonnance;
import ma.prodenta.repository.modules.ordonnance.impl.OrdonnanceDaoImpl;
import ma.prodenta.service.modules.ordonnance.impl.OrdonnanceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * OrdonnancePanel - Panel for managing prescriptions (Ordonnances)
 * Displays all ordonnances from the database with search and details capabilities
 */
public class OrdonnancePanel extends JPanel {
    
    private JTable ordonnanceTable;
    private JTable medicamentsTable;
    private DefaultTableModel ordonnanceTableModel;
    private DefaultTableModel medicamentsTableModel;
    private JButton viewDetailsButton;
    private JButton createButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JTextField searchField;
    private OrdonnanceImpl ordonnanceService;
    private DateTimeFormatter dateFormat;

    public OrdonnancePanel() {
        this.dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        initializeService();
        setupUI();
    }

    private void initializeService() {
        try {
            OrdonnanceDaoImpl ordonnanceDao = new OrdonnanceDaoImpl();
            this.ordonnanceService = new OrdonnanceImpl();
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
        searchPanel.setBorder(new TitledBorder("Recherche"));

        searchField = new JTextField("Rechercher par ID ou Dossier...", 20);
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
        tablePanel.setBorder(new TitledBorder("Ordonnances"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Date", "ID Dossier", "ID Consultation", "Total"};
        ordonnanceTableModel = new DefaultTableModel(new Object[0][], columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ordonnanceTable = new JTable(ordonnanceTableModel);
        ordonnanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordonnanceTable.setRowHeight(25);
        ordonnanceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateMedicamentsTable();
            }
        });

        JScrollPane scrollPane = new JScrollPane(ordonnanceTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(new TitledBorder("Médicaments de l'Ordonnance"));
        detailsPanel.setBackground(Color.WHITE);

        String[] medColumns = {"ID Médicament", "Nom", "Laboratoire", "Type", "Prix Unitaire"};
        medicamentsTableModel = new DefaultTableModel(new Object[0][], medColumns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        medicamentsTable = new JTable(medicamentsTableModel);
        medicamentsTable.setRowHeight(25);
        medicamentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane medScrollPane = new JScrollPane(medicamentsTable);
        detailsPanel.add(medScrollPane, BorderLayout.CENTER);

        JPanel splitPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        splitPanel.add(tablePanel);
        splitPanel.add(detailsPanel);

        return splitPanel;
    }

    private void updateMedicamentsTable() {
        int selectedRow = ordonnanceTable.getSelectedRow();
        medicamentsTableModel.setRowCount(0);

        if (selectedRow >= 0) {
            try {
                Long idOrd = (Long) ordonnanceTableModel.getValueAt(selectedRow, 0);
                Ordonnance ordonnance = ordonnanceService.findById(idOrd);
                
                if (ordonnance != null) {
                    List<Medicament> medicaments = ordonnanceService.medicamentsOrdonnance(ordonnance);
                    
                    for (Medicament med : medicaments) {
                        Object[] row = {
                            med.getIdMed(),
                            med.getNom(),
                            med.getLaboratoire(),
                            med.getType(),
                            String.format("%.2f DH", med.getPrixUnit() != null ? med.getPrixUnit() : 0.0)
                        };
                        medicamentsTableModel.addRow(row);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);

        viewDetailsButton = new JButton("Voir Détails");
        viewDetailsButton.addActionListener(e -> showOrdonnanceDetails());

        createButton = new JButton("Créer Ordonnance");
        createButton.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Création d'ordonnance disponible via la consultation médicale", 
            "Information", JOptionPane.INFORMATION_MESSAGE));

        deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> deleteOrdonnance());

        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private void loadDataFromDatabase() {
        try {
            List<Ordonnance> ordonnances = ordonnanceService.findAll();
            ordonnanceTableModel.setRowCount(0);

            for (Ordonnance ordonnance : ordonnances) {
                String dateStr = ordonnance.getDateOrd() != null ?
                    ordonnance.getDateOrd().format(dateFormat) : "N/A";
                
                int total = ordonnanceService.totalOrdonnance(ordonnance);
                
                Object[] row = {
                    ordonnance.getIdOrd(),
                    dateStr,
                    ordonnance.getIdDossier(),
                    ordonnance.getIdConsultation(),
                    String.format("%d DH", total)
                };
                ordonnanceTableModel.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des données: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void showOrdonnanceDetails() {
        int selectedRow = ordonnanceTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ordonnance", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Long idOrd = (Long) ordonnanceTableModel.getValueAt(selectedRow, 0);
            Ordonnance ordonnance = ordonnanceService.findById(idOrd);
            
            if (ordonnance != null) {
                StringBuilder details = new StringBuilder();
                details.append("ID Ordonnance: ").append(ordonnance.getIdOrd()).append("\n");
                details.append("Date: ").append(ordonnance.getDateOrd().format(dateFormat)).append("\n");
                details.append("ID Dossier: ").append(ordonnance.getIdDossier()).append("\n");
                details.append("ID Consultation: ").append(ordonnance.getIdConsultation()).append("\n");
                details.append("Total: ").append(ordonnanceService.totalOrdonnance(ordonnance)).append(" DH\n\n");
                
                List<Medicament> medicaments = ordonnanceService.medicamentsOrdonnance(ordonnance);
                details.append("Médicaments:\n");
                for (Medicament med : medicaments) {
                    details.append("- ").append(med.getNom())
                        .append(" (").append(med.getLaboratoire())
                        .append(") - ").append(String.format("%.2f DH", med.getPrixUnit()))
                        .append("\n");
                }
                
                JOptionPane.showMessageDialog(this, details.toString(), "Détails Ordonnance", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrdonnance() {
        int selectedRow = ordonnanceTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ordonnance", "Sélection requise", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette ordonnance?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Long idOrd = (Long) ordonnanceTableModel.getValueAt(selectedRow, 0);
                Ordonnance ordonnance = ordonnanceService.findById(idOrd);
                
                if (ordonnanceService.delete(ordonnance)) {// reje3tha boolean 7it katl3 bl 7mr :)
                    JOptionPane.showMessageDialog(this, "Ordonnance supprimée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadDataFromDatabase();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
