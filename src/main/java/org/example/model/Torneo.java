package org.example.model;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Torneo {
    String nombre;
    String disciplina;
    ArrayList<Jugador> participantes=new ArrayList<>();;
    ArrayList<Equipo> equipos=new ArrayList<>();;
    private LocalDateTime fecha;
    String formato;
    boolean activo = false;

    public Torneo(String nombre, String disciplina, ArrayList<Jugador> participantes, String fecha,String formato) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.participantes = participantes;
        this.formato=formato;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.fecha = LocalDateTime.parse(fecha, formatter);
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
    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public ArrayList<Jugador> getParticipantes() {
        return participantes;
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.fecha = LocalDateTime.parse(fechaStr, formatter);
    }

    public String getFormato() {
        return formato;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

