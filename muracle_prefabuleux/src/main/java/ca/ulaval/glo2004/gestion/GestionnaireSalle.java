package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Salle;

public class GestionnaireSalle {

    private Salle salleActive;

    public void creerSalle(Imperial tailleX, Imperial tailleY)
    {
//        Salle salle = new Salle(tailleX, tailleY);
//        salleActive = salle;
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
