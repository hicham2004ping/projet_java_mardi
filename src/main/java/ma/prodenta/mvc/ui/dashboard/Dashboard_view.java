package ma.prodenta.mvc.ui.dashboard;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

import ma.prodenta.mvc.ui.dossier.Afficher_DossierMedical;
import ma.prodenta.mvc.ui.dossier.Detailles_DossierMedical;
import ma.prodenta.mvc.ui.palette.dashboard.Sidebar;
import ma.prodenta.mvc.ui.palette.dashboard.Header_bar;
import ma.prodenta.mvc.ui.patient.Afficher_patient;
import ma.prodenta.mvc.ui.patient.Ajouter_patient;

public class Dashboard_view extends JFrame
{
    CardLayout cardLayout;
    JPanel contenu_Centre;
    Afficher_patient afficherPatient;
    public Dashboard_view() throws Exception {
        contenu_Centre=new JPanel();
        cardLayout=new CardLayout();
        this.afficherPatient=new Afficher_patient(this);

        contenu_Centre.setLayout(cardLayout);
        contenu_Centre.add(new Afficher_DossierMedical(),"Dossier medical");
        contenu_Centre.add(afficherPatient,"patients");
        contenu_Centre.add(new Ajouter_patient(this),"ajouter_patient");

        JPanel p=new Sidebar(this);
        JPanel p1=new Header_bar();
        setTitle("Dashboard");
        setSize(1200,800);
        URL iconURL = getClass().getResource("/static/images/icones/logo.png");
        System.out.println("le chemin c'est "+iconURL);
        if (iconURL != null) {
            System.out.println("on est ici ");
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        }
            add(p,BorderLayout.WEST);
        add(p1,BorderLayout.NORTH);
        add(contenu_Centre,BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void afficher_Panel(String nom){
        System.out.println("cette fonction a ete appeler et le nom c'est  "+nom);
        this.cardLayout.show(contenu_Centre,nom);
    }
    public void afficherDetailsDossier(int idDossier) {
        Detailles_DossierMedical panel = new Detailles_DossierMedical(idDossier);
        contenu_Centre.add(panel, "details");
        cardLayout.show(contenu_Centre, "details");
    }
    public Afficher_patient getAfficherPatient(){
        return afficherPatient;
    }

}