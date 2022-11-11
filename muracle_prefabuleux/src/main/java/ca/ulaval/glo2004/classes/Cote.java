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

    public Imperial getmZ() {
        return mZ;
    }

    public void setmZ(Imperial mZ) {
        this.mZ = mZ;
    }

    public Imperial getmPolygonePlan() {
        return mPolygonePlan;
    }

    public void setmPolygonePlan(Imperial mPolygonePlan) {
        this.mPolygonePlan = mPolygonePlan;
    }

    public Imperial getmPolygoneElevation() {
        return mPolygoneElevation;
    }

    public void setmPolygoneElevation(Imperial mPolygoneElevation) {
        this.mPolygoneElevation = mPolygoneElevation;
    }

    public ArrayList<Mur> getMurs() {
        return murs;
    }

    public void setMurs(ArrayList<Mur> murs) {
        this.murs = murs;
    }

    public ArrayList<Accessoire> getAccessoires() {
        return accessoires;
    }

    public void setAccessoires(ArrayList<Accessoire> accessoires) {
        this.accessoires = accessoires;
    }

    public ArrayList<Separateur> getSeparateurs() {
        return separateurs;
    }

    public void setSeparateurs(ArrayList<Separateur> separateurs) {
        this.separateurs = separateurs;
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
