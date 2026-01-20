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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import ma.prodenta.common.util.UserSession;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel errorLabel;
    private JLabel infoLabel;
    private JCheckBox rememberMeCheckBox;
    private AuthControlleur_Impl authController;

    // Couleurs modernes
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final Color LEFT_PANEL_COLOR = new Color(250, 248, 245);
    private static final Color RIGHT_PANEL_COLOR = new Color(189, 215, 227);
    private static final Color PRIMARY_COLOR = new Color(0, 102, 102);
    private static final Color TEXT_COLOR = new Color(51, 51, 51);
    private static final Color PLACEHOLDER_COLOR = new Color(150, 150, 150);
    private static final Color LINK_COLOR = new Color(102, 51, 153);

    public LoginFrame() {
        setTitle("Prodenta - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
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
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Panneau gauche - Formulaire
        JPanel leftPanel = createLeftPanel();

        // Panneau droit - Illustration
        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(LEFT_PANEL_COLOR);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));

        // Titre principal
        JLabel titleLabel = new JLabel("Bienvenue utilisateur");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(40));

        // Champ Email/Nom utilisateur
        JLabel emailLabel = new JLabel("Nom utilisateur :");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(TEXT_COLOR);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(emailLabel);
        leftPanel.add(Box.createVerticalStrut(8));

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setForeground(PLACEHOLDER_COLOR);
        emailField.setText("Veuillez saisir le nom d'utilisateur...");
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        emailField.setBackground(Color.WHITE);

        // Placeholder functionality
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Veuillez saisir le nom d'utilisateur...")) {
                    emailField.setText("");
                    emailField.setForeground(TEXT_COLOR);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setForeground(PLACEHOLDER_COLOR);
                    emailField.setText("Veuillez saisir le nom d'utilisateur...");
                }
            }
        });

        leftPanel.add(emailField);
        leftPanel.add(Box.createVerticalStrut(25));

        // Champ Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(passwordLabel);
        leftPanel.add(Box.createVerticalStrut(8));

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setEchoChar((char) 0);
        passwordField.setForeground(PLACEHOLDER_COLOR);
        passwordField.setText("Veuillez saisir le mot de passe...");
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        passwordField.setBackground(Color.WHITE);

        // Placeholder functionality for password
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Veuillez saisir le mot de passe...")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                    passwordField.setForeground(TEXT_COLOR);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(PLACEHOLDER_COLOR);
                    passwordField.setText("Veuillez saisir le mot de passe...");
                }
            }
        });

        leftPanel.add(passwordField);
        leftPanel.add(Box.createVerticalStrut(20));

        // Lien "mot de passe oublié"
        JLabel forgotPasswordLabel = new JLabel("<html><u>nom d'utilisateur ou mot de passe oublié ?</u></html>");
        forgotPasswordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        forgotPasswordLabel.setForeground(LINK_COLOR);
        forgotPasswordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftPanel.add(forgotPasswordLabel);
        leftPanel.add(Box.createVerticalStrut(25));

        // Messages d'erreur et info
        errorLabel = new JLabel();
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setForeground(new Color(220, 20, 60));
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(errorLabel);

        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(34, 139, 34));
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(infoLabel);
        leftPanel.add(Box.createVerticalStrut(15));

        // Bouton de connexion
        loginButton = new JButton("Se connecter");
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(PRIMARY_COLOR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(this::handleLogin);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(loginButton);

        // Enter key listener
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (passwordField.hasFocus() || emailField.hasFocus()) {
                    handleLogin(null);
                    return true;
                }
            }
            return false;
        });

        leftPanel.add(Box.createVerticalGlue());

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fond dégradé
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(189, 215, 227),
                        getWidth(), getHeight(), new Color(169, 200, 215)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(RIGHT_PANEL_COLOR);

        // Panneau central pour le logo avec padding
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        try {
            ImageIcon logoIcon = new ImageIcon(LoginFrame.class.getResource("/static/images/icones/logo.png"));
            if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                // Calculer la taille pour que le logo prenne quasiment tout l'espace
                int maxWidth = 380;
                int maxHeight = 350;

                Image originalImage = logoIcon.getImage();
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);

                // Calculer le ratio pour maintenir les proportions
                double widthRatio = (double) maxWidth / originalWidth;
                double heightRatio = (double) maxHeight / originalHeight;
                double ratio = Math.min(widthRatio, heightRatio);

                int newWidth = (int) (originalWidth * ratio);
                int newHeight = (int) (originalHeight * ratio);

                Image scaledLogo = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
                centerPanel.add(logoLabel);
            } else {
                // Texte alternatif si le logo ne charge pas
                JLabel titleLabel = new JLabel("Prodenta");
                titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
                titleLabel.setForeground(PRIMARY_COLOR);
                centerPanel.add(titleLabel);
            }
        } catch (Exception e) {
            // Texte alternatif en cas d'erreur
            JLabel titleLabel = new JLabel("Prodenta");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
            titleLabel.setForeground(PRIMARY_COLOR);
            centerPanel.add(titleLabel);
        }

        rightPanel.add(centerPanel, BorderLayout.CENTER);

        return rightPanel;
    }

    private void handleLogin(ActionEvent e) {
        clearMessages();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Ignorer les placeholders
        if (email.equals("Veuillez saisir le nom d'utilisateur...")) {
            email = "";
        }
        if (password.equals("Veuillez saisir le mot de passe...")) {
            password = "";
        }

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
                        UserSession.getInstance().setCurrentUser(user);
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
                passwordField.setEchoChar((char) 0);
                passwordField.setForeground(PLACEHOLDER_COLOR);
                passwordField.setText("Veuillez saisir le mot de passe...");
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

    private Utilisateur authenticateUser(String emailOrLogin, String password) throws Exception {
        try {
            if (validateur_email.is_valid(emailOrLogin)) {
                return authController.authenticate(emailOrLogin, password);
            }
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