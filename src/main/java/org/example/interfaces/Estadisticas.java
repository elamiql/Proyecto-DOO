package org.example.interfaces;

import org.example.model.Participante.Participante;

/**
 * Representa las estadísticas de un participante en una competencia.
 *
 * <p>Esta interfaz genérica permite registrar resultados y obtener información
 * relevante como los puntos acumulados y una representación en forma de tabla.</p>
 *
 * @param <T> el tipo de participante.
 * @param <R> el tipo de resultado asociado a la disciplina.
 */
public interface Estadisticas<T, R> {

    /**
     * Registra un resultado para un participante específico.
     * @param resultado el resultado del enfrentamiento.
     * @param participante el participante involucrado.
     * @param esLocal indica si el participante jugó como local (true) o visitante (false).
     */
    void registrarResultado(R resultado, Participante participante, boolean esLocal);

    /**
     * Devuelve la cantidad total de puntos obtenidos por el participante
     * según los resultados registrados.
     * @return los puntos acumulados.
     */
    int getPuntos();

    /**
     * Retorna una representación en forma de cadena de texto
     * adecuada para ser mostrada en una tabla de estadísticas.
     * @return una cadena con los datos estadísticos formateados.
     */
    String toTablaString();
    /**
     * Reinicia las estadísticas acumuladas, dejando el estado como si no se hubieran registrado resultados.
     */
    void reiniciarEstadisticas();
}
