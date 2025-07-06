package org.example.model;

import org.example.interfaces.Resultado;

/**
 * Clase que representa el resultado de un enfrentamiento de ajedrez entre dos participantes.
 * Cada participante puede obtener 1.0, 0.5 o 0.0 puntos, siguiendo las reglas estándar de ajedrez.
 */
public class ResultadoAjedrez implements Resultado {

    /**
     * Primer jugador del enfrentamiento.
     */
    private Participante jugador1;

    /**
     * Segundo jugador del enfrentamiento.
     */
    private Participante jugador2;

    /**
     * Puntos obtenidos por el jugador1 (pueden ser 1.0, 0.5 o 0.0).
     */
    private Double puntosJugador1;

    /**
     * Puntos obtenidos por el jugador2 (pueden ser 1.0, 0.5 o 0.0).
     */
    private Double puntosJugador2;

    /**
     * Crea un resultado de ajedrez con los dos jugadores y sus puntos respectivos.
     * Valida que los puntos sean un resultado válido (1-0, 0-1, o empate 0.5-0.5).
     *
     * @param jugador1      Primer jugador.
     * @param jugador2      Segundo jugador.
     * @param puntosJugador1 Puntos del primer jugador (1.0, 0.5 o 0.0).
     * @param puntosJugador2 Puntos del segundo jugador (1.0, 0.5 o 0.0).
     * @throws IllegalArgumentException si los puntos no representan un resultado válido.
     */
    public ResultadoAjedrez(Participante jugador1, Participante jugador2, double puntosJugador1, double puntosJugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.puntosJugador1 = puntosJugador1;
        this.puntosJugador2 = puntosJugador2;

        if (!esValido()) {
            throw new IllegalArgumentException("Resultado inválido para ajedrez: los puntos deben sumar 1 o ser empate 0.5-0.5.");
        }
    }

    /**
     * Obtiene el ganador del enfrentamiento.
     * @return El jugador ganador, o null en caso de empate.
     */
    @Override
    public Participante getGanador() {
        if (puntosJugador1 > puntosJugador2) return jugador1;
        if (puntosJugador2 > puntosJugador1) return jugador2;
        return null; // empate
    }

    /**
     * Verifica si el resultado es válido según las reglas del ajedrez.
     * @return true si el resultado es válido; false en caso contrario.
     */
    @Override
    public boolean esValido() {
        // Los puntos deben ser 1-0, 0-1, o 0.5-0.5
         if ((puntosJugador1 == 1.0 && puntosJugador2 == 0.0) ||
                (puntosJugador1 == 0.0 && puntosJugador2 == 1.0) ||
                (puntosJugador1 == 0.5 && puntosJugador2 == 0.5)) {
            return true;
        }
        return false;
    }

    /**
     * Devuelve un resumen textual del resultado, mostrando los nombres de los jugadores y sus puntos.
     * @return Resumen en formato "jugador1 puntosJugador1 - puntosJugador2 jugador2".
     */
    @Override
    public String getResumen() {
        return jugador1.getNombre() + " " + puntosJugador1 + " - " +  puntosJugador2 + " " +  jugador2.getNombre();
    }

    /**
     * Obtiene los puntos del jugador1.
     * @return Puntos obtenidos por el jugador1.
     */
    public Double getPuntosJugador1() {
        return puntosJugador1;
    }

    /**
     * Obtiene los puntos del jugador2.
     * @return Puntos obtenidos por el jugador2.
     */
    public Double getPuntosJugador2() {
        return puntosJugador2;
    }
}
