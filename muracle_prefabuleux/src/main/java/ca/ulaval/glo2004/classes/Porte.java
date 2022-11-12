package ca.ulaval.glo2004.classes;
//import Accessoire as parent

public class Porte extends Accessoire{

    Imperial largeur = new Imperial(38,0,0);
    Imperial hauteur = new Imperial(88,0,0);
    Boolean perceInterieur = true;
    Boolean perceExterirur = false;


    public Porte(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur,
                 Polygone mPolygonePlan, Polygone mPolygoneElevation, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mPolygonePlan, mPolygoneElevation, mNom);
    this.largeur = mLargeur;
    this.hauteur = mHauteur;
    this.perceInterieur = mPerceInterieur;
    this.perceExterirur = mPerceExtérieur;
    }
}
