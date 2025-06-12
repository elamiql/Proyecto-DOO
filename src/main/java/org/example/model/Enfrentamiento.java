package org.example.model;

public class Enfrentamiento {

    private Participante participante1;
    private Participante participante2;
    private boolean finalizado;
    private Participante ganador;

    public Enfrentamiento(Participante p1, Participante p2){
        if (p1 == null){
            throw new IllegalArgumentException("Participante1 no puede ser null");
        }
        if (p2 != null && !p1.getClass().equals(p2.getClass())){
            throw new IllegalArgumentException("No se puede enfrentar un jugador con un equipo");
        }

        this.participante1 = p1;
        this.participante2 = p2;
        this.finalizado = false;
    }

    public Participante getParticipante1(){
        return participante1;
    }

    public Participante getParticipante2(){
        return participante2;
    }

    public Boolean isFinalizado(){
        return finalizado;
    }

    public Participante getGanador(){
        return ganador;
    }

    public void setGanador(Participante ganador){
        if (!ganador.equals(participante1) && !ganador.equals(participante2)){
            throw new IllegalArgumentException("El ganador debe ser uno de los participantes");
        }
        this.ganador = ganador;
        this.finalizado = true;
    }

    @Override
    public String toString(){
        return participante1.getNombre() + " vs " + participante2.getNombre();
    }
}
