package org.example;

import java.util.ArrayList;


class Torneo {
    String nombre;
    String disciplina;
    ArrayList<Jugador> participantes;
    ArrayList<Equipo> equipos;
    String fecha;
    String formato;
    boolean activo = false;

    public Torneo(String nombre, String disciplina, ArrayList<Jugador> participantes, String fecha,String formato) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.participantes = participantes;
        this.fecha = fecha;
        this.formato=formato;


    }

    public void registrarResultados(Jugador p, boolean sigueActivo) {
        p.setActivo(sigueActivo);
    }
    public void addparticipante(Jugador jugador){
        participantes.add(jugador);
    }
    public void addequipo(Equipo equipo){
        equipos.add(equipo);
    }
}

