package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.dto.MurDTO;
import ca.ulaval.glo2004.classes.*;
import ca.ulaval.glo2004.classes.dto.SeparateurDTO;

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
        Cote est = new Cote(new Imperial(0), new Imperial(1), new Imperial(0), Utilitaire.Direction.EST);
        Cote sud = new Cote(new Imperial(20), new Imperial(0), new Imperial(0), Utilitaire.Direction.SUD);
        Cote ouest = new Cote(new Imperial(20), new Imperial(0), new Imperial(0), Utilitaire.Direction.OUEST);

        creerSalle(new Imperial(0), new Imperial(0),
                new Imperial(1), new Imperial(1),
                new Imperial(100),
                new Imperial(20), new Imperial(20),
                true, new ArrayList<>(Arrays.asList(nord, est, sud, ouest)));
    }

    public void test(int pixelX, int pixelY)
    {
        PointImperial point = Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY);
        salleActive.separateur(point);
    }

    public void creerSalle(Imperial mY, Imperial mX, Imperial epaisseurMurs, Imperial marge, Imperial hauteur, Imperial largeur, Imperial profondeur, boolean vuePlan, ArrayList<Cote> cotes)
    {


        Salle salle = new Salle(mY, mX,epaisseurMurs, marge,hauteur,largeur, profondeur, vuePlan, cotes);

        Mur mn1 = new Mur(salle, cotes.get(0), new Imperial(0), new Imperial(0), new Imperial(10),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));

        Mur mn2 = new Mur(salle, cotes.get(0), new Imperial(0), new Imperial(10), new Imperial(5),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));

        Mur mn3 = new Mur(salle, cotes.get(0), new Imperial(0), new Imperial(15), new Imperial(5),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));

        Separateur sn1 = new Separateur(new Imperial(0), new Imperial(0), new Imperial(10), cotes.get(0), salle, null);
        Separateur sn2 = new Separateur(new Imperial(0), new Imperial(0), new Imperial(15), cotes.get(0), salle, null);

        cotes.get(0).setSeparateurs(new ArrayList<>(Arrays.asList(sn1, sn2)));
        cotes.get(0).setMurs(new ArrayList<>(Arrays.asList(mn1, mn2, mn3)));

        Mur ms1 = new Mur(salle, cotes.get(2), new Imperial(19), new Imperial(0), new Imperial(20),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));
        cotes.get(2).setMurs(new ArrayList<>(Arrays.asList(ms1)));

        Mur me1 = new Mur(salle, cotes.get(1), new Imperial(1), new Imperial(0), new Imperial(18),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));
        cotes.get(1).setMurs(new ArrayList<>(Arrays.asList(me1)));

        Mur mo1 = new Mur(salle, cotes.get(3), new Imperial(1), new Imperial(19), new Imperial(18),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));
        cotes.get(3).setMurs(new ArrayList<>(Arrays.asList(mo1)));
        salleActive = salle;

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

    public void editSeparateurSelectionne(Imperial posRelative)
    {
        Separateur sepPrec = mSeparateur.getSeparateurPrecedent();

        if(sepPrec == null)
            mSeparateur.setDistanceBordDeReference(posRelative.clone());
        else
            mSeparateur.setDistanceBordDeReference(sepPrec.getDistanceBordDeReference().add(posRelative));
    }

    public Accessoire accessoireSelectionne(String cheminDossier)
    {
        return this.mAccessoire;
    }

    public void AjouteSeparateurAPartirVuePlan(PointImperial point)
    {
        this.mSalle.separateur(point);
    }



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
