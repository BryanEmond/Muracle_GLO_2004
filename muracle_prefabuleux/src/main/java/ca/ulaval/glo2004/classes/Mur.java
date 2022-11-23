package ca.ulaval.glo2004.classes;

import sun.nio.ch.Util;

import java.awt.*;
import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Mur extends Element implements Serializable {

    int uniqueID;
    Cote mCote;
    Salle mSalle;
    Polygone mPolygoneElevation;
    Polygone mPolygonePlan;

    Imperial mLargeur;

    Imperial mBandeSoudageVerticale;
    Imperial mBandeSoudageHorizontale;
    Imperial mPolygoneMargeHaut;
    Imperial mPolygoneMargeBas;
    Imperial mPolygoneMargeGauche;
    Imperial mPolygoneMargeDroite;

    public Mur(Salle mSalle, Cote mCote, Imperial y, Imperial x, Imperial largeur, Imperial mBandeSoudageVerticale, Imperial mBandeSoudageHorizontale, Imperial mPolygoneMargeHaut, Imperial mPolygoneMargeBas, Imperial mPolygoneMargeGauche, Imperial mPolygoneMargeDroite) {
        super(y, x);
        this.mLargeur = largeur;

        this.mCote = mCote;
        this.mSalle = mSalle;
        this.mBandeSoudageVerticale = mBandeSoudageVerticale;
        this.mBandeSoudageHorizontale = mBandeSoudageHorizontale;
        this.mPolygoneMargeHaut = mPolygoneMargeHaut;
        this.mPolygoneMargeBas = mPolygoneMargeBas;
        this.mPolygoneMargeGauche = mPolygoneMargeGauche;
        this.mPolygoneMargeDroite = mPolygoneMargeDroite;

        //genererPolygonePlan();
        genererPolygoneELV();
    }

    public Imperial getmLargeur() {
        return mLargeur;
    }

    public void setmLargeur(Imperial mLargeur) {
        this.mLargeur = mLargeur;
    }

    public Imperial getmBandeSoudageVerticale() {
        return mBandeSoudageVerticale;
    }

    public void setmBandeSoudageVerticale(Imperial mBandeSoudageVerticale) {
        this.mBandeSoudageVerticale = mBandeSoudageVerticale;
    }

    public Imperial getmBandeSoudageHorizontale() {
        return mBandeSoudageHorizontale;
    }

    public void setmBandeSoudageHorizontale(Imperial mBandeSoudageHorizontale) {
        this.mBandeSoudageHorizontale = mBandeSoudageHorizontale;
    }

    public void calculerDisposition() {

    }

    public Polygone polygonesElevation(boolean exterieur) {
        return mPolygoneElevation;
    }

    public void polygonesElevationDecoupage(boolean exterieur) {

    }

    public ArrayList<Accessoire> accessoires() {

        ArrayList<Accessoire> listAccessoires = new ArrayList<>();

        for (Accessoire var : mCote.accessoires)
        {
            if (var.mur.uniqueID == uniqueID){
                listAccessoires.add(var);
            };
        }

        return listAccessoires;
    }

    public Accessoire accessoire(PointImperial point) {

        ArrayList<Accessoire> listAccessoires = new ArrayList<>();

        for (Accessoire var : mCote.accessoires)
        {
            if (var.mur.uniqueID == uniqueID){
                listAccessoires.add(var);
            };
        }

        // TODO méthode dans accessoires pour determiner zone par rapport point

        return null;
    }

    public void genererPolygonePlan()
    {
        Imperial x1 = super.mX;
        Imperial y1 = super.mY;
        Imperial x2;
        Imperial y2;

        boolean estPremierMur = mCote.getPremierMur() == this;
        boolean estDernierMur = mCote.getDernierMur() == this;

        if(mCote.mDirection.equals(Utilitaire.Direction.NORD) || mCote.mDirection.equals(Utilitaire.Direction.SUD))
        {
            x2 = x1.add(mLargeur);
            y2 = y1.add(mSalle.epaisseurMurs);
        }
        else
        {
            x2 = x1.add(mSalle.epaisseurMurs);
            y2 = y1.add(mLargeur);
        }

        PointImperial p1 = new PointImperial(x1, y1);
        PointImperial p2 = new PointImperial(x1, y2);
        PointImperial p3 = new PointImperial(x2, y2);
        PointImperial p4 = new PointImperial(x2, y1);

        if(mCote.mDirection == Utilitaire.Direction.NORD)
        {
            if(estPremierMur)
                p2.mX = p2.mX.add(mSalle.epaisseurMurs);
            if(estDernierMur)
                p3.mX = p3.mX.add(mSalle.epaisseurMurs.negative());
        }
        else if(mCote.mDirection == Utilitaire.Direction.SUD)
        {
            if(estPremierMur)
                p1.mX = p1.mX.add(mSalle.epaisseurMurs);
            if (estDernierMur)
                p4.mX = p4.mX.add(mSalle.epaisseurMurs.negative());
        }
        else if(mCote.mDirection == Utilitaire.Direction.EST)
        {
            if(estPremierMur)
                p1.mY = p1.mY.add(mSalle.epaisseurMurs.negative());
            if(estDernierMur)
                p2.mY = p2.mY.add(mSalle.epaisseurMurs);
        }
        else if(mCote.mDirection == Utilitaire.Direction.OUEST)
        {
            if(estPremierMur)
                p4.mY = p4.mY.add(mSalle.epaisseurMurs.negative());
            if(estDernierMur)
                p3.mY = p3.mY.add(mSalle.epaisseurMurs);
        }

        this.mPolygonePlan = new Polygone(Color.BLACK, p1, p2, p3, p4);
    }

    public void genererPolygoneELV(){
        Imperial x1 = super.mX;
        Imperial y1 = super.mY;
        Imperial x2;
        Imperial y2;

        x2 = x1.add(mLargeur);
        y2 = y1.add(mSalle.getHauteur());

        this.mPolygoneElevation = new Polygone(Color.BLACK, new PointImperial(x1,y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));
    }

    //public
}
