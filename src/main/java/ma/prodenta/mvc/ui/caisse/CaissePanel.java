package ma.prodenta.mvc.ui.caisse;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CaissePanel extends JPanel {
    
    private JTable factureTable;
    private JLabel totalPaymentLabel;
    private JLabel pendingPaymentLabel;
    private JButton generateInvoiceButton;

    public CaissePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        
        add(createTablePanel(), BorderLayout.CENTER);
        add(createStatsPanel(), BorderLayout.SOUTH);
        add(createButtonPanel(), BorderLayout.NORTH);
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Liste des factures"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"N. facture", "Date", "Nom du patient", "Montant", "État", "Actions"};
        Object[][] data = {
            {1, "05-11-2024", "Patient 1", "7000 DH", "Payé", "Détails"},
            {2, "13-02-2025", "Patient 2", "5500 DH", "Payé", "Détails"},
            {3, "03-05-2025", "Patient 3", "1200 DH", "Payé", "Détails"},
            {4, "29-07-2025", "Patient 4", "3150 DH", "Non-Payé", "Détails"},
            {5, "06-10-2025", "Patient 5", "600 DH", "Non-Payé", "Détails"}
        };

        factureTable = new JTable(data, columns);
        factureTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(factureTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 2));
        statsPanel.setBorder(new TitledBorder("Résumé financier"));
        statsPanel.setBackground(Color.WHITE);

        totalPaymentLabel = new JLabel("Totale des payements: 17450 DH");
        pendingPaymentLabel = new JLabel("Crédit des payement en attente: 3750 DH");

        statsPanel.add(totalPaymentLabel);
        statsPanel.add(pendingPaymentLabel);

        return statsPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        generateInvoiceButton = new JButton("Génération facture");
        buttonPanel.add(generateInvoiceButton);

        return buttonPanel;
    }
}
