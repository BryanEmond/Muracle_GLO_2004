
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Cote;
import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Mur;
import ca.ulaval.glo2004.classes.Polygone;

import java.awt.*;
import java.util.ArrayList;


public class AfficheurElevationCote extends Afficheur{
    private Cote cote;
    private boolean bool;
    public AfficheurElevationCote(Cote cote, boolean exterieur) {

        this.bool = exterieur;
        this.cote = cote;}


    @Override
    public void affiche(Graphics g) {

    }

    @Override
    public void affiche(Graphics g, boolean exterieur) {
        setOffset(10,10);

        ArrayList<Polygone> polygones = cote.getPolygoneElevation(exterieur);


        dessinerPolygones(g, polygones);

    }

}