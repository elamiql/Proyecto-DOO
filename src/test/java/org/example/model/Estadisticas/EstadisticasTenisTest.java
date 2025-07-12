package org.example.model.Estadisticas;

import org.example.model.Participante.*;
import org.example.model.Resultado.ResultadoTenis;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class EstadisticasTenisTest {

    private Jugador j1;
    private Jugador j2;

    private EstadisticasTenis estadisticas;
    private EstadisticasTenis estadisticas1;

    @BeforeEach
    void setUp() {
        j1 = new Jugador("J1", "1");
        j2 = new Jugador("J2", "2");
        estadisticas = new EstadisticasTenis(j1);
        estadisticas1 = new EstadisticasTenis(j2);
    }

    @Test
    void testRegistrarVictoriaConTiebreak() {
        ResultadoTenis resultado = new ResultadoTenis(j1, j2, 3);

        resultado.agregarSet(0, 7, 6); // victoria en tiebreak
        resultado.agregarSet(1, 6, 4); // victoria normal

        estadisticas.registrarResultado(resultado, j1, true);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(1, estadisticas.getGanados());
        assertEquals(0, estadisticas.getPerdidos());

        assertEquals(2, estadisticas.getSetsGanados());
        assertEquals(0, estadisticas.getSetsPerdidos());

        assertEquals(13, estadisticas.getJuegosGanados());
        assertEquals(10, estadisticas.getJuegosPerdidos());

        assertEquals(1, estadisticas.getSetsGanadosEnTiebreak());
        assertEquals(0, estadisticas.getSetsPerdidosEnTiebreak());

        assertEquals(1, estadisticas.getPartidosGanadosEnTiebreak());
        assertEquals(0, estadisticas.getPartidosPerdidosEnTiebreak());
    }

    @Test
    void testRegistrarDerrotaSinTiebreak() {
        ResultadoTenis resultado = new ResultadoTenis(j1, j2, 3);

        resultado.agregarSet(0, 3, 6);
        resultado.agregarSet(1, 4, 6);

        estadisticas.registrarResultado(resultado, j1, true);

        assertEquals(1, estadisticas.getPartidosJugados());
        assertEquals(0, estadisticas.getGanados());
        assertEquals(1, estadisticas.getPerdidos());

        assertEquals(0, estadisticas.getSetsGanados());
        assertEquals(2, estadisticas.getSetsPerdidos());

        assertEquals(7, estadisticas.getJuegosGanados());
        assertEquals(12, estadisticas.getJuegosPerdidos());

        assertEquals(0, estadisticas.getSetsGanadosEnTiebreak());
        assertEquals(0, estadisticas.getSetsPerdidosEnTiebreak());

        assertEquals(0, estadisticas.getPartidosGanadosEnTiebreak());
        assertEquals(0, estadisticas.getPartidosPerdidosEnTiebreak());
    }

    @Test
    void testPorcentajesYPromedios() {
        ResultadoTenis resultado1 = new ResultadoTenis(j1, j2, 3);
        resultado1.agregarSet(0, 6, 4); // gana set normal
        resultado1.agregarSet(1, 7, 6); // gana set tiebreak

        ResultadoTenis resultado2 = new ResultadoTenis(j1, j2, 3);
        resultado2.agregarSet(0, 4, 6); // pierde set
        resultado2.agregarSet(1, 5, 7); // pierde set

        estadisticas.registrarResultado(resultado1, j1, true);
        estadisticas.registrarResultado(resultado2, j1, true);

        assertEquals(2, estadisticas.getPartidosJugados());
        assertEquals(1, estadisticas.getGanados());
        assertEquals(1, estadisticas.getPerdidos());

        assertEquals(50.0, estadisticas.getPorcentajeVictorias(), 0.1);
        assertEquals(2, estadisticas.getSetsGanados());
        assertEquals(2, estadisticas.getSetsPerdidos());
        assertEquals(50.0, estadisticas.getPorcentajeSetsGanados(), 0.1);

        int totalJuegos = estadisticas.getJuegosGanados() + estadisticas.getJuegosPerdidos();
        assertTrue(totalJuegos > 0);
        assertEquals(50.0, estadisticas.getPorcentajeJuegosGanados(), 20.0); // tolerancia amplia

        assertTrue(estadisticas.getPromedioJuegosPorSet() >= 5.0);
    }

    @Test
    void testToTablaStringYEstadisticasCompletas() {
        ResultadoTenis resultado = new ResultadoTenis(j1, j2, 3);
        resultado.agregarSet(0, 6, 4);
        resultado.agregarSet(1, 7, 6);

        estadisticas.registrarResultado(resultado, j1, true);

        String tabla = estadisticas.toTablaString();
        assertTrue(tabla.contains("J1"));
        assertTrue(tabla.contains("PJ"));
        assertTrue(tabla.contains("TB") || tabla.contains("Tiebreak"));

        String completas = estadisticas.getEstadisticasCompletas();
        assertTrue(completas.contains("ESTADÍSTICAS"));
        assertTrue(completas.contains("Partidos"));
        assertTrue(completas.contains("Porcentaje de victorias"));
    }
}
