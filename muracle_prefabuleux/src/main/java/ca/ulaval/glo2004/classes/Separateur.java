package ca.ulaval.glo2004.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Separateur extends Element implements Serializable, Comparable<Separateur> {

    Imperial distanceBordDeReference ;

    Cote mCote ;

    Polygone mPolygonePlan;

    Polygone mPolygoneElevation;

    public Separateur(Imperial mY, Imperial mX, Imperial distanceBordDeReference, Cote mCote, Polygone mPolygonePlan) {
        super(mY, mX);
        this.distanceBordDeReference = distanceBordDeReference;
        this.mCote = mCote;
        this.mPolygonePlan = mPolygonePlan;
    }

    public ArrayList<Polygone> getPolygones() {
        return null;
    }

    public Imperial getDistanceBordDeReference() {
        return distanceBordDeReference;
    }

    public void setDistanceBordDeReference(Imperial distanceBordDeReference) {
        this.distanceBordDeReference = distanceBordDeReference;
    }

    public Cote getmCote() {
        return mCote;
    }

    public void setmCote(Cote mCote) {
        this.mCote = mCote;
    }

    public Polygone getmPolygonePlan() {
        return mPolygonePlan;
    }

    public void setmPolygonePlan(Polygone mPolygonePlan) {
        this.mPolygonePlan = mPolygonePlan;
    }

    @Override
    public void calculeDisposition() {
        super.calculeDisposition();
    }

    public Separateur getSeparateurPrecedent()
    {
        return mCote.getSeparateurPrecedent(this);
    }

    public Separateur getSeparateurSuivant()
    {
        return mCote.getSeparateurSuivant(this);
    }

    @Override
    public int compareTo(Separateur separateur) {
        return this.distanceBordDeReference.compareTo(separateur.distanceBordDeReference);
    }
}
