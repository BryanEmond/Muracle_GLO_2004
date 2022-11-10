package ca.ulaval.glo2004.classes;
//import Accessoire as parent

public class Porte extends Accessoire{

    int largeur = 38;
    int hauteur = 88;

    private void porte(int largeur, int hauteur) {
        Porte porteObjet = new Porte(); //call constructor
        this.largeur=largeur;
        this.hauteur = hauteur;

    }
}
