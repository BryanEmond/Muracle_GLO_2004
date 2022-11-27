package ca.ulaval.glo2004.classes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

//import Accessoire as parent
public class Fenetre extends Accessoire implements Serializable {

    Boolean perceInterieur = true;
    Boolean perceExterieur = true;
    Imperial bordure;

    ArrayList<Polygone> PolygoneELV;

    Polygone mPolygoneElevation;

    public Fenetre(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur,
                   Imperial mHauteur) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur);
        this.perceExterieur = mPerceExtérieur;
        this.perceInterieur = mPerceInterieur;
        this.mHauteur = mHauteur;
        this.mLargeur = mLargeur;
        bordure = new Imperial(0, 1, 8);
    }

    @Override
    public ArrayList<Polygone> genererPolygoneELV() {
       //TODO CADRE DE BASE 1/8 CONFIGURABLE
        Imperial x1 = mX;
        Imperial y1 = mY;
        Imperial x2;
        Imperial y2;

        x2 = x1.add(mLargeur);
        y2 = y1.add(mHauteur);

        //Polygone fenetre = this.mPolygoneElevation = new Polygone(Color.PINK, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));
        Polygone fenetre = new Polygone(Color.PINK, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));

        Imperial x3 = x1.substract(bordure);
        Imperial y3 = y1.add(bordure);
        Imperial x4 = x2.add(bordure);
        Imperial y4 = y2.substract(bordure);

       // Polygone cadre = this.mPolygoneElevation = new Polygone(Color.PINK, new PointImperial(x3, y3), new PointImperial(x3, y4), new PointImperial(x4, y4), new PointImperial(x4, y3));
        Polygone cadre = new Polygone(Color.DARK_GRAY, new PointImperial(x3, y3), new PointImperial(x3, y4), new PointImperial(x4, y4), new PointImperial(x4, y3));

        ArrayList<Polygone> fenetres = new ArrayList<>();
        fenetres.add(fenetre);
        fenetres.add(cadre);

        PolygoneELV = fenetres;
        super.mPolygoneElevation = cadre;
        return fenetres;
    }

    @Override
    public void setmPolygoneElevation(Polygone mPolygoneElevation) {
        this.mPolygoneElevation = mPolygoneElevation;
    }

    public Imperial getBordure() {
        return bordure;
    }

    public void setBordure(Imperial bordure) {
        this.bordure = bordure;
    }
}

