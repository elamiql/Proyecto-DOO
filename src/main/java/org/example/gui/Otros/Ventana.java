package org.example.gui.Otros;

import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.enums.Videojuegos;
import org.example.gui.Paneles.PanelPrincipal;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.torneo.ActualizadorEstadoTorneos;
import org.example.model.torneo.GestorTorneos;
import org.example.model.torneo.TorneoEquipo;
import org.example.model.torneo.TorneoIndividual;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase principal de la interfaz gráfica que representa la ventana del sistema de torneos.
 *
 * <p>Esta ventana configura y lanza la interfaz de usuario inicial cargando el {@link PanelPrincipal}
 * como contenido base, e inicia un hilo de actualización periódica del estado de los torneos mediante
 * la clase {@link ActualizadorEstadoTorneos}.</p>
 * <p>Hereda de {@link JFrame} para construir una ventana estándar de Swing.</p>
 */
public class Ventana extends JFrame {

    /**
     * Crea e inicializa la ventana principal del sistema de torneos.
     *
     * <p>
     * Establece el título de la ventana.
     * </p>
     * Inicia el actualizador periódico de estados de torneos.
     * <p>
     * Configura dimensiones, posición y comportamiento al cerrar.
     * </p>
     * Establece {@link PanelPrincipal} como panel inicial.
     *
     */
    public Ventana() {
        super("Sistema de torneos");
        generarTorneosConEquiposDeEjemplo();


        ActualizadorEstadoTorneos actualizador = new ActualizadorEstadoTorneos(1_000);
        actualizador.iniciar();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Carga el panel principal
        setContentPane(new PanelPrincipal(this));
        setVisible(true);
    }

    /**
     * Genera torneos prederteminados para iniciar la aplicacion.
     */
    private void generarTorneosConEquiposDeEjemplo() {
        // ----------------- JUGADORES BASE -------------------
        Jugador j1 = new Jugador("Alice", "001");
        Jugador j2 = new Jugador("Bob", "002");
        Jugador j3 = new Jugador("Charlie", "003");
        Jugador j4 = new Jugador("Dana", "004");
        Jugador j5 = new Jugador("Eve", "005");
        Jugador j6 = new Jugador("Frank", "006");
        Jugador j7 = new Jugador("Grace", "007");
        Jugador j8 = new Jugador("Henry", "008");

        // ----------------- EQUIPOS DE PRUEBA -------------------
        Equipo equipoA = new Equipo("Equipo A", "A01", new ArrayList<>(List.of(j1, j2)));
        Equipo equipoB = new Equipo("Equipo B", "B01", new ArrayList<>(List.of(j3, j4)));
        Equipo equipoC = new Equipo("Equipo C", "C01", new ArrayList<>(List.of(j5, j6)));
        Equipo equipoD = new Equipo("Equipo D", "D01", new ArrayList<>(List.of(j7, j8)));

        // ----------------- TORNEO DE FUTBOL -------------------
        TorneoIndividual torneoFutbol = new TorneoIndividual(
                "Torneo Fútbol",
                Deporte.FUTBOL,
                "01-06-2025 10:00",
                Formato.ELIMINATORIA,
                "1"
        );
        equipoA.inscribirse(torneoFutbol);
        equipoB.inscribirse(torneoFutbol);
        equipoC.inscribirse(torneoFutbol);
        equipoD.inscribirse(torneoFutbol);
        GestorTorneos.agregarTorneo(torneoFutbol);

        // ----------------- TORNEO FIFA -------------------
        ArrayList<Equipo> equiposFIFA = new ArrayList<>(Arrays.asList(
                new Equipo("PSG", "001", new ArrayList<>()),
                new Equipo("Real Madrid", "002", new ArrayList<>()),
                new Equipo("Barcelona", "003", new ArrayList<>()),
                new Equipo("Manchester City", "004", new ArrayList<>())
        ));
        TorneoEquipo torneoFIFA = new TorneoEquipo(
                "Champions FIFA",
                Videojuegos.FIFA,
                "02-06-2025 18:00",
                Formato.ELIMINATORIA,
                "1"
        );
        equiposFIFA.forEach(torneoFIFA::addParticipante);
        GestorTorneos.agregarTorneo(torneoFIFA);

        // ----------------- TORNEO TENIS -------------------
        TorneoIndividual torneoTenis = new TorneoIndividual(
                "Roland Garros",
                Deporte.TENIS,
                "03-06-2025 12:00",
                Formato.LIGA,
                "1"
        );
        j1.inscribirse(torneoTenis);
        j2.inscribirse(torneoTenis);
        j3.inscribirse(torneoTenis);
        j4.inscribirse(torneoTenis);
        GestorTorneos.agregarTorneo(torneoTenis);

        // ----------------- TORNEO TENIS DE MESA -------------------
        TorneoIndividual torneoPingPong = new TorneoIndividual(
                "Ping Pong Liga",
                Deporte.TENIS_DE_MESA,
                "04-06-2025 14:00",
                Formato.LIGA,
                "1"
        );
        j5.inscribirse(torneoPingPong);
        j6.inscribirse(torneoPingPong);
        j7.inscribirse(torneoPingPong);
        j8.inscribirse(torneoPingPong);
        GestorTorneos.agregarTorneo(torneoPingPong);

        // ----------------- TORNEO AJEDREZ -------------------
        TorneoIndividual torneoAjedrez = new TorneoIndividual(
                "Copa Ajedrez",
                Deporte.AJEDREZ,
                "05-06-2025 15:30",
                Formato.ELIMINATORIA,
                "1"
        );
        j1.inscribirse(torneoAjedrez);
        j2.inscribirse(torneoAjedrez);
        j3.inscribirse(torneoAjedrez);
        j4.inscribirse(torneoAjedrez);
        GestorTorneos.agregarTorneo(torneoAjedrez);

        // ----------------- TORNEO LOL -------------------
        ArrayList<Equipo> equiposLol = new ArrayList<>(Arrays.asList(
                new Equipo("G2", "L01", new ArrayList<>()),
                new Equipo("T1", "L02", new ArrayList<>()),
                new Equipo("Fnatic", "L03", new ArrayList<>()),
                new Equipo("DRX", "L04", new ArrayList<>())
        ));
        TorneoEquipo torneoLOL = new TorneoEquipo(
                "Worlds LoL",
                Videojuegos.LOL,
                "06-06-2025 17:00",
                Formato.LIGA,
                "1"
        );
        equiposLol.forEach(torneoLOL::addParticipante);
        GestorTorneos.agregarTorneo(torneoLOL);
        TorneoEquipo torneoNoIniciado = new TorneoEquipo(
                "LoL ",
                Videojuegos.LOL,
                "10-06-2026 20:00",
                Formato.ELIMINATORIA,
                "1"
        );
        GestorTorneos.agregarTorneo(torneoNoIniciado);

    }

}
