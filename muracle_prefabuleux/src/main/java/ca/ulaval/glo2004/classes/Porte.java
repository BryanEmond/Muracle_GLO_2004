package ca.ulaval.glo2004.classes;
//import Accessoire as parent

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Porte extends Accessoire implements Serializable {
    Boolean perceInterieur = true;
    Boolean perceExterieur = false;




    public Porte(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur);
    this.mLargeur = mLargeur;
    this.mHauteur = mHauteur;
    this.perceInterieur = mPerceInterieur;
    this.perceExterieur = mPerceExtérieur;
    }
    public Porte clone()
    {
        return new Porte(this.mY,this.mX,this.mPerceExtérieur,this.mPerceInterieur,this.mLargeur,this.mHauteur,"nom");
    }


    @Override
    public ArrayList<Polygone> genererPolygoneELV() {
        //TODO largeur et hauteur configurable
        Imperial x1 = new Imperial(mX.entier, mX.numerateur, mX.denominateur);
        Imperial y1 = new Imperial(cote.getmSalle().hauteur.entier, cote.getmSalle().hauteur.numerateur, cote.getmSalle().hauteur.denominateur);
        Imperial x2;
        Imperial y2;

        x2 = x1.add(mLargeur);
        y2 = y1.add(mHauteur.negative());

        ArrayList<Polygone> portes = new ArrayList<>();
        portes.add(this.mPolygoneElevation = new Polygone(Color.DARK_GRAY, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1)));

        super.mPolygoneElevation = portes.get(0);
        return portes;
    }}


