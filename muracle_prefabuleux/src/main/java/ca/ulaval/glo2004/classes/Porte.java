package ca.ulaval.glo2004.classes;
//import Accessoire as parent

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Porte extends Accessoire implements Serializable {

    Imperial largeur = new Imperial(38,0,0);
    Imperial hauteur = new Imperial(88,0,0);
    Boolean perceInterieur = true;
    Boolean perceExterieur = false;




    public Porte(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur);
    this.largeur = mLargeur;
    this.hauteur = mHauteur;
    this.perceInterieur = mPerceInterieur;
    this.perceExterieur = mPerceExtérieur;
    }

    @Override
    public ArrayList<Polygone> genererPolygoneELV() {
        //TODO largeur et hauteur configurable
        Imperial x1 = new Imperial(super.mX.entier, super.mX.numerateur, super.mX.denominateur);
        Imperial y1 = new Imperial(cote.getmSalle().hauteur.entier, cote.getmSalle().hauteur.numerateur, cote.getmSalle().hauteur.denominateur);
        Imperial x2;
        Imperial y2;

        x2 = x1.add(largeur);
        y2 = y1.add(hauteur.negative());

        ArrayList<Polygone> portes = new ArrayList<>();
        portes.add(this.mPolygoneElevation = new Polygone(Color.PINK, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1)));

        return portes;
    }}
