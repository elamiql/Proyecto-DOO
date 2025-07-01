package org.example.model;

public class EstadisticasFutbol extends EstadisticasParticipante<Participante>{

    private int golesFavor;
    private int golesContra;

    public EstadisticasFutbol(Participante participante){
        super(participante);
    }

    public void agregarGoles(int golesFavor, int golesContra){
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;
    }

    public int getDiferenciaGoles(){
        return golesFavor - golesContra;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", GF: " + golesFavor +
                ", GC: " + golesContra +
                ", DIF: " + getDiferenciaGoles();
    }
}
