package ca.ulaval.glo2004.classes;
//import Acessoire as a parent

// toujours centré sur le panneau, largeur configurable, seulement sur les panneau
//intérieur

public class RetourAir extends Accessoire{


    public RetourAir(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur, Polygone mPolygonePlan, Polygone mPolygoneElevation, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mPolygonePlan, mPolygoneElevation, mNom);
    }
}
