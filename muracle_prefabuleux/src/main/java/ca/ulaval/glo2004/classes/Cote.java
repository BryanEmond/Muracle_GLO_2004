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

    boolean mExterieur;

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

    public void AjouterSeparateur(Separateur separateur) {
        separateurs.add(separateur);
        Collections.sort(separateurs);
        separateur.setmCote(this);
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

        for (Accessoire accessoire : accessoires)
            accessoire.setCote(this);
    }

    public ArrayList<Separateur> getSeparateurs() {
        return separateurs;
    }
    public void sortSeparateurs(){
        Collections.sort(this.separateurs);
    }
    public void setSeparateurs(ArrayList<Separateur> separateurs) {
        Collections.sort(separateurs);
        this.separateurs = separateurs;

        for(Separateur sep : separateurs)
            sep.setmCote(this);
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

        for(Mur mur : murs)
        {
            mur.genererPolygonePlan();
            mur.genererPolygonePlanRetourAir();

            polygones.add(mur.mPolygonePlan);
            if(mur.aRetourAir())
                polygones.add(mur.mPolygonePlanRetourAir);
        }

        return polygones;
    }

    public ArrayList<Polygone> getPolygoneElevation(boolean exterieur)
    {
        ArrayList<Polygone> polygones = new ArrayList<Polygone>();

        for(int i = 0; i < murs.size(); i++)
        {
            murs.get(i).genererPolygoneELV(exterieur);
            polygones.add((murs.get(i).mPolygoneElevation));

            if(murs.get(i).aRetourAir())
            {
                murs.get(i).genererPolygoneRetourAirELV(exterieur);
                polygones.add(murs.get(i).mPolygoneElevationRetourAir);
            }
        }

        if(mSalle.ElementSelectionne instanceof Separateur)
        {
            Separateur sep = (Separateur) mSalle.ElementSelectionne;

            Imperial x = sep.getDistanceBordDeReference();

            if(exterieur)
            {
                if(getDirection().estHorizontal())
                    x = x.substract(mSalle.getLargeur()).abs();
                else
                    x = x.substract(mSalle.getProfondeur()).abs();
            }

            Imperial y1 = new Imperial(0);
            Imperial y2 = mSalle.getHauteur();
            polygones.add(new Polygone(Color.RED, new PointImperial(x, y1), new PointImperial(x, y2)));
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
    public boolean PointEstDansCoteElevation(PointImperial point) {
        ArrayList<Double> coins = getPolygoneElevationCoins();

        return point.mX.getFormeNormal() >= coins.get(0) && point.mX.getFormeNormal() <= coins.get(1) &&
                point.mY.getFormeNormal() >= coins.get(2) && point.mY.getFormeNormal() <= coins.get(3);
    }
    public boolean PointEstDansCote(PointImperial point) {
        ArrayList<Double> coins = getPolygonePlanCoins();

        return point.mX.getFormeNormal() >= coins.get(0) && point.mX.getFormeNormal() <= coins.get(1) &&
                point.mY.getFormeNormal() >= coins.get(2) && point.mY.getFormeNormal() <= coins.get(3);
    }

    public boolean PointSeparateurEstSurAccessoire(Imperial point) {
        for (Accessoire accessoire: accessoires) {
            accessoire.genererPolygoneELV();
            ArrayList<Double> coins = accessoire.mPolygoneElevation.getCoinsDouble();
            if(point.getFormeNormal() >= coins.get(0) && point.getFormeNormal() <= coins.get(1)) return true;
        }
        return false;
    }

    public void SupprimerSeparateur(Separateur separateur) {separateurs.remove(separateur);}

    public void AjouterAccessoire(Accessoire accessoire) {accessoires.add(accessoire);}
    public void SupprimerAccessoire(Accessoire accessoire) {accessoires.remove(accessoire);}

    public Separateur getPremierSeparateur()
    {
        if(separateurs.size() == 0)
            return null;

        return separateurs.get(0);
    }

    public Separateur getDernierSeparateur()
    {
        if(separateurs.size() == 0)
            return null;

        return separateurs.get(separateurs.size() - 1);
    }

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

    public Mur getMurSuivant(int index){
        if(murs.size()==0)
            return null;
        if(murs.size() -1 == index)
            return null;
        return murs.get(index + 1);
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

    public boolean getExterieur(){
        return mExterieur;
    }
}
