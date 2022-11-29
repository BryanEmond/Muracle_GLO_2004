
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.*;

import java.awt.*;
import java.util.ArrayList;


public class AfficheurElevationCote extends Afficheur{

    private Salle salle;
    private Utilitaire.Direction direction;
    private boolean exterieur;

    public AfficheurElevationCote(Salle salle, Utilitaire.Direction direction, boolean exterieur) {
        this.salle = salle;
        this.direction = direction;
        this.exterieur = exterieur;
    }

    @Override
    public void affiche(Graphics g) {
        Cote cote = salle.getCote(direction);
        ArrayList<Polygone> polygones = cote.getPolygoneElevation(exterieur);
        for (int i = 0; i < cote.getAccessoires().size(); i++){
            if (exterieur && cote.getAccessoires().get(i).ismPerceExtérieur())
            {
                polygones.addAll(cote.getAccessoires().get(i).genererPolygoneELV(exterieur));
            }
            if (!exterieur)
            {
                polygones.addAll((cote.getAccessoires().get(i).genererPolygoneELV(exterieur)));
            }
        //TODO penser a gérer l'affichage de prise et de retourd'air ne doivent pas s'afficher dehors
        }

        dessinerPolygones(g, polygones);
    }
}