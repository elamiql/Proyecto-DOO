package org.example.model;

public class EstadisticasParticipante<T> {
    private T participante;
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;

    public EstadisticasParticipante(T participante){
        this.participante = participante;
        this.partidosJugados = 0;
        this.ganados = 0;
        this.empatados = 0;
        this.perdidos = 0;
    }

    public void registrarVictoria() {
        ganados++;
        partidosJugados++;
    }

    public void registrarEmpate(){
        empatados++;
        partidosJugados++;
    }

    public void registrarDerrota(){
        perdidos++;
        partidosJugados++;
    }

    public void reiniciarEstadisticas(){
        partidosJugados = 0;
        ganados = 0;
        empatados = 0;
        perdidos = 0;
    }

    @Override
    public String toString() {
        return "Estadísticas de " + participante +
                " - PJ: " + partidosJugados +
                ", G: " + ganados +
                ", E: " + empatados +
                ", P: " + perdidos;
    }

    // Getters y setters
    public T getParticipante() {
        return participante;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getGanados() {
        return ganados;
    }

    public int getEmpatados() {
        return empatados;
    }

    public int getPerdidos() {
        return perdidos;
    }
}
