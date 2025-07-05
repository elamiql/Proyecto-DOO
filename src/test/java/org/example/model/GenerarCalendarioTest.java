package org.example.model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GenerarCalendarioTest {

    private Liga<Jugador> liga;
    private ArrayList<Jugador> jugadores;

    @BeforeEach
    void setUp() {
        jugadores = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            jugadores.add(new Jugador("Jugador" + i, String.valueOf(i)));
        }
        liga = new Liga<>(jugadores, true);
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