package ma.prodenta.mvc.ui.auth;
import ma.prodenta.entities.En.Utilisateur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.repository.modules.user.implementation.UserImpl;
public class LoginView extends JFrame {

    public LoginView() {
        setTitle("ProDenta - Connexion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // === Panel principal ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(250, 248, 245));

        // === Partie gauche (formulaire) ===
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(250, 248, 245));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titleLabel = new JLabel("Bienvenue utilisateur");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userLabel = new JLabel("Nom utilisateur :");
        JTextField userField = new JTextField("Veuillez saisir le nom d’utilisateur...");
        userField.setForeground(Color.GRAY);
        userField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (userField.getText().equals("Veuillez saisir le nom d’utilisateur...")) {
                    userField.setText("");
                    userField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (userField.getText().isEmpty()) {
                    userField.setForeground(Color.GRAY);
                    userField.setText("Veuillez saisir le nom d’utilisateur...");
                }
            }
        });

        JLabel passLabel = new JLabel("Mot de passe :");
        JPasswordField passField = new JPasswordField("Veuillez saisir le mot de passe...");
        passField.setEchoChar((char) 0);
        passField.setForeground(Color.GRAY);
        passField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                String pwd = new String(passField.getMotDePasse());
                if (pwd.equals("Veuillez saisir le mot de passe...")) {
                    passField.setText("");
                    passField.setEchoChar('•');
                    passField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                String pwd = new String(passField.getMotDePasse());
                if (pwd.isEmpty()) {
                    passField.setEchoChar((char) 0);
                    passField.setForeground(Color.GRAY);
                    passField.setText("Veuillez saisir le mot de passe...");
                }
            }
        });

        JLabel forgotLabel = new JLabel("<html><u>nom d’utilisateur ou mot de passe oublié ?</u></html>");
        forgotLabel.setForeground(new Color(76, 0, 153));
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Fonctionnalité à implémenter.");
            }
        });

        JButton loginButton = new JButton("Se connecter");
        loginButton.setBackground(new Color(0, 102, 102));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.addActionListener(e->{
            String motdepasse = passField.getText();
            String login=userField.getText();
            System.out.println(motdepasse);
            UserImpl user=new UserImpl();
            dispose();
            new Dashboard_view();
            try{
                Utilisateur u=user.getUser(login,motdepasse);
                if (u!=null){
                    System.out.println("connexion reussie");
                    dispose();
                    new Dashboard_view();
                }
                else{
                    System.out.println("connexion non reussie");
                }
            }
            catch(SQLException e1){
                System.out.println(e1.getMessage());
            }
        });

        // Espacement
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(userLabel);
        leftPanel.add(userField);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(passLabel);
        leftPanel.add(passField);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(forgotLabel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(loginButton);

        // === Partie droite (image principale) ===
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("path_to_main_image.png").getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        rightPanel.setPreferredSize(new Dimension(350, 400));
        rightPanel.setBackground(new Color(245, 245, 245));

        // === Logo en bas à droite ===
        JLabel logoLabel = new JLabel(new ImageIcon("path_to_logo.png"));
        logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        mainPanel.add(logoLabel, BorderLayout.SOUTH);

        // === Ajout au panel principal ===
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}


