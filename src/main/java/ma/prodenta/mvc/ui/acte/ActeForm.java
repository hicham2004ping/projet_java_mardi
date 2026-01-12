package ma.prodenta.mvc.ui.acte;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Acte;
import ma.prodenta.service.modules.actes.api.Acte_Service_api;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActeForm extends JDialog {

    private JTextField libelleField;
    private JTextField categorieField;
    private JTextField prixField;
    private JButton btnEnregistrer;
    private JButton btnAnnuler;

    private Acte acteEdite;
    private ActePanel parentPanel;
    private Acte_Service_api acteService;

    public ActeForm(Acte acte, ActePanel parent) {
        this.acteEdite = acte;
        this.parentPanel = parent;
        this.acteService = Application_contexte.getacteService();

        setTitle(acte == null ? "Ajouter un Acte" : "Modifier un Acte");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setModal(true);
        setLayout(new BorderLayout());

        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        if (acte != null) {
            remplirChamps();
        }
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("Libellé:"));
        libelleField = new JTextField();
        panel.add(libelleField);

        panel.add(new JLabel("Catégorie:"));
        categorieField = new JTextField();
        panel.add(categorieField);

        panel.add(new JLabel("Prix de Base:"));
        prixField = new JTextField();
        panel.add(prixField);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);

        btnEnregistrer = new JButton("Enregistrer");
        btnAnnuler = new JButton("Annuler");

        btnEnregistrer.setBackground(new Color(46, 204, 113));
        btnEnregistrer.setForeground(Color.WHITE);

        btnAnnuler.setBackground(new Color(231, 76, 60));
        btnAnnuler.setForeground(Color.WHITE);

        btnEnregistrer.addActionListener(e -> enregistrer());
        btnAnnuler.addActionListener(e -> dispose());

        panel.add(btnEnregistrer);
        panel.add(btnAnnuler);

        return panel;
    }

    private void remplirChamps() {
        libelleField.setText(acteEdite.getLibelle());
        categorieField.setText(acteEdite.getCategorie());
        prixField.setText(String.valueOf(acteEdite.getPrix_de_base()));
    }

    private void enregistrer() {
        String libelle = libelleField.getText();
        String categorie = categorieField.getText();
        String prixStr = prixField.getText();

        if (libelle.isEmpty() || categorie.isEmpty() || prixStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double prix;
        try {
            prix = Double.parseDouble(prixStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le prix doit être un nombre valide.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (acteEdite == null) {
                // Création
                Acte nouvelActe = new Acte();
                nouvelActe.setLibelle(libelle);
                nouvelActe.setCategorie(categorie);
                nouvelActe.setPrix_de_base(prix);
                acteService.ajouterActe(nouvelActe);
            } else {
                // Modification
                acteEdite.setLibelle(libelle);
                acteEdite.setCategorie(categorie);
                acteEdite.setPrix_de_base(prix);
                acteService.modifierActe(acteEdite);
            }

            parentPanel.reloadData();
            dispose();
            JOptionPane.showMessageDialog(parentPanel, "Enregistrement réussi !");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement : " + e.getMessage(), "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
