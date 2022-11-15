package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Cote extends Element{
    Imperial mZ;

    String mDirection;
    Imperial mPolygonePlan;
    Imperial mPolygoneElevation;

    ArrayList<Mur> murs = new ArrayList<>();
    ArrayList<Accessoire> accessoires;

    ArrayList<Separateur> separateurs;

    public Cote(Imperial mY, Imperial mX, String mDirection) {
        super(mY, mX);
        this.mDirection = mDirection;
    }
    @Override
    public void calculeDisposition() {
        super.calculeDisposition();
    }

    public void AjouterSeparateur(Separateur separateur) {separateurs.add(separateur);}

    public Imperial getmPolygonePlan() {
        return mPolygonePlan;
    }

    public void setmPolygonePlan(Imperial mPolygonePlan) {
        this.mPolygonePlan = mPolygonePlan;
    }

    public Imperial getmPolygoneElevation() {
        return mPolygoneElevation;
    }

    public void setmPolygoneElevation(Imperial mPolygoneElevation) {
        this.mPolygoneElevation = mPolygoneElevation;
    }

    public ArrayList<Mur> getMurs() {
        return murs;
    }

    public void setMurs(ArrayList<Mur> murs) {
        this.murs = murs;
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

    public void SupprimerSeparateur(Separateur separateur) {separateurs.remove(separateur);}

    public void AjouterAccessoire(Accessoire accessoire) {accessoires.add(accessoire);}
    public void SupprimerAccessoire(Accessoire accessoire) {accessoires.remove(accessoire);}


}
