package ma.prodenta.mvc.ui.consultation;

import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.mvc.ui.fileattente.ConsultationMedecinFrame;
import ma.prodenta.mvc.ui.fileattente.FileAttenteFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Frame de consultation - Redirige vers la file d'attente si disponible
 * Sinon affiche un message d'information
 */
public class ConsultationFrame extends JFrame {

    public ConsultationFrame() {
        this(null);
    }

    public ConsultationFrame(Dashboard_view dashboard) {
        setTitle("Consultation");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (dashboard != null && dashboard.getFileAttenteFrame() != null) {
            // Si on a accès au dashboard avec file d'attente, l'utiliser
            FileAttenteFrame fileAttente = dashboard.getFileAttenteFrame();
            ConsultationMedecinFrame consultationView = new ConsultationMedecinFrame(dashboard, fileAttente);
            add(consultationView, BorderLayout.CENTER);
        } else {
            // Sinon, afficher un message informatif
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

            JLabel title = new JLabel("Interface Consultation", SwingConstants.CENTER);
            title.setFont(new Font("Segoe UI", Font.BOLD, 18));

            JTextArea info = new JTextArea(
                    "Pour utiliser la consultation complète:\n\n" +
                            "1. Accédez au Dashboard principal\n" +
                            "2. Utilisez le menu 'Consultation' dans le Sidebar\n" +
                            "3. La file d'attente s'affichera automatiquement\n\n" +
                            "Le système permet de:\n" +
                            "- Voir les patients en attente\n" +
                            "- Prendre un patient et ouvrir son dossier\n" +
                            "- Créer des consultations, RDV, certificats et ordonnances"
            );
            info.setEditable(false);
            info.setOpaque(false);
            info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            info.setLineWrap(true);
            info.setWrapStyleWord(true);

            panel.add(title, BorderLayout.NORTH);
            panel.add(info, BorderLayout.CENTER);

            add(panel);
        }

        setVisible(true);
    }
}