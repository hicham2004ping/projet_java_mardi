//package ma.prodenta.mvc.ui.agenda;
//
//import com.toedter.calendar.JDateChooser;
//import ma.prodenta.mvc.controllers.modules.agenda.AgendaController;
//import ma.prodenta.mvc.dto.agenda.AgendaDTO;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.Date;
//
//public class AgendaForm extends JDialog {
//
//    private AgendaController controller;
//    private AgendaFrame parentFrame;
//    private AgendaDTO agendaToEdit;
//
//    private JDateChooser dateDebutChooser;
//    private JDateChooser dateFinChooser;
//    private JTextField txtIdMedecin;
//    private JTextField txtIdPatient;
//    private JTextArea txtNote;
//    private JComboBox<String> cmbStatut;
//    private JButton btnSave;
//    private JButton btnCancel;
//
//    public AgendaForm(AgendaFrame parent, AgendaDTO agenda) {
//        super((Frame) SwingUtilities.getWindowAncestor(parent), "Formulaire Agenda", true);
//        this.parentFrame = parent;
//        this.agendaToEdit = agenda;
//        this.controller = new AgendaController();
//
//        initializeUI();
//        if (agenda != null) {
//            fillForm();
//        }
//    }
//
//    private void initializeUI() {
//        setSize(400, 450);
//        setLocationRelativeTo(parentFrame);
//        setLayout(new BorderLayout());
//
//        JPanel formPanel = new JPanel(new GridBagLayout());
//        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Date Début
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        formPanel.add(new JLabel("Date Début:"), gbc);
//        dateDebutChooser = new JDateChooser();
//        dateDebutChooser.setDate(new Date());
//        gbc.gridx = 1;
//        formPanel.add(dateDebutChooser, gbc);
//
//        // Date Fin
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        formPanel.add(new JLabel("Date Fin:"), gbc);
//        dateFinChooser = new JDateChooser();
//        dateFinChooser.setDate(new Date());
//        gbc.gridx = 1;
//        formPanel.add(dateFinChooser, gbc);
//
//        // Médecin
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        formPanel.add(new JLabel("ID Médecin:"), gbc);
//        txtIdMedecin = new JTextField(10);
//        gbc.gridx = 1;
//        formPanel.add(txtIdMedecin, gbc);
//
//        // Patient
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        formPanel.add(new JLabel("ID Patient:"), gbc);
//        txtIdPatient = new JTextField(10);
//        gbc.gridx = 1;
//        formPanel.add(txtIdPatient, gbc);
//
//        // Statut
//        gbc.gridx = 0;
//        gbc.gridy = 4;
//        formPanel.add(new JLabel("Statut:"), gbc);
//        String[] statuts = { "Planifié", "En cours", "Terminé", "Annulé" };
//        cmbStatut = new JComboBox<>(statuts);
//        gbc.gridx = 1;
//        formPanel.add(cmbStatut, gbc);
//
//        // Note
//        gbc.gridx = 0;
//        gbc.gridy = 5;
//        gbc.anchor = GridBagConstraints.NORTHWEST;
//        formPanel.add(new JLabel("Note:"), gbc);
//        txtNote = new JTextArea(5, 20);
//        JScrollPane scrollNote = new JScrollPane(txtNote);
//        gbc.gridx = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
//        formPanel.add(scrollNote, gbc);
//
//        // Buttons
//        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        btnSave = new JButton("Enregistrer");
//        btnCancel = new JButton("Annuler");
//
//        btnPanel.add(btnSave);
//        btnPanel.add(btnCancel);
//
//        add(formPanel, BorderLayout.CENTER);
//        add(btnPanel, BorderLayout.SOUTH);
//
//        // Actions
//        btnSave.addActionListener(e -> save());
//        btnCancel.addActionListener(e -> dispose());
//    }
//
//    private void fillForm() {
//        if (agendaToEdit.getDateDebut() != null)
//            dateDebutChooser.setDate(agendaToEdit.getDateDebut());
//        if (agendaToEdit.getDateFin() != null)
//            dateFinChooser.setDate(agendaToEdit.getDateFin());
//        if (agendaToEdit.getIdMedecin() != null)
//            txtIdMedecin.setText(String.valueOf(agendaToEdit.getIdMedecin()));
//        if (agendaToEdit.getIdPatient() != null)
//            txtIdPatient.setText(String.valueOf(agendaToEdit.getIdPatient()));
//        cmbStatut.setSelectedItem(agendaToEdit.getStatut());
//        txtNote.setText(agendaToEdit.getNote());
//    }
//
//    private void save() {
//        try {
//            AgendaDTO dto = agendaToEdit != null ? agendaToEdit : new AgendaDTO();
//
//            dto.setDateDebut(dateDebutChooser.getDate());
//            dto.setDateFin(dateFinChooser.getDate());
//
//            String medId = txtIdMedecin.getText().trim();
//            if (!medId.isEmpty())
//                dto.setIdMedecin(Integer.parseInt(medId));
//
//            String patId = txtIdPatient.getText().trim();
//            if (!patId.isEmpty())
//                dto.setIdPatient(Integer.parseInt(patId));
//
//            dto.setStatut((String) cmbStatut.getSelectedItem());
//            dto.setNote(txtNote.getText());
//
//            if (agendaToEdit == null) {
//                controller.ajouterAgenda(dto);
//            } else {
//                controller.modifierAgenda(dto);
//            }
//
//            parentFrame.refresh();
//            dispose();
//
//        } catch (NumberFormatException nfe) {
//            JOptionPane.showMessageDialog(this, "ID Médecin/Patient doit être un nombre.", "Erreur",
//                    JOptionPane.ERROR_MESSAGE);
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement: " + ex.getMessage(), "Erreur",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}
