package org.example.exceptions;

/**
 * Excepción que se lanza cuando se detecta que un participante es {@code null}
 * en una operación que requiere su presencia.
 *
 * <p>Es una excepción no verificada (hereda de {@link RuntimeException}).</p>
 *
 * <p>Esta excepción suele utilizarse para validar que los enfrentamientos, inscripciones u otras
 * operaciones relacionadas con participantes tengan valores válidos.</p>
 */
public class ParticipanteNullException extends RuntimeException {

    /**
     * Crea una nueva instancia de {@code ParticipanteNullException} con el mensaje especificado.
     * @param message el mensaje que describe el error.
     */
    public ParticipanteNullException(String message) {
        super(message);
    }
}
