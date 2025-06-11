package org.example.model;

public abstract class Participante {
    private String nombre;
    private String numero;
    private Boolean Activo;

    public Participante(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
        this.Activo=true;
    }

    // Cambiar para recibir Torneo como parámetro
    public abstract void inscribirse(Torneo torneo);

    public String getNombre() {
        return nombre;
    }

    public String getNumero() {
        return numero;
    }

    public Boolean getActivo(){
        return Activo;
    }

    public void setActivo(Boolean activo){
        this.Activo=activo;
    }
}
