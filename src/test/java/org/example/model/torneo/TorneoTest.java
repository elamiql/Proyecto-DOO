package org.example.model.torneo;

import org.example.enums.Deporte;
import org.example.interfaces.Disciplina;
import org.example.enums.Formato;
import org.example.exceptions.ParticipanteDuplicadoException;
import org.example.exceptions.ParticipantesInsuficientesException;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Participante.Jugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TorneoTest {

    private Torneo<Jugador> torneo;
    private Jugador jugador1;
    private Jugador jugador2;

    // Subclase dummy para testear Torneo
    static class TorneoTestImpl extends Torneo<Jugador> {
        public TorneoTestImpl(String nombre, Disciplina disciplina, String fecha, Formato formato, String contraseña) {
            super(nombre, disciplina, fecha, formato, contraseña);
        }

        @Override
        public void generarCalendario() {
            // Simula la generación del calendario
            enfrentamientos.add(new Enfrentamiento(participantes.get(0), participantes.get(1)));
        }
    }

    @BeforeEach
    void setUp() {
        torneo = new TorneoTestImpl("Torneo Test", Deporte.TENIS, "01-01-2030 12:00", Formato.ELIMINATORIA, "1234");
        jugador1 = new Jugador("Jugador 1", "1");
        jugador2 = new Jugador("Jugador 2", "2");
    }

    @Test
    void testAgregarParticipantes() {
        torneo.addParticipante(jugador1);
        torneo.addParticipante(jugador2);

        assertEquals(2, torneo.getParticipantes().size());
        assertEquals(2, torneo.getParticipantes().size());
    }

    @Test
    void testAgregarParticipanteDuplicadoLanzaExcepcion() {
        torneo.addParticipante(jugador1);

        assertThrows(ParticipanteDuplicadoException.class, () -> {
            torneo.addParticipante(jugador1);
        });
    }

    @Test
    void testActualizarEstadoConSuficientesParticipantesActivaTorneoYGeneraCalendario() {
        torneo.addParticipante(jugador1);
        torneo.addParticipante(jugador2);

        torneo.setFecha("01-01-2020 12:00"); // fecha pasada para activar
        torneo.actualizarEstado();

        assertTrue(torneo.isActivo());
        assertFalse(torneo.getEnfrentamientos().isEmpty());
    }

    @Test
    void testActualizarEstadoConParticipantesInsuficientesLanzaExcepcion() {
        torneo.addParticipante(jugador1);

        torneo.setFecha("01-01-2020 12:00");

        assertThrows(ParticipantesInsuficientesException.class, () -> {
            torneo.actualizarEstado();
        });
    }

    @Test
    void testToStringIncluyeDatosImportantes() {
        String descripcion = torneo.toString();

        assertTrue(descripcion.contains("Torneo Test"));
        assertTrue(descripcion.contains("TENIS"));
        assertTrue(descripcion.contains("ELIMINATORIA"));
        assertTrue(descripcion.contains("2030"));
    }

    @Test
    void testGettersBasicos() {
        assertEquals("Torneo Test", torneo.getNombre());
        assertEquals("1234", torneo.getContraseña());
        assertEquals(Deporte.TENIS, torneo.getDisciplina());
        assertEquals(Formato.ELIMINATORIA, torneo.getFormato());
    }
}
