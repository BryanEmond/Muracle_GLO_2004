package ca.ulaval.glo2004.classes;
//import Acessoire as a parent

// toujours centré sur le panneau, largeur configurable, seulement sur les panneau
//intérieur

import ca.ulaval.glo2004.gestion.GestionnaireSalle;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class RetourAir extends Accessoire implements Serializable {

        boolean perceInterieur = true;
        boolean perceExterieur = false;
        Imperial largeur = new Imperial(12, 0 , 0);
        Imperial hauteur = new Imperial(2,0,0);


    public RetourAir(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur,
                     Imperial mHauteur, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mNom);
    this.perceExterieur = mPerceExtérieur;
    this.perceInterieur = mPerceInterieur;
    this.largeur = mLargeur;
    this.hauteur = mHauteur;
    }

    public ArrayList<Polygone> genererPolygoneELV() {
        //TODO toujours centré sur le mur,
        // largeur configurable,
        // seulement sur les panneau interieur (on le vois pas en vue extérieur)
        // ont tous la même hauteur et même distance avec le sol
        // crée aussi un trou en haut du panneau qui sera visible de la vue PLAN


        Imperial x1 = super.mX;
        Imperial y1 = super.mY;
        Imperial x2;
        Imperial y2;


        x2 = x1.add(largeur);
        y2 = y1.add(hauteur);

        Polygone retourAir = this.mPolygoneElevation = new Polygone(Color.PINK, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));

        return null;
    }
}
