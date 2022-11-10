package ca.ulaval.glo2004.classes;

//import Accessoire as parent
//s'affiche uniquement a l'intérieur de la salle

public class PrisesElectrique extends Accessoire {

    int largeur = 2;
    int hauteur = 4;

    private void priseElectrique(int largeur, int hauteur) {
PrisesElectrique prise = new PrisesElectrique(); //call constructor
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    public boolean getContrainte() {
        return contrainte;
    }

}

