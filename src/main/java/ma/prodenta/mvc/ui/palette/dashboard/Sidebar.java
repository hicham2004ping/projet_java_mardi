package ma.prodenta.mvc.ui.palette.dashboard;
import ma.prodenta.mvc.ui.consultation.ConsultationFrame;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    JButton dashboard, rendez_vous, patients, agenda, caise, dossier_medical, log_out, users, consultation, file_attente, gerer_consultations;
    private Dashboard_view view;
    private Integer userRoleId;

    public Sidebar(Dashboard_view view) {
        this(view, null);
    }

    public Sidebar(Dashboard_view view, Integer userRoleId) {
        this.view = view;
        this.userRoleId = userRoleId;

        initializeButtons();
        setupLayout();
        setupActions();
    }

    private void initializeButtons() {
        dashboard = new JButton("Dashboard");
        rendez_vous = new JButton("Rendez vous");
        patients = new JButton("Patients");
        agenda = new JButton("Agenda");
        caise = new JButton("Caise");
        dossier_medical = new JButton("Dossier medical");
        log_out = new JButton("Logout");
        users = new JButton("Utilisateurs");
        consultation = new JButton("Consultation");
        file_attente = new JButton("File d'attente");
        gerer_consultations = new JButton("Gérer Consultations");
    }

    private void setupLayout() {
        // Calculer le nombre de boutons selon le rôle
        int buttonCount = 9; // Base: dashboard, patients, dossier_medical, agenda, caise, users, log_out

        if (isSecretaire()) {
            buttonCount += 4; // rendez_vous, file_attente, consultation, gerer_consultations
        } else if (isMedecin()) {
            buttonCount += 1; // consultation
        } else {
            buttonCount += 2; // rendez_vous, consultation (par défaut)
        }

        setLayout(new GridLayout(buttonCount, 1));

        // Boutons communs
        add(dashboard);
        add(patients);
        add(dossier_medical);

        // Boutons selon le rôle
        if (isSecretaire()) {
            add(rendez_vous);
            add(file_attente);
            add(consultation);
            add(gerer_consultations);
        } else if (isMedecin()) {
            add(consultation);
        } else {
            // Par défaut, afficher tous
            add(rendez_vous);
            add(consultation);
        }

        // Boutons communs (fin)
        add(agenda);
        add(caise);
        add(users);
        add(log_out);

        setPreferredSize(new Dimension(300, 500));
    }

    private void setupActions() {
        patients.addActionListener(e -> {
            System.out.println("le boutton du patient a ete clicker");
            view.afficher_Panel("patients");
        });

        dossier_medical.addActionListener(e -> {
            System.out.println("le boutton du dossier medical a ete clicker");
            view.afficher_Panel("Dossier medical");
        });

        if (isSecretaire()) {
            rendez_vous.addActionListener(e -> openRDVFrame());
            file_attente.addActionListener(e -> openFileAttenteFrame());
            consultation.addActionListener(e -> openConsultationSecretaireFrame());
            gerer_consultations.addActionListener(e -> openGererConsultations());
        } else if (isMedecin()) {
            consultation.addActionListener(e -> openConsultationMedecinFrame());
        } else {
            rendez_vous.addActionListener(e -> openConsultation());
            consultation.addActionListener(e -> openConsultation());
        }
    }

    private boolean isSecretaire() {
        return userRoleId != null && userRoleId == 2;
    }

    private boolean isMedecin() {
        return userRoleId != null && userRoleId == 1;
    }

    private void openRDVFrame() {
        view.afficher_Panel("rdv");
    }

    private void openFileAttenteFrame() {
        view.afficher_Panel("file_attente");
    }

    private void openGererConsultations() {
        // Afficher la liste des dossiers médicaux pour gérer les consultations
        view.afficher_Panel("Dossier medical");
    }

    private void openConsultationMedecinFrame() {
        view.afficher_Panel("consultation_medecin");
    }

    private void openConsultationSecretaireFrame() {
        view.afficher_Panel("consultation_secretaire");
    }

    private void openConsultation() {
        new ConsultationFrame();
        setVisible(true);
    }
}
