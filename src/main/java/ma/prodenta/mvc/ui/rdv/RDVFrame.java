package ma.prodenta.mvc.ui.rdv;

import com.toedter.calendar.JDateChooser;
import ma.prodenta.config.Application_contexte;
import ma.prodenta.entities.En.Patient;
import ma.prodenta.mvc.controllers.modules.dossierMedical.DossierMedicalController;
import ma.prodenta.mvc.controllers.modules.rdv.RDVController;
import ma.prodenta.mvc.dto.rdv.RDVDTO;
import ma.prodenta.mvc.ui.dashboard.Dashboard_view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RDVFrame extends JPanel {

    // Palette de couleurs ProDenta
    private static final Color PRIMARY_COLOR = new Color(0, 150, 136); // Teal
    private static final Color ACCENT_COLOR = new Color(255, 87, 34); // Orange
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    private static final Color BORDER_COLOR = new Color(224, 224, 224);
    private static final Color HOVER_COLOR = new Color(245, 245, 245);

    private RDVController controller;
    private DossierMedicalController dossierController;
    private JTable table;
    private DefaultTableModel model;
    private Dashboard_view dashboard;
    private JButton btnAjouter, btnModifier, btnSupprimer, btnActualiser;
    private JDateChooser dateChooser;

    public RDVFrame(Dashboard_view dashboard) {
        this.dashboard = dashboard;
        this.controller = new RDVController();
        this.dossierController = Application_contexte.getDossierMedicalController();

        initializeUI();
        loadRDVs();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(0, 0));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        // Panel principal avec titre et actions
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // En-tête avec titre
        JPanel headerPanel = createHeaderPanel();

        // Panel des actions
        JPanel actionsPanel = createActionsPanel();

        // Panel de la table avec style carte
        JPanel tableCard = createTableCard();

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(actionsPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Gestion des Rendez-vous");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel subtitleLabel = new JLabel("Gérez et organisez vos rendez-vous patients");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(BACKGROUND_COLOR);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);

        panel.add(textPanel, BorderLayout.WEST);

        return panel;
    }

    private JPanel createActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panel.setBackground(BACKGROUND_COLOR);

        // Boutons d'action
        btnAjouter = createStyledButton("+ Nouveau RDV", PRIMARY_COLOR, true);
        btnModifier = createStyledButton("Modifier", new Color(76, 175, 80), false);
        btnSupprimer = createStyledButton("Supprimer", new Color(244, 67, 54), false);
        btnActualiser = createStyledButton("⟳ Actualiser", new Color(96, 125, 139), false);

        // Filtre par date
        JPanel dateFilterPanel = createDateFilterPanel();

        panel.add(btnAjouter);
        panel.add(btnModifier);
        panel.add(btnSupprimer);
        panel.add(btnActualiser);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(dateFilterPanel);

        setupButtonActions();

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isPrimary) {
            button.setPreferredSize(new Dimension(160, 40));
        } else {
            button.setPreferredSize(new Dimension(120, 40));
        }

        // Effet hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JPanel createDateFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JLabel dateLabel = new JLabel("📅");
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setPreferredSize(new Dimension(140, 30));
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JButton btnFiltrerDate = new JButton("Filtrer");
        btnFiltrerDate.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnFiltrerDate.setForeground(PRIMARY_COLOR);
        btnFiltrerDate.setBackground(CARD_COLOR);
        btnFiltrerDate.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btnFiltrerDate.setFocusPainted(false);
        btnFiltrerDate.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnFiltrerDate.addActionListener(e -> {
            Date date = dateChooser.getDate();
            if (date != null) {
                loadRDVsByDate(date);
            } else {
                loadRDVs();
            }
        });

        panel.add(dateLabel);
        panel.add(dateChooser);
        panel.add(btnFiltrerDate);

        return panel;
    }

    private JPanel createTableCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // Création du modèle de table
        String[] colonnes = {"ID", "Date RDV", "Heure", "Motif", "Note Médecin", "ID Dossier", "Patient"};
        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        customizeTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_COLOR);

        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    private void customizeTable() {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(50);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setGridColor(new Color(240, 240, 240));
        table.setFillsViewportHeight(true);
        table.setBackground(CARD_COLOR);
        table.setSelectionBackground(new Color(224, 247, 250));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setIntercellSpacing(new Dimension(10, 10));

        // Header personnalisé
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(250, 250, 250));
        header.setForeground(TEXT_SECONDARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 45));

        // Masquer la colonne ID
        TableColumn idColumn = table.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setResizable(false);

        // Masquer la colonne ID Dossier
        TableColumn idDossierColumn = table.getColumnModel().getColumn(5);
        idDossierColumn.setMinWidth(0);
        idDossierColumn.setMaxWidth(0);
        idDossierColumn.setResizable(false);

        // Largeurs des colonnes
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(200);
        table.getColumnModel().getColumn(6).setPreferredWidth(180);

        // Centrage pour certaines colonnes
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        // Renderer personnalisé pour améliorer l'apparence
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? CARD_COLOR : new Color(248, 248, 248));
                }

                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 1 && i != 2) { // Sauf les colonnes avec centerRenderer
                table.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
            }
        }
    }

    private void setupButtonActions() {
        btnAjouter.addActionListener(e -> {
            RDVForm form = new RDVForm(this, null);
            form.setVisible(true);
        });

        btnModifier.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Integer idRDV = (Integer) table.getValueAt(selectedRow, 0);
                try {
                    RDVDTO dto = controller.afficherParId(idRDV);
                    RDVForm form = new RDVForm(this, dto);
                    form.setVisible(true);
                } catch (Exception ex) {
                    showErrorDialog("Erreur lors du chargement du RDV: " + ex.getMessage());
                }
            } else {
                showWarningDialog("Veuillez sélectionner un RDV à modifier");
            }
        });

        btnSupprimer.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Êtes-vous sûr de vouloir supprimer ce rendez-vous?",
                        "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    Integer idRDV = (Integer) table.getValueAt(selectedRow, 0);
                    try {
                        controller.supprimerRDV(idRDV);
                        model.removeRow(selectedRow);
                        showSuccessDialog("Rendez-vous supprimé avec succès");
                    } catch (Exception ex) {
                        showErrorDialog("Erreur lors de la suppression: " + ex.getMessage());
                    }
                }
            } else {
                showWarningDialog("Veuillez sélectionner un RDV à supprimer");
            }
        });

        btnActualiser.addActionListener(e -> loadRDVs());
    }

    public void loadRDVs() {
        model.setRowCount(0);
        List<RDVDTO> rdvs = controller.afficherTous();
        for (RDVDTO rdv : rdvs) {
            addRDVToTable(rdv);
        }
    }

    public void loadRDVsByDate(Date date) {
        model.setRowCount(0);
        List<RDVDTO> rdvs = controller.afficherParDate(date);
        for (RDVDTO rdv : rdvs) {
            addRDVToTable(rdv);
        }
    }

    private void addRDVToTable(RDVDTO rdv) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        String dateStr = rdv.getDateRDV() != null ? dateFormat.format(rdv.getDateRDV()) : "";
        String heureStr = rdv.getHeure() != null ? timeFormat.format(rdv.getHeure()) : "";

        // Récupérer le nom du patient
        String patientNom = "";
        if (rdv.getIdDossier() != null) {
            try {
                Patient patient = dossierController.find_patient(rdv.getIdDossier());
                patientNom = patient.getNom() + " " + patient.getPrenom();
            } catch (Exception e) {
                patientNom = "Non trouvé";
            }
        }

        model.addRow(new Object[]{
                rdv.getIdRDV(),
                dateStr,
                heureStr,
                rdv.getMotif() != null ? rdv.getMotif() : "",
                rdv.getNoteMedecin() != null ? rdv.getNoteMedecin() : "",
                rdv.getIdDossier(),
                patientNom
        });
    }

    public void refresh() {
        loadRDVs();
    }

    // Méthodes utilitaires pour les dialogues
    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    private void showWarningDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Attention", JOptionPane.WARNING_MESSAGE);
    }
}