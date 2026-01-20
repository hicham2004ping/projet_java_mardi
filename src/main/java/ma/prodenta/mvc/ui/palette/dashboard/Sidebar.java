package ma.prodenta.mvc.ui.palette.dashboard;

import ma.prodenta.common.util.UserSession;
import ma.prodenta.mvc.ui.auth.LoginFrame;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class Sidebar extends JPanel {

    private final Dashboard_view dashboard;
    private Integer userRoleId;

    public Sidebar(Dashboard_view dashboard, Integer userRoleId) {
        this.dashboard = dashboard;

        // Fallback to session role if not passed
        this.userRoleId = (userRoleId != null)
                ? userRoleId
                : UserSession.getInstance().getCurrentRoleId();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        setPreferredSize(new Dimension(220, 800));

        // Header avec logo agrandi
        add(createHeaderPanel());
        add(Box.createVerticalStrut(35));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Role-based menu
        if (isAdmin()) {
            addAdminButtons(buttonPanel);
        } else if (isSecretaire()) {
            addSecretaireButtons(buttonPanel);
        } else if (isMedecin()) {
            addMedecinButtons(buttonPanel);
        } else {
            addMinimalButtons(buttonPanel);
        }

        add(buttonPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        
        // Essayer de charger le logo depuis les ressources
        JLabel logoLabel = new JLabel();
        try {
            URL logoURL = getClass().getResource("/static/images/icones/logo.png");
            if (logoURL != null) {
                ImageIcon logoIcon = new ImageIcon(logoURL);
                // Redimensionner le logo à une taille plus grande
                Image scaledImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                // Fallback: emoji
                logoLabel.setText("🦷");
                logoLabel.setFont(new Font("Arial", Font.PLAIN, 64));
            }
        } catch (Exception e) {
            // Fallback: emoji
            logoLabel.setText("🦷");
            logoLabel.setFont(new Font("Arial", Font.PLAIN, 64));
        }
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("ProDenta");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(30, 120, 170));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Cabinet Médical");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        subtitleLabel.setForeground(new Color(100, 120, 140));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(4));
        headerPanel.add(subtitleLabel);
        
        return headerPanel;
    }

    // Menus by role
    private void addMedecinButtons(JPanel panel) {
        addMenuButton(panel, "📊 Dashboard", e -> dashboard.afficher_Panel("dashboard"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "📅 Agenda", e -> dashboard.afficher_Panel("agenda"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "👥 Patients", e -> dashboard.afficher_Panel("patients"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "🏥 Consultations", e -> dashboard.afficher_Panel("consultation_medecin"));

        panel.add(Box.createVerticalGlue());
        addLogoutButton(panel);
    }

    private void addSecretaireButtons(JPanel panel) {
        addMenuButton(panel, "📊 Dashboard", e -> dashboard.afficher_Panel("dashboard"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "📅 Rendez-vous", e -> dashboard.afficher_Panel("rdv"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "👥 Patients", e -> dashboard.afficher_Panel("patients"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "📆 Agenda", e -> dashboard.afficher_Panel("agenda"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "⏳ File d'attente", e -> dashboard.afficher_Panel("file_attente"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "💰 Caisse", e -> dashboard.afficher_Panel("caisse"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "📋 Dossier médical", e -> dashboard.afficher_Panel("Dossier medical"));

        panel.add(Box.createVerticalGlue());
        addLogoutButton(panel);
    }

    private void addAdminButtons(JPanel panel) {
        addMenuButton(panel, "👤 Utilisateurs", e -> dashboard.afficher_Panel("utilisateurs"));
        panel.add(Box.createVerticalStrut(8));
        addMenuButton(panel, "📊 Journaux d'Audit", e -> dashboard.afficher_Panel("logs"));

        panel.add(Box.createVerticalGlue());
        addLogoutButton(panel);
    }

    private void addMinimalButtons(JPanel panel) {
        addMenuButton(panel, "📊 Dashboard", e -> dashboard.afficher_Panel("dashboard"));
        panel.add(Box.createVerticalGlue());
        addLogoutButton(panel);
    }

    private void addMenuButton(JPanel panel, String text, ActionListener action) {
        JButton button = createButton(text);
        button.addActionListener(action);
        panel.add(button);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(190, 45));
        button.setPreferredSize(new Dimension(190, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(new Color(245, 247, 250));
        button.setForeground(new Color(40, 120, 160));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220), 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(true);

        // Hover effect avec couleurs assortis
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(30, 120, 170));
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(new Color(20, 100, 150), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(245, 247, 250));
                button.setForeground(new Color(40, 120, 160));
                button.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220), 2));
            }
        });

        return button;
    }

    private void addLogoutButton(JPanel panel) {
        JButton logoutBtn = new JButton("🔓 Log Out");
        logoutBtn.setMaximumSize(new Dimension(190, 45));
        logoutBtn.setPreferredSize(new Dimension(190, 45));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setBackground(new Color(255, 100, 100));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutBtn.setBorder(BorderFactory.createLineBorder(new Color(220, 80, 80), 2));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setContentAreaFilled(true);

        logoutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutBtn.setBackground(new Color(220, 80, 80));
                logoutBtn.setBorder(BorderFactory.createLineBorder(new Color(180, 60, 60), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutBtn.setBackground(new Color(255, 100, 100));
                logoutBtn.setBorder(BorderFactory.createLineBorder(new Color(220, 80, 80), 2));
            }
        });

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
            //Clear session
            UserSession.getInstance().clear();

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) frame.dispose();

            new LoginFrame();
        }
    }

    // Role checks
    private Integer getRoleId() {
        return (userRoleId != null) ? userRoleId : UserSession.getInstance().getCurrentRoleId();
    }

    private boolean isMedecin() {
        Integer role = getRoleId();
        return role != null && role == 1;
    }

    private boolean isSecretaire() {
        Integer role = getRoleId();
        return role != null && role == 2;
    }

    private boolean isAdmin() {
        Integer role = getRoleId();
        return role != null && role == 3;
    }
}
