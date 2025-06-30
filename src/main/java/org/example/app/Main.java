package org.example.app;

import org.example.model.*;
import org.example.enums.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear lista con los 20 equipos de la liga chilena
        ArrayList<Equipo> equiposLiga = new ArrayList<>() {{
            add(new Equipo("Colo Colo", "001", new ArrayList<>()));
            add(new Equipo("Universidad de Chile", "002", new ArrayList<>()));
            add(new Equipo("Deportes Iquique", "018", new ArrayList<>()));
            add(new Equipo("Palestino", "012", new ArrayList<>()));
            add(new Equipo("Universidad Católica", "003", new ArrayList<>()));
            add(new Equipo("Unión Española", "005", new ArrayList<>()));
            add(new Equipo("Everton", "009", new ArrayList<>()));
            add(new Equipo("Coquimbo Unido", "014", new ArrayList<>()));
            add(new Equipo("Ñublense", "010", new ArrayList<>()));
            add(new Equipo("Audax Italiano", "004", new ArrayList<>()));
            add(new Equipo("Unión La Calera", "017", new ArrayList<>()));
            add(new Equipo("Huachipato", "016", new ArrayList<>()));
            add(new Equipo("Cobresal", "006", new ArrayList<>()));
            add(new Equipo("O'Higgins", "011", new ArrayList<>()));
            add(new Equipo("Cobreloa", "008", new ArrayList<>()));
            add(new Equipo("Copiapo", "007", new ArrayList<>()));
        }};
        /*
        // Crear torneo de equipos (ejemplo con formato LIGA)
        TorneoEquipo torneo = new TorneoEquipo(
                "Liga Chilena 2025",
                Deporte.FUTBOL,
                "01-07-2025 20:00",
                Formato.LIGA
        );

        // Inscribir equipos en el torneo
        for (Equipo equipo : equiposLiga) {
            torneo.addParticipante(equipo);
        }

        // Imprimir calendario usando la clase GenerarCalendario (si usas esa)
        GenerarCalendario<Equipo> generador = new Liga<>(torneo.getParticipantes(), true);
        generador.generarCalendario();
        generador.imprimirCalendario();

        torneo.generarCalendario();

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre del equipo: ");
        String equipo = sc.nextLine();

        ArrayList<Enfrentamiento> calendarioFiltrado = generador.filtrarPorParticipante(equipo);

        System.out.println("=== Calendario para "+ equipo + " ===");
        int n = 1;
        for (Enfrentamiento partido : calendarioFiltrado){
            System.out.println("Partido: " + n++ + ": " + partido);
        }

         */
    }
}
