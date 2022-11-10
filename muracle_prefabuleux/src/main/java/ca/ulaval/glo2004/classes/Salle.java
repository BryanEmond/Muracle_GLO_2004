package ca.ulaval.glo2004.classes;

import java.util.ArrayList;

public class Salle extends Element{

    Imperial epaisseurMurs;
    Imperial marge;
    Imperial hauteur;
    Imperial largeur;
    Imperial profondeur;
    boolean vuePlan;
    ArrayList<Cote> cotes;

    public Salle(Imperial mY, Imperial mX, Imperial epaisseurMurs, Imperial marge, Imperial hauteur, Imperial largeur, Imperial profondeur, boolean vuePlan, ArrayList<Cote> cotes) {
        super(mY, mX);
        this.epaisseurMurs = epaisseurMurs;
        this.marge = marge;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.profondeur = profondeur;
        this.vuePlan = vuePlan;
        this.cotes = cotes;
    }

    public ArrayList<Polygone> polygonesPlan() {
        return cotes;
    }

    public ArrayList<Polygone> polygonesElevation() {
        return cotes;
    }

    public Cote cote(PointImperial point) {
        return ;
    }

    public Separateur separateur(PointImperial point) {
        return new Separateur();
    }

}
