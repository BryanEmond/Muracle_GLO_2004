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

    private Afficheur afficheurCote;

    public DrawingPanel(MainWindow mainWindow)
    {
        this.mainWindow = mainWindow;
        mainWindow.mainPanel.add(this);

        this.setBorder(new EmptyBorder(50, 10, 10, 10));

        Salle salle = GetTestingSalle();
        afficheur = new AfficheurVueDessus(salle);
    }

    public DrawingPanel(MainWindow mainWindow,Salle salle){
        this.mainWindow = mainWindow;
        mainWindow.mainPanel.add(this);

        this.setBorder(new EmptyBorder(50, 10, 10, 10));

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

        mainWindow.gestionnaireSalle.creerSalle(new Imperial(0), new Imperial(0),
                new Imperial(1), new Imperial(1),
                new Imperial(20),
                new Imperial(20), new Imperial(20),
                true, new ArrayList<>(Arrays.asList(nord, est, sud, ouest)));
        Salle salle = mainWindow.gestionnaireSalle.getSalleActive();

        return salle;
    }

    private Cote GetTestingCote() {
        Cote coteTest = new Cote(new Imperial(96), new Imperial(60), "nord" );



        return coteTest;
    }

}
