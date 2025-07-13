package org.example.model.Formatos;

import org.example.model.Estadisticas.EstadisticasFutbol;
import org.example.model.Estadisticas.EstadisticasTenis;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Resultado.ResultadoFutbol;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LigaTest {
    Jugador p1;
    Jugador p2;
    Jugador p3;

    Equipo e1;
    Equipo e2;
    Equipo e3;


    ArrayList<Jugador> jugadores;
    ArrayList<Equipo> equipos;

    EstadisticasFutbol plantillaEstadisticas;

    @BeforeEach
    void setUp() {
        jugadores = new ArrayList<>();
        equipos = new ArrayList<>();

        p1 = new Jugador("participante1", "1");
        p2 = new Jugador("participante2", "2");
        p3 = new Jugador("participante3", "3");

        e1 = new Equipo("participante1", "1", jugadores);
        e2 = new Equipo("participante2", "3", jugadores);
        e3 = new Equipo("participante3", "2", jugadores);

    }

    @Test
    void ligaDobleVuelta_dosParticipantes_generaDosEnfrentamientos() {

        equipos.add(e1);
        equipos.add(e2);

        // Especificar explícitamente los tipos genéricos
        Liga<?> liga = new Liga<>(equipos, true);
        liga.generarEnfrentamientos();

        List<Enfrentamiento> enf = liga.getEnfrentamientos();
        assertEquals(2, enf.size());

        assertTrue(contieneEnfrentamiento(enf, "participante1", "participante2"));
        assertTrue(contieneEnfrentamiento(enf, "participante2", "participante1"));
    }

    @Test
    void ligaVueltaSimple_tresParticipantes_generaTresEnfrentamientos() {

        equipos.add(e1);
        equipos.add(e2);
        equipos.add(e3);

        // Especificar explícitamente los tipos genéricos
        Liga<Equipo> liga = new Liga<>(equipos, false);
        liga.generarEnfrentamientos();

        List<Enfrentamiento> enf = liga.getEnfrentamientos();
        assertEquals(3, enf.size());

        assertTrue(contieneEnfrentamiento(enf, "participante1", "participante2"));
        assertTrue(contieneEnfrentamiento(enf, "participante1", "participante3"));
        assertTrue(contieneEnfrentamiento(enf, "participante2", "participante3"));
    }

    @Test
    void ligaDobleVuelta_tresParticipantes_generaSeisEnfrentamientos() {

        equipos.add(e1);
        equipos.add(e2);
        equipos.add(e3);

        // Especificar explícitamente los tipos genéricos
        Liga<Equipo> liga = new Liga<>(equipos, true);
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
}