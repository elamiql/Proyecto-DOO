package org.example.model;

/**
 * Clase que gestiona las estadísticas de un participante en un torneo de ajedrez.
 * Extiende {@link EstadisticasParticipante} usando {@link Participante} como tipo de participante
 * y {@link ResultadoAjedrez} como tipo de resultado.
 *
 * <p>Implementa el sistema de puntuación del ajedrez:
 * <ul>
 *     <li>Victoria: 1 punto</li>
 *     <li>Empate: 0.5 puntos</li>
 *     <li>Derrota: 0 puntos</li>
 * </ul>
 */
public class EstadisticasAjedrez extends EstadisticasParticipante<Participante, ResultadoAjedrez> {

    /**
     * Crea una instancia de estadísticas para el participante especificado.
     * @param participante Participante del que se registran las estadísticas.
     */
    public EstadisticasAjedrez(Participante participante) {
        super(participante);
    }

    /**
     * Registra el resultado de un enfrentamiento para el participante.
     * Según el resultado (victoria, empate o derrota), incrementa los contadores correspondientes.
     * @param resultado   Resultado del enfrentamiento.
     * @param participante Participante cuyas estadísticas se están actualizando.
     * @param esLocal     Indica si el participante jugaba como local (no utilizado en esta implementación).
     */
    @Override
    public void registrarResultado(ResultadoAjedrez resultado, Participante participante, boolean esLocal) {
        Double puntos = null;
        if (participante.equals(resultado.getGanador())) {
            registrarVictoria();
            return;
        } else if (resultado.getGanador() == null) { // empate
            registrarEmpate();
            return;
        } else {
            registrarDerrota();
            return;
        }
    }

    /**
     * Calcula los puntos acumulados por el participante.
     * Cada victoria vale 1 punto, y cada empate 0.5 puntos (truncado a entero).
     * @return Puntos acumulados como valor entero.
     */
    @Override
    public int getPuntos() {
        return (int)(getGanados() + getEmpatados() * 0.5);
    }

    /**
     * Devuelve una representación en forma de tabla con los datos estadísticos del participante.
     * @return Cadena con formato de tabla incluyendo PJ, G, E, P y puntos.
     */
    @Override
    public String toTablaString() {
        return String.format("%s - PJ: %d, G: %d, E: %d, P: %d, Puntos: %.1f",
                getParticipante(),
                getPartidosJugados(),
                getGanados(),
                getEmpatados(),
                getPerdidos(),
                getGanados() * 1.0 + getEmpatados() * 0.5);
    }
}
