package org.example.model;

import org.example.enums.*;

public class TorneoEquipo extends Torneo<Equipo> {
    private int numGrupos;
    private int clasificadosPorGrupo;

    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato,String c) {
        super(nombre, disciplina, fecha, formato,c);
        this.numGrupos = 8;
        this.clasificadosPorGrupo = 2;
    }

    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato,
                        int numGrupos, int clasificadosPorGrupo,String c) {
        super(nombre, disciplina, fecha, formato,c);
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
        System.out.println("Generando calendario para el torneo: " + getNombre());

        GenerarCalendario<Equipo> generador;

        switch (getFormato()) {
            case LIGA:
                generador = new Liga<>(getEquipos(), true);
                generador.generarCalendario();
                this.enfrentamientos = generador.getEnfrentamientos();
                generador.imprimirCalendario();
                break;

            case ELIMINATORIA:
                generador = new Eliminatoria<>(getEquipos(), true);
                generador.generarCalendario();
                this.enfrentamientos = generador.getEnfrentamientos();
                generador.imprimirCalendario();
                break;

            case GRUPOS_CON_ELIMINATORIA:
                if (numGrupos <= 0 || clasificadosPorGrupo <= 0) {
                    System.out.println("Error: numGrupos y clasificadosPorGrupo deben ser mayores que 0");
                    return;
                }
                generador = new GruposEliminatoria<>(getEquipos(), numGrupos, clasificadosPorGrupo);
                generador.generarCalendario();
                this.enfrentamientos = generador.getEnfrentamientos();
                generador.imprimirCalendario();
                break;

            default:
                System.out.println("Formato no soportado aún.");
        }
    }
}