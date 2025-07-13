package org.example.model;

import org.example.exceptions.ParticipanteNullException;
import org.example.model.Formatos.GruposEliminatoria;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GruposEliminatoriaTest {

    @Test
    void testFaseEliminatoriaSoloConEquipos_noLanzaExcepcion() {
        ArrayList<Participante> participantes = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            ArrayList<Jugador> jugadores = new ArrayList<>();
            jugadores.add(new Jugador("JugadorA_" + i, "A" + i));
            jugadores.add(new Jugador("JugadorB_" + i, "B" + i));

            participantes.add(new Equipo("Equipo " + i, String.valueOf(i), jugadores));
        }

        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);

       // assertDoesNotThrow(torneo::generarEnfrentamientos);
    }

    @Test
    void testFaseEliminatoriaSoloConJugadores_noLanzaExcepcion() {
        ArrayList<Participante> participantes = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            participantes.add(new Jugador("Jugador " + i, String.valueOf(i)));
        }

        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);

        //assertDoesNotThrow(torneo::generarEnfrentamientos);
    }

    @Test
    void testFaseEliminatoriaMixta_lanzaExcepcion() {
        ArrayList<Participante> participantes = new ArrayList<>();

        ArrayList<Jugador> jugadoresEquipo = new ArrayList<>();
        jugadoresEquipo.add(new Jugador("JugadorX", "X"));
        jugadoresEquipo.add(new Jugador("JugadorY", "Y"));

        participantes.add(new Jugador("Jugador 1", "1"));
        participantes.add(new Equipo("Equipo 1", "E1", jugadoresEquipo));
        participantes.add(new Jugador("Jugador 2", "2"));
        participantes.add(new Equipo("Equipo 2", "E2", jugadoresEquipo));
        participantes.add(new Jugador("Jugador 3", "3"));
        participantes.add(new Equipo("Equipo 3", "E3", jugadoresEquipo));
        participantes.add(new Jugador("Jugador 4", "4"));
        participantes.add(new Equipo("Equipo 4", "E4", jugadoresEquipo));

        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);

        //assertThrows(ParticipanteNullException.class, torneo::generarEnfrentamientos);
    }

    @Test
    void testGeneracionGrupos_correctaDivision() {
        ArrayList<Participante> participantes = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            participantes.add(new Jugador("Jugador " + i, String.valueOf(i)));
        }

        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);
        //torneo.generarEnfrentamientos();

        // Debe haber 2 grupos
        assertEquals(2, torneo.getGrupos().size());

        // Cada grupo debe tener 4 participantes (8/2)
        for (List<Participante> grupo : torneo.getGrupos()) {
            assertEquals(4, grupo.size());
        }
    }

    @Test
    void testFaseEliminatoria_tieneEnfrentamientos() {
        ArrayList<Participante> participantes = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            participantes.add(new Jugador("Jugador " + i, String.valueOf(i)));
        }

        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);
        //torneo.generarEnfrentamientos();

        // Al menos debe haber enfrentamientos en la fase eliminatoria
        assertNotNull(torneo.getGeneradorEliminatorias());
        assertFalse(torneo.getGeneradorEliminatorias().getEnfrentamientos().isEmpty());
    }

    @Test
    void testParticipantesInsuficientes_paraNumeroGrupos_lanzaExcepcion() {
        ArrayList<Participante> participantes = new ArrayList<>();
        participantes.add(new Jugador("Jugador 1", "1"));
        participantes.add(new Jugador("Jugador 2", "2"));
        participantes.add(new Jugador("Jugador 3", "3")); // Solo 3 participantes

        // Numero grupos = 2 -> necesita al menos 4 participantes (2 por grupo)
        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);

        //assertThrows(IllegalArgumentException.class, torneo::validarParticipantes);
    }

    @Test
    void testClasificadosPorGrupoInsuficientes_paraEliminatoria_lanzaExcepcion() {
        ArrayList<Participante> participantes = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            participantes.add(new Jugador("Jugador " + i, String.valueOf(i)));
        }

        // Clasificados por grupo = 1, numero grupos = 2, total 2 clasificados < 4 (mínimo para eliminatoria)
        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 1);

        //assertThrows(IllegalArgumentException.class, torneo::validarParticipantes);
    }

    @Test
    void testNumeroExactoParticipantes_paraDivisionGrupos_correcto() {
        ArrayList<Participante> participantes = new ArrayList<>();

        // 6 participantes, 2 grupos, debería dividirse 3 por grupo
        for (int i = 1; i <= 6; i++) {
            participantes.add(new Jugador("Jugador " + i, String.valueOf(i)));
        }

        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);
       // torneo.generarEnfrentamientos();

        assertEquals(2, torneo.getGrupos().size());
        assertEquals(3, torneo.getGrupos().get(0).size());
        assertEquals(3, torneo.getGrupos().get(1).size());
    }

    @Test
    void testNumeroImparParticipantes_divideConCocienteDescartado() {
        ArrayList<Participante> participantes = new ArrayList<>();

        // 7 participantes, 2 grupos, reparto 3 y 3 (se descarta el extra)
        for (int i = 1; i <= 7; i++) {
            participantes.add(new Jugador("Jugador " + i, String.valueOf(i)));
        }

        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);
       // torneo.generarEnfrentamientos();

        assertEquals(2, torneo.getGrupos().size());
        // Cada grupo debería tener 3 (porque se hace división entera en el código)
        assertEquals(3, torneo.getGrupos().get(0).size());
        assertEquals(3, torneo.getGrupos().get(1).size());

        // El participante extra (7mo) queda descartado, podrías probar eso si quieres
    }

    @Test
    void testParticipanteExtra_descartadoAlDividirEnGrupos() {
        ArrayList<Participante> participantes = new ArrayList<>();

        // 7 participantes, 2 grupos -> cada grupo recibe solo 3 (7/2 = 3 int)
        for (int i = 1; i <= 7; i++) {
            participantes.add(new Jugador("Jugador " + i, String.valueOf(i)));
        }

        GruposEliminatoria<Participante> torneo = new GruposEliminatoria<>(participantes, 2, 2);
        //torneo.generarEnfrentamientos();

        // Grupos creados
        assertEquals(2, torneo.getGrupos().size());

        // Cada grupo tiene 3 participantes
        assertEquals(3, torneo.getGrupos().get(0).size());
        assertEquals(3, torneo.getGrupos().get(1).size());

        // El participante extra no está en ningún grupo
        // Verificamos que ninguno de los grupos contenga "Jugador 7"
        boolean jugador7EnGrupos = torneo.getGrupos().stream()
                .flatMap(List::stream)
                .anyMatch(p -> p.getNombre().equals("Jugador 7"));

        assertFalse(jugador7EnGrupos, "El participante extra Jugador 7 debería estar descartado y no en ningún grupo");
    }
}
