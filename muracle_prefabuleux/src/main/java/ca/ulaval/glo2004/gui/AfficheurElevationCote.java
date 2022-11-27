
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.*;

import java.awt.*;
import java.util.ArrayList;


public class AfficheurElevationCote extends Afficheur{
    private Cote cote;
    private boolean exterieur;
    public AfficheurElevationCote(Cote cote, boolean exterieur) {

        this.exterieur = exterieur;
        this.cote = cote;}



    @Override
    public void affiche(Graphics g) {
        setOffset(10,10);

        ArrayList<Polygone> polygones = cote.getPolygoneElevation(exterieur);
        for (int i = 0; i < cote.getAccessoires().size(); i++){
            if (!exterieur && cote.getAccessoires().get(i).getmNom().equals("Prise"))
            {
                //TODO créer generer polygone accessoire
                //TODO créer getmPolygone accessoire
                //polygones.add(cote.getAccessoires().get(i).)
            }
        }
        //TODO generer polygone accessoire
        dessinerPolygones(g, polygones);
    }
}