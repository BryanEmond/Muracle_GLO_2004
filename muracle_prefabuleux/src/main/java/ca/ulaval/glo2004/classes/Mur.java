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
        this.mEpaisseurMur1 = mEpaisseurMur1;
        this.mEpaisseurMur2 = mEpaisseurMur2;
        this.mBandeSoudage1 = mBandeSoudage1;
        this.mBandeSoudage2 = mBandeSoudage2;
        this.mPolygoneMarge1 = mPolygoneMarge1;
        this.mPolygoneMarge12 = mPolygoneMarge12;
        this.mPolygoneMarge2 = mPolygoneMarge2;
        this.mPolygoneMarge22 = mPolygoneMarge22;
        this.mPolygoneElevation = mPolygoneElevation;
        this.mPolygonePlan = mPolygonePlan;
    }

    @Override
    public void calculeDisposition() {
        super.calculeDisposition();
    }

    public int getUniqueID() {
        return uniqueID;
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

    public Polygone getmPolygoneElevation() {
        return mPolygoneElevation;
    }

    public void setmPolygoneElevation(Polygone mPolygoneElevation) {
        this.mPolygoneElevation = mPolygoneElevation;
    }

    public Polygone getmPolygonePlan() {
        return mPolygonePlan;
    }

    public void setmPolygonePlan(Polygone mPolygonePlan) {
        this.mPolygonePlan = mPolygonePlan;
    }

    public Imperial getmEpaisseurMur1() {
        return mEpaisseurMur1;
    }

    public void setmEpaisseurMur1(Imperial mEpaisseurMur1) {
        this.mEpaisseurMur1 = mEpaisseurMur1;
    }

    public Imperial getmEpaisseurMur2() {
        return mEpaisseurMur2;
    }

    public void setmEpaisseurMur2(Imperial mEpaisseurMur2) {
        this.mEpaisseurMur2 = mEpaisseurMur2;
    }

    public Imperial getmBandeSoudage1() {
        return mBandeSoudage1;
    }

    public void setmBandeSoudage1(Imperial mBandeSoudage1) {
        this.mBandeSoudage1 = mBandeSoudage1;
    }

    public Imperial getmBandeSoudage2() {
        return mBandeSoudage2;
    }

    public void setmBandeSoudage2(Imperial mBandeSoudage2) {
        this.mBandeSoudage2 = mBandeSoudage2;
    }

    public Imperial getmPolygoneMarge1() {
        return mPolygoneMarge1;
    }

    public void setmPolygoneMarge1(Imperial mPolygoneMarge1) {
        this.mPolygoneMarge1 = mPolygoneMarge1;
    }

    public Imperial getmPolygoneMarge12() {
        return mPolygoneMarge12;
    }

    public void setmPolygoneMarge12(Imperial mPolygoneMarge12) {
        this.mPolygoneMarge12 = mPolygoneMarge12;
    }

    public Imperial getmPolygoneMarge2() {
        return mPolygoneMarge2;
    }

    public void setmPolygoneMarge2(Imperial mPolygoneMarge2) {
        this.mPolygoneMarge2 = mPolygoneMarge2;
    }

    public Imperial getmPolygoneMarge22() {
        return mPolygoneMarge22;
    }

    public void setmPolygoneMarge22(Imperial mPolygoneMarge22) {
        this.mPolygoneMarge22 = mPolygoneMarge22;
    }

    public Polygone genererPlan() {
        return ;
    }

    public Polygone genererDecoupage() {
        return ;
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
