package org.example.model.Resultado;

import org.example.interfaces.Resultado;
import org.example.model.Participante.Participante;

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

    private Participante ganador;

    /**
     * Constructor que inicializa el resultado de tenis con los jugadores y el número máximo de sets.
     *
     * @param jugador1 Participante jugador 1.
     * @param jugador2 Participante jugador 2.
     * @param maxSets Número máximo de sets posibles en el partido.
     * @param ganador       Ganador del encuentro
     */
    public ResultadoTenis(Participante jugador1, Participante jugador2, int maxSets,Participante ganador) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.juegosSetsJugador1 = new int[maxSets];
        this.juegosSetsJugador2 = new int[maxSets];
        this.setsJugador1 = 0;
        this.setsJugador2 = 0;
        this.ganador=ganador;
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
        if (setIndex < 0 || setIndex >= juegosSetsJugador1.length)
            throw new IllegalArgumentException("Índice de set fuera de rango");

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
        // Gana 6-0 hasta 6-4
        if (ganados >= 6 && ganados <= 7 && ganados - perdidos >= 2) return true;

        // Gana 7-5 o 7-6 (tiebreak)
        if (ganados == 7 && (perdidos == 5 || perdidos == 6)) return true;

        return false;
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
            sb.append("Set ").append(i + 1).append(": ").append(j1).append(" - ").append(j2).append("\n");
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

        return setsJugador1 > setsJugador2 ? jugador1 :
                setsJugador2 > setsJugador1 ? jugador2 : null;
    }

    /**
     * Verifica si el resultado es válido considerando los puntajes de los sets y que los sets ganados tengan coherencia con el ganador del encuentro.
     *
     * @return true si el resultado es válido, false en caso contrario.
     */
    @Override
    public boolean esValido() {
        if (setsJugador1 < 0 || setsJugador2 < 0) {
            return false;
        }

        int maxSets = juegosSetsJugador1.length;
        int necesarios = (maxSets / 2) + 1;

        if (setsJugador1 > maxSets || setsJugador2 > maxSets) {
            return false;
        }
        if (setsJugador1 + setsJugador2 > maxSets) {
            return false;
        }

        for (int i = 0; i < maxSets; i++) {
            int juegosJ1 = juegosSetsJugador1[i];
            int juegosJ2 = juegosSetsJugador2[i];

            if (juegosJ1 == 0 && juegosJ2 == 0) continue;
            if (juegosJ1 > 7 || juegosJ2 > 7) return false;
            if (juegosJ1 == 7 && (juegosJ2 != 5 && juegosJ2 != 6)) return false;
            if (juegosJ2 == 7 && (juegosJ1 != 5 && juegosJ1 != 6)) return false;
            if (juegosJ1 < 6 && juegosJ2 < 6) {
                if (juegosJ1 < 4 && juegosJ2 < 4) return false;
            }
        }

        // Solo válido si el partido terminó con un ganador
        if (ganador != null) {
            if (ganador.equals(jugador1)) {
                return setsJugador1 >= necesarios && setsJugador2 < necesarios;
            } else if (ganador.equals(jugador2)) {
                return setsJugador2 >= necesarios && setsJugador1 < necesarios;
            }
        }

        return false; // ahora partidos no terminados son inválidos
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
        recalcularSets();
    }

    public void setJuegosSetsJugador2(int[] juegosSetsJugador2) {
        this.juegosSetsJugador2 = juegosSetsJugador2;
        recalcularSets();
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
