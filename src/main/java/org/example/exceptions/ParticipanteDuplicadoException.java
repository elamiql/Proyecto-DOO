package org.example.exceptions;

/**
 * Excepción que se lanza cuando se intenta registrar un participante que ya ha sido inscrito previamente
 * en un torneo.
 * <p>Es una excepción no verificada (hereda de {@link RuntimeException}).</p>
 */
public class ParticipanteDuplicadoException extends RuntimeException {

    /**
     * Crea una nueva instancia de {@code ParticipanteDuplicadoException} con el mensaje especificado.
     * @param message el mensaje que describe el error.
     */
    public ParticipanteDuplicadoException(String message) {
        super(message);
    }
}
