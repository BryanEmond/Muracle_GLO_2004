package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.PointImperial;
import ca.ulaval.glo2004.classes.Polygone;
import ca.ulaval.glo2004.gestion.Conversion;

import java.awt.*;
import java.util.ArrayList;

public abstract class Afficheur {

    private int offsetX = 0;
    private int offsetY = 0 ;

    public abstract void affiche(Graphics g);



    public Graphics2D dessinerPolygones(Graphics g, ArrayList<Polygone> polygones)
    {
        for (Polygone p : polygones)
        {
            dessinerPolygone(g, p);
        }
        return null;
    }

    public void dessinerPolygone(Graphics g, Polygone polygone)
    {
        ArrayList<PointImperial> points = polygone.getPoints();

        if(points.size() < 2)
            throw new RuntimeException("Le polygone doit avoir au moins 2 points pour être dessinés");

        g.setColor(polygone.getCouleur());

        PointImperial pointPrecedent = null;

        int index = 0;
        for (PointImperial point : points)
        {
            if(pointPrecedent != null)
            {
                dessinerLigne(g, pointPrecedent, point, polygone.ligneEstPointille(index));
                index++;
            }

            pointPrecedent = point;
        }

        dessinerLigne(g, pointPrecedent, points.get(0), polygone.ligneEstPointille(index));
    }

    public void dessinerLigne(Graphics g, PointImperial pointDebut, PointImperial pointFin, boolean pointille)
    {
        Point point1Pixel = trouverPixel(pointDebut);
        Point point2Pixel = trouverPixel(pointFin);

        Graphics2D g2d = (Graphics2D) g.create();

        if(pointille)
        {
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{9}, 0);
            g2d.setStroke(dashed);
        }

        g2d.drawLine(point1Pixel.x, point1Pixel.y, point2Pixel.x, point2Pixel.y);
        g2d.dispose();
    }

    protected Point trouverPixel(PointImperial pointImperial)
    {
        Point pointPixel = Conversion.getConversion().trouverCoordonneePixel(pointImperial.getmX(), pointImperial.getmY());
        pointPixel.x = pointPixel.x + offsetX;
        pointPixel.y = pointPixel.y + offsetY;

        return pointPixel;
    }

    public void setOffset(int x, int y)
    {
        this.offsetX = x;
        this.offsetY = y;
    }

}
