package ca.ulaval.glo2004.classes;

//import Accessoire as parent
//s'affiche uniquement a l'intérieur de la salle

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class PrisesElectrique extends Accessoire implements Serializable {

    Imperial largeur = new Imperial(2,0,0);
    Imperial hauteur = new Imperial(4,0,0);

    Boolean perceInterieur = true;
    Boolean perceExterieur = false;


    public PrisesElectrique(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mNom);
    this.hauteur = mHauteur;
    this.largeur = mLargeur;
    this.perceExterieur = mPerceExtérieur;
    this.perceInterieur = mPerceInterieur;
    }


    @Override
    public ArrayList<Polygone> genererPolygoneELV() {
        //TODO ajuster les point x1 et y1
        Imperial x1 = super.mX;
        Imperial y1 = super.mY;
        Imperial x2;
        Imperial y2;

        x2 = x1.add(largeur);
        y2 = y1.add(hauteur);

        Polygone prise = this.mPolygoneElevation = new Polygone(Color.PINK, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));
        ArrayList<Polygone> prises = new ArrayList<>();
        prises.add(prise);

        return prises;
    }
}

