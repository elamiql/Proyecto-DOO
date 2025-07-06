package org.example.interfaces;

/**
 * Representa una disciplina o categoría dentro de una competencia o torneo.
 *
 * <p>Esta interfaz define el comportamiento mínimo que cualquier clase que represente una
 * disciplina debe implementar.</p>
 */
public interface Disciplina {

    /**
     * Devuelve el nombre de la disciplina.
     * @return el nombre de la disciplina como una cadena de texto.
     */
    String getNombre();
}
