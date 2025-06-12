package org.example.model;

import org.example.enums.*;

public class TorneoEquipo extends Torneo<Equipo>{

    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato){
        super(nombre, disciplina, fecha, formato);
    }

    public void addEquipo(Equipo e){
        super.equipos.add(e);
    }

    @Override
    public void generarCalendario(){
        System.out.println("Generando calendario para el torneo: "+ getNombre());
        GenerarCalendario<Equipo> generarCalEquipo = new GenerarCalendario<>(getEquipos(), getFormato());
        generarCalEquipo.generar();
        generarCalEquipo.imprimirCalendario();
    }
}
