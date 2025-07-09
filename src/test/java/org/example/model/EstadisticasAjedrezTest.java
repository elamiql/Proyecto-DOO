package org.example.model;

import org.example.model.Estadisticas.EstadisticasAjedrez;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoAjedrez;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstadisticasAjedrezTest {
    Participante p1;
    EstadisticasAjedrez estadisticas;

    @BeforeEach
    void setUp() {
        p1 = new Jugador("Jugador 1", "1");
        estadisticas = new EstadisticasAjedrez(p1);
    }

    @Test
    void testRegistrarVictoria() {
        ResultadoAjedrez resultado = new ResultadoAjedrez(p1, new Jugador("Jugador 2", "2"), 1.0, 0.0);
        estadisticas.registrarResultado(resultado, p1, true);
        assertEquals(1, estadisticas.getGanados());
        assertEquals(0, estadisticas.getEmpatados());
        assertEquals(0, estadisticas.getPerdidos());
        assertEquals(1, estadisticas.getPuntos());
    }

    @Test
    void testRegistrarEmpate() {
        ResultadoAjedrez resultado = new ResultadoAjedrez(p1, new Jugador("Jugador 2", "2"), 0.5, 0.5);
        estadisticas.registrarResultado(resultado, p1, true);
        assertEquals(0, estadisticas.getGanados());
        assertEquals(1, estadisticas.getEmpatados());
        assertEquals(0, estadisticas.getPerdidos());
        assertEquals(0, estadisticas.getPuntos());
    }

    @Test
    void testRegistrarDerrota() {
        ResultadoAjedrez resultado = new ResultadoAjedrez(new Jugador("Jugador 2", "2"), p1, 1.0, 0.0);
        estadisticas.registrarResultado(resultado, p1, false);
        assertEquals(0, estadisticas.getGanados());
        assertEquals(0, estadisticas.getEmpatados());
        assertEquals(1, estadisticas.getPerdidos());
        assertEquals(0, estadisticas.getPuntos());
    }

    @Test
    void testToTablaString() {
        ResultadoAjedrez resultado1 = new ResultadoAjedrez(p1, new Jugador("Jugador 2", "2"), 1.0, 0.0);
        ResultadoAjedrez resultado2 = new ResultadoAjedrez(p1, new Jugador("Jugador 3", "3"), 0.5, 0.5);
        estadisticas.registrarResultado(resultado1, p1, true);
        estadisticas.registrarResultado(resultado2, p1, true);

        String tabla = estadisticas.toTablaString();
        assertTrue(tabla.contains("Jugador 1"));
        assertTrue(tabla.contains("PJ: 2"));
        assertTrue(tabla.contains("G: 1"));
        assertTrue(tabla.contains("E: 1"));
        assertTrue(tabla.contains("P: 0"));
        assertTrue(tabla.contains("Puntos: 1.5"));
    }
}
