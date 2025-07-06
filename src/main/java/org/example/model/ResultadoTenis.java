package org.example.model;

import org.example.interfaces.Resultado;

/**
 * Representa el resultado de un enfrentamiento de tenis entre dos participantes.
 * Incluye la cantidad de sets ganados por cada jugador y los juegos obtenidos en cada set.
 */
public class ResultadoTenis implements Resultado {

    /**
     * Sets ganados por el jugador 1.
     */
    private int setsJugador1;

    /**
     * Sets ganados por el jugador 2.
     */
    private int setsJugador2;

    /**
     * Juegos obtenidos en cada set por el jugador 1.
     */
    private int[] juegosSetsJugador1;

    /**
     * Juegos obtenidos en cada set por el jugador 2.
     */
    private int[] juegosSetsJugador2;

    /**
     * Participante jugador 1.
     */
    private Participante jugador1;

    /**
     * Participante jugador 2.
     */
    private Participante jugador2;

    /**
     * Constructor que inicializa el resultado de tenis con los jugadores y el número máximo de sets.
     *
     * @param jugador1 Participante jugador 1.
     * @param jugador2 Participante jugador 2.
     * @param maxSets Número máximo de sets posibles en el partido.
     */
    public ResultadoTenis(Participante jugador1, Participante jugador2, int maxSets) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.juegosSetsJugador1 = new int[maxSets];
        this.juegosSetsJugador2 = new int[maxSets];
        this.setsJugador1 = 0;
        this.setsJugador2 = 0;
    }

    /**
     * Agrega el resultado de un set específico indicando los juegos ganados por cada jugador.
     * Luego recalcula la cantidad de sets ganados.
     *
     * @param setIndex índice del set (0 basado).
     * @param juegosJ1 juegos ganados por el jugador 1 en el set.
     * @param juegosJ2 juegos ganados por el jugador 2 en el set.
     */
    public void agregarSet(int setIndex, int juegosJ1, int juegosJ2) {
        juegosSetsJugador1[setIndex] = juegosJ1;
        juegosSetsJugador2[setIndex] = juegosJ2;
        recalcularSets();
    }

    /**
     * Recalcula los sets ganados por cada jugador basado en los juegos por set registrados.
     */
    private void recalcularSets() {
        setsJugador1 = 0;
        setsJugador2 = 0;
        for (int i = 0; i < juegosSetsJugador1.length; i++) {
            int j1 = juegosSetsJugador1[i];
            int j2 = juegosSetsJugador2[i];

            if (j1 == 0 && j2 == 0) continue;

            if (esSetGanado(j1, j2)) {
                setsJugador1++;
            } else if (esSetGanado(j2, j1)) {
                setsJugador2++;
            }
        }
    }

    /**
     * Determina si un set ha sido ganado por el jugador que tiene 'ganados' juegos frente al que tiene 'perdidos'.
     * Considera las reglas típicas de tenis donde se gana con 6 juegos y diferencia de 2, o un tie-break 7-6.
     *
     * @param ganados juegos ganados por un jugador.
     * @param perdidos juegos ganados por el oponente.
     * @return true si el set está ganado por el jugador con 'ganados' juegos.
     */
    private boolean esSetGanado(int ganados, int perdidos) {
        if (ganados >= 6 && ganados - perdidos >= 2 && ganados <= 7) return true;
        return ganados == 7 && perdidos == 6;
    }

    /**
     * Obtiene un resumen textual del resultado, incluyendo sets ganados y los juegos por cada set.
     *
     * @return resumen del resultado del partido.
     */
    @Override
    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sets: ").append(setsJugador1).append(" - ").append(setsJugador2).append("\n");
        sb.append("Juegos por set:\n");
        for (int i = 0; i < juegosSetsJugador1.length; i++) {
            int j1 = juegosSetsJugador1[i];
            int j2 = juegosSetsJugador2[i];
            if (j1 == 0 && j2 == 0) continue;
            sb.append("Set ").append(i + 1).append(": ")
                    .append(j1).append(" - ").append(j2).append("\n");
        }
        return sb.toString();
    }

    /**
     * Obtiene el participante ganador del partido, si el resultado es válido.
     *
     * @return participante ganador o null si no hay ganador definido.
     */
    @Override
    public Participante getGanador() {
        if (!esValido()) return null;

        if (setsJugador1 > setsJugador2) {
            return jugador1;
        } else if (setsJugador2 > setsJugador1) {
            return jugador2;
        } else {
            return null;
        }
    }

    /**
     * Verifica si el resultado es válido considerando la cantidad mínima de sets ganados para declarar un ganador.
     *
     * @return true si el resultado es válido, false en caso contrario.
     */
    @Override
    public boolean esValido() {
        int maxSets = juegosSetsJugador1.length;
        int necesarios = (maxSets / 2) + 1;
        return setsJugador1 >= necesarios || setsJugador2 >= necesarios;
    }

    // Getters y Setters

    public int getSetsJugador1() {
        return setsJugador1;
    }

    public int getSetsJugador2() {
        return setsJugador2;
    }

    public int[] getJuegosSetsJugador1() {
        return juegosSetsJugador1;
    }

    public int[] getJuegosSetsJugador2() {
        return juegosSetsJugador2;
    }

    public Participante getJugador1() {
        return jugador1;
    }

    public Participante getJugador2() {
        return jugador2;
    }

    public void setJuegosSetsJugador1(int[] juegosSetsJugador1) {
        this.juegosSetsJugador1 = juegosSetsJugador1;
    }

    public void setJuegosSetsJugador2(int[] juegosSetsJugador2) {
        this.juegosSetsJugador2 = juegosSetsJugador2;
    }

    public void setJugador1(Participante jugador1) {
        this.jugador1 = jugador1;
    }

    public void setJugador2(Participante jugador2) {
        this.jugador2 = jugador2;
    }

    public void setSetsJugador1(int setsJugador1) {
        this.setsJugador1 = setsJugador1;
    }

    public void setSetsJugador2(int setsJugador2) {
        this.setsJugador2 = setsJugador2;
    }
}
