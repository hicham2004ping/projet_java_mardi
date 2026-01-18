package ma.prodenta.mvc.ui.dashboard;

import ma.prodenta.common.util.UserSession;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.ui.facture.FacturePanel;
import ma.prodenta.mvc.ui.ordonance.OrdonnancePanel;
import ma.prodenta.mvc.ui.caisse.CaissePanel;
import ma.prodenta.mvc.ui.patient.ModifierPatient;
import ma.prodenta.mvc.ui.statistics.StatisticsPanel;
import ma.prodenta.mvc.ui.admin.UtilisateursPanel;
import ma.prodenta.mvc.ui.admin.AuditLogsPanel;
import ma.prodenta.mvc.ui.dossier.Afficher_DossierMedical;
import ma.prodenta.mvc.ui.dossier.DossierMedicalView;
import ma.prodenta.mvc.ui.fileattente.ConsultationMedecinFrame;
import ma.prodenta.mvc.ui.fileattente.FileAttenteFrame;
import ma.prodenta.mvc.ui.palette.dashboard.Header_bar;
import ma.prodenta.mvc.ui.palette.dashboard.Sidebar;
import ma.prodenta.mvc.ui.patient.Afficher_patient;
import ma.prodenta.mvc.ui.patient.Ajouter_patient;
import ma.prodenta.mvc.ui.rdv.RDVFrame;
import ma.prodenta.mvc.ui.agenda.AgendaFrame;
import ma.prodenta.mvc.ui.acte.ActePanel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Dashboard_view extends JFrame {
    public CardLayout cardLayout;
    public JPanel contenu_Centre;
    Afficher_patient afficherPatient;
    private Integer userRoleId;

    private FileAttenteFrame fileAttenteFrame;
    private RDVFrame rdvFrame;
    private FacturePanel facturePanel;
    private OrdonnancePanel ordonnancePanel;
    private CaissePanel caissePanel;
    private StatisticsPanel statisticsPanel;

    public Dashboard_view() throws Exception {
        this(null);
    }

    public Dashboard_view(Integer userRoleId) throws Exception {

        //fallback
        this.userRoleId = (userRoleId != null)
                ? userRoleId
                : UserSession.getInstance().getCurrentRoleId();

        contenu_Centre = new JPanel();
        cardLayout = new CardLayout();
        this.afficherPatient = new Afficher_patient(this);

        contenu_Centre.setLayout(cardLayout);
        contenu_Centre.add(new Afficher_DossierMedical(this), "Dossier medical");
        contenu_Centre.add(afficherPatient, "patients");
        contenu_Centre.add(new Ajouter_patient(this), "ajouter_patient");

        this.facturePanel = new FacturePanel();
        this.ordonnancePanel = new OrdonnancePanel();
        this.caissePanel = new CaissePanel();
        this.statisticsPanel = new StatisticsPanel(this);

        contenu_Centre.add(facturePanel, "facture");
        contenu_Centre.add(ordonnancePanel, "ordonnance");
        contenu_Centre.add(caissePanel, "caisse");
        contenu_Centre.add(statisticsPanel, "statistics");
        // up up
        if (this.userRoleId != null && this.userRoleId == 3) {
            UtilisateursPanel utilisateursPanel = new UtilisateursPanel();
            AuditLogsPanel auditLogsPanel = new AuditLogsPanel();
            contenu_Centre.add(utilisateursPanel, "utilisateurs");
            contenu_Centre.add(auditLogsPanel, "logs");
        }

        if (this.userRoleId != null && this.userRoleId == 2) { // Secrétaire
            this.fileAttenteFrame = new FileAttenteFrame(this);
            this.rdvFrame = new RDVFrame(this);
            contenu_Centre.add(fileAttenteFrame, "file_attente");
            contenu_Centre.add(rdvFrame, "rdv");

            ConsultationMedecinFrame consultationSecretaire = new ConsultationMedecinFrame(this, fileAttenteFrame);
            contenu_Centre.add(consultationSecretaire, "consultation_secretaire");
        }

        if (this.userRoleId != null && this.userRoleId == 1) { // Médecin
            if (fileAttenteFrame == null) {
                this.fileAttenteFrame = new FileAttenteFrame(this);
            }
            ConsultationMedecinFrame consultationMedecin = new ConsultationMedecinFrame(this, fileAttenteFrame);
            contenu_Centre.add(consultationMedecin, "consultation_medecin");
        }

        // tout le monde peut voir agenda + actes mais pas admin
        if (this.userRoleId != null && this.userRoleId != 3) {
            AgendaFrame agendaFrame = new AgendaFrame(this);
            contenu_Centre.add(agendaFrame, "agenda");
            ActePanel actePanel = new ActePanel();
            contenu_Centre.add(actePanel, "actes");
        }

        JPanel p = new Sidebar(this, this.userRoleId);
        JPanel p1 = new Header_bar();

        setTitle("Dashboard");
        setSize(1200, 800);

        URL iconURL = getClass().getResource("/static/images/icones/logo.png");
        System.out.println("le chemin c'est " + iconURL);
        if (iconURL != null) {
            System.out.println("on est ici ");
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        }

        add(p, BorderLayout.WEST);
        add(p1, BorderLayout.NORTH);
        add(contenu_Centre, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void afficher_Panel(String nom) {
        System.out.println("cette fonction a ete appeler et le nom c'est  " + nom);
        this.cardLayout.show(contenu_Centre, nom);
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

    public void ModifierPatient(Patient patient) throws Exception {
        System.out.println("on est dans la fonction modification patient");
        System.out.println("le nom du patient est" + patient.getNom());
        ModifierPatient modifierPatient = new ModifierPatient(patient);
        contenu_Centre.add(modifierPatient, "modifierPatient");
        cardLayout.show(contenu_Centre, "modifierPatient");
    }

    public Afficher_patient getAfficherPatient() {
        return afficherPatient;
    }

    public FileAttenteFrame getFileAttenteFrame() {
        return fileAttenteFrame;
    }
}
