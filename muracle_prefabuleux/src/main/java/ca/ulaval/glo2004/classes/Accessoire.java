package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Accessoire {
int hauteur;
int largeur;
int positionX;
int positionY;

ArrayList<ArrayList<Integer>> positions = new Arraylist<ArrayList<Integer>>();
ArrayList<Integer> point = new ArrayList<Integer>();

    private Accessoire (int hauteur, int largeur, int positionX, int positionY){
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.positionX = positionX;
        this.positionY = positionY;
    }

public boolean validerContrainte() {return true;}
    public ArrayList getZone() {

        //option 1: positionX et Y = point au centre
        point.add(positionX - largeur/2);
        point.add(positionY + hauteur/2);
        positions.add(point);
        return positions;}


}
