package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.PointImperial;

public class Conversion {

    private float pixelPerInches;
    private int offsetX;
    private int offsetY;

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
        //TODO : Calculate fractional part of imperial, is it needed ?
        float inchesValue = (pixels + pixelOffset) / pixelPerInches;
        int entier = (int) inchesValue;

        return new Imperial(entier, 0, 0);
    }

    public void zoomer(float pixelPerInches)
    {
        this.pixelPerInches += pixelPerInches;
    }

    public void dezoomer(float pixelPerInches)
    {
        this.pixelPerInches -= pixelPerInches;
    }

    public PointImperial trouverCoordonne(int pixelX, int pixelY)
    {
        Imperial x = pixelsToInches(pixelX, offsetX);
        Imperial y = pixelsToInches(pixelY, offsetY);

        //TODO return complete PointImperial
        return new PointImperial();
    }
}
