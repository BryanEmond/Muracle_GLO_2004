package ca.ulaval.glo2004.classes.dto;

import ca.ulaval.glo2004.classes.*;

import java.io.Serializable;

public class AccessoireDTO implements Serializable {

    private Imperial x;
    private Imperial y;
    private Imperial hauteur;
    private Imperial largeur;
    private Imperial bordureFenetre;
    private Utilitaire.AccessoireEnum typeAccessoire;

    public AccessoireDTO(Accessoire accessoire)
    {
        x = accessoire.getmX().clone();
        if(!(accessoire instanceof Porte)){
            y = accessoire.getmY().clone();
        }else{
            y= new Imperial(0);
        }

        hauteur = accessoire.getmHauteur().clone();
        largeur = accessoire.getmLargeur().clone();

        if(accessoire instanceof Fenetre)
        {
            bordureFenetre = ((Fenetre) accessoire).getBordure().clone();
            typeAccessoire = Utilitaire.AccessoireEnum.Fenetre;
        }
        else if(accessoire instanceof Porte)
            typeAccessoire = Utilitaire.AccessoireEnum.Porte;
        else if(accessoire instanceof PrisesElectrique)
            typeAccessoire = Utilitaire.AccessoireEnum.PriseElectrique;
    }

    public AccessoireDTO(Imperial x, Imperial y, Imperial hauteur, Imperial largeur, Imperial bordureFenetre, Utilitaire.AccessoireEnum typeAccessoire) {
        this.x = x;
        this.y = y;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.bordureFenetre = bordureFenetre;
        this.typeAccessoire = typeAccessoire;
    }

    public Imperial getX() {
        return x;
    }

    public Imperial getY() {
        return y;
    }

    public Imperial getHauteur() {
        return hauteur;
    }

    public Imperial getLargeur() {
        return largeur;
    }

    public Imperial getBordureFenetre() {
        return bordureFenetre;
    }

    public Utilitaire.AccessoireEnum getTypeAccessoire() {
        return typeAccessoire;
    }
}
