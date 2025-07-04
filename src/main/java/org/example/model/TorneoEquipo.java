package org.example.model;

import org.example.enums.*;
import org.example.exceptions.ParticipantesInsuficientesException;
import org.example.interfaces.Disciplina;

public class TorneoEquipo extends Torneo<Equipo> {
    private int numGrupos;
    private int clasificadosPorGrupo;

    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato, String c) {
        //Constructor por defecto para torneos de equipo de 32 equipos con estilo mundialista
        super(nombre, disciplina, fecha, formato, c);
        this.numGrupos = 8;
        this.clasificadosPorGrupo = 2;
    }

    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato,
                        int numGrupos, int clasificadosPorGrupo, String c) {
        super(nombre, disciplina, fecha, formato, c);
        this.numGrupos = numGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
    }

    public int getNumGrupos() {
        return numGrupos;
    }

    public int getClasificadosPorGrupo() {
        return clasificadosPorGrupo;
    }

    @Override
    public void generarCalendario() {
        GenerarCalendario<Equipo> generador;

        switch (getFormato()) {
            case LIGA:
                generador = new Liga<>(getEquipos(), true);
                generador.generarCalendario();
                this.enfrentamientos = generador.getEnfrentamientos();
                break;

            case ELIMINATORIA:
                generador = new Eliminatoria<>(getEquipos(), true);
                generador.generarCalendario();
                this.enfrentamientos = generador.getEnfrentamientos();
                break;

            case GRUPOS_CON_ELIMINATORIA:
                if (numGrupos <= 0 || clasificadosPorGrupo <= 0) {
                    throw new ParticipantesInsuficientesException("Error: numGrupos y clasificadosPorGrupo deben ser mayores que 0");
                }
                generador = new GruposEliminatoria<>(getEquipos(), numGrupos, clasificadosPorGrupo);
                generador.generarCalendario();
                this.enfrentamientos = generador.getEnfrentamientos();
                break;

            default:
                throw new NumberFormatException("Formato no soportado aún");
        }
    }
}