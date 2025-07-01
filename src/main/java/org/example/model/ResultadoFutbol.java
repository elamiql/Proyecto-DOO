package org.example.model;

import org.example.interfaces.Resultado;

public class ResultadoFutbol implements Resultado {
    private int golesLocal;
    private int golesVisitante;
    private Participante p1;
    private Participante p2;

    public ResultadoFutbol(Participante p1, Participante p2, int golesLocal, int golesVisitante){
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public String getResumen() {
        return p1.getNombre() + " " + golesLocal + " - " + golesVisitante + " " + p2.getNombre();
    }

    @Override
    public boolean esValido() {
        return golesLocal >= 0 && golesVisitante >=0;
    }

    @Override
    public Participante getGanador(){
        if (golesLocal > golesVisitante){
            return p1;
        }
        else if (golesVisitante > golesLocal){
            return p2;
        }
        else{
            return null; //empate
        }
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public Participante getLocal() {
        return p1;
    }

    public Participante getVisitante() {
        return p2;
    }
}
