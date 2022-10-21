package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Accessoire {
int hauteur;
int largeur;
int positionX;
int positionY;

ArrayList<ArrayList<Integer>> positions = new Arraylist<ArrayList<Integer>>();
ArrayList<Integer> point = new ArrayList<Integer>();
ArrayList<Integer> point2 = new ArrayList<Integer>();
    private Accessoire (int hauteur, int largeur, int positionX, int positionY){
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.positionX = positionX;
        this.positionY = positionY;
    }


    public ArrayList getZone() {

        //option 1: positionX et Y = point au centre
        point.add(positionX - largeur/2);
        point.add(positionY + hauteur/2);
        point2.add(positionX +largeur/2);
        point2.add(positionY - hauteur/2);

        positions.add(point);
        positions.add(point2);
        return positions;}

    public boolean validerContrainte() {
        //a précisé pour chaque type d'accessoire
return true;
    }
}
