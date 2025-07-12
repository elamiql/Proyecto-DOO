package org.example.model.Resultado;

import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultadoFutbolTest {
    Participante p1;
    Participante p2;

    @BeforeEach
    void setUp() {
        p1 = new Jugador("participante1","1");
        p2 = new Jugador("participante2","2");
    }

    @Test
    void esValido() {
        ResultadoFutbol resultado = new ResultadoFutbol(p1, p2, 2, 1);
        assertTrue(resultado.esValido());
    }

    @Test
    void GolesNegativos() {
        ResultadoFutbol resultado1 = new ResultadoFutbol(p1, p2, -1, 2);
        ResultadoFutbol resultado2 = new ResultadoFutbol(p1, p2, 2, -3);

        assertFalse(resultado1.esValido());
        assertFalse(resultado2.esValido());
    }

    @Test
    void localGana() {
        ResultadoFutbol resultado = new ResultadoFutbol(p1, p2, 3, 1);
        assertEquals(p1, resultado.getGanador());
    }

    @Test
    void visitanteGana() {
        ResultadoFutbol resultado = new ResultadoFutbol(p1, p2, 0, 2);
        assertEquals(p2, resultado.getGanador());
    }

    @Test
    void getResumen() {
        ResultadoFutbol resultado = new ResultadoFutbol(p1, p2, 2, 2);
        String resumen = resultado.getResumen();
        assertEquals("participante1 2 - 2 participante2", resumen);
    }

    @AfterEach
    void tearDown() {
    }
}