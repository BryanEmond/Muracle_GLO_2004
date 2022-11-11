package ca.ulaval.glo2004.classes;

import java.util.ArrayList;
//as un impacte sur le poid du mur...

public class Accessoire extends Element{

    Mur mur;
    private boolean mPerceExtérieur;
    private boolean mPerceInterieur;
    private Imperial mLargeur;
    private Imperial mHauteur;
    private Polygone mPolygonePlan;
    private Polygone mPolygoneElevation;
    private String mNom;

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
    return new Cote(new Imperial(1,1,1),new Imperial(1,1,1),new Imperial(1,1,1),
            new Imperial(1,1,1),new Imperial(1,1,1) );
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


}
