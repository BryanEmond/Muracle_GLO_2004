package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Utilitaire;
import ca.ulaval.glo2004.gestion.GestionnaireSalle;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;

public class MainWindow {
    public JPanel rootPanel;
    private JPanel propertiesPanel;
    private JPanel rightPanel;
    private JPanel controlPanel;
    public JPanel mainPanel;
    private JPanel buttonsPanel;
    private JButton btnSave;
    private JButton btnUndo;
    private JButton btnRedo;
    private JButton btnGrille;
    private JButton btnFenetre;
    private JButton btnPorte;
    private JButton btnPrise;
    private JButton btnRetourAir;
    private JButton btnSupprimer;
    private JPanel DimensionsPanel;
    private JButton btnDimensionsCollapse;
    private JPanel dimensionPanelContent;
    private JPanel dimLargeurPanel;
    private JTextField tbLargeur;
    private JTextField tbProfondeur;
    private JTextField tbHauteur;
    private JTextField tbEpaisseurMurs;
    private JTextField tbLargeurPli;
    private JTextField tbPliSoudure;

    private JButton btnElvNordEXT;
    private JButton btnElvNordINT;
    private JButton btnElvEstEXT;
    private JButton btnElvEstINT;
    private JButton btnElvSudEXT;
    private JButton btnELVSudINT;
    private JButton btnElvOuestEXT;
    private JButton btnElvOuestINT;
    private JButton btnPlan;

