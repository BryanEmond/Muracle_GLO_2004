package ca.ulaval.glo2004.classes;

import java.io.Serializable;

public class Imperial implements Comparable<Imperial>,Serializable {

    int entier ;
    int numerateur ;
    int denominateur ;

    public Imperial(int entier)
    {
        this.entier = entier;
        this.numerateur = 0;
        this.denominateur = 1;
    }

    public Imperial(int entier,int numerateur,int denominateur) {
        this.entier = entier;
        this.numerateur = numerateur;
        this.denominateur = denominateur;
    }

    public int getEntier() {
        return entier;
    }

    public Double getFormeNormal() {
        if(denominateur != 0 && numerateur != 0)
            return (double)entier + (numerateur/denominateur);

        return (double)entier;
    }

    public void setEntier(int entier) {
        this.entier = entier;
    }

    public int getNumerateur() {
        return numerateur;
    }

    public void setNumerateur(int numerateur) {
        this.numerateur = numerateur;
    }

    public int getDenominateur() {
        return denominateur;
    }

    public void setDenominateur(int denominateur) {
        this.denominateur = denominateur;
    }

    @Override
    public int compareTo(Imperial o) {
        int result = 0;

        if(this.entier < o.entier){
            result = -1;
        } else if (this.entier > o.entier) {
            result = 1;
        }

        /*if(denominateur != 0 && numerateur != 0){
            if(this.denominateur/this.numerateur < o.denominateur/o.numerateur){
                result = -1;
            } else if (this.denominateur/this.numerateur > o.denominateur/o.numerateur) {
                result = 1;
            }
        }*/

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Imperial))
            return false;

        Imperial other = (Imperial) obj;

        int newDenominateur = PGCD(denominateur, other.getDenominateur());
        int newNumerateur1 = (numerateur * 1000 / denominateur * newDenominateur / 1000);
        int newNumerateur2 = (other.numerateur * 1000 / other.denominateur * newDenominateur / 1000);

        return other.entier == entier && newNumerateur1 == newNumerateur2;
    }

    public long getValue()
    {
        return entier + ((long)numerateur / denominateur);
    }

    /***
     * Trouver le plus grand commun diviseur
     * https://stackoverflow.com/questions/4009198/java-get-greatest-common-divisor
     */
    public int PGCD(int a, int b)
    {
        if(b == 0) return a;
        return PGCD(b, a%b);
    }

    public Imperial add(Imperial other)
    {
        int newEntier = entier + other.entier;
        int newDenominateur = PGCD(denominateur, other.denominateur);
        int newNumerateur1 = (numerateur * 1000 / denominateur * newDenominateur / 1000);
        int newNumerateur2 = (other.numerateur * 1000 / other.denominateur * newDenominateur / 1000);

        return new Imperial(newEntier, newNumerateur1 + newNumerateur2, newDenominateur);
    }

    public Imperial substract(Imperial other)
    {
        return new Imperial(other.negative().entier );
    }

    public Imperial negative()
    {
        return new Imperial(-this.entier, -this.numerateur, this.denominateur);
    }

    @Override
    public String toString() {
        if(numerateur == 0)
            return entier + "\"";

        return entier + "\" " + numerateur + "/" + denominateur;
    }

    public static Imperial fromString(String imperial)
    {
        int entier = 0;
        int numerateur = 0;
        int denominateur = 1;
        String[] parts = imperial.trim().split(" ");

        if(parts.length < 1)
            return null;

        try
        {
            entier = Integer.parseInt(parts[0].replace("\"", "").trim());

            if(parts.length == 2)
            {
                String[] partsFraction = parts[1].trim().split("/");
                if(partsFraction.length != 2)
                    return null;

                numerateur = Integer.parseInt(partsFraction[0]);
                denominateur = Integer.parseInt(partsFraction[1]);
            }

        }catch (NumberFormatException e)
        {
            return null;
        }

        if(denominateur == 0)
            return null;

        return new Imperial(entier, numerateur, denominateur);
    }

    public Imperial clone()
    {
        return new Imperial(entier, numerateur, denominateur);
    }

}