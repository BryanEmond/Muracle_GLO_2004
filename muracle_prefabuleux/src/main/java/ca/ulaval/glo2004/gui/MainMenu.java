package ca.ulaval.glo2004.gui;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MainMenu extends JMenuBar {

    public MainMenu()
    {
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem itemOuvrir = new JMenuItem("Ouvrir");
        itemOuvrir.addActionListener((e) -> OnClickOuvrir());
        JMenuItem itemEnregistrer = new JMenuItem("Enregistrer");
        JMenuItem itemEnregistrerSous = new JMenuItem("Enregistrer sous");
        JMenuItem itemExporter = new JMenuItem("Exporter");
        menuFichier.add(itemOuvrir);
        menuFichier.add(itemEnregistrer);
        menuFichier.add(itemEnregistrerSous);
        menuFichier.add(itemExporter);
        this.add(menuFichier);

        JMenu menuEdition = new JMenu("Edition");
        JMenuItem itemUndo = new JMenuItem("Undo");
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        itemUndo.addActionListener((e) -> OnClickUndo());
        JMenuItem itemRedo = new JMenuItem("Redo");
        itemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        menuEdition.add(itemUndo);
        menuEdition.add(itemRedo);
        this.add(menuEdition);

        JMenu menuAffichage = new JMenu("Affichage");
        JMenuItem itemGrille = new JMenuItem("Afficher grille");
        menuAffichage.add(itemGrille);
        this.add(menuAffichage);
    }

    private void OnClickOuvrir() { System.out.println("Ouvrir"); }

    private void OnClickUndo() { System.out.println("Undo"); }

}
