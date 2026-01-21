package ma.prodenta.mvc.ui.patient;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PatientsListPanel extends JPanel {

    private JTable patientsTable;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JTextField searchField;

    public PatientsListPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Patients");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        titlePanel.add(createSearchPanel(), BorderLayout.EAST);

        return titlePanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setBackground(Color.WHITE);

        searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton searchButton = new JButton("Rechercher");
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        searchPanel.add(new JLabel("Recherche:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        return searchPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // En-tête de la table
        JPanel tableHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tableHeader.setBackground(new Color(240, 240, 240));
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        JLabel tableTitle = new JLabel("Liste des Patients");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(new Color(60, 60, 60));
        tableHeader.add(tableTitle);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));

        tablePanel.add(tableHeader, BorderLayout.NORTH);

        // Données de la table
        String[] columns = {"N°", "Nom", "Date de naissance", "Adresse", "Téléphone", "Actions"};
        Object[][] data = {
                {20041013, "Chougrad Othmane", "13-10-2004", "Rue Ait Zakri aviation Rabat", "06 57 19 31 75", "Détails"},
                {20040325, "Youssef Hachimi", "22-04-2004", "Rue Al Oufir, Kenitra", "06 66 55 44 11", "Détails"},
                {20000325, "Yasser Touil", "14-03-2001", "Avenue Al Qods, Océan", "06 54 89 47 54", "Détails"}
        };

        patientsTable = new JTable(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Seule la colonne "Actions" est éditable
            }
        };

        // Style de la table
        patientsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        patientsTable.setRowHeight(35);
        patientsTable.setIntercellSpacing(new Dimension(0, 0));
        patientsTable.setShowVerticalLines(false);
        patientsTable.setShowHorizontalLines(true);
        patientsTable.setGridColor(new Color(230, 230, 230));

        // Style du header
        patientsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        patientsTable.getTableHeader().setBackground(new Color(0, 102, 204));
        patientsTable.getTableHeader().setForeground(Color.WHITE);
        patientsTable.getTableHeader().setPreferredSize(new Dimension(
                patientsTable.getTableHeader().getPreferredSize().width, 40
        ));

        // Style de la colonne Actions
        JButton detailsBtn = new JButton("Détails");
        detailsBtn.setBackground(new Color(0, 102, 204));
        detailsBtn.setForeground(Color.WHITE);
        detailsBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        detailsBtn.setFocusPainted(false);
        detailsBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // On pourrait utiliser un renderer personnalisé pour les boutons dans la table

        JScrollPane scrollPane = new JScrollPane(patientsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        createButton = createStyledButton("Créer", new Color(0, 102, 204));
        editButton = createStyledButton("Modifier", new Color(255, 153, 51));
        detailsButton = createStyledButton("Afficher Détails", new Color(102, 153, 0));
        deleteButton = createStyledButton("Supprimer", new Color(204, 0, 0));

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }
}