package org.example.model;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.exceptions.FormatoInvalidoException;
import org.example.interfaces.Disciplina;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TorneoIndividualTest {
    Jugador p1;
    Jugador p2;
    Disciplina disciplina;


    @BeforeEach
    void setUp() {
        p1 = new Jugador("participante1","1");
        p2 = new Jugador("participante2","2");
    }

    @Test
    void registrarResultado() {
        TorneoIndividual torneo = new TorneoIndividual("Test", disciplina,"25-12-2025 14:30", Formato.ELIMINATORIA, "Online");
        torneo.registrarResultado(p1, true);

    }


    @Test
    void generarCalendario_formatoInvalido() {
        TorneoIndividual torneo = new TorneoIndividual("Torneo Error", disciplina,"25-12-2025 14:30", Formato.CUSTOM, "Online");

        assertThrows(FormatoInvalidoException.class, torneo::generarCalendario);
    }

    @AfterEach
    void tearDown() {
    }
}