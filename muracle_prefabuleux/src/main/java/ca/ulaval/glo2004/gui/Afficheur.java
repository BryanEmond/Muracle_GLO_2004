package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.PointImperial;
import ca.ulaval.glo2004.classes.Polygone;
import ca.ulaval.glo2004.gestion.Conversion;

import java.awt.*;
import java.util.ArrayList;

public abstract class Afficheur {

    public abstract void affiche(Graphics g);

    public void dessinerPolygones(Graphics g, ArrayList<Polygone> polygones)
    {
        for (Polygone p : polygones)
        {
            dessinerPolygone(g, p);
        }
    }

    public void dessinerPolygone(Graphics g, Polygone polygone)
    {
        ArrayList<PointImperial> points = polygone.getPoints();

        if(points.size() < 2)
            throw new RuntimeException("Le polygone doit avoir au moins 2 points pour être dessinés");

        g.setColor(polygone.getCouleur());

        PointImperial pointPrecedent = null;

        for (PointImperial point : points)
        {
            if(pointPrecedent != null)
            {
                dessinerLigne(g, pointPrecedent, point);
            }

            pointPrecedent = point;
        }

        dessinerLigne(g, pointPrecedent, points.get(0));
    }

    public void dessinerLigne(Graphics g, PointImperial pointDebut, PointImperial pointFin)
    {
        Point point1Pixel = Conversion.getConversion().trouverCoordonneePixel(pointDebut.getmX(), pointDebut.getmY());
        Point point2Pixel = Conversion.getConversion().trouverCoordonneePixel(pointFin.getmX(), pointFin.getmY());

        g.drawLine(point1Pixel.x, point1Pixel.y, point2Pixel.x, point2Pixel.y);
    }
}
