package ma.prodenta.mvc.ui.statistics;

import ma.prodenta.config.SessionFactory;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * StatisticsPanel - Panel for displaying financial situation statistics
 * Shows dashboard with financial metrics, invoices status, and charges
 */
public class StatisticsPanel extends JPanel {
    
    private JLabel totalRevenusLabel;
    private JLabel totalChargesLabel;
    private JLabel soldeLabel;
    private JLabel invoicesPaidLabel;
    private JLabel invoicesPendingLabel;
    private JLabel invoicesDueLabel;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private DecimalFormat currencyFormat;
    private Dashboard_view dashboard;

    public StatisticsPanel() {
        this(null);
    }

    public StatisticsPanel(Dashboard_view dashboard) {
        this.dashboard = dashboard;
        this.currencyFormat = new DecimalFormat("0.00");
        setupUI();
        loadFinancialData();
    }

    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        add(createMetricsPanel(), BorderLayout.NORTH);
        add(createDetailTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createMetricsPanel() {
        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new GridLayout(2, 3, 15, 15));
        metricsPanel.setBackground(Color.WHITE);
        metricsPanel.setBorder(new TitledBorder("Résumé Financier"));

        totalRevenusLabel = createMetricPanel("Revenus Total", "0.00 DH");
        totalChargesLabel = createMetricPanel("Charges Total", "0.00 DH");
        soldeLabel = createMetricPanel("Solde", "0.00 DH");
        invoicesPaidLabel = createMetricPanel("Factures Payées", "0");
        invoicesPendingLabel = createMetricPanel("Factures en Attente", "0");
        invoicesDueLabel = createMetricPanel("Factures Non Payées", "0");

        metricsPanel.add(totalRevenusLabel.getParent());
        metricsPanel.add(totalChargesLabel.getParent());
        metricsPanel.add(soldeLabel.getParent());
        metricsPanel.add(invoicesPaidLabel.getParent());
        metricsPanel.add(invoicesPendingLabel.getParent());
        metricsPanel.add(invoicesDueLabel.getParent());

        return metricsPanel;
    }

    private JLabel createMetricPanel(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLabel.setForeground(new Color(100, 100, 100));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueLabel.setForeground(new Color(0, 100, 200));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return valueLabel;
    }

    private JPanel createDetailTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Détail des Transactions"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"Type", "Description", "Montant", "Date", "Statut"};
        tableModel = new DefaultTableModel(new Object[0][], columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionTable = new JTable(tableModel);
        transactionTable.setRowHeight(25);
        transactionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton refreshButton = new JButton("Actualiser");
        refreshButton.addActionListener(e -> {
            loadFinancialData();
            JOptionPane.showMessageDialog(this, "Données actualisées", "Succès", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton exportButton = new JButton("Exporter");
        exportButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fonction d'export en cours de développement", "Info", JOptionPane.INFORMATION_MESSAGE));

        buttonPanel.add(refreshButton);
        buttonPanel.add(exportButton);
        tablePanel.add(buttonPanel, BorderLayout.SOUTH);

        return tablePanel;
    }

    private void loadFinancialData() {
        try {
            Map<String, Double> financialData = calculateFinancialMetrics();
            Map<String, Integer> invoiceStats = calculateInvoiceStats();

            // Update metrics
            totalRevenusLabel.setText(String.format("%.2f DH", financialData.get("revenus")));
            totalChargesLabel.setText(String.format("%.2f DH", financialData.get("charges")));
            soldeLabel.setText(String.format("%.2f DH", financialData.get("solde")));
            invoicesPaidLabel.setText(String.valueOf(invoiceStats.get("payee")));
            invoicesPendingLabel.setText(String.valueOf(invoiceStats.get("attente")));
            invoicesDueLabel.setText(String.valueOf(invoiceStats.get("non_payee")));

            loadTransactionDetails();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement des données: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private Map<String, Double> calculateFinancialMetrics() throws Exception {
        Map<String, Double> metrics = new HashMap<>();
        double totalRevenues = 0.0;
        double totalCharges = 0.0;

        // Calculate revenues from Facture
        String revenueSQL = "SELECT SUM(totalpaye) as total FROM facture WHERE statut = 'payee'";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(revenueSQL)) {
            if (rs.next()) {
                totalRevenues = rs.getDouble("total");
            }
        }

        // Calculate charges from Charges table
        String chargesSQL = "SELECT SUM(montant) as total FROM charges";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(chargesSQL)) {
            if (rs.next()) {
                totalCharges = rs.getDouble("total");
            }
        }

        metrics.put("revenus", totalRevenues);
        metrics.put("charges", totalCharges);
        metrics.put("solde", totalRevenues - totalCharges);

        return metrics;
    }

    private Map<String, Integer> calculateInvoiceStats() throws Exception {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("payee", 0);
        stats.put("attente", 0);
        stats.put("non_payee", 0);

        String sql = "SELECT statut, COUNT(*) as count FROM facture GROUP BY statut";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String statut = rs.getString("statut");
                int count = rs.getInt("count");
                if ("payee".equals(statut)) {
                    stats.put("payee", count);
                } else if ("en attente".equals(statut)) {
                    stats.put("attente", count);
                } else if ("non payee".equals(statut)) {
                    stats.put("non_payee", count);
                }
            }
        }

        return stats;
    }

    private void loadTransactionDetails() throws Exception {
        tableModel.setRowCount(0);

        // Load invoices
        String factureSQL = "SELECT idFact, 'Facture' as type, CONCAT('Facture #', idFact) as description, total as montant, dateFact as date, statut FROM facture";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(factureSQL)) {
            while (rs.next()) {
                Object[] row = {
                    rs.getString("type"),
                    rs.getString("description"),
                    String.format("%.2f DH", rs.getDouble("montant")),
                    rs.getDate("date"),
                    rs.getString("statut")
                };
                tableModel.addRow(row);
            }
        }

        // Load charges
        String chargesSQL = "SELECT idCharge, 'Charge' as type, titre as description, montant, dateCharge as date, 'Dépense' as statut FROM charges";
        try (Connection conn = SessionFactory.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(chargesSQL)) {
            while (rs.next()) {
                Object[] row = {
                    rs.getString("type"),
                    rs.getString("description"),
                    String.format("-%.2f DH", rs.getDouble("montant")),
                    rs.getDate("date"),
                    rs.getString("statut")
                };
                tableModel.addRow(row);
            }
        }
       
    }
}
