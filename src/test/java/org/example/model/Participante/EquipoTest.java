package org.example.model.Participante;

import org.example.model.torneo.Torneo;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class EquipoTest {

    private Equipo equipo;
    private Jugador jugador1;
    private Jugador jugador2;

    @BeforeEach
    void setUp() {
        jugador1 = new Jugador("Juan", "10");
        jugador2 = new Jugador("Pedro", "11");
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        equipo = new Equipo("EquipoTest", "001", jugadores);
    }

    @Test
    void testAddJugador() {
        equipo.addJugador(jugador2);
        assertTrue(equipo.getJugadores().contains(jugador2), "El jugador debe ser agregado");
    }

    @Test
    void testRemoveJugador() {
        equipo.removeJugador(jugador1);
        assertFalse(equipo.getJugadores().contains(jugador1), "El jugador debe ser removido");
    }

    @Test
    void testGetJugadores() {
        ArrayList<Jugador> jugadores = equipo.getJugadores();
        assertEquals(1, jugadores.size(), "Debe tener un jugador");
        assertEquals(jugador1, jugadores.get(0), "El jugador debe ser jugador1");
    }

    @Test
    void testInscribirse() {
        // Usamos un Torneo dummy para verificar que no lanza excepción
        Torneo<Participante> torneoDummy = new Torneo<>("TorneoTest", null, "01-01-2025 10:00", null, "pass") {
            @Override
            public void addParticipante(Participante participante) {
                // No hace nada, solo aceptamos la llamada
            }

            @Override
            public void generarCalendario() {
                // No implementado para este dummy
            }
        };

        assertDoesNotThrow(() -> equipo.inscribirse(torneoDummy), "Debe inscribirse sin lanzar excepción");
    }
}
