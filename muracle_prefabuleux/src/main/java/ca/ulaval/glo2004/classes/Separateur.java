package ca.ulaval.glo2004.classes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Separateur extends Element implements Serializable, Comparable<Separateur> {

    Imperial distanceBordDeReference ;

    Cote mCote ;

    Polygone mPolygonePlan;

    Polygone mPolygoneElevation;

    public Separateur(Imperial mY, Imperial mX, Imperial distanceBordDeReference, Cote mCote) {
        this(mY, mX, distanceBordDeReference, mCote, null);
    }

    public Separateur(Imperial mY, Imperial mX, Imperial distanceBordDeReference, Cote mCote, Polygone mPolygonePlan) {
        super(mY, mX);
        this.distanceBordDeReference = distanceBordDeReference;
        this.mCote = mCote;
        this.mPolygonePlan = mPolygonePlan;
    }

    public ArrayList<Polygone> getPolygones() {
        return null;
    }

    public Imperial getDistanceBordDeReference() {
        return distanceBordDeReference;
    }

    public void setDistanceBordDeReference(Imperial distanceBordDeReference) {
        this.distanceBordDeReference = distanceBordDeReference;
    }

    public Cote getmCote() {
        return mCote;
    }

    public void setmCote(Cote mCote) {
        this.mCote = mCote;
    }

    public Polygone getmPolygonePlan() {
        return mPolygonePlan;
    }

    public void setmPolygonePlan(Polygone mPolygonePlan) {
        this.mPolygonePlan = mPolygonePlan;
    }

    @Override
    public void calculeDisposition() {
        super.calculeDisposition();
    }

    public Separateur getSeparateurPrecedent()
    {
        return mCote.getSeparateurPrecedent(this);
    }

    public Separateur getSeparateurSuivant()
    {
        return mCote.getSeparateurSuivant(this);
    }

    @Override
    public int compareTo(Separateur separateur) {
        return this.distanceBordDeReference.compareTo(separateur.distanceBordDeReference);
    }

    public void genererPolygonePlan()
    {
        Imperial x1, x2, y1, y2;

        if(getmCote().getDirection().estHorizontal())
        {
            Imperial center = getmCote().getmX().add(distanceBordDeReference);
            x1 = center.substract(new Imperial(1));
            x2 = center.add(new Imperial(1));

            y1 = getmCote().getmY().clone();
            y2 = y1.add(getmCote().getmSalle().getEpaisseurMurs());
        }
        else
        {
            x1 = getmCote().getmX().clone();
            x2 = x1.add(getmCote().getmSalle().getEpaisseurMurs());

            Imperial center = getmCote().getmY().add(distanceBordDeReference);
            y1 = center.substract(new Imperial(1));
            y2 = center.add(new Imperial(1));
        }

        PointImperial p1 = new PointImperial(x1, y1);
        PointImperial p2 = new PointImperial(x1, y2);
        PointImperial p3 = new PointImperial(x2, y2);
        PointImperial p4 = new PointImperial(x2, y1);

        mPolygonePlan = new Polygone(Color.BLACK, p1, p2, p3, p4);
    }

    public void genererPolygoneELV(boolean exterieur) {
        Salle salle = getmCote().getmSalle();
        Imperial x1, x2, y1, y2;

        Imperial center = distanceBordDeReference;
        if(!mCote.mDirection.estHorizontal())
            center = center.substract(mCote.getmSalle().getEpaisseurMurs());
        x1 = center.substract(new Imperial(1));
        x2 = center.add(new Imperial(1));

        y1 = new Imperial(0);
        y2 = salle.getHauteur();


        if(exterieur)
        {
            if (mCote.mDirection.equals(Utilitaire.Direction.NORD) || mCote.mDirection.equals(Utilitaire.Direction.SUD)) {

                Imperial xSoustrait = new Imperial(salle.largeur.entier, salle.largeur.numerateur, salle.largeur.denominateur);
                x1 = x1.substract(xSoustrait).abs();
                x2 = x2.substract(xSoustrait).abs();
            }
            else {
                Imperial xSoustrait = new Imperial(salle.profondeur.entier, salle.profondeur.numerateur, salle.profondeur.denominateur);
                x1 = x1.substract(xSoustrait).abs();
                x2 = x2.substract(xSoustrait).abs();
            }
        }

        PointImperial p1 = new PointImperial(x1, y1);
        PointImperial p2 = new PointImperial(x1, y2);
        PointImperial p3 = new PointImperial(x2, y2);
        PointImperial p4 = new PointImperial(x2, y1);

        this.mPolygoneElevation = new Polygone(Color.BLACK, p1, p2, p3, p4);
    }


}
