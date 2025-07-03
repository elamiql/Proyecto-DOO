package org.example.model;

import org.example.interfaces.Resultado;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EnfrentamientoTest {
    Participante p1;
    Participante p2;
    Participante p3;
    Participante equipo;
    Resultado resultado;
    Resultado novalido;
    Resultado empate;
    Enfrentamiento enfrentamiento;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        p1 = new Jugador("participante1","1");
        p2 = new Jugador("participante2","2");
        p3 = new Jugador("participante3","3");
        equipo = new Equipo("equipo","1",new ArrayList<>());
        resultado = new ResultadoFutbol(p1,p2,3,1);
        empate = new ResultadoFutbol(p1,p2,1,1);
        novalido = new ResultadoFutbol(p1,p2,-1,0);
        enfrentamiento = new Enfrentamiento(p1,p2);

    }

    @Test
    void enfrentamientosinerrores() {
        Enfrentamiento enfrentamiento = new Enfrentamiento(p1, p1);


        assertNotNull(enfrentamiento);
    }

    @Test
    void ParticipanteNull() {
        assertThrows(IllegalArgumentException.class, () -> new Enfrentamiento(null, p2));
        assertThrows(IllegalArgumentException.class, () -> new Enfrentamiento(p1, null));
    }

    @Test
    void ClasesDistintas() {
        assertThrows(IllegalArgumentException.class, () -> new Enfrentamiento(p1, equipo));
    }

    @Test
    void resultado(){
        enfrentamiento.registrarResultado(resultado);

        assertEquals(p1, enfrentamiento.getGanador());
    }

    @Test
    void resultado_empate() {
        enfrentamiento.registrarResultado(empate);
        assertNull(enfrentamiento.getGanador());
    }

    @Test
    void resultado_invalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            enfrentamiento.registrarResultado(novalido);
        });
    }

    @Test
    void setGanador_conParticipanteValido_funcionaCorrectamente() {
        enfrentamiento.setGanador(p1);

        assertEquals(p1, enfrentamiento.getGanador(), "El ganador debe ser el jugador1");
    }

    @Test
    void setGanador_conParticipanteInvalido_lanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            enfrentamiento.setGanador(p3);
        });

        assertEquals("El ganador debe ser uno de los participantes", ex.getMessage());
        assertNull(enfrentamiento.getGanador(), "No debe haber un ganador asignado");
    }



    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }
}