package ca.ulaval.glo2004.classes.dto;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Mur;

public class MurDTO {

    private Imperial x;
    private Imperial y;
    private Imperial largeur;
    private Imperial mBandeSoudageVerticale;
    private Imperial mBandeSoudageHorizontale;

    public MurDTO(Mur mur)
    {
        this(mur.getmX().clone(), mur.getmY().clone(), mur.getmLargeur().clone(),
                mur.getmBandeSoudageVerticale().clone(), mur.getmBandeSoudageHorizontale().clone());
    }

    public MurDTO(Imperial x, Imperial y, Imperial largeur, Imperial mBandeSoudageVerticale, Imperial mBandeSoudageHorizontale) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.mBandeSoudageVerticale = mBandeSoudageVerticale;
        this.mBandeSoudageHorizontale = mBandeSoudageHorizontale;
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

    public Imperial getmBandeSoudageVerticale() {
        return mBandeSoudageVerticale;
    }

    public Imperial getmBandeSoudageHorizontale() {
        return mBandeSoudageHorizontale;
    }

}
