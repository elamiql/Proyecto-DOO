package org.example.model.Estadisticas;

import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoFutbol;

/**
 * Estadísticas de un participante en un torneo de fútbol.
 * Usa sistema de puntuación FIFA (3 puntos victoria, 1 empate, 0 derrota).
 * Registra goles a favor, goles en contra, partidos ganados, empatados y perdidos.
 *
 * @see EstadisticasParticipante
 * @see ResultadoFutbol
 * @see Participante
 */
public class EstadisticasFutbol extends EstadisticasParticipante<Participante, ResultadoFutbol> {
    private int golesFavor;
    private int golesContra;

    /**
     * Crea una instancia para las estadísticas del participante dado.
     *
     * @param participante participante a quien pertenecen estas estadísticas
     */
    public EstadisticasFutbol(Participante participante) {
        super(participante);
    }

    /**
     * Registra el resultado de un partido para este participante.
     * Suma goles a favor y en contra, y actualiza resultados (victoria, empate o derrota).
     *
     * @param resultado   resultado del partido a registrar
     * @param participante participante cuyas estadísticas se actualizan (debe coincidir con esta instancia)
     * @param esLocal     indica si el participante jugó como local (true) o visitante (false)
     * @throws IllegalArgumentException si el resultado no es válido
     */
    @Override
    public void registrarResultado(ResultadoFutbol resultado, Participante participante, boolean esLocal) {
        if (!resultado.esValido()) throw new IllegalArgumentException("Resultado no válido");

        int gf = esLocal ? resultado.getGolesLocal() : resultado.getGolesVisitante();
        int gc = esLocal ? resultado.getGolesVisitante() : resultado.getGolesLocal();

        golesFavor += gf;
        golesContra += gc;

        if (gf > gc) registrarVictoria();
        else if (gf == gc) registrarEmpate();
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

    /**
     * Agrega manualmente goles a favor y en contra al participante.
     *
     * @param golesLocal goles a favor que se desean añadir
     * @param golesVisitante goles en contra que se desean añadir
     */
    public void agregarGoles(int golesLocal, int golesVisitante) {
        this.golesFavor += golesLocal;
        this.golesContra += golesVisitante;
    }

    /**
     * Reinicia todas las estadísticas acumuladas del participante y
     * se deja el estado como si no se hubiera disputado ningún partido.
     */
    public void reiniciarEstadisticas(){
        super.reiniciarEstadisticas();
        this.golesFavor = 0;
        this.golesContra = 0;
    }
}
