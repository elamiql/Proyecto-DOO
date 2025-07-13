package org.example.model.Estadisticas;

import org.example.interfaces.*;

/**
 * Clase abstracta que representa las estadísticas de un participante en una competencia.
 *
 * @param <T> Tipo del participante (por ejemplo, un jugador o equipo).
 * @param <R> Tipo de resultado que se utiliza para registrar las estadísticas.
 */
public abstract class EstadisticasParticipante<T, R extends Resultado> implements Estadisticas<T, R>{

    /** Participante al que pertenecen estas estadísticas */
    private T participante;

    /** Número total de partidos jugados */
    private int partidosJugados;

    /** Número total de partidos ganados */
    private int ganados;

    /** Número total de partidos empatados */
    private int empatados;

    /** Número total de partidos perdidos */
    private int perdidos;

    /**
     * Constructor que inicializa las estadísticas del participante.
     * @param participante El participante cuyas estadísticas se están registrando.
     */
    public EstadisticasParticipante(T participante){
        this.participante = participante;
    }

    /**
     * Registra una victoria para el participante.
     */
    public void registrarVictoria() {
        ganados++;
        partidosJugados++;
    }

    /**
     * Registra un empate para el participante.
     */
    public void registrarEmpate(){
        empatados++;
        partidosJugados++;
    }

    /**
     * Registra una derrota para el participante.
     */
    public void registrarDerrota(){
        perdidos++;
        partidosJugados++;
    }

    /**
     * Reinicia todas las estadísticas del participante.
     */
    public void reiniciarEstadisticas(){
        partidosJugados = 0;
        ganados = 0;
        empatados = 0;
        perdidos = 0;
    }

    /**
     * Devuelve una representación en forma de cadena de las estadísticas del participante.
     * @return Cadena con las estadísticas básicas del participante.
     */
    @Override
    public String toString() {
        return participante +
                " - PJ: " + partidosJugados +
                ", G: " + ganados +
                ", E: " + empatados +
                ", P: " + perdidos;
    }

    /**
     * Registra un resultado para el participante.
     * @param resultado Resultado del enfrentamiento.
     * @param participante Participante relacionado con el resultado.
     * @param esLocal Indica si el participante jugó como local.
     */

    public abstract void registrarResultado(R resultado, T participante, boolean esLocal);

    /**
     * Devuelve la cantidad total de puntos del participante según el sistema de puntuación.
     *
     * @return Total de puntos obtenidos.
     */
    @Override
    public abstract int getPuntos();

    /**
     * Devuelve una representación en forma de tabla de las estadísticas del participante.
     * @return Cadena con las estadísticas en formato tabular.
     */
    public abstract String toTablaString();

    // Getters y setters

    /**
     * Devuelve el participante al que pertenecen estas estadísticas.
     * @return El participante.
     */
    public T getParticipante() {
        return participante;
    }

    /**
     * Devuelve el número total de partidos jugados.
     * @return Partidos jugados.
     */
    public int getPartidosJugados() {
        return partidosJugados;
    }

    /**
     * Devuelve el número total de partidos ganados.
     * @return Partidos ganados.
     */
    public int getGanados() {
        return ganados;
    }

    /**
     * Devuelve el número total de partidos empatados.
     * @return Partidos empatados.
     */
    public int getEmpatados() {
        return empatados;
    }

    /**
     * Devuelve el número total de partidos perdidos.
     * @return Partidos perdidos.
     */
    public int getPerdidos() {
        return perdidos;
    }
}
