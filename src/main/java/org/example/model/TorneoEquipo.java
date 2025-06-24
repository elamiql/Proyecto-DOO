package org.example.model;

import org.example.enums.*;
import java.util.*;

public class TorneoEquipo extends Torneo<Equipo> {
    private int numGrupos = 0;
    private int clasificadosPorGrupo = 0;

    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato) {
        super(nombre, disciplina, fecha, formato);
    }

    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato,
                        int numGrupos, int clasificadosPorGrupo) {
        super(nombre, disciplina, fecha, formato);
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
                generador.imprimirCalendario();
                break;

            case ELIMINATORIA:
                generador = new Eliminatoria<>(getEquipos(), true);
                generador.generarCalendario();
                generador.imprimirCalendario();
                break;

            case GRUPOS_CON_ELIMINATORIA:
                if (numGrupos <= 0 || clasificadosPorGrupo <= 0) {
                    System.out.println("Error: numGrupos y clasificadosPorGrupo deben ser mayores que 0");
                    return;
                }
                generador = new GruposEliminatoria<>(getEquipos(), numGrupos, clasificadosPorGrupo);
                generador.generarCalendario();
                generador.imprimirCalendario();
                break;

            default:
                System.out.println("Formato no soportado aún.");
        }
    }
}