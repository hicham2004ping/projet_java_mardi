package ma.prodenta.mvc.ui.dossier;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.dto.dossiermedical.Dossier_Medical_vu_generale_DTO;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import java.awt.*;
import java.util.List;

public class Afficher_DossierMedical extends JPanel {
    DossierMedicalController controller;
    private Dashboard_view dashboard;

    public Afficher_DossierMedical() throws Exception {
        this(null);
    }

    public Afficher_DossierMedical(Dashboard_view dashboard) throws Exception {
        this.dashboard = dashboard;
        controller = Application_contexte.getDossierMedicalController();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        List<Dossier_Medical_vu_generale_DTO> dossiers = Application_contexte.getDossierMedicalController().find_all_view();
        String[] noms_colonnes = {"Id", "Nom", "Prenom", "Date Creation", "Total consultations", "Total ordonnances"};
        DefaultTableModel model = new DefaultTableModel(noms_colonnes, 0);

        for (Dossier_Medical_vu_generale_DTO dossier : dossiers) {
            Object[] ligne = new Object[6];
            ligne[0] = dossier.getIdDossier();
            ligne[1] = dossier.getPatient_nom();
            ligne[2] = dossier.getPatient_prenom();
            ligne[3] = dossier.getDate_creation();
            ligne[4] = dossier.getTotal_conusltations();
            ligne[5] = dossier.getTotal_ordonnance();
            model.addRow(ligne);
        }

        JTable tableDossier = new JTable(model);
        TableColumn colonne_id = tableDossier.getColumnModel().getColumn(0);
        colonne_id.setMinWidth(0);
        colonne_id.setMaxWidth(0);
        colonne_id.setResizable(false);
        colonne_id.setPreferredWidth(0);
        tableDossier.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollDossier = new JScrollPane(tableDossier);
        scrollDossier.setPreferredSize(new Dimension(800, 400));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weighty = 0.9;
        add(scrollDossier, gbc);

        JButton supprimer_dossier = new JButton("Supprimer dossier");
        JButton detailles = new JButton("Detailles dossier");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.add(supprimer_dossier);
        buttonPanel.add(detailles);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        supprimer_dossier.addActionListener(e -> {
            int indice=tableDossier.getSelectedRow();
            if(indice!=-1){
                int id=Integer.parseInt(tableDossier.getValueAt(indice,0).toString());
                try{
                    this.controller.supprimer_dossier(id);
                    model.removeRow(indice);
                    JOptionPane.showMessageDialog(this,"suppression avec success du dossier Medical","Validation",JOptionPane.INFORMATION_MESSAGE);
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(this,ex.getMessage(),"erruer",JOptionPane.ERROR_MESSAGE);
                }

            }
            else{
                JOptionPane.showMessageDialog(this,"vous devez selectionner une ligne ","impossible de supprimer un dossier",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        detailles.addActionListener(e -> {
            int indice=tableDossier.getSelectedRow();
            if(indice==-1){
                JOptionPane.showMessageDialog(this,"avant de lister les detailles d'un dossier vous devez selectioner un dossier ","erruer",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                int idDossier=Integer.parseInt(tableDossier.getValueAt(indice,0).toString());
                try {
                    if (dashboard != null) {
                        dashboard.afficherDetailsDossier(idDossier);
                    } else {
                        new Dashboard_view().afficherDetailsDossier(idDossier);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(this,
                            "Erreur: " + ex.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
