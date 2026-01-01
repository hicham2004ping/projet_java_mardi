package ma.prodenta.mvc.ui.dashboard;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DashboardPanel extends JPanel {
    
    private JLabel totalAppointmentsLabel;
    private JLabel totalPatientsLabel;
    private JLabel todayRevenueLabel;
    private JLabel waitingQueueLabel;
    private JTable weeklyAppointmentsTable;
    private JTable weeklyPatientsTable;

    public DashboardPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        
        add(createStatsPanel(), BorderLayout.NORTH);
        add(createTablesPanel(), BorderLayout.CENTER);
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statsPanel.setBorder(new TitledBorder("Statistiques"));
        statsPanel.setBackground(Color.WHITE);

        statsPanel.add(createStatCard("Total Rendez-vous", "812"));
        statsPanel.add(createStatCard("Total Patients", "1658"));
        statsPanel.add(createStatCard("Recette du jour", "4500 DH"));
        statsPanel.add(createStatCard("File d'attente", "5"));

        return statsPanel;
    }

    private JPanel createStatCard(String label, String value) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setBackground(new Color(240, 248, 255));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(new Color(0, 102, 204));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(titleLabel);
        card.add(valueLabel);
        return card;
    }

    private JPanel createTablesPanel() {
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        tablesPanel.setBorder(new TitledBorder("Données de la semaine"));
        tablesPanel.setBackground(Color.WHITE);

        String[] appointmentColumns = {"Nom", "Date", "Heure", "Motif", "Statut"};
        Object[][] appointmentData = {
            {"Chougrad Othmane", "22-11-2025", "12:30", "Dent cassée", "Confirmé"},
            {"Yasser Touil", "23-11-2025", "09:30", "Rage de dent", "Confirmé"}
        };
        weeklyAppointmentsTable = new JTable(appointmentData, appointmentColumns);
        weeklyAppointmentsTable.setDefaultEditor(Object.class, null);
        JScrollPane appointmentScroll = new JScrollPane(weeklyAppointmentsTable);
        appointmentScroll.setBorder(new TitledBorder("Rendez-vous"));

        String[] patientColumns = {"Nom", "Date Naissance", "Adresse", "Téléphone"};
        Object[][] patientData = {
            {"Chougrad Othmane", "13-10-2004", "Rue Ait Zakri", "06 57 19 31 75"},
            {"Youssef Hachimi", "22-04-2004", "Rue Al Oufir", "06 66 55 44 11"}
        };
        JTable patientsTable = new JTable(patientData, patientColumns);
        patientsTable.setDefaultEditor(Object.class, null);
        JScrollPane patientsScroll = new JScrollPane(patientsTable);
        patientsScroll.setBorder(new TitledBorder("Patients"));

        tablesPanel.add(appointmentScroll);
        tablesPanel.add(patientsScroll);

        return tablesPanel;
    }
}
