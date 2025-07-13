package org.example.model.torneo;

import org.example.exceptions.*;
import org.example.enums.*;
import org.example.interfaces.*;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Enfrentamientos.GenerarCalendario;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Clase abstracta que representa los torneos
 * Administra la inscripción de participantes, control de fechas, enfrentamientos y el formato del torneo.
 * @param <T> Tipo de participante, que debe extender de {@link Participante}.
 */
public abstract class Torneo <T extends Participante>{

    /** Nombre del torneo */
    protected String nombre;

    /** Disciplina del torneo */
    protected Disciplina disciplina;

    /** Lista de participantes inscritos */
    protected ArrayList<T> participantes;


    protected GenerarCalendario<T> generadorActivo;

    /** Formato del torneo (liga, eliminación, grupos) */
    protected Formato formato;

    /** Fecha de inicio del torneo */
    protected LocalDateTime fecha;

    /** Indica si el torneo está activo */
    protected boolean activo = false;

    /** Contraseña de acceso o inscripción al torneo */
    protected String contraseña;

    /** Lista de enfrentamientos programados en el torneo */
    protected ArrayList<Enfrentamiento> enfrentamientos;

    /**
     * Constructor del torneo. Inicializa los atributos básicos y listas.
     * @param nombre Nombre del torneo.
     * @param disciplina Disciplina o deporte del torneo.
     * @param fecha Fecha de inicio en formato "dd-MM-yyyy HH:mm".
     * @param formato Formato del torneo.
     * @param contraseña Contraseña de inscripción.
     */
    public Torneo(String nombre, Disciplina disciplina, String fecha, Formato formato, String contraseña) {
        this.nombre = nombre;
        this.disciplina = disciplina;
        this.formato = formato;
        this.participantes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.fecha = LocalDateTime.parse(fecha, formatter);
        this.enfrentamientos = new ArrayList<>();
        this.contraseña = contraseña;
    }

    /**
     * Agrega un participante al torneo. Lanza excepción si ya está inscrito.
     * @param participante Participante a agregar.
     * @throws ParticipanteDuplicadoException si el participante ya está inscrito.
     */
    public void addParticipante(Participante participante){

        if (participantes.contains((T) participante)){
            throw new ParticipanteDuplicadoException("El participante ya esta inscrito");
        }

        participantes.add((T) participante);
    }

    // Getters
    /**
     * Devuelve la lista de enfrentamientos del torneo.
     * @return Lista de enfrentamientos.
     */
    public ArrayList<Enfrentamiento> getEnfrentamientos(){
        return enfrentamientos;
    }

    /**
     * Establece la lista de enfrentamientos del torneo.
     * @param enfrentamientos Lista nueva de enfrentamientos.
     */
    public void setEnfrentamientos(ArrayList<Enfrentamiento> enfrentamientos){
        this.enfrentamientos = enfrentamientos;
    }

    /**
     * Devuelve la lista de todos los participantes inscritos.
     * @return Lista de participantes.
     */
    public ArrayList<T> getParticipantes() {
        return participantes;
    }



    /**
     * Devuelve la contraseña del torneo.
     * @return Contraseña.
     */
    public String getContraseña(){
        return contraseña;
    }


    /**
     * Devuelve el nombre del torneo.
     * @return Nombre del torneo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la fecha de inicio del torneo.
     * @return Fecha del torneo.
     */
    public LocalDateTime getFecha(){
        return fecha;
    }

    /**
     * Devuelve la disciplina del torneo.
     * @return Disciplina.
     */
    public Disciplina getDisciplina() {
        return disciplina;
    }

    /**
     * Establece la fecha de inicio del torneo desde un String.
     * @param fechaStr Fecha en formato "dd-MM-yyyy HH:mm".
     */
    public void setFecha(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.fecha = LocalDateTime.parse(fechaStr, formatter);
    }

    /**
     * Devuelve el formato del torneo.
     * @return Formato.
     */
    public Formato getFormato() {
        return formato;
    }

    /**
     * Indica si el torneo está activo.
     *
     * @return true si está activo, false en caso contrario.
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Cambia el estado de actividad del torneo.
     * @param activo Nuevo estado.
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Verifica si el torneo ya debe comenzar (según la fecha actual),
     * lo marca como activo y genera el calendario si hay suficientes participantes.
     * @throws ParticipantesInsuficientesException si hay menos de 2 participantes.
     */
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

    /**
     * Devuelve una representación en texto del torneo con nombre, disciplina, formato y fecha.
     * @return Descripción del torneo.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return nombre + " - " + disciplina.getNombre() + " - " + formato + " - " + fecha.format(formatter);
    }

    /**
     * Método abstracto para generar el calendario del torneo.
     */
    public abstract void generarCalendario();

    public GenerarCalendario<T> getGeneradorActivo() {
        return generadorActivo;
    }

    public ArrayList<T> getJugadores() {
        return participantes;
    }
}


