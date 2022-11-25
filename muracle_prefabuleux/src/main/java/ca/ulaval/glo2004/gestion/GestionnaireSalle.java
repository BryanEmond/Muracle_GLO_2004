package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.dto.MurDTO;
import ca.ulaval.glo2004.classes.*;
import ca.ulaval.glo2004.classes.dto.SeparateurDTO;
import sun.tools.jconsole.JConsole;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GestionnaireSalle {

    private Salle mSalle;
    private Cote mCoteCourant;
    private Mur mMurCourant;
    private Accessoire mAccessoire;
    private Separateur mSeparateur;
    private boolean mDecoupage;
    private String filePath;
    private Salle salleActive;

    public GestionnaireSalle()
    {
        Cote nord = new Cote(new Imperial(0), new Imperial(0), new Imperial(0), Utilitaire.Direction.NORD);
        Cote est = new Cote(new Imperial(0), new Imperial(19), new Imperial(0), Utilitaire.Direction.EST);
        Cote sud = new Cote(new Imperial(19), new Imperial(0), new Imperial(0), Utilitaire.Direction.SUD);
        Cote ouest = new Cote(new Imperial(0), new Imperial(0), new Imperial(0), Utilitaire.Direction.OUEST);

        creerSalle(new Imperial(0), new Imperial(0),
                new Imperial(10), new Imperial(1),
                new Imperial(100),
                new Imperial(20), new Imperial(20),
                true, new ArrayList<>(Arrays.asList(nord, est, sud, ouest)));
    }

    public void creerSalle(Imperial mY, Imperial mX, Imperial epaisseurMurs, Imperial marge, Imperial hauteur, Imperial largeur, Imperial profondeur, boolean vuePlan, ArrayList<Cote> cotes)
    {


        Salle salle = new Salle(mY, mX,epaisseurMurs, marge,hauteur,largeur, profondeur, vuePlan, cotes);

        Mur mn1 = new Mur(salle, salle.getCote(Utilitaire.Direction.NORD), new Imperial(0), new Imperial(0), new Imperial(10));

        Mur mn2 = new Mur(salle, salle.getCote(Utilitaire.Direction.NORD), new Imperial(0), new Imperial(10), new Imperial(5));

        Mur mn3 = new Mur(salle, salle.getCote(Utilitaire.Direction.NORD), new Imperial(0), new Imperial(15), new Imperial(5));

        Separateur sn1 = new Separateur(new Imperial(0), new Imperial(0), new Imperial(10), salle.getCote(Utilitaire.Direction.NORD), null);
        Separateur sn2 = new Separateur(new Imperial(0), new Imperial(0), new Imperial(15), salle.getCote(Utilitaire.Direction.NORD), null);

        salle.getCote(Utilitaire.Direction.NORD).setSeparateurs(new ArrayList<>(Arrays.asList(sn1, sn2)));
        salle.getCote(Utilitaire.Direction.NORD).setMurs(new ArrayList<>(Arrays.asList(mn1, mn2, mn3)));

        Mur ms1 = new Mur(salle, salle.getCote(Utilitaire.Direction.SUD), new Imperial(19), new Imperial(0), new Imperial(20));
        salle.getCote(Utilitaire.Direction.SUD).setMurs(new ArrayList<>(Arrays.asList(ms1)));

        Mur me1 = new Mur(salle, salle.getCote(Utilitaire.Direction.EST), new Imperial(1), new Imperial(19), new Imperial(18));
        salle.getCote(Utilitaire.Direction.EST).setMurs(new ArrayList<>(Arrays.asList(me1)));

        Mur mo1 = new Mur(salle, salle.getCote(Utilitaire.Direction.OUEST), new Imperial(1), new Imperial(0), new Imperial(18));
        salle.getCote(Utilitaire.Direction.OUEST).setMurs(new ArrayList<>(Arrays.asList(mo1)));
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
    public ArrayList<Mur> updateMurs(Utilitaire.Direction direction){
        ArrayList<Mur> murs = new ArrayList<>();
        ArrayList<Separateur> listSep = this.salleActive.getCote(direction).getSeparateurs();
        for(int i = 0;i <= listSep.size(); i++){
            if(direction == Utilitaire.Direction.NORD || direction == Utilitaire.Direction.SUD){
                murs.add(new Mur(this.salleActive, this.salleActive.getCote(direction),
                        direction == Utilitaire.Direction.NORD?this.salleActive.getCote(direction).getmY():this.salleActive.getCote(direction).getmY().substract(this.salleActive.getEpaisseurMurs()),
                        i==0?new Imperial(0):listSep.get(i-1).getDistanceBordDeReference(),
                        i!=listSep.size()?listSep.get(i).getDistanceBordDeReference():this.salleActive.getLargeur().substract(listSep.get(i-1).getDistanceBordDeReference())));
            }
            else{
                murs.add(new Mur(this.salleActive, this.salleActive.getCote(direction),
                        this.salleActive.getCote(direction).getmY(),
                        i==0?new Imperial(0):listSep.get(i-1).getDistanceBordDeReference(),
                        i==listSep.size()?listSep.get(i).getDistanceBordDeReference():this.salleActive.getLargeur()));
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

        float min = sepPrec == null ? 0 : sepPrec.getDistanceBordDeReference().getValue();
        float max = sepSuivant == null ? tailleSalle.getValue() : sepSuivant.getDistanceBordDeReference().getValue();
        float value = posRelative.getValue();

        if(value < 0 || (value - min) > max)
            return false;

        if(sepPrec == null)
            mSeparateur.setDistanceBordDeReference(posRelative.clone());
        else
            mSeparateur.setDistanceBordDeReference(sepPrec.getDistanceBordDeReference().add(posRelative));

        return true;
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


}
