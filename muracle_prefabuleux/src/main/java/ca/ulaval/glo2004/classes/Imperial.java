package ca.ulaval.glo2004.classes;

import jdk.vm.ci.code.site.ImplicitExceptionDispatch;

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
            return (double)entier + ((double)numerateur/(double)denominateur);

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

    public double getValue()
    {
        return entier + ((double)numerateur / denominateur);
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
        int newDenominateur = Math.max(denominateur, other.denominateur);

        int newNumerateur1 = (numerateur * 1024 / denominateur * newDenominateur / 1024);
        int newNumerateur2 = (other.numerateur * 1024 / other.denominateur * newDenominateur / 1024);

        int totalNumerator = newNumerateur1 + newNumerateur2;
        newEntier += totalNumerator / newDenominateur;

        return new Imperial(newEntier, totalNumerator % newDenominateur, newDenominateur);
    }

    public Imperial substract(Imperial other)
    {
        return this.add(other.negative());
    }

    public Imperial negative()
    {
        return new Imperial(-this.entier, -this.numerateur, this.denominateur);
    }


    public Imperial multiply(int multiplier)
    {
        return new Imperial(entier * multiplier, numerateur * multiplier, denominateur);
    }

    public Imperial divide(int divisor)
    {
        int newNumerator = (numerateur + entier * denominateur);
        int newDenominator = denominateur * divisor;
        int newEntier = newNumerator / newDenominator;

        return new Imperial(newEntier, newNumerator % newDenominator, newDenominator);
    }

    public Imperial mirror(Cote cote)
    {
        Utilitaire.Direction direction = cote.getDirection();

        if(direction.estHorizontal())
            return this.substract(cote.getmSalle().getLargeur()).abs();
        else
            return this.substract(cote.getmSalle().getProfondeur()).abs();
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
        String[] parts = imperial.trim().replace("\"", "").split(" ");

        if(parts.length < 1)
            return null;

        try
        {
            entier = Integer.parseInt(parts[0].trim());

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

        Imperial imp = new Imperial(entier, numerateur, denominateur);
        return imp;
    }

    public Imperial clone() {
        return new Imperial(entier, numerateur, denominateur);
    }

    public Imperial abs() {
        return new Imperial(Math.abs(this.entier), Math.abs(this.numerateur), Math.abs(this.denominateur));
    }

    public static Imperial fromValue(double value)
    {
       int entier = (int) value;
       int denominateur = 1024;
       int numerateur = (int) ((value - entier) * denominateur);

       return new Imperial(entier, numerateur, denominateur);
    }

}