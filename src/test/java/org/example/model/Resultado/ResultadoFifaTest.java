package org.example.model.Resultado;

import org.example.model.Participante.Equipo;
import org.example.model.Participante.Participante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultadoFifaTest {
    Participante local;
    Participante visitante;

    @BeforeEach
    void setUp() {
        local = new Equipo("Local", "L", null);
        visitante = new Equipo("Visitante", "V", null);
    }

    @Test
    void testGanadorLocal() {
        ResultadoFifa resultado = new ResultadoFifa(local, visitante, 3, 1);
        assertTrue(resultado.esValido());
        assertEquals(local, resultado.getGanador());
        assertEquals(local.getNombre() + " " + resultado.getGolesLocal() + " - " + resultado.getGolesVisitante() + " " + visitante.getNombre()
                , resultado.getResumen());
    }

    @Test
    void testGanadorVisitante() {
        ResultadoFifa resultado = new ResultadoFifa(local, visitante, 0, 2);
        assertTrue(resultado.esValido());
        assertEquals(visitante, resultado.getGanador());
    }

    @Test
    void testEmpate() {
        ResultadoFifa resultado = new ResultadoFifa(local, visitante, 2, 2);
        assertTrue(resultado.esValido());
        assertNull(resultado.getGanador());
    }

    @Test
    void testGolesNegativosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new ResultadoFifa(local, visitante, -1, 2));
    }
}
