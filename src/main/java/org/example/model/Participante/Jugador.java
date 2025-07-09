package org.example.model.Participante;

import org.example.model.torneo.Torneo;

/**
 * Clase que representa a un jugador individual en un torneo.
 * Extiende de {@link Participante}.
 */
public class Jugador extends Participante {

    /**
     * Constructor que crea un jugador con nombre y número identificador.
     * @param nombre Nombre del jugador.
     * @param numero Número identificador del jugador.
     */
    public Jugador(String nombre, String numero) {
        super(nombre, numero);
    }

    /**
     * Inscribe al jugador en un torneo específico.
     *
     * @param torneo Torneo en el que se inscribe el jugador.
     */
    @Override
    public void inscribirse(Torneo<?> torneo) {
        torneo.addParticipante(this);
    }

    /**
     * Devuelve una representación en cadena del jugador con su nombre y número.
     * @return Cadena con nombre y número del jugador.
     */
    @Override
    public String toString() {
        return getNombre() + " " + getNumero();
    }
}