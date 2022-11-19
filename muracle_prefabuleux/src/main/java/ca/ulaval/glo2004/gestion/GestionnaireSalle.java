package ca.ulaval.glo2004.gestion;

import ca.ulaval.glo2004.classes.*;

import java.awt.*;

public class GestionnaireSalle {

    private Salle mSalle;
    private Cote mCoteCourant;
    private Mur mMurCourant;
    private Accessoire mAccessoire;
    private boolean mDecoupage;

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



    public void VuePlan(String cheminDossier)
    {

    }

    public void VueCote(String cheminDossier)
    {

    }

    public Mur murSelectionne(Mur mur)
    {
        return this.mMurCourant;
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

}
