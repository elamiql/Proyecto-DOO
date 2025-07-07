package org.example.exceptions;

/**
 * Excepción que se lanza cuando se ingresan datos inválidos o incompletos en una operación.
 *
 * <p>Hereda de {@link RuntimeException}, por lo que no requiere ser declarada en las firmas
 * de los métodos donde se lanza.</p>
 */
public class DatosInvalidosException extends RuntimeException {

    /**
     * Crea una nueva instancia de {@code DatosInvalidosException} con el mensaje especificado.
     * @param message el mensaje que describe los datos inválidos detectados.
     */
    public DatosInvalidosException(String message) {
        super(message);
    }
}
