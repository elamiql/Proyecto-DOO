package org.example.model.Estadisticas;

import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoFifa;

/**
 * Estadísticas de un participante en un torneo FIFA.
 * Usa sistema de puntuación FIFA (3 puntos victoria, 1 empate, 0 derrota).
 * Registra goles a favor, goles en contra, partidos ganados, empatados y perdidos.
 *
 * @see EstadisticasParticipante
 * @see ResultadoFifa
 * @see Participante
 */
public class EstadisticasFifa extends EstadisticasParticipante<Participante, ResultadoFifa> {
    private int golesFavor;
    private int golesContra;

    /**
     * Crea una instancia para las estadísticas del participante dado.
     *
     * @param participante participante a quien pertenecen estas estadísticas
     */
    public EstadisticasFifa(Participante participante) {
        super(participante);
    }

    /**
     * Registra el resultado de un partido para este participante.
     * Suma goles a favor y en contra, y actualiza resultados (victoria, empate o derrota).
     *
     * @param resultado   resultado del partido a registrar
     * @param participante participante cuyas estadísticas se actualizan (debe coincidir con esta instancia)
     * @param esLocal     indica si el participante jugó como local (true) o visitante (false)
     * @throws IllegalArgumentException si el resultado es nulo
     */
    @Override
    public void registrarResultado(ResultadoFifa resultado, Participante participante, boolean esLocal) {
        if (resultado == null) throw new IllegalArgumentException("Resultado nulo");

        int gf = esLocal ? resultado.getGolesLocal() : resultado.getGolesVisitante();
        int gc = esLocal ? resultado.getGolesVisitante() : resultado.getGolesLocal();

        golesFavor += gf;
        golesContra += gc;

        Participante ganador = resultado.getGanador();
        if (ganador == null) registrarEmpate();
        else if (ganador.equals(participante)) registrarVictoria();
        else registrarDerrota();
    }

    /**
     * Calcula los puntos acumulados.
     * 3 por victoria, 1 por empate, 0 por derrota.
     *
     * @return puntos totales del participante
     */
    @Override
    public int getPuntos() {
        return 3 * getGanados() + getEmpatados();
    }

    /**
     * Devuelve los goles a favor acumulados.
     *
     * @return goles a favor
     */
    public int getGolesFavor() {
        return golesFavor;
    }

    /**
     * Devuelve los goles en contra acumulados.
     *
     * @return goles en contra
     */
    public int getGolesContra() {
        return golesContra;
    }

    /**
     * Calcula la diferencia de goles (goles a favor menos goles en contra).
     *
     * @return diferencia de goles
     */
    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }

    /**
     * Representación en forma de tabla de las estadísticas.
     * Columnas: Participante, PJ, G, E, P, GF, GC, DIF, PTS.
     *
     * @return cadena con formato tabular de las estadísticas
     */
    @Override
    public String toTablaString() {
        return String.format("%-20s | %2d | %2d | %2d | %2d | %3d | %3d | %4d | %3d",
                getParticipante(),
                getPartidosJugados(),
                getGanados(),
                getEmpatados(),
                getPerdidos(),
                golesFavor,
                golesContra,
                getDiferenciaGoles(),
                getPuntos());
    }
}
