package org.example.model.Participante;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.exceptions.ParticipantesInsuficientesException;
import org.example.model.torneo.TorneoIndividual;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JugadorTest {
    @Test
    void testConstructorYToString() {
        Jugador jugador = new Jugador("Juan", "10");
        assertEquals("Juan", jugador.getNombre());
        assertEquals("10", jugador.getNumero());
        assertEquals("Juan 10", jugador.toString());
    }

    @Test
    void testInscribirseAgregaJugadorATorneoIndividual() {
        Jugador jugador1 = new Jugador("Juan", "10");
        Jugador jugador2 = new Jugador("Pedro", "11");

        TorneoIndividual torneo = new TorneoIndividual(
                "Torneo Test",
                Deporte.FUTBOL,
                "04-07-2025 20:00",
                Formato.LIGA,
                "cualquier cadena"
        );

        jugador1.inscribirse(torneo);
        jugador2.inscribirse(torneo);

        assertTrue(torneo.getParticipantes().contains(jugador1));
        assertTrue(torneo.getParticipantes().contains(jugador2));
        assertEquals(2, torneo.getParticipantes().size());
    }

    @Test
    void testGenerarCalendarioConInscritos() {
        Jugador jugador1 = new Jugador("Juan", "10");
        Jugador jugador2 = new Jugador("Pedro", "11");

        TorneoIndividual torneo = new TorneoIndividual(
                "Torneo Test",
                Deporte.FUTBOL,
                "04-07-2025 20:00",
                Formato.LIGA,
                "cualquier cadena"
        );

        jugador1.inscribirse(torneo);
        jugador2.inscribirse(torneo);

        assertDoesNotThrow(torneo::generarCalendario);
        assertFalse(torneo.getEnfrentamientos().isEmpty());
    }

    @Test
    void testGenerarCalendarioConParticipantesInsuficientes_lanzaExcepcion() {
        Jugador jugador1 = new Jugador("Juan", "10");

        TorneoIndividual torneo = new TorneoIndividual(
                "Torneo Test",
                Deporte.FUTBOL,
                "04-07-2025 20:00",
                Formato.LIGA,
                "cualquier cadena"
        );

        jugador1.inscribirse(torneo);

        assertThrows(ParticipantesInsuficientesException.class, torneo::generarCalendario);
    }
}
