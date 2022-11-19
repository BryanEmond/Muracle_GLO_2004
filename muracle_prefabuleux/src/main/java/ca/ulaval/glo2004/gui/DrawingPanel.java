package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.*;

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

    public DrawingPanel(MainWindow mainWindow, Cote cote){
        this.mainWindow = mainWindow;
        mainWindow.mainPanel.add(this);

        this.setBorder(new EmptyBorder(50, 10, 10,10));

        afficheur = new AfficheurElevationCote(cote);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        afficheur.affiche(g);
    }



    private Salle GetTestingSalle()
    {
        Cote nord = new Cote(new Imperial(0),
                            new Imperial(0),new Imperial(0),new Polygone(Color.BLACK,new ArrayList<PointImperial>()),
                            "NORD",new Polygone(Color.BLACK,new ArrayList<PointImperial>()));

        Cote est = new Cote(new Imperial(0),
                new Imperial(1),
                new Imperial(0),new Polygone(Color.BLACK,new ArrayList<PointImperial>()),
            "EST",new Polygone(Color.BLACK,new ArrayList<PointImperial>()));

        Cote sud = new Cote(new Imperial(20),
                new Imperial(0),
                new Imperial(0),new Polygone(Color.BLACK,new ArrayList<PointImperial>()),
                "SUD",new Polygone(Color.BLACK,new ArrayList<PointImperial>()));

        Cote ouest = new Cote(new Imperial(20),
                new Imperial(1),
                new Imperial(0),new Polygone(Color.BLACK,new ArrayList<PointImperial>()),
                "OUEST",new Polygone(Color.BLACK,new ArrayList<PointImperial>()));

        mainWindow.gestionnaireSalle.creerSalle(new Imperial(0), new Imperial(0),
                new Imperial(1), new Imperial(1),
                new Imperial(20),
                new Imperial(20), new Imperial(20),
                true, new ArrayList<>(Arrays.asList(nord, est, sud, ouest)));
        Salle salle = mainWindow.gestionnaireSalle.getSalleActive();

        return salle;
    }


    private Cote GetTestingCote() {
        Cote coteTest = new Cote(new Imperial(96), new Imperial(60), new Imperial(0),new Polygone(Color.BLACK,new ArrayList<PointImperial>()),
                "NORD",new Polygone(Color.BLACK,new ArrayList<PointImperial>()) );



        return coteTest;
    }

}
