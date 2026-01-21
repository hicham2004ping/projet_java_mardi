package ma.prodenta.mvc.ui.caisse;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.entities.En.Charges;
import ma.prodenta.repository.modules.Caisse.implementation.ChargesRepositoryImpl;
import ma.prodenta.service.modules.caisse.impl.ChargesServiceImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

/**
 * CaissePanel - Panel moderne pour la gestion de la caisse
 */
public class CaissePanel extends JPanel {

    // Palette de couleurs
    private static final Color PRIMARY_COLOR = new Color(0, 150, 136);
    private static final Color ACCENT_COLOR = new Color(255, 152, 0);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color DANGER_COLOR = new Color(244, 67, 54);
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    private static final Color BORDER_COLOR = new Color(224, 224, 224);

    private JTable chargesTable;
    private DefaultTableModel tableModel;
    private JButton createButton, editButton, deleteButton, refreshButton;
    private JTextField searchField;
    private JLabel totalChargesLabel, totalRevenusLabel, soldeLabel;
    private ChargesServiceImpl chargesService;
    private SimpleDateFormat dateFormat;
    private boolean useFakeData = false; // Pour utiliser des données factices

    public CaissePanel() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.useFakeData = true; // Forcer les données factices
        initializeService();
        setupUI();
        loadData();
    }

    private void initializeService() {
        if (!useFakeData) {
            try {
                Connection conn = SessionFactory.getInstance().getConnection();
                ChargesRepositoryImpl chargesRepository = new ChargesRepositoryImpl(conn);
                this.chargesService = new ChargesServiceImpl(chargesRepository);
            } catch (Exception e) {
                useFakeData = true;
                System.out.println("Service non disponible, utilisation de données factices");
            }
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout(0, 0));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 30, 30, 30));

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // En-tête
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // Contenu central
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(createSummaryCardsPanel(), BorderLayout.NORTH);
        centerPanel.add(createActionsPanel(), BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Table
        add(mainPanel, BorderLayout.NORTH);
        add(createTableCard(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Gestion de la Caisse");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel subtitleLabel = new JLabel("Gérez vos revenus et dépenses");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(BACKGROUND_COLOR);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);

        panel.add(textPanel, BorderLayout.WEST);
        return panel;
    }

    private JPanel createSummaryCardsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setBackground(BACKGROUND_COLOR);

        // Carte Revenus
        JPanel revenusCard = createSummaryCard(
                "💰 Revenus Total",
                "0.00 DH",
                SUCCESS_COLOR,
                totalRevenusLabel = new JLabel("0.00 DH")
        );

        // Carte Dépenses
        JPanel chargesCard = createSummaryCard(
                "💸 Dépenses Total",
                "0.00 DH",
                DANGER_COLOR,
                totalChargesLabel = new JLabel("0.00 DH")
        );

        // Carte Solde
        JPanel soldeCard = createSummaryCard(
                "📊 Solde Net",
                "0.00 DH",
                PRIMARY_COLOR,
                soldeLabel = new JLabel("0.00 DH")
        );

        panel.add(revenusCard);
        panel.add(chargesCard);
        panel.add(soldeCard);

        return panel;
    }

    private JPanel createSummaryCard(String title, String defaultValue, Color accentColor, JLabel valueLabel) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 10));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(TEXT_SECONDARY);

        valueLabel.setText(defaultValue);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(accentColor);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setBackground(BACKGROUND_COLOR);

        // Boutons d'action
        createButton = createStyledButton("+ Nouvelle Dépense", PRIMARY_COLOR);
        editButton = createStyledButton("Modifier", ACCENT_COLOR);
        deleteButton = createStyledButton("Supprimer", DANGER_COLOR);
        refreshButton = createStyledButton("⟳ Actualiser", new Color(96, 125, 139));

        // Champ de recherche
        JPanel searchPanel = createSearchPanel();

        panel.add(createButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(refreshButton);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(searchPanel);

        setupButtonActions();

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBorder(new EmptyBorder(5, 5, 5, 5));
        searchField.setForeground(TEXT_SECONDARY);

        panel.add(searchIcon);
        panel.add(searchField);

        return panel;
    }

    private JPanel createTableCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(0, 0, 0, 0)
        ));

        String[] columns = {"ID", "Titre", "Description", "Montant", "Date", "Cabinet ID"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        chargesTable = new JTable(tableModel);
        customizeTable();

        JScrollPane scrollPane = new JScrollPane(chargesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_COLOR);

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private void customizeTable() {
        chargesTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chargesTable.setRowHeight(50);
        chargesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chargesTable.setShowGrid(true);
        chargesTable.setGridColor(new Color(240, 240, 240));
        chargesTable.setFillsViewportHeight(true);
        chargesTable.setBackground(CARD_COLOR);
        chargesTable.setSelectionBackground(new Color(224, 247, 250));
        chargesTable.setSelectionForeground(TEXT_PRIMARY);
        chargesTable.setIntercellSpacing(new Dimension(10, 10));

        // Header
        JTableHeader header = chargesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(250, 250, 250));
        header.setForeground(TEXT_SECONDARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 45));

        // Masquer colonne ID
        chargesTable.getColumnModel().getColumn(0).setMinWidth(0);
        chargesTable.getColumnModel().getColumn(0).setMaxWidth(0);

        // Masquer colonne Cabinet ID
        chargesTable.getColumnModel().getColumn(5).setMinWidth(0);
        chargesTable.getColumnModel().getColumn(5).setMaxWidth(0);

        // Largeurs
        chargesTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        chargesTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        chargesTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        chargesTable.getColumnModel().getColumn(4).setPreferredWidth(120);

        // Renderer pour le montant
        DefaultTableCellRenderer montantRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.RIGHT);
                setFont(new Font("Segoe UI", Font.BOLD, 13));
                setForeground(DANGER_COLOR);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_COLOR : new Color(248, 248, 248));
                }
                setBorder(new EmptyBorder(5, 10, 5, 10));
                return c;
            }
        };
        chargesTable.getColumnModel().getColumn(3).setCellRenderer(montantRenderer);

        // Renderer pour date
        DefaultTableCellRenderer dateRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_COLOR : new Color(248, 248, 248));
                }
                setBorder(new EmptyBorder(5, 10, 5, 10));
                return c;
            }
        };
        chargesTable.getColumnModel().getColumn(4).setCellRenderer(dateRenderer);

        // Renderer par défaut
        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_COLOR : new Color(248, 248, 248));
                }
                setBorder(new EmptyBorder(5, 10, 5, 10));
                return c;
            }
        };
        chargesTable.getColumnModel().getColumn(1).setCellRenderer(defaultRenderer);
        chargesTable.getColumnModel().getColumn(2).setCellRenderer(defaultRenderer);
    }

    private void setupButtonActions() {
        createButton.addActionListener(e -> openCreateChargeDialog());
        editButton.addActionListener(e -> openEditChargeDialog());
        deleteButton.addActionListener(e -> deleteCharge());
        refreshButton.addActionListener(e -> {
            loadData();
            showSuccessDialog("Données actualisées avec succès");
        });
    }

    private void loadData() {
        if (useFakeData) {
            loadFakeData();
        } else {
            loadDataFromDatabase();
        }
    }

    private void loadFakeData() {
        tableModel.setRowCount(0);

        List<FakeCharge> charges = generateFakeCharges();

        double totalCharges = 0;
        double totalRevenus = 45000.00; // Revenus fictifs

        for (FakeCharge charge : charges) {
            Object[] row = {
                    charge.id,
                    charge.titre,
                    charge.description,
                    String.format("%.2f DH", charge.montant),
                    dateFormat.format(charge.date),
                    charge.cabinetId
            };
            tableModel.addRow(row);
            totalCharges += charge.montant;
        }

        updateSummaryLabels(totalRevenus, totalCharges);
    }

    private List<FakeCharge> generateFakeCharges() {
        List<FakeCharge> charges = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        charges.add(new FakeCharge(1L, "Matériel Dentaire", "Achat d'instruments dentaires stérilisés", 3500.00, getDate(cal, -15), 1));
        charges.add(new FakeCharge(2L, "Produits Anesthésiques", "Stock de produits anesthésiques locaux", 1200.50, getDate(cal, -12), 1));
        charges.add(new FakeCharge(3L, "Équipement Radiologie", "Maintenance équipement rayons X", 2800.00, getDate(cal, -10), 1));
        charges.add(new FakeCharge(4L, "Consommables", "Gants, masques, désinfectants", 850.75, getDate(cal, -8), 1));
        charges.add(new FakeCharge(5L, "Formation Continue", "Séminaire nouvelles techniques implantaires", 4500.00, getDate(cal, -7), 1));
        charges.add(new FakeCharge(6L, "Loyer Cabinet", "Loyer mensuel du cabinet médical", 6000.00, getDate(cal, -5), 1));
        charges.add(new FakeCharge(7L, "Électricité", "Facture électricité du mois", 980.00, getDate(cal, -4), 1));
        charges.add(new FakeCharge(8L, "Assurance", "Assurance responsabilité civile professionnelle", 1500.00, getDate(cal, -3), 1));
        charges.add(new FakeCharge(9L, "Matériel Prothèse", "Matériaux pour prothèses dentaires", 2200.00, getDate(cal, -2), 1));
        charges.add(new FakeCharge(10L, "Salaires", "Salaire assistante dentaire", 8000.00, getDate(cal, -1), 1));
        charges.add(new FakeCharge(11L, "Téléphone & Internet", "Abonnement communications cabinet", 450.00, new Date(), 1));
        charges.add(new FakeCharge(12L, "Nettoyage", "Service de nettoyage professionnel", 600.00, new Date(), 1));

        return charges;
    }

    private Date getDate(Calendar cal, int daysOffset) {
        Calendar temp = (Calendar) cal.clone();
        temp.add(Calendar.DAY_OF_MONTH, daysOffset);
        return temp.getTime();
    }

    private void loadDataFromDatabase() {
        try {
            List<Charges> chargesList = chargesService.findAll();
            tableModel.setRowCount(0);

            double totalCharges = 0;
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

                if (charge.getMontant() != null) {
                    totalCharges += charge.getMontant();
                }
            }

            updateSummaryLabels(0, totalCharges);
        } catch (Exception e) {
            showErrorDialog("Erreur lors du chargement: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateSummaryLabels(double revenus, double charges) {
        totalRevenusLabel.setText(String.format("%.2f DH", revenus));
        totalChargesLabel.setText(String.format("%.2f DH", charges));

        double solde = revenus - charges;
        soldeLabel.setText(String.format("%.2f DH", solde));
        soldeLabel.setForeground(solde >= 0 ? SUCCESS_COLOR : DANGER_COLOR);
    }

    private void openCreateChargeDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Ajouter une Nouvelle Dépense");
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(CARD_COLOR);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 15, 15));
        formPanel.setBackground(CARD_COLOR);

        JTextField titreField = createFormField(formPanel, "Titre:");
        JTextField descriptionField = createFormField(formPanel, "Description:");
        JTextField montantField = createFormField(formPanel, "Montant (DH):");
        JTextField cabinetField = createFormField(formPanel, "Cabinet ID:");
        cabinetField.setText("1");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(CARD_COLOR);

        JButton saveButton = createStyledButton("Enregistrer", SUCCESS_COLOR);
        JButton cancelButton = createStyledButton("Annuler", new Color(158, 158, 158));

        saveButton.addActionListener(e -> {
            try {
                if (useFakeData) {
                    // Simuler l'ajout
                    showSuccessDialog("Dépense ajoutée avec succès");
                    dialog.dispose();
                    loadData();
                } else {
                    Charges charge = Charges.builder()
                            .titre(titreField.getText())
                            .description(descriptionField.getText())
                            .montant(Double.parseDouble(montantField.getText()))
                            .dateCharge(new Date())
                            .idCabinet(Integer.parseInt(cabinetField.getText()))
                            .build();

                    chargesService.create(charge);
                    showSuccessDialog("Dépense créée avec succès");
                    loadDataFromDatabase();
                    dialog.dispose();
                }
            } catch (NumberFormatException ex) {
                showErrorDialog("Veuillez entrer un montant valide");
            } catch (Exception ex) {
                showErrorDialog("Erreur: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private JTextField createFormField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_PRIMARY);

        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(8, 10, 8, 10)
        ));

        panel.add(label);
        panel.add(field);

        return field;
    }

    private void openEditChargeDialog() {
        int selectedRow = chargesTable.getSelectedRow();
        if (selectedRow < 0) {
            showWarningDialog("Veuillez sélectionner une dépense");
            return;
        }

        showInfoDialog("Fonction de modification à implémenter");
    }

    private void deleteCharge() {
        int selectedRow = chargesTable.getSelectedRow();
        if (selectedRow < 0) {
            showWarningDialog("Veuillez sélectionner une dépense");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer cette dépense?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            showSuccessDialog("Dépense supprimée avec succès");
            loadData();
        }
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void showWarningDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Attention", JOptionPane.WARNING_MESSAGE);
    }

    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    // Classe interne pour les données factices
    private static class FakeCharge {
        Long id;
        String titre;
        String description;
        Double montant;
        Date date;
        Integer cabinetId;

        FakeCharge(Long id, String titre, String description, Double montant, Date date, Integer cabinetId) {
            this.id = id;
            this.titre = titre;
            this.description = description;
            this.montant = montant;
            this.date = date;
            this.cabinetId = cabinetId;
        }
    }
}