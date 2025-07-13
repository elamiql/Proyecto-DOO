package org.example.enums;

/**
 * Enumeración que representa los distintos formatos de competencia disponibles
 * para la organización de torneos en el sistema.
 *
 * <p>Los formatos definen la estructura de los enfrentamientos y el progreso
 * de los participantes en un torneo.</p>
 */
public enum Formato {
    ELIMINATORIA,
    LIGA;
    //GRUPOS_CON_ELIMINATORIA,
    //CUSTOM;

    /**
     * Retorna el nombre del formato como texto.
     * @return el nombre del formato.
     */
    public String getNombre(){
        return name();
    }
}
