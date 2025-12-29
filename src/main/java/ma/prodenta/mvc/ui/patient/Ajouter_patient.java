package ma.prodenta.mvc.ui.patient;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.mvc.controllers.modules.patient.impl.Patient_Controlleur;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;
import ma.prodenta.service.modules.antecedent.impl.Antecedent_Service_ServiceImpl;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Ajouter_patient extends JPanel {

    private Dashboard_view view;
    private Antecedent_Service_ServiceImpl antecedentService;

    public Ajouter_patient(Dashboard_view view) throws Exception {
        java.util.List<Antecedent>list=new ArrayList<>();
        this.antecedentService = Application_contexte.getAntecedent_Service();
        Patient_Controlleur controller = Application_contexte.getPatientControlleur();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(5, 5, 5, 5);
        fgbc.anchor = GridBagConstraints.WEST;
        fgbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nom = new JTextField(15);
        JTextField prenom = new JTextField(15);
        JTextField email = new JTextField(15);
        JTextField telephone = new JTextField(15);
        JTextField adresse = new JTextField(15);
        JTextField sexe = new JTextField(15);
        JTextField assurance = new JTextField(15);
        JDateChooser dateChooser = new JDateChooser();

        int row = 0;

        addField(formPanel, fgbc, row++, "Nom :", nom);
        addField(formPanel, fgbc, row++, "Prénom :", prenom);
        addField(formPanel, fgbc, row++, "Date naissance :", dateChooser);
        addField(formPanel, fgbc, row++, "Email :", email);
        addField(formPanel, fgbc, row++, "Téléphone :", telephone);
        addField(formPanel, fgbc, row++, "Adresse :", adresse);
        addField(formPanel, fgbc, row++, "Sexe :", sexe);
        addField(formPanel, fgbc, row++, "Assurance :", assurance);

        JButton enregistrer = new JButton("Enregistrer");
        fgbc.gridx = 0;
        fgbc.gridy = row;
        fgbc.gridwidth = 2;
        fgbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(enregistrer, fgbc);


        String[] colonnes = {"Id", "Nom", "Catégorie", "Niveau Risque"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);

        for (Antecedent a : antecedentService.findAll()) {
            model.addRow(new Object[]{
                    a.getIdAntecedent(),
                    a.getNom(),
                    a.getCategorie(),
                    a.getNiveauRisque().name()
            });
        }
        TableColumn colonne=table.getColumnModel().getColumn(0);
        colonne.setMinWidth(0);
        colonne.setMaxWidth(0);
        colonne.setResizable(false);
        colonne.setPreferredWidth(0);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(400, 300));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        add(formPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableScroll, gbc);


        enregistrer.addActionListener(e -> {
            int [] lignes_selectionner=table.getSelectedRows();
            for (int ligne : lignes_selectionner) {
                int id=Integer.parseInt(table.getValueAt(ligne, 0).toString());
                try {
                    list.add(antecedentService.findById(id));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "erreur lors de l'ajout des antecedents", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }

            try {
                Date date = dateChooser.getDate();
                if (date == null) {
                    throw new Exception("Date obligatoire");
                }

                LocalDate localDate = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                controller.creation_patient(
                        1,
                        nom.getText().trim(),
                        prenom.getText().trim(),
                        localDate,
                        adresse.getText().trim(),
                        email.getText().trim(),
                        telephone.getText().trim(),
                        sexe.getText().trim(),
                        assurance.getText().trim(),
                        list
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Le patient a été ajouté avec succès",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }


    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }
}
