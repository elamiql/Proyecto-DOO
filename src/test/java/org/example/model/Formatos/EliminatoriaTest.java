package org.example.model.Formatos;


import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Participante.Jugador;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class EliminatoriaTest {

    @Test
    void testEliminatoriaConNumeroImpar() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            jugadores.add(new Jugador("Jugador" + i, String.valueOf(i)));
        }

        Eliminatoria<Jugador> eliminatoria = new Eliminatoria<>(jugadores);
        eliminatoria.generarCalendario();

        // Verificar que se maneja correctamente el bye
        assertNotNull(eliminatoria.getBracket());
        assertFalse(eliminatoria.getBracket().isEmpty());
    }

    @Test
    void testImprimirBracket() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            jugadores.add(new Jugador("Jugador" + i, String.valueOf(i)));
        }

        Eliminatoria<Jugador> eliminatoria = new Eliminatoria<>(jugadores, true);
        eliminatoria.generarCalendario();

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        eliminatoria.imprimirBracket();

        String output = outContent.toString();
        assertTrue(output.contains("Bracket Eliminatorio"));
        assertTrue(output.contains("Ronda 1"));

        System.setOut(System.out);
    }

    @Test
    void testGetRondas() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            jugadores.add(new Jugador("Jugador" + i, String.valueOf(i)));
        }

        Eliminatoria<Jugador> eliminatoria = new Eliminatoria<>(jugadores, true);
        eliminatoria.generarCalendario();

        List<List<Enfrentamiento>> rondas = eliminatoria.getRondas();
        assertEquals(2, rondas.size()); // Semifinal y Final
        assertEquals(2, rondas.get(0).size()); // 2 semifinales
        assertEquals(1, rondas.get(1).size()); // 1 final
    }
}

