package org.example.enums;

public enum Formato {
    ELIMINATORIA,
    LIGA,
    GRUPOS_CON_ELIMINATORIA,
    CUSTOM;

    public String getNombre(){
        return name();
    }
}
