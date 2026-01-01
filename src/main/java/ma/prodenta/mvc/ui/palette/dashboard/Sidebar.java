package ma.prodenta.mvc.ui.palette.dashboard;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    JButton dashboard, rendez_vous, patients, agenda, caisse, dossier_medical, log_out, users, consultation;

    public Sidebar(Dashboard_view view) {
        setLayout(new GridLayout(9, 1));
        consultation = new JButton("Consultation");
        dashboard = new JButton("Dashboard");
        rendez_vous = new JButton("Rendez vous");
        patients = new JButton("Patients");
        agenda = new JButton("Agenda");
        caisse = new JButton("Caisse");
        dossier_medical = new JButton("Dossier medical");
        log_out = new JButton("Logout");
        users = new JButton("Utilisateurs");
        
        add(dashboard);
        dashboard.addActionListener(e -> view.afficher_Panel("dashboard"));
        
        add(rendez_vous);
        rendez_vous.addActionListener(e -> view.afficher_Panel("rendezvous"));
        
        add(consultation);
        consultation.addActionListener(e -> view.afficher_Panel("consultation"));
        
        add(patients);
        patients.addActionListener(e -> {
            System.out.println("le boutton du patient a ete clicker");
            view.afficher_Panel("patients");
        });

        add(dossier_medical);
        dossier_medical.addActionListener(e -> {
            System.out.println("le boutton du dossier medical a ete clicker");
            view.afficher_Panel("Dossier medical");
        });

        add(agenda);
        agenda.addActionListener(e -> view.afficher_Panel("agenda"));
        
        add(caisse);
        caisse.addActionListener(e -> view.afficher_Panel("caisse"));
        
        add(users);
        users.addActionListener(e -> view.afficher_Panel("admin"));
        
        add(log_out);

        setPreferredSize(new Dimension(300, 500));
    }
}
