package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawingPanel extends JPanel {

    private MainWindow mainWindow;
    private Afficheur afficheur;

    private Afficheur afficheurCote;
    private Afficheur afficheurGridPlacement;

    public DrawingPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        mainWindow.mainPanel.add(this);

        this.setBorder(new EmptyBorder(50, 10, 10, 10));
        afficheur = new AfficheurVueDessus(mainWindow.gestionnaireSalle.getSalleActive());
        afficheurGridPlacement = new AfficheurGridPlacement(false,mainWindow);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        afficheurGridPlacement.affiche(g);
        afficheur.affiche(g);
    }


    private Cote GetTestingCote() {
        Cote coteTest = new Cote(new Imperial(96), new Imperial(60), new Imperial(0),
                Utilitaire.Direction.NORD);
        return coteTest;
    }

    public void setAfficheur(Afficheur afficheur) {

        this.afficheur = afficheur;
        mainWindow.mainPanel.validate();
        mainWindow.mainPanel.repaint();
    }
    public void setAfficheurGridPlacement(Afficheur afficheurGridPlacement) {

        this.afficheurGridPlacement = afficheurGridPlacement;
        mainWindow.mainPanel.validate();
        mainWindow.mainPanel.repaint();
    }
}