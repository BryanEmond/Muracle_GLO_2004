
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
            //if (!exterieur)
            //{
                polygones.addAll(cote.getAccessoires().get(i).genererPolygoneELV());
            //}
        //TODO penser a gérer l'affichage de prise et de retourd'air ne doivent pas s'afficher dehors
        }

        dessinerPolygones(g, polygones);
    }
}