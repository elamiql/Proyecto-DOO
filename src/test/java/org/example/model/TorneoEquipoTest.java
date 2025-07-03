package org.example.model;

import org.example.enums.Formato;
import org.example.exceptions.FormatoInvalidoException;
import org.example.exceptions.ParticipantesInsuficientesException;
import org.example.interfaces.Disciplina;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TorneoEquipoTest {
    Equipo e1;
    Equipo e2;
    Disciplina disciplina;
    TorneoEquipo torneoEquipo;

    @BeforeEach
    void setUp() {
        e1 = new Equipo("1","1",new ArrayList<Jugador>());
        e2 = new Equipo("2","2",new ArrayList<Jugador>());
        torneoEquipo = new TorneoEquipo("torneo",disciplina,"25-12-2025 14:30",Formato.ELIMINATORIA,2,1,"");
    }

    @Test
    void generarCalendario_formatoInvalido() {
        TorneoEquipo Torneo_error = new TorneoEquipo("Torneo Error", disciplina,"25-12-2025 14:30", Formato.CUSTOM, "Online");

        assertThrows(NumberFormatException.class, Torneo_error::generarCalendario);
    }

    @Test
    void gruposMenores0() {
        TorneoEquipo Torneo_error = new TorneoEquipo("Torneo Error", disciplina,"25-12-2025 14:30", Formato.GRUPOS_CON_ELIMINATORIA,0,0,"online" );

        assertThrows(ParticipantesInsuficientesException.class, Torneo_error::generarCalendario);
    }

    @AfterEach
    void tearDown() {
    }
}