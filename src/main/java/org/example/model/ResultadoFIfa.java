package org.example.model;

import org.example.interfaces.Resultado;

public class ResultadoFifa implements Resultado {
    private int golesLocal;
    private int golesVisitante;
    private Participante local;
    private Participante visitante;

    public ResultadoFifa(Participante local, Participante visitante, int golesLocal, int golesVisitante) {
        if (golesLocal < 0 || golesVisitante < 0) {
            throw new IllegalArgumentException("Los goles no pueden ser negativos");
        }
        this.local = local;
        this.visitante = visitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    @Override
    public Participante getGanador() {
        if (golesLocal > golesVisitante) return local;
        else if (golesVisitante > golesLocal) return visitante;
        else return null; // Empate
    }

    @Override
    public boolean esValido() {
        // Validamos que goles sean >=0 y participantes no nulos
        return local != null && visitante != null && golesLocal >= 0 && golesVisitante >= 0;
    }

    @Override
    public String getResumen() {
        return String.format("%s %d - %d %s", local.toString(), golesLocal, golesVisitante, visitante.toString());
    }
}
