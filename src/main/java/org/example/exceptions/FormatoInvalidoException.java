package org.example.exceptions;

/**
 * Excepción que se lanza cuando se detecta un formato no válido en algún dato ingresado,
 * como fechas, contraseñas, nombres u otros campos que requieren un formato específico.
 *
 * <p>Es una excepción no verificada (hereda de {@link RuntimeException}).</p>
 */
public class FormatoInvalidoException extends RuntimeException {

    /**
     * Crea una nueva instancia de {@code FormatoInvalidoException} con el mensaje especificado.
     * @param message el mensaje que describe el error de formato.
     */
    public FormatoInvalidoException(String message) {
        super(message);
    }
}
