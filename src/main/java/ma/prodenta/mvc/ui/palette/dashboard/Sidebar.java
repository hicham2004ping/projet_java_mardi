package ma.prodenta.mvc.ui.palette.dashboard;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    JButton dashboard, rendez_vous, patients, agenda, caise,dossier_medical,log_out,users;

    public Sidebar(Dashboard_view view) {
        setLayout(new GridLayout(8, 1));
        dashboard = new JButton("Dashboard");
        rendez_vous = new JButton("Rendez vous");
        patients = new JButton("Patients");
        agenda = new JButton("Agenda");
        caise = new JButton("Caise");
        dossier_medical = new JButton("Dossier medical");
        log_out=new JButton("Logout");
        users=new JButton("Utilisateurs");
        add(dashboard);
        add(rendez_vous);

        add(patients);
        patients.addActionListener(e ->{
            System.out.println("le boutton du patient a ete clicker");
            view.afficher_Panel("patients");
        });

        add(dossier_medical);
        dossier_medical.addActionListener(e ->{
            System.out.println("le boutton du dossier medical a ete clicker");
            view.afficher_Panel("Dossier medical");
        });

        add(agenda);
        add(caise);
        add(users);
        add(log_out);
        setPreferredSize(new Dimension(300,500));
    }
}
