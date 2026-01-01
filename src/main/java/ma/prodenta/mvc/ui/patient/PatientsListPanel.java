package ma.prodenta.mvc.ui.patient;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PatientsListPanel extends JPanel {
    
    private JTable patientsTable;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField searchField;

    public PatientsListPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        
        add(createSearchPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        searchField = new JTextField("Chercher N°", 20);
        searchPanel.add(new JLabel("Recherche:"));
        searchPanel.add(searchField);

        return searchPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Liste des Patients"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"N°", "Nom", "Date de naissance", "Adresse", "Téléphone", "Actions"};
        Object[][] data = {
            {20041013, "Chougrad Othmane", "13-10-2004", "Rue Ait Zakri aviation Rabat", "06 57 19 31 75", "Détails"},
            {20040325, "Youssef Hachimi", "22-04-2004", "Rue Al Oufir, Kenitra", "06 66 55 44 11", "Détails"},
            {20000325, "Yasser Touil", "14-03-2001", "Avenue Al Qods, Océan", "06 54 89 47 54", "Détails"}
        };

        patientsTable = new JTable(data, columns);
        patientsTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(patientsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        createButton = new JButton("Créer");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }
}
