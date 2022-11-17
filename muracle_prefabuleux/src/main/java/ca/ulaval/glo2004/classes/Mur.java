package ca.ulaval.glo2004.classes;

import java.awt.*;
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

        genererPolygonePlan();
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

        if(mCote.mDirection == "NORD" || mCote.mDirection == "SUD")
        {
            x2 = x1.add(mLargeur);
            y2 = y1.add(mSalle.epaisseurMurs);
        }
        else
        {
            x2 = x1.add(mSalle.epaisseurMurs);
            y2 = y1.add(mLargeur);
        }


        this.mPolygonePlan = new Polygone(Color.BLACK, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));
    }

}
