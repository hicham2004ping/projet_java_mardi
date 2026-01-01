package ma.prodenta.mvc.ui.dossier;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DossierMedicalListPanel extends JPanel {
    
    private JTable dossierTable;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;

    public DossierMedicalListPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Liste des dossiers médicaux"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"Id dossier", "Id patient", "Nom", "Prénom", "Email", "Total consultations", "Montant total"};
        Object[][] data = {
            {1, 1, "Moulragouba", "Hicham", "hicham@gmail.com", 5, "122500"},
            {2, 2, "Ouedghiti", "Abdo", "abdo@gmail.com", 3, "1200"},
            {3, 3, "Mouad", "Mouad", "Mouad@gmail.com", 2, "300"},
            {4, 4, "Othman", "Otman", "Othman@gmail.com", 4, "32000"}
        };

        dossierTable = new JTable(data, columns);
        dossierTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(dossierTable);
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
