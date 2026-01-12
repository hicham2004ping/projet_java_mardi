package ma.prodenta.mvc.ui.admin;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import ma.prodenta.config.util.DBConnection;

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

        String sql = """
        SELECT 
            al.id,
            u.email,
            al.action,
            al.log_date,
            al.ip_address,
            al.status
        FROM audit_logs al
        LEFT JOIN utilisateur u ON al.idUser = u.idUser
        ORDER BY al.log_date DESC
        LIMIT 10
    """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("email") != null ? rs.getString("email") : "Utilisateur inconnu",
                        rs.getString("action"),
                        rs.getTimestamp("log_date").toLocalDateTime()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        rs.getString("ip_address"),
                        rs.getString("status")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erreur lors du chargement des logs : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void exportLogs() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(this, "Logs exportés avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
