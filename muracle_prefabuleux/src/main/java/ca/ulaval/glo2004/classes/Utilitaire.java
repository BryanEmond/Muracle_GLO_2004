package ca.ulaval.glo2004.classes;

import java.io.Serializable;

public abstract class Utilitaire implements Serializable {
    public enum AccessoireEnum{
        Porte,
        Fenetre,
        PriseElectrique,
        RetourAir,
        Supprimer,
        Separateur,
    }
    public enum Direction {
        NORD("Nord", true),
        SUD("Sud", true),
        EST("Est", false),
        OUEST("Ouest", false);

        private String name;
        private boolean horizontal;

        Direction(String name, boolean horizontal)
        {
            this.name = name;
            this.horizontal = horizontal;
        }

        public String getName() {
            return name;
        }

        public boolean estHorizontal() {
            return horizontal;
        }

        @Override
        public String toString() {
            return "{ Direction : " + getName() + " }";
        }
    }
}
