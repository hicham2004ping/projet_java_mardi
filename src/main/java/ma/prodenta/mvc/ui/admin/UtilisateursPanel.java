package ma.prodenta.mvc.ui.admin;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Utilisateur;
import ma.prodenta.mvc.controllers.modules.auth.impl.AuthControlleur_Impl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UtilisateursPanel extends JPanel {
    private JTable utilisateurTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private AuthControlleur_Impl authController;

    public UtilisateursPanel() {
        try {
            authController = Application_contexte.getAuthControlleur();
        } catch (Exception e) {
            System.out.println("Erreur initialisation: " + e.getMessage());
        }

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        loadUsers();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Gestion des Utilisateurs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(titleLabel);
        return topPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new TitledBorder("Liste des Utilisateurs"));
        tablePanel.setBackground(Color.WHITE);

        String[] columns = {"ID", "Nom", "Prénom", "Email", "Login", "Rôle", "CIN", "Téléphone"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        utilisateurTable = new JTable(tableModel);
        utilisateurTable.setRowHeight(25);
        utilisateurTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        utilisateurTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(utilisateurTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        addButton = new JButton("Ajouter");
        editButton = new JButton("Modifier");
        deleteButton = new JButton("Supprimer");
        refreshButton = new JButton("Rafraîchir");

        addButton.addActionListener(e -> handleAddUser());
        editButton.addActionListener(e -> handleEditUser());
        deleteButton.addActionListener(e -> handleDeleteUser());
        refreshButton.addActionListener(e -> loadUsers());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        return buttonPanel;
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        try {
            List<Utilisateur> users = authController.getAllUsers();
            for (Utilisateur user : users) {
                String role = getRoleName(user.getIdRole());
                tableModel.addRow(new Object[]{
                        user.getIdUser(),
                        user.getNom() != null ? user.getNom() : "",
                        user.getEmail() != null ? user.getEmail() : "",
                        user.getLogin(),
                        role,
                        user.getCin() != null ? user.getCin() : "",
                        user.getTel() != null ? user.getTel() : ""
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement: " + e.getMessage(), 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getRoleName(Integer roleId) {
        if (roleId == null) return "Inconnu";
        switch (roleId) {
            case 1: return "Médecin";
            case 2: return "Secrétaire";
            case 3: return "Administrateur";
            default: return "Inconnu";
        }
    }

    private void handleAddUser() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Ajouter Utilisateur", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Médecin", "Secrétaire", "Administrateur"});
        JTextField cinField = new JTextField();
        JTextField telField = new JTextField();

        addFormField(panel, "Nom:", nomField);
        addFormField(panel, "Prénom:", prenomField);
        addFormField(panel, "Email:", emailField);
        addFormField(panel, "Login:", loginField);
        addFormField(panel, "Mot de passe:", passwordField);
        addFormField(panel, "Rôle:", roleCombo);
        addFormField(panel, "CIN:", cinField);
        addFormField(panel, "Téléphone:", telField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            try {
                Utilisateur user = new Utilisateur();
                user.setNom(nomField.getText());
                user.setEmail(emailField.getText());
                user.setLogin(loginField.getText());
                user.setMotdepasse(new String(passwordField.getPassword()));
                user.setIdRole(roleCombo.getSelectedIndex() + 1);
                user.setCin(cinField.getText());
                user.setTel(telField.getText());

                authController.register(user);
                JOptionPane.showMessageDialog(dialog, "Utilisateur ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadUsers();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(buttonPanel);

        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }

    private void handleEditUser() {
        int selectedRow = utilisateurTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un utilisateur", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int userId = (Integer) tableModel.getValueAt(selectedRow, 0);

        try {
            List<Utilisateur> users = authController.getAllUsers();
            Utilisateur user = users.stream().filter(u -> u.getIdUser() == userId).findFirst().orElse(null);

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Utilisateur non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Modifier Utilisateur", true);
            dialog.setSize(400, 450);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JTextField nomField = new JTextField(user.getNom());
            JTextField emailField = new JTextField(user.getEmail());
            JTextField loginField = new JTextField(user.getLogin());
            JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Médecin", "Secrétaire", "Administrateur"});
            roleCombo.setSelectedIndex(user.getIdRole() - 1);
            JTextField cinField = new JTextField(user.getCin());
            JTextField telField = new JTextField(user.getTel());

            addFormField(panel, "Nom:", nomField);
            addFormField(panel, "Email:", emailField);
            addFormField(panel, "Login:", loginField);
            addFormField(panel, "Rôle:", roleCombo);
            addFormField(panel, "CIN:", cinField);
            addFormField(panel, "Téléphone:", telField);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton saveButton = new JButton("Enregistrer");
            JButton cancelButton = new JButton("Annuler");

            saveButton.addActionListener(e -> {
                try {
                    user.setNom(nomField.getText());
                    user.setEmail(emailField.getText());
                    user.setIdRole(roleCombo.getSelectedIndex() + 1);
                    user.setCin(cinField.getText());
                    user.setTel(telField.getText());

                    authController.updateUser(user);
                    JOptionPane.showMessageDialog(dialog, "Utilisateur modifié avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadUsers();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

            cancelButton.addActionListener(e -> dialog.dispose());

            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            panel.add(Box.createVerticalStrut(20));
            panel.add(buttonPanel);

            dialog.add(new JScrollPane(panel));
            dialog.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteUser() {
        int selectedRow = utilisateurTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un utilisateur", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int userId = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Êtes-vous sûr de vouloir supprimer cet utilisateur?", 
                "Confirmation", 
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                authController.deleteUser(userId);
                loadUsers();
                JOptionPane.showMessageDialog(this, "Utilisateur supprimé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addFormField(JPanel panel, String label, JComponent field) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BorderLayout(5, 5));
        fieldPanel.setOpaque(false);
        fieldPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(120, 30));
        fieldPanel.add(lbl, BorderLayout.WEST);
        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(fieldPanel);
        panel.add(Box.createVerticalStrut(5));
    }
}
