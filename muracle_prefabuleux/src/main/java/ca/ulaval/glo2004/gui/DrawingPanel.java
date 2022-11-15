package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Cote;
import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Mur;
import ca.ulaval.glo2004.classes.Salle;

import javax.swing.*;
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

        Mur mn2 = new Mur(salle, nord, new Imperial(10), new Imperial(0), new Imperial(10),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));

        ArrayList<Mur> mursNord = new ArrayList<>();
        mursNord.add(mn1);
        mursNord.add(mn2);
        nord.setMurs(mursNord);

        return salle;
    }
}
