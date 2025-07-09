package org.example.model;

import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ParticipanteTest {

    @Test
    void testJugadorActivo() {
        Jugador jugador = new Jugador("Test", "1");
        assertTrue(jugador.getActivo());

        jugador.setActivo(false);
        assertFalse(jugador.getActivo());
    }

    @Test
    void testEquipoActivo() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        Equipo equipo = new Equipo("Test Team", "1", jugadores);
        assertTrue(equipo.getActivo());

        equipo.setActivo(false);
        assertFalse(equipo.getActivo());
    }

    @Test
    void testParticipanteToString() {
        Jugador jugador = new Jugador("Juan Carlos", "10");
        assertEquals("Juan Carlos 10", jugador.toString());

        ArrayList<Jugador> jugadores = new ArrayList<>();
        Equipo equipo = new Equipo("FC Barcelona", "1", jugadores);
        assertEquals("FC Barcelona", equipo.toString());
    }
}