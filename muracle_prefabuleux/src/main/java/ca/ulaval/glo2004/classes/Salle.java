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

    Imperial hauteurRetourAir;

    Imperial positionRetourAir;

    Imperial hauteurTrouRetourAir;

    ArrayList<Cote> cotes;

    public Salle(ArrayList<Cote> cotes) {
        this.epaisseurMurs = new Imperial(6, 0, 1);
        this.hauteur = new Imperial(96,0,1);
        this.largeur = new Imperial(144,0,1);
        this.profondeur = new Imperial(144,0,1);
        this.largeurPliSoudure = new Imperial(1,0,1);
        this.anglePliSoudure = 45;
        this.hauteurRetourAir = new Imperial(5);
        this.hauteurTrouRetourAir = new Imperial(4);
        this.positionRetourAir = new Imperial(5);
        setCotes(cotes);
    }
    public ArrayList<Polygone> polygonePlan(){
        return new ArrayList<Polygone>();
    }

    public boolean AjouterFenetre(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        Cote cote = getCote(direction);
        if(cote.PointEstDansCoteElevation(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if (polygone == null){
                return false;
            }
            Fenetre fenetre = new Fenetre(point.mY, point.mX,interieur,interieur, new Imperial(24),new Imperial(24));
            fenetre.setCote(cote);

            ArrayList<Polygone> fenetres = fenetre.genererPolygoneELV();

            for (PointImperial pointImperial:fenetres.get(1).getPoints()
                 )
            {
                if(!polygone.PointEstDansPolygone(pointImperial)){
                    return false;
                }

                for (Accessoire accessoire: cote.accessoires)
                {
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
        if(cote.PointEstDansCoteElevation(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if (polygone == null){
                return false;
            }
            Porte porte = new Porte(point.mY, point.mX,interieur,interieur, new Imperial(38),new Imperial(88), null);
            porte.setCote(cote);
            ArrayList<Polygone> portes = porte.genererPolygoneELV();
            for (PointImperial pointImperial:portes.get(0).getPoints())
            { //                if(polygone.PointEstDansPolygone(pointImperial)){ //
                // return false; //                }
                for (Accessoire accessoire: cote.accessoires) {
                if(accessoire.mPolygoneElevation.PointEstDansPolygone(pointImperial)){
                return false;
                }
                }
            }
            cote.accessoires.add(porte);
            return  true;}
        return  false;     }

    public boolean SupprimerPlan(PointImperial point){
        return  false;
    }

    public boolean SupprimerElevation(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        return  false;
    }

    public boolean AjouterPriseElectrique(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        Cote cote = getCote(direction);
        if(cote.PointEstDansCoteElevation(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if (polygone == null){
                return false;
            }
            PrisesElectrique prisesElectrique = new PrisesElectrique(point.mY, point.mX,interieur,interieur, new Imperial(24),new Imperial(24), null)    ;
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

    public boolean AjouterRetourAirPlan(PointImperial point)
    {
        Mur mur = getMurCliquePlan(point);
        if(mur != null)
        {
            mur.setRetourAir(!mur.aRetourAir());
            return true;
        }
        return false;
    }

    public boolean AjouterRetourAirElevation(PointImperial point, Utilitaire.Direction direction, boolean interieur){
        // Mur ?
        Cote cote = getCote(direction);
        Mur mur = getMurCliqueElevation(cote, point, interieur);
        if(mur != null)
        {
            mur.setRetourAir(!mur.aRetourAir());
            return true;
        }
        return  false;
    }


    public void selectionPlan(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        for (Cote cote:cotes) {

            for (Mur mur: cote.murs) {
                //mur.genererPolygoneELV(interieur);
                if(mur.mPolygonePlan.PointEstDansPolygone(point)){
                    ElementSelectionne = mur;
                }
            }

            for (Separateur separateur: cote.separateurs) {
                separateur.genererPolygonePlan();
                if(separateur.mPolygonePlan.PointEstDansPolygone(point)){
                    ElementSelectionne = separateur;
                }
            }
        }
    }

    public void selectionElevantion(PointImperial point, Utilitaire.Direction direction,boolean interieur){

        Cote cote = getCote(direction);
        if(cote == null)
            return;

        for (Mur mur: cote.murs) {
            mur.genererPolygoneELV(!interieur);
            if(mur.polygonesElevation(interieur).PointEstDansPolygone(point)){
                ElementSelectionne = mur;
            }
        }

        for (Separateur separateur: cote.separateurs) {
            separateur.genererPolygoneELV(!interieur);
            if(separateur.mPolygoneElevation.PointEstDansPolygone(point)){
                ElementSelectionne = separateur;
            }
        }

        for (Accessoire accessoire: cote.accessoires) {
            accessoire.genererPolygoneELV();
            if(accessoire.getmPolygoneElevation(interieur).PointEstDansPolygone(point)){
                ElementSelectionne = accessoire;
            }
        }

    }

    public Mur getMurCliquePlan(PointImperial point)
    {
        for(Cote cote : getCotes())
        {
            for(Mur mur : cote.getMurs())
            {
                if(mur.PointEstDansMur(point))
                    return mur;
            }
        }

        return null;
    }

    public ArrayList<Polygone> polygonesElevation(){
        return new ArrayList<Polygone>();
    }

    public void separateurElevation(PointImperial point,Utilitaire.Direction direction,boolean interieur){
        ArrayList<PointImperial> points = new ArrayList<>();
        Cote cote = getCote(direction);
        if(cote.PointEstDansCoteElevation(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if(polygone != null){
                if(cote.mDirection == Utilitaire.Direction.NORD || cote.mDirection == Utilitaire.Direction.SUD){

                    if(!cote.PointSeparateurEstSurAccessoire(point.mX)){
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));

                        Imperial distanceBord = point.getmX().substract(cote.getPremierMur().getmX());
                        cote.AjouterSeparateur(new Separateur(point.mY,point.mX,distanceBord,cote,new Polygone(Color.BLACK,points)));
                    }

                    }
                else {
                    if(!cote.PointSeparateurEstSurAccessoire(point.mY)){
                        points.add(new PointImperial(point.mY,polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY,polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY,polygone.points.get(1).mX));
                        points.add(new PointImperial(point.mY,polygone.points.get(1).mX));
                        Imperial distanceBord = point.getmY().substract(cote.getPremierMur().getmY());
                        cote.AjouterSeparateur(new Separateur(point.mY,point.mX,distanceBord,cote,new Polygone(Color.BLACK,points)));
                    }
                }
            }
        }
    }

    public Utilitaire.Direction separateur(PointImperial point) {

        ArrayList<PointImperial> points = new ArrayList<>();
        Utilitaire.Direction direction = null;

        for (Cote var : cotes)
        {
            if(var.PointEstDansCote(point)){
                Polygone polygone = getPolygoneMurPlan(var,point);
                Mur mur = getMurCliquePlan(point);



                if (polygone == null){
                    return null;
                }

                if(var.mDirection == Utilitaire.Direction.NORD || var.mDirection == Utilitaire.Direction.SUD){

                    if(mur.retourAir && mur.mPolygonePlanRetourAir.SeparateurEstDansPolygoneNordSud(point)){
                        return null;
                    }

                    if(!var.PointSeparateurEstSurAccessoire(point.mX)){
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                        Imperial distanceBord = point.getmX().substract(var.getPremierMur().getmX());
                        direction = var.mDirection;
                        var.AjouterSeparateur(new Separateur(point.mY,point.mX,distanceBord,var,new Polygone(Color.BLACK,points)));
                        return direction;
                    }
                }else {
                    if(mur.retourAir && mur.mPolygonePlanRetourAir.SeparateurEstDansPolygonesEstOuest(point)){
                        return null;
                    }

                    if(!var.PointSeparateurEstSurAccessoire(point.mY)) {
                        points.add(new PointImperial(point.mY, polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(1).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(1).mX));
                        Imperial distanceBord = point.getmY().substract(var.getPremierMur().getmY());
                        direction = var.mDirection;
                        var.AjouterSeparateur(new Separateur(point.mY,point.mX,distanceBord,var,new Polygone(Color.BLACK,points)));
                        return direction;
                    }
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

    private Mur getMurCliqueElevation(Cote cote, PointImperial point,boolean interieur)
    {
        for(Mur mur : cote.getMurs())
        {
            if(mur.polygonesElevation(interieur).PointEstDansPolygone(point))
            {
                return mur;
            }
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

        for(Cote cote : cotes)
            cote.setmSalle(this);
    }

    public ArrayList<Polygone> getPolygonesPlan()
    {
        ArrayList<Polygone> polygones = new ArrayList<>();

        for (Cote cote : cotes)
        {
            polygones.addAll(cote.getPolygonesPlan());
        }

        if(ElementSelectionne instanceof Separateur)
        {
            Separateur sep = (Separateur) ElementSelectionne;

            if(sep.getmCote().getDirection().estHorizontal())
            {
                Imperial x = sep.getmCote().getmX().add(sep.getDistanceBordDeReference());
                Imperial y1 = sep.getmCote().getmY();
                Imperial y2 = y1.add(sep.getmCote().getmSalle().getEpaisseurMurs());
                polygones.add(new Polygone(Color.RED, new PointImperial(x, y1), new PointImperial(x, y2)));
            }
            else {
                Imperial y = sep.getmCote().getmY().add(sep.getDistanceBordDeReference());
                Imperial x1 = sep.getmCote().getmX();
                Imperial x2 = x1.add(sep.getmCote().getmSalle().getEpaisseurMurs());
                polygones.add(new Polygone(Color.RED, new PointImperial(x1, y), new PointImperial(x2, y)));
            }
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

    public Imperial getHauteurRetourAir() {
        return hauteurRetourAir;
    }

    public void setHauteurRetourAir(Imperial hauteurRetourAir) {
        this.hauteurRetourAir = hauteurRetourAir;
    }

    public Imperial getPositionRetourAir() {
        return positionRetourAir;
    }

    public void setPositionRetourAir(Imperial positionRetourAir) {
        this.positionRetourAir = positionRetourAir;
    }

    public Imperial getHauteurTrouRetourAir() {
        return hauteurTrouRetourAir;
    }

    public void setHauteurTrouRetourAir(Imperial hauteurTrouRetourAir) {
        this.hauteurTrouRetourAir = hauteurTrouRetourAir;
    }

    public Element getElementSelectionne()
    {
        return ElementSelectionne;
    }
}
