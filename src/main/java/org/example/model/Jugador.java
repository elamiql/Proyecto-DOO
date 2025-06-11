package org.example.model;

public class Jugador extends Participante {

    public Jugador(String nombre, String numero) {
        super(nombre, numero);
    }

    @Override
    public void inscribirse(Torneo torneo) {
        torneo.addParticipante(this);
    }
}