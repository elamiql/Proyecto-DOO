package org.example.model;

import org.example.enums.*;
import java.util.*;

public class TorneoIndividual extends Torneo<Jugador> {

    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato) {
        super(nombre, disciplina, fecha, formato);
    }

    public void registrarResultado(Jugador j, boolean sigueActivo) {
        j.setActivo(sigueActivo);
    }

    public void addJugador(Jugador jugador) {
        addParticipante(jugador);
    }

    @Override
    public void generarCalendario() {
        System.out.println("Generando calendario para el torneo: " + getNombre());
        GenerarCalendario<Jugador> generador = new GenerarCalendario<>(getParticipantes(), getFormato());

        switch (getFormato()) {
            case ELIMINATORIA -> {
                generador.generar();
                generador.crearYGuardarBracket();
                generador.imprimirBracket();
            }

            case LIGA -> {
                generador.generar();
                generador.imprimirCalendario();
            }

            default -> System.out.println("Formato no soportado para torneo individual.");
        }
    }
}