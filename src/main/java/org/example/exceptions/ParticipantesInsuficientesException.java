package org.example.exceptions;

/**
 * Excepción que se lanza cuando un torneo no cuenta con la cantidad mínima requerida de participantes
 * para poder iniciar o continuar su ejecución.
 *
 * <p>Es una excepción no verificada (hereda de {@link RuntimeException}).</p>
 *
 * <p>Se utiliza típicamente en validaciones internas al generar enfrentamientos o al iniciar un torneo.</p>
 */
public class ParticipantesInsuficientesException extends RuntimeException {

    /**
     * Crea una nueva instancia de {@code ParticipantesInsuficientesException} con el mensaje especificado.
     * @param message el mensaje que describe el error.
     */
    public ParticipantesInsuficientesException(String message) {
        super(message);
    }
}
