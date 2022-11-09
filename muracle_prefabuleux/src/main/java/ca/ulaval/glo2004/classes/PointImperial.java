package ca.ulaval.glo2004.classes;
public class PointImperial {
    Imperial mX;
    Imperial mY;

    public PointImperial(Imperial mX, Imperial mY) {
        this.mX = mX;
        this.mY = mY;
    }

    public Imperial getmX() {
        return mX;
    }

    public void setmX(Imperial mX) {
        this.mX = mX;
    }

    public Imperial getmY() {
        return mY;
    }

    public void setmY(Imperial mY) {
        this.mY = mY;
    }

    public Boolean EstDans(Imperial mY) {
        return true;
    }
}