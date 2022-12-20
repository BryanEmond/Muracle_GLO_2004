package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Polygone;
import ca.ulaval.glo2004.classes.Salle;
import ca.ulaval.glo2004.gestion.GestionnaireSalle;

import java.awt.*;
import java.util.ArrayList;

public class AfficheurGridPlacement extends Afficheur{

    private MainWindow mainWindow;
    private boolean bool;
    public AfficheurGridPlacement(boolean bool,MainWindow mainWindow){
        this.mainWindow = mainWindow;
        this.bool = bool;
    }
    @Override
    public void affiche(Graphics g) {
        if(this.bool){
            ArrayList<Polygone> polygones = GestionnaireSalle.genererGridPlacement(0,0,mainWindow.panel.getWidth(),mainWindow.mainPanel.getHeight(),mainWindow.gestionnaireSalle.getSpaceBetween());
            dessinerPolygones(g, polygones);
        }else{
            dessinerPolygones(g, new ArrayList<Polygone>());
        }
    }
}
