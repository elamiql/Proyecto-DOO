package org.example.app;

import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Estadisticas.EstadisticasFutbol;
import org.example.model.Formatos.Eliminatoria;
import org.example.model.Formatos.GruposEliminatoria;
import org.example.model.Formatos.Liga;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Participante;
import org.example.model.Resultado.ResultadoFutbol;

import java.util.*;

public class Main3 {
    public static void main(String[] args) {
        ArrayList<Equipo> mundialEquipos = new ArrayList<>();

        // Grupos del Mundial de Qatar 2022
        String[][] gruposQatar = {
                {"Paises Bajos", "Senegal", "Ecuador", "Qatar"},        // Grupo A
                {"Inglaterra", "EEUU", "Irán", "Gales"},                // Grupo B
                {"Argentina", "Polonia", "México", "Arabia Saudita"},   // Grupo C
                {"Francia", "Australia", "Túnez", "Dinamarca"},         // Grupo D
                {"Japón", "España", "Alemania", "Costa Rica"},          // Grupo E
                {"Marruecos", "Croacia", "Bélgica", "Canadá"},          // Grupo F
                {"Brasil", "Suiza", "Camerún", "Serbia"},               // Grupo G
                {"Portugal", "Corea del Sur", "Uruguay", "Ghana"}       // Grupo H
        };

        for (String[] grupo : gruposQatar) {
            for (String nombre : grupo) {
                mundialEquipos.add(new Equipo(nombre, "Seleccion", new ArrayList<>()));
            }
        }

        // Generador de mundial (fase grupos + eliminación) - Corregido con tipos genéricos
        EstadisticasFutbol estadisticasFutbol= new EstadisticasFutbol();
        GruposEliminatoria<Equipo, ResultadoFutbol, EstadisticasFutbol> generador =
                new GruposEliminatoria<>(mundialEquipos, 8, 2, estadisticasFutbol);

        generador.generarCalendario();

        Random rnd = new Random();

        // Simular resultados aleatorios en fase de grupos - Corregido con tipos genéricos
        List<Liga<Equipo, ResultadoFutbol, EstadisticasFutbol>> ligasGrupos = generador.getGeneradoresGrupos();
        for (Liga<Equipo, ResultadoFutbol, EstadisticasFutbol> liga : ligasGrupos) {
            for (Enfrentamiento e : liga.getEnfrentamientos()) {
                int golesLocal = rnd.nextInt(6);
                int golesVisitante = rnd.nextInt(6);
                ResultadoFutbol resultado = new ResultadoFutbol(e.getParticipante1(), e.getParticipante2(), golesLocal, golesVisitante);
                e.registrarResultado(resultado);

                Equipo local = (Equipo) e.getParticipante1();
                Equipo visita = (Equipo) e.getParticipante2();

                liga.getTablaEstadisticas().get(local).registrarResultado(resultado, local, true);
                liga.getTablaEstadisticas().get(visita).registrarResultado(resultado, visita, false);
            }
        }

        // Mostrar resultados de la fase de grupos
        System.out.println("\n=== FASE DE GRUPOS ===");
        char grupoLetra = 'A';
        for (int i = 0; i < ligasGrupos.size(); i++) {
            Liga<Equipo, ResultadoFutbol, EstadisticasFutbol> liga = ligasGrupos.get(i);
            System.out.println("\nGRUPO " + grupoLetra++ + ":");
            System.out.println("Equipo               | PJ |  G |  E |  P | GF  | GC  | DIF | Pts");
            System.out.println("------------------------------------------------------------------");
            liga.getTablaEstadisticas().values().stream()
                    .sorted(Comparator.comparingInt(EstadisticasFutbol::getPuntos).reversed()
                            .thenComparingInt(EstadisticasFutbol::getDiferenciaGoles).reversed()
                            .thenComparingInt(EstadisticasFutbol::getGolesFavor).reversed())
                    .forEach(est -> System.out.println(est.toTablaString()));
        }

        // Preparar y simular fase eliminatoria con clasificados desde fase de grupos
        Eliminatoria<Equipo> eliminatoria = generador.getGeneradorEliminatorias();

        if (eliminatoria != null) {
            List<List<Enfrentamiento>> rondas = eliminatoria.getRondasEliminatorias();

            System.out.println("\n=== FASE ELIMINATORIA ===");

            int rondaNum = 1;
            for (List<Enfrentamiento> ronda : rondas) {
                System.out.println("\n--- Ronda " + rondaNum++ + " ---");
                for (Enfrentamiento partido : ronda) {
                    Participante p1 = partido.getParticipante1();
                    Participante p2 = partido.getParticipante2();

                    if (p2 == null) {
                        System.out.println(p1.getNombre() + " pasa automáticamente (bye)");
                        partido.setGanador(p1);
                        continue;
                    }

                    // Simulación aleatoria
                    int golesP1 = rnd.nextInt(6);
                    int golesP2 = rnd.nextInt(6);
                    while (golesP1 == golesP2) { // evitar empate en eliminación
                        golesP1 = rnd.nextInt(6);
                        golesP2 = rnd.nextInt(6);
                    }

                    ResultadoFutbol res = new ResultadoFutbol(p1, p2, golesP1, golesP2);
                    partido.registrarResultado(res);
                    Participante ganador = golesP1 > golesP2 ? p1 : p2;
                    partido.setGanador(ganador);

                    System.out.println(p1.getNombre() + " " + golesP1 + " - " + golesP2 + " " + p2.getNombre()
                            + " --> Ganador: " + ganador.getNombre());
                }
            }

            // Mostrar ganador final
            Enfrentamiento finalMatch = rondas.get(rondas.size() - 1).get(0);
            System.out.println("\n=== CAMPEÓN DEL MUNDIAL ===");
            System.out.println("🏆 " + finalMatch.getGanador().getNombre());
        }
    }
}