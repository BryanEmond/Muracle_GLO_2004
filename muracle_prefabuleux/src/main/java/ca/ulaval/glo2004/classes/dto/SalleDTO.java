package ca.ulaval.glo2004.classes.dto;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Salle;

public class SalleDTO {
    private Imperial largeur;
    private Imperial profondeur;
    private Imperial hauteur;
    private Imperial epaisseurMurs;
    private Imperial largeurPli;
    private int anglePliSoudure;

    public SalleDTO(Salle salle)
    {
        this(salle.getLargeur().clone(), salle.getProfondeur().clone(), salle.getHauteur().clone(),
                salle.getEpaisseurMurs().clone(), salle.getLargeurPliSoudure().clone(), salle.getAnglePliSoudure());
    }

    public SalleDTO(Imperial largeur, Imperial profondeur, Imperial hauteur, Imperial epaisseurMurs, Imperial largeurPli, int anglePliSoudure) {
        this.largeur = largeur;
        this.profondeur = profondeur;
        this.hauteur = hauteur;
        this.epaisseurMurs = epaisseurMurs;
        this.largeurPli = largeurPli;
        this.anglePliSoudure = anglePliSoudure;
    }

    public Imperial getLargeur() {
        return largeur;
    }

    public Imperial getProfondeur() {
        return profondeur;
    }

    public Imperial getHauteur() {
        return hauteur;
    }

    public Imperial getEpaisseurMurs() {
        return epaisseurMurs;
    }

    public Imperial getLargeurPli() {
        return largeurPli;
    }

    public int getAnglePliSoudure() {
        return anglePliSoudure;
    }
}
