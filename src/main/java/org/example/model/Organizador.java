package org.example.model;

import org.example.enums.*;
import java.util.ArrayList;

public class Organizador {
    String nombre;

    public Organizador(String nombre) {
        this.nombre = nombre;
    }
    /*
    public TorneoIndividual crearTorneoIndividual(String nombre, Disciplina disciplina, ArrayList<Jugador> participantes, String fecha, Formato formato) {
        return new TorneoIndividual(nombre, disciplina, fecha, formato);
    }

    public TorneoEquipo crearTorneoEquipo(String nombre, Disciplina disciplina, ArrayList<Equipo> participantes, String fecha, Formato formato) {
        return new TorneoEquipo(nombre, disciplina, fecha, formato);
    }

     */

    public void registrarResultados(Torneo<?> torneo, Jugador participante, boolean sigueActivo) {
        torneo.registrarResultados(participante, sigueActivo);
    }
}