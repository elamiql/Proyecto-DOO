package org.example.enums;

import org.example.interfaces.Disciplina;

/**
 * Enumeración que representa las disciplinas de videojuegos disponibles en el sistema de torneos.
 *
 * <p>Cada constante implementa la interfaz {@link Disciplina}, lo que permite manejar
 * videojuegos junto con otras disciplinas como deportes.</p>
 */
public enum Videojuegos implements Disciplina {
    FIFA,
    LOL;

    /**
     * Retorna el nombre de la disciplina en formato de cadena.
     * @return el nombre de la disciplina.
     */
    @Override
    public String getNombre(){
        return name();
    }
}
