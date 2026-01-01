package ma.prodenta.mvc.ui.rendezvous;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RendezvousPanel extends JPanel {
    
    private JTable rendezvousTable;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;

    public RendezvousPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Liste des Rendez-vous"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"N°", "Nom", "Date", "Heure", "Motif", "Note", "Statut", "Actions"};
        Object[][] data = {
            {112, "Chougrad Othmane", "22-11-2025", "12:30", "Dent cassée", "-", "Confirmé", "Détails"},
            {113, "Youssef Hachimi", "22-11-2025", "15:00", "Détartrage", "Patient ne doit pas venir à jeun", "Annulé", "Détails"},
            {114, "Yasser Touil", "23-11-2025", "09:30", "Rage de dent", "Patient doit venir à jeun", "Confirmé", "Détails"}
        };

        rendezvousTable = new JTable(data, columns);
        rendezvousTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPane = new JScrollPane(rendezvousTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);

        createButton = new JButton("Créer RDV");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }
}
