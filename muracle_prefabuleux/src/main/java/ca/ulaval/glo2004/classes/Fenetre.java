package ca.ulaval.glo2004.classes;
//import Accessoire as parent
public class Fenetre extends Accessoire {

    Boolean perceInterieur = true;
    Boolean perceExterieur = true;
    Imperial largeur = new Imperial(24, 0, 0);
    Imperial hauteur = new Imperial( 24, 0, 0);

    public Fenetre(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur,
                   Imperial mHauteur, Polygone mPolygonePlan, Polygone mPolygoneElevation, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur, mPolygonePlan, mPolygoneElevation, mNom);
    this.perceExterieur = mPerceExtérieur;
    this.perceInterieur = mPerceInterieur;
    this.hauteur = ajoutMargeMoulure(mHauteur);
    this.largeur = ajoutMargeMoulure(mLargeur);
    }

    private Imperial ajoutMargeMoulure(Imperial valeur){
        return valeur.add(new Imperial(0,1,8));
    }

}

