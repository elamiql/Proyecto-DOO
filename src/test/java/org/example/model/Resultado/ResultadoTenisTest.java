package org.example.model.Resultado;

import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultadoTenisTest {

    Participante p1 = new Jugador("Jugador 1", "1");
    Participante p2 = new Jugador("Jugador 2", "2");

    @Test
    void testGanadorPorDosSets() {
        ResultadoTenis resultado = new ResultadoTenis(p1, p2, 3, p1); // Mejor de 3
        resultado.agregarSet(0, 6, 4);
        resultado.agregarSet(1, 6, 4);

        assertTrue(resultado.esValido());
        assertEquals(p1, resultado.getGanador());
        assertEquals(2, resultado.getSetsJugador1());
        assertEquals(0, resultado.getSetsJugador2());
    }

    @Test
    void testSetConTiebreak() {
        ResultadoTenis resultado = new ResultadoTenis(p1, p2, 3, p1);
        resultado.agregarSet(0, 7, 6); // Tiebreak
        resultado.agregarSet(1, 3, 6);
        resultado.agregarSet(2, 6, 4);

        assertTrue(resultado.esValido());
        assertEquals(p1, resultado.getGanador());
        assertEquals(2, resultado.getSetsJugador1());
        assertEquals(1, resultado.getSetsJugador2());
    }

    @Test
    void testNoValidoSinSetsGanadosSuficientes() {
        ResultadoTenis resultado = new ResultadoTenis(p1, p2, 3, null);
        resultado.agregarSet(0, 6, 4);

        assertFalse(resultado.esValido());
        assertNull(resultado.getGanador());
    }

    @Test
    void testEmpateInvalido() {
        ResultadoTenis resultado = new ResultadoTenis(p1, p2, 2, null);
        resultado.agregarSet(0, 6, 4);
        resultado.agregarSet(1, 3, 6);

        assertFalse(resultado.esValido()); // Nadie ganó 2 sets
        assertNull(resultado.getGanador());
    }
}

