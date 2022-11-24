package ca.ulaval.glo2004.classes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Salle extends Element implements Serializable {

    Imperial epaisseurMurs;
    Imperial marge;
    Imperial hauteur;
    Imperial largeur;
    Imperial profondeur;
    boolean vuePlan;
    ArrayList<Cote> cotes;

    public Salle(Imperial mY, Imperial mX, Imperial epaisseurMurs, Imperial marge, Imperial hauteur, Imperial largeur, Imperial profondeur, boolean vuePlan, ArrayList<Cote> cotes) {
        super(mY, mX);
        this.epaisseurMurs = epaisseurMurs;
        this.marge = marge;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.profondeur = profondeur;
        this.vuePlan = vuePlan;
        this.cotes = cotes;
    }
    public ArrayList<Polygone> polygonePlan(){
        return new ArrayList<Polygone>();
    }

    public Element selection(){
        Imperial imp = new Imperial(1,1,1);
        return new Element(imp, imp);
    }

    public ArrayList<Polygone> polygonesElevation(){
        return new ArrayList<Polygone>();
    }
    public void separateur(PointImperial point) {

        ArrayList<PointImperial> points = new ArrayList<>();

        for (Cote var : cotes)
        {
            for (Polygone polygone:var.getPolygonesPlan()) {
                if(polygone.PointEstDansPolygone(point)){

                    if(var.mDirection == Utilitaire.Direction.NORD || var.mDirection == Utilitaire.Direction.SUD){


                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));

                        // distanceBord = var.getPremierMur().mPolygonePlan.getCoins().get(0);

                        var.AjouterSeparateur(new Separateur(point.mY,point.mX,point.mX,var,new Polygone(Color.BLACK,points)));
                    }else {

                        points.add(new PointImperial(point.mY,polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY,polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY,polygone.points.get(1).mX));
                        points.add(new PointImperial(point.mY,polygone.points.get(1).mX));

                        //double distanceBord = var.getDernierMur().mPolygonePlan.getCoins().get(2);

                        var.AjouterSeparateur(new Separateur(point.mY,point.mX,point.mX,var,new Polygone(Color.BLACK,points)));
                    }
                };
            }
        }
    }

    public Imperial getEpaisseurMurs() {
        return epaisseurMurs;
    }

    public void setEpaisseurMurs(Imperial epaisseurMurs) {
        this.epaisseurMurs = epaisseurMurs;
    }

    public Imperial getMarge() {
        return marge;
    }

    public void setMarge(Imperial marge) {
        this.marge = marge;
    }

    public Imperial getHauteur() {
        return hauteur;
    }

    public void setHauteur(Imperial hauteur) {
        this.hauteur = hauteur;
    }

    public Imperial getLargeur() {
        return largeur;
    }

    public void setLargeur(Imperial largeur) {
        this.largeur = largeur;
    }

    public Imperial getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(Imperial profondeur) {
        this.profondeur = profondeur;
    }

    public boolean isVuePlan() {
        return vuePlan;
    }

    public void setVuePlan(boolean vuePlan) {
        this.vuePlan = vuePlan;
    }

    public ArrayList<Cote> getCotes() {
        return cotes;
    }

    public void setCotes(ArrayList<Cote> cotes) {
        this.cotes = cotes;
    }

    public ArrayList<Polygone> getPolygonesPlan()
    {
        ArrayList<Polygone> polygones = new ArrayList<>();

        for (Cote cote : cotes)
        {
            polygones.addAll(cote.getPolygonesPlan());
        }

        return polygones;
    }

    public Cote getCote(Utilitaire.Direction direction){

        for ( Cote cote : cotes){
            if (cote.mDirection.equals(direction)) {
                return cote;
            }
        }
    return null;
    }


}
