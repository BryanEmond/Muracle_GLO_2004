package ca.ulaval.glo2004.classes;
//import Acessoire as a parent

// toujours centré sur le panneau, largeur configurable, seulement sur les panneau
//intérieur

public class RetourAir extends Accessoire{


    private void retourAir(int largeur, int hauteur) {
        RetourAir retourAirObjet = new RetourAir(); //call constructor
        this.hauteur= hauteur;
        this.largeur = largeur;
    }

}
