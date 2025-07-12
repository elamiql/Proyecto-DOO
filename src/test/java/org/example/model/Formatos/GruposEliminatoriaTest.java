package org.example.model.Formatos;

import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Estadisticas.EstadisticasFutbol;
import org.example.model.Formatos.GruposEliminatoria;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Resultado.ResultadoFutbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GruposEliminatoriaTest {

    GruposEliminatoria<Equipo, ResultadoFutbol, EstadisticasFutbol> gruposEliminatoria;
    ArrayList<Equipo> equipos;
    ArrayList<Jugador> jugadores;
    EstadisticasFutbol estadisticasFutbol;


    @BeforeEach
    void setUp() {
        jugadores = new ArrayList<>();
        equipos = new ArrayList<>();
        estadisticasFutbol = new EstadisticasFutbol();
        for (int i = 1; i <= 8; i++) {
            equipos.add(new Equipo("Equipo " + i, String.valueOf(i), jugadores));
            for (int j = 1; j <= 2; j++){
                jugadores.add(new Jugador("J" + j, String.valueOf(j)));
            }
        }
    }

    @Nested
    @DisplayName("Tests de Constructor y Validación")
    class ConstructorYValidacionTests {

        @Test
        void testConstructor() {
            gruposEliminatoria = new GruposEliminatoria<>(equipos, 2, 2, estadisticasFutbol);
            assertEquals(2, gruposEliminatoria.getNumeroGrupos());
            assertNotNull(gruposEliminatoria.getGrupos());
        }
    }

    @Nested
    @DisplayName("Tests de División en Grupos")
    class DivisionGruposTests {

        @BeforeEach
        void setUp() {
            gruposEliminatoria = new GruposEliminatoria<>(equipos, 2, 2, estadisticasFutbol);
            gruposEliminatoria.generarCalendario();
        }

        @Test
        void testDivisionEnGrupos() {
            List<List<Equipo>> grupos = gruposEliminatoria.getGrupos();
            assertEquals(2, grupos.size());
        }

        @Test
        void testTodosParticipantesAsignados() {
            List<List<Equipo>> grupos = gruposEliminatoria.getGrupos();
            Set<Equipo> todos = new HashSet<>();
            for (List<Equipo> grupo : grupos) todos.addAll(grupo);
            assertEquals(equipos.size(), todos.size());
        }
    }

    @Nested
    @DisplayName("Tests de Generación de Enfrentamientos")
    class GeneracionEnfrentamientosTests {

        @BeforeEach
        void setUp() {
            gruposEliminatoria = new GruposEliminatoria<>(equipos, 2, 2, estadisticasFutbol);
            gruposEliminatoria.generarCalendario();
        }

        @Test
        void testGeneraEliminatoria() {
            assertNotNull(gruposEliminatoria.getGeneradorEliminatorias());
        }

        @Test
        void testIncluyeGruposYEliminatoria() {
            int total = gruposEliminatoria.getEnfrentamientos().size();
            assertTrue(total > 0);
        }
    }

    @Nested
    @DisplayName("Tests de Casos Especiales")
    class CasosEspecialesTests {

        @Test
        void testReGenerarCalendario() {
            gruposEliminatoria = new GruposEliminatoria<>(equipos, 2, 2, estadisticasFutbol);
            gruposEliminatoria.generarCalendario();
            int primero = gruposEliminatoria.getEnfrentamientos().size();
            gruposEliminatoria.generarCalendario();
            int segundo = gruposEliminatoria.getEnfrentamientos().size();
            assertEquals(primero, segundo);
        }
    }
}
