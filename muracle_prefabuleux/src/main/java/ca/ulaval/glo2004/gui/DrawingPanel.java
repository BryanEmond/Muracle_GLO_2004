package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Cote;
import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Mur;
import ca.ulaval.glo2004.classes.Salle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawingPanel extends JPanel
{

    private MainWindow mainWindow;
    private Afficheur afficheur;

    public DrawingPanel(MainWindow mainWindow)
    {
        this.mainWindow = mainWindow;
        mainWindow.mainPanel.add(this);

        this.setBorder(new EmptyBorder(50, 10, 10, 10));

        Salle salle = GetTestingSalle();
        afficheur = new AfficheurVueDessus(salle);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        afficheur.affiche(g);
    }

    private Salle GetTestingSalle()
    {
        Cote nord = new Cote(new Imperial(0),
                            new Imperial(0),
                            "NORD");

        Cote est = new Cote(new Imperial(0),
                new Imperial(1),
                "EST");

        Cote sud = new Cote(new Imperial(20),
                new Imperial(0),
                "SUD");

        Cote ouest = new Cote(new Imperial(20),
                new Imperial(1),
                "OUEST");

        Salle salle = new Salle(new Imperial(0), new Imperial(0),
                new Imperial(1), new Imperial(1),
                new Imperial(20),
                new Imperial(20), new Imperial(20),
                true, new ArrayList<>(Arrays.asList(nord, est, sud, ouest)));

        Mur mn1 = new Mur(salle, nord, new Imperial(0), new Imperial(0), new Imperial(10),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));

        Mur mn2 = new Mur(salle, nord, new Imperial(0), new Imperial(10), new Imperial(10),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));

        nord.setMurs(new ArrayList<>(Arrays.asList(mn1, mn2)));

        Mur ms1 = new Mur(salle, sud, new Imperial(19), new Imperial(0), new Imperial(20),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));
        sud.setMurs(new ArrayList<>(Arrays.asList(ms1)));

        Mur me1 = new Mur(salle, est, new Imperial(1), new Imperial(0), new Imperial(18),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));
        est.setMurs(new ArrayList<>(Arrays.asList(me1)));

        Mur mo1 = new Mur(salle, ouest, new Imperial(1), new Imperial(19), new Imperial(18),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));
        ouest.setMurs(new ArrayList<>(Arrays.asList(mo1)));

        return salle;
    }
}
