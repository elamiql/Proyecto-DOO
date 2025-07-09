package org.example.model;

import org.example.exceptions.ParticipanteNullException;
import org.example.interfaces.Resultado;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoFutbol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EnfrentamientoTest {

    private Jugador jugador1;
    private Jugador jugador2;
    private Enfrentamiento enfrentamiento;

    @BeforeEach
    void setUp() {
        jugador1 = new Jugador("Juan", "1");
        jugador2 = new Jugador("Pedro", "2");
        enfrentamiento = new Enfrentamiento(jugador1, jugador2);
    }

    @Test
    void testConstructorValido() {
        assertNotNull(enfrentamiento);
        assertEquals(jugador1, enfrentamiento.getParticipante1());
        assertEquals(jugador2, enfrentamiento.getParticipante2());
        assertFalse(enfrentamiento.isFinalizado());
        assertNull(enfrentamiento.getGanador());
    }

    @Test
    void testConstructorConParticipanteNull() {
        assertThrows(ParticipanteNullException.class,
                () -> new Enfrentamiento(null, jugador2));
        assertThrows(ParticipanteNullException.class,
                () -> new Enfrentamiento(jugador1, null));
    }

    @Test
    void testConstructorConDiferentesTipos() {
        Equipo equipo = new Equipo("Equipo1", "1", new ArrayList<>());
        assertThrows(ParticipanteNullException.class,
                () -> new Enfrentamiento(jugador1, equipo));
    }

    @Test
    void testSetGanadorValido() {
        enfrentamiento.setGanador(jugador1);
        assertEquals(jugador1, enfrentamiento.getGanador());
        assertTrue(enfrentamiento.isFinalizado());
    }

    @Test
    void testSetGanadorInvalido() {
        Jugador jugador3 = new Jugador("Carlos", "3");
        assertThrows(IllegalArgumentException.class,
                () -> enfrentamiento.setGanador(jugador3));
    }

    @Test
    void testToString() {
        String expected = "Juan vs Pedro";
        assertEquals(expected, enfrentamiento.toString());
    }

    @Test
    void testRegistrarResultadoValido() {
        Resultado resultado = new ResultadoFutbol(jugador1, jugador2, 3, 2) {
            @Override
            public boolean esValido() { return true; }
            @Override
            public Participante getGanador() { return jugador1; }
        };

        enfrentamiento.registrarResultado(resultado);
        assertEquals(resultado, enfrentamiento.getResultado());
        assertEquals(jugador1, enfrentamiento.getGanador());
        assertTrue(enfrentamiento.isFinalizado());
    }

    @Test
    void testRegistrarResultadoInvalido() {
        Resultado resultado = new ResultadoFutbol(jugador1, jugador2, 0, -1) {
            @Override
            public boolean esValido() { return false; }
            @Override
            public Participante getGanador() { return jugador1; }
        };

        assertThrows(IllegalArgumentException.class,
                () -> enfrentamiento.registrarResultado(resultado));
    }
}