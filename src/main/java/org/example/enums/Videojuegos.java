package org.example.enums;

import org.example.model.Disciplina;

public enum Videojuegos implements Disciplina {
    FIFA,
    LOL;

    @Override
    public String getNombre(){
        return name();
    }
}
