package ma.prodenta.mvc.ui.patient;
import lombok.Builder;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Dashboard;
import ma.prodenta.mvc.dto.patient.PatientDTO;
import ma.prodenta.mvc.controllers.modules.patient.impl.Patient_Controlleur;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.List;

public class Afficher_patient extends JPanel {
    Patient_Controlleur controlleur;
    JButton ajouter_Patient;
    JButton supprimer_Patient;
    JButton modifer_Patient;
    JButton afficher_Detailles;
    Dashboard_view dashboard;

    public Afficher_patient(Dashboard_view view) {
        //declaration
        this.dashboard=view;
         this.controlleur= Application_contexte.getPatientControlleur();
        List<PatientDTO> list_patients;
        ajouter_Patient=new JButton("Ajouter Patient");
        supprimer_Patient=new JButton("Supprimer Patient");
        modifer_Patient=new JButton("Modifier Patient");
        afficher_Detailles=new JButton("Afficher_Detailles");
        JTable table=new JTable();

        //remplissage
        String[] noms_colonnes={"Id","Nom","Prenom","Date naissance"};
        DefaultTableModel model=new DefaultTableModel(noms_colonnes,0);
        table.setModel(model);
        list_patients=controlleur.afficher_tous();

        for(PatientDTO patient:list_patients){
            Object[] ligne=new Object[4];
            ligne[0]=patient.getId();
            ligne[1]=patient.getNom();
            ligne[2]=patient.getPrenom();
            ligne[3]=patient.getDate_naissance();
            model.addRow(ligne);
        }

        //cacher l'id du patient
        TableColumn column=table.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setResizable(false);

        //l'ajout des elements au panel
        JScrollPane scroll=new JScrollPane(table);
        add(ajouter_Patient);
        add(supprimer_Patient);
        add(modifer_Patient);
        add(afficher_Detailles);
        add(scroll);

        //l'ajout des listners sur les bouttons
        supprimer_Patient.addActionListener(e->{
            int ligne_selectionner=table.getSelectedRow();
            int id;
            if(ligne_selectionner!=-1){
               id =Integer.parseInt(table.getValueAt(ligne_selectionner,0).toString());
               System.out.println("l'id du user est "+id);
               int valeur=JOptionPane.showConfirmDialog(this,"vous voulez vraiment supprimer le patient","Supprimer patient",JOptionPane.YES_NO_OPTION);
               try{
                   if(valeur==0){
                       controlleur.supprimer_patient(id);
                        model.removeRow(ligne_selectionner);
                   }
               }
              catch (Exception e1){
                   System.out.println(e1.getMessage());
              }
            }
            else{
                JOptionPane.showMessageDialog(this,"vous devez selectionner un ligne avant de supprimer un patient");
            }
        });

        ajouter_Patient.addActionListener(e->{
            System.out.println("on est ici dans le listener ");
            view.afficher_Panel("ajouter_patient");
        });
    }
}