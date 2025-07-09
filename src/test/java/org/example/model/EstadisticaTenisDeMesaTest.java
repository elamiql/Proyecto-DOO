package org.example.model;

import org.example.model.Estadisticas.EstadisticaTenisDeMesa;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoTenisDeMesa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstadisticaTenisDeMesaTest {

    private Participante jugador1;
    private Participante jugador2;
    private EstadisticaTenisDeMesa estadistica;
    private ResultadoTenisDeMesa resultado;

    @BeforeEach
    void setUp() {
        jugador1 = new Equipo("Jugador 1", "01", null);
        jugador2 = new Equipo("Jugador 2", "02", null);
        estadistica = new EstadisticaTenisDeMesa(jugador1);
        resultado = new ResultadoTenisDeMesa(jugador1, jugador2, 5);
    }

    @Test
    void testRegistrarVictoria() {
        resultado.agregarSet(0, 11, 8);
        resultado.agregarSet(1, 11, 7);
        resultado.agregarSet(2, 11, 9); // jugador1 gana 3-0

        estadistica.registrarResultado(resultado, jugador1, true);

        assertEquals(1, estadistica.getGanados());
        assertEquals(0, estadistica.getPerdidos());
        assertEquals(1, estadistica.getPartidosJugados());
        assertEquals(3, estadistica.getPuntos());
    }

    @Test
    void testRegistrarDerrota() {
        resultado.agregarSet(0, 8, 11);
        resultado.agregarSet(1, 9, 11);
        resultado.agregarSet(2, 7, 11); // jugador2 gana 3-0

        estadistica.registrarResultado(resultado, jugador1, true);

        assertEquals(0, estadistica.getGanados());
        assertEquals(1, estadistica.getPerdidos());
        assertEquals(1, estadistica.getPartidosJugados());
        assertEquals(0, estadistica.getPuntos());
    }

    @Test
    void testNoRegistraSiResultadoNoValido() {
        resultado.agregarSet(0, 11, 9); // solo un set
        // No suficiente para declarar ganador

        estadistica.registrarResultado(resultado, jugador1, true);

        assertEquals(0, estadistica.getGanados());
        assertEquals(0, estadistica.getPerdidos());
        assertEquals(0, estadistica.getPartidosJugados());
        assertEquals(0, estadistica.getPuntos());
    }

    @Test
    void testToTablaStringMuestraDatosCorrectos() {
        resultado.agregarSet(0, 11, 8);
        resultado.agregarSet(1, 11, 9);
        resultado.agregarSet(2, 11, 7); // jugador1 gana

        estadistica.registrarResultado(resultado, jugador1, true);

        String tabla = estadistica.toTablaString();

        assertTrue(tabla.contains("Jugador 1"));
        assertTrue(tabla.contains("PJ: 1"));
        assertTrue(tabla.contains("G: 1"));
        assertTrue(tabla.contains("P: 0"));
        assertTrue(tabla.contains("Ptos: 3"));
    }
}
