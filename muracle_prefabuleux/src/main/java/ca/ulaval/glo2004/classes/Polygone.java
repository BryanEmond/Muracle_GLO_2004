package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Polygone {
    String mCouleur;
    Element mElement;
    ArrayList<PointImperial> points;

    public Polygone(String couleur, ArrayList<PointImperial> points) {
        this.mCouleur = couleur;
        this.points = trier(points);
    }

    public String getCouleur() {
        return mCouleur;
    }

    public void setCouleur(String couleur) {
        this.mCouleur = couleur;
    }

    public Element getmY() {
        return mElement;
    }

    public void setmY(Element mY) {
        this.mElement = mY;
    }

    public ArrayList<PointImperial> getPoint() {
        return points;
    }

    public void setPoint(ArrayList<PointImperial> point) {
        this.points = point;
    }

    public ArrayList<PointImperial> trier(ArrayList<PointImperial> points) {
        int conteur = 1;
        PointImperial pointModif;

        while (points.get(0).mY != points.get(1).mY) {
            pointModif = points.get(3);
            points.set(3, points.get(2));
            points.set(2, points.get(1));
            points.set(1, pointModif);
        }

        if(points.get(0).mX.compareTo(points.get(1).mX) > 0){
            pointModif = points.get(0);
            points.set(0, points.get(1));
            points.set(1, pointModif);
        }

        if(points.get(2).mY.compareTo(points.get(3).mY) > 0){
            pointModif = points.get(2);
            points.set(2, points.get(3));
            points.set(3, pointModif);
        }

        return  points;
    }


    public boolean EstDansPolygone(PointImperial point) {
        return point.mX.compareTo(this.points.get(0).mX) >= 0 && point.mY.compareTo(this.points.get(3).mY) <= 0;
    }


}