package org.example.enums;
import org.example.model.Disciplina;

public enum Deporte implements Disciplina {
    FUTBOL,
    TENIS_DE_MESA,
    VIDEOJUEGOS,
    AJEDREZ;

    @Override
    public String getNombre(){
        return name();
    }
}
