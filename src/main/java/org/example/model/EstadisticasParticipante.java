package org.example.model;

import org.example.interfaces.*;


public abstract class EstadisticasParticipante<T, R extends Resultado> implements Estadisticas<T, R>{
    private T participante;
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;

    public EstadisticasParticipante(T participante){
        this.participante = participante;
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
        return participante +
                " - PJ: " + partidosJugados +
                ", G: " + ganados +
                ", E: " + empatados +
                ", P: " + perdidos;
    }

    @Override
    public abstract void registrarResultado(R resultado, T participante, boolean esLocal);

    @Override
    public abstract int getPuntos();

    public abstract String toTablaString();

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
