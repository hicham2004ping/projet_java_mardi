package ma.prodenta.mvc.ui.palette.dashboard;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {
    public Sidebar() {
        setLayout(new GridLayout(8, 1));
        JButton dashboard = new JButton("Dashboard");
        JButton rendez_vous = new JButton("Rendez vous");
        JButton patients = new JButton("Patients");
        JButton agenda = new JButton("Agenda");
        JButton caise = new JButton("Caise");
        JButton dossier_medical = new JButton("Dossier medical");
        JButton log_out=new JButton("Logout");
        JButton users=new JButton("Utilisateurs");
        add(dashboard);
        add(rendez_vous);
        add(patients);
        add(agenda);
        add(caise);
        add(dossier_medical);
        add(users);
        add(log_out);
        setPreferredSize(new Dimension(300,500));
    }
}
