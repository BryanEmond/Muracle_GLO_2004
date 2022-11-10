package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Polygone {
    String mCouleur;
    Element mElement;
    ArrayList<PointImperial> point;

    public Polygone(String couleur, ArrayList<PointImperial> point) {
        this.mCouleur = couleur;
        this.point = point;
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
        return point;
    }

    public void setPoint(ArrayList<PointImperial> point) {
        this.point = point;
    }


}