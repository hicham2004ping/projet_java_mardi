package ma.prodenta.mvc.ui.admin;
import javax.swing.*;
import java.awt.*;
import ma.prodenta.mvc.dto.UserDTO;
public class AdminDashboardFrame extends JFrame {
    private final UserDTO admin;
    public AdminDashboardFrame(UserDTO admin) {
        this.admin = admin;
        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }
    private void initUI() {
        JPanel main = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("Bienvenue, " + admin.getUsername());
        main.add(welcome, BorderLayout.NORTH);
// Placeholder: boutons de gestion
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton manageUsers = new JButton("Gérer utilisateurs");
        JButton manageRecords = new JButton("Dossier médicaux");
        center.add(manageUsers); center.add(manageRecords);
        main.add(center, BorderLayout.CENTER);
        add(main);

    }
}
