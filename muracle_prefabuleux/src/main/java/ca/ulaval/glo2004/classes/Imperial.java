public class Imperial {

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
}