package org.example.enums;
import org.example.model.Disciplina;

public enum Deporte implements Disciplina {
    FUTBOL,
    TENIS,
    TENIS_DE_MESA,
    AJEDREZ;

    @Override
    public String getNombre(){
        return name();
    }
}
