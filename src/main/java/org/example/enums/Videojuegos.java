package org.example.enums;

import org.example.interfaces.Disciplina;

public enum Videojuegos implements Disciplina {
    FIFA,
    LOL;

    @Override
    public String getNombre(){
        return name();
    }
}
