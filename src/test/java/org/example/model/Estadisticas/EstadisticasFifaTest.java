package org.example.model.Estadisticas;

import org.example.model.Participante.Equipo;
import org.example.model.Resultado.ResultadoFifa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EstadisticasFifaTest {

    private Equipo equipo;
    private EstadisticasFifa estadisticas;

    @BeforeEach
    void setUp() {
        equipo = new Equipo("Equipo A", "1", new ArrayList<>());
        estadisticas = new EstadisticasFifa(equipo);
    }

    @Test
    void testConstructorInicializaCorrectamente() {
        assertEquals(equipo, estadisticas.getParticipante());
        assertEquals(0, estadisticas.getPartidosJugados());
        assertEquals(0, estadisticas.getPuntos());
    }

    @Test
    void testRegistrarVictoriaLocal() {
        Equipo visitante = new Equipo("Equipo B", "2", new ArrayList<>());
        ResultadoFifa resultado = new ResultadoFifa(equipo, visitante, 3, 1, equipo);

        estadisticas.registrarResultado(resultado, equipo, true);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(3, estadisticas.getPuntos());
        assertEquals(3, estadisticas.getGolesFavor());
        assertEquals(1, estadisticas.getGolesContra());
        assertEquals(2, estadisticas.getDiferenciaGoles());
        assertEquals(equipo, resultado.getGanador());
    }

    @Test
    void testRegistrarEmpateVisitante() {
        Equipo local = new Equipo("Equipo B", "2", new ArrayList<>());
        ResultadoFifa resultado = new ResultadoFifa(local, equipo, 2, 2, null);

        estadisticas.registrarResultado(resultado, equipo, false);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(1, estadisticas.getPuntos());
        assertEquals(2, estadisticas.getGolesFavor());
        assertEquals(2, estadisticas.getGolesContra());
        assertEquals(0, estadisticas.getDiferenciaGoles());
        assertNull(resultado.getGanador());
    }

    @Test
    void testRegistrarDerrotaLocal() {
        Equipo visitante = new Equipo("Equipo B", "2", new ArrayList<>());
        ResultadoFifa resultado = new ResultadoFifa(equipo, visitante, 0, 2, visitante);

        estadisticas.registrarResultado(resultado, equipo, true);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(0, estadisticas.getPuntos());
        assertEquals(0, estadisticas.getGolesFavor());
        assertEquals(2, estadisticas.getGolesContra());
        assertEquals(-2, estadisticas.getDiferenciaGoles());
        assertEquals(visitante, resultado.getGanador());
    }

    @Test
    void testToTablaString() {
        Equipo visitante = new Equipo("Equipo B", "2", new ArrayList<>());
        ResultadoFifa resultado = new ResultadoFifa(equipo, visitante, 1, 0, equipo);

        estadisticas.registrarResultado(resultado, equipo, true);

        String tabla = estadisticas.toTablaString();
        assertTrue(tabla.contains("Equipo A"));
        assertTrue(tabla.contains("1")); // PJ o Goles
        assertTrue(tabla.contains("3")); // PTS
    }

    @Test
    void testRegistrarResultadoNuloLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                estadisticas.registrarResultado(null, equipo, true));
        assertEquals("Resultado nulo", ex.getMessage());
    }
}
