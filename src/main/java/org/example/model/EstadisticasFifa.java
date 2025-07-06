package org.example.model;

/**
 * Clase que gestiona las estadísticas de un participante en un torneo con formato similar a FIFA.
 * Registra información sobre goles, partidos ganados, empatados y perdidos, además de calcular puntos y diferencia de goles.
 * <p>Se considera el sistema de puntuación FIFA:</p>
 * <ul>
 *   <li>Victoria = 3 puntos</li>
 *   <li>Empate = 1 punto</li>
 *   <li>Derrota = 0 puntos</li>
 * </ul>
 */
public class EstadisticasFifa extends EstadisticasParticipante<Participante, ResultadoFifa> {
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;
    private int golesFavor;
    private int golesContra;

    /**
     * Constructor que inicializa las estadísticas para un participante.
     *
     * @param participante Participante al que se le registrarán las estadísticas.
     */
    public EstadisticasFifa(Participante participante) {
        super(participante);
        partidosJugados = 0;
        ganados = 0;
        empatados = 0;
        perdidos = 0;
        golesFavor = 0;
        golesContra = 0;
    }

    /**
     * Registra el resultado de un partido para el participante.
     * Se determina si el participante jugó como local o visitante y se actualizan
     * los goles marcados y recibidos, así como el resultado del partido.
     *
     * @param resultado   Resultado del partido.
     * @param participante Participante cuyas estadísticas se actualizan.
     * @param esLocal     {@code true} si el participante jugó como local; {@code false} si fue visitante.
     */
    @Override
    public void registrarResultado(ResultadoFifa resultado, Participante participante, boolean esLocal) {
        if (resultado == null) return;

        partidosJugados++;
        int golesMarcados = esLocal ? resultado.getGolesLocal() : resultado.getGolesVisitante();
        int golesRecibidos = esLocal ? resultado.getGolesVisitante() : resultado.getGolesLocal();

        golesFavor += golesMarcados;
        golesContra += golesRecibidos;

        Participante ganador = resultado.getGanador();
        if (ganador == null) { // empate
            empatados++;
        } else if (ganador.equals(participante)) {
            ganados++;
        } else {
            perdidos++;
        }
    }

    /**
     * Devuelve la cantidad total de puntos acumulados.
     * @return Puntos totales (victoria = 3 puntos, empate = 1 punto).
     */
    @Override
    public int getPuntos() {
        return ganados * 3 + empatados;
    }

    /**
     * Retorna una representación en forma de tabla con todas las estadísticas del participante.
     * @return Cadena con PJ, G, E, P, GF, GC, DG y Puntos.
     */
    @Override
    public String toTablaString() {
        int diferenciaGoles = golesFavor - golesContra;
        return String.format(
                "%s - PJ: %d, G: %d, E: %d, P: %d, GF: %d, GC: %d, DG: %d, Puntos: %d",
                getParticipante().toString(), partidosJugados, ganados, empatados, perdidos, golesFavor, golesContra, diferenciaGoles, getPuntos()
        );
    }

    /**
     * @return Total de partidos jugados.
     */
    public int getPartidosJugados() {
        return partidosJugados;
    }

    /**
     * @return Total de partidos ganados.
     */
    public int getGanados() {
        return ganados;
    }

    /**
     * @return Total de partidos empatados.
     */
    public int getEmpatados() {
        return empatados;
    }

    /**
     * @return Total de partidos perdidos.
     */
    public int getPerdidos() {
        return perdidos;
    }

    /**
     * @return Total de goles a favor.
     */
    public int getGolesFavor() {
        return golesFavor;
    }

    /**
     * @return Total de goles en contra.
     */
    public int getGolesContra() {
        return golesContra;
    }

}
