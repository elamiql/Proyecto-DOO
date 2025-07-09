package org.example.model.Estadisticas;

import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoTenisDeMesa;

/**
 * Clase que representa las estadísticas de un participante en un torneo de tenis de mesa.
 * <p>
 * Esta clase extiende {@link EstadisticasParticipante} usando {@link Participante} como tipo de entidad
 * y {@link ResultadoTenisDeMesa} como tipo de resultado.
 * </p>
 */
public class EstadisticaTenisDeMesa extends EstadisticasParticipante<Participante, ResultadoTenisDeMesa> {

    /**
     * Constructor que crea las estadísticas para un participante específico.
     * @param participante Participante del torneo de tenis de mesa.
     */
    public EstadisticaTenisDeMesa(Participante participante) {
        super(participante);
    }

    /**
     * Registra el resultado de un enfrentamiento para el participante.
     * <p>
     * Se incrementa la estadística de victoria si el participante es el ganador,
     * o de derrota en caso contrario. No se contemplan empates.
     * </p>
     *
     * @param resultado     Resultado del enfrentamiento.
     * @param participante  Participante al que pertenecen las estadísticas.
     * @param esLocal       Indica si fue el jugador local (no se usa en esta implementación).
     */
    @Override
    public void registrarResultado(ResultadoTenisDeMesa resultado, Participante participante, boolean esLocal) {
        Participante ganador = resultado.getGanador();
        if (ganador == null) {
            return;
        }
        if (ganador.equals(participante)) {
            registrarVictoria();
        } else {
            registrarDerrota();
        }
    }

    /**
     * Devuelve el puntaje total del participante.
     * En tenis de mesa, cada victoria otorga 3 puntos.
     * @return Puntaje total del participante.
     */
    @Override
    public int getPuntos() {
        return getGanados() * 3;
    }

    /**
     * Representación del participante y sus estadísticas en formato tabular.
     * @return Cadena con estadísticas resumidas.
     */
    @Override
    public String toTablaString() {
        return getParticipante() +
                " - PJ: " + getPartidosJugados() +
                ", G: " + getGanados() +
                ", P: " + getPerdidos() +
                ", Ptos: " + getPuntos();
    }
}
