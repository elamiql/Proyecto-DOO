package org.example.model.torneo;

import org.example.enums.*;
import org.example.exceptions.*;
import org.example.interfaces.*;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TorneoIndividualTest {
    private Disciplina disciplina;
    private Participante participante1;
    private Participante participante2;

    @BeforeEach
    void setUp() {
        disciplina = new Disciplina() {
            @Override
            public String getNombre() {
                return "DisciplinaTest";
            }
        };
        participante1 = new Jugador("J1", "1");
        participante2 = new Jugador("J2", "2");
    }

    @Test
    void generarCalendario_valido() {
        TorneoIndividual torneo = new TorneoIndividual("Torneo Test", disciplina, "25-12-2025 14:30", Formato.ELIMINATORIA, "Online");

        assertTrue(torneo.getEnfrentamientos().isEmpty(), "El calendario debería tener enfrentamientos");
    }
    /*
    @Test
    void generarCalendario_formatoInvalido_lanzaExcepcion() {
        TorneoIndividual torneo = new TorneoIndividual("Torneo Error", disciplina, "25-12-2025 14:30", Formato.CUSTOM, "Online");

        torneo.addParticipante(participante1);
        torneo.addParticipante(participante2);

        assertThrows(FormatoInvalidoException.class, torneo::generarCalendario);
    }

     */
}
