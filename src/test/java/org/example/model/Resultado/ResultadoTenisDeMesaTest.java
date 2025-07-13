package org.example.model.Resultado;

import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResultadoTenisDeMesaTest {

    Participante p1 = new Jugador("Jugador 1", "1");
    Participante p2 = new Jugador("Jugador 2", "2");

    @Test
    void testGanadorPorMayoriaDeSets() {
        ResultadoTenisDeMesa resultado = new ResultadoTenisDeMesa(p1, p2, 5, p1); // Mejor de 5 sets
        resultado.agregarSet(0, 11, 6);
        resultado.agregarSet(1, 9, 11);
        resultado.agregarSet(2, 11, 7);
        resultado.agregarSet(3, 11, 9);

        assertTrue(resultado.esValido());
        assertEquals(p1, resultado.getGanador());
        assertEquals(3, resultado.getSetsJugador1());
        assertEquals(1, resultado.getSetsJugador2());
    }

    @Test
    void testSetConVentaja() {
        ResultadoTenisDeMesa resultado = new ResultadoTenisDeMesa(p1, p2, 3, p1);
        resultado.agregarSet(0, 12, 10); // Ventaja
        resultado.agregarSet(1, 10, 12); // Ventaja del otro
        resultado.agregarSet(2, 11, 9);

        assertTrue(resultado.esValido());
        assertEquals(p1, resultado.getGanador());
        assertEquals(2, resultado.getSetsJugador1());
        assertEquals(1, resultado.getSetsJugador2());
    }

    @Test
    void testNoValidoPorPocosSetsGanados() {
        ResultadoTenisDeMesa resultado = new ResultadoTenisDeMesa(p1, p2, 3, null);
        resultado.agregarSet(0, 11, 9);

        assertFalse(resultado.esValido());
        assertNull(resultado.getGanador());
    }

    @Test
    void testSetNoValidoPorPuntos() {
        ResultadoTenisDeMesa resultado = new ResultadoTenisDeMesa(p1, p2, 3, null);
        resultado.agregarSet(0, 10, 10); // No válido (empate sin ventaja)

        assertFalse(resultado.esValido());
        assertNull(resultado.getGanador());
    }
}
