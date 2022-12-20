package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Salle;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MainMenu extends JMenuBar {
    private MainWindow mainWindow;
    public MainMenu(MainWindow mw)
    {
        this.mainWindow = mw;
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem itemOuvrir = new JMenuItem("Ouvrir");
        JMenuItem itemEnregistrer = new JMenuItem("Enregistrer");
        JMenuItem itemEnregistrerSous = new JMenuItem("Enregistrer sous");
        JMenuItem itemExporter = new JMenuItem("Exporter");
        menuFichier.add(itemOuvrir);
        menuFichier.add(itemEnregistrer);
        menuFichier.add(itemEnregistrerSous);
//        menuFichier.add(itemExporter);
        this.add(menuFichier);

        JMenu menuEdition = new JMenu("Edition");
        JMenuItem itemUndo = new JMenuItem("Undo");
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        JMenuItem itemRedo = new JMenuItem("Redo");
        itemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        menuEdition.add(itemUndo);
        menuEdition.add(itemRedo);
        this.add(menuEdition);

        itemOuvrir.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainWindow = new MainWindow(mainWindow.gestionnaireSalle);
                JFileChooser fc = new JFileChooser("c:/Documents/");
                int returnFcVal = fc.showOpenDialog(mainWindow.rootPanel.getParent());
                if (returnFcVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = fc.getSelectedFile();
//                        mainWindow.setHomePage(e);
                        mainWindow.gestionnaireSalle.chargerSalle(file.getPath());
                        Salle salle = mainWindow.gestionnaireSalle.getSalleActive();
                        mainWindow.panel.setAfficheur(new AfficheurVueDessus(salle));
                        mainWindow.updatePanels();
//                        mainWindow.setHomePage(e);
                    } catch (Exception error) {
                        System.out.println(error);
                    }
                }
            }
        });

        itemEnregistrer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        });

        itemEnregistrerSous.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        });

        itemUndo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        itemRedo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
        });

        JMenu menuAffichage = new JMenu("Affichage");
        JMenuItem itemGrille = new JMenuItem("Afficher grille");
        menuAffichage.add(itemGrille);
        this.add(menuAffichage);

        itemGrille.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainWindow.activateGridPlacement();
            }
        });
    }

    private void OnClickOuvrir() { System.out.println("Ouvrir"); }

    private void OnClickUndo() { System.out.println("Undo"); }

}
