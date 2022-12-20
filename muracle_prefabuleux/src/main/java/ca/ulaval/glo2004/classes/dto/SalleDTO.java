package ca.ulaval.glo2004.classes.dto;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Salle;

import java.io.Serializable;

public class SalleDTO implements Serializable {
    private Imperial largeur;
    private Imperial profondeur;
    private Imperial hauteur;
    private Imperial epaisseurMurs;
    private Imperial largeurPli;
    private int anglePliSoudure;
    Imperial hauteurRetourAir;
    Imperial positionRetourAir;
    Imperial hauteurTrouRetourAir;

    Imperial epaisseurMateriaux;

    private double poidsMateriaux;
    private double poidsMaxPanneau;

    public SalleDTO(Salle salle)
    {
        this(salle.getLargeur().clone(), salle.getProfondeur().clone(), salle.getHauteur().clone(),
                salle.getEpaisseurMurs().clone(), salle.getLargeurPliSoudure().clone(), salle.getAnglePliSoudure(),
                salle.getHauteurRetourAir().clone(), salle.getPositionRetourAir().clone(), salle.getHauteurTrouRetourAir().clone(),
                salle.getEpaisseurMateriaux().clone(), salle.getPoidsMateriaux(), salle.getPoidsMaxPanneau());
    }

    public SalleDTO(Imperial largeur, Imperial profondeur, Imperial hauteur, Imperial epaisseurMurs, Imperial largeurPli,
                    int anglePliSoudure, Imperial hauteurRetourAir, Imperial positionRetourAir, Imperial hauteurTrouRetourAir,
                    Imperial epaisseurMateriaux, double poidsMateriaux, double poidsMaxPanneau) {
        this.largeur = largeur;
        this.profondeur = profondeur;
        this.hauteur = hauteur;
        this.epaisseurMurs = epaisseurMurs;
        this.largeurPli = largeurPli;
        this.anglePliSoudure = anglePliSoudure;
        this.hauteurRetourAir = hauteurRetourAir;
        this.positionRetourAir = positionRetourAir;
        this.hauteurTrouRetourAir = hauteurTrouRetourAir;
        this.epaisseurMateriaux = epaisseurMateriaux;
        this.poidsMateriaux = poidsMateriaux;
        this.poidsMaxPanneau = poidsMaxPanneau;
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

    public Imperial getHauteurRetourAir() {
        return hauteurRetourAir;
    }

    public Imperial getPositionRetourAir() {
        return positionRetourAir;
    }

    public Imperial getHauteurTrouRetourAir() {
        return hauteurTrouRetourAir;
    }

    public Imperial getEpaisseurMateriaux() {
        return epaisseurMateriaux;
    }

    public double getPoidsMateriaux() {
        return poidsMateriaux;
    }

    public void setPoidsMateriaux(double poidsMateriaux) {
        this.poidsMateriaux = poidsMateriaux;
    }

    public double getPoidsMaxPanneau() {
        return poidsMaxPanneau;
    }

    public void setPoidsMaxPanneau(double poidsMaxPanneau) {
        this.poidsMaxPanneau = poidsMaxPanneau;
    }
}
