package org.example.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LigaTest {
    Jugador p1;
    Jugador p2;
    Jugador p3;

    @BeforeEach
    void setUp() {
        p1 = new Jugador("participante1","1");
        p2 = new Jugador("participante2","2");
        p3 = new Jugador("participante3","3");
    }
    @Test
    void ligaDobleVuelta_dosParticipantes_generaDosEnfrentamientos() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(p1);
        jugadores.add(p2);

        Liga<Jugador> liga = new Liga<>(jugadores, true);
        liga.generarEnfrentamientos();

        List<Enfrentamiento> enf = liga.getEnfrentamientos();
        assertEquals(2, enf.size());

        // Verificamos ida y vuelta
        assertTrue(contieneEnfrentamiento(enf, "participante1", "participante2"));
        assertTrue(contieneEnfrentamiento(enf, "participante2", "participante1"));
    }

    @Test
    void ligaVueltaSimple_tresParticipantes_generaTresEnfrentamientos() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(p1);
        jugadores.add(p2);
        jugadores.add(p3);

        Liga<Jugador> liga = new Liga<>(jugadores, false);
        liga.generarEnfrentamientos();

        List<Enfrentamiento> enf = liga.getEnfrentamientos();
        assertEquals(3, enf.size());

        assertTrue(contieneEnfrentamiento(enf, "participante1", "participante2"));
        assertTrue(contieneEnfrentamiento(enf, "participante1", "participante3"));
        assertTrue(contieneEnfrentamiento(enf, "participante2", "participante3"));
    }

    @Test
    void ligaDobleVuelta_tresParticipantes_generaSeisEnfrentamientos() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(p1);
        jugadores.add(p2);
        jugadores.add(p3);

        Liga<Jugador> liga = new Liga<>(jugadores, true);
        liga.generarEnfrentamientos();

        List<Enfrentamiento> enf = liga.getEnfrentamientos();
        assertEquals(6, enf.size());

        assertTrue(contieneEnfrentamiento(enf, "participante1", "participante2"));
        assertTrue(contieneEnfrentamiento(enf, "participante2", "participante1"));
        assertTrue(contieneEnfrentamiento(enf, "participante1", "participante3"));
        assertTrue(contieneEnfrentamiento(enf, "participante3", "participante1"));
        assertTrue(contieneEnfrentamiento(enf, "participante2", "participante3"));
        assertTrue(contieneEnfrentamiento(enf, "participante3", "participante2"));
    }

    private boolean contieneEnfrentamiento(List<Enfrentamiento> lista, String nombre1, String nombre2) {
        return lista.stream().anyMatch(e ->
                e.getParticipante1().getNombre().equals(nombre1) &&
                        e.getParticipante2().getNombre().equals(nombre2)
        );
    }
    @AfterEach
    void tearDown() {
    }
}