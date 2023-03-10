package ca.ulaval.glo2004.classes;

import java.io.Serializable;

public class PointImperial implements Serializable {
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

    public PointImperial mirror(Cote cote)
    {
        return new PointImperial(mX.mirror(cote), mY);
    }

    public PointImperial substract(PointImperial other)
    {
        return new PointImperial(getmX().substract(other.getmX()), getmY().substract(other.getmY()));
    }

    @Override
    public String toString() {
        return "(" + mX.toString() + ", " + mY.toString() + ")";
    }
}