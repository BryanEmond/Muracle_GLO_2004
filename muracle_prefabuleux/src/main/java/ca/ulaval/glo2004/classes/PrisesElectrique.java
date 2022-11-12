package ca.ulaval.glo2004.classes;

//import Accessoire as parent
//s'affiche uniquement a l'intérieur de la salle

public class PrisesElectrique extends Accessoire {

    Imperial largeur = new Imperial(2,0,0);
    Imperial hauteur = new Imperial(4,0,0);

    Boolean perceInterieur = true;
    Boolean perceExterieur = false;


    public PrisesElectrique(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur,
                            Polygone mPolygonePlan, Polygone mPolygoneElevation, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mPolygonePlan, mPolygoneElevation, mNom);
    this.hauteur = mHauteur;
    this.largeur = mLargeur;
    this.perceExterieur = mPerceExtérieur;
    this.perceInterieur = mPerceInterieur;
    }


}

