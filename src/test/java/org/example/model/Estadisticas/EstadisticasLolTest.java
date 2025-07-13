package org.example.model.Estadisticas;

import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoLol;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EstadisticasLolTest {

    Participante j1 = new Jugador("Chino1", "1");
    Participante j2 = new Jugador("Faker", "2");
    Participante j3 = new Jugador("Chino2", "3");
    Participante j4 = new Jugador("FakerPeruano", "4");

    // Creamos listas de jugadores para los equipos
    ArrayList<Jugador> jugadoresEquipo1 = new ArrayList<>(Arrays.asList((Jugador) j1, (Jugador) j3));
    ArrayList<Jugador> jugadoresEquipo2 = new ArrayList<>(Arrays.asList((Jugador) j2, (Jugador) j4));

    // Creamos los equipos
    Participante teamA = new Equipo("Team A", "1", jugadoresEquipo1);
    Participante teamB = new Equipo("Team B", "2", jugadoresEquipo2);
    @Test
    void testEstadisticasActualizadasCorrectamente() {
        ResultadoLol resultado = new ResultadoLol(teamA, teamB);
        resultado.registrarEstadisticas(
                22, 10,  // Kills
                11, 4,   // Torres
                3, 1,    // Dragones
                2, 0,    // Barones
                teamA    // Ganador
        );

        EstadisticasLol estadisticas = new EstadisticasLol(teamA);
        estadisticas.registrarResultado(resultado, teamA, true);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(1, estadisticas.getGanados());
        assertEquals(0, estadisticas.getPerdidos());
        assertEquals(3, estadisticas.getPuntos()); // 3 por victoria

        String resumen = estadisticas.toTablaString();
        assertTrue(resumen.contains("Kills: 22"));
        assertTrue(resumen.contains("Torres: 11"));
    }

    @Test
    void testEstadisticasConDerrota() {
        ResultadoLol resultado = new ResultadoLol(teamA, teamB);
        resultado.registrarEstadisticas(
                12, 17,  // Kills
                5, 10,   // Torres
                1, 3,    // Dragones
                0, 1,    // Barones
                teamB    // Ganador
        );

        EstadisticasLol estadisticas = new EstadisticasLol(teamA);
        estadisticas.registrarResultado(resultado, teamA, true);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(0, estadisticas.getGanados());
        assertEquals(1, estadisticas.getPerdidos());
        assertEquals(0, estadisticas.getPuntos());

        String resumen = estadisticas.toTablaString();
        assertTrue(resumen.contains("Kills: 12"));
        assertTrue(resumen.contains("Torres: 5"));
    }
}
