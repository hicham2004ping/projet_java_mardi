package ma.prodenta.mvc.ui.admin;

import ma.prodenta.mvc.controllers.modules.admin.AdminAuthController;
import ma.prodenta.mvc.dto.admin.AdminViewDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 fenetre connexion admin
 */
public class AdminLoginFrame extends JFrame {

    private final AdminAuthController authController;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginFrame(AdminAuthController authController) {
        this.authController = authController;
        initComponents();
    }

    private void initComponents() {
        setTitle("Connexion - Administration Cabinet Dentaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Nom d'utilisateur :");
        JLabel passwordLabel = new JLabel("Mot de passe :");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Se connecter");
        loginButton.addActionListener(this::onLoginClicked);

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        add(panel);
    }

    private void onLoginClicked(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        AdminViewDto admin = authController.login(username, password, this);
        if (admin != null) {
            // Ouvrir le dashboard admin
            SwingUtilities.invokeLater(() -> {
                AdminDashboardFrame dashboard = new AdminDashboardFrame(admin);
                dashboard.setVisible(true);
            });
            dispose(); // Ferme la fenêtre de login
        }
    }
}

