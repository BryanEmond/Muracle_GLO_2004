package ca.ulaval.glo2004.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Mur extends Element {

    int uniqueID;
    Cote mCote;
    Salle mSalle;
    Polygone mPolygoneElevation;
    Polygone mPolygonePlan;
    Imperial mEpaisseurMur1;
    Imperial mEpaisseurMur2;
    Imperial mBandeSoudage1;
    Imperial mBandeSoudage2;
    Imperial mPolygoneMarge1;
    Imperial mPolygoneMarge12;
    Imperial mPolygoneMarge2;
    Imperial mPolygoneMarge22;

    public Mur(Imperial mY, Imperial mX, Cote mCote, Salle mSalle, Polygone mPolygoneElevation, Polygone mPolygonePlan, Imperial mEpaisseurMur1, Imperial mEpaisseurMur2, Imperial mBandeSoudage1, Imperial mBandeSoudage2, Imperial mPolygoneMarge1, Imperial mPolygoneMarge12, Imperial mPolygoneMarge2, Imperial mPolygoneMarge22) {
        super(mY, mX);
        String uniqueID = UUID.randomUUID().toString();
        this.mCote = mCote;
        this.mSalle = mSalle;
        this.mPolygoneElevation = mPolygoneElevation;
        this.mPolygonePlan = mPolygonePlan;
        this.mEpaisseurMur1 = mEpaisseurMur1;
        this.mEpaisseurMur2 = mEpaisseurMur2;
        this.mBandeSoudage1 = mBandeSoudage1;
        this.mBandeSoudage2 = mBandeSoudage2;
        this.mPolygoneMarge1 = mPolygoneMarge1;
        this.mPolygoneMarge12 = mPolygoneMarge12;
        this.mPolygoneMarge2 = mPolygoneMarge2;
        this.mPolygoneMarge22 = mPolygoneMarge22;
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
    }
}
