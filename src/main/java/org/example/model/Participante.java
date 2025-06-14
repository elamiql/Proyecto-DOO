package org.example.model;

public abstract class Participante {
    private String nombre;
    private String numero;
    private Boolean activo;

    public Participante(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
        this.activo = true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumero() {
        return numero;
    }

    public Boolean getActivo(){
        return activo;
    }

    public void setActivo(Boolean activo){
        this.activo = activo;
    }

    // Cambiar para recibir Torneo como parámetro
    public abstract void inscribirse(Torneo<?> torneo);
}