    public JPanel starterPanel;
    private JButton creerUnNouveauProjetButton;
    private JButton ouvrirUnProjectExistantButton;
    private JLabel title;
    private Box container;
    private MainWindow mainWindow;
    private DrawingPanel panel;
    GestionnaireSalle gestionnaireSalle;
    private String filePath;
    public MainWindow(GestionnaireSalle gestionnaireSalle) {
        this.gestionnaireSalle = gestionnaireSalle;
        mainWindow = this;
        creerUnNouveauProjetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainWindow = new MainWindow(gestionnaireSalle);
                setHomePage(e);
                JFileChooser fc = new JFileChooser();
                fc.setSelectedFile(new File("sale.ser"));
                int returnFcVal = fc.showSaveDialog(rootPanel.getParent());
                if(returnFcVal == JFileChooser.APPROVE_OPTION){
                    try{
                        File file = fc.getSelectedFile();
                        panel = new DrawingPanel(mainWindow);
                        mainWindow.gestionnaireSalle.enregistrerSalle(file.getPath());
                        mainWindow.mainPanel.add(panel);
                    }catch (Exception error){
                        System.out.println(error);
                    }
                }
            }
        });
        ouvrirUnProjectExistantButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainWindow = new MainWindow(gestionnaireSalle);
                setHomePage(e);
                JFileChooser fc = new JFileChooser("d:");
                int returnFcVal = fc.showOpenDialog(rootPanel.getParent());
                if(returnFcVal == JFileChooser.APPROVE_OPTION){
                    try{
                        File file = fc.getSelectedFile();

                        mainWindow.gestionnaireSalle.chargerSalle(file.getPath());
                        panel = new DrawingPanel(mainWindow,mainWindow.gestionnaireSalle.getSalleActive());
                        mainWindow.mainPanel.add(panel);
                    }catch (Exception error){
                        System.out.println(error);
                    }
                }
            }
        });
        btnDimensionsCollapse.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean isActivated = !dimensionPanelContent.isVisible();
                dimensionPanelContent.setVisible(isActivated);
            }
        });
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.enregistrerSalle(null);
            }
        });
        this.$$$getRootComponent$$$().registerKeyboardAction((ActionListener) e -> {
            gestionnaireSalle.enregistrerSalle(null);
        }, KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK),JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.mainPanel.addMouseWheelListener(e -> {
            gestionnaireSalle.zoomer(-e.getWheelRotation(), e.getX(), e.getY());
            this.mainPanel.validate();
            this.mainPanel.repaint();
        });

        btnElvEstINT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

               panel = new DrawingPanel(mainWindow, mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.EST));
                mainWindow.mainPanel.add(panel);
            }
        });

        btnElvEstEXT.addMouseListener(new MouseAdapter() {
            //TODO enlever : retour d'air et prise de courant des accessoires
            @Override
            public void mousePressed(MouseEvent e) {

                panel = new DrawingPanel(mainWindow, mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.EST));
                mainWindow.mainPanel.add(panel);
            }
        });

        btnELVSudINT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                panel = new DrawingPanel(mainWindow, mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.SUD));
                mainWindow.mainPanel.add(panel);
            }
        });
        btnElvSudEXT.addMouseListener(new MouseAdapter() {
            //TODO enlever : retour d'air et prise de courant des accessoires
            @Override
            public void mousePressed(MouseEvent e) {

                panel = new DrawingPanel(mainWindow, mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.SUD));
                mainWindow.mainPanel.add(panel);
            }
        });
        btnElvOuestINT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                panel = new DrawingPanel(mainWindow, mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.OUEST));
                mainWindow.mainPanel.add(panel);
            }
        });
        btnElvOuestEXT.addMouseListener(new MouseAdapter() {
            //TODO enlever : retour d'air et prise de courant des accessoires
            @Override
            public void mousePressed(MouseEvent e) {

                panel = new DrawingPanel(mainWindow, mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.OUEST));
                mainWindow.mainPanel.add(panel);
            }
        });

        btnElvNordINT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                panel = new DrawingPanel(mainWindow, mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.NORD));
                mainWindow.mainPanel.add(panel);
            }
        });

        btnElvNordEXT.addMouseListener(new MouseAdapter() {
            //TODO enlever : retour d'air et prise de courant des accessoires
            @Override
            public void mousePressed(MouseEvent e) {

                panel = new DrawingPanel(mainWindow, mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.NORD));
                mainWindow.mainPanel.add(panel);
            }
        });

        btnPlan.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                panel = new DrawingPanel(mainWindow,mainWindow.gestionnaireSalle.getSalleActive());
                mainWindow.mainPanel.add(panel);
            }
        });
    }

    {
        $$$setupUI$$$();
    }
    private void setHomePage(MouseEvent e){
        Component component = (Component) e.getComponent();
        JFrame frame = (JFrame) SwingUtilities.getRoot(component);
        frame.setContentPane(mainWindow.rootPanel);
        frame.pack();
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        starterPanel = new JPanel();
        starterPanel.setBackground(new Color(60,64,65));
        title = new JLabel();
        title.setHorizontalAlignment((int)JPanel.CENTER_ALIGNMENT);
        title.setFont(new Font("Segoe Script",0,48));
        title.setForeground(new Color(255,255,255));
        title.setText("Muracle");
        creerUnNouveauProjetButton = new JButton();
        creerUnNouveauProjetButton.setText("Créer un Nouveau Projet");
        creerUnNouveauProjetButton.setForeground(new Color(255,255,255));
        creerUnNouveauProjetButton.setBackground(new Color(60,64,65));
        ouvrirUnProjectExistantButton  = new JButton();
        ouvrirUnProjectExistantButton.setText("Ouvrir un projet Existant");
        ouvrirUnProjectExistantButton.setForeground(new Color(255,255,255));
        ouvrirUnProjectExistantButton.setBackground(new Color(60,64,65));

        btnDimensionsCollapse = new JButton();
        DimensionsPanel = new JPanel();
        propertiesPanel = new JPanel();
        dimensionPanelContent = new JPanel();
        dimLargeurPanel = new JPanel();
        tbProfondeur = new JTextField();
        mainPanel = new JPanel();
        btnSupprimer = new JButton();
        btnRetourAir = new JButton();
        btnPrise = new JButton();
        btnPorte = new JButton();
        btnFenetre = new JButton();
        btnGrille = new JButton();
        btnRedo = new JButton();
        btnUndo = new JButton();
        btnSave = new JButton();
        buttonsPanel = new JPanel();
        controlPanel = new JPanel();
        rightPanel = new JPanel();
        tbPliSoudure = new JTextField();
        tbLargeurPli = new JTextField();
        tbEpaisseurMurs = new JTextField();
        tbHauteur = new JTextField();

        container = Box.createVerticalBox();
        container.add(title);
        container.add(creerUnNouveauProjetButton);
        container.add(Box.createRigidArea(new Dimension(0,5)));
        container.add(ouvrirUnProjectExistantButton);
        starterPanel.add(container);

        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout(0, 0));
        rootPanel.setBackground(new Color(-12763843));
        propertiesPanel.setLayout(new BorderLayout(0, 0));
        propertiesPanel.setBackground(new Color(-8882056));
        propertiesPanel.setEnabled(true);
        propertiesPanel.setMinimumSize(new Dimension(235, 0));
        propertiesPanel.setPreferredSize(new Dimension(235, 0));
        rootPanel.add(propertiesPanel, BorderLayout.WEST);
        DimensionsPanel.setLayout(new BorderLayout(0, 0));
        DimensionsPanel.setBackground(new Color(-8882056));
        DimensionsPanel.setMinimumSize(new Dimension(221, 240));
        DimensionsPanel.setPreferredSize(new Dimension(221, 240));
        propertiesPanel.add(DimensionsPanel, BorderLayout.NORTH);
        btnDimensionsCollapse.setBackground(new Color(-8224126));
        btnDimensionsCollapse.setBorderPainted(false);
        btnDimensionsCollapse.setFocusPainted(false);
        Font btnDimensionsCollapseFont = this.$$$getFont$$$(null, -1, 11, btnDimensionsCollapse.getFont());
        if (btnDimensionsCollapseFont != null) btnDimensionsCollapse.setFont(btnDimensionsCollapseFont);
        btnDimensionsCollapse.setHideActionText(true);
        btnDimensionsCollapse.setHorizontalAlignment(2);
        btnDimensionsCollapse.setHorizontalTextPosition(10);
        btnDimensionsCollapse.setIcon(new ImageIcon(getClass().getResource("/buttons/collapse-up.png")));
        btnDimensionsCollapse.setIconTextGap(0);
        btnDimensionsCollapse.setText("DIMENSIONS DE LA SALLE");
        DimensionsPanel.add(btnDimensionsCollapse, BorderLayout.NORTH);
        dimensionPanelContent.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        dimensionPanelContent.setBackground(new Color(-4408132));
        DimensionsPanel.add(dimensionPanelContent, BorderLayout.CENTER);
        dimLargeurPanel.setLayout(new BorderLayout(0, 0));
        dimLargeurPanel.setBackground(new Color(-4408132));
        dimLargeurPanel.setPreferredSize(new Dimension(200, 24));
        dimensionPanelContent.add(dimLargeurPanel);
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-4408132));
        label1.setForeground(new Color(-16777216));
        label1.setText("LARGEUR :");
        dimLargeurPanel.add(label1, BorderLayout.WEST);
        tbLargeur = new JTextField();
        tbLargeur.setBackground(new Color(-1));
        tbLargeur.setForeground(new Color(-16777216));
        tbLargeur.setPreferredSize(new Dimension(60, 30));
        tbLargeur.setText("45\"");
        dimLargeurPanel.add(tbLargeur, BorderLayout.EAST);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        panel1.setBackground(new Color(-4408132));
        panel1.setPreferredSize(new Dimension(200, 24));
        dimensionPanelContent.add(panel1);
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-4408132));
        label2.setForeground(new Color(-16777216));
        label2.setMaximumSize(new Dimension(64, 300));
        label2.setMinimumSize(new Dimension(64, 300));
        label2.setPreferredSize(new Dimension(120, 300));
        label2.setText("PROFONDEUR :");
        panel1.add(label2, BorderLayout.WEST);
        tbProfondeur.setBackground(new Color(-1));
        tbProfondeur.setForeground(new Color(-16777216));
        tbProfondeur.setPreferredSize(new Dimension(60, 30));
        tbProfondeur.setText("48\"");
        panel1.add(tbProfondeur, BorderLayout.EAST);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel2.setBackground(new Color(-4408132));
        panel2.setPreferredSize(new Dimension(200, 24));
        dimensionPanelContent.add(panel2);
        final JLabel label3 = new JLabel();
        label3.setBackground(new Color(-4408132));
        label3.setForeground(new Color(-16777216));
        label3.setText("HAUTEUR :");
        panel2.add(label3, BorderLayout.WEST);
        tbHauteur.setBackground(new Color(-1));
        tbHauteur.setForeground(new Color(-16777216));
        tbHauteur.setPreferredSize(new Dimension(60, 30));
        tbHauteur.setText("96\"");
        panel2.add(tbHauteur, BorderLayout.EAST);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel3.setBackground(new Color(-4408132));
        panel3.setPreferredSize(new Dimension(200, 24));
        dimensionPanelContent.add(panel3);
        final JLabel label4 = new JLabel();
        label4.setBackground(new Color(-4408132));
        label4.setForeground(new Color(-16777216));
        label4.setText("ÉPAISSEUR MURS :");
        panel3.add(label4, BorderLayout.WEST);
        tbEpaisseurMurs.setBackground(new Color(-1));
        tbEpaisseurMurs.setForeground(new Color(-16777216));
        tbEpaisseurMurs.setPreferredSize(new Dimension(60, 30));
        tbEpaisseurMurs.setText("4\"");
        panel3.add(tbEpaisseurMurs, BorderLayout.EAST);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setBackground(new Color(-4408132));
        panel4.setPreferredSize(new Dimension(200, 24));
        dimensionPanelContent.add(panel4);
        final JLabel label5 = new JLabel();
        label5.setBackground(new Color(-4408132));
        label5.setForeground(new Color(-16777216));
        label5.setText("LARGEUR DE PLI :");
        panel4.add(label5, BorderLayout.WEST);
        tbLargeurPli.setBackground(new Color(-1));
        tbLargeurPli.setForeground(new Color(-16777216));
        tbLargeurPli.setPreferredSize(new Dimension(60, 30));
        tbLargeurPli.setText("2\"");
        panel4.add(tbLargeurPli, BorderLayout.EAST);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel5.setBackground(new Color(-4408132));
        panel5.setPreferredSize(new Dimension(200, 24));
        dimensionPanelContent.add(panel5);
        final JLabel label6 = new JLabel();
        label6.setBackground(new Color(-4408132));
        label6.setForeground(new Color(-16777216));
        label6.setText("PLI DE SOUDURE :");
        panel5.add(label6, BorderLayout.WEST);
        tbPliSoudure.setBackground(new Color(-1));
        tbPliSoudure.setForeground(new Color(-16777216));
        tbPliSoudure.setPreferredSize(new Dimension(60, 30));
        tbPliSoudure.setText("45°");
        panel5.add(tbPliSoudure, BorderLayout.EAST);
        rightPanel.setLayout(new BorderLayout(0, 0));
        rootPanel.add(rightPanel, BorderLayout.CENTER);
        controlPanel.setLayout(new BorderLayout(0, 0));
        controlPanel.setBackground(new Color(-12829632));
        controlPanel.setMinimumSize(new Dimension(24, 230));
        controlPanel.setOpaque(true);
        controlPanel.setPreferredSize(new Dimension(24, 230));
        rightPanel.add(controlPanel, BorderLayout.NORTH);
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setBackground(new Color(-12829633));
        controlPanel.add(buttonsPanel, BorderLayout.WEST);
        btnSave.setBackground(new Color(-12829636));
        btnSave.setIcon(new ImageIcon(getClass().getResource("/buttons/save.png")));
        btnSave.setMargin(new Insets(0, 0, 0, 0));
        btnSave.setMaximumSize(new Dimension(50, 50));
        btnSave.setMinimumSize(new Dimension(50, 50));
        btnSave.setPreferredSize(new Dimension(50, 50));
        btnSave.setText("");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnSave, gbc);
        btnUndo.setBackground(new Color(-12829636));
        btnUndo.setIcon(new ImageIcon(getClass().getResource("/buttons/undo.png")));
        btnUndo.setMargin(new Insets(0, 0, 0, 0));
        btnUndo.setMaximumSize(new Dimension(50, 50));
        btnUndo.setMinimumSize(new Dimension(50, 50));
        btnUndo.setPreferredSize(new Dimension(50, 50));
        btnUndo.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnUndo, gbc);
        btnRedo.setBackground(new Color(-12829636));
        btnRedo.setIcon(new ImageIcon(getClass().getResource("/buttons/redo.png")));
        btnRedo.setMargin(new Insets(0, 0, 0, 0));
        btnRedo.setMaximumSize(new Dimension(50, 50));
        btnRedo.setMinimumSize(new Dimension(50, 50));
        btnRedo.setPreferredSize(new Dimension(50, 50));
        btnRedo.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnRedo, gbc);
        btnGrille.setBackground(new Color(-12829636));
        btnGrille.setIcon(new ImageIcon(getClass().getResource("/buttons/grille.png")));
        btnGrille.setMargin(new Insets(0, 0, 0, 0));
        btnGrille.setMaximumSize(new Dimension(50, 50));
        btnGrille.setMinimumSize(new Dimension(50, 50));
        btnGrille.setPreferredSize(new Dimension(50, 50));
        btnGrille.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnGrille, gbc);
        btnFenetre.setBackground(new Color(-12829636));
        btnFenetre.setIcon(new ImageIcon(getClass().getResource("/buttons/fenetre.png")));
        btnFenetre.setMargin(new Insets(0, 0, 0, 0));
        btnFenetre.setMaximumSize(new Dimension(50, 50));
        btnFenetre.setMinimumSize(new Dimension(50, 50));
        btnFenetre.setPreferredSize(new Dimension(50, 50));
        btnFenetre.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnFenetre, gbc);
        btnPorte.setBackground(new Color(-12829636));
        btnPorte.setIcon(new ImageIcon(getClass().getResource("/buttons/porte.png")));
        btnPorte.setMargin(new Insets(0, 0, 0, 0));
        btnPorte.setMaximumSize(new Dimension(50, 50));
        btnPorte.setMinimumSize(new Dimension(50, 50));
        btnPorte.setPreferredSize(new Dimension(50, 50));
        btnPorte.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnPorte, gbc);
        btnPrise.setBackground(new Color(-12829636));
        btnPrise.setIcon(new ImageIcon(getClass().getResource("/buttons/prise.png")));
        btnPrise.setMargin(new Insets(0, 0, 0, 0));
        btnPrise.setMaximumSize(new Dimension(50, 50));
        btnPrise.setMinimumSize(new Dimension(50, 50));
        btnPrise.setPreferredSize(new Dimension(50, 50));
        btnPrise.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnPrise, gbc);
        btnRetourAir.setBackground(new Color(-12829636));
        btnRetourAir.setIcon(new ImageIcon(getClass().getResource("/buttons/air.png")));
        btnRetourAir.setMargin(new Insets(0, 0, 0, 0));
        btnRetourAir.setMaximumSize(new Dimension(50, 50));
        btnRetourAir.setMinimumSize(new Dimension(50, 50));
        btnRetourAir.setPreferredSize(new Dimension(50, 50));
        btnRetourAir.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnRetourAir, gbc);
        btnSupprimer.setBackground(new Color(-12829636));
        btnSupprimer.setIcon(new ImageIcon(getClass().getResource("/buttons/supprimer.png")));
        btnSupprimer.setMargin(new Insets(0, 0, 0, 0));
        btnSupprimer.setMaximumSize(new Dimension(50, 50));
        btnSupprimer.setMinimumSize(new Dimension(50, 50));
        btnSupprimer.setPreferredSize(new Dimension(50, 50));
        btnSupprimer.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);
        buttonsPanel.add(btnSupprimer, gbc);
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setAutoscrolls(true);
        mainPanel.setBackground(new Color(-1));
        mainPanel.setMinimumSize(new Dimension(200, 24));
        mainPanel.setPreferredSize(new Dimension(200, 24));
        rightPanel.add(mainPanel, BorderLayout.CENTER);


        btnElvOuestEXT = new JButton();
        btnElvOuestEXT.setBackground(new Color(-12829636));
        btnElvOuestEXT.setIcon(new ImageIcon(getClass().getResource("/buttons/exterieurOuest.png")));
        btnElvOuestEXT.setMargin(new Insets(0,0,0,0));
        btnElvOuestEXT.setMaximumSize(new Dimension(30,50));
        btnElvOuestEXT.setMinimumSize(new Dimension(30,50));
        btnElvOuestEXT.setPreferredSize(new Dimension(30,50));
        btnElvOuestEXT.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2,2,2,2);
        buttonsPanel.add(btnElvOuestEXT, gbc);

        btnElvOuestINT = new JButton();
        btnElvOuestINT.setBackground(new Color(-12829636));
        btnElvOuestINT.setIcon(new ImageIcon(getClass().getResource("/buttons/interieurOuest.png")));
        btnElvOuestINT.setMargin(new Insets(0,0,0,0));
        btnElvOuestINT.setMaximumSize(new Dimension(30,50));
        btnElvOuestINT.setMinimumSize(new Dimension(30,50));
        btnElvOuestINT.setPreferredSize(new Dimension(30,50));
        btnElvOuestINT.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 12;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2,2,2,2);
        buttonsPanel.add(btnElvOuestINT, gbc);

        btnElvNordEXT = new JButton();
        btnElvNordEXT.setBackground(new Color(-12829636));
        btnElvNordEXT.setIcon(new ImageIcon(getClass().getResource("/buttons/exterieurNord.png")));
        btnElvNordEXT.setMargin(new Insets(0,0,0,0));
        btnElvNordEXT.setMaximumSize(new Dimension(30,30));
        btnElvNordEXT.setMinimumSize(new Dimension(30,30));
        btnElvNordEXT.setPreferredSize(new Dimension(30,30));
        btnElvNordEXT.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50,2,0,2);
        buttonsPanel.add(btnElvNordEXT, gbc);

        btnElvNordINT = new JButton();
        btnElvNordINT.setBackground(new Color(-12829636));
        btnElvNordINT.setIcon(new ImageIcon(getClass().getResource("/buttons/interieurNord.png")));
        btnElvNordINT.setMargin(new Insets(0,0,0,0));
        btnElvNordINT.setMaximumSize(new Dimension(50,30));
        btnElvNordINT.setMinimumSize(new Dimension(50,30));
        btnElvNordINT.setPreferredSize(new Dimension(50,30));
        btnElvNordINT.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2,2,18,2);
        buttonsPanel.add(btnElvNordINT, gbc);

        btnElvSudEXT = new JButton();
        btnElvSudEXT.setBackground(new Color(-12829636));
        btnElvSudEXT.setIcon(new ImageIcon(getClass().getResource("/buttons/exterieurSud.png")));
        btnElvSudEXT.setMargin(new Insets(0,0,0,0));
        btnElvSudEXT.setMaximumSize(new Dimension(30,30));
        btnElvSudEXT.setMinimumSize(new Dimension(30,30));
        btnElvSudEXT.setPreferredSize(new Dimension(30,30));
        btnElvSudEXT.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0,2,50,2);
        buttonsPanel.add(btnElvSudEXT, gbc);

        btnELVSudINT = new JButton();
        btnELVSudINT.setBackground(new Color(-12829636));
        btnELVSudINT.setIcon(new ImageIcon(getClass().getResource("/buttons/interieurSud.png")));
        btnELVSudINT.setMargin(new Insets(0,0,0,0));
        btnELVSudINT.setMaximumSize(new Dimension(50,30));
        btnELVSudINT.setMinimumSize(new Dimension(50,30));
        btnELVSudINT.setPreferredSize(new Dimension(50,30));
        btnELVSudINT.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(18,2,2,2);
        buttonsPanel.add(btnELVSudINT, gbc);

        btnElvEstEXT = new JButton();
        btnElvEstEXT.setBackground(new Color(-12829636));
        btnElvEstEXT.setIcon(new ImageIcon(getClass().getResource("/buttons/exterieurEst.png")));
        btnElvEstEXT.setMargin(new Insets(0,0,0,0));
        btnElvEstEXT.setMaximumSize(new Dimension(30,50));
        btnElvEstEXT.setMinimumSize(new Dimension(30,50));
        btnElvEstEXT.setPreferredSize(new Dimension(30,50));
        btnElvEstEXT.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 15;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2,2,2,2);
        buttonsPanel.add(btnElvEstEXT, gbc);

        btnElvEstINT = new JButton();
        btnElvEstINT.setIcon(new ImageIcon(getClass().getResource("/buttons/interieurEst.png")));
        btnElvEstINT.setBackground(new Color(-1));
        btnElvEstINT.setMargin(new Insets(0,0,0,0));
        btnElvEstINT.setMaximumSize(new Dimension(30,50));
        btnElvEstINT.setMinimumSize(new Dimension(30,50));
        btnElvEstINT.setPreferredSize(new Dimension(30,50));
        btnElvEstINT.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 14;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2,2,2,2);
        buttonsPanel.add(btnElvEstINT, gbc);

        btnPlan = new JButton();
        btnPlan.setIcon(new ImageIcon(getClass().getResource("/buttons/plan.png")));
        btnPlan.setBackground(new Color(-1));
        btnPlan.setMargin(new Insets(0,0,0,0));
        btnPlan.setMaximumSize(new Dimension(30,50));
        btnPlan.setMinimumSize(new Dimension(30,50));
        btnPlan.setPreferredSize(new Dimension(30,50));
        btnPlan.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2,2,2,2);
        buttonsPanel.add(btnPlan, gbc);

    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
