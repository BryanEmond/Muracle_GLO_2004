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

    String nom = "Porte";


    public Porte(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mNom);
    this.largeur = mLargeur;
    this.hauteur = mHauteur;
    this.perceInterieur = mPerceInterieur;
    this.perceExterieur = mPerceExtérieur;
    this.nom = mNom;
    }

    @Override
    public ArrayList<Polygone> genererPolygoneELV() {
        //TODO CHANGER LE X PAR POSITION DU CLICK ET LE Y DOIT ETRE A 0 (TOUJOURS AU SOL)
        Imperial x1 = super.mX;
        Imperial y1;
        Imperial x2;
        Imperial y2 = new Imperial(0);

        x2 = x1.add(largeur);
        y1 = y2.add(hauteur);

        ArrayList<Polygone> portes = new ArrayList<>();
        portes.add(this.mPolygoneElevation = new Polygone(Color.PINK, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1)));

        return portes;
    }
}
