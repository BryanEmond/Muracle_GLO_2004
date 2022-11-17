package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.Cote;
import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Salle;

import java.io.File;
import java.util.ArrayList;

public class GestionnaireSalle {

    private Salle salleActive;

    public void creerSalle(Imperial mY, Imperial mX, Imperial epaisseurMurs, Imperial marge, Imperial hauteur, Imperial largeur, Imperial profondeur, boolean vuePlan, ArrayList<Cote> cotes)
    {
        Salle salle = new Salle(mY, mX, epaisseurMurs, marge, hauteur, largeur, profondeur, vuePlan, cotes);
        System.out.println(salle);
        salleActive = salle;
    }

    public void enregistrerSalle()
    {

    }

    public void chargerSalle(File file)
    {
        System.out.println(file.getName());

    }

    public void exporerSVG(String cheminDossier)
    {

    }

    public void quitter()
    {
        //TODO Ne pas oublier d'implémenter la vérification de sauvegarde un jour
        System.exit(0);
    }

}
