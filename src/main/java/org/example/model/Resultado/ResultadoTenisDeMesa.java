package org.example.model.Resultado;

import org.example.interfaces.*;
import org.example.model.Participante.Participante;

/**
 * Representa el resultado de un enfrentamiento de tenis de mesa entre dos participantes.
 * Contiene los sets ganados por cada jugador y los puntos obtenidos en cada set.
 */
public class ResultadoTenisDeMesa implements Resultado {

    /**
     * Sets ganados por el jugador 1.
     */
    private int setsJugador1;

    /**
     * Sets ganados por el jugador 2.
     */
    private int setsJugador2;

    /**
     * Puntos obtenidos en cada set por el jugador 1.
     */
    private int[] puntosSetsJugador1;

    /**
     * Puntos obtenidos en cada set por el jugador 2.
     */
    private int[] puntosSetsJugador2;

    /**
     * Participante jugador 1.
     */
    private Participante jugador1;

    /**
     * Participante jugador 2.
     */
    private Participante jugador2;

    /**
     * Constructor que inicializa el resultado de tenis de mesa con los jugadores y el número máximo de sets.
     *
     * @param jugador1 Participante jugador 1.
     * @param jugador2 Participante jugador 2.
     * @param maxSets Número máximo de sets posibles en el partido.
     */
    public ResultadoTenisDeMesa(Participante jugador1, Participante jugador2, int maxSets) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        puntosSetsJugador1 = new int[maxSets];
        puntosSetsJugador2 = new int[maxSets];
        setsJugador1 = 0;
        setsJugador2 = 0;
    }

    /**
     * Agrega el resultado de un set específico indicando los puntos obtenidos por cada jugador.
     * Recalcula los sets ganados.
     *
     * @param setIndex índice del set (0 basado).
     * @param puntosJ1 puntos obtenidos por el jugador 1 en el set.
     * @param puntosJ2 puntos obtenidos por el jugador 2 en el set.
     */
    public void agregarSet(int setIndex, int puntosJ1, int puntosJ2) {
        puntosSetsJugador1[setIndex] = puntosJ1;
        puntosSetsJugador2[setIndex] = puntosJ2;

        // Recalcular sets ganados (por si cambian sets después)
        setsJugador1 = 0;
        setsJugador2 = 0;
        for (int i = 0; i < puntosSetsJugador1.length; i++) {
            if (esSetValido(puntosSetsJugador1[i], puntosSetsJugador2[i])) {
                if (puntosSetsJugador1[i] > puntosSetsJugador2[i]) setsJugador1++;
                else if (puntosSetsJugador2[i] > puntosSetsJugador1[i]) setsJugador2++;
            }
        }
    }

    /**
     * Determina si el set es válido según las reglas del tenis de mesa:
     * - Al menos 11 puntos para uno de los jugadores.
     * - Diferencia mínima de 2 puntos.
     * - No se permiten puntos negativos.
     *
     * @param p1 puntos del jugador 1.
     * @param p2 puntos del jugador 2.
     * @return true si el set es válido, false en caso contrario.
     */
    public boolean esSetValido(int p1, int p2) {
        if (p1 < 0 || p2 < 0) return false;
        if (p1 < 11 && p2 < 11) return false;
        if (Math.abs(p1 - p2) < 2) return false;
        if (p1 >= 11 || p2 >= 11) return true;
        return false;
    }

    /**
     * Obtiene un resumen textual del resultado, incluyendo sets ganados y los puntos por set.
     *
     * @return resumen del resultado del partido.
     */
    @Override
    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sets: ").append(setsJugador1).append(" - ").append(setsJugador2).append("\n");
        sb.append("Puntos por set:\n");
        for (int i = 0; i < puntosSetsJugador1.length; i++) {
            if (puntosSetsJugador1[i] == 0 && puntosSetsJugador2[i] == 0) continue;
            sb.append("Set ").append(i + 1).append(": ")
                    .append(puntosSetsJugador1[i]).append(" - ").append(puntosSetsJugador2[i]).append("\n");
        }
        return sb.toString();
    }

    /**
     * Obtiene el participante ganador del partido, si alguno ha ganado la mayoría de los sets.
     *
     * @return participante ganador o null si no hay ganador definido.
     */
    @Override
    public Participante getGanador() {
        int setsNecesarios = (puntosSetsJugador1.length / 2) + 1;
        if (setsJugador1 >= setsNecesarios) return jugador1;
        else if (setsJugador2 >= setsNecesarios) return jugador2;
        else return null;
    }

    /**
     * Verifica si el resultado es válido, es decir, si algún jugador ha ganado la mayoría de sets.
     *
     * @return true si el resultado es válido, false en caso contrario.
     */
    @Override
    public boolean esValido() {
        int setsNecesarios = (puntosSetsJugador1.length / 2) + 1;
        // Validar que algún jugador haya ganado la mayoría de sets y sets válidos
        if (setsJugador1 >= setsNecesarios || setsJugador2 >= setsNecesarios) return true;
        return false;
    }

    // Getters para pruebas

    public int getSetsJugador1() {
        return setsJugador1;
    }

    public int getSetsJugador2() {
        return setsJugador2;
    }

    public void setSetsJugador2(int setsJugador2) {
        this.setsJugador2 = setsJugador2;
    }

    public void setSetsJugador1(int setsJugador1) {
        this.setsJugador1 = setsJugador1;
    }

    public void setJugador2(Participante jugador2) {
        this.jugador2 = jugador2;
    }

    public void setJugador1(Participante jugador1) {
        this.jugador1 = jugador1;
    }

    public Participante getJugador2() {
        return jugador2;
    }

    public Participante getJugador1() {
        return jugador1;
    }

    public int[] getPuntosSetsJugador1() {
        return puntosSetsJugador1;
    }

    public int[] getPuntosSetsJugador2() {
        return puntosSetsJugador2;
    }

    public void setPuntosSetsJugador1(int[] puntosSetsJugador1) {
        this.puntosSetsJugador1 = puntosSetsJugador1;
    }

    public void setPuntosSetsJugador2(int[] puntosSetsJugador2) {
        this.puntosSetsJugador2 = puntosSetsJugador2;
    }
}
