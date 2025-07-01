package org.example.interfaces;

import org.example.model.Participante;

public interface Resultado {
    String getResumen();
    Participante getGanador();
    boolean esValido();
}
