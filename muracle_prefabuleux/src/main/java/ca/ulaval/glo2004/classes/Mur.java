package ca.ulaval.glo2004.classes;

import jdk.tools.jlink.internal.Platform;
import sun.nio.ch.Util;

import java.awt.*;
import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.Vector;

import ca.ulaval.glo2004.classes.Porte;
import ca.ulaval.glo2004.classes.Accessoire;

public class Mur extends Element implements Serializable {


    int uniqueID;
    Cote mCote;
    boolean exterieur;
    Salle mSalle;
    Polygone mPolygoneElevation;
    Polygone mPolygonePlan;
    ArrayList<ArrayList<Polygone>> polygoneElevationDecoupage;
    Imperial mLargeur;

    boolean retourAir;
    Imperial largeurRetourAir;
    Polygone mPolygoneElevationRetourAir;
    Polygone mPolygonePlanRetourAir;

    boolean interieurTropLourd = false;
    boolean exterieurTropLourd = false;

    public Mur(Salle mSalle, Cote mCote, Imperial y, Imperial x, Imperial largeur) {
        super(y, x);
        this.mLargeur = largeur;

        this.mCote = mCote;
        this.mSalle = mSalle;

        this.retourAir = false;
        this.largeurRetourAir = new Imperial(15);

        interieurTropLourd = !estAssezLeger(true);
        exterieurTropLourd = !estAssezLeger(false);
        //genererPolygonePlan();
        //genererPolygoneELV(this.getExterieur());
    }

    public Imperial getmLargeur() {
        return mLargeur;
    }

    public void setmLargeur(Imperial mLargeur) {
        this.mLargeur = mLargeur;
    }


    public ArrayList<Double> getPolygonePlanCoins()
    {
        return mPolygonePlan.getCoinsDouble();
    }

    public boolean PointEstDansMur(PointImperial point) {
        ArrayList<Double> coins = getPolygonePlanCoins();

        return point.mX.getFormeNormal() >= coins.get(0) && point.mX.getFormeNormal() <= coins.get(1) &&
                point.mY.getFormeNormal() >= coins.get(2) && point.mY.getFormeNormal() <= coins.get(3);
    }

    public void calculerDisposition() {

    }

    public Polygone polygonesElevation(boolean interieur) {
        return mPolygoneElevation;
    }

    public ArrayList<Accessoire> accessoires() {

        ArrayList<Accessoire> listAccessoires = new ArrayList<>();
        this.genererPolygoneELV(false);
        this.genererPolygoneRetourAirELV(false);
        Imperial xDebut= this.mPolygoneElevation.getPoints().get(0).mX;
        Imperial xFin=this.mPolygoneElevation.getPoints().get(2).mX;
        Imperial yDebut=this.mPolygoneElevation.getPoints().get(0).mY;
        Imperial yFin=this.mPolygoneElevation.getPoints().get(2).mY;

        for (Accessoire accessoire : mCote.accessoires)
        {
            Imperial xDebutAccessoire= accessoire.mPolygoneElevation.getPoints().get(0).mX;
            Imperial xFinAccessoire= accessoire.mPolygoneElevation.getPoints().get(2).mX;
            Imperial yDebutAccessoire= accessoire.mPolygoneElevation.getPoints().get(1).mY;
            Imperial yFinAccessoire= accessoire.mPolygoneElevation.getPoints().get(3).mY;
            if(xDebut.getValue() <= xDebutAccessoire.getValue()
                    && yDebut.getValue() <= yDebutAccessoire.getValue()
                    && xFin.getValue() >=xFinAccessoire.getValue()
                    && yFin.getValue() >=yFinAccessoire.getValue()){
                listAccessoires.add(accessoire);
            }
        }

        return listAccessoires;
    }

