package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.Cote;
import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Salle;

import java.util.ArrayList;

public class GestionnaireSalle {

    private Salle salleActive;

    public void creerSalle(Imperial mY, Imperial mX, Imperial epaisseurMurs, Imperial marge, Imperial hauteur, Imperial largeur, Imperial profondeur, boolean vuePlan, ArrayList<Cote> cotes)
    {
        Salle salle = new Salle(mY, mX, epaisseurMurs, marge, hauteur, largeur, profondeur, vuePlan, cotes);
        salleActive = salle;
    }

    public void enregistrerSalle()
    {

    }

    public void chargerSalle(String cheminFichier)
    {

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
