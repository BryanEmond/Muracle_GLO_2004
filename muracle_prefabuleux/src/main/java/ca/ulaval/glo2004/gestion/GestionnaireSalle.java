package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.dto.MurDTO;
import ca.ulaval.glo2004.classes.*;
import ca.ulaval.glo2004.classes.dto.SalleDTO;
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

    public GestionnaireSalle()
    {
    }

    public void test(int pixelX, int pixelY)
    {

    }

    public void creerSalleDefaut()
    {
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
    public void onClickEvents(int pixelX, int pixelY, Utilitaire.AccessoireEnum accessoireEnum, boolean interieur, Utilitaire.Direction directionParams){
        if(accessoireEnum == Utilitaire.AccessoireEnum.Separateur && !interieur && directionParams == null){
            Utilitaire.Direction direction = salleActive.separateur(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY));
            this.salleActive.getCote(direction).setMurs(updateMurs(direction));
        }
        else if(accessoireEnum == Utilitaire.AccessoireEnum.Separateur && !interieur){
            salleActive.separateirElevation(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY), directionParams);
            this.salleActive.getCote(directionParams).setMurs(updateMurs(directionParams));
        }
        else if(accessoireEnum == Utilitaire.AccessoireEnum.Separateur && interieur){
            salleActive.separateirElevation(Conversion.getConversion().trouverCoordonneImperial(pixelX, pixelY), directionParams);
            this.salleActive.getCote(directionParams).setMurs(updateMurs(directionParams));
        }
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
        nord.setAccessoires(oldNord.getAccessoires());
        nord.setSeparateurs(oldNord.getSeparateurs());

        Cote est = new Cote(new Imperial(0), xCoteEst, new Imperial(0), Utilitaire.Direction.EST);
        est.setAccessoires(oldEst.getAccessoires());
        est.setSeparateurs(oldEst.getSeparateurs());

        Cote sud = new Cote(yCoteSud, new Imperial(0), new Imperial(0), Utilitaire.Direction.SUD);
        sud.setAccessoires(oldSud.getAccessoires());
        sud.setSeparateurs(oldSud.getSeparateurs());

        Cote ouest = new Cote(new Imperial(0), new Imperial(0), new Imperial(0), Utilitaire.Direction.OUEST);
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

        if(listSep.size() == 0)
        {
            Mur mur;

            if(direction == Utilitaire.Direction.NORD)
                mur = new Mur(salle, cote, new Imperial(0), new Imperial(0), salle.getLargeur());
            else if(direction == Utilitaire.Direction.SUD)
                mur = new Mur(salle, cote, salle.getProfondeur().substract(salle.getEpaisseurMurs()), new Imperial(0), salle.getLargeur());
            else if(direction == Utilitaire.Direction.EST)
            {
                Imperial taille = salle.getProfondeur().substract(salle.getEpaisseurMurs()).substract(salle.getEpaisseurMurs());
                mur = new Mur(salle, cote, salle.getEpaisseurMurs(), salle.getLargeur().substract(salle.getEpaisseurMurs()), taille);
            }
            else
            {
                Imperial taille = salle.getProfondeur().substract(salle.getEpaisseurMurs()).substract(salle.getEpaisseurMurs());
                mur = new Mur(salle, cote, salle.getEpaisseurMurs(), new Imperial(0), taille);
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

                murs.add(new Mur(salle, cote, y, x, largeur));
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

        this.salleActive.setHauteur(salle.getHauteur());
        this.salleActive.setLargeur(salle.getLargeur());
        this.salleActive.setProfondeur(salle.getProfondeur());
        this.salleActive.setEpaisseurMurs(salle.getEpaisseurMurs());
        this.salleActive.setAnglePliSoudure(salle.getAnglePliSoudure());
        this.salleActive.setLargeurPliSoudure(salle.getLargeurPli());

        updateSalle();
        return 0;
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

        double min = (sepPrec == null ? 0 : sepPrec.getDistanceBordDeReference().getValue());
        double max = (sepSuivant == null ? tailleSalle.getValue() : sepSuivant.getDistanceBordDeReference().getValue()) - salleActive.getEpaisseurMurs().getValue();
        double value = posRelative.getValue();

        if(value <= 5 || (value + min) > (max - 5))
            return false;

        if(sepPrec == null)
            mSeparateur.setDistanceBordDeReference(posRelative.clone());
        else
            mSeparateur.setDistanceBordDeReference(sepPrec.getDistanceBordDeReference().add(posRelative));

        mSeparateur.getmCote().setMurs(updateMurs(direction));
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
