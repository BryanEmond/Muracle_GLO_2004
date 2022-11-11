package ca.ulaval.glo2004.classes;

//import Accessoire as parent
//s'affiche uniquement a l'intérieur de la salle

public class PrisesElectrique extends Accessoire {

    int largeur = 2;
    int hauteur = 4;

    public PrisesElectrique(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur, Polygone mPolygonePlan, Polygone mPolygoneElevation, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mPolygonePlan, mPolygoneElevation, mNom);
    }


    public boolean getContrainte() {
        return true;
    }

}

