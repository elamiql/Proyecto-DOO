package org.example.model;

import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoLol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ResultadoLolTest {

    Participante j1 = new Jugador("Chino1", "1");
    Participante j2 = new Jugador("Faker", "2");
    Participante j3 = new Jugador("Chino2", "3");
    Participante j4 = new Jugador("FakerPeruano", "4");

    // Creamos listas de jugadores para los equipos
    ArrayList<Jugador> jugadoresEquipo1 = new ArrayList<>(Arrays.asList((Jugador) j1, (Jugador) j3));
    ArrayList<Jugador> jugadoresEquipo2 = new ArrayList<>(Arrays.asList((Jugador) j2, (Jugador) j4));

    // Creamos los equipos
    Participante equipo1 = new Equipo("Team A", "1", jugadoresEquipo1);
    Participante equipo2 = new Equipo("Team B", "2", jugadoresEquipo2);

    @Test
    void testResultadoCorrectoYValido() {
        ResultadoLol resultado = new ResultadoLol(equipo1, equipo2);
        resultado.registrarEstadisticas(
                20, 15, // Kills
                10, 6,  // Torres
                3, 1,   // Dragones
                2, 0,   // Barones
                equipo1 // Ganador
        );

        assertTrue(resultado.esValido());
        assertEquals(equipo1, resultado.getGanador());
        assertEquals(20, resultado.getKills(equipo1));
        assertEquals(6, resultado.getTorres(equipo2));
    }

    @Test
    void testResultadoInvalidoSinGanador() {
        ResultadoLol resultado = new ResultadoLol(equipo1, equipo2);
        resultado.registrarEstadisticas(
                15, 15,
                7, 7,
                2, 2,
                1, 1,
                null
        );

        assertFalse(resultado.esValido());
        assertNull(resultado.getGanador());
    }

    @Test
    void testResumenGeneradoCorrectamente() {
        ResultadoLol resultado = new ResultadoLol(equipo1, equipo2);
        resultado.registrarEstadisticas(18, 12, 9, 5, 4, 2, 1, 1, equipo1);

        String resumen = resultado.getResumen();

        assertTrue(resumen.contains("Kills: 18 - 12"));
        assertTrue(resumen.contains("Ganador: Team A"));
    }
}
