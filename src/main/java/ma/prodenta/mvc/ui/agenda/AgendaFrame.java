package ma.prodenta.mvc.ui.agenda;

import com.toedter.calendar.JDateChooser;
import ma.prodenta.mvc.controllers.modules.agenda.AgendaController;
import ma.prodenta.mvc.dto.agenda.AgendaDTO;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AgendaFrame extends JPanel {

    private AgendaController controller;
    private Dashboard_view dashboard;
    private JTable table;
    private DefaultTableModel model;
    private JDateChooser dateChooser;

    public AgendaFrame(Dashboard_view dashboard) {
        this.dashboard = dashboard;
        this.controller = new AgendaController();

        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        // Toolbar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton btnAdd = new JButton("Ajouter");
        JButton btnEdit = new JButton("Modifier");
        JButton btnDelete = new JButton("Supprimer");
        JButton btnRefresh = new JButton("Actualiser");

        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        JButton btnFilter = new JButton("Filtrer par Date");

        topPanel.add(btnAdd);
        topPanel.add(btnEdit);
        topPanel.add(btnDelete);
        topPanel.add(btnRefresh);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        topPanel.add(new JLabel("Date:"));
        topPanel.add(dateChooser);
        topPanel.add(btnFilter);

        // Table
        String[] columns = { "ID", "Date Début", "Date Fin", "Médecin", "Patient", "Statut", "Note" };
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Hide ID column
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setResizable(false);

        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        btnAdd.addActionListener(e -> {
            AgendaForm form = new AgendaForm(this, null);
            form.setVisible(true);
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                Integer id = (Integer) model.getValueAt(row, 0);
                try {
                    AgendaDTO dto = controller.afficherParId(id);
                    AgendaForm form = new AgendaForm(this, dto);
                    form.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne.");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Supprimer cet élément ?", "Confirmer",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Integer id = (Integer) model.getValueAt(row, 0);
                    try {
                        controller.supprimerAgenda(id);
                        model.removeRow(row);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
                    }
                }
            }
        });

        btnRefresh.addActionListener(e -> loadData());

        btnFilter.addActionListener(e -> loadDataByDate(dateChooser.getDate()));
    }

    public void loadData() {
        model.setRowCount(0);
        List<AgendaDTO> list = controller.afficherTous();
        fillTable(list);
    }

    private void loadDataByDate(Date date) {
        model.setRowCount(0);
        List<AgendaDTO> list = controller.afficherParDate(date);
        fillTable(list);
    }

    private void fillTable(List<AgendaDTO> list) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (AgendaDTO dto : list) {
            model.addRow(new Object[] {
                    dto.getIdAgenda(),
                    dto.getDateDebut() != null ? sdf.format(dto.getDateDebut()) : "",
                    dto.getDateFin() != null ? sdf.format(dto.getDateFin()) : "",
                    dto.getIdMedecin(),
                    dto.getIdPatient(),
                    dto.getStatut(),
                    dto.getNote()
            });
        }
    }

    public void refresh() {
        loadData();
    }
}
