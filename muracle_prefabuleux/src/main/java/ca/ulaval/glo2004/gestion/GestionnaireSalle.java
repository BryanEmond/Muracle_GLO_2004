package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.Cote;
import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Mur;
import ca.ulaval.glo2004.classes.Salle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GestionnaireSalle {

    private Salle salleActive;

    public void creerSalle(Imperial mY, Imperial mX, Imperial epaisseurMurs, Imperial marge, Imperial hauteur, Imperial largeur, Imperial profondeur, boolean vuePlan, ArrayList<Cote> cotes)
    {


        Salle salle = new Salle(mY, mX,epaisseurMurs, marge,hauteur,largeur, profondeur, vuePlan, cotes);

        Mur mn1 = new Mur(salle, cotes.get(0), new Imperial(0), new Imperial(0), new Imperial(10),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));

        Mur mn2 = new Mur(salle, cotes.get(0), new Imperial(0), new Imperial(10), new Imperial(10),
                new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0), new Imperial(0));

        cotes.get(0).setMurs(new ArrayList<>(Arrays.asList(mn1, mn2)));

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
    }

    public void enregistrerSalle(String path)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(salleActive);
            out.close();
            fileOut.close();
            System.out.printf(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void chargerSalle(String path)
    {
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
