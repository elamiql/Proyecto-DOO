package org.example.model;

/**
 * Clase abstracta que representa un participante genérico en un torneo.
 * Puede representar a una persona, equipo, o entidad que compite en un {@link Torneo}.
 */
public abstract class Participante {

    /** Nombre del participante */
    private final String nombre;

    /** Número identificador del participante (por ejemplo, dorsal, ID, etc.) */
    private final String numero;

    /** Estado del participante: activo o inactivo */
    private Boolean activo;


    /**
     * Constructor que inicializa los atributos básicos del participante.
     * @param nombre Nombre del participante.
     * @param numero Número o identificador del participante.
     */
    public Participante(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
        this.activo = true;
    }

    /**
     * Devuelve el nombre del participante.
     * @return Nombre del participante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el número o identificador del participante.
     * @return Número del participante.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Devuelve si el participante está activo.
     *
     * @return {@code true} si está activo, {@code false} si no.
     */
    public Boolean getActivo(){
        return activo;
    }

    /**
     * Establece el estado activo o inactivo del participante.
     * @param activo Nuevo estado del participante.
     */
    public void setActivo(Boolean activo){
        this.activo = activo;
    }

    /**
     * Devuelve una representación en texto del participante, usando su nombre.
     * @return Nombre del participante como cadena.
     */
    @Override
    public String toString(){
        return nombre;
    }

    /**
     * Método abstracto que define cómo un participante se inscribe en un torneo.
     * @param torneo Torneo en el que el participante se inscribirá.
     */
    public abstract void inscribirse(Torneo<?> torneo);
}
