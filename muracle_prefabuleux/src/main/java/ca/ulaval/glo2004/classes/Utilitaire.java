package ca.ulaval.glo2004.classes;

import java.io.Serializable;

public abstract class Utilitaire implements Serializable {
    public enum AccessoireEnum{
        Porte,
        Fenetre,
        PriseElectrique,
        RetourAir,
        Supprimer,
        Separateur
    }
    public enum Direction {
        NORD,
        SUD,
        EST,
        OUEST
    }
}
