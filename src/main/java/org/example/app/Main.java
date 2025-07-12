package org.example.app;

import org.example.model.Enfrentamientos.*;
import org.example.model.Estadisticas.*;
import org.example.model.Formatos.*;
import org.example.model.Participante.*;
import org.example.model.Resultado.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {

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

        ArrayList<Equipo> participantes = new ArrayList<>(equiposLiga);

        // Map con Equipo como clave
        Map<Equipo, EstadisticasFutbol> tablaEstadisticas = new HashMap<>();
        for (Equipo equipo : participantes) {
            tablaEstadisticas.put(equipo, new EstadisticasFutbol(equipo));
        }

        // Liga con Equipo como T
        EstadisticasFutbol p = new EstadisticasFutbol();
        Liga<Equipo, ResultadoFutbol, EstadisticasFutbol> liga = new Liga<>(participantes, true, p);

        liga.generarCalendario();

        Random rnd = new Random();
        for (Enfrentamiento partido : liga.getEnfrentamientos()) {
            int golesLocal = rnd.nextInt(6);
            int golesVisitante = rnd.nextInt(6);

            ResultadoFutbol resultado = new ResultadoFutbol(
                    partido.getParticipante1(),
                    partido.getParticipante2(),
                    golesLocal, golesVisitante
            );

            partido.registrarResultado(resultado);

            EstadisticasFutbol estadLocal = liga.getTablaEstadisticas().get(partido.getParticipante1());
            EstadisticasFutbol estadVisitante = liga.getTablaEstadisticas().get(partido.getParticipante2());

            estadLocal.registrarResultado(resultado, (Equipo) partido.getParticipante1(), true);
            estadVisitante.registrarResultado(resultado, (Equipo) partido.getParticipante2(), false);
        }

        liga.imprimirCalendario();

        System.out.println("\nTabla de Posiciones:");
        System.out.println("Equipo               | PJ |  G |  E |  P | GF  | GC  | DIF | Pts");
        System.out.println("---------------------------------------------------------------");

        liga.getTablaEstadisticas().values().stream()
                .sorted(Comparator.comparingInt(EstadisticasFutbol::getPuntos).reversed()
                        .thenComparingInt(EstadisticasFutbol::getDiferenciaGoles).reversed()
                        .thenComparingInt(EstadisticasFutbol::getGolesFavor).reversed())
                .forEach(est -> System.out.println(est.toTablaString()));

        Scanner sc = new Scanner(System.in);
        System.out.print("\nIngrese el nombre del equipo para ver sus partidos: ");
        String nombreEquipo = sc.nextLine();

        ArrayList<Enfrentamiento> partidosEquipo = liga.filtrarPorParticipante(nombreEquipo);

        if (partidosEquipo.isEmpty()) {
            System.out.println("No se encontraron partidos para el equipo: " + nombreEquipo);
        } else {
            System.out.println("\nPartidos de " + nombreEquipo + ":");
            int num = 1;
            for (Enfrentamiento partido : partidosEquipo) {
                ResultadoFutbol res = (ResultadoFutbol) partido.getResultado();
                System.out.printf("Partido %d: %s - Resultado: %d:%d\n",
                        num++, partido.toString(),
                        res.getGolesLocal(), res.getGolesVisitante());
            }
        }
    }
}
