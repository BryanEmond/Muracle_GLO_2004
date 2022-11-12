package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Separateur extends Element {

    Imperial distanceBordDeReference ;

    Cote mCote ;

    Salle mSalle ;

    Polygone mPolygonePlan;

    public Separateur(Imperial mY, Imperial mX, Imperial distanceBordDeReference, Cote mCote, Salle mSalle, Polygone mPolygonePlan) {
        super(mY, mX);
        this.distanceBordDeReference = distanceBordDeReference;
        this.mCote = mCote;
        this.mSalle = mSalle;
        this.mPolygonePlan = mPolygonePlan;
    }

    public ArrayList<Polygone> getPolygones() {
        return ;
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

    public Salle getmSalle() {
        return mSalle;
    }

    public void setmSalle(Salle mSalle) {
        this.mSalle = mSalle;
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
}
