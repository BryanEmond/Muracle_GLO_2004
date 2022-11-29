package ca.ulaval.glo2004.classes.dto;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Mur;

import java.io.Serializable;

public class MurDTO implements Serializable {

    private Imperial x;
    private Imperial y;
    private Imperial largeur;

    private boolean retourAir;
    private Imperial largeurRetourAir;

    public MurDTO(Mur mur)
    {
        this(mur.getmX().clone(), mur.getmY().clone(), mur.getmLargeur().clone(), mur.aRetourAir(), mur.getLargeurRetourAir().clone());
    }

    public MurDTO(Imperial x, Imperial y, Imperial largeur, boolean retourAir, Imperial largeurRetourAir) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.retourAir = retourAir;
        this.largeurRetourAir = largeurRetourAir;
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

    public boolean aRetourAir() {
        return retourAir;
    }

    public Imperial getLargeurRetourAir() {
        return largeurRetourAir;
    }
}
