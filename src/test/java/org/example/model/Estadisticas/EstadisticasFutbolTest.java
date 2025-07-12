package org.example.model.Estadisticas;

import org.example.model.Participante.Equipo;
import org.example.model.Resultado.ResultadoFutbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EstadisticasFutbolTest {

    private Equipo equipo;
    private EstadisticasFutbol estadisticas;

    @BeforeEach
    void setUp() {
        equipo = new Equipo("Equipo A", "1", new ArrayList<>());
        estadisticas = new EstadisticasFutbol(equipo);
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
        ResultadoFutbol resultado = new ResultadoFutbol(equipo, visitante, 3, 1);

        estadisticas.registrarResultado(resultado, equipo, true);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(3, estadisticas.getPuntos());
        assertEquals(3, estadisticas.getGolesFavor());
        assertEquals(1, estadisticas.getGolesContra());
        assertEquals(2, estadisticas.getDiferenciaGoles());
    }

    @Test
    void testRegistrarEmpateVisitante() {
        Equipo local = new Equipo("Equipo B", "2", new ArrayList<>());
        ResultadoFutbol resultado = new ResultadoFutbol(local, equipo, 2, 2);

        estadisticas.registrarResultado(resultado, equipo, false);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(1, estadisticas.getPuntos());
        assertEquals(2, estadisticas.getGolesFavor());
        assertEquals(2, estadisticas.getGolesContra());
        assertEquals(0, estadisticas.getDiferenciaGoles());
    }

    @Test
    void testRegistrarDerrotaLocal() {
        Equipo visitante = new Equipo("Equipo B", "2", new ArrayList<>());
        ResultadoFutbol resultado = new ResultadoFutbol(equipo, visitante, 0, 2);

        estadisticas.registrarResultado(resultado, equipo, true);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(0, estadisticas.getPuntos());
        assertEquals(0, estadisticas.getGolesFavor());
        assertEquals(2, estadisticas.getGolesContra());
        assertEquals(-2, estadisticas.getDiferenciaGoles());
    }

    @Test
    void testToTablaString() {
        Equipo visitante = new Equipo("Equipo B", "2", new ArrayList<>());
        ResultadoFutbol resultado = new ResultadoFutbol(equipo, visitante, 1, 0);

        estadisticas.registrarResultado(resultado, equipo, true);

        String tabla = estadisticas.toTablaString();
        assertTrue(tabla.contains("Equipo A"));
        assertTrue(tabla.contains("1")); // PJ o Goles
        assertTrue(tabla.contains("3")); // PTS
    }

    @Test
    void testRegistrarResultadoNoValidoLanzaExcepcion() {
        Equipo visitante = new Equipo("Equipo B", "2", new ArrayList<>());
        ResultadoFutbol resultado = new ResultadoFutbol(equipo, visitante, -1, 2); // invalido porque <0

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                estadisticas.registrarResultado(resultado, equipo, true));
        assertEquals("Resultado no válido", ex.getMessage());
    }

    @Test
    void testAgregarGolesManualmente() {
        estadisticas.agregarGoles(4, 2);

        assertEquals(4, estadisticas.getGolesFavor());
        assertEquals(2, estadisticas.getGolesContra());
        assertEquals(2, estadisticas.getDiferenciaGoles());
    }
}
