package ca.ulaval.glo2004.classes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Salle implements Serializable {

    Imperial epaisseurMurs;
    Imperial hauteur;
    Imperial largeur;
    Imperial profondeur;

    Imperial largeurPliSoudure;

    Element ElementSelectionne;

    int anglePliSoudure;

    ArrayList<Cote> cotes;

    public Salle(ArrayList<Cote> cotes) {
        this.epaisseurMurs = new Imperial(6, 0, 1);
        this.hauteur = new Imperial(96,0,1);
        this.largeur = new Imperial(144,0,1);
        this.profondeur = new Imperial(144,0,1);
        this.largeurPliSoudure = new Imperial(1,0,1);
        this.anglePliSoudure = 45;
        this.cotes = cotes;
    }
    public ArrayList<Polygone> polygonePlan(){
        return new ArrayList<Polygone>();
    }

    public boolean AjouterFenetre(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        Cote cote = getCote(direction);
        if(cote.PointEstDansCote(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if (polygone == null){
                return false;
            }
            Fenetre fenetre = new Fenetre(point.mY, point.mX,interieur,interieur, new Imperial(24, 0, 0),new Imperial(24, 0, 0), null)    ;
            ArrayList<Polygone> fenetres = fenetre.genererPolygoneELV();
            for (PointImperial pointImperial:fenetres.get(1).getPoints()
                 )
            {
                if(!polygone.PointEstDansPolygone(pointImperial)){
                    return false;
                }

                for (Accessoire accessoire: cote.accessoires
                ) {
                    if(accessoire.mPolygoneElevation.PointEstDansPolygone(pointImperial)){
                        return false;
                    }
                }
            }

            cote.accessoires.add(fenetre);
            return  true;
        }
        return  false;
    }

    public boolean AjouterPorte(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        Cote cote = getCote(direction);
        if(cote.PointEstDansCote(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if (polygone == null){
                return false;
            }
            Porte porte = new Porte(point.mY, point.mX,interieur,interieur, new Imperial(38, 0, 0),new Imperial(88, 0, 0), null)    ;
            ArrayList<Polygone> portes = porte.genererPolygoneELV();
            for (PointImperial pointImperial:portes.get(0).getPoints()
            )
            {
                if(!polygone.PointEstDansPolygone(pointImperial)){
                    return false;
                }

                for (Accessoire accessoire: cote.accessoires
                ) {
                    if(accessoire.mPolygoneElevation.PointEstDansPolygone(pointImperial)){
                        return false;
                    }
                }
            }

            cote.accessoires.add(porte);
            return  true;
        }
        return  false;
    }

    public boolean Supprimer(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        return  false;
    }

    public boolean AjouterPriseElectrique(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        Cote cote = getCote(direction);
        if(cote.PointEstDansCote(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if (polygone == null){
                return false;
            }
            PrisesElectrique prisesElectrique = new PrisesElectrique(point.mY, point.mX,interieur,interieur, new Imperial(24, 0, 0),new Imperial(24, 0, 0), null)    ;
            ArrayList<Polygone> prisesElectriques = prisesElectrique.genererPolygoneELV();
            for (PointImperial pointImperial:prisesElectriques.get(0).getPoints()
            )
            {
                if(!polygone.PointEstDansPolygone(pointImperial)){
                    return false;
                }

                for (Accessoire accessoire: cote.accessoires
                ) {
                    if(accessoire.mPolygoneElevation.PointEstDansPolygone(pointImperial)){
                        return false;
                    }
                }
            }

            cote.accessoires.add(prisesElectrique);
            return  true;
        }
        return  false;
    }

    public void AjouterRetourAir(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        // Mur ?
    }


    public void selection(PointImperial point, Utilitaire.Direction direction,boolean interieur){

        Cote cote = getCote(direction);

        for (Mur mur: cote.murs) {
            mur.genererPolygoneELV();
            if(mur.polygonesElevation(interieur).PointEstDansPolygone(point)){
                ElementSelectionne = mur;
            }
        }

        for (Accessoire accessoire: cote.accessoires) {
            accessoire.genererPolygoneELV();
            if(accessoire.getmPolygoneElevation(interieur).PointEstDansPolygone(point)){
                ElementSelectionne = accessoire;
            }
        }
    }

    public ArrayList<Polygone> polygonesElevation(){
        return new ArrayList<Polygone>();
    }

    public Utilitaire.Direction separateirElevation(PointImperial point,Utilitaire.Direction direction){
        ArrayList<PointImperial> points = new ArrayList<>();
        Cote cote = getCote(direction);
        if(cote.PointEstDansCote(point)){
            Polygone polygone = getPolygoneMurPlan(cote,point);
            if (polygone == null){
                return null;
            }
            if(cote.mDirection == Utilitaire.Direction.NORD || cote.mDirection == Utilitaire.Direction.SUD){

                if(cote.PointSeparateurEstSurAccessoire(point.mX)){
                    points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                    points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                    points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                    points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                }
                Imperial distanceBord = point.getmX().substract(cote.getPremierMur().getmX());
                direction = cote.mDirection;
                cote.AjouterSeparateur(new Separateur(point.mY,point.mX,distanceBord,cote,new Polygone(Color.BLACK,points)));
            }else {
                if(cote.PointSeparateurEstSurAccessoire(point.mY)){
                    points.add(new PointImperial(point.mY,polygone.points.get(0).mX));
                    points.add(new PointImperial(point.mY,polygone.points.get(0).mX));
                    points.add(new PointImperial(point.mY,polygone.points.get(1).mX));
                    points.add(new PointImperial(point.mY,polygone.points.get(1).mX));
                }
                Imperial distanceBord = point.getmY().substract(cote.getPremierMur().getmY());
                direction = cote.mDirection;
                cote.AjouterSeparateur(new Separateur(point.mY,point.mX,distanceBord,cote,new Polygone(Color.BLACK,points)));
            }
        }
        return direction;
    }

    public Utilitaire.Direction separateur(PointImperial point) {

        ArrayList<PointImperial> points = new ArrayList<>();
        Utilitaire.Direction direction = null;

        for (Cote var : cotes)
        {
            if(var.PointEstDansCote(point)){
                Polygone polygone = getPolygoneMurPlan(var,point);

                if (polygone == null){
                    return null;
                }

                if(var.mDirection == Utilitaire.Direction.NORD || var.mDirection == Utilitaire.Direction.SUD){

                    if(var.PointSeparateurEstSurAccessoire(point.mX)){
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                    }
                    Imperial distanceBord = point.getmX().substract(var.getPremierMur().getmX());
                   /* System.out.println("Point : " + point);
                    System.out.println("Premier : " + var.);
                    System.out.println("Distance : " + distanceBord);*/
                    direction = var.mDirection;
                    var.AjouterSeparateur(new Separateur(point.mY,point.mX,distanceBord,var,new Polygone(Color.BLACK,points)));
                    return direction;
                }else {
                    if(var.PointSeparateurEstSurAccessoire(point.mY)) {
                        points.add(new PointImperial(point.mY, polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(1).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(1).mX));
                    }
                    Imperial distanceBord = point.getmY().substract(var.getPremierMur().getmY());
                    direction = var.mDirection;
                    var.AjouterSeparateur(new Separateur(point.mY,point.mX,distanceBord,var,new Polygone(Color.BLACK,points)));
                    return direction;
                }
            };
        }
        return null;
    }

    private Polygone getPolygoneMurPlan(Cote var, PointImperial point) {
        for (Polygone polygone:var.getPolygonesPlan()) {
            if(polygone.PointEstDansPolygone(point)){
                return polygone;
            };
        }
        return null;
    }

    private Polygone getPolygoneMurElevation(Cote var, PointImperial point,boolean interieur) {
        for (Polygone polygone:var.getPolygoneElevation(interieur)) {
            if(polygone.PointEstDansPolygone(point)){
                return polygone;
            };
        }
        return null;
    }

    public Imperial getEpaisseurMurs() {
        return epaisseurMurs;
    }

    public void setEpaisseurMurs(Imperial epaisseurMurs) {
        this.epaisseurMurs = epaisseurMurs;
    }

    public Imperial getHauteur() {
        return hauteur;
    }

    public void setHauteur(Imperial hauteur) {
        this.hauteur = hauteur;
    }

    public Imperial getLargeur() {
        return largeur;
    }

    public void setLargeur(Imperial largeur) {
        this.largeur = largeur;
    }

    public Imperial getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(Imperial profondeur) {
        this.profondeur = profondeur;
    }

    public ArrayList<Cote> getCotes() {
        return cotes;
    }

    public void setCotes(ArrayList<Cote> cotes) {
        this.cotes = cotes;
    }

    public ArrayList<Polygone> getPolygonesPlan()
    {
        ArrayList<Polygone> polygones = new ArrayList<>();

        for (Cote cote : cotes)
        {
            polygones.addAll(cote.getPolygonesPlan());
        }

        return polygones;
    }

    public Cote getCote(Utilitaire.Direction direction){

        for ( Cote cote : cotes){
            if (cote.mDirection.equals(direction)) {
                return cote;
            }
        }
        return null;
    }

    public Imperial getLargeurPliSoudure()
    {
        return largeurPliSoudure;
    }

    public void setLargeurPliSoudure(Imperial largeurPliSoudure) {
        this.largeurPliSoudure = largeurPliSoudure;
    }

    public int getAnglePliSoudure() {
        return anglePliSoudure;
    }

    public void setAnglePliSoudure(int anglePliSoudure) {
        this.anglePliSoudure = anglePliSoudure;
    }
}
