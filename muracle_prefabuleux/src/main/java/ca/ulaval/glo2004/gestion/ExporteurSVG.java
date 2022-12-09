package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.PointImperial;
import ca.ulaval.glo2004.classes.Polygone;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExporteurSVG {

    public static void EnregistrerSVG(String cheminFichier, List<Polygone> polygones) throws IOException {
        FileWriter fichier = new FileWriter(cheminFichier);

        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;

        for (Polygone poly : polygones)
        {
            for(PointImperial p : poly.getPoints())
            {
                if(p.getmX().getValue() > maxX)
                    maxX = p.getmX().getValue();

                if(p.getmY().getValue() > maxY)
                    maxY = p.getmY().getValue();
            }
        }

        EcrireEntete(fichier, maxY, maxX);
        for(Polygone polygone : polygones)
        {
            EcrirePolygone(fichier, polygone);
        }
        EcrireEnqueue(fichier);

        fichier.close();
    }

    private static void EcrireEntete(FileWriter writer, double height, double width) throws IOException {
        writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
        writer.append( String.format("<svg height=\"%1$s\" width=\"%2$s\">\n", height, width));
    }

    private static void EcrireEnqueue(FileWriter writer) throws IOException {
        writer.append("</svg>");
    }

    private static void EcrirePolygone(FileWriter writer, Polygone polygone) throws IOException {
        ArrayList<PointImperial> points = polygone.getPoints();

        if(points.size() < 2)
            throw new RuntimeException("Le polygone doit avoir au moins 2 points pour être dessinés");

        int indexLigne = 0;
        PointImperial pointPrecedent = null;

        for (PointImperial point : points)
        {
            if(pointPrecedent != null)
            {
                EcrireLigne(writer, pointPrecedent, point, polygone.ligneEstPointille(indexLigne));
            }

            pointPrecedent = point;
            indexLigne++;
        }

        EcrireLigne(writer, pointPrecedent, points.get(0), polygone.ligneEstPointille(indexLigne));
    }

    private static void EcrireLigne(FileWriter writer, PointImperial p1, PointImperial p2, boolean pointille) throws IOException
    {
        double x1 = p1.getmX().getValue();
        double y1 = p1.getmY().getValue();

        double diffX = p2.getmX().getValue() - x1;
        double diffY = p2.getmY().getValue() - y1;

        String strPointille = pointille ? " stroke-dasharray=\"2,2\"": "";

        writer.append( String.format("<path stroke=\"red\"%5$s d=\"m%1$s %2$s l%3$s %4$s\" />\n", x1 + "", y1 + "", diffX + "", diffY + "", strPointille) );
    }

}
