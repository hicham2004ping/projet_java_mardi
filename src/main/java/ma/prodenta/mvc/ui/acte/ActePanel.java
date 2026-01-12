package ma.prodenta.mvc.ui.acte;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.service.modules.actes.api.Acte_Service_api;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActePanel extends JPanel {

    private JTable acteTable;
    private DefaultTableModel tableModel;
    private Acte_Service_api acteService;

    public ActePanel() {
        this.acteService = Application_contexte.getacteService();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new TitledBorder("Gestion des Actes"));

        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        refreshTable();
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] columns = { "ID", "Libellé", "Catégorie", "Prix de Base" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        acteTable = new JTable(tableModel);
        acteTable.setRowHeight(25);
        acteTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(acteTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnActualiser = new JButton("Actualiser");

        styleButton(btnAjouter, new Color(46, 204, 113));
        styleButton(btnModifier, new Color(52, 152, 219));
        styleButton(btnSupprimer, new Color(231, 76, 60));
        styleButton(btnActualiser, new Color(149, 165, 166));

        btnAjouter.addActionListener(e -> ouvrirFormulaireAjout());
        btnModifier.addActionListener(e -> modifierActeSelectionne());
        btnSupprimer.addActionListener(e -> supprimerActeSelectionne());
        btnActualiser.addActionListener(e -> refreshTable());

        panel.add(btnAjouter);
        panel.add(btnModifier);
        panel.add(btnSupprimer);
        panel.add(btnActualiser);

        return panel;
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Acte> actes = acteService.getAllActes();
            for (Acte acte : actes) {
                tableModel.addRow(new Object[] {
                        acte.getId(),
                        acte.getLibelle(),
                        acte.getCategorie(),
                        acte.getPrix_de_base()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des actes : " + e.getMessage(), "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ouvrirFormulaireAjout() {
        ActeForm form = new ActeForm(null, this);
        form.setVisible(true);
    }

    private void modifierActeSelectionne() {
        int selectedRow = acteTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un acte à modifier.", "Attention",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String libelle = (String) tableModel.getValueAt(selectedRow, 1);
        String categorie = (String) tableModel.getValueAt(selectedRow, 2);
        double prix = (double) tableModel.getValueAt(selectedRow, 3);

        Acte acte = new Acte(id, categorie, libelle, prix);
        ActeForm form = new ActeForm(acte, this);
        form.setVisible(true);
    }

    private void supprimerActeSelectionne() {
        int selectedRow = acteTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un acte à supprimer.", "Attention",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet acte ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                acteService.supprimerActeParId(id);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Acte supprimé avec succès.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + e.getMessage(), "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to allow external refresh
    public void reloadData() {
        refreshTable();
    }
}
