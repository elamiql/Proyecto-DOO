package org.example.model;

import org.example.enums.*;

public class TorneoIndividual extends Torneo<Jugador>{

    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato ){
        super(nombre, disciplina, fecha, formato);
    }

    public void registrarResultado(Jugador j, boolean sigueActivo){
        j.setActivo(sigueActivo);
    }

    @Override
    public void generarCalendario(){
        //TODO
    }
}
