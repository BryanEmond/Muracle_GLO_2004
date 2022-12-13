package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.*;
import ca.ulaval.glo2004.classes.dto.MurDTO;

import java.awt.*;
import java.util.ArrayList;

public class AfficheurVueDecoupage extends Afficheur{

    private Salle salle;
    private Mur mur;

    public AfficheurVueDecoupage(Salle salle, Mur mur) {
        this.salle = salle;
        this.mur = mur;
    }

    @Override
    public void affiche(Graphics g) {
        ArrayList<ArrayList> murPolygone = this.mur.genererpolygonesElevationDecoupage();
        for(ArrayList<Polygone> list : murPolygone){
            dessinerPolygones(g, list);
        }
    }
}
