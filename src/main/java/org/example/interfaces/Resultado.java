package org.example.interfaces;

import org.example.model.Participante.Participante;

/**
 * Representa el resultado de un enfrentamiento entre participantes.
 * <p>Esta interfaz define los métodos necesarios para obtener información relevante
 * sobre un resultado, como su validez, el ganador y un resumen en forma de texto.</p>
 */
public interface Resultado {

    /**
     * Devuelve un resumen textual del resultado del enfrentamiento.
     * @return una cadena que describe el resultado.
     */
    String getResumen();

    /**
     * Devuelve el participante que resultó ganador del enfrentamiento.
     * @return el participante ganador, o {@code null} si hubo un empate o no se puede determinar.
     */
    Participante getGanador();

    /**
     * Indica si el resultado registrado es válido.
     *
     * @return {@code true} si el resultado es válido; {@code false} en caso contrario.
     */
    boolean esValido();
}
