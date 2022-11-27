package ca.ulaval.glo2004.classes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

//import Accessoire as parent
public class Fenetre extends Accessoire implements Serializable {

    Boolean perceInterieur = true;
    Boolean perceExterieur = true;
    Imperial largeur = new Imperial(24, 0, 0);
    Imperial hauteur = new Imperial( 24, 0, 0);

    String mNom = "Fenetre";

    ArrayList<Polygone> PolygoneELV;

    public Fenetre(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur,
                   Imperial mHauteur, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mNom);
    this.perceExterieur = mPerceExtérieur;
    this.perceInterieur = mPerceInterieur;
    this.hauteur = mHauteur;
    this.largeur = mLargeur;
    this.mNom = "Fenetre";
    }

    @Override
    public ArrayList<Polygone> genererPolygoneELV() {
       //TODO CADRE DE BASE 1/8 CONFIGURABLE
        Imperial x1 = super.mX;
        Imperial y1 = super.mY;
        Imperial x2;
        Imperial y2;

        x2 = x1.add(largeur);
        y2 = y1.add(hauteur);

        Polygone fenetre = this.mPolygoneElevation = new Polygone(Color.PINK, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));

        Imperial x3 = x1.substract(new Imperial(0, 1, 8));
        Imperial y3 = y1.add(new Imperial(0,1,8));
        Imperial x4 = x2.add(new Imperial(0,1,8));
        Imperial y4 = y2.substract(new Imperial(0,1,8));

        Polygone cadre = this.mPolygoneElevation = new Polygone(Color.PINK, new PointImperial(x3, y3), new PointImperial(x3, y4), new PointImperial(x4, y4), new PointImperial(x4, y3));

        ArrayList<Polygone> fenetres = new ArrayList<>();
        fenetres.add(fenetre);
        fenetres.add(cadre);

        PolygoneELV = fenetres;
        return fenetres;
    }


}

