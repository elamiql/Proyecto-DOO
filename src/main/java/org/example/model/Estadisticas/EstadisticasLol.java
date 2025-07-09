package org.example.model.Estadisticas;

import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoLol;

/**
 * Clase que representa las estadísticas de un participante en un torneo de League of Legends (LoL).
 * <p>
 * Incluye métricas comunes del juego como kills, torres destruidas, dragones y barones obtenidos.
 * Extiende {@link EstadisticasParticipante} con {@link Participante} como tipo de participante
 * y {@link ResultadoLol} como tipo de resultado.
 * </p>
 */
public class EstadisticasLol extends EstadisticasParticipante<Participante, ResultadoLol> {


    private int killsTotales;


    private int torresTotales;
    private int dragonesTotales;
    private int baronesTotales;

    /**
     * Constructor que inicializa las estadísticas del participante.
     * @param participante Participante del torneo de LoL.
     */
    public EstadisticasLol(Participante participante) {
        super(participante);
    }

    /**
     * Registra un resultado de enfrentamiento para el participante en una partida de LoL.
     * <p>Se actualizan:</p>
     * <ul>
     *   <li>Kills totales</li>
     *   <li>Torres destruidas</li>
     *   <li>Dragones tomados</li>
     *   <li>Barones tomados</li>
     * </ul>
     * Además, se actualiza el estado como victoria o derrota según el ganador del resultado.
     *
     * @param resultado     Resultado de la partida.
     * @param participante  Participante cuyas estadísticas se están registrando.
     * @param esLocal       Indica si jugó como local.
     */
    @Override
    public void registrarResultado(ResultadoLol resultado, Participante participante, boolean esLocal) {
        if (!resultado.esValido()) return;

        if (resultado.getGanador().equals(participante)) {
            registrarVictoria();
        } else {
            registrarDerrota();
        }

        killsTotales += resultado.getKills(participante);
        torresTotales += resultado.getTorres(participante);
        dragonesTotales += resultado.getDragones(participante);
        baronesTotales += resultado.getBarones(participante);
    }

    /**
     * Devuelve el puntaje total acumulado.
     * Por defecto, se otorgan 3 puntos por cada victoria.
     * @return Total de puntos.
     */
    @Override
    public int getPuntos() {
        return getGanados() * 3; // Por ejemplo, 3 puntos por victoria
    }

    /**
     * Retorna una representación en formato de tabla de las estadísticas del participante,
     * incluyendo desempeño en objetivos del juego.
     * @return Cadena con estadísticas resumidas para tabla.
     */
    @Override
    public String toTablaString() {
        return getParticipante().getNombre() +
                " - PJ: " + getPartidosJugados() +
                ", G: " + getGanados() +
                ", P: " + getPerdidos() +
                ", Puntos: " + getPuntos() +
                ", Kills: " + killsTotales +
                ", Torres: " + torresTotales +
                ", Dragones: " + dragonesTotales +
                ", Barones: " + baronesTotales;
    }
}
