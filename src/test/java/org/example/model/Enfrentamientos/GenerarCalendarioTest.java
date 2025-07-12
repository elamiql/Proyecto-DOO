package org.example.model.Enfrentamientos;

import org.example.model.Estadisticas.EstadisticasFutbol;
import org.example.model.Estadisticas.EstadisticasTenis;
import org.example.model.Formatos.Liga;
import org.example.model.Participante.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GenerarCalendarioTest {

    private Liga<Jugador, ?, ?> liga;
    private Liga<Equipo, ?, ?> liga2;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Equipo> equipos;
    private EstadisticasFutbol estadisticasFutbol;
    private EstadisticasTenis estadisticasTenis;

    @BeforeEach
    void setUp() {
        jugadores = new ArrayList<>();
        equipos = new ArrayList<>();

        estadisticasFutbol = new EstadisticasFutbol();
        estadisticasTenis = new EstadisticasTenis();

        for (int i = 1; i <= 4; i++) {
            jugadores.add(new Jugador("Jugador" + i, String.valueOf(i)));
        }

        for (int i = 1; i <= 4; i++){
            equipos.add(new Equipo("Equipo " + i, String.valueOf(i), jugadores));
        }

        liga = new Liga<>(jugadores, true, estadisticasTenis);
        liga2 = new Liga<>(equipos, true, estadisticasFutbol);
    }


    @Test
    void testImprimirCalendarioVacio() {
        // Capturar salida del sistema para verificar mensaje
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        liga.imprimirCalendario();

        String output = outContent.toString();
        assertTrue(output.contains("No se han generado aun"));

        // Restaurar salida del sistema
        System.setOut(System.out);
    }

    @Test
    void testImprimirCalendarioConEnfrentamientos() {
        liga.generarCalendario();

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        liga.imprimirCalendario();

        String output = outContent.toString();
        assertTrue(output.contains("Calendario Liga"));
        assertTrue(output.contains("Partido 1:"));

        System.setOut(System.out);
    }

    @Test
    void testLimpiarCalendario() {
        liga.generarCalendario();
        assertFalse(liga.getEnfrentamientos().isEmpty());

        liga.limpiarCalendario();
        assertTrue(liga.getEnfrentamientos().isEmpty());
    }

    @Test
    void testFiltrarPorParticipanteInexistente() {
        liga.generarCalendario();
        ArrayList<Enfrentamiento> resultado = liga.filtrarPorParticipante("NoExiste");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void testFiltrarPorParticipanteCaseInsensitive() {
        liga.generarCalendario();
        ArrayList<Enfrentamiento> resultado1 = liga.filtrarPorParticipante("jugador1");
        ArrayList<Enfrentamiento> resultado2 = liga.filtrarPorParticipante("JUGADOR1");
        assertEquals(resultado1.size(), resultado2.size());
    }
}