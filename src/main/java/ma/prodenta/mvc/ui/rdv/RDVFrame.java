package ma.prodenta.mvc.ui.rdv;

import com.toedter.calendar.JDateChooser;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.controllers.modules.rdv.RDVController;
import ma.prodenta.mvc.dto.rdv.RDVDTO;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RDVFrame extends JPanel {
    
    private RDVController controller;
    private DossierMedicalController dossierController;
    private JTable table;
    private DefaultTableModel model;
    private Dashboard_view dashboard;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnActualiser;
    private JDateChooser dateChooser;
    
    public RDVFrame(Dashboard_view dashboard) {
        this.dashboard = dashboard;
        this.controller = new RDVController();
        this.dossierController = Application_contexte.getDossierMedicalController();
        
        initializeUI();
        loadRDVs();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel des boutons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        btnAjouter = new JButton("Ajouter RDV");
        btnModifier = new JButton("Modifier RDV");
        btnSupprimer = new JButton("Supprimer RDV");
        btnActualiser = new JButton("Actualiser");
        
        // Sélecteur de date
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setPreferredSize(new Dimension(150, 30));
        JButton btnFiltrerDate = new JButton("Filtrer par date");
        
        topPanel.add(btnAjouter);
        topPanel.add(btnModifier);
        topPanel.add(btnSupprimer);
        topPanel.add(btnActualiser);
        topPanel.add(new JSeparator(SwingConstants.VERTICAL));
        topPanel.add(new JLabel("Date:"));
        topPanel.add(dateChooser);
        topPanel.add(btnFiltrerDate);
        
        // Tableau des RDV
        String[] colonnes = {"ID", "Date RDV", "Heure", "Motif", "Note Médecin", "ID Dossier", "Patient"};
        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        
        // Masquer la colonne ID
        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setResizable(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 500));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Actions des boutons
        btnAjouter.addActionListener(e -> {
            RDVForm form = new RDVForm(this, null);
            form.setVisible(true);
        });
        
        btnModifier.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Integer idRDV = (Integer) table.getValueAt(selectedRow, 0);
                try {
                    RDVDTO dto = controller.afficherParId(idRDV);
                    RDVForm form = new RDVForm(this, dto);
                    form.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Erreur lors du chargement du RDV: " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Veuillez sélectionner un RDV à modifier",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnSupprimer.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Êtes-vous sûr de vouloir supprimer ce RDV?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    Integer idRDV = (Integer) table.getValueAt(selectedRow, 0);
                    try {
                        controller.supprimerRDV(idRDV);
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, 
                            "RDV supprimé avec succès",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                            "Erreur lors de la suppression: " + ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner un RDV à supprimer",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnActualiser.addActionListener(e -> loadRDVs());
        
        btnFiltrerDate.addActionListener(e -> {
            Date date = dateChooser.getDate();
            if (date != null) {
                loadRDVsByDate(date);
            } else {
                loadRDVs();
            }
        });
    }
    
    public void loadRDVs() {
        model.setRowCount(0);
        List<RDVDTO> rdvs = controller.afficherTous();
        for (RDVDTO rdv : rdvs) {
            addRDVToTable(rdv);
        }
    }
    
    public void loadRDVsByDate(Date date) {
        model.setRowCount(0);
        List<RDVDTO> rdvs = controller.afficherParDate(date);
        for (RDVDTO rdv : rdvs) {
            addRDVToTable(rdv);
        }
    }
    
    private void addRDVToTable(RDVDTO rdv) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        String dateStr = rdv.getDateRDV() != null ? dateFormat.format(rdv.getDateRDV()) : "";
        String heureStr = rdv.getHeure() != null ? timeFormat.format(rdv.getHeure()) : "";
        
        // Récupérer le nom du patient
        String patientNom = "";
        if (rdv.getIdDossier() != null) {
            try {
                Patient patient = dossierController.find_patient(rdv.getIdDossier());
                patientNom = patient.getNom() + " " + patient.getPrenom();
            } catch (Exception e) {
                patientNom = "Non trouvé";
            }
        }
        
        model.addRow(new Object[]{
            rdv.getIdRDV(),
            dateStr,
            heureStr,
            rdv.getMotif() != null ? rdv.getMotif() : "",
            rdv.getNoteMedecin() != null ? rdv.getNoteMedecin() : "",
            rdv.getIdDossier(),
            patientNom
        });
    }
    
    public void refresh() {
        loadRDVs();
    }
}



