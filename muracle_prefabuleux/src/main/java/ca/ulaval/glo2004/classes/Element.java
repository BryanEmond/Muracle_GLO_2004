package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Element {
    Imperial mY;
    Imperial mX;

    public Element(Imperial mY, Imperial mX) {
        this.mY = mY;
        this.mX = mX;
    }


    public Imperial getmY() {
        return mY;
    }

    public void setmY(Imperial mY) {
        this.mY = mY;
    }

    public Imperial getmX() {
        return mX;
    }

    public void setmX(Imperial mX) {
        this.mX = mX;
    }

    public void calculeDisposition() {

    }

    public void Salle() {

    }

    public int largeur() {
        return 0;
    }

    public int hauteur() {
        return 0;
    }


}