package ma.prodenta.mvc.ui.palette.dashboard;

import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.mvc.ui.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {

    private Dashboard_view dashboard;
    private Integer userRoleId;

    public Sidebar(Dashboard_view dashboard, Integer userRoleId) {
        this.dashboard = dashboard;
        this.userRoleId = userRoleId;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(50, 50, 50));
        setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        setPreferredSize(new Dimension(230, 800));

        JLabel titleLabel = new JLabel("Prodenta");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        if (isAdmin()) {
            addAdminButtons(buttonPanel);
        } else {
            addUserButtons(buttonPanel);
        }

        add(buttonPanel);
    }

    private void addUserButtons(JPanel panel) {

        JButton dashboardBtn = createButton("Dashboard");
        JButton rdvBtn = createButton("Rendez-vous");
        JButton patientsBtn = createButton("Patients");
        JButton consultationBtn = createButton("Consultations");
        JButton gererConsultBtn = createButton("Gérer Consultations");
        JButton fileAttenteBtn = createButton("File d'attente");
        JButton caisseBtn = createButton("Caisse");
        JButton situationBtn = createButton("Situation financière");
        JButton factureBtn = createButton("Factures");
        JButton agendaBtn = createButton("Agenda");

        dashboardBtn.addActionListener(e ->
                dashboard.afficher_Panel("dashboard")
        );

        rdvBtn.addActionListener(e ->
                dashboard.afficher_Panel("rdv")
        );

        patientsBtn.addActionListener(e ->
                dashboard.afficher_Panel("patients")
        );

        consultationBtn.addActionListener(e -> {
            if (isMedecin()) {
                dashboard.afficher_Panel("consultation_medecin");
            } else {
                dashboard.afficher_Panel("consultation_secretaire");
            }
        });

        gererConsultBtn.addActionListener(e ->
                dashboard.afficher_Panel("Dossier medical")
        );

        fileAttenteBtn.addActionListener(e ->
                dashboard.afficher_Panel("file_attente")
        );

        caisseBtn.addActionListener(e ->
                dashboard.afficher_Panel("caisse")
        );

        situationBtn.addActionListener(e ->
                dashboard.afficher_Panel("statistics")
        );

        factureBtn.addActionListener(e ->
                dashboard.afficher_Panel("facture")
        );

        agendaBtn.addActionListener(e ->
                dashboard.afficher_Panel("agenda")
        );

        panel.add(dashboardBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(rdvBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(agendaBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(patientsBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(consultationBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(gererConsultBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(fileAttenteBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(caisseBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(situationBtn);
        panel.add(Box.createVerticalStrut(8));
        panel.add(factureBtn);

        panel.add(Box.createVerticalGlue());
        addLogoutButton(panel);
    }

    private void addAdminButtons(JPanel panel) {

        JButton usersBtn = createButton("Utilisateurs");
        JButton logsBtn = createButton("Journaux d'Audit");

        usersBtn.addActionListener(e ->
                dashboard.afficher_Panel("utilisateurs")
        );

        logsBtn.addActionListener(e ->
                dashboard.afficher_Panel("logs")
        );

        panel.add(usersBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(logsBtn);
        panel.add(Box.createVerticalGlue());

        addLogoutButton(panel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 150)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addLogoutButton(JPanel panel) {
        JButton logoutBtn = createButton("Déconnexion");
        logoutBtn.setBackground(new Color(180, 60, 60));
        logoutBtn.addActionListener(e -> handleLogout());
        panel.add(logoutBtn);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vous déconnecter ?",
                "Déconnexion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) frame.dispose();
            new LoginFrame();
        }
    }
    private boolean isMedecin() {
        return userRoleId != null && userRoleId == 1;
    }
    private boolean isSecretaire() {
        return userRoleId != null && userRoleId == 2;
    }
    private boolean isAdmin() {
        return userRoleId != null && userRoleId == 3;
    }



}
