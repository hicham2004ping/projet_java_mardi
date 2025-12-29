package ma.prodenta.mvc.ui.patient;
import ma.prodenta.common.exceptions.*;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.mvc.controllers.modules.patient.impl.Patient_Controlleur;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;
import ma.prodenta.service.modules.antecedent.impl.Antecedent_Service_ServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ajouter_patient extends JPanel {

    private Dashboard_view view;
    private Antecedent_Service_ServiceImpl antecedentService;

    public Ajouter_patient(Dashboard_view view) throws Exception {

        this.view = view;
        this.antecedentService = Application_contexte.getAntecedent_Service();
        Patient_Controlleur controller = Application_contexte.getPatientControlleur();

        List<Antecedent> list = new ArrayList<>();


        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.BOTH;

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations du patient"));

        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(5, 5, 5, 5);
        fgbc.fill = GridBagConstraints.HORIZONTAL;
        fgbc.weightx = 1;

        JTextField nom = new JTextField(18);
        JTextField prenom = new JTextField(18);
        JTextField email = new JTextField(18);
        JTextField telephone = new JTextField(18);
        JTextField adresse = new JTextField(18);
        JDateChooser dateChooser = new JDateChooser();

        int row = 0;

        addField(formPanel, fgbc, row++, "Nom :", nom);
        addField(formPanel, fgbc, row++, "Prénom :", prenom);
        addField(formPanel, fgbc, row++, "Date naissance :", dateChooser);
        addField(formPanel, fgbc, row++, "Email :", email);
        addField(formPanel, fgbc, row++, "Téléphone :", telephone);
        addField(formPanel, fgbc, row++, "Adresse :", adresse);


        DefaultTableModel sexeModel = new DefaultTableModel(new String[]{"Id", "Sexe"}, 0);
        for (Sexe s : Sexe.values()) {
            sexeModel.addRow(new Object[]{s.getId(), s.name()});
        }

        JTable sexeTable = new JTable(sexeModel);
        sexeTable.setRowHeight(22);
        sexeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableColumn sexeIdCol = sexeTable.getColumnModel().getColumn(0);
        sexeIdCol.setMinWidth(0);
        sexeIdCol.setMaxWidth(0);

        JScrollPane sexeScroll = new JScrollPane(sexeTable);
        sexeScroll.setPreferredSize(new Dimension(160, 90));

        fgbc.gridx = 1;
        fgbc.gridy = row++;
        fgbc.fill = GridBagConstraints.NONE;
        formPanel.add(sexeScroll, fgbc);


        DefaultTableModel assuranceModel = new DefaultTableModel(new String[]{"Id", "Assurance"}, 0);
        for (Assurance a : Assurance.values()) {
            assuranceModel.addRow(new Object[]{a.getId(), a.name()});
        }

        JTable assuranceTable = new JTable(assuranceModel);
        assuranceTable.setRowHeight(22);
        assuranceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableColumn assIdCol = assuranceTable.getColumnModel().getColumn(0);
        assIdCol.setMinWidth(0);
        assIdCol.setMaxWidth(0);

        JScrollPane assuranceScroll = new JScrollPane(assuranceTable);
        assuranceScroll.setPreferredSize(new Dimension(160, 90));

        fgbc.gridy = row++;
        formPanel.add(assuranceScroll, fgbc);

        fgbc.fill = GridBagConstraints.HORIZONTAL;


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JButton enregistrer = new JButton("Enregistrer");
        JButton clear = new JButton("Clear");

        buttonPanel.add(enregistrer);
        buttonPanel.add(clear);

        fgbc.gridx = 0;
        fgbc.gridy = row;
        fgbc.gridwidth = 2;
        formPanel.add(buttonPanel, fgbc);


        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Id", "Nom", "Catégorie", "Niveau Risque"}, 0);

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        for (Antecedent a : antecedentService.findAll()) {
            model.addRow(new Object[]{
                    a.getIdAntecedent(),
                    a.getNom(),
                    a.getCategorie(),
                    a.getNiveauRisque().name()
            });
        }

        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Antécédents"));
        tableScroll.setPreferredSize(new Dimension(450, 350));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        add(formPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.55;
        add(tableScroll, gbc);


        enregistrer.addActionListener(e -> {
            Sexe sexe1 = null;
            Assurance assurance1 = null;
            list.clear();
            for (int rowIndex : table.getSelectedRows()) {
                int id = Integer.parseInt(table.getValueAt(rowIndex, 0).toString());
                try {
                    list.add(antecedentService.findById(id));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors du chargement des antécédents", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
            int table_Sexe_ligne=sexeTable.getSelectedRow();
            if(table_Sexe_ligne!=-1){
                int id=Integer.parseInt(sexeTable.getValueAt(table_Sexe_ligne,0).toString());
                sexe1=Sexe.get_sexeby_id(id);
            }
            int table_assurance_ligne=assuranceTable.getSelectedRow();
            if(table_assurance_ligne!=-1){
                int id=Integer.parseInt(assuranceTable.getValueAt(table_assurance_ligne,0).toString());
                assurance1=Assurance.getBy_id(id);
            }
            try {
                Date date = dateChooser.getDate();LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                controller.creation_patient(
                        1,
                        nom.getText().trim(),
                        prenom.getText().trim(),
                        localDate,
                        adresse.getText().trim(),
                        email.getText().trim(),
                        telephone.getText().trim(),
                        sexe1.name(),
                        assurance1.name(),
                        list
                );

                JOptionPane.showMessageDialog(this,
                        "Le patient a été ajouté avec succès",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);

            } catch (ArgumentException | EmailInvalideException | EmailExisteException |
                     Date_Naissance_Exception | ErreurLectureException | SQLException ex) {

                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Erreur lors de la création",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        clear.addActionListener(e -> {
            nom.setText("");
            prenom.setText("");
            email.setText("");
            telephone.setText("");
            adresse.setText("");
            dateChooser.setDate(null);
            table.clearSelection();
            sexeTable.clearSelection();
            assuranceTable.clearSelection();
            list.clear();
        });
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row,
                          String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }
}
