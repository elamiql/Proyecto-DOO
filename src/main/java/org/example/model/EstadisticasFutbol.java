package org.example.model;

/**
 * Clase que gestiona las estadísticas de un participante en un torneo de fútbol.
 * Extiende {@link EstadisticasParticipante} utilizando {@link Participante} como tipo de participante
 * y {@link ResultadoFutbol} como tipo de resultado.
 *
 * <p>Incluye datos adicionales como goles a favor, goles en contra y diferencia de goles.</p>
 * <p>El sistema de puntuación utilizado es:</p>
 * <ul>
 *   <li>Victoria: 3 puntos</li>
 *   <li>Empate: 1 punto</li>
 *   <li>Derrota: 0 puntos</li>
 * </ul>
 */
public class EstadisticasFutbol extends EstadisticasParticipante<Participante, ResultadoFutbol>{
    private int golesFavor;
    private int golesContra;

    /**
     * Constructor que crea una instancia de estadísticas para un participante.
     * @param participante Participante del torneo.
     */
    public EstadisticasFutbol(Participante participante){
        super(participante);
    }

    /**
     * Agrega goles a favor y en contra manualmente (uso opcional).
     * @param golesFavor   Goles a favor.
     * @param golesContra  Goles en contra.
     */
    public void agregarGoles(int golesFavor, int golesContra){
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;
    }

    /**
     * Calcula la diferencia de goles (favor - contra).
     * @return Diferencia de goles.
     */
    public int getDiferenciaGoles(){
        return golesFavor - golesContra;
    }

    /**
     * Devuelve el estado de las estadísticas,
     * goles en contra y diferencia de goles.
     * @return Cadena descriptiva de las estadísticas.
     */
    @Override
    public String toString() {
        return super.toString() +
                ", GF: " + golesFavor +
                ", GC: " + golesContra +
                ", DIF: " + getDiferenciaGoles();
    }

    /**
     * Registra el resultado de un enfrentamiento de fútbol para el participante.
     * Calcula goles según si fue local o visitante y actualiza el resultado (victoria, empate o derrota).
     *
     * @param resultado     Resultado del partido.
     * @param participante  Participante cuyas estadísticas se registran.
     * @param esLocal       {@code true} si el participante fue local, {@code false} si fue visitante.
     * @throws IllegalArgumentException si el resultado no es válido.
     */
    @Override
    public void registrarResultado(ResultadoFutbol resultado, Participante participante, boolean esLocal) {
        if (!resultado.esValido()) throw new IllegalArgumentException("Resultado no valido");

        int golesLocal = resultado.getGolesLocal();
        int golesVisitante = resultado.getGolesVisitante();

        if (esLocal) {
            golesFavor += golesLocal;
            golesContra += golesVisitante;

            if (golesLocal > golesVisitante) {
                registrarVictoria();
            } else if (golesLocal == golesVisitante) {
                registrarEmpate();
            } else {
                registrarDerrota();
            }
        } else {
            golesFavor += golesVisitante;
            golesContra += golesLocal;

            if (golesVisitante > golesLocal) {
                registrarVictoria();
            } else if (golesVisitante == golesLocal) {
                registrarEmpate();
            } else {
                registrarDerrota();
            }
        }
    }

    /**
     * Devuelve los puntos acumulados por el participante.
     * @return Puntos totales (3 por victoria, 1 por empate).
     */
    @Override
    public int getPuntos() {

        return 3*getGanados() + getEmpatados();
    }

    /**
     * Representación en forma de tabla con formato personalizado para visualizar estadísticas del participante.
     * @return Cadena formateada con columnas: nombre, PJ, G, E, P, GF, GC, DIF, PTS.
     */
    @Override
    public String toTablaString() {
        return String.format("%-20s | %2d | %2d | %2d | %2d | %3d | %3d | %4d | %3d",
                getParticipante().toString(),
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
     * @return Total de goles a favor.
     */
    public int getGolesFavor(){
        return golesFavor;
    }

    /**
     * @return Total de goles en contra.
     */
    public int getGolesContra(){
        return golesContra;
    }
}
