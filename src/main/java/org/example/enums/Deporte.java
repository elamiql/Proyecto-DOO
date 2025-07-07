package org.example.enums;
import org.example.interfaces.Disciplina;

/**
 * Enumeración que representa las disciplinas deportivas disponibles en el sistema de torneos.
 *
 * <p>Cada constante de esta enumeración implementa la interfaz {@link Disciplina},
 * lo que permite utilizarla de forma genérica junto a otras disciplinas como videojuegos.</p>
 *
 */
public enum Deporte implements Disciplina {
    FUTBOL,
    TENIS,
    TENIS_DE_MESA,
    AJEDREZ;

    /**
     * Retorna el nombre de la disciplina en formato de cadena.
     * @return el nombre de la disciplina.
     */
    @Override
    public String getNombre(){
        return name();
    }
}
