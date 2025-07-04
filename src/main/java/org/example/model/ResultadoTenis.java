package org.example.model;

import org.example.interfaces.*;

public class ResultadoTenis implements Resultado {
    private int setsJugador1;
    private int setsJugador2;
    private int[] juegosSetsJugador1;
    private int[] juegosSetsJugador2;
    private Participante jugador1;
    private Participante jugador2;

    public ResultadoTenis(Participante jugador1, Participante jugador2, int maxSets) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        juegosSetsJugador1 = new int[maxSets];
        juegosSetsJugador2 = new int[maxSets];
        setsJugador1 = 0;
        setsJugador2 = 0;
    }

    public void agregarSet(int setIndex, int juegosJ1, int juegosJ2) {
        juegosSetsJugador1[setIndex] = juegosJ1;
        juegosSetsJugador2[setIndex] = juegosJ2;

        // Recalcular sets ganados (por si cambian sets después)
        setsJugador1 = 0;
        setsJugador2 = 0;
        for (int i = 0; i < juegosSetsJugador1.length; i++) {
            if (juegosSetsJugador1[i] > juegosSetsJugador2[i]) setsJugador1++;
            else if (juegosSetsJugador2[i] > juegosSetsJugador1[i]) setsJugador2++;
        }
    }

    @Override
    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sets: ").append(setsJugador1).append(" - ").append(setsJugador2).append("\n");
        sb.append("Juegos por set:\n");
        for (int i = 0; i < juegosSetsJugador1.length; i++) {
            if (juegosSetsJugador1[i] == 0 && juegosSetsJugador2[i] == 0) continue;
            sb.append("Set ").append(i + 1).append(": ")
                    .append(juegosSetsJugador1[i]).append(" - ").append(juegosSetsJugador2[i]).append("\n");
        }
        return sb.toString();
    }

    @Override
    public Participante getGanador() {
        if (setsJugador1 > setsJugador2){
            return jugador1;
        }
        else if (setsJugador2 > setsJugador1) {
            return jugador2;
        }
        else {
            return null;
        }
    }

    @Override
    public boolean esValido() {
        int maxSetsGanados = Math.max(setsJugador1, setsJugador2);
        // Validar si alguien ya ganó la mayoría de sets (ej. 2 de 3, 3 de 5)
        int setsNecesarios = (juegosSetsJugador1.length / 2) + 1;
        return maxSetsGanados >= setsNecesarios;
    }
}
