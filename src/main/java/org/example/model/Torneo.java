package org.example.model;

import org.example.enums.*;
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

    public Torneo(String nombre, Disciplina disciplina, String fecha, Formato formato,String Contraseña) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.formato = formato;
        this.participantes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.fecha = LocalDateTime.parse(fecha, formatter);
        this.jugadores = new ArrayList<>();
        this.equipos = new ArrayList<>();
        this.enfrentamientos = new ArrayList<>();
        this.contraseña=Contraseña;
    }

    public void addParticipante(Participante participante){
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

    public void registrarResultados(Jugador p, boolean sigueActivo) {
        p.setActivo(sigueActivo);
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
            System.out.println("Torneo activado: " + nombre);

            // ✅ Solo si hay suficientes participantes
            if (participantes.size() >= 2) {
                generarCalendario();
            } else {
                System.out.println("No se puede generar calendario: menos de 2 participantes");
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


