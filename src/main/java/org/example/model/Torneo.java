package org.example.model;

import org.example.exceptions.*;
import org.example.enums.*;
import org.example.interfaces.*;

import java.awt.desktop.OpenFilesEvent;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Torneo <T extends Participante>{
    protected String nombre;
    protected Disciplina disciplina;
    protected ArrayList<T> participantes;
    protected ArrayList<Jugador> jugadores;
    protected ArrayList<Equipo> equipos;
    protected Formato formato;
    protected LocalDateTime fecha;
    protected boolean activo = false;
    protected String contraseña;
    protected ArrayList<Enfrentamiento> enfrentamientos;

    public Torneo(String nombre, Disciplina disciplina, String fecha, Formato formato, String contraseña) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.formato = formato;
        this.participantes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.fecha = LocalDateTime.parse(fecha, formatter);
        this.jugadores = new ArrayList<>();
        this.equipos = new ArrayList<>();
        this.enfrentamientos = new ArrayList<>();
        this.contraseña = contraseña;
    }

    public void addParticipante(Participante participante){

        if (participantes.contains((T) participante)){
            throw new ParticipanteDuplicadoException("El participante ya esta inscrito");
        }

        participantes.add((T) participante);
        if (participante instanceof Jugador){
            jugadores.add((Jugador) participante);
        }
        else if (participante instanceof Equipo){
            equipos.add((Equipo) participante);
        }
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos(){
        return enfrentamientos;
    }

    public void setEnfrentamientos(ArrayList<Enfrentamiento> enfrentamientos){
        this.enfrentamientos = enfrentamientos;
    }

    public ArrayList<T> getParticipantes() {
        return participantes;
    }


    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
    public String getContraseña(){
        return contraseña;
    }


    public ArrayList<Equipo> getEquipos(){
        return equipos;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public LocalDateTime getFecha(){
        return fecha;
    }
    
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setFecha(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.fecha = LocalDateTime.parse(fechaStr, formatter);
    }

    public Formato getFormato() {
        return formato;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void actualizarEstado() {
        if (!activo && fecha.isBefore(java.time.LocalDateTime.now())) {
            activo = true;

            if (participantes.size() >= 2) {
                generarCalendario();
            } else {
                throw new ParticipantesInsuficientesException("No se puede generar calendario: menos de 2 participantes");
            }
        }
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return nombre + " - " + disciplina.getNombre() + " - " + formato + " - " + fecha.format(formatter);
    }

    public abstract void generarCalendario();
}


