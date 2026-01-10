package ma.prodenta.mvc.ui.dashboard;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

import ma.prodenta.mvc.ui.dossier.Afficher_DossierMedical;
import ma.prodenta.mvc.ui.dossier.Detailles_DossierMedical;
import ma.prodenta.mvc.ui.dossier.DossierMedicalView;
import ma.prodenta.mvc.ui.palette.dashboard.Sidebar;
import ma.prodenta.mvc.ui.palette.dashboard.Header_bar;
import ma.prodenta.mvc.ui.patient.Afficher_patient;
import ma.prodenta.mvc.ui.patient.Ajouter_patient;
import ma.prodenta.mvc.ui.rdv.RDVFrame;
import ma.prodenta.mvc.ui.fileattente.FileAttenteFrame;
import ma.prodenta.mvc.ui.fileattente.ConsultationMedecinFrame;

public class Dashboard_view extends JFrame
{
    CardLayout cardLayout;
    JPanel contenu_Centre;
    Afficher_patient afficherPatient;
    private Integer userRoleId;
    private FileAttenteFrame fileAttenteFrame;
    private RDVFrame rdvFrame;

    public Dashboard_view() throws Exception {
        this(null);
    }

    public Dashboard_view(Integer userRoleId) throws Exception {
        this.userRoleId = userRoleId;
        contenu_Centre=new JPanel();
        cardLayout=new CardLayout();
        this.afficherPatient=new Afficher_patient(this);

        contenu_Centre.setLayout(cardLayout);
        contenu_Centre.add(new Afficher_DossierMedical(this),"Dossier medical");
        contenu_Centre.add(afficherPatient,"patients");
        contenu_Centre.add(new Ajouter_patient(this),"ajouter_patient");

        // Ajouter les panneaux selon le rôle
        if (userRoleId != null && userRoleId == 2) { // Secrétaire
            this.fileAttenteFrame = new FileAttenteFrame(this);
            this.rdvFrame = new RDVFrame(this);
            contenu_Centre.add(fileAttenteFrame, "file_attente");
            contenu_Centre.add(rdvFrame, "rdv");
            // Ajouter aussi la vue consultation pour la secrétaire (pour tester)
            ConsultationMedecinFrame consultationSecretaire = new ConsultationMedecinFrame(this, fileAttenteFrame);
            contenu_Centre.add(consultationSecretaire, "consultation_secretaire");
        }

        if (userRoleId != null && userRoleId == 1) { // Médecin
            if (fileAttenteFrame == null) {
                // Si pas de file d'attente, créer une instance vide pour le médecin
                this.fileAttenteFrame = new FileAttenteFrame(this);
            }
            ConsultationMedecinFrame consultationMedecin = new ConsultationMedecinFrame(this, fileAttenteFrame);
            contenu_Centre.add(consultationMedecin, "consultation_medecin");
        }

        JPanel p=new Sidebar(this, userRoleId);
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
        try {
            DossierMedicalView panel = new DossierMedicalView(idDossier);
            String cardName = "dossier_" + idDossier;
            contenu_Centre.add(panel, cardName);
            cardLayout.show(contenu_Centre, cardName);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'ouverture du dossier: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public Afficher_patient getAfficherPatient(){
        return afficherPatient;
    }

    public FileAttenteFrame getFileAttenteFrame() {
        return fileAttenteFrame;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

}