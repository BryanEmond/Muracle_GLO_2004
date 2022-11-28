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

    public boolean AjouterAccessoire(PointImperial point, Utilitaire.Direction direction, boolean interieur, Accessoire accessoire, Imperial marge)
    {
        //TODO NE PAS METTRE SUR UN SEPARATEUR OU UN RETOUR D'AIR ou autre accessoire
        Cote cote = getCote(direction);

        Imperial debutX = point.getmX().substract(marge);
        Imperial finX = point.getmX().add(accessoire.getmLargeur()).add(marge);

        for (Separateur sep : cote.getSeparateurs()) {
            Imperial posSep = sep.getDistanceBordDeReference();

            if(!direction.estHorizontal())
                posSep = posSep.substract(cote.getmSalle().getEpaisseurMurs());

            if(!interieur)
                posSep = posSep.mirror(cote);

            if (posSep.getValue() >= debutX.getValue() - 1 && posSep.getValue() <= finX.getValue() + 1)
                return false;
        }

        double sallelargeur = 0;
        if (cote.mDirection.equals(Utilitaire.Direction.NORD) || cote.mDirection.equals(Utilitaire.Direction.SUD)) {
            sallelargeur = cote.getmSalle().largeur.getValue();
        } else {
            sallelargeur = cote.getmSalle().profondeur.substract(cote.getmSalle().getEpaisseurMurs().multiply(2)).getValue();

            if(!interieur)
                sallelargeur = cote.getmSalle().profondeur.add(cote.getmSalle().getEpaisseurMurs().multiply(3)).getValue();
        }

        if (point.mX.getValue() + accessoire.getmLargeur().getValue() + .5 > sallelargeur - cote.getmSalle().getEpaisseurMurs().getValue())
            return false;

        if(interieur)
        {
            if(point.mX.getValue() - 1 < cote.getmSalle().getEpaisseurMurs().getValue())
                return false;
        }
        else {
            if(point.mX.getValue() - 1 < cote.getmSalle().getEpaisseurMurs().getValue() * 3)
                return false;
        }

        if (point.mY.getValue() + accessoire.getmHauteur().getValue() > cote.getmSalle().getHauteur().getValue() ||
                point.mY.getValue() < 1)
            return false;

        if (!interieur)
            accessoire.mX = accessoire.mX.add(accessoire.getmLargeur()).mirror(cote);

        accessoire.setmPerceExtérieur(true);
        ArrayList<Polygone> polygones = accessoire.genererPolygoneELV(!interieur);

        for (PointImperial pointImperial : polygones.get(1).getPoints()) {
            for (Accessoire a : cote.accessoires) {
                if (a.mPolygoneElevation.PointEstDansPolygone(pointImperial)) {
                    return false;
                }
            }
        }

        cote.accessoires.add(accessoire);
        ElementSelectionne = accessoire;
        return true;
    }

    public boolean AjouterFenetre(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        Cote cote = getCote(direction);

        Fenetre fenetre = new Fenetre(point.mY, point.mX, interieur, interieur, new Imperial(24), new Imperial(24));
        fenetre.setCote(cote);
        fenetre.setmPerceExtérieur(true);
        fenetre.setmPerceInterieur(true);

        return AjouterAccessoire(point, direction, interieur, fenetre, new Imperial(0, 1, 8));
    }

    public boolean AjouterPorte(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        // TODO NE PAS METRE SUR SEPARATEUR OU RETOUR D'AIR ou autre accessoire
        Cote cote = getCote(direction);

        if(!interieur)
            point.mX = point.mX.add(new Imperial(38));

        if(cote.PointEstDansCoteElevation(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if (polygone == null){
                return false;
            }


            int sallelargeur=0;
            if (cote.mDirection.equals(Utilitaire.Direction.NORD) || cote.mDirection.equals(Utilitaire.Direction.SUD))
            {
                sallelargeur = cote.getmSalle().largeur.entier;
            }
            else{sallelargeur = cote.getmSalle().profondeur.entier;}


            if(!interieur)
                point.mX = point.mX.mirror(cote);
            int comparaison = point.mX.entier + cote.getmSalle().getEpaisseurMurs().entier;
            if (comparaison + 39 > sallelargeur){
                return false;
            }

            Porte porte = new Porte(point.mY, point.mX,interieur,interieur, new Imperial(38),new Imperial(88), null);

            porte.setCote(cote);
            porte.setmPerceExtérieur(true);
            Polygone portePolygone = porte.genererPolygoneELV(interieur).get(0);
            for (PointImperial pointImperial:portePolygone.getPoints()) {

                for (Accessoire accessoire: cote.accessoires) {
                    accessoire.genererPolygoneELV(interieur);
                    if (accessoire.mPolygoneElevation.PointEstDansPolygone(pointImperial)) {
                        return false;
                    }

                    for(PointImperial pointDeAccessoire: accessoire.getmPolygoneElevation(interieur).getPoints()) {
                        if(portePolygone.PointEstDansPolygone(pointDeAccessoire)){
                            return false;
                        }
                    }
                }

                if (!polygone.PointEstDansPolygone(pointImperial)) {
                    return false;
                }
            }
                //TODO verifier si l'accessoire est dans la porte
               /* for (PointImperial pointImperiall: cote.accessoires.get(0).getmPolygoneElevation(interieur).getPoints()){
                    for(Porte porteIndex: ){
                        if(porteIndex.mPolygoneElevation.PointEstDansPolygone(pointImperial)){return false;}
                    }
                }*/


                //for(){}

                  /*  ArrayList<Polygone> polygonesObstruant = cote.getPolygoneElevation(!interieur);
                    for (int i = 0; i < cote.getAccessoires().size(); i++){
                        //polygonesObstruant.addAll(cote.getAccessoires().get(i).genererPolygoneELV(!interieur));
                        polygonesObstruant.add(cote.getAccessoires().get(i).getmPolygoneElevation(interieur));}
                  //  for (int ii=0; ii<polygonesObstruant.size(); ii++){
                        if(polygonesObstruant.get().PointEstDansPolygone(pointImperial))
                            return false;
                    }
/*
                    if (cote.mDirection.equals(Utilitaire.Direction.NORD) || cote.mDirection.equals(Utilitaire.Direction.SUD))
                    {
                        for (int i =0; i < cote.getMurs().size();i++)
                        {
                            Imperial pointDebutMur = cote.getMurs().get(i).mX.clone();
                            Imperial pointFinMur = cote.getMurs().get(i).mX.clone().add(cote.getMurs().get(i).mLargeur);
                        }
                    }
                    else{
                        for (int i = 0; i < cote.getMurs().size();i++)
                        {
                            Imperial pointDebutMur = cote.getMurs().get(i).mY.clone();
                            Imperial pointFinMur = cote.getMurs().get(i).mY.clone().add(cote.getMurs().get(i).mLargeur);
                        }
                    }
                    */
                    /*for (int i =0; i < cote.getMurs().size();i++)
                    {
                        Imperial pointDebutMur = cote.getMurs().get(i).mX.clone();
                        Imperial pointFinMur = cote.getMurs().get(i).mX.clone().add(cote.getMurs().get(i).mLargeur);

                        if ( pointDebutMur.entier < pointImperial.mX.entier && pointImperial.mX.entier > pointFinMur.entier ){
                            return false;
                        }

                    }*/





            cote.accessoires.add(porte);
            ElementSelectionne = porte;
            return  true;}
        return  false;   }

    public  Utilitaire.Direction SupprimerPlan(PointImperial point){
        for (Cote var : cotes)
        {
            if(var.PointEstDansCote(point)){
                for (Separateur separateur: var.separateurs)
                {
                    separateur.genererPolygonePlan();
                    if(separateur.mPolygonePlan.PointEstDansPolygone(point)){
                        var.separateurs.remove(var.separateurs.indexOf(separateur));
                        deselectionnerElement();
                        return var.mDirection;
                    }
                }

                for (Mur mur: var.murs)
                {
                    if(mur.aRetourAir() && mur.mPolygonePlanRetourAir.SeparateurEstDansPolygoneNordSud(point)){
                        mur.setRetourAir(false);
                        deselectionnerElement();
                        return var.mDirection;
                    }
                }

            };
        }
        return null;
    }

    public void SupprimerElevation(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        Cote cote = getCote(direction);
        if(cote.PointEstDansCoteElevation(point)){
            for (Accessoire accessoire: cote.accessoires) {
                accessoire.genererPolygoneELV(!interieur);
                if(accessoire.mPolygoneElevation.PointEstDansPolygone(point)){
                    cote.accessoires.remove(accessoire);
                    deselectionnerElement();
                    break;
                }
            }

            Imperial distanceBord = point.mX;

            if(!interieur)
            {
                if(direction.estHorizontal())
                    distanceBord = distanceBord.substract(largeur).abs();
                else
                    distanceBord = distanceBord.substract(profondeur).abs();
            }

            for (Separateur separateur: cote.separateurs) {
                separateur.genererPolygoneELV(interieur);

                Imperial distSep = separateur.distanceBordDeReference;
                if(!separateur.getmCote().mDirection.estHorizontal())
                    distSep = distSep.substract(epaisseurMurs);

                if(distSep.getFormeNormal() + 0.5 >= distanceBord.getFormeNormal() &&
                        distSep.getFormeNormal() - 0.5 <= distanceBord.getFormeNormal()){
                    cote.separateurs.remove(separateur);
                    deselectionnerElement();
                    break;
                }
            }

            for (Mur mur: cote.murs)
            {
                mur.genererPolygoneRetourAirELV(!interieur);
                if(mur.aRetourAir() && mur.mPolygoneElevationRetourAir.SeparateurEstDansPolygoneNordSud(point)){
                    mur.setRetourAir(false);
                }
            }
        }
    }

    public boolean AjouterPriseElectrique(PointImperial point, Utilitaire.Direction direction,boolean interieur){
        //TODO NE PAS METTRE SUR UN SEPARATEUR OU RETOUR D'AIR ou autre accessoire
        Cote cote = getCote(direction);
        if(cote.PointEstDansCoteElevation(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if (polygone == null){
                return false;
            }

            int sallelargeur=0;

            if (cote.mDirection.equals(Utilitaire.Direction.NORD) || cote.mDirection.equals(Utilitaire.Direction.SUD))
            {
                sallelargeur = cote.getmSalle().largeur.entier;
            }
            else{sallelargeur = cote.getmSalle().profondeur.entier;}


            if(!interieur)
                point.mX = point.mX.mirror(cote);
            int comparaison = point.mX.entier + cote.getmSalle().getEpaisseurMurs().entier * 2 ;
            if (comparaison + 3 > sallelargeur){
                return false;
            }

            PrisesElectrique prisesElectrique = new PrisesElectrique(point.mY, point.mX,interieur,interieur, new Imperial(2),new Imperial(4), null)    ;
            prisesElectrique.setCote(cote);
            prisesElectrique.setmPerceExtérieur(false);
            ArrayList<Polygone> prisesElectriques = prisesElectrique.genererPolygoneELV(!interieur);

            for (PointImperial pointImperial:prisesElectriques.get(0).getPoints())
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

            cote.accessoires.add(prisesElectrique);
            ElementSelectionne = prisesElectrique;
            return  true;
        }
        return  false;
    }

    public boolean AjouterRetourAirPlan(PointImperial point)
    {
        Mur mur = getMurCliquePlan(point);
        if(mur != null && mur.getmLargeur().getValue() >= mur.getLargeurRetourAir().getValue())
        {
            mur.setRetourAir(!mur.aRetourAir());

            ElementSelectionne = mur;
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
            ElementSelectionne = mur;
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

    public Mur selectionElevantion(PointImperial point, Utilitaire.Direction direction,boolean interieur){

        Cote cote = getCote(direction);
        if(cote == null)
            return null;

        Mur murSelectionne = null;
        for (Mur mur: cote.murs) {
            mur.genererPolygoneELV(!interieur);
            if(mur.polygonesElevation(interieur).PointEstDansPolygone(point)){
                ElementSelectionne = mur;
                murSelectionne = mur;
            }
        }

        for (Separateur separateur: cote.separateurs) {
            separateur.genererPolygoneELV(!interieur);
            if(separateur.mPolygoneElevation.PointEstDansPolygone(point)){
                ElementSelectionne = separateur;
            }
        }

        for (Accessoire accessoire: cote.accessoires) {
            accessoire.genererPolygoneELV(!interieur);
            if(accessoire.getmPolygoneElevation(interieur).PointEstDansPolygone(point)){
                ElementSelectionne = accessoire;
            }
        }
        return murSelectionne;
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
    public void separateurElevation(PointImperial point,Utilitaire.Direction direction,boolean interieur){
        ArrayList<PointImperial> points = new ArrayList<>();
        Cote cote = getCote(direction);
        if(cote.PointEstDansCoteElevation(point)){
            Polygone polygone = getPolygoneMurElevation(cote,point,interieur);
            if(polygone != null){
                if(!cote.PointSeparateurEstSurAccessoire(point.mX, !interieur)) {
                    Mur mur = getMurCliqueElevation(cote, point, interieur);

                    if (mur.retourAir && mur.mPolygoneElevationRetourAir.SeparateurEstDansPolygoneNordSud(point)) {
                        return;
                    }

                    points.add(new PointImperial(point.mX, polygone.points.get(0).mY));
                    points.add(new PointImperial(point.mX, polygone.points.get(0).mY));
                    points.add(new PointImperial(point.mX, polygone.points.get(2).mY));
                    points.add(new PointImperial(point.mX, polygone.points.get(2).mY));

                    Imperial distanceBord = point.getmX();

                    if(!direction.estHorizontal())
                        distanceBord = distanceBord.add(cote.getmSalle().getEpaisseurMurs());

                    if (!interieur) {
                        if (direction.estHorizontal()){
                            distanceBord = distanceBord.substract(largeur).abs();
                        }
                        else{
                            distanceBord = distanceBord.substract(profondeur).substract(cote.getmSalle().getEpaisseurMurs().multiply(2)).abs();
                        }
                    }
                    Separateur separateur = new Separateur(point.mY,point.mX,distanceBord,cote,new Polygone(Color.BLACK,points));
                    cote.AjouterSeparateur(separateur);
                    ElementSelectionne = separateur;
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

                    if(!var.PointSeparateurEstSurAccessoire(point.mX, false)){
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(0).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                        points.add(new PointImperial(point.mX,polygone.points.get(2).mY));
                        Imperial distanceBord = point.getmX();
                        direction = var.mDirection;
                        if(epaisseurMurs.getValue() < distanceBord.getValue() && largeur.substract(epaisseurMurs).getValue() > distanceBord.getValue() ){
                            Separateur separateur = new Separateur(point.mY,point.mX,distanceBord,var,new Polygone(Color.BLACK,points));
                            var.AjouterSeparateur(separateur);
                            ElementSelectionne = separateur;
                            return direction;
                        }
                        return null;
                    }
                }else {
                    if(mur.retourAir && mur.mPolygonePlanRetourAir.SeparateurEstDansPolygonesEstOuest(point)){
                        return null;
                    }

                    if(!var.PointSeparateurEstSurAccessoire(point.mY, false)) {
                        points.add(new PointImperial(point.mY, polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(0).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(1).mX));
                        points.add(new PointImperial(point.mY, polygone.points.get(1).mX));
                        Imperial distanceBord = point.getmY();
                        direction = var.mDirection;
                        if(epaisseurMurs.getValue() < distanceBord.getValue() && profondeur.substract(epaisseurMurs).getValue() > distanceBord.getValue()){
                            Separateur separateur = new Separateur(point.mY,point.mX,distanceBord,var,new Polygone(Color.BLACK,points));
                            var.AjouterSeparateur(separateur);
                            ElementSelectionne = separateur;
                            return direction;
                        }
                        return null;
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

    public Mur getMurCliqueElevation(Cote cote, PointImperial point,boolean interieur)
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
                polygones.add(new Polygone(Color.BLUE, new PointImperial(x, y1), new PointImperial(x, y2)));
            }
            else {
                Imperial y = sep.getmCote().getmY().add(sep.getDistanceBordDeReference());
                Imperial x1 = sep.getmCote().getmX();
                Imperial x2 = x1.add(sep.getmCote().getmSalle().getEpaisseurMurs());
                polygones.add(new Polygone(Color.BLUE, new PointImperial(x1, y), new PointImperial(x2, y)));
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

    public void deselectionnerElement()
    {
        ElementSelectionne = null;
    }

    public Element getElementSelectionne()
    {
        return ElementSelectionne;
    }
}
