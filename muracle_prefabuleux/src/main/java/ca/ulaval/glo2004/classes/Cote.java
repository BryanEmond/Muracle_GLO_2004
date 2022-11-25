package ca.ulaval.glo2004.classes;

import java.awt.*;
import ca.ulaval.glo2004.enums.Direction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Cote extends Element implements Serializable {
    Imperial mZ;

    Salle mSalle;

    Utilitaire.Direction mDirection;
    Direction mDirectionL;
    Polygone mPolygonePlan;
    Polygone mPolygoneElevation;

    ArrayList<Mur> murs = new ArrayList<>();
    ArrayList<Accessoire> accessoires;

    ArrayList<Separateur> separateurs;

    public Cote(Imperial mY, Imperial mX, Imperial mZ, Utilitaire.Direction mDirection) {
        super(mY, mX);
        this.mZ = mZ;
        this.mDirection = mDirection;
        this.accessoires = new ArrayList<>();
        separateurs = new ArrayList<>();
    }
    @Override
    public void calculeDisposition() {
        super.calculeDisposition();
    }

    public void AjouterSeparateur(Separateur separateur) {separateurs.add(separateur);}

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

    public ArrayList<Mur> getMurs() {
        return murs;
    }

    public void setMurs(ArrayList<Mur> murs) {
        this.murs = murs;

        for(Mur mur : murs)
        {
            mur.genererPolygonePlan();
        }
    }

    public ArrayList<Accessoire> getAccessoires() {
        return accessoires;
    }

    public void setAccessoires(ArrayList<Accessoire> accessoires) {
        this.accessoires = accessoires;
    }

    public ArrayList<Separateur> getSeparateurs() {
        return separateurs;
    }

    public void setSeparateurs(ArrayList<Separateur> separateurs) {
        this.separateurs = separateurs;
    }

    public Separateur getSeparateurPrecedent(Separateur separateur)
    {
        int index = separateurs.indexOf(separateur);

        if (index == 0 || index == -1)
            return null;

        return separateurs.get(index - 1);
    }

    public Separateur getSeparateurSuivant(Separateur separateur)
    {
        int index = separateurs.indexOf(separateur);

        if(index == separateurs.size() - 1 || index == -1)
        {
            return null;
        }

        return separateurs.get(index + 1);
    }

    public ArrayList<Polygone> getPolygonesPlan()
    {
        ArrayList<Polygone> polygones = new ArrayList<Polygone>();

        for(int i = 0; i < murs.size(); i++)
        {
            polygones.add(murs.get(i).mPolygonePlan);
        }

        //TODO THIS IS JUST FOR TESTS, JUSTE POUR LE CÔTÉ NORD
       /* if(separateurs != null)
        {
            for(Separateur s : separateurs)
            {
                PointImperial p1 = new PointImperial(s.getDistanceBordDeReference(), new Imperial(0));
                PointImperial p2 = new PointImperial(s.getDistanceBordDeReference(), new Imperial(1));
                Polygone p = new Polygone(Color.pink, p1, p2, p1, p2);
                polygones.add(p);
            }
        }*/

        return polygones;
    }

    public ArrayList<Polygone> getPolygoneElevation(boolean exterieur)
    {
        ArrayList<Polygone> polygones = new ArrayList<Polygone>();

        for(int i = 0; i < murs.size(); i++)
        {
            polygones.add(murs.get(i).mPolygoneElevation);
            if (!exterieur && i == 0 && murs.size()>1)
            {
                Mur premierMur = getPremierMur().copieMur(getPremierMur());

                premierMur.setmLargeur(premierMur.getmLargeur().add(getmSalle().epaisseurMurs.negative()));
                premierMur.setmX(premierMur.mX.add(getmSalle().epaisseurMurs));
                premierMur.genererPolygoneELV();
                polygones.remove(polygones.get(i));
                polygones.add(premierMur.mPolygoneElevation);
            }
            if (!exterieur && i == murs.size() - 1 && murs.size()>1){
                Mur dernierMur = getDernierMur().copieMur(getDernierMur());

                dernierMur.setmLargeur(dernierMur.getmLargeur().add(getmSalle().epaisseurMurs.negative()));
                dernierMur.genererPolygoneELV();
                polygones.remove(polygones.get(i));
                polygones.add(dernierMur.mPolygoneElevation);
            }

            if (!exterieur && murs.size() == 1){
                Mur premierMur = getPremierMur().copieMur(getPremierMur());
                Imperial epaisseurMurDouble = new Imperial(getmSalle().epaisseurMurs.entier * 2);
                premierMur.setmLargeur(premierMur.getmLargeur().add(epaisseurMurDouble.negative()));
                premierMur.setmX(premierMur.mX.add(getmSalle().epaisseurMurs));
                premierMur.genererPolygoneELV();
                polygones.remove(polygones.get(i));
                polygones.add(premierMur.mPolygoneElevation);

            }



        }

        return polygones;
    }

    public ArrayList<Double> getPolygonePlanCoins()
    {
        Mur PremierMur = getPremierMur();
        Mur DernierMur = getDernierMur();

        if(PremierMur == DernierMur){
            return PremierMur.mPolygonePlan.getCoinsDouble();
        }

        ArrayList<Double> pointsCoin = new ArrayList<>();

        ArrayList<Double> coinsPremierMur = PremierMur.mPolygonePlan.getCoinsDouble();
        ArrayList<Double> coinsDernierMur = DernierMur.mPolygonePlan.getCoinsDouble();

        coinsPremierMur.addAll(coinsDernierMur);

        ArrayList<Double> coinsMurX = new ArrayList<>();
        ArrayList<Double> coinsMurY = new ArrayList<>();

        coinsMurX.add(coinsPremierMur.get(0));
        coinsMurX.add(coinsPremierMur.get(1));
        coinsMurX.add(coinsDernierMur.get(0));
        coinsMurX.add(coinsDernierMur.get(1));

        coinsMurY.add(coinsPremierMur.get(2));
        coinsMurY.add(coinsPremierMur.get(3));
        coinsMurY.add(coinsDernierMur.get(2));
        coinsMurY.add(coinsDernierMur.get(3));

        pointsCoin.add(Collections.min(coinsMurX));
        pointsCoin.add(Collections.max(coinsMurX));
        pointsCoin.add(Collections.min(coinsMurY));
        pointsCoin.add(Collections.max(coinsMurY));

        return pointsCoin;
    }

    public ArrayList<Double> getPolygoneElevationCoins()
    {
        Mur PremierMur = getPremierMur();
        Mur DernierMur = getDernierMur();

        if(PremierMur == DernierMur){
            return PremierMur.mPolygoneElevation.getCoinsDouble();
        }

        ArrayList<Double> pointsCoin = new ArrayList<>();

        ArrayList<Double> coinsPremierMur = PremierMur.mPolygoneElevation.getCoinsDouble();
        ArrayList<Double> coinsDernierMur = DernierMur.mPolygoneElevation.getCoinsDouble();

        coinsPremierMur.addAll(coinsDernierMur);

        ArrayList<Double> coinsMurX = new ArrayList<>();
        ArrayList<Double> coinsMurY = new ArrayList<>();

        coinsMurX.add(coinsPremierMur.get(0));
        coinsMurX.add(coinsPremierMur.get(1));
        coinsMurX.add(coinsDernierMur.get(0));
        coinsMurX.add(coinsDernierMur.get(1));

        coinsMurY.add(coinsPremierMur.get(2));
        coinsMurY.add(coinsPremierMur.get(3));
        coinsMurY.add(coinsDernierMur.get(2));
        coinsMurY.add(coinsDernierMur.get(3));

        pointsCoin.add(Collections.min(coinsMurX));
        pointsCoin.add(Collections.max(coinsMurX));
        pointsCoin.add(Collections.min(coinsMurY));
        pointsCoin.add(Collections.max(coinsMurY));

        return pointsCoin;
    }

    public boolean PointEstDansCote(PointImperial point) {
        ArrayList<Double> coins = getPolygonePlanCoins();

        return point.mX.getFormeNormal() >= coins.get(0) && point.mX.getFormeNormal() <= coins.get(1) &&
                point.mY.getFormeNormal() >= coins.get(2) && point.mY.getFormeNormal() <= coins.get(3);
    }

    public boolean PointSeparateurEstSurAccessoire(Imperial point) {
        /*for (Accessoire accessoire: accessoires) {
            ArrayList<Double> coins = accessoire.mPolygoneElevation.getCoinsDouble();
            if(point.getFormeNormal() >= coins.get(0) && point.getFormeNormal() <= coins.get(1)) return true;
        }*/
        return false;
    }

    public void SupprimerSeparateur(Separateur separateur) {separateurs.remove(separateur);}

    public void AjouterAccessoire(Accessoire accessoire) {accessoires.add(accessoire);}
    public void SupprimerAccessoire(Accessoire accessoire) {accessoires.remove(accessoire);}

    public Mur getPremierMur()
    {
        if(murs.size() == 0)
            return null;

        return murs.get(0);
    }

    public Mur getDernierMur()
    {
        if(murs.size() == 0)
            return null;

        return murs.get(murs.size() - 1);
    }

    public void setmSalle(Salle mSalle) {
        this.mSalle = mSalle;
    }

    public Salle getmSalle(){
        return mSalle;
    }

    public Utilitaire.Direction getDirection() {
        return mDirection;
    }
}
