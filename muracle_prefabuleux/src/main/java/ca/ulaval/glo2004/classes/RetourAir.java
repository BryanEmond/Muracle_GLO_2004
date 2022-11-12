package ca.ulaval.glo2004.classes;
//import Acessoire as a parent

// toujours centré sur le panneau, largeur configurable, seulement sur les panneau
//intérieur

import com.sun.org.apache.xpath.internal.operations.Bool;

public class RetourAir extends Accessoire{

        Boolean perceInterieur = true;
        Boolean perceExterieur = false;
        Imperial largeur = new Imperial(12, 0 , 0);
        Imperial hauteur = new Imperial(2,0,0);


    public RetourAir(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur,
                     Imperial mHauteur, Polygone mPolygonePlan, Polygone mPolygoneElevation, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mPolygonePlan, mPolygoneElevation, mNom);
    this.perceExterieur = mPerceExtérieur;
    this.perceInterieur = mPerceInterieur;
    this.largeur = mLargeur;
    this.hauteur = mHauteur;
    }
}
