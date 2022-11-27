package ca.ulaval.glo2004.classes;

//import Accessoire as parent
//s'affiche uniquement a l'intérieur de la salle

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class PrisesElectrique extends Accessoire implements Serializable {

    Boolean perceInterieur = true;
    Boolean perceExterieur = false;


    public PrisesElectrique(Imperial mY, Imperial mX, boolean mPerceExtérieur, boolean mPerceInterieur, Imperial mLargeur, Imperial mHauteur, String mNom) {
        super(mY, mX, mPerceExtérieur, mPerceInterieur, mLargeur, mHauteur);
    this.mHauteur = mHauteur;
    this.mLargeur = mLargeur;
    this.perceExterieur = mPerceExtérieur;
    this.perceInterieur = mPerceInterieur;

    }


    @Override
    public ArrayList<Polygone> genererPolygoneELV(boolean exterieur) {
        //TODO ajuster les point x1 et y1
        Imperial x1 = mX;
        Imperial y1 = mY;
        Imperial x2;
        Imperial y2;

        x2 = x1.add(mLargeur);
        y2 = y1.add(mHauteur);

        if(exterieur)
        {
            x1 = x1.mirror(cote);
            x2 = x2.mirror(cote);
        }

        boolean sel = getCote().getmSalle().getElementSelectionne() == this;
        Polygone prise = this.mPolygoneElevation = new Polygone(sel ? Color.BLUE : Color.DARK_GRAY, new PointImperial(x1, y1), new PointImperial(x1, y2), new PointImperial(x2, y2), new PointImperial(x2, y1));
        ArrayList<Polygone> prises = new ArrayList<>();
        prises.add(prise);

        return prises;
    }
}

