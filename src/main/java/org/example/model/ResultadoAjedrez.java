package org.example.model;

import org.example.interfaces.Resultado;

public class ResultadoAjedrez implements Resultado {
    private Participante jugador1;
    private Participante jugador2;
    private Double puntosJugador1; // 1.0, 0.5 o 0.0
    private Double puntosJugador2; // 1.0, 0.5 o 0.0

    public ResultadoAjedrez(Participante jugador1, Participante jugador2, double puntosJugador1, double puntosJugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.puntosJugador1 = puntosJugador1;
        this.puntosJugador2 = puntosJugador2;

        if (!esValido()) {
            throw new IllegalArgumentException("Resultado inválido para ajedrez: los puntos deben sumar 1 o ser empate 0.5-0.5.");
        }
    }

    @Override
    public Participante getGanador() {
        if (puntosJugador1 > puntosJugador2) return jugador1;
        if (puntosJugador2 > puntosJugador1) return jugador2;
        return null; // empate
    }

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

    @Override
    public String getResumen() {
        return jugador1.getNombre() + " " + puntosJugador1 + " - " +  puntosJugador2 + " " +  jugador2.getNombre();
    }

    public Double getPuntosJugador1() {
        return puntosJugador1;
    }

    public Double getPuntosJugador2() {
        return puntosJugador2;
    }
}
