
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Cote;
import ca.ulaval.glo2004.classes.Polygone;

import java.awt.*;
import java.util.ArrayList;


public class AfficheurElevationCote extends Afficheur{
    private Cote cote;

    public AfficheurElevationCote(Cote cote) {this.cote = cote;}

    @Override
    public void affiche(Graphics g) {
        setOffset(10,10);

        ArrayList<Polygone> polygones = cote.getPolygoneElevation();
        dessinerPolygones(g, polygones);

    }

}