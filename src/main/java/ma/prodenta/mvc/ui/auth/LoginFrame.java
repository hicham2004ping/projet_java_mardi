package ma.prodenta.mvc.ui.auth;
import ma.prodenta.common.exceptions.AuthException;
import ma.prodenta.common.exceptions.ErreurLectureException;
import ma.prodenta.common.validators.AuthValidator;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.mvc.controllers.modules.auth.impl.AuthControlleur_Impl;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.service.common.validateur.email.validateur_email;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel errorLabel;
    private JLabel infoLabel;
    private JCheckBox rememberMeCheckBox;
    private AuthControlleur_Impl authController;

    public LoginFrame() {
        setTitle("Prodenta - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(LoginFrame.class.getResource("/static/images/icones/logo.png")).getImage());

        try {
            authController = new AuthControlleur_Impl();
        } catch (Exception e) {
            showError("Erreur d'initialisation: " + e.getMessage());
        }

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Title
        JLabel titleLabel = new JLabel("Connexion Prodenta");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Entrez vos identifiants");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        // Email field
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.setOpaque(false);
        JLabel emailLabel = new JLabel("Email ou Identifiant:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        emailField.setFont(new Font("Arial", Font.PLAIN, 12));
        emailField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        emailPanel.add(emailLabel);
        emailPanel.add(Box.createVerticalStrut(5));
        emailPanel.add(emailField);
        mainPanel.add(emailPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Password field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setOpaque(false);
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createVerticalStrut(5));
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Remember me checkbox
        rememberMeCheckBox = new JCheckBox("Se souvenir de moi");
        rememberMeCheckBox.setFont(new Font("Arial", Font.PLAIN, 11));
        rememberMeCheckBox.setOpaque(false);
        rememberMeCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(rememberMeCheckBox);
        mainPanel.add(Box.createVerticalStrut(20));

        // Error label
        errorLabel = new JLabel();
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        errorLabel.setForeground(new Color(220, 20, 60));
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(errorLabel);

        // Info label
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(34, 139, 34));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);

        clearButton = new JButton("Effacer");
        clearButton.setPreferredSize(new Dimension(120, 40));
        clearButton.setFont(new Font("Arial", Font.PLAIN, 12));
        clearButton.setBackground(new Color(200, 200, 200));
        clearButton.setForeground(Color.BLACK);
        clearButton.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(this::handleClear);

        loginButton = new JButton("Se connecter");
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185)));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this::handleLogin);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (passwordField.hasFocus()) {
                    handleLogin(null);
                    return true;
                }
            }
            return false;
        });

        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(loginButton);
        mainPanel.add(buttonPanel);

        add(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        clearMessages();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        try {
            if (email.isEmpty()) {
                showError("L'email ou l'identifiant est requis");
                emailField.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                showError("Le mot de passe est requis");
                passwordField.requestFocus();
                return;
            }

            AuthValidator.validatePassword(password);

            infoLabel.setText("Connexion en cours...");
            loginButton.setEnabled(false);

            Utilisateur user = authenticateUser(email, password);

            if (user != null) {
                showInfo("Connexion réussie!");
                SwingUtilities.invokeLater(() -> {
                    try {
                        new Dashboard_view(user.getIdRole());
                        dispose();
                    } catch (Exception ex) {
                        showError("Erreur lors de l'ouverture du tableau de bord: " + ex.getMessage());
                        loginButton.setEnabled(true);
                    }
                });
            } else {
                showError("Email/Identifiant ou mot de passe incorrect");
                passwordField.setText("");
                passwordField.requestFocus();
                loginButton.setEnabled(true);
            }
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
            loginButton.setEnabled(true);
        } catch (AuthException ex) {
            showError("Authentification échouée: " + ex.getMessage());
            loginButton.setEnabled(true);
        } catch (Exception ex) {
            showError("Erreur: " + ex.getMessage());
            loginButton.setEnabled(true);
        }
    }

    /**
     * Try to authenticate with email first, then with login
     */
    private Utilisateur authenticateUser(String emailOrLogin, String password) throws Exception {
        try {
            // Try with email if it looks like an email
            if (validateur_email.is_valid(emailOrLogin)) {
                return authController.authenticate(emailOrLogin, password);
            }

            // Otherwise try with login directly
            return authController.authenticate(emailOrLogin, password);
        } catch (AuthException | ErreurLectureException ex) {
            return null;
        }
    }

    private void handleClear(ActionEvent e) {
        emailField.setText("");
        passwordField.setText("");
        clearMessages();
        emailField.requestFocus();
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setForeground(new Color(220, 20, 60));
        infoLabel.setText("");
    }

    private void showInfo(String message) {
        infoLabel.setText(message);
        infoLabel.setForeground(new Color(34, 139, 34));
        errorLabel.setText("");
    }

    private void clearMessages() {
        errorLabel.setText("");
        infoLabel.setText("");
    }
}
