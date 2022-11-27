package ca.ulaval.glo2004.classes;

import java.io.Serializable;
import java.util.ArrayList;
//as un impacte sur le poid du mur...

public abstract class Accessoire extends Element implements Serializable {

    Cote cote;
    Mur mur;
    boolean mPerceExtérieur;
    boolean mPerceInterieur;
    Imperial mLargeur;
    Imperial mHauteur;

    Salle salle;
    Polygone mPolygonePlan;
    Polygone mPolygoneElevation;

    public Accessoire(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur,
                      Imperial mLargeur, Imperial mHauteur) {
        super(mY, mX);
        this.mPerceExtérieur = mPerceExtérieur;
        this.mPerceInterieur = mPerceInterieur;
        this.mLargeur = mLargeur;
        this.mHauteur = mHauteur;
    }

    public abstract ArrayList<Polygone> genererPolygoneELV(boolean exterieur);

    public void calculeDisposition(){ }

    public ArrayList<Polygone> polygonesElevation(boolean exterieur) {
        return exterieur ? new ArrayList<Polygone>() : new ArrayList<Polygone>();
    }

    public ArrayList<Polygone>polygonesPlan() {
    return new ArrayList<Polygone>();
    }

    public Mur getMur() {
        return mur;
    }

    public void setMur(Mur mur) {
        this.mur = mur;
    }

    public boolean ismPerceExtérieur() {
        return mPerceExtérieur;
    }

    public void setmPerceExtérieur(boolean mPerceExtérieur) {
        this.mPerceExtérieur = mPerceExtérieur;
    }

    public boolean ismPerceInterieur() {
        return mPerceInterieur;
    }

    public void setmPerceInterieur(boolean mPerceInterieur) {
        this.mPerceInterieur = mPerceInterieur;
    }

    public Imperial getmLargeur() {
        return mLargeur;
    }

    public void setmLargeur(Imperial mLargeur) {
        this.mLargeur = mLargeur;
    }

    public Imperial getmHauteur() {
        return mHauteur;
    }

    public void setmHauteur(Imperial mHauteur) {
        this.mHauteur = mHauteur;
    }

    public Polygone getmPolygonePlan() {
        return mPolygonePlan;
    }

    public void setmPolygonePlan(Polygone mPolygonePlan) {
        this.mPolygonePlan = mPolygonePlan;
    }

    public Polygone getmPolygoneElevation(boolean interieur) {
        return mPolygoneElevation;
    }

    public void setmPolygoneElevation(Polygone mPolygoneElevation) {
        this.mPolygoneElevation = mPolygoneElevation;
    }

    public Cote getCote() {
        return cote;
    }

    public void setCote(Cote cote) {
        this.cote = cote;
    }
}
