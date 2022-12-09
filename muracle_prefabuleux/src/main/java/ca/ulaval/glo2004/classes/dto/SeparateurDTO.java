package ca.ulaval.glo2004.classes.dto;

import ca.ulaval.glo2004.classes.Imperial;
import ca.ulaval.glo2004.classes.Polygone;

import java.io.Serializable;

public class SeparateurDTO implements Serializable
{
    private Imperial position;
    private Imperial positionRelative;


    public SeparateurDTO(Imperial position, Imperial positionRelative) {
        this.position = position;
        this.positionRelative = positionRelative;
    }

    public Imperial getPosition() {
        return position;
    }

    public Imperial getPositionRelative() {
        return positionRelative;
    }





}


