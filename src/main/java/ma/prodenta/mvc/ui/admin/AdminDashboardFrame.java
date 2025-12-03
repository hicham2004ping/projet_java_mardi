package ma.prodenta.mvc.ui.admin;

import ma.prodenta.mvc.dto.admin.AdminViewDto;
import ma.prodenta.mvc.ui.dossiermedical.DossierMedicalFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
                                dashboard
 */
public class AdminDashboardFrame extends JFrame {

    private final AdminViewDto admin;

    public AdminDashboardFrame(AdminViewDto admin) {
        this.admin = admin;
        initComponents();
    }

    private void initComponents() {
        setTitle("Dashboard Admin - Cabinet Dentaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        //menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menuDossiers = new JMenu("Dossiers médicaux");
        JMenuItem itemGestionDossiers = new JMenuItem("Gérer les dossiers");
        itemGestionDossiers.addActionListener(this::onGestionDossiersClicked);
        menuDossiers.add(itemGestionDossiers);

        menuBar.add(menuDossiers);

        setJMenuBar(menuBar);

        //lwst
        JLabel welcomeLabel = new JLabel("Bienvenue, " + admin.getFullName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.CENTER);
    }

    private void onGestionDossiersClicked(ActionEvent e) {
        //fenetre de gestion des dossiers médicaux
        SwingUtilities.invokeLater(() -> {
            DossierMedicalFrame frame = new DossierMedicalFrame(this);
            frame.setVisible(true);
        });
    }
}
