package ma.prodenta.mvc.ui.patient;

import ma.prodenta.common.exceptions.*;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Antecedent;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.entities.Enum.Assurance;
import ma.prodenta.entities.Enum.Sexe;
import ma.prodenta.mvc.controllers.modules.patient.impl.Patient_Controlleur;
import ma.prodenta.mvc.dto.patient.PatientDTO;
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

public class ModifierPatient extends JPanel {

    private Dashboard_view view;
    private Antecedent_Service_ServiceImpl antecedentService;
    private Patient patient1;

    public ModifierPatient(Patient patient1) throws Exception {

        this.patient1 = patient1;
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

        nom.setText(patient1.getNom());
        prenom.setText(patient1.getPrenom());
        email.setText(patient1.getEmail());
        telephone.setText(patient1.getTelephone());
        adresse.setText(patient1.getAdresse());

        LocalDate ld = patient1.getDateNaissance();
        Date date1 = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
        dateChooser.setDate(date1);

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


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JButton enregistrer = new JButton("Enregistrer");
        JButton clear = new JButton("Clear");

        buttonPanel.add(enregistrer);
        buttonPanel.add(clear);

        fgbc.gridx = 0;
        fgbc.gridy = row;
        fgbc.gridwidth = 2;
        fgbc.fill = GridBagConstraints.HORIZONTAL;
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

        JLabel antecedentLabel = new JLabel("Choisir les antécédents du patient");
        antecedentLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        antecedentLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(450, 330));

        JPanel antecedentPanel = new JPanel(new BorderLayout());
        antecedentPanel.setBorder(BorderFactory.createTitledBorder("Antécédents"));
        antecedentPanel.add(antecedentLabel, BorderLayout.NORTH);
        antecedentPanel.add(tableScroll, BorderLayout.CENTER);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        add(formPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.55;
        add(antecedentPanel, gbc);
        enregistrer.addActionListener(e->{
            try {
                /* ================= TEXTE ================= */
                String nomValue = nom.getText().trim();
                String prenomValue = prenom.getText().trim();
                String emailValue = email.getText().trim();
                String telephoneValue = telephone.getText().trim();
                String adresseValue = adresse.getText().trim();

                /* ================= DATE ================= */
                if (dateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(this, "Veuillez choisir une date de naissance");
                    return;
                }

                LocalDate dateNaissance = dateChooser.getDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                /* ================= SEXE ================= */
                if (sexeTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(this, "Veuillez choisir le sexe");
                    return;
                }

                int sexeId = Integer.parseInt(
                        sexeTable.getValueAt(sexeTable.getSelectedRow(), 0).toString()
                );
                Sexe sexeValue = Sexe.get_sexeby_id(sexeId);

                /* ================= ASSURANCE ================= */
                if (assuranceTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(this, "Veuillez choisir l'assurance");
                    return;
                }

                int assuranceId = Integer.parseInt(
                        assuranceTable.getValueAt(assuranceTable.getSelectedRow(), 0).toString()
                );
                Assurance assuranceValue = Assurance.getBy_id(assuranceId);

                /* ================= ANTÉCÉDENTS ================= */
                List<Antecedent> antecedents = new ArrayList<>();

                for (int rowIndex : table.getSelectedRows()) {
                    int id = Integer.parseInt(table.getValueAt(rowIndex, 0).toString());
                    antecedents.add(antecedentService.findById(id));
                }

                System.out.println("la liste des antecedents est ");
                System.out.println(antecedents);
                Patient patient3=new Patient();
                patient3.setNom(nomValue);
                patient3.setPrenom(prenomValue);
                patient3.setAdresse(adresseValue);
                patient3.setEmail(emailValue);
                patient3.setTelephone(telephoneValue);
                patient3.setAdresse(adresseValue);
                patient3.setDateNaissance(dateNaissance);
                patient3.setSexe(sexeValue);
                patient3.setAntecedents(antecedents);
                patient3.setAssurance(assuranceValue);
                patient3.setId(patient1.getId());
                controller.update(patient3);

                JOptionPane.showMessageDialog(
                        this,
                        "Patient modifié avec succès",
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
