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

    public void registrarResultado(Jugador j, boolean sigueActivo) {
        j.setActivo(sigueActivo);
        for (Enfrentamiento e : enfrentamientos) {
            if (e.getGanador() == null &&
                    (e.getParticipante1() == j || e.getParticipante2() == j)) {
                e.setGanador(j);
                break;
            }
        }
    }

    @Override
    public void generarCalendario() {
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
                throw new FormatoInvalidoException("Formato no incluido / soportado");
        }

        generador.generarCalendario();
        this.enfrentamientos = generador.getEnfrentamientos();
        generador.imprimirCalendario();
    }
}