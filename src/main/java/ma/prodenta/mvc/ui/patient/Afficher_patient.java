package ma.prodenta.mvc.ui.patient;

import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.dto.patient.PatientDTO;
import ma.prodenta.mvc.controllers.modules.patient.impl.Patient_Controlleur;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class Afficher_patient extends JPanel {

    Patient_Controlleur controlleur;
    JButton ajouter_Patient;
    JButton supprimer_Patient;
    JButton modifer_Patient;
    JButton afficher_Detailles;
    Dashboard_view dashboard;
    JTable table;
    DefaultTableModel model;

    public Afficher_patient(Dashboard_view view) {
        this.dashboard = view;
        this.controlleur = Application_contexte.getPatientControlleur();

        // Style homogène avec le dashboard
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre de la page
        JPanel titlePanel = createTitlePanel();

        // Boutons d'action
        JPanel buttonPanel = createButtonPanel();

        // Tableau des patients
        JPanel tablePanel = createTablePanel();

        add(titlePanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setupEventListeners();
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Gestion des Patients");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));

        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        return titlePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Création des boutons avec style homogène
        ajouter_Patient = createStyledButton("Ajouter Patient", new Color(0, 102, 204));
        modifer_Patient = createStyledButton("Modifier Patient", new Color(255, 153, 51));
        afficher_Detailles = createStyledButton("Afficher Détails", new Color(102, 153, 0));
        supprimer_Patient = createStyledButton("Supprimer Patient", new Color(204, 0, 0));

        // Icônes pour les boutons (optionnel)
        ajouter_Patient.setIcon(new ImageIcon("icons/add.png")); // Ajouter une icône si disponible
        modifer_Patient.setIcon(new ImageIcon("icons/edit.png"));
        afficher_Detailles.setIcon(new ImageIcon("icons/details.png"));
        supprimer_Patient.setIcon(new ImageIcon("icons/delete.png"));

        buttonPanel.add(ajouter_Patient);
        buttonPanel.add(modifer_Patient);
        buttonPanel.add(afficher_Detailles);
        buttonPanel.add(supprimer_Patient);

        return buttonPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // En-tête de la table
        JPanel tableHeader = new JPanel(new BorderLayout());
        tableHeader.setBackground(new Color(240, 240, 240));
        tableHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel tableTitle = new JLabel("Liste des Patients");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableTitle.setForeground(new Color(60, 60, 60));
        tableHeader.add(tableTitle, BorderLayout.WEST);

        tablePanel.add(tableHeader, BorderLayout.NORTH);

        // Modèle de table
        String[] noms_colonnes = {"Id", "Nom", "Prénom", "Date de naissance"};
        this.model = new DefaultTableModel(noms_colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Integer.class;
                return String.class;
            }
        };

        // Configuration de la table
        this.table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                // Alternance des couleurs des lignes
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                } else {
                    c.setBackground(new Color(220, 240, 255)); // Couleur de sélection
                    c.setForeground(new Color(0, 102, 204));
                }

                // Style pour l'en-tête
                if (c instanceof JComponent) {
                    ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                }

                return c;
            }
        };

        // Style de la table
        table.setRowHeight(40);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(220, 240, 255));
        table.setSelectionForeground(new Color(0, 102, 204));

        // Style de l'en-tête de la table
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(
                table.getTableHeader().getPreferredSize().width, 45
        ));

        // Centrage du contenu
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Chargement des données
        List<PatientDTO> list_patients = controlleur.afficher_tous();
        for (PatientDTO patient : list_patients) {
            model.addRow(new Object[]{
                    patient.getId(),
                    patient.getNom(),
                    patient.getPrenom(),
                    patient.getDate_naissance()
            });
        }

        // Masquer la colonne ID
        TableColumn column = table.getColumnModel().getColumn(0);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setResizable(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void setupEventListeners() {
        // Écouteur pour le bouton Supprimer Patient
        supprimer_Patient.addActionListener(e -> {
            int ligne_selectionner = table.getSelectedRow();
            if (ligne_selectionner != -1) {
                int id = Integer.parseInt(table.getValueAt(ligne_selectionner, 0).toString());
                int valeur = JOptionPane.showConfirmDialog(
                        this,
                        "Êtes-vous sûr de vouloir supprimer ce patient ?",
                        "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                try {
                    if (valeur == JOptionPane.YES_OPTION) {
                        controlleur.supprimer_patient(id);
                        model.removeRow(ligne_selectionner);

                        // Message de confirmation
                        JOptionPane.showMessageDialog(
                                this,
                                "Patient supprimé avec succès",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Erreur lors de la suppression: " + e1.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez sélectionner un patient avant de supprimer",
                        "Avertissement",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        // Écouteur pour le bouton Ajouter Patient
        ajouter_Patient.addActionListener(e -> {
            dashboard.afficher_Panel("ajouter_patient");
        });

        // Écouteur pour le bouton Modifier Patient
        modifer_Patient.addActionListener(e -> {
            int ligne_selectionner = table.getSelectedRow();
            if (ligne_selectionner != -1) {
                int id = Integer.parseInt(table.getValueAt(ligne_selectionner, 0).toString());
                try {
                    Patient patient2 = controlleur.find_by_id(id);
                    dashboard.ModifierPatient(patient2);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Erreur: " + e1.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez sélectionner un patient avant de modifier",
                        "Avertissement",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        // Écouteur pour le bouton Afficher Détails
        afficher_Detailles.addActionListener(e -> {
            int ligne_selectionner = table.getSelectedRow();
            if (ligne_selectionner != -1) {
                int id = Integer.parseInt(table.getValueAt(ligne_selectionner, 0).toString());
                try {
                    dashboard.afficherDetailsDossier(id);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Erreur: " + e1.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez sélectionner un patient",
                        "Avertissement",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });
    }

    public void ajouter_patient_ligne(PatientDTO patient) {
        model.addRow(new Object[]{
                patient.getId(),
                patient.getNom(),
                patient.getPrenom(),
                patient.getDate_naissance()
        });

        // Scroll jusqu'à la nouvelle ligne
        table.scrollRectToVisible(table.getCellRect(model.getRowCount()-1, 0, true));
    }
}