package ca.ulaval.glo2004.classes;

import java.util.ArrayList;
//as un impacte sur le poid du mur...

public class Accessoire extends Element{

    Mur mur;
    boolean mPerceExtérieur;
    boolean mPerceInterieur;
    Imperial mLargeur;
    Imperial mHauteur;
    Polygone mPolygonePlan;
    Polygone mPolygoneElevation;
    String mNom;

    public Accessoire(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur,
                      Imperial mLargeur, Imperial mHauteur,Polygone mPolygonePlan, Polygone mPolygoneElevation,
                      String mNom) {
        super(mY, mX);
        this.mPerceExtérieur = mPerceExtérieur;
        this.mPerceInterieur = mPerceInterieur;
        this.mLargeur = mLargeur;
        this.mHauteur = mHauteur;
        this.mPolygonePlan = mPolygonePlan;
        this.mPolygoneElevation = mPolygoneElevation;
        this.mNom = mNom;
    }


    public Salle salle(){
    return new Salle(new Imperial(1, 1, 1),new Imperial(1, 1, 1),
            new Imperial(1, 1, 1),new Imperial(1, 1, 1),
            new Imperial(1, 1, 1),new Imperial(1, 1, 1),
            new Imperial(1, 1, 1), true, new ArrayList<Cote>()) ;
    }

    public Cote cote(){
        ArrayList<PointImperial> pointImperials = new ArrayList<PointImperial>();
    return new Cote(new Imperial(1,1,1),new Imperial(1,1,1),new Imperial(1,1,1),
            new Polygone("FFFFF", pointImperials),"NORD",new Polygone("FFFFF", pointImperials) );
    }

    public Mur mur(){
        Imperial imperial = new Imperial(1,1,1);

        ArrayList<PointImperial> pointImperials = new ArrayList<PointImperial>();
        pointImperials.add(new PointImperial(new Imperial(1,1,1),
                new Imperial(1,1,1)));

        Polygone polygone =new Polygone("FFFFF", pointImperials);


    return new Mur(imperial, imperial, cote(), salle(), polygone, polygone, imperial, imperial, imperial, imperial,
            imperial, imperial,imperial,imperial);
    }

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
