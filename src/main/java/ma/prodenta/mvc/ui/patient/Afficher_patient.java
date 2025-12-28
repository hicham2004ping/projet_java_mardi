package ma.prodenta.mvc.ui.patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Afficher_patient extends JPanel {
    JButton ajouter_Patient;
    JButton supprimer_Patient;
    JButton modifer_Patient;
    JButton afficher_Detailles;
    public Afficher_patient() {
        ajouter_Patient=new JButton("Ajouter Patient");
        supprimer_Patient=new JButton("Upprimer Patient");
        modifer_Patient=new JButton("Modifier Patient");
        afficher_Detailles=new JButton("Afficher_Detailles");
        JTable table=new JTable();
        String[] noms_colonnes={"Id","Nom","Prenom","Date naissance","DateCreation formatee"};
        DefaultTableModel model=new DefaultTableModel(noms_colonnes,0);
        table.setModel(model);
        add(ajouter_Patient);
        add(supprimer_Patient);
        add(modifer_Patient);
        add(afficher_Detailles);
    }
}