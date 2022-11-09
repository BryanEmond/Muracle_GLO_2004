package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.PointImperial;

import java.awt.*;

public class Conversion {

    private float pixelPerInches;
    private int offsetX;
    private int offsetY;

    // TODO : Make it a Singleton ?
    public Conversion(int pixelsX, int pixelsY)
    {
        //TODO Implement default pixelPerInches with screen size
        this.pixelPerInches = 1;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    private int inchesToPixel(Imperial inches)
    {
        float inchesValue = inches.getEntier() + (float) inches.getNumerateur() / inches.getDenominateur();
        return (int) (inchesValue * pixelPerInches);
    }

    private Imperial pixelsToInches(int pixels, int pixelOffset)
    {
        //TODO : Calculate fractional part of imperial
        float inchesValue = (pixels + pixelOffset) / pixelPerInches;
        int entier = (int) inchesValue;

        // Petite perte de précision ici
        int denominateur = 8;
        int numerateur = (int) ((inchesValue - entier) * denominateur);

        return new Imperial(entier, numerateur, denominateur);
    }

    public void zoomer(float pixelPerInches)
    {
        this.pixelPerInches += pixelPerInches;
    }

    public void dezoomer(float pixelPerInches)
    {
        this.pixelPerInches -= pixelPerInches;
    }

    public PointImperial trouverCoordonneImperial(int pixelX, int pixelY)
    {
        Imperial x = pixelsToInches(pixelX, offsetX);
        Imperial y = pixelsToInches(pixelY, offsetY);

        return new PointImperial(x, y);
    }

    public Point trouverCoordonneePixel(Imperial impX, Imperial impY)
    {
        int x = inchesToPixel(impX);
        int y = inchesToPixel(impY);

        return new Point(x, y);
    }
}
