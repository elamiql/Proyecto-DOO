package org.example.model;

import org.example.interfaces.Resultado;

public class EstadisticasTenis extends EstadisticasParticipante<Participante, ResultadoTenis>{
    private int golesFavor;
    private int golesContra;

    public EstadisticasTenis(Participante participante){
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

    @Override
    public void registrarResultado(ResultadoTenis resultado, Participante participante, boolean esLocal) {
        if (!resultado.esValido()) throw new IllegalArgumentException("Resultado no valido");



    }

    @Override
    public int getPuntos() {

        return 3*getGanados() + getEmpatados();
    }

    @Override
    public String toTablaString() {
        return String.format("%-20s | %2d | %2d | %2d | %2d | %3d | %3d | %4d | %3d",
                getParticipante().toString(),
                getPartidosJugados(),
                getGanados(),
                getEmpatados(),
                getPerdidos(),
                golesFavor,
                golesContra,
                getDiferenciaGoles(),
                getPuntos());
    }

    public int getGolesFavor(){
        return golesFavor;
    }

    public int getGolesContra(){
        return golesContra;
    }



}
