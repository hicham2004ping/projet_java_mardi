package ma.prodenta.mvc.ui.agenda;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AgendaPanel extends JPanel {
    
    private String[] days = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
    private String[] hours = {"8h - 9h", "9h - 12h", "12h - 14h", "14h - 16h"};
    private JTable agendaTable;

    public AgendaPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        
        add(createAgendaTable(), BorderLayout.CENTER);
    }

    private JPanel createAgendaTable() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Agenda de la semaine"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = new String[5];
        columns[0] = "Jour";
        System.arraycopy(hours, 0, columns, 1, hours.length);

        Object[][] data = new Object[days.length][5];
        for (int i = 0; i < days.length; i++) {
            data[i][0] = days[i];
            data[i][1] = "Tournée général";
            data[i][2] = "Consultation";
            data[i][3] = "Pause déjeuner";
            data[i][4] = "Consultation";
        }

        agendaTable = new JTable(data, columns);
        agendaTable.setDefaultEditor(Object.class, null);
        agendaTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(agendaTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }
}
