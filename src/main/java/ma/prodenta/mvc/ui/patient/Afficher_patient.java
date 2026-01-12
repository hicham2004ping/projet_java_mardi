    package ma.prodenta.mvc.ui.patient;

    import ma.prodenta.config.Application_contexte;
    import ma.prodenta.entities.En.Patient;
    import ma.prodenta.mvc.dto.patient.PatientDTO;
    import ma.prodenta.mvc.controllers.modules.patient.impl.Patient_Controlleur;
    import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

    import javax.swing.*;
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

            ajouter_Patient = new JButton("Ajouter Patient");
            supprimer_Patient = new JButton("Supprimer Patient");
            modifer_Patient = new JButton("Modifier Patient");
            afficher_Detailles = new JButton("Afficher_Detailles");

            String[] noms_colonnes = {"Id", "Nom", "Prenom", "Date naissance"};
            this.model = new DefaultTableModel(noms_colonnes, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            this.table = new JTable(model);
            table.setRowHeight(30);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setShowGrid(false);
            table.setIntercellSpacing(new Dimension(0, 0));
            table.setFillsViewportHeight(true);
            table.setPreferredScrollableViewportSize(new Dimension(750, 420));

            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

            List<PatientDTO> list_patients = controlleur.afficher_tous();
            for (PatientDTO patient : list_patients) {
                model.addRow(new Object[]{
                        patient.getId(),
                        patient.getNom(),
                        patient.getPrenom(),
                        patient.getDate_naissance()
                });
            }

            TableColumn column = table.getColumnModel().getColumn(0);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setPreferredWidth(0);
            column.setResizable(false);

            setLayout(new BorderLayout(10, 10));

            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            topPanel.add(ajouter_Patient);
            topPanel.add(modifer_Patient);
            topPanel.add(afficher_Detailles);
            topPanel.add(supprimer_Patient);

            JPanel centerPanel = new JPanel(new GridBagLayout());
            JScrollPane scroll = new JScrollPane(table);
            scroll.setPreferredSize(new Dimension(780, 440));
            scroll.setBorder(BorderFactory.createEmptyBorder());

            centerPanel.add(scroll);

            add(topPanel, BorderLayout.NORTH);
            add(centerPanel, BorderLayout.CENTER);

            supprimer_Patient.addActionListener(e -> {
                int ligne_selectionner = table.getSelectedRow();
                if (ligne_selectionner != -1) {
                    int id = Integer.parseInt(table.getValueAt(ligne_selectionner, 0).toString());
                    int valeur = JOptionPane.showConfirmDialog(
                            this,
                            "vous voulez vraiment supprimer le patient",
                            "Supprimer patient",
                            JOptionPane.YES_NO_OPTION
                    );
                    try {
                        if (valeur == JOptionPane.YES_OPTION) {
                            controlleur.supprimer_patient(id);
                            model.removeRow(ligne_selectionner);
                        }
                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "vous devez selectionner un ligne avant de supprimer un patient");
                }
            });

            ajouter_Patient.addActionListener(e -> {
                view.afficher_Panel("ajouter_patient");
            });

            modifer_Patient.addActionListener(e -> {
                int ligne_selectionner = table.getSelectedRow();
                if (ligne_selectionner != -1) {
                    int id = Integer.parseInt(table.getValueAt(ligne_selectionner, 0).toString());
                    try {
                        Patient patient2=controlleur.find_by_id(id);
                        view.ModifierPatient(patient2);
                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "vous devez selectionner un ligne avant de modifier un patient");
                }
            });

            afficher_Detailles.addActionListener(e -> {
                int ligne_selectionner = table.getSelectedRow();
                if (ligne_selectionner != -1) {
                    int id = Integer.parseInt(table.getValueAt(ligne_selectionner, 0).toString());
                    try {
                        view.afficherDetailsDossier(id);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(this, "Erreur: " + e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Sélectionnez un patient");
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
        }
    }
