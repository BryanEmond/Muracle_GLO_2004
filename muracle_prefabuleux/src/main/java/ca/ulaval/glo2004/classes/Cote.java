package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Cote extends Element{
    Imperial mZ;
    Imperial mPolygonePlan;
    Imperial mPolygoneElevation;

    ArrayList<Mur> murs;
    ArrayList<Accessoire> accessoires;

    ArrayList<Separateur> separateurs;

    public Cote(Imperial mY, Imperial mX, Imperial mZ, Imperial mPolygonePlan, Imperial mPolygoneElevation) {
        super(mY, mX);
        this.mZ = mZ;
        this.mPolygonePlan = mPolygonePlan;
        this.mPolygoneElevation = mPolygoneElevation;
    }

    public Accessoire AjouterAccessoire(PointImperial pointImperial) {
        for (Accessoire customer : accessoires) {
            if (true) {
                return customer;
            }
        }
        return null;

    }

    public void AjouterAccessoire(Accessoire accessoire) {

    }

    public void AjouterSeparateur(Accessoire accessoire) {

    }

    public void SupprimerSeparateur(PointImperial point) {

    }

    public void SupprimerAccessoire(PointImperial point) {

    }


}
