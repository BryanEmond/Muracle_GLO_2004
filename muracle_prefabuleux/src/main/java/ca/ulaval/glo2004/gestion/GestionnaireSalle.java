package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.dto.MurDTO;
import ca.ulaval.glo2004.classes.*;
import ca.ulaval.glo2004.classes.dto.SeparateurDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GestionnaireSalle {

    private Salle mSalle;
    private Cote mCoteCourant;
    private Mur mMurCourant;
    private Accessoire mAccessoire;
    private Separateur mSeparateur;
    private boolean mDecoupage;
    private String filePath;
    private Salle salleActive;

    private boolean vuePlan;

    private boolean vueCote;

    public GestionnaireSalle()
    {
    }

    public void test(int pixelX, int pixelY)
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

        Separateur sn1 = new Separateur(new Imperial(0), new Imperial(0), new Imperial(48), nord, null);
        Separateur sn2 = new Separateur(new Imperial(0), new Imperial(0), new Imperial(96), nord, null);

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

        this.mMurCourant = mn1;
        this.mSeparateur = sn2;
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
    public void onClickEvents(int pixelX, int pixelY, Utilitaire.AccessoireEnum accessoireEnum, boolean interieur ){
        if(accessoireEnum == Utilitaire.AccessoireEnum.Separateur){
            Utilitaire.Direction direction = salleActive.separateur(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY));
            this.salleActive.getCote(direction).setMurs(updateMurs(direction));
//            Salle salle = new Salle(salleActive.getmY(),salleActive.getmX(),salleActive.getEpaisseurMurs(), salleActive.getMarge(),salleActive.getHauteur(),salleActive.getLargeur(), salleActive.getProfondeur(), salleActive.isVuePlan(), cote);
        }
    }

    public void selectionnerElement(int pixelX, int pixelY, Utilitaire.AccessoireEnum accessoireEnum,Utilitaire.Direction direction, boolean interieur ){
        if(accessoireEnum == Utilitaire.AccessoireEnum.Separateur){
            Element element = salleActive.selection(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY),direction,interieur);

//            Salle salle = new Salle(salleActive.getmY(),salleActive.getmX(),salleActive.getEpaisseurMurs(), salleActive.getMarge(),salleActive.getHauteur(),salleActive.getLargeur(), salleActive.getProfondeur(), salleActive.isVuePlan(), cote);
        }
    }


    public ArrayList<Mur> updateMurs(Utilitaire.Direction direction){
        ArrayList<Mur> murs = new ArrayList<>();
        ArrayList<Separateur> listSep = this.salleActive.getCote(direction).getSeparateurs();
        for(int i = 0;i <= listSep.size(); i++){

            if(direction == Utilitaire.Direction.NORD || direction == Utilitaire.Direction.SUD) {
                Salle salle = this.salleActive;
                Cote cote = salleActive.getCote(direction);
                Imperial y = this.salleActive.getCote(direction).getmY();
                ;
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

                murs.add(new Mur(salle, cote, y, x, largeur));
            }
            else{
                Salle salle = this.salleActive;
                Cote cote = salleActive.getCote(direction);
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
                    largeur = this.salleActive.getLargeur().substract(listSep.get(i - 1).getDistanceBordDeReference()).substract(this.salleActive.getEpaisseurMurs());

                murs.add(new Mur(salle, cote, y, x, largeur));
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

    public void exporerSVG(String cheminDossier)
    {

    }

    public void VueCote(String cheminDossier)
    {

    }



    public MurDTO getMurSelectionne()
    {
        if(this.mMurCourant == null)
            return null;

        return new MurDTO(this.mMurCourant);
    }

    public void editMurSelectionne(Imperial largeur)
    {
        //this.mMurCourant.setmX(x);
        //this.mMurCourant.setmY(y);
        if(this.mMurCourant != null)
        {
            this.mMurCourant.setmLargeur(largeur);
            System.out.println("Mur modifier à : " + largeur);
        }
    }

    public SeparateurDTO getSeparateurSelectionne()
    {
        if(this.mSeparateur == null)
            return null;

        Separateur separateurPrec = mSeparateur.getSeparateurPrecedent();
        Imperial position = mSeparateur.getDistanceBordDeReference().clone();
        Imperial positionRelative;

        if(separateurPrec == null)
            positionRelative = position.clone();
        else
            positionRelative = position.add(separateurPrec.getDistanceBordDeReference().negative());

        return new SeparateurDTO(position, positionRelative);
    }

    public boolean editSeparateurSelectionne(Imperial posRelative)
    {
        Separateur sepPrec = mSeparateur.getSeparateurPrecedent();
        Separateur sepSuivant = mSeparateur.getSeparateurSuivant();

        Utilitaire.Direction direction = mSeparateur.getmCote().getDirection();
        Imperial tailleSalle = direction.estHorizontal() ? salleActive.getLargeur() : salleActive.getProfondeur();

        double min = sepPrec == null ? 0 : sepPrec.getDistanceBordDeReference().getValue();
        double max = sepSuivant == null ? tailleSalle.getValue() : sepSuivant.getDistanceBordDeReference().getValue();
        double value = posRelative.getValue();

        if(value <= 0 || (value + min) > max)
            return false;

        if(sepPrec == null)
            mSeparateur.setDistanceBordDeReference(posRelative.clone());
        else
            mSeparateur.setDistanceBordDeReference(sepPrec.getDistanceBordDeReference().add(posRelative));

        return true;
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
        return salleActive;
    }

    public void ChangementDeVueVersPlan()
    {
        this.vuePlan = true;
        this.vueCote = false;
    }

    public void ChangementDeVueVersCote()
    {
        this.vuePlan = false;
        this.vueCote = true;
    }

}
