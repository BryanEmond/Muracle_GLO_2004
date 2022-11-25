package ca.ulaval.glo2004.classes.dto;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Mur;

public class MurDTO {

    private Imperial x;
    private Imperial y;
    private Imperial largeur;

    public MurDTO(Mur mur)
    {
        this(mur.getmX().clone(), mur.getmY().clone(), mur.getmLargeur().clone());
    }

    public MurDTO(Imperial x, Imperial y, Imperial largeur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
    }

    public Imperial getX() {
        return x;
    }

    public Imperial getY() {
        return y;
    }

    public Imperial getLargeur() {
        return largeur;
    }

}
