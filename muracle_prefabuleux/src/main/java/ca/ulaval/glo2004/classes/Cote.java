package ca.ulaval.glo2004.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Cote extends Element implements Serializable {
    Imperial mZ;

    Utilitaire.Direction mDirection;
    Polygone mPolygonePlan;
    Polygone mPolygoneElevation;

    ArrayList<Mur> murs = new ArrayList<>();
    ArrayList<Accessoire> accessoires;

    ArrayList<Separateur> separateurs;

    public Cote(Imperial mY, Imperial mX, Imperial mZ, Utilitaire.Direction mDirection) {
        super(mY, mX);
        this.mZ = mZ;
        this.mDirection = mDirection;
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

    public ArrayList<Polygone> getPolygonesPlan()
    {
        ArrayList<Polygone> polygones = new ArrayList<Polygone>();

        for(int i = 0; i < murs.size(); i++)
        {
            polygones.add(murs.get(i).mPolygonePlan);
        }

        return polygones;
    }

    public ArrayList<Polygone> getPolygoneElevation()
    {
        ArrayList<Polygone> polygones = new ArrayList<Polygone>();

        for(int i = 0; i < murs.size(); i++)
        {
            polygones.add(murs.get(i).mPolygoneElevation);
        }

        return polygones;
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
}
