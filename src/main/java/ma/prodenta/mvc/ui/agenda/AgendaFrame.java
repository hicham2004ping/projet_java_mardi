package ma.prodenta.mvc.ui.agenda;

import com.toedter.calendar.JCalendar;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class AgendaFrame extends JPanel {

    private Dashboard_view dashboard;
    private JTable agendaTable;
    private DefaultTableModel tableModel;
    private JCalendar calendar;
    private JLabel selectedDateLabel;
    private Map<String, List<FakeRendezVous>> rendezVousMap;

    // Données factices pour les rendez-vous
    private static class FakeRendezVous {
        String heure;
        String patient;
        String medecin;
        String statut;
        String type;
        Color color;

        FakeRendezVous(String heure, String patient, String medecin, String statut, String type, Color color) {
            this.heure = heure;
            this.patient = patient;
            this.medecin = medecin;
            this.statut = statut;
            this.type = type;
            this.color = color;
        }
    }

    public AgendaFrame(Dashboard_view dashboard) {
        this.dashboard = dashboard;
        this.rendezVousMap = new HashMap<>();

        // Initialiser des rendez-vous factices pour démo
        initializeFakeData();

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout(20, 20));

        // Titre principal
        add(createTitlePanel(), BorderLayout.NORTH);

        // Contenu principal
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                createCalendarPanel(), createAgendaPanel());
        splitPane.setDividerLocation(400);
        splitPane.setDividerSize(3);
        splitPane.setBackground(Color.WHITE);

        add(splitPane, BorderLayout.CENTER);

        // Panel inférieur avec statistiques
        add(createStatsPanel(), BorderLayout.SOUTH);
    }

    private void initializeFakeData() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Aujourd'hui
        String todayStr = today.format(formatter);
        List<FakeRendezVous> todayList = new ArrayList<>();
        todayList.add(new FakeRendezVous("09:00", "Jean Dupont", "Dr. Martin", "Confirmé", "Consultation", new Color(0, 153, 204)));
        todayList.add(new FakeRendezVous("11:30", "Marie Curie", "Dr. Bernard", "Confirmé", "Contrôle", new Color(102, 204, 0)));
        todayList.add(new FakeRendezVous("14:00", "Pierre Durand", "Dr. Martin", "En attente", "Urgence", new Color(255, 153, 0)));
        todayList.add(new FakeRendezVous("16:30", "Sophie Lambert", "Dr. Petit", "Annulé", "Consultation", new Color(204, 0, 0)));
        rendezVousMap.put(todayStr, todayList);

        // Demain
        String tomorrowStr = today.plusDays(1).format(formatter);
        List<FakeRendezVous> tomorrowList = new ArrayList<>();
        tomorrowList.add(new FakeRendezVous("08:30", "Luc Tremblay", "Dr. Bernard", "Confirmé", "Suivi", new Color(0, 153, 204)));
        tomorrowList.add(new FakeRendezVous("10:15", "Julie Moreau", "Dr. Martin", "Confirmé", "Consultation", new Color(102, 204, 0)));
        rendezVousMap.put(tomorrowStr, tomorrowList);

        // Après-demain
        String dayAfterStr = today.plusDays(2).format(formatter);
        List<FakeRendezVous> dayAfterList = new ArrayList<>();
        dayAfterList.add(new FakeRendezVous("13:45", "Thomas Leroy", "Dr. Petit", "En attente", "Contrôle", new Color(255, 153, 0)));
        rendezVousMap.put(dayAfterStr, dayAfterList);

        // Semaine prochaine
        String nextWeekStr = today.plusDays(7).format(formatter);
        List<FakeRendezVous> nextWeekList = new ArrayList<>();
        nextWeekList.add(new FakeRendezVous("09:30", "Isabelle Roy", "Dr. Martin", "Confirmé", "Consultation", new Color(0, 153, 204)));
        nextWeekList.add(new FakeRendezVous("15:00", "David Girard", "Dr. Bernard", "Confirmé", "Suivi", new Color(102, 204, 0)));
        rendezVousMap.put(nextWeekStr, nextWeekList);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel titleLabel = new JLabel("Agenda Médical");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));

        // Date sélectionnée
        selectedDateLabel = new JLabel("Aujourd'hui");
        selectedDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectedDateLabel.setForeground(new Color(60, 60, 60));

        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(selectedDateLabel, BorderLayout.EAST);

        return titlePanel;
    }

    private JPanel createCalendarPanel() {
        JPanel calendarPanel = new JPanel(new BorderLayout(10, 10));
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel calendarTitle = new JLabel("Calendrier");
        calendarTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        calendarTitle.setForeground(new Color(0, 102, 204));
        calendarTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        calendarPanel.add(calendarTitle, BorderLayout.NORTH);

        // Création du calendrier
        calendar = new JCalendar();
        calendar.setDecorationBackgroundColor(Color.WHITE);
        calendar.setSundayForeground(new Color(204, 0, 0));
        calendar.setWeekdayForeground(new Color(60, 60, 60));
        calendar.setTodayButtonVisible(true);

        // Styliser le calendrier
        calendar.getDayChooser().getDayPanel().setBackground(Color.WHITE);
        calendar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Ajouter des marqueurs pour les jours avec rendez-vous
        highlightAppointmentDays();

        calendar.addPropertyChangeListener("calendar", evt -> {
            Date selectedDate = calendar.getDate();
            updateAgendaForDate(selectedDate);
            updateSelectedDateLabel(selectedDate);
        });

        calendarPanel.add(calendar, BorderLayout.CENTER);

        return calendarPanel;
    }

    private void highlightAppointmentDays() {
        // Personnaliser l'apparence des jours avec rendez-vous
        calendar.getDayChooser().addPropertyChangeListener("day", evt -> {
            // Cette écoute permet de réagir aux changements de jour
            highlightDaysWithAppointments();
        });

        // Appliquer la mise en surbrillance initiale
        highlightDaysWithAppointments();
    }

    private void highlightDaysWithAppointments() {
        // Accéder au panel des jours
        JPanel dayPanel = calendar.getDayChooser().getDayPanel();

        // Obtenir le mois et l'année actuels du calendrier
        Calendar cal = calendar.getCalendar();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        // Parcourir les composants (boutons de jours)
        Component[] components = dayPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JButton) {
                JButton dayButton = (JButton) comp;
                try {
                    int day = Integer.parseInt(dayButton.getText());

                    // Créer la clé de date
                    LocalDate date = LocalDate.of(currentYear, currentMonth + 1, day);
                    String dateKey = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    // Vérifier si ce jour a des rendez-vous
                    if (rendezVousMap.containsKey(dateKey) && !rendezVousMap.get(dateKey).isEmpty()) {
                        // Marquer le bouton (petit point coloré ou bordure)
                        dayButton.setBackground(new Color(230, 240, 255));
                        dayButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
                    }
                } catch (NumberFormatException e) {
                    // Ignorer les boutons qui ne sont pas des jours (espaces vides)
                }
            }
        }
    }

    private JPanel createAgendaPanel() {
        JPanel agendaPanel = new JPanel(new BorderLayout(10, 10));
        agendaPanel.setBackground(Color.WHITE);
        agendaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // En-tête de l'agenda
        JPanel agendaHeader = new JPanel(new BorderLayout());
        agendaHeader.setBackground(Color.WHITE);

        JLabel agendaTitle = new JLabel("Rendez-vous du Jour");
        agendaTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        agendaTitle.setForeground(new Color(0, 102, 204));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = createActionButton("Nouveau Rendez-vous", new Color(0, 153, 204));
        JButton printButton = createActionButton("Imprimer", new Color(102, 102, 102));

        addButton.addActionListener(e -> showAddAppointmentDialog());
        printButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Impression de l'agenda...", "Information", JOptionPane.INFORMATION_MESSAGE));

        buttonPanel.add(addButton);
        buttonPanel.add(printButton);

        agendaHeader.add(agendaTitle, BorderLayout.WEST);
        agendaHeader.add(buttonPanel, BorderLayout.EAST);
        agendaHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        agendaPanel.add(agendaHeader, BorderLayout.NORTH);

        // Table des rendez-vous
        String[] columns = {"Heure", "Patient", "Médecin", "Type", "Statut", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Seule la colonne Actions est éditable
            }
        };

        agendaTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);

                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                }

                // Coloration par statut
                String statut = getValueAt(row, 4).toString();
                if (column == 4) { // Colonne Statut
                    if (statut.equals("Confirmé")) {
                        c.setForeground(new Color(0, 153, 0));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (statut.equals("En attente")) {
                        c.setForeground(new Color(255, 153, 0));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else if (statut.equals("Annulé")) {
                        c.setForeground(new Color(204, 0, 0));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    }
                }

                return c;
            }
        };

        // Style de la table
        agendaTable.setRowHeight(40);
        agendaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        agendaTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        agendaTable.setGridColor(new Color(230, 230, 230));
        agendaTable.setShowGrid(false);

        // Style de l'en-tête
        agendaTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        agendaTable.getTableHeader().setBackground(new Color(0, 102, 204));
        agendaTable.getTableHeader().setForeground(Color.WHITE);
        agendaTable.getTableHeader().setReorderingAllowed(false);

        // Renderer pour la colonne Actions
        agendaTable.getColumnModel().getColumn(5).setCellRenderer(new AppointmentActionRenderer());
        agendaTable.getColumnModel().getColumn(5).setCellEditor(new AppointmentActionEditor());

        JScrollPane scrollPane = new JScrollPane(agendaTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        agendaPanel.add(scrollPane, BorderLayout.CENTER);

        // Charger les rendez-vous d'aujourd'hui
        updateAgendaForDate(new Date());

        return agendaPanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Statistiques factices
        String[][] statsData = {
                {"Aujourd'hui", "4", "rdv"},
                {"Demain", "2", "rdv"},
                {"Cette semaine", "12", "rdv"},
                {"Disponibilité", "85%", "taux"}
        };

        Color[] colors = {
                new Color(0, 153, 204),
                new Color(102, 204, 0),
                new Color(255, 153, 0),
                new Color(153, 102, 204)
        };

        for (int i = 0; i < statsData.length; i++) {
            JPanel statCard = createStatCard(statsData[i][0], statsData[i][1], statsData[i][2], colors[i]);
            statsPanel.add(statCard);
        }

        return statsPanel;
    }

    private JPanel createStatCard(String title, String value, String unit, Color color) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(100, 100, 100));

        JPanel valuePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        valuePanel.setBackground(Color.WHITE);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JLabel unitLabel = new JLabel(" " + unit);
        unitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        unitLabel.setForeground(new Color(150, 150, 150));

        valuePanel.add(valueLabel);
        valuePanel.add(unitLabel);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.CENTER);

        return card;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void updateAgendaForDate(Date date) {
        tableModel.setRowCount(0);

        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String dateKey = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<FakeRendezVous> appointments = rendezVousMap.get(dateKey);

        if (appointments != null && !appointments.isEmpty()) {
            for (FakeRendezVous rdv : appointments) {
                tableModel.addRow(new Object[] {
                        rdv.heure,
                        rdv.patient,
                        rdv.medecin,
                        rdv.type,
                        rdv.statut,
                        "Modifier | Supprimer"
                });
            }
        } else {
            tableModel.addRow(new Object[] {
                    "09:00", "Aucun rendez-vous", "", "", "Libre", "Ajouter"
            });
            tableModel.addRow(new Object[] {
                    "10:30", "Aucun rendez-vous", "", "", "Libre", "Ajouter"
            });
            tableModel.addRow(new Object[] {
                    "14:00", "Aucun rendez-vous", "", "", "Libre", "Ajouter"
            });
            tableModel.addRow(new Object[] {
                    "16:00", "Aucun rendez-vous", "", "", "Libre", "Ajouter"
            });
        }
    }

    private void updateSelectedDateLabel(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String formattedDate = localDate.format(formatter);

        // Capitaliser la première lettre
        formattedDate = formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);

        selectedDateLabel.setText(formattedDate);
    }

    private void showAddAppointmentDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Nouveau Rendez-vous", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField patientField = new JTextField(20);
        JTextField medecinField = new JTextField(20);
        JComboBox<String> heureBox = new JComboBox<>(new String[]{"09:00", "10:30", "14:00", "16:00"});
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Consultation", "Contrôle", "Urgence", "Suivi"});

        int row = 0;
        addDialogField(contentPanel, gbc, row++, "Patient:", patientField);
        addDialogField(contentPanel, gbc, row++, "Médecin:", medecinField);
        addDialogField(contentPanel, gbc, row++, "Heure:", heureBox);
        addDialogField(contentPanel, gbc, row++, "Type:", typeBox);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = createActionButton("Enregistrer", new Color(0, 153, 204));
        JButton cancelButton = createActionButton("Annuler", new Color(153, 153, 153));

        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Rendez-vous ajouté (simulation)",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        contentPanel.add(buttonPanel, gbc);

        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void addDialogField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }

    // Renderer pour les actions dans le tableau
    private class AppointmentActionRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());

            String[] actions = value.toString().split("\\|");
            for (String action : actions) {
                JLabel label = new JLabel(action.trim());
                label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                label.setForeground(new Color(0, 102, 204));
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                panel.add(label);
            }

            return panel;
        }
    }

    // Editor pour les actions dans le tableau
    private class AppointmentActionEditor extends DefaultCellEditor {
        private JPanel panel;

        public AppointmentActionEditor() {
            super(new JTextField());
            setClickCountToStart(1);

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

            JLabel editLabel = new JLabel("Modifier");
            JLabel deleteLabel = new JLabel("Supprimer");

            editLabel.setForeground(new Color(0, 102, 204));
            deleteLabel.setForeground(new Color(204, 0, 0));

            editLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            editLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(AgendaFrame.this,
                            "Modification du rendez-vous (simulation)", "Information",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });

            deleteLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(AgendaFrame.this,
                            "Voulez-vous vraiment supprimer ce rendez-vous ?",
                            "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(AgendaFrame.this,
                                "Rendez-vous supprimé (simulation)", "Information",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            panel.add(editLabel);
            panel.add(new JLabel("|"));
            panel.add(deleteLabel);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }
    }
}