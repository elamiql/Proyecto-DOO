package org.example.model;

import org.example.enums.*;
import org.example.exceptions.*;
import org.example.interfaces.Disciplina;

public class TorneoIndividual extends Torneo<Jugador> {

    private int numGrupos;
    private int clasificadosPorGrupo;

    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato, String c) {
        super(nombre, disciplina, fecha, formato, c);
    }

    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato,
                        int numGrupos, int clasificadosPorGrupo, String c) {
        super(nombre, disciplina, fecha, formato, c);
        this.numGrupos = numGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
    }

    @Override
    public void generarCalendario() {
        GenerarCalendario<Jugador> generador;

        if (participantes.size() < 2){
            throw new ParticipantesInsuficientesException("Se requieren 2 participantes");
        }

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
                throw new FormatoInvalidoException("Formato no incluido / soportado");
        }

        generador.generarCalendario();
        this.enfrentamientos = generador.getEnfrentamientos();
        generador.imprimirCalendario();
    }
}