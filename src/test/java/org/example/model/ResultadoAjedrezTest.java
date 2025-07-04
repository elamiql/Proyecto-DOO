package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultadoAjedrezTest {
    Participante p1;
    Participante p2;

    @BeforeEach
    void setUp() {
        p1 = new Jugador("Jugador 1", "1");
        p2 = new Jugador("Jugador 2", "2");
    }

    @Test
    void testResultadoValidoVictoriaJugador1() {
        ResultadoAjedrez resultado = new ResultadoAjedrez(p1, p2, 1.0, 0.0);
        assertTrue(resultado.esValido());
        assertEquals(p1, resultado.getGanador());
        assertEquals("Jugador 1 1.0 - 0.0 Jugador 2", resultado.getResumen());
    }

    @Test
    void testResultadoValidoEmpate() {
        ResultadoAjedrez resultado = new ResultadoAjedrez(p1, p2, 0.5, 0.5);
        assertTrue(resultado.esValido());
        assertNull(resultado.getGanador());
    }

    @Test
    void testResultadoValidoVictoriaJugador2() {
        ResultadoAjedrez resultado = new ResultadoAjedrez(p1, p2, 0.0, 1.0);
        assertTrue(resultado.esValido());
        assertEquals(p2, resultado.getGanador());
    }

    @Test
    void testResultadoInvalido() {
        // puntos no suman 1 o no están en {0,0.5,1}
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            new ResultadoAjedrez(p1, p2, 0.7, 0.3);
        });
        assertTrue(thrown.getMessage().contains("Resultado inválido"));
    }
}
