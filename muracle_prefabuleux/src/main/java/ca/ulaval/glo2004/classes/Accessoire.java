package ca.ulaval.glo2004.classes;

import java.io.Serializable;
import java.util.ArrayList;
//as un impacte sur le poid du mur...

public abstract class Accessoire extends Element implements Serializable {

    Mur mur;
    boolean mPerceExtérieur;
    boolean mPerceInterieur;
    Imperial mLargeur;
    Imperial mHauteur;
    Polygone mPolygonePlan;
    Polygone mPolygoneElevation;
    String mNom;

    public Accessoire(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur,
                      Imperial mLargeur, Imperial mHauteur, String mNom) {
        super(mY, mX);
        this.mPerceExtérieur = mPerceExtérieur;
        this.mPerceInterieur = mPerceInterieur;
        this.mLargeur = mLargeur;
        this.mHauteur = mHauteur;
        this.mNom = mNom;
    }

    public abstract ArrayList<Polygone> genererPolygoneELV();

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

    public Polygone getmPolygoneElevation() {
        return mPolygoneElevation;
    }

    public void setmPolygoneElevation(Polygone mPolygoneElevation) {
        this.mPolygoneElevation = mPolygoneElevation;
    }

    public String getmNom() {
        return mNom;
    }

    public void setmNom(String mNom) {
        this.mNom = mNom;
    }
}
