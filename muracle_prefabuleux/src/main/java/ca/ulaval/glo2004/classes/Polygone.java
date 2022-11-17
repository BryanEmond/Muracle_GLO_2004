package ca.ulaval.glo2004.classes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygone implements Serializable {
    Color mCouleur;
    Element mElement;
    ArrayList<PointImperial> points;

    public Polygone(Color couleur, ArrayList<PointImperial> point) {
        this.mCouleur = couleur;
        this.points = point;
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

    public void setPoints(ArrayList<PointImperial> points) {
        this.points = points;
    }


}