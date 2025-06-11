package org.example.model;
import java.util.ArrayList;

public class Organizador {
    String nombre;

    public Organizador(String nombre) {
        this.nombre = nombre;
    }

    public Torneo crearTorneo(String nombre, String disciplina, ArrayList<Jugador> participantes, String fecha,String formato) {
        return new Torneo(nombre, disciplina, participantes, fecha,formato);
    }

    public void registrarResultados(Torneo torneo, Jugador participante, boolean sigueActivo) {
        torneo.registrarResultados(participante, sigueActivo);
    }
}