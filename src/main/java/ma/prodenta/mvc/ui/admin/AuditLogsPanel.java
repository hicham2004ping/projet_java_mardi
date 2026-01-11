package ma.prodenta.mvc.ui.admin;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogsPanel extends JPanel {
    private JTable logsTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton exportButton;

    public AuditLogsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadLogs();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Journaux d'Audit");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(titleLabel);
        return topPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Historique des Accès"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Utilisateur", "Action", "Date/Heure", "Adresse IP", "Statut"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        logsTable = new JTable(tableModel);
        logsTable.setRowHeight(25);
        logsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(logsTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        refreshButton = new JButton("Rafraîchir");
        exportButton = new JButton("Exporter");

        refreshButton.addActionListener(e -> loadLogs());
        exportButton.addActionListener(e -> exportLogs());

        buttonPanel.add(refreshButton);
        buttonPanel.add(exportButton);

        return buttonPanel;
    }

    private void loadLogs() {
        tableModel.setRowCount(0);
        
        // Sample data - in a real application, this would come from a database
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        tableModel.addRow(new Object[]{
                1,
                "admin@prodenta.com",
                "Connexion",
                LocalDateTime.now().format(formatter),
                "192.168.1.100",
                "Succès"
        });
        
        tableModel.addRow(new Object[]{
                2,
                "medecin@prodenta.com",
                "Connexion",
                LocalDateTime.now().minusHours(1).format(formatter),
                "192.168.1.101",
                "Succès"
        });
        
        tableModel.addRow(new Object[]{
                3,
                "secretaire@prodenta.com",
                "Création Patient",
                LocalDateTime.now().minusHours(2).format(formatter),
                "192.168.1.102",
                "Succès"
        });
        
        tableModel.addRow(new Object[]{
                4,
                "unknown_user",
                "Tentative Connexion",
                LocalDateTime.now().minusHours(3).format(formatter),
                "192.168.1.103",
                "Échec"
        });
    }

    private void exportLogs() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "Logs exportés avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
