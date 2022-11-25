package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Polygone;
import ca.ulaval.glo2004.classes.Salle;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AfficheurVueDessus extends Afficheur {

    private Salle salle;

    public AfficheurVueDessus(Salle salle) {
        this.salle = salle;
    }

    @Override
    public void affiche(Graphics g, boolean exterieur) {

    }

    @Override
    public void affiche(Graphics g) {
        setOffset(10, 10);

        ArrayList<Polygone> polygones = salle.getPolygonesPlan();
        dessinerPolygones(g, polygones);
    }
}
