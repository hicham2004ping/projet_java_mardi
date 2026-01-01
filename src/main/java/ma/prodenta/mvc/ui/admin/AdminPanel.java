package ma.prodenta.mvc.ui.admin;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AdminPanel extends JPanel {
    
    private JTabbedPane tabbedPane;

    public AdminPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Gestion Utilisateurs", createUsersPanel());
        tabbedPane.addTab("Sécurité", createSecurityPanel());
        tabbedPane.addTab("Données", createDataPanel());
        tabbedPane.addTab("Catalogue Médical", createCatalogPanel());
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Nom", "Email", "CIN", "Dr.", "Naissance", "Téléphone", "Sexe", "Login", "Mot de passe", "Salaire", "Prime", "Dt recrt", "Sld Congé", "Rôle"};
        Object[][] data = new Object[6][15];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 15; j++) {
                data[i][j] = "text";
            }
        }

        JTable usersTable = new JTable(data, columns);
        usersTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSecurityPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Journaux de sécurité"));
        panel.setBackground(Color.WHITE);

        JTextArea logsArea = new JTextArea();
        logsArea.setText("Dernière connexion le 22-11-2025 à 15:59:23\n" +
                         "Dernière connexion le 22-11-2025 à 15:59:23\n" +
                         "Dernière connexion le 22-11-2025 à 15:59:23\n");
        logsArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(logsArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createDataPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Gestion des données"));
        panel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Nom", "Rôle", "Assurance"};
        Object[][] data = {
            {1, "text", "text", "text"},
            {2, "text", "text", "text"}
        };

        JTable dataTable = new JTable(data, columns);
        dataTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCatalogPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Catalogue Médical"));
        panel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Nom", "Labo", "Type", "Remboursable", "Prix U.", "Description", "Forme"};
        Object[][] data = new Object[5][8];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                data[i][j] = "text";
            }
        }

        JTable catalogTable = new JTable(data, columns);
        catalogTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(catalogTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
