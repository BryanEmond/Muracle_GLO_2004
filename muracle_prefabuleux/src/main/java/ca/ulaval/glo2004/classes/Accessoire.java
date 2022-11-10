package ca.ulaval.glo2004.classes;

import java.util.ArrayList;
//as un impacte sur le poid du mur...
public class Accessoire {
    int hauteur;
    int largeur;
    int positionX;
    int positionY;
    boolean contrainte;

    public Accessoire() {
    }

    public static void accessoire() {
        Accessoire accessoireObjet = new Accessoire(); //call constructor
    }


ArrayList<ArrayList<Integer>> positions = new ArrayList<ArrayList<Integer>>();
ArrayList<Integer> point = new ArrayList<>();
ArrayList<Integer> point2 = new ArrayList<>();
    private Accessoire (int hauteur, int largeur, int positionX, int positionY){
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.positionX = positionX;
        this.positionY = positionY;
    }


    public ArrayList<ArrayList<Integer>> getZone() {

        //option 1: positionX et Y = point au centre
        point.add(positionX - largeur/2);
        point.add(positionY + hauteur/2);
        point2.add(positionX +largeur/2);
        point2.add(positionY - hauteur/2);

        positions.add(point);
        positions.add(point2);
        return positions;}


    public boolean getContrainte() {
        //return vrai si les contraintes de l'accessoire sont respecté,
        return contrainte;
    }



}
