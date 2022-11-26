package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Utilitaire;
import ca.ulaval.glo2004.classes.dto.MurDTO;
import ca.ulaval.glo2004.classes.dto.SalleDTO;
import ca.ulaval.glo2004.classes.dto.SeparateurDTO;
import ca.ulaval.glo2004.gestion.Conversion;
import ca.ulaval.glo2004.gestion.GestionnaireSalle;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;
import java.util.Objects;

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

    private PanelProprietes proprietesSalle;
    private PanelProprietes proprietesMur;
    private PanelProprietes proprietesAccessoire;
    private PanelProprietes proprietesSeparateur;

    private MainWindow mainWindow;
    private DrawingPanel panel;
    GestionnaireSalle gestionnaireSalle;
    private String filePath;
    Utilitaire.AccessoireEnum AccessoireEnum;
    Utilitaire.Direction direction;
    private boolean interieur;
    private boolean plan;
    public MainWindow(GestionnaireSalle gestionnaireSalle) {
        this.interieur = false;
        this.plan = true;
        this.gestionnaireSalle = gestionnaireSalle;
        mainWindow = this;
        direction = null;
        interieur = false;
        panel = new DrawingPanel(this);
        creerUnNouveauProjetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.creerSalleDefaut();
                mainWindow = new MainWindow(gestionnaireSalle);
                JFileChooser fc = new JFileChooser();
                fc.setSelectedFile(new File("sale.ser"));
                int returnFcVal = fc.showSaveDialog(rootPanel.getParent());
                if(returnFcVal == JFileChooser.APPROVE_OPTION){
                    try{
                        File file = fc.getSelectedFile();
                        setHomePage(e);
                        mainWindow.gestionnaireSalle.enregistrerSalle(file.getPath());
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
                JFileChooser fc = new JFileChooser("d:");
                int returnFcVal = fc.showOpenDialog(rootPanel.getParent());
                if(returnFcVal == JFileChooser.APPROVE_OPTION){
                    try{
                        File file = fc.getSelectedFile();
                        setHomePage(e);
                        mainWindow.gestionnaireSalle.chargerSalle(file.getPath());
                    }catch (Exception error){
                        System.out.println(error);
                    }
                }
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
               gestionnaireSalle.ChangementDeVueVersCote();
               direction = Utilitaire.Direction.EST;
               interieur = true;
                resetButtonView();
                btnElvEstINT.setBorder(BorderFactory.createLineBorder(Color.blue));
               panel.setAfficheur(new AfficheurElevationCote(mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.EST), false));
            }
        });

        btnElvEstEXT.addMouseListener(new MouseAdapter() {
            //TODO enlever : retour d'air et prise de courant des accessoires
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.ChangementDeVueVersCote();
                direction = Utilitaire.Direction.EST;
                interieur = false;
                resetButtonView();
                btnElvEstEXT.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setAfficheur(new AfficheurElevationCote(mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.EST), true));
            }
        });

        btnELVSudINT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.ChangementDeVueVersCote();
                direction = Utilitaire.Direction.SUD;
                interieur = true;
                resetButtonView();
                btnELVSudINT.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setAfficheur(new AfficheurElevationCote(mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.SUD), false));
            }
        });
        btnElvSudEXT.addMouseListener(new MouseAdapter() {
            //TODO enlever : retour d'air et prise de courant des accessoires
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.ChangementDeVueVersCote();
                direction = Utilitaire.Direction.SUD;
                interieur = false;
                resetButtonView();
                btnElvSudEXT.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setAfficheur(new AfficheurElevationCote(mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.SUD), true));

            }
        });
        btnElvOuestINT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.ChangementDeVueVersCote();
                direction = Utilitaire.Direction.OUEST;
                interieur = true;
                resetButtonView();
                btnElvOuestINT.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setAfficheur(new AfficheurElevationCote(mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.OUEST), false));

            }
        });
        btnElvOuestEXT.addMouseListener(new MouseAdapter() {
            //TODO enlever : retour d'air et prise de courant des accessoires
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.ChangementDeVueVersCote();
                direction = Utilitaire.Direction.OUEST;
                interieur = false;
                resetButtonView();
                btnElvOuestEXT.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setAfficheur(new AfficheurElevationCote(mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.OUEST), true));
            }
        });

        btnElvNordINT.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.ChangementDeVueVersCote();
                direction = Utilitaire.Direction.NORD;
                interieur = true;
                resetButtonView();
                btnElvNordINT.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setAfficheur(new AfficheurElevationCote(mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.NORD), false));
            }
        });

        btnElvNordEXT.addMouseListener(new MouseAdapter() {
            //TODO enlever : retour d'air et prise de courant des accessoires
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.ChangementDeVueVersCote();
                direction = Utilitaire.Direction.NORD;
                interieur = false;
                resetButtonView();
                btnElvNordEXT.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setAfficheur(new AfficheurElevationCote(mainWindow.gestionnaireSalle.getSalleActive().getCote(Utilitaire.Direction.NORD), true));
            }
        });

        btnPlan.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gestionnaireSalle.ChangementDeVueVersPlan();
                direction = null;
                interieur = false;
                resetButtonView();
                btnPlan.setBorder(BorderFactory.createLineBorder(Color.blue));
                panel.setAfficheur( new AfficheurVueDessus(gestionnaireSalle.getSalleActive()));

            }
        });

        btnPorte.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetButtonAccessoires();
                btnPorte.setBorder(BorderFactory.createLineBorder(Color.red));
                AccessoireEnum = Utilitaire.AccessoireEnum.Porte;
            }
        });

        btnPrise.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetButtonAccessoires();
                btnPrise.setBorder(BorderFactory.createLineBorder(Color.red));
                AccessoireEnum = Utilitaire.AccessoireEnum.PriseElectrique;
            }
        });

        btnRetourAir.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetButtonAccessoires();
                btnRetourAir.setBorder(BorderFactory.createLineBorder(Color.red));
                AccessoireEnum = Utilitaire.AccessoireEnum.RetourAir;
            }
        });

        btnFenetre.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetButtonAccessoires();
                btnFenetre.setBorder(BorderFactory.createLineBorder(Color.red));
                AccessoireEnum = Utilitaire.AccessoireEnum.Fenetre;
            }
        });

        btnSupprimer.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                resetButtonAccessoires();
                btnSupprimer.setBorder(BorderFactory.createLineBorder(Color.red));
                AccessoireEnum = Utilitaire.AccessoireEnum.Supprimer;
            }
        });
        this.mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getClickCount() == 2 && gestionnaireSalle.GetvuePlan()) {
                    gestionnaireSalle.onClickEvents(e.getX(), e.getY(),Utilitaire.AccessoireEnum.Separateur,false,null);
                }
                else if(interieur){
                    gestionnaireSalle.onClickEvents(e.getX(), e.getY(),Utilitaire.AccessoireEnum.Separateur,interieur,direction);
                }else {
                    gestionnaireSalle.onClickEvents(e.getX(), e.getY(), Utilitaire.AccessoireEnum.Separateur, interieur, direction);
                }
                if (direction != null && e.getClickCount() == 2) {
                    switch (AccessoireEnum){
                        case Fenetre:
                            gestionnaireSalle.AjouterFenetre(e.getX(), e.getY(),direction,interieur);
                            break;
                        case RetourAir:
                            gestionnaireSalle.AjouterRetourAir(e.getX(), e.getY(),direction,interieur);
                            break;
                        case Supprimer:
                            gestionnaireSalle.Supprimer(e.getX(), e.getY(),direction,interieur);
                            break;
                        case Porte:
                            gestionnaireSalle.AjouterPorte(e.getX(), e.getY(),direction,interieur);
                            break;
                        case PriseElectrique:
                            gestionnaireSalle.AjouterPriseElectrique(e.getX(), e.getY(),direction,interieur);
                            break;
                        case Separateur:
                            break;
                        default:
                            gestionnaireSalle.selectionnerElement(e.getX(), e.getY(),direction,interieur);
                    }
                }
                mainPanel.validate();
                mainPanel.repaint();
            }
        });

        MouseAdapter mouvementCameraAdapter =new MouseAdapter() {

            Point lastPoint = null;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                if(e.getButton() == MouseEvent.BUTTON2)
                {
                    lastPoint = e.getPoint();
                }

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if(e.getButton() == MouseEvent.BUTTON2)
                {
                    lastPoint = null;
                }
            }


            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                if(lastPoint != null)
                {
                    Point point = e.getPoint();
                    int offsetX = point.x - lastPoint.x;
                    int offsetY = point.y - lastPoint.y;
                    lastPoint = point;

                    Conversion.getConversion().pan(-offsetX, -offsetY);
                    mainPanel.validate();
                    mainPanel.repaint();
                }
            }
        };

        this.mainPanel.addMouseListener(mouvementCameraAdapter);
        this.mainPanel.addMouseMotionListener(mouvementCameraAdapter);

        SalleDTO salleSelect = gestionnaireSalle.getSalleSelectionne();
        if(salleSelect != null)
        {
            proprietesSalle.setValue("largeur", salleSelect.getLargeur().toString());
            proprietesSalle.setValue("hauteur", salleSelect.getHauteur().toString());
            proprietesSalle.setValue("profondeur", salleSelect.getProfondeur().toString());
            proprietesSalle.setValue("epaisseurMur", salleSelect.getEpaisseurMurs().toString());
            proprietesSalle.setValue("largeurPli", salleSelect.getLargeurPli().toString());
            proprietesSalle.setValue("pliSoudure", salleSelect.getAnglePliSoudure() + "");
            proprietesSalle.updateValues();
        }

        MurDTO murSelect = gestionnaireSalle.getMurSelectionne();
        if(murSelect != null)
        {
            proprietesMur.setValue("x", murSelect.getX().toString());
            proprietesMur.setValue("y", murSelect.getY().toString());
            proprietesMur.setValue("largeur", murSelect.getLargeur().toString());
            proprietesMur.updateValues();
        }

        SeparateurDTO sepSelect = gestionnaireSalle.getSeparateurSelectionne();
        if(sepSelect != null)
        {
            proprietesSeparateur.setValue("pos", sepSelect.getPosition().toString());
            proprietesSeparateur.setValue("posRel", sepSelect.getPositionRelative().toString());
            proprietesSeparateur.updateValues();
        }
    }
    public void resetButtonView(){
        btnElvEstINT.setBorder(null);
        btnElvEstEXT.setBorder(null);
        btnElvNordEXT.setBorder(null);
        btnElvNordINT.setBorder(null);
        btnElvOuestEXT.setBorder(null);
        btnElvOuestINT.setBorder(null);
        btnElvSudEXT.setBorder(null);
        btnELVSudINT.setBorder(null);
        btnPlan.setBorder(null);
    }
    public void resetButtonAccessoires(){
        btnSupprimer.setBorder(null);
        btnRetourAir.setBorder(null);
        btnPrise.setBorder(null);
        btnPorte.setBorder(null);
        btnFenetre.setBorder(null);
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
        propertiesPanel.setLayout(new BoxLayout(propertiesPanel, BoxLayout.PAGE_AXIS));
        propertiesPanel.setBackground(new Color(-8882056));
        propertiesPanel.setEnabled(true);
        propertiesPanel.setMinimumSize(new Dimension(235, 0));
        propertiesPanel.setPreferredSize(new Dimension(235, 700));

        JScrollPane propertiesScroll = new JScrollPane(propertiesPanel);
        propertiesScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        propertiesScroll.setPreferredSize(new Dimension(235, 0));
        rootPanel.add(propertiesScroll, BorderLayout.WEST);

        proprietesSalle = new PanelProprietes("DIMENSIONS DE LA SALLE", 0);
        proprietesSalle.addProperty("largeur", "LARGEUR :");
        proprietesSalle.addProperty("profondeur", "PROFONDEUR :");
        proprietesSalle.addProperty("hauteur", "HAUTEUR :");
        proprietesSalle.addProperty("epaisseurMur", "ÉPAISSEUR MURS :");
        proprietesSalle.addProperty("largeurPli", "LARGEUR DE PLI :");
        proprietesSalle.addProperty("pliSoudure", "PLI DE SOUDURE :");
        proprietesSalle.generateLayout();
        propertiesPanel.add(proprietesSalle);

        proprietesSalle.setOnChangeListener(values -> {
            Imperial largeur = proprietesSalle.getImperial("largeur");
            Imperial profondeur = proprietesSalle.getImperial("profondeur");
            Imperial hauteur = proprietesSalle.getImperial("hauteur");
            Imperial epaisseurMur = proprietesSalle.getImperial("epaisseurMur");
            Imperial largeurPli = proprietesSalle.getImperial("largeurPli");
            int pliSoudure = proprietesSalle.getInt("pliSoudure");

            if(largeur == null || profondeur == null || hauteur == null || epaisseurMur == null || largeurPli == null || pliSoudure == -1)
                return;

            int result = gestionnaireSalle.editSalleSelectionne(new SalleDTO(largeur, profondeur, hauteur, epaisseurMur, largeurPli, pliSoudure));

            if(result == 0)
            {
                mainPanel.validate();
                mainPanel.repaint();
            }

            proprietesSalle.setError("largeur", result == 1);
            proprietesSalle.setError("profondeur", result == 2);
        });

        proprietesMur = new PanelProprietes("DIMENSIONS DU MUR", 0);
        proprietesMur.addProperty("x", "POSITION X :", "", true);
        proprietesMur.addProperty("y", "POSITION Y :", "", true);
        proprietesMur.addProperty("largeur", "LARGEUR :", "", true);
        proprietesMur.generateLayout();
        propertiesPanel.add(proprietesMur);

        proprietesSeparateur = new PanelProprietes("SÉPARATEUR", 100);
        proprietesSeparateur.addProperty("pos", "POSITION :", "", true);
        proprietesSeparateur.addProperty("posRel", "SEP. PRÉCÉDENT :");
        proprietesSeparateur.generateLayout();
        propertiesPanel.add(proprietesSeparateur);

        proprietesSeparateur.setOnChangeListener(values -> {

            Imperial posRel = proprietesSeparateur.getImperial("posRel");

            if(posRel != null && gestionnaireSalle.editSeparateurSelectionne(posRel))
            {
                proprietesSeparateur.setError("posRel", false);
                SeparateurDTO newValue = gestionnaireSalle.getSeparateurSelectionne();
                proprietesSeparateur.setValue("pos", newValue.getPosition().toString());

                mainPanel.validate();
                mainPanel.repaint();
            }
            else
                proprietesSeparateur.setError("posRel", true);

        });

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
        btnPlan.setBorder(BorderFactory.createLineBorder(Color.blue));
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
