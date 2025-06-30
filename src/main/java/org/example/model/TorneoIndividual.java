package org.example.model;

import org.example.enums.*;
import java.util.*;

public class TorneoIndividual extends Torneo<Jugador> {

    private int numGrupos;
    private int clasificadosPorGrupo;

    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato,String c) {
        super(nombre, disciplina, fecha, formato,c);
    }

    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato,
                        int numGrupos, int clasificadosPorGrupo,String c) {
        super(nombre, disciplina, fecha, formato,c);
        this.numGrupos = numGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
    }

    public void registrarResultado(Jugador j, boolean sigueActivo) {
        j.setActivo(sigueActivo);
    }

    @Override
    public void generarCalendario() {
        System.out.println("Generando calendario para el torneo: " + getNombre());
        GenerarCalendario<Jugador> generador;

        switch (getFormato()) {
            case LIGA:
                generador = new Liga<>(getParticipantes(), true);
                break;

            case ELIMINATORIA:
                generador = new Eliminatoria<>(getParticipantes(), true);
                break;

            case GRUPOS_CON_ELIMINATORIA:
                generador = new GruposEliminatoria<>(getParticipantes(), numGrupos, clasificadosPorGrupo);
                break;

            default:
                System.out.println("Formato no incluido / soportado");
                return;
        }
        generador.generarCalendario();
        this.enfrentamientos = generador.getEnfrentamientos();
        generador.imprimirCalendario();
    }
}