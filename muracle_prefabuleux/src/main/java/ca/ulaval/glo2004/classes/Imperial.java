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

    public int getFormeNormal() {
        return entier + (numerateur/denominateur);
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

        if(this.denominateur/this.numerateur < o.denominateur/o.numerateur){
            result = -1;
        } else if (this.denominateur/this.numerateur > o.denominateur/o.numerateur) {
            result = 1;
        }

        return result;
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

    @Override
    public String toString() {
        return entier + "\"" + numerateur + "/" + denominateur;
    }
}