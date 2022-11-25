package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.PointImperial;

import java.awt.*;
import java.io.Serializable;

public class Conversion implements Serializable {

    private static Conversion conversion;

    private float pixelPerInches;
    private int offsetX;
    private int offsetY;

    public Conversion()
    {
        reset();
    }

    public void reset()
    {
        this.pixelPerInches = 4;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    private int inchesToPixel(Imperial inches, int pixelOffset)
    {
        float inchesValue = inches.getEntier() + (float) inches.getNumerateur() / inches.getDenominateur();
        return (int) (inchesValue * pixelPerInches) - pixelOffset;
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

    public void pan(int offsetX, int offsetY)
    {
        this.offsetX += offsetX;
        this.offsetY += offsetY;
    }

    public void zoomer(double quantity, int x, int y)
    {
        double factor;

        if(quantity > 0)
            factor = 1.5;
        else
            factor = 0.75;

        double xInchVise = (x + offsetX) / pixelPerInches;
        double yInchVise = (y + offsetY) / pixelPerInches;

        this.pixelPerInches *= factor;

        int xAfter = (int) (xInchVise * pixelPerInches) - offsetX;
        int yAfter = (int) (yInchVise * pixelPerInches) - offsetY;

        offsetX += xAfter - x;
        offsetY += yAfter - y;
    }


    public PointImperial trouverCoordonneImperial(int pixelX, int pixelY)
    {
        Imperial x = pixelsToInches(pixelX, offsetX);
        Imperial y = pixelsToInches(pixelY, offsetY);

        return new PointImperial(x, y);
    }

    public Point trouverCoordonneePixel(Imperial impX, Imperial impY)
    {
        int x = inchesToPixel(impX, offsetX);
        int y = inchesToPixel(impY, offsetY);

        return new Point(x, y);
    }

    public static Conversion getConversion()
    {
        if(conversion == null)
            conversion = new Conversion();

        return conversion;
    }
}
