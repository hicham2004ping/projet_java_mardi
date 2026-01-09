package ma.prodenta.mvc.ui.patient;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PatientDetailPanel extends JPanel {
    
    private JTabbedPane tabbedPane;
    private JTable consultationTable;
    private JTable prescriptionTable;

    public PatientDetailPanel(String patientName) {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        
        add(createPatientInfoPanel(patientName), BorderLayout.NORTH);
        add(createTabbedPanel(), BorderLayout.CENTER);
    }

    private JPanel createPatientInfoPanel(String patientName) {
        JPanel infoPanel = new JPanel(new GridLayout(2, 3));
        infoPanel.setBorder(new TitledBorder("Informations du Patient: " + patientName));
        infoPanel.setBackground(Color.WHITE);

        infoPanel.add(createInfoField("Date Naissance", "13-10-2004"));
        infoPanel.add(createInfoField("Adresse", "Rue Ait Zakri"));
        infoPanel.add(createInfoField("Téléphone", "06 57 19 31 75"));
        infoPanel.add(createInfoField("Email", "patient@example.com"));
        infoPanel.add(createInfoField("Âge", "21 ans"));
        infoPanel.add(createInfoField("Sexe", "Homme"));

        return infoPanel;
    }

    private JPanel createInfoField(String label, String value) {
        JPanel fieldPanel = new JPanel(new GridLayout(2, 1));
        fieldPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel valueComponent = new JLabel(value);

        fieldPanel.add(labelComponent);
        fieldPanel.add(valueComponent);
        return fieldPanel;
    }

    private JPanel createTabbedPanel() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Consultation", createConsultationPanel());
        tabbedPane.addTab("RDV", createRDVPanel());
        tabbedPane.addTab("Certificats", createCertificatePanel());
        tabbedPane.addTab("Ordonnances", createPrescriptionPanel());
        tabbedPane.addTab("Situation Financière", createFinancialPanel());

        return tabbedPane;
    }

    private JPanel createConsultationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Date", "Acte Dentaire", "Dents", "Prix", "Actions"};
        Object[][] data = {
            {"22-11-2025", "Détartrage", "tout", "300", "Modifier"},
            {"11-10-2025", "Consultation", "-", "300", "Modifier"}
        };

        consultationTable = new JTable(data, columns);
        consultationTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(consultationTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(new JButton("Créer consultation"));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRDVPanel() {
        return new JPanel();
    }

    private JPanel createCertificatePanel() {
        return new JPanel();
    }

    private JPanel createPrescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        String[] columns = {"N°", "Date", "N° Con.", "Médicaments", "Actions"};
        Object[][] data = {
            {112, "22-11-2025", "120", "Doliprane", "Modifier"},
            {113, "22-11-2025", "121", "Sensodyne", "Modifier"}
        };

        prescriptionTable = new JTable(data, columns);
        prescriptionTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(prescriptionTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(new JButton("Créer ordonnance"));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFinancialPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        String[] columns = {"N. facture", "Date", "État", "Montant", "Actions"};
        Object[][] data = {
            {1, "05-11-2024", "Payé", "7000 DH", "Détails"},
            {2, "13-02-2025", "Payé", "5500 DH", "Détails"}
        };

        JTable financialTable = new JTable(data, columns);
        financialTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(financialTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