    public Accessoire getaccessoire(PointImperial point) {

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

    public void genererPolygones()
    {
        genererPolygonePlan();
        genererPolygonePlanRetourAir();
        //genererPolygoneELV(exterieur);
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
        else if(mCote.mDirection == Utilitaire.Direction.OUEST)
        {
            if(estPremierMur)
                p1.mY = p1.mY.add(mSalle.epaisseurMurs.negative());
            if(estDernierMur)
                p2.mY = p2.mY.add(mSalle.epaisseurMurs);
        }
        else if(mCote.mDirection == Utilitaire.Direction.EST)
        {
            if(estPremierMur)
                p4.mY = p4.mY.add(mSalle.epaisseurMurs.negative());
            if(estDernierMur)
                p3.mY = p3.mY.add(mSalle.epaisseurMurs);
        }

        boolean selectionne = this.mSalle.ElementSelectionne == this;
        this.mPolygonePlan = new Polygone(getCouleurPolygone(), p1, p2, p3, p4);
    }

    public void genererPolygonePlanRetourAir()
    {
        Imperial x1, x2, y1, y2;

        if(mCote.getDirection().estHorizontal())
        {
            Imperial xCentre = super.mX.add(mLargeur.divide(2));
            Imperial yCentre = super.mY.add(mSalle.getEpaisseurMurs().divide(2));

            x1 = xCentre.substract(largeurRetourAir.divide(2));
            x2 = x1.add(largeurRetourAir);
            y1 = yCentre.substract(mSalle.getHauteurTrouRetourAir().divide(2));
            y2 = y1.add(mSalle.getHauteurTrouRetourAir());
        }
        else
        {
            Imperial xCentre = super.mX.add(mSalle.getEpaisseurMurs().divide(2));
            Imperial yCentre = super.mY.add(mLargeur.divide(2));

            x1 = xCentre.substract(mSalle.getHauteurTrouRetourAir().divide(2));
            x2 = x1.add(mSalle.getHauteurTrouRetourAir());
            y1 = yCentre.substract(largeurRetourAir.divide(2));
            y2 = y1.add(largeurRetourAir);
        }

        PointImperial p1 = new PointImperial(x1, y1);
        PointImperial p2 = new PointImperial(x1, y2);
        PointImperial p3 = new PointImperial(x2, y2);
        PointImperial p4 = new PointImperial(x2, y1);

        boolean selectionne = this.mSalle.ElementSelectionne == this;
        this.mPolygonePlanRetourAir = new Polygone(getCouleurPolygone(), p1, p2, p3, p4);
    }


    public void genererPolygoneELV(boolean exterieur) {
        Imperial x1;
        Imperial y1;
        Imperial x2;
        Imperial y2;
        if (mCote.mDirection.equals(Utilitaire.Direction.NORD) || mCote.mDirection.equals(Utilitaire.Direction.SUD)) {
            x1 = super.mX;
            y1 = new Imperial(0);
        } else {
            x1 = super.mY.substract(mSalle.getEpaisseurMurs());
            y1 = new Imperial(0);
        }

        x2 = x1.add(mLargeur);
        y2 = y1.add(mSalle.getHauteur());

        if (!exterieur && this.equals(mCote.getPremierMur()) && mCote.murs.size() > 1) {

            Imperial epaisseurMur = new Imperial(mSalle.epaisseurMurs.entier, mSalle.epaisseurMurs.numerateur, mSalle.epaisseurMurs.denominateur);
            Imperial newLargeur = new Imperial(mLargeur.entier, mLargeur.numerateur, mLargeur.denominateur);
            newLargeur = newLargeur.add(epaisseurMur.negative());

            x1 = x1.add(epaisseurMur);
            x2 = x1.add(newLargeur);
            //TODO retirer epaisseur du mur à la largeur et appliquer au point
        }

        if (!exterieur && this.equals(mCote.getDernierMur()) && mCote.murs.size() > 1) {
            //TODO retirer epaisseur du mur a la largeur et appliquer au point
            Imperial epaisseurMur = new Imperial(mSalle.epaisseurMurs.entier, mSalle.epaisseurMurs.numerateur, mSalle.epaisseurMurs.denominateur);
            Imperial newLargeur = new Imperial(mLargeur.entier, mLargeur.numerateur, mLargeur.denominateur);
            newLargeur = newLargeur.add(epaisseurMur.negative());

            x2 = x1.add(newLargeur);
        }

        if (!exterieur && mCote.murs.size() == 1) {
            //TODO retirer 2x l'épaisseur de la largeur
            Imperial epaisseurMur = new Imperial(mSalle.epaisseurMurs.entier, mSalle.epaisseurMurs.numerateur, mSalle.epaisseurMurs.denominateur);
            Imperial newLargeur = new Imperial(mLargeur.entier, mLargeur.numerateur, mLargeur.denominateur);
            Imperial epaisseurMurDouble = new Imperial(epaisseurMur.entier * 2);
            newLargeur = newLargeur.add(epaisseurMurDouble.negative());


            x1 = x1.add(epaisseurMur);
            x2 = x1.add(newLargeur);
        }

        if (exterieur) {

            if (mCote.mDirection.equals(Utilitaire.Direction.NORD) || mCote.mDirection.equals(Utilitaire.Direction.SUD)) {

                Imperial xSoustrait = new Imperial(mSalle.largeur.entier, mSalle.largeur.numerateur, mSalle.largeur.denominateur);
                x1 = x1.substract(xSoustrait).abs();
                x2 = x2.substract(xSoustrait).abs();


                //TODO faire un miroir sur le coté au complet a voir
            }
            else {
                Imperial xSoustrait = new Imperial(mSalle.profondeur.entier, mSalle.profondeur.numerateur, mSalle.profondeur.denominateur);
                x1 = x1.substract(xSoustrait).abs();
                x2 = x2.substract(xSoustrait).abs();
            }
        }

        boolean selectionne = this.mSalle.ElementSelectionne == this;
        this.mPolygoneElevation = new Polygone(getCouleurPolygone(), new PointImperial(x1,y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));
    }

    public void genererPolygoneRetourAirELV(boolean exterieur) {
        Imperial xCentre;

        if(mCote.getDirection().estHorizontal())
            xCentre = super.mX.add(mLargeur.divide(2));
        else
            xCentre = super.mY.add(mLargeur.divide(2));

        Imperial x1 = xCentre.substract(largeurRetourAir.divide(2));
        Imperial x2 = x1.add(largeurRetourAir.clone());
        Imperial y2 = mSalle.getHauteur().substract(mSalle.getPositionRetourAir());
        Imperial y1 = y2.substract(mSalle.getHauteurRetourAir());

        if(exterieur)
        {
            if (mCote.mDirection.equals(Utilitaire.Direction.NORD) || mCote.mDirection.equals(Utilitaire.Direction.SUD)) {

                Imperial xSoustrait = new Imperial(mSalle.largeur.entier, mSalle.largeur.numerateur, mSalle.largeur.denominateur);
                x1 = x1.substract(xSoustrait).abs();
                x2 = x2.substract(xSoustrait).abs();
            }
            else {
                Imperial xSoustrait = new Imperial(mSalle.profondeur.entier, mSalle.profondeur.numerateur, mSalle.profondeur.denominateur);
                x1 = x1.substract(xSoustrait).abs();
                x2 = x2.substract(xSoustrait).abs();
            }
        }

        PointImperial p1 = new PointImperial(x1, y1);
        PointImperial p2 = new PointImperial(x1, y2);
        PointImperial p3 = new PointImperial(x2, y2);
        PointImperial p4 = new PointImperial(x2, y1);

        boolean selectionne = this.mSalle.ElementSelectionne == this;
        this.mPolygoneElevationRetourAir = new Polygone(selectionne ? Color.BLUE : Color.BLACK, p1, p2, p3, p4);
    }

    private Polygone createPolygone(Imperial hauteur, Imperial largeur, Imperial distanceSeparationHauteur,Imperial distanceSeparationLargeur,Color color,int... pointiller){
        PointImperial mX1Debut= new PointImperial(distanceSeparationLargeur,distanceSeparationHauteur);
        PointImperial mX1Fin= new PointImperial(distanceSeparationLargeur,hauteur);
        PointImperial mX2Debut=new PointImperial(largeur,hauteur);
        PointImperial mX2Fin=new PointImperial(largeur,distanceSeparationHauteur);
        ArrayList<PointImperial> listPoint = new ArrayList<>();
        listPoint.add(mX1Debut);
        listPoint.add(mX1Fin);
        listPoint.add(mX2Debut);
        listPoint.add(mX2Fin);

        Polygone polygone = new Polygone(color,listPoint);

        for(int i : pointiller)
            polygone.setLignePointille(i);

        return polygone;
    }
    private Polygone createPolygoneTriagualaire(Imperial hauteur, Imperial largeur, Imperial distanceSeparationHauteur,Imperial distanceSeparationLargeur,Color color,int pointiller, String direction){
        ArrayList<PointImperial> listPoint = new ArrayList<>();
        PointImperial mX1Debut;
        PointImperial mX1Fin;
        PointImperial mX2Debut;
        PointImperial mX2Fin;
        switch (direction){
            case "NW":
                mX1Debut= new PointImperial(distanceSeparationLargeur,distanceSeparationHauteur);
                mX2Debut=new PointImperial(largeur,hauteur);
                mX2Fin =new PointImperial(largeur,distanceSeparationHauteur);
                listPoint.add(mX1Debut);
                listPoint.add(mX2Debut);
                listPoint.add(mX2Fin);
                break;
            case "NE":
                mX1Debut= new PointImperial(distanceSeparationLargeur,distanceSeparationHauteur);
                mX1Fin= new PointImperial(distanceSeparationLargeur,hauteur);
                mX2Fin=new PointImperial(largeur,distanceSeparationHauteur);
                listPoint.add(mX1Debut);
                listPoint.add(mX1Fin);
                listPoint.add(mX2Fin);
                break;
            case "SW":
                mX1Fin= new PointImperial(distanceSeparationLargeur,hauteur);
                mX2Debut=new PointImperial(largeur,hauteur);
                mX2Fin=new PointImperial(largeur,distanceSeparationHauteur);
                listPoint.add(mX1Fin);
                listPoint.add(mX2Debut);
                listPoint.add(mX2Fin);
                break;
            case "SE":
                mX1Debut= new PointImperial(distanceSeparationLargeur,distanceSeparationHauteur);
                mX1Fin= new PointImperial(distanceSeparationLargeur,hauteur);
                mX2Debut=new PointImperial(largeur,hauteur);
                listPoint.add(mX1Debut);
                listPoint.add(mX1Fin);
                listPoint.add(mX2Debut);
                break;
        }

        return new Polygone(color,listPoint);
    }
    public ArrayList<ArrayList<Polygone>> genererpolygonesElevationDecoupage() {
        polygoneElevationDecoupage = new ArrayList<>();
        boolean exterieur = true;
        ArrayList<Accessoire> accessoires = accessoires();
        double epaisseurMursDouble = this.mSalle.getEpaisseurMurs().getValue();
        double hypotenuse = Math.sqrt(2*(epaisseurMursDouble * epaisseurMursDouble));
        do{
            ArrayList<Polygone> polygoneMurDecoupage = new ArrayList<>();
            if(!exterieur){
                Imperial defaultDistance;
                Imperial pliSoudureAvant = this.mSalle.getLargeurPliSoudure();
                Imperial pliSoudureApres = this.mSalle.getLargeurPliSoudure();
                if(this == this.getCote().getPremierMur() && this == this.getCote().getDernierMur()){
                    defaultDistance = new Imperial(0);
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            defaultDistance,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.GREEN,
                            1
                    ));
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getEpaisseurMurs());
                    polygoneMurDecoupage.add(createPolygoneTriagualaire(
                            pliSoudureApres,
                            this.mSalle.epaisseurMurs.add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.pink,
                            1,
                            "NW"
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.blue,
                            1, 3
                    ));
                    polygoneMurDecoupage.add(createPolygoneTriagualaire(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.pink,
                            1,
                            "NE"
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getHauteur());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.BLACK,
                            1, 3
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getEpaisseurMurs());
                    polygoneMurDecoupage.add(createPolygoneTriagualaire(
                            pliSoudureApres,
                            this.mSalle.epaisseurMurs.add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.pink,
                            1,
                            "SW"
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.blue,
                            1, 3
                    ));
                    polygoneMurDecoupage.add(createPolygoneTriagualaire(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.pink,
                            1,
                            "SE"
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getLargeurPliSoudure());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.GREEN,
                            3
                    ));

                    if(this.retourAir){
                        Imperial y2 = mSalle.getHauteur().substract(mSalle.getPositionRetourAir());
                        Imperial y1 = y2.substract(mSalle.getHauteurRetourAir());
                        polygoneMurDecoupage.add(createPolygone(
                                this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(mSalle.getPositionRetourAir()).substract(mSalle.getPositionRetourAir()),
                                this.getmLargeur().divide(2).add(largeurRetourAir.divide(2)),
                                this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(mSalle.getPositionRetourAir()),
                                this.getmLargeur().divide(2).substract(largeurRetourAir.divide(2)),
                                Color.black
                        ));
                    }
                }
                else if(this == this.getCote().getPremierMur()){
                    defaultDistance = new Imperial(0);
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            defaultDistance,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.GREEN,
                            1
                    ));
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getEpaisseurMurs());
                    polygoneMurDecoupage.add(createPolygoneTriagualaire(
                            pliSoudureApres,
                            this.mSalle.epaisseurMurs.add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.pink,
                            1,
                            "NW"
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.blue,
                            1, 3
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getHauteur());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.BLACK,
                            1, 3
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getEpaisseurMurs());
                    polygoneMurDecoupage.add(createPolygoneTriagualaire(
                            pliSoudureApres,
                            this.mSalle.epaisseurMurs.add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.pink,
                            1,
                            "SW"
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.blue,
                            1, 3
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getLargeurPliSoudure());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.GREEN,
                            3
                    ));

                    if(this.retourAir){
                        Imperial y2 = mSalle.getHauteur().substract(mSalle.getPositionRetourAir());
                        Imperial y1 = y2.substract(mSalle.getHauteurRetourAir());
                        polygoneMurDecoupage.add(createPolygone(
                                this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(mSalle.getPositionRetourAir()).substract(mSalle.getPositionRetourAir()),
                                this.getmLargeur().add(this.mSalle.getEpaisseurMurs()).divide(2).add(largeurRetourAir.divide(2)),
                                this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(mSalle.getPositionRetourAir()),
                                this.getmLargeur().add(this.mSalle.getEpaisseurMurs()).divide(2).substract(largeurRetourAir.divide(2)),
                                Color.black
                        ));
                    }
                }
                else if(this == this.getCote().getDernierMur()){
                    defaultDistance = new Imperial(0);
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            defaultDistance,
                            defaultDistance.add(new Imperial(1)),
                            Color.GREEN,
                            1
                    ));
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getEpaisseurMurs());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.blue,
                            1, 3
                    ));
                    polygoneMurDecoupage.add(createPolygoneTriagualaire(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.pink,
                            1,
                            "NE"
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getHauteur());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.BLACK,
                            1, 3
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getEpaisseurMurs());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.blue,
                            1, 3
                    ));
                    polygoneMurDecoupage.add(createPolygoneTriagualaire(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            Color.pink,
                            1,
                            "NE"
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getLargeurPliSoudure());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().substract(this.mSalle.epaisseurMurs).add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.GREEN,
                            3
                    ));
                    if(this.retourAir){
                        Imperial y2 = mSalle.getHauteur().substract(mSalle.getPositionRetourAir());
                        Imperial y1 = y2.substract(mSalle.getHauteurRetourAir());
                        polygoneMurDecoupage.add(createPolygone(
                                this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(mSalle.getPositionRetourAir()).substract(mSalle.getPositionRetourAir()),
                                this.getmLargeur().substract(this.mSalle.getEpaisseurMurs()).divide(2).add(largeurRetourAir.divide(2)),
                                this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(mSalle.getPositionRetourAir()),
                                this.getmLargeur().substract(this.mSalle.getEpaisseurMurs()).divide(2).substract(largeurRetourAir.divide(2)),
                                Color.black
                        ));
                    }
                }
                else{
                    defaultDistance = new Imperial(0);
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            defaultDistance,
                            defaultDistance.add(new Imperial(1)),
                            Color.GREEN,
                            1
                    ));
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getEpaisseurMurs());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.blue,
                            1, 3
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getHauteur());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.BLACK,
                            1, 3
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getEpaisseurMurs());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.blue,
                            1, 3
                    ));
                    pliSoudureAvant = pliSoudureApres;
                    pliSoudureApres = pliSoudureApres.add(this.mSalle.getLargeurPliSoudure());
                    polygoneMurDecoupage.add(createPolygone(
                            pliSoudureApres,
                            this.getmLargeur().add(new Imperial(1)),
                            pliSoudureAvant,
                            defaultDistance.add(new Imperial(1)),
                            Color.GREEN,
                            3
                    ));


                    if(this.retourAir){
                        Imperial y2 = mSalle.getHauteur().substract(mSalle.getPositionRetourAir());
                        Imperial y1 = y2.substract(mSalle.getHauteurRetourAir());
                        polygoneMurDecoupage.add(createPolygone(
                                this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(mSalle.getPositionRetourAir()).substract(mSalle.getPositionRetourAir()),
                                this.getmLargeur().divide(2).add(largeurRetourAir.divide(2)),
                                this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(mSalle.getPositionRetourAir()),
                                this.getmLargeur().divide(2).substract(largeurRetourAir.divide(2)),
                                Color.black
                        ));
                    }
                }
                for(Accessoire accessoire: accessoires) {
                    if(getCote().getDirection() == Utilitaire.Direction.NORD || getCote().getDirection() == Utilitaire.Direction.SUD){
                        if(accessoire instanceof Porte){
                            polygoneMurDecoupage.add(createPolygone(
                                    this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()),
                                    accessoire.getmX().substract(this.mX).add(new Imperial(1)).add(accessoire.getmLargeur()),
                                    this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(accessoire.getmHauteur()),
                                    accessoire.getmX().substract(this.mX).add(new Imperial(1)),
                                    Color.BLACK
                            ));
                        }
                        else{
                            polygoneMurDecoupage.add(createPolygone(
                                    accessoire.getmY().add(accessoire.getmHauteur()).add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()),
                                    accessoire.getmX().substract(this.mX).add(accessoire.getmLargeur()).add(new Imperial(1)),
                                    accessoire.getmY().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()),
                                    accessoire.getmX().substract(this.mX).add(new Imperial(1)),
                                    Color.black
                            ));
                        }
                    }
                    else{
                        if(accessoire instanceof Porte){
                            polygoneMurDecoupage.add(createPolygone(
                                    this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()),
                                    accessoire.getmX().substract(this.mY).add(new Imperial(1)).add(accessoire.getmLargeur()).add(this.mSalle.getEpaisseurMurs()),
                                    this.mSalle.getHauteur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).substract(accessoire.getmHauteur()),
                                    accessoire.getmX().substract(this.mY).add(new Imperial(1)).add(this.mSalle.getEpaisseurMurs()),
                                    Color.BLACK
                            ));
                        }
                        else{
                            polygoneMurDecoupage.add(createPolygone(
                                    accessoire.getmY().add(accessoire.getmHauteur()).add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()),
                                    accessoire.getmX().substract(this.mY).add(accessoire.getmLargeur()).add(new Imperial(1)).add(this.mSalle.getEpaisseurMurs()),
                                    accessoire.getmY().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()),
                                    accessoire.getmX().substract(this.mY).add(new Imperial(1)).add(this.mSalle.getEpaisseurMurs()),
                                    Color.black
                            ));
                        }
                    }
                }
                exterieur = true;
            }
            else{
                Imperial defaultDistance = new Imperial(0);
                Imperial defaultDistanceLargeur;
                Imperial largeur = new Imperial(6);
                Imperial hypotenuseImp;
                if(this == this.getCote().getPremierMur() && this == this.getCote().getDernierMur()){
                    hypotenuseImp = Imperial.fromValue(hypotenuse);
                    defaultDistanceLargeur = this.getmLargeur().add(hypotenuse < largeur.getValue()? largeur : Imperial.fromValue(hypotenuse));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur),
                            defaultDistance,
                            defaultDistanceLargeur,
                            Color.GREEN,
                            2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur).add(Imperial.fromValue(hypotenuse)),
                            defaultDistance,
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur),
                            Color.pink,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(Imperial.fromValue(hypotenuse)).add(defaultDistanceLargeur),
                            defaultDistance,
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur).add(Imperial.fromValue(hypotenuse)),
                            Color.black,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(Imperial.fromValue(hypotenuse)).add(Imperial.fromValue(hypotenuse)),
                            defaultDistance,
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(Imperial.fromValue(hypotenuse)).add(defaultDistanceLargeur),
                            Color.PINK,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(this.mSalle.getLargeurPliSoudure()).add(Imperial.fromValue(hypotenuse)).add(Imperial.fromValue(hypotenuse)),
                            defaultDistance,
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(Imperial.fromValue(hypotenuse)).add(Imperial.fromValue(hypotenuse)),
                            Color.GREEN,
                            0
                    ));

                }
                else if(this == this.getCote().getPremierMur()){
                    defaultDistanceLargeur = this.getmLargeur().add(hypotenuse < largeur.getValue()? largeur : Imperial.fromValue(hypotenuse));
                    hypotenuseImp = Imperial.fromValue(hypotenuse);
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur),
                            defaultDistance,
                            defaultDistanceLargeur,
                            Color.GREEN,
                            2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur).add(Imperial.fromValue(hypotenuse)),
                            defaultDistance,
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur),
                            Color.pink,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(Imperial.fromValue(hypotenuse)).add(defaultDistanceLargeur),
                            defaultDistance,
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur).add(Imperial.fromValue(hypotenuse)),
                            Color.black,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(Imperial.fromValue(hypotenuse)).add(this.mSalle.getEpaisseurMurs()),
                            defaultDistance,
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(Imperial.fromValue(hypotenuse)).add(defaultDistanceLargeur),
                            Color.BLUE,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(this.mSalle.getLargeurPliSoudure()).add(Imperial.fromValue(hypotenuse)).add(this.mSalle.getEpaisseurMurs()),
                            defaultDistance,
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(Imperial.fromValue(hypotenuse)).add(this.mSalle.getEpaisseurMurs()),
                            Color.GREEN,
                            0
                    ));
                }
                else if(this == this.getCote().getDernierMur()){
                    hypotenuseImp = this.mSalle.getEpaisseurMurs();
                    defaultDistanceLargeur = this.getmLargeur().add(hypotenuse < largeur.getValue()? largeur : Imperial.fromValue(hypotenuse));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur),
                            defaultDistance,
                            defaultDistanceLargeur,
                            Color.GREEN,
                            2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur).add(this.mSalle.getEpaisseurMurs()),
                            defaultDistance,
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur),
                            Color.BLUE,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).add(defaultDistanceLargeur),
                            defaultDistance,
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur).add(this.mSalle.getEpaisseurMurs()),
                            Color.black,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(this.mSalle.getEpaisseurMurs()).add(Imperial.fromValue(hypotenuse)),
                            defaultDistance,
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).add(defaultDistanceLargeur),
                            Color.pink,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).add(Imperial.fromValue(hypotenuse)),
                            defaultDistance,
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(this.mSalle.getEpaisseurMurs()).add(Imperial.fromValue(hypotenuse)),
                            Color.GREEN,
                            0
                    ));
                }
                else{
                    defaultDistanceLargeur = this.getmLargeur().add(largeur);
                    hypotenuseImp = this.mSalle.getEpaisseurMurs();
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur),
                            defaultDistance,
                            defaultDistanceLargeur,
                            Color.GREEN,
                            2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur).add(this.mSalle.getEpaisseurMurs()),
                            defaultDistance,
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur),
                            Color.BLUE,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).add(defaultDistanceLargeur),
                            defaultDistance,
                            this.mSalle.getLargeurPliSoudure().add(defaultDistanceLargeur).add(this.mSalle.getEpaisseurMurs()),
                            Color.black,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(this.mSalle.getEpaisseurMurs()).add(this.mSalle.getEpaisseurMurs()),
                            defaultDistance,
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).add(defaultDistanceLargeur),
                            Color.BLUE,
                            0, 2
                    ));
                    polygoneMurDecoupage.add(createPolygone(
                            this.mSalle.getHauteur(),
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).add(this.mSalle.getEpaisseurMurs()),
                            defaultDistance,
                            this.getmLargeur().add(this.mSalle.getLargeurPliSoudure()).add(defaultDistanceLargeur).add(this.mSalle.getEpaisseurMurs()).add(this.mSalle.getEpaisseurMurs()),
                            Color.GREEN,
                            0
                    ));
                }
                for(Accessoire accessoire: accessoires) {
                    if(getCote().getDirection() == Utilitaire.Direction.NORD || getCote().getDirection() == Utilitaire.Direction.SUD){
                        if(accessoire instanceof Porte){
                            polygoneMurDecoupage.add(createPolygone(
                                    this.mSalle.getHauteur(),
                                    defaultDistanceLargeur.add(accessoire.getmX().substract(this.mX).add(this.mSalle.getLargeurPliSoudure()).add(hypotenuseImp).add(new Imperial(1)).add(accessoire.getmLargeur())),
                                    this.mSalle.getHauteur().substract(accessoire.getmHauteur()),
                                    defaultDistanceLargeur.add(accessoire.getmX().substract(this.mX).add(this.mSalle.getLargeurPliSoudure()).add(hypotenuseImp).add(new Imperial(1))),
                                    Color.black
                            ));
                        }
                        else if(accessoire instanceof PrisesElectrique){}
                        else{
                            polygoneMurDecoupage.add(createPolygone(
                                    accessoire.getmY().add(accessoire.getmHauteur()),
                                    defaultDistanceLargeur.add(accessoire.getmX().add(this.mSalle.getLargeurPliSoudure().add(hypotenuseImp)).add(accessoire.getmLargeur()).add(new Imperial(1))),
                                    accessoire.getmY(),
                                    defaultDistanceLargeur.add(accessoire.getmX().add(this.mSalle.getLargeurPliSoudure().add(hypotenuseImp)).add(new Imperial(1))),
                                    Color.black
                            ));
                        }
                    }else{
                        if(accessoire instanceof Porte){
                            polygoneMurDecoupage.add(createPolygone(
                                    this.mSalle.getHauteur(),
                                    defaultDistanceLargeur.add(accessoire.getmX().substract(this.mY).add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).add(hypotenuseImp).add(new Imperial(1)).add(accessoire.getmLargeur())),
                                    this.mSalle.getHauteur().substract(accessoire.getmHauteur()),
                                    defaultDistanceLargeur.add(accessoire.getmX().substract(this.mY).add(this.mSalle.getLargeurPliSoudure()).add(this.mSalle.getEpaisseurMurs()).add(hypotenuseImp).add(new Imperial(1))),
                                    Color.black
                            ));
                        }
                        else if(accessoire instanceof PrisesElectrique){}
                        else{
                            polygoneMurDecoupage.add(createPolygone(
                                    accessoire.getmY().add(accessoire.getmHauteur()),
                                    defaultDistanceLargeur.add(accessoire.getmX().substract(this.mY).add(this.mSalle.getLargeurPliSoudure().add(this.mSalle.getEpaisseurMurs()).add(hypotenuseImp)).add(accessoire.getmLargeur()).add(new Imperial(1))),
                                    accessoire.getmY(),
                                    defaultDistanceLargeur.add(accessoire.getmX().substract(this.mY).add(this.mSalle.getLargeurPliSoudure().add(this.mSalle.getEpaisseurMurs()).add(hypotenuseImp)).add(new Imperial(1))),
                                    Color.black
                            ));
                        }
                    }
                }
                exterieur =false;
            }
            if(!estAssezLeger(!exterieur)){
                for(Polygone polygone: polygoneMurDecoupage){
                    polygone.setCouleur(Color.red);
                }
            }
            polygoneElevationDecoupage.add(polygoneMurDecoupage);
        }while(!exterieur);
        return polygoneElevationDecoupage;
    }
    public ArrayList<Polygone> getPolygoneAccessoires(boolean exterieur){
        ArrayList<Polygone> polygonesAccessoires = new ArrayList<>();

        for(Accessoire accessoire : mCote.accessoires){
                polygonesAccessoires.addAll(accessoire.genererPolygoneELV(exterieur));
            }
        return polygonesAccessoires;
        };


    public Mur copieMur(){
        Mur copieMur = new Mur(mSalle, mCote, getmY(), getmX(), getmLargeur());
        return copieMur;
    }

    public double calculerPoidsPanneauInterieur() {
        double air = this.mLargeur.getFormeNormal() * this.mCote.hauteur();
        air += this.mCote.mSalle.epaisseurMurs.getFormeNormal() * this.mLargeur.getFormeNormal() * 2;
        air += this.mCote.mSalle.largeurPliSoudure.getFormeNormal() * this.mLargeur.getFormeNormal() * 2;
        ArrayList<Accessoire> accessoireList = accessoires();
        if(this.retourAir){
            ArrayList<Double> points = this.mPolygonePlanRetourAir.getCoinsDouble();
            double largeur = points.get(1) - points.get(0);
            double hauteur = points.get(3) - points.get(2);
            air -= largeur * hauteur;
        }
        for (Accessoire accessoire: accessoireList) {
            air -= accessoire.getmHauteur().getFormeNormal() * accessoire.mLargeur.getFormeNormal();
        }
        System.out.println(air);
        return air;
    }

    public double calculerPoidsPanneauInterieurExtremite() {
        double air = (this.mLargeur.getFormeNormal()-this.mCote.mSalle.epaisseurMurs.getFormeNormal())  * this.mCote.hauteur();
        air += this.mCote.mSalle.epaisseurMurs.getFormeNormal() * this.mLargeur.getFormeNormal() * 2;
        air += this.mCote.mSalle.largeurPliSoudure.getFormeNormal() * this.mLargeur.getFormeNormal() * 2;
        ArrayList<Accessoire> accessoireList = accessoires();
        if(this.retourAir){
            ArrayList<Double> points = this.mPolygonePlanRetourAir.getCoinsDouble();
            double largeur = points.get(1) - points.get(0);
            double hauteur = points.get(3) - points.get(2);
            air -= largeur * hauteur;
        }


        for (Accessoire accessoire: accessoireList) {
            air -= accessoire.getmHauteur().getFormeNormal() * accessoire.mLargeur.getFormeNormal();
        }
        return air;
    }

    public double calculerPoidsPanneauExterieur() {
        double air = this.mLargeur.getFormeNormal() * this.mCote.hauteur();
        air += this.mCote.mSalle.epaisseurMurs.getFormeNormal() * this.mCote.mSalle.hauteur.getFormeNormal() * 2;
        air += this.mCote.mSalle.largeurPliSoudure.getFormeNormal() * this.mCote.mSalle.hauteur.getFormeNormal() * 2;
        ArrayList<Accessoire> accessoireList = accessoires();
        for (Accessoire accessoire: accessoireList) {
            if(accessoire.mPerceExtérieur){
                air -= accessoire.getmHauteur().getFormeNormal() * accessoire.mLargeur.getFormeNormal();
            }
        }
        System.out.println(air);
        return air;
    }

    public double calculerPoidsPanneauExterieurExtremite() {
        double air = this.mLargeur.getFormeNormal() * this.mCote.hauteur();
        air += this.mCote.mSalle.epaisseurMurs.getFormeNormal() * this.mCote.mSalle.hauteur.getFormeNormal() * 2;
        air += this.mCote.mSalle.largeurPliSoudure.getFormeNormal() * this.mCote.mSalle.hauteur.getFormeNormal() * 2;
        double hypothenuse = Math.sqrt(((Math.pow(this.mCote.mSalle.epaisseurMurs.getFormeNormal(),2))*2));
        air += hypothenuse * this.mCote.mSalle.hauteur.getFormeNormal();
        ArrayList<Accessoire> accessoireList = accessoires();
        for (Accessoire accessoire: accessoireList) {
            if(accessoire.mPerceExtérieur){
                air -= accessoire.getmHauteur().getFormeNormal() * accessoire.mLargeur.getFormeNormal();
            }
        }
        System.out.println(air);
        return air;
    }

    public void setExterieur(boolean exterieur) {
        this.exterieur = exterieur;
    }

    public boolean aRetourAir() {
        return retourAir;
    }

    public boolean getExterieur() {
        return this.exterieur;
    }

    public void setRetourAir(boolean retourAir) {
        this.retourAir = retourAir;
    }

    public Imperial getLargeurRetourAir() {
        return largeurRetourAir;
    }

    public void setLargeurRetourAir(Imperial largeurRetourAir) {
        this.largeurRetourAir = largeurRetourAir;
    }

    public void setRetourAirePolygone(Polygone polygoneRetourAir){
        this.mPolygonePlanRetourAir = polygoneRetourAir;
    }
    public void setRetourAirePolygoneElv(Polygone polygoneRetourAir){
        this.mPolygonePlanRetourAir = polygoneRetourAir;
    }
    public Polygone getPolygonePlanRetourAir(){
        return this.mPolygonePlanRetourAir;
    }
    public Polygone getPolygoneElvRetourAir(){
        return this.mPolygoneElevationRetourAir;
    }
    public Polygone getPolygonePlan(){
        return this.mPolygonePlan;
    }

    public Cote getCote() {
        return mCote;
    }

    public boolean estAssezLeger(boolean panneauInterieur)
    {
        return calculerPoids(panneauInterieur) <= mCote.getmSalle().getPoidsMaxPanneau();
    }

    public double calculerPoids(boolean panneauInterieur)
    {
        double air;

        boolean estExtremite = getCote().getPremierMur() == this || getCote().getDernierMur() == this;

        if(panneauInterieur)
            air = estExtremite ? calculerPoidsPanneauInterieurExtremite() : calculerPoidsPanneauInterieur();
        else
            air = estExtremite ? calculerPoidsPanneauExterieurExtremite() : calculerPoidsPanneauExterieur();

        return air * mCote.getmSalle().getPoidsParPouce();
    }

    public Color getCouleurPolygone()
    {
        boolean selectionne = this.mSalle.ElementSelectionne == this;
        boolean estTropLourd = interieurTropLourd || exterieurTropLourd;

        if(selectionne)
            return Color.BLUE;
        else if(estTropLourd)
            return Color.RED;
        else
            return Color.BLACK;
    }
}

