package org.example;

class EstadoParticipante {
    Jugador participante;
    boolean activo;

    public EstadoParticipante(Jugador participante, boolean activo) {
        this.participante = participante;
        this.activo = activo;
    }
}