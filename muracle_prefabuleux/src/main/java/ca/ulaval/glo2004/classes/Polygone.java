package ca.ulaval.glo2004.classes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Polygone implements Serializable {
    Color mCouleur;
    Element mElement;
    ArrayList<PointImperial> points;

    ArrayList<PointImperial> pointsTrier;

    public Polygone(Color couleur, ArrayList<PointImperial> points) {
        this.mCouleur = couleur;
        this.points = points;
    }

    public Polygone(Color couleur, PointImperial... points)
    {
        this(couleur, new ArrayList<>(Arrays.asList(points)));
    }

    public Color getCouleur() {
        return mCouleur;
    }

    public void setCouleur(Color couleur) {
        this.mCouleur = couleur;
    }

    public Element getmY() {
        return mElement;
    }

    public void setmY(Element mY) {
        this.mElement = mY;
    }

    public ArrayList<PointImperial> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<PointImperial> point) {
        this.points = point;
    }

    public ArrayList<Double> getCoinsDouble() {

        ArrayList<Double> pointsX = new ArrayList<>();
        ArrayList<Double> pointsY = new ArrayList<>();

        pointsX.add(this.points.get(0).mX.getFormeNormal());
        pointsX.add(this.points.get(1).mX.getFormeNormal());
        pointsX.add(this.points.get(2).mX.getFormeNormal());
        pointsX.add(this.points.get(3).mX.getFormeNormal());

        pointsY.add(this.points.get(0).mY.getFormeNormal());
        pointsY.add(this.points.get(1).mY.getFormeNormal());
        pointsY.add(this.points.get(2).mY.getFormeNormal());
        pointsY.add(this.points.get(3).mY.getFormeNormal());

        ArrayList<Double> pointsCoin = new ArrayList<>();

        pointsCoin.add(Collections.min(pointsX));
        pointsCoin.add(Collections.max(pointsX));
        pointsCoin.add(Collections.min(pointsY));
        pointsCoin.add(Collections.max(pointsY));

        return  pointsCoin;
    }

    public ArrayList<Imperial> getCoinsImperial() {

        ArrayList<Double> pointsX = new ArrayList<>();
        ArrayList<Double> pointsY = new ArrayList<>();

        pointsX.add(this.points.get(0).mX.getFormeNormal());
        pointsX.add(this.points.get(1).mX.getFormeNormal());
        pointsX.add(this.points.get(2).mX.getFormeNormal());
        pointsX.add(this.points.get(3).mX.getFormeNormal());

        pointsY.add(this.points.get(0).mY.getFormeNormal());
        pointsY.add(this.points.get(1).mY.getFormeNormal());
        pointsY.add(this.points.get(2).mY.getFormeNormal());
        pointsY.add(this.points.get(3).mY.getFormeNormal());

        ArrayList<Imperial> pointsCoin = new ArrayList<>();

        for (PointImperial point:this.points) {
            if(Objects.equals(Collections.min(pointsX), point.mX.getFormeNormal())) {pointsCoin.add(point.mX); break ;}
        }

        for (PointImperial point:this.points) {
            if(Objects.equals(Collections.max(pointsX), point.mX.getFormeNormal())) {pointsCoin.add(point.mX); break ;}
        }

        for (PointImperial point:this.points) {
            if(Objects.equals(Collections.min(pointsY), point.mY.getFormeNormal())) {pointsCoin.add(point.mX); break ;}
        }

        for (PointImperial point:this.points) {
            if(Objects.equals(Collections.max(pointsY), point.mY.getFormeNormal())) {pointsCoin.add(point.mX); break ;}
        }

        return pointsCoin;
    }


    public boolean PointEstDansPolygone(PointImperial point) {
        ArrayList<Double> coins = getCoinsDouble();

        return point.mX.getFormeNormal() >= coins.get(0) && point.mX.getFormeNormal() <= coins.get(1) &&
                point.mY.getFormeNormal() >= coins.get(2) && point.mY.getFormeNormal() <= coins.get(3);
    }

    public boolean SeparateurEstDansPolygones(PointImperial point, Polygone polygone) {
        ArrayList<Double> coins = getCoinsDouble();

        return point.mX.getFormeNormal() >= coins.get(0) && point.mX.getFormeNormal() <= coins.get(1) &&
                point.mY.getFormeNormal() >= coins.get(2) && point.mY.getFormeNormal() <= coins.get(3);
    }


    public boolean PolygoneEstDansPolygone(Polygone polygone) {


        return false;
    }


}