package ca.ulaval.glo2004.classes;
public class Imperial implements Comparable<Imperial> {

    int entier ;
    int numerateur ;
    int denominateur ;

    public Imperial(int entier,int numerateur,int denominateur) {
        this.entier = entier;
        this.numerateur = numerateur;
        this.denominateur = denominateur;
    }

    public int getEntier() {
        return entier;
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
}