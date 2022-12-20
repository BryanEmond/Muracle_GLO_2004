package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.dto.AccessoireDTO;
import ca.ulaval.glo2004.classes.dto.MurDTO;
import ca.ulaval.glo2004.classes.*;
import ca.ulaval.glo2004.classes.dto.SalleDTO;
import ca.ulaval.glo2004.classes.dto.SeparateurDTO;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class GestionnaireSalle implements Serializable{

    private Utilitaire.Direction mCoteCourant;
    private Mur mMurCourant;
    private Accessoire mAccessoire;
    private Separateur mSeparateur;

    private PointImperial DernierPointSelectionner;

    private boolean mDecoupage;
    private String filePath;
    public Salle salleActive;

    private boolean vuePlan;
    private boolean vueCote;
    private boolean vueInterieur;
    private boolean addToNextMur = false;
    private boolean isbtnDecoupageVisible = false;
    private Imperial largeurRetourAir;
    private Imperial spaceBetween;
    private Mur murSelectionner;

    Stack<Salle> undoList = new Stack<>();
    Stack<Salle> redoList = new Stack<>();



    public GestionnaireSalle()
    {
    }

    public void creerSalleDefaut()
    {
        vuePlan =true;
        vueCote = false;

        Cote nord = new Cote(new Imperial(0), new Imperial(0), new Imperial(0), Utilitaire.Direction.NORD);
        Cote est = new Cote(new Imperial(0), new Imperial(138), new Imperial(0), Utilitaire.Direction.EST);
        Cote sud = new Cote(new Imperial(138), new Imperial(0), new Imperial(0), Utilitaire.Direction.SUD);
        Cote ouest = new Cote(new Imperial(0), new Imperial(0), new Imperial(0), Utilitaire.Direction.OUEST);

        Salle salle = new Salle(new ArrayList<>(Arrays.asList(nord, est, sud, ouest)));

        Mur mn1 = new Mur(salle, nord, new Imperial(0), new Imperial(0), new Imperial(48));
        Mur mn2 = new Mur(salle, nord, new Imperial(0), new Imperial(48), new Imperial(48));
        Mur mn3 = new Mur(salle, nord, new Imperial(0), new Imperial(96), new Imperial(48));

        Separateur sn1 = new Separateur(new Imperial(0), new Imperial(0), new Imperial(48), nord);
        Separateur sn2 = new Separateur(new Imperial(0), new Imperial(0), new Imperial(96), nord);

        nord.setSeparateurs(new ArrayList<>(Arrays.asList(sn1, sn2)));
        nord.setMurs(new ArrayList<>(Arrays.asList(mn1, mn2, mn3)));

        Mur ms1 = new Mur(salle, sud, new Imperial(138), new Imperial(0), new Imperial(144));
        sud.setMurs(new ArrayList<>(Arrays.asList(ms1)));

        Mur me1 = new Mur(salle, est, new Imperial(6), new Imperial(138), new Imperial(132));
        est.setMurs(new ArrayList<>(Arrays.asList(me1)));

        Mur mo1 = new Mur(salle, ouest, new Imperial(6), new Imperial(0), new Imperial(132));
        ouest.setMurs(new ArrayList<>(Arrays.asList(mo1)));
        this.salleActive = salle;

        for (Cote cote: salle.getCotes() ){
            cote.setmSalle(salle);
        }

        mn2.setRetourAir(true);
        this.mMurCourant = mn2;
        this.mSeparateur = sn2;
        setSpaceBetween(new Imperial(5));
    }

    public void enregistrerSalle(String path)
    {
        filePath  = filePath != null? filePath: path;
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(salleActive);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in d:/sale.ser");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void AjouterSeparateurVuePlan(int pixelX, int pixelY) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);
        Utilitaire.Direction direction = salleActive.separateur(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY));
        if(direction != null){
            changement(ancienneSalle);
            this.salleActive.getCote(direction).setMurs(updateMurs(direction));
        };
    }
    public void AjouterSeparateurVueElevation(int pixelX, int pixelY, boolean interieur, Utilitaire.Direction directionParams) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);

        boolean modification = salleActive.separateurElevation(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY), directionParams,interieur);
        if(modification){
            changement(ancienneSalle);
        }
        this.salleActive.getCote(directionParams).setMurs(updateMurs(directionParams));
    }

    public void selectionnerElementPlan(int pixelX, int pixelY,Utilitaire.Direction direction, boolean interieur ){
        salleActive.selectionPlan(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY),direction,interieur);
        if(salleActive.getElementSelectionne() instanceof Mur){
            setBtnDecoupageVisible(true);
            setMurSelectionner(salleActive.getElementSelectionne());
        }
        else{
            setBtnDecoupageVisible(false);
        }
    }

    public void selectionnerElementElevantion(int pixelX, int pixelY,Utilitaire.Direction direction, boolean interieur ){
        DernierPointSelectionner = Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY);
        mMurCourant = salleActive.selectionElevantion(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY),direction,interieur);
        if(salleActive.getElementSelectionne() instanceof Mur){
            setBtnDecoupageVisible(true);
            setMurSelectionner(salleActive.getElementSelectionne());
            getMurSelectionnerNoneDto().genererPolygoneELV(interieur);
        }
        else{
            setBtnDecoupageVisible(false);
        }
    }
    public void setMurSelectionner(Element elementSelectionne) {
        this.murSelectionner = (Mur)elementSelectionne;
    }
    public Mur getMurSelectionnerNoneDto(){
        return this.murSelectionner;
    }

    public boolean AjouterPorte(int pixelX, int pixelY,Utilitaire.Direction direction, boolean interieur ) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);

        boolean modification = salleActive.AjouterPorte(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY),direction,interieur);

        if(modification){
            changement(ancienneSalle);
        }

        return modification;
    }

    public void SupprimerPlan(int pixelX, int pixelY) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);

        Utilitaire.Direction direction = salleActive.SupprimerPlan(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY));
        if(direction != null){
            changement(ancienneSalle);
            this.salleActive.getCote(direction).setMurs(updateMurs(direction));
        }
    }

    public void SupprimerElevation(int pixelX, int pixelY,Utilitaire.Direction direction, boolean interieur ) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);

        boolean modification = salleActive.SupprimerElevation(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY),direction,interieur);

        if(modification){
            changement(ancienneSalle);
        }

        this.salleActive.getCote(direction).setMurs(updateMurs(direction));
    }

    public boolean AjouterPriseElectrique(int pixelX, int pixelY,Utilitaire.Direction direction, boolean interieur ) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);

        boolean modification = salleActive.AjouterPriseElectrique(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY),direction,interieur);

        if(modification){
            changement(ancienneSalle);
        }
        return modification;
    }

    public boolean AjouterRetourAirPlan(int pixelX, int pixelY) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);
        boolean modification =  salleActive.AjouterRetourAirPlan(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY));
        if(modification){
            changement(ancienneSalle);
        }
        return modification;
    }

    public boolean AjouterRetourAirElevation(int pixelX, int pixelY, Utilitaire.Direction direction, boolean interieur ) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);
        boolean modification = salleActive.AjouterRetourAirElevation(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY),direction,interieur);

        if(modification){
            changement(ancienneSalle);
        }
        return modification;
    }

    public boolean AjouterFenetre(int pixelX, int pixelY,Utilitaire.Direction direction, boolean interieur ) throws IOException, ClassNotFoundException {
        Salle ancienneSalle = Utilitaire.CopySalle(salleActive);
        boolean modification =  salleActive.AjouterFenetre(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY),direction,interieur);

        if(modification){
            changement(ancienneSalle);
        }
        return modification;
    }

    public void updateSalle()
    {
        Imperial yCoteSud = this.salleActive.getProfondeur().substract(this.salleActive.getEpaisseurMurs());
        Imperial xCoteEst = this.salleActive.getLargeur().substract(this.salleActive.getEpaisseurMurs());

        Cote oldNord = salleActive.getCote(Utilitaire.Direction.NORD);
        Cote oldEst = salleActive.getCote(Utilitaire.Direction.EST);
        Cote oldSud = salleActive.getCote(Utilitaire.Direction.SUD);
        Cote oldOuest = salleActive.getCote(Utilitaire.Direction.OUEST);

        Cote nord = new Cote(new Imperial(0), new Imperial(0), new Imperial(0), Utilitaire.Direction.NORD);
        nord.setmSalle(salleActive);
        nord.setMurs(oldNord.getMurs());
        nord.setAccessoires(oldNord.getAccessoires());
        nord.setSeparateurs(oldNord.getSeparateurs());

        Cote est = new Cote(new Imperial(0), xCoteEst, new Imperial(0), Utilitaire.Direction.EST);
        est.setmSalle(salleActive);
        est.setMurs(oldEst.getMurs());
        est.setAccessoires(oldEst.getAccessoires());
        est.setSeparateurs(oldEst.getSeparateurs());

        Cote sud = new Cote(yCoteSud, new Imperial(0), new Imperial(0), Utilitaire.Direction.SUD);
        sud.setmSalle(salleActive);
        sud.setMurs(oldSud.getMurs());
        sud.setAccessoires(oldSud.getAccessoires());
        sud.setSeparateurs(oldSud.getSeparateurs());

        Cote ouest = new Cote(new Imperial(0), new Imperial(0), new Imperial(0), Utilitaire.Direction.OUEST);
        ouest.setmSalle(salleActive);
        ouest.setMurs(oldOuest.getMurs());
        ouest.setAccessoires(oldOuest.getAccessoires());
        ouest.setSeparateurs(oldOuest.getSeparateurs());

        salleActive.setCotes(new ArrayList<>(Arrays.asList(nord, est, sud, ouest)));
        for (Cote cote : salleActive.getCotes())
        {
            cote.setMurs(updateMurs(cote.getDirection()));
        }

    }

    public ArrayList<Mur> updateMurs(Utilitaire.Direction direction){
        ArrayList<Mur> murs = new ArrayList<>();
        ArrayList<Separateur> listSep = this.salleActive.getCote(direction).getSeparateurs();
        Salle salle = this.salleActive;
        Cote cote = salleActive.getCote(direction);
        ArrayList<Mur> oldMurs = cote.getMurs();

        if(listSep.size() == 0)
        {
            Mur mur;

            if(direction == Utilitaire.Direction.NORD){
                mur = new Mur(salle, cote, new Imperial(0), new Imperial(0), salle.getLargeur());
                mur.setRetourAir(this.salleActive.getCote(direction).getMurs().get(0).aRetourAir());
                mur.setRetourAirePolygone(this.salleActive.getCote(direction).getMurs().get(0).getPolygonePlanRetourAir());
                mur.setRetourAirePolygoneElv(this.salleActive.getCote(direction).getMurs().get(0).getPolygonePlanRetourAir());

            }
            else if(direction == Utilitaire.Direction.SUD)
            {
                mur = new Mur(salle, cote, salle.getProfondeur().substract(salle.getEpaisseurMurs()), new Imperial(0), salle.getLargeur());
                mur.setRetourAir(this.salleActive.getCote(direction).getMurs().get(0).aRetourAir());
                mur.setRetourAirePolygone(this.salleActive.getCote(direction).getMurs().get(0).getPolygonePlanRetourAir());
                mur.setRetourAirePolygoneElv(this.salleActive.getCote(direction).getMurs().get(0).getPolygonePlanRetourAir());
            }
            else if(direction == Utilitaire.Direction.EST)
            {
                Imperial taille = salle.getProfondeur().substract(salle.getEpaisseurMurs()).substract(salle.getEpaisseurMurs());
                mur = new Mur(salle, cote, salle.getEpaisseurMurs(), salle.getLargeur().substract(salle.getEpaisseurMurs()), taille);
                mur.setRetourAir(this.salleActive.getCote(direction).getMurs().get(0).aRetourAir());
                mur.setRetourAirePolygone(this.salleActive.getCote(direction).getMurs().get(0).getPolygonePlanRetourAir());
                mur.setRetourAirePolygoneElv(this.salleActive.getCote(direction).getMurs().get(0).getPolygonePlanRetourAir());
            }
            else
            {
                Imperial taille = salle.getProfondeur().substract(salle.getEpaisseurMurs()).substract(salle.getEpaisseurMurs());
                mur = new Mur(salle, cote, salle.getEpaisseurMurs(), new Imperial(0), taille);
                mur.setRetourAir(this.salleActive.getCote(direction).getMurs().get(0).aRetourAir());
                mur.setRetourAirePolygone(this.salleActive.getCote(direction).getMurs().get(0).getPolygonePlanRetourAir());
                mur.setRetourAirePolygoneElv(this.salleActive.getCote(direction).getMurs().get(0).getPolygonePlanRetourAir());
            }

            return new ArrayList<>(Arrays.asList(mur));
        }

        for(int i = 0;i <= listSep.size(); i++){

            if(direction == Utilitaire.Direction.NORD || direction == Utilitaire.Direction.SUD) {
                Imperial y = this.salleActive.getCote(direction).getmY();
                Imperial x;
                Imperial largeur;

                if (i == 0)
                    x = new Imperial(0);
                else
                    x = listSep.get(i - 1).getDistanceBordDeReference();

                if (i != listSep.size())
                    largeur = listSep.get(i).getDistanceBordDeReference().substract(x);
                else
                    largeur = this.salleActive.getLargeur().substract(listSep.get(i - 1).getDistanceBordDeReference());
                Mur mur = new Mur(salle, cote, y, x, largeur);
                mur.accessoires();
                mur.genererPolygonePlan();
                murs.add(mur);
            }
            else{
                Imperial y;
                Imperial x = this.salleActive.getCote(direction).getmX();
                Imperial largeur;

                if (i == 0)
                    y = this.salleActive.getEpaisseurMurs();
                else
                    y = listSep.get(i - 1).getDistanceBordDeReference();

                if (i != listSep.size())
                    largeur = listSep.get(i).getDistanceBordDeReference().substract(y);
                else
                    largeur = this.salleActive.getProfondeur().substract(listSep.get(i - 1).getDistanceBordDeReference()).substract(this.salleActive.getEpaisseurMurs());
                Mur mur = new Mur(salle, cote, y, x, largeur);
                mur.genererPolygonePlan();
                mur.genererPolygoneELV(!vueInterieur);
                murs.add(mur);
            }
            for(Mur oldMur : oldMurs) {
                if (oldMur.aRetourAir()) {
                    oldMur.genererPolygonePlanRetourAir();
                    Polygone polygoneRetourAir = oldMur.getPolygonePlanRetourAir();
                    ArrayList<PointImperial> pointRetourAir = polygoneRetourAir.getPoints();
                    for (Mur newMur : murs) {
                        ArrayList<PointImperial> murPointImperial = newMur.getPolygonePlan().getPoints();
                        if (direction == Utilitaire.Direction.NORD || direction == Utilitaire.Direction.SUD) {
                            double valueX1RetourAir = pointRetourAir.get(0).getmX().getValue();
                            double valueX1MurPoint =murPointImperial.get(0).getmX().getValue();
                            double valueX2RetourAir= pointRetourAir.get(2).getmX().getValue() ;
                            double valueX2MurPoint=murPointImperial.get(2).getmX().getValue();
                            Imperial epaisseur = this.salleActive.getEpaisseurMurs();
                            if (valueX1RetourAir >= valueX1MurPoint
                                    && valueX2RetourAir <= valueX2MurPoint
                                    && valueX1RetourAir >= epaisseur.getValue()){
                                if(murs.size() - 1 == murs.indexOf(newMur)){
                                    double largeurMur = newMur.getmLargeur().getValue();
                                    double retourAirPlusEpaisseur = oldMur.getLargeurRetourAir().add(epaisseur).add(epaisseur.divide(2)).getValue();
                                    if(retourAirPlusEpaisseur <= largeurMur){
                                        newMur.setRetourAir(oldMur.aRetourAir());
                                        newMur.setLargeurRetourAir(oldMur.getLargeurRetourAir());
                                    }
                                }else{
                                    newMur.setRetourAir(oldMur.aRetourAir());
                                    newMur.setLargeurRetourAir(oldMur.getLargeurRetourAir());
                                }
                            }

                        }else{
                            double valueY1RetourAir = pointRetourAir.get(0).getmY().getValue();
                            double valueY1MurPoint =murPointImperial.get(0).getmY().getValue();
                            double valueY2RetourAir= pointRetourAir.get(2).getmY().getValue() ;
                            double valueY2MurPoint=murPointImperial.get(2).getmY().getValue();
                            if (valueY1RetourAir >= valueY1MurPoint
                                    && valueY2RetourAir <= valueY2MurPoint &&
                                    valueY1RetourAir >= this.salleActive.getEpaisseurMurs().getValue()) {
                                newMur.setRetourAir(oldMur.aRetourAir());
                                newMur.setLargeurRetourAir(oldMur.getLargeurRetourAir());
                            }
                        }

                    }
                }
            }
        }

        return murs;
    }
    public void chargerSalle(String path)
    {
        filePath = path;
        try{
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            this.salleActive = (Salle)in.readObject();
            in.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void undo() throws IOException, ClassNotFoundException {
        Salle salle = Utilitaire.CopySalle(this.salleActive);
        redoList.add(salle);
        if(undoList.size() > 0) {
            salleActive = undoList.pop();
        }
    }

    public void changement(Salle salle) {
        undoList.add(salle);
    }

    public void redo() throws IOException, ClassNotFoundException {
        Salle salle = Utilitaire.CopySalle(this.salleActive);
        undoList.add(salle);
        if(redoList.size() > 0) {
            salleActive = redoList.pop();
        }
    }

    public void exporerSVG(String cheminDossier)
    {

    }

    public void VueCote(String cheminDossier)
    {

    }

    public SalleDTO getSalleSelectionne()
    {
        if(this.salleActive == null)
            return null;

        return new SalleDTO(salleActive);
    }

    public int editSalleSelectionne(SalleDTO salle)
    {
        double largeurMin = 0;
        double profondeurMin = 0;

        for(Cote cote : salleActive.getCotes())
        {
            Separateur dernierSep = cote.getDernierSeparateur();
            double distValue = (dernierSep != null ? dernierSep.getDistanceBordDeReference().getValue() : 0) + 2 * salle.getEpaisseurMurs().getValue() + 5;

            if(cote.getDirection().estHorizontal())
            {
                if(distValue > largeurMin)
                    largeurMin = distValue;
            }
            else
            {
                if(distValue > profondeurMin)
                    profondeurMin = distValue;
            }
        }

        if(salle.getLargeur().getValue() < largeurMin)
            return 1;

        if(salle.getProfondeur().getValue() < profondeurMin)
            return 2;

        if(salle.getHauteurTrouRetourAir().getValue() > salle.getEpaisseurMurs().getValue() - 1)
            return 3;

        if(salle.getPositionRetourAir().getValue() + salle.getHauteurRetourAir().getValue() > salle.getHauteur().getValue() - 1)
            return 4;

        if(salle.getPoidsMateriaux() < 0)
            return 5;

        if(salle.getPoidsMaxPanneau() < 0)
            return 6;

        this.salleActive.setHauteur(salle.getHauteur());
        this.salleActive.setLargeur(salle.getLargeur());
        this.salleActive.setProfondeur(salle.getProfondeur());
        this.salleActive.setEpaisseurMurs(salle.getEpaisseurMurs());
        this.salleActive.setAnglePliSoudure(salle.getAnglePliSoudure());
        this.salleActive.setLargeurPliSoudure(salle.getLargeurPli());
        this.salleActive.setHauteurRetourAir(salle.getHauteurRetourAir());
        this.salleActive.setPositionRetourAir(salle.getPositionRetourAir());
        this.salleActive.setHauteurTrouRetourAir(salle.getHauteurTrouRetourAir());
        this.salleActive.setEpaisseurMateriaux(salle.getEpaisseurMateriaux());
        this.salleActive.setPoidsMateriaux(salle.getPoidsMateriaux());
        this.salleActive.setPoidsMaxPanneau(salle.getPoidsMaxPanneau());

        updateSalle();
        return 0;
    }

    public MurDTO getMurSelectionne()
    {
        Element element = salleActive.getElementSelectionne();

        if(!(element instanceof Mur))
            return null;

        Mur mur = (Mur) element;
        return new MurDTO(mur);
    }


    public boolean editMurSelectionne(Imperial largeurRetourAir)
    {
        Element element = salleActive.getElementSelectionne();
        if(!(element instanceof Mur))
            return false;

        Mur mur = (Mur) element;

        if(largeurRetourAir.getValue() > mur.getmLargeur().getValue() - 1)
            return false;

        Mur murCopie = mur.copieMur();
        murCopie.setLargeurRetourAir(largeurRetourAir);
        murCopie.genererPolygoneRetourAirELV(false);

        if(salleActive.anyAccessoireInterfereAvecRetourAir(murCopie.getPolygoneElvRetourAir(), mur.getCote(), true))
            return false;

        mur.setLargeurRetourAir(largeurRetourAir);
        mur.genererPolygonePlanRetourAir();
        return true;
    }

    public SeparateurDTO getSeparateurSelectionne()
    {
        Element element = salleActive.getElementSelectionne();
        if(!(element instanceof Separateur))
            return null;

        Separateur separateur = (Separateur) element;
        Separateur separateurPrec = separateur.getSeparateurPrecedent();
        Imperial position = separateur.getDistanceBordDeReference().clone();
        Imperial positionRelative;

        if(separateurPrec == null)
            positionRelative = position.clone();
        else
            positionRelative = position.add(separateurPrec.getDistanceBordDeReference().negative());

        return new SeparateurDTO(position, positionRelative);
    }

    public boolean editSeparateurSelectionne(Imperial posRelative)
    {
        Element element = salleActive.getElementSelectionne();
        if(!(element instanceof Separateur))
            return false;

        Separateur separateur = (Separateur) element;
        Separateur sepPrec = separateur.getSeparateurPrecedent();
        Separateur sepSuivant = separateur.getSeparateurSuivant();

        Utilitaire.Direction direction = separateur.getmCote().getDirection();
        Imperial tailleSalle = direction.estHorizontal() ? salleActive.getLargeur() : salleActive.getProfondeur();

        double min = (sepPrec == null ? 0 : sepPrec.getDistanceBordDeReference().getValue());
        double max = (sepSuivant == null ? tailleSalle.getValue() : sepSuivant.getDistanceBordDeReference().getValue()) - salleActive.getEpaisseurMurs().getValue();
        double value = posRelative.getValue();

        if(value <= 5 || (value + min) > (max - 5))
            return false;

        Cote cote = separateur.getmCote();
        Imperial nouvellePosition;

        if(sepPrec == null)
            nouvellePosition = posRelative.clone();
        else
            nouvellePosition = sepPrec.getDistanceBordDeReference().add(posRelative);

        if(cote.PointSeparateurEstSurAccessoire(nouvellePosition, false))
            return false;

        ArrayList<Mur> murs = separateur.getmCote().getMurs();

        int separateurIndex = separateur.getmCote().getSeparateurs().indexOf(separateur);
        Mur murPrecedent = murs.get(separateurIndex);
        Mur murSuivant = murs.get(separateurIndex + 1);

        if(murPrecedent.aRetourAir() && murPrecedent.getLargeurRetourAir().getValue() >= posRelative.getValue())
            return false;

        if(murSuivant.aRetourAir() && murSuivant.getLargeurRetourAir().getValue() >= sepSuivant.getDistanceBordDeReference().substract(nouvellePosition).getValue())
            return false;

        separateur.setDistanceBordDeReference(nouvellePosition);
        separateur.getmCote().setMurs(updateMurs(direction));
        return true;
    }

    public AccessoireDTO getAccessoireSelectionne()
    {
        Element element = salleActive.getElementSelectionne();
        if(!(element instanceof Accessoire))
            return null;

        Accessoire accessoire = (Accessoire) element;
        return new AccessoireDTO(accessoire);
    }

    public int editAccessoireSelectionne(AccessoireDTO accessoireDTO)
    {
        ArrayList<Accessoire> listAccessoire = new ArrayList<>(salleActive.getCote(mCoteCourant).getAccessoires());

        Element element = salleActive.getElementSelectionne();

        if(!(element instanceof Accessoire))
            return -1;

        listAccessoire.removeIf(e -> e.Id == ((Accessoire) element).getId());

        Accessoire accessoire = (Accessoire) element;

        Mur mur = salleActive.getMurCliqueElevation(salleActive.getCote(mCoteCourant),new PointImperial(accessoire.getmX().clone(),accessoire.getmY().clone()),true);

        switch(accessoireDTO.getTypeAccessoire()) {
            case  Porte:

                Accessoire accessoireClone = accessoire.clonePorte();
                accessoireClone.setmHauteur(accessoireDTO.getHauteur());
                accessoireClone.setmLargeur(accessoireDTO.getLargeur());
                accessoireClone.setCote(salleActive.getCote(mCoteCourant));
                accessoireClone.genererPolygoneELV(false);

                Porte porteAccessoire = (Porte) accessoireClone;
                porteAccessoire.setmX(accessoireDTO.getX());
                porteAccessoire.setCote(salleActive.getCote(mCoteCourant));
                porteAccessoire.genererPolygoneELV(false);
                mur.genererPolygoneELV(false);

                if(this.salleActive.isAccessoirInterfereAvecRetourAir(porteAccessoire,salleActive.getCote(mCoteCourant),true)){
                    return -1;
                }

                for (PointImperial pointImperial:accessoireClone.getmPolygoneElevation(false).getPoints()){
                    for (Accessoire accessoireCote: listAccessoire) {
                        accessoireCote.genererPolygoneELV(false);
                        if(accessoireCote.getmPolygoneElevation(false).PointEstDansPolygone(pointImperial)){
                            return -1;
                        }
                    }

                    if(!mur.polygonesElevation(false).PointEstDansPolygone(pointImperial)){
                        return -1;
                    }
                }

                for (Accessoire accessoireCote: listAccessoire) {
                    accessoireCote.genererPolygoneELV(false);
                    for (PointImperial pointAccessoire: accessoireCote.getmPolygoneElevation(false).getPoints()) {
                        accessoireCote.genererPolygoneELV(false);
                        if(porteAccessoire.getmPolygoneElevation(false).PointEstDansPolygone(pointAccessoire)){
                            return -1;
                        }
                    }
                }

                accessoire.setmHauteur(accessoireDTO.getHauteur());
                accessoire.setmLargeur(accessoireDTO.getLargeur());
                accessoire.setmX(accessoireDTO.getX());

                break;
            case  Fenetre:
                Fenetre fenetreClone = (Fenetre) accessoire.cloneFenetre();
                fenetreClone.setmX(accessoireDTO.getX());
                ((Fenetre)fenetreClone).setmY(accessoireDTO.getY());
                fenetreClone.setmHauteur(accessoireDTO.getHauteur());
                fenetreClone.setmLargeur(accessoireDTO.getLargeur());
                fenetreClone.setBordure(accessoireDTO.getBordureFenetre());
                fenetreClone.setCote(salleActive.getCote(mCoteCourant));
                fenetreClone.genererPolygoneELV(false);
                Polygone bordurePoints =((Fenetre)fenetreClone).genererPolygoneELV(false).get(1);
                mur.genererPolygoneELV(false);
                if(this.salleActive.isAccessoirInterfereAvecRetourAir(fenetreClone,salleActive.getCote(mCoteCourant),true)){
                    return -1;
                }
                for (PointImperial pointImperial:bordurePoints.getPoints()){
                    for (Accessoire accessoireCote: listAccessoire) {
                        accessoireCote.genererPolygoneELV(false);
                        if(accessoireCote.getmPolygoneElevation(false).PointEstDansPolygone(pointImperial)){
                            return -1;
                        }
                    }

                    if(!mur.polygonesElevation(false).PointEstDansPolygone(pointImperial)){
                        return -1;
                    }

                }

                for (Accessoire accessoireCote: listAccessoire) {
                    accessoireCote.genererPolygoneELV(false);
                    for (PointImperial pointAccessoire: accessoireCote.getmPolygoneElevation(false).getPoints()) {
                        accessoireCote.genererPolygoneELV(false);
                        if(bordurePoints.PointEstDansPolygone(pointAccessoire)){
                            return -1;
                        }
                    }
                }

                accessoire.setmHauteur(accessoireDTO.getHauteur());
                accessoire.setmLargeur(accessoireDTO.getLargeur());
                ((Fenetre)accessoire).setBordure(accessoireDTO.getBordureFenetre());
                accessoire.setmX(accessoireDTO.getX());
                accessoire.setmY(accessoireDTO.getY());

                break;
            case PriseElectrique:
                PrisesElectrique priseElectriqueClone = (PrisesElectrique) accessoire.clonePriseElectrique();
                priseElectriqueClone.setmX(accessoireDTO.getX());
                ((PrisesElectrique)priseElectriqueClone).setmY(accessoireDTO.getY());
                priseElectriqueClone.setmHauteur(accessoireDTO.getHauteur());
                priseElectriqueClone.setmLargeur(accessoireDTO.getLargeur());
                priseElectriqueClone.setCote(salleActive.getCote(mCoteCourant));
                priseElectriqueClone.genererPolygoneELV(false);
                mur.genererPolygoneELV(false);
                if(this.salleActive.isAccessoirInterfereAvecRetourAir(priseElectriqueClone,salleActive.getCote(mCoteCourant),true)){
                    return -1;
                }
                for (PointImperial pointImperial:priseElectriqueClone.getmPolygoneElevation(false).getPoints()){
                    for (Accessoire accessoireCote: listAccessoire) {
                        accessoireCote.genererPolygoneELV(false);
                        if(accessoireCote.getmPolygoneElevation(false).PointEstDansPolygone(pointImperial)){
                            return -1;
                        }
                    }

                    if(!mur.polygonesElevation(false).PointEstDansPolygone(pointImperial)){
                        return -1;
                    }
                }


                accessoire.setmHauteur(accessoireDTO.getHauteur());
                accessoire.setmLargeur(accessoireDTO.getLargeur());
                accessoire.setmX(accessoireDTO.getX());
                accessoire.setmY(accessoireDTO.getY());
                break;
        }
        return 0;
    }

    public boolean GetvuePlan()
    {
        return this.vuePlan;
    }

    public boolean GetvueCote()
    {
        return this.vueCote;
    }

    public Accessoire accessoireSelectionne(String cheminDossier)
    {
        return this.mAccessoire;
    }

    /*public void AjouteSeparateurAPartirVuePlan(PointImperial point)
    {
        this.mSalle.separateur(point);
    }*/

    public void quitter()
    {
        //TODO Ne pas oublier d'implémenter la vérification de sauvegarde un jour
        System.exit(0);
    }

    public void zoomer(int quantite, int x, int y)
    {
        Conversion.getConversion().zoomer(quantite, x, y);
    }

    public Salle getSalleActive(){
        return this.salleActive;
    }

    public void ChangementDeVueVersPlan()
    {
        this.vuePlan = true;
        this.vueCote = false;
        salleActive.deselectionnerElement();
    }

    public void ChangementDeVueVersCote(boolean vueInterieur)
    {
        this.vuePlan = false;
        this.vueCote = true;
        this.vueInterieur = vueInterieur;
        salleActive.deselectionnerElement();
    }

    public Utilitaire.Direction getmCoteCourant() {
        return mCoteCourant;
    }

    public void setmCoteCourant(Utilitaire.Direction mCoteCourant) {
        this.mCoteCourant = mCoteCourant;
    }

    public void dragAndDropElement(){
        /*Imperial posXOriginel = accessoireSelect.getX();
        Imperial posYOriginel = accessoireSelect.getY();
        int newX = posXOriginel.getEntier() + differenceX;
        int newY = posYOriginel.getEntier() + differenceY;
        gestionnaireSalle.editAccessoireSelectionne(new AccessoireDTO(new Imperial(newX), new Imperial(newY),accessoireSelect.getHauteur(), accessoireSelect.getLargeur(), accessoireSelect.getBordureFenetre(), accessoireSelect.getTypeAccessoire()));
        */


    }

    public boolean exporterPanneau(String cheminFichier, Mur mur, boolean panneauInterieur) throws IOException {
        if(!mur.estAssezLeger(panneauInterieur))
            return false;

        int indexPolygones = panneauInterieur ? 1 : 0;
        ArrayList<Polygone> polygones = mur.genererpolygonesElevationDecoupage().get(indexPolygones);

        ExporteurSVG.EnregistrerSVG(cheminFichier, polygones);
        return true;
    }

    public void setBtnDecoupageVisible(boolean bool){this.isbtnDecoupageVisible = bool;}
    public boolean getBtnDecoupageVisible(){return this.isbtnDecoupageVisible;}

    public static ArrayList<Polygone> genererGridPlacement(int x, int y, int lenght, int width, Imperial spaceBetween){
        PointImperial pointImperialPanelDepart = Conversion.getConversion().trouverCoordonneImperial(x,y);
        PointImperial pointImperialPanelFin = Conversion.getConversion().trouverCoordonneImperial(lenght,width);
        PointImperial pointImperialFin;
        Imperial space = spaceBetween;
        ArrayList<Polygone> polygones = new ArrayList<Polygone>();
        do{
            ArrayList<PointImperial> pointImperials = new ArrayList<>();
            PointImperial pointImperialDebut = new PointImperial(pointImperialPanelDepart.getmX().add(space),pointImperialPanelDepart.getmY());
            pointImperialFin = new PointImperial(pointImperialPanelDepart.getmX().add(space),pointImperialPanelFin.getmY());
            pointImperials.add(pointImperialDebut);
            pointImperials.add(pointImperialFin);
            polygones.add(new Polygone(Color.LIGHT_GRAY,pointImperials));
            space = space.add(spaceBetween);
        }while(pointImperialFin.getmX().compareTo(pointImperialPanelFin.getmX()) == -1);
        space = spaceBetween;
        do{
            ArrayList<PointImperial> pointImperials = new ArrayList<>();
            PointImperial pointImperialDebut = new PointImperial(pointImperialPanelDepart.getmX(),pointImperialPanelDepart.getmY().add(space));
            pointImperialFin = new PointImperial(pointImperialPanelFin.getmX(),pointImperialPanelDepart.getmY().add(space));
            pointImperials.add(pointImperialDebut);
            pointImperials.add(pointImperialFin);
            polygones.add(new Polygone(Color.LIGHT_GRAY,pointImperials));
            space = space.add(spaceBetween);
        }while(pointImperialFin.getmY().compareTo(pointImperialPanelFin.getmY()) == -1);
        return polygones;
    }

    public Imperial getSpaceBetween() {
        return spaceBetween;
    }

    public void setSpaceBetween(Imperial spaceBetween) {
        this.spaceBetween = spaceBetween;
    }
}
