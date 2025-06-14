package org.example.model;

import org.example.enums.*;
import java.util.*;

public class TorneoEquipo extends Torneo<Equipo>{
    private int numGrupos = 0;
    private int clasificadosPorGrupo = 0;

    // Constructor para torneos preestablecidos que no usan grupos o no necesitan esos parámetros
    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato){
        super(nombre, disciplina, fecha, formato);
    }

    // Constructor para torneos custom con grupos y clasificados configurables
    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato,
                        int numGrupos, int clasificadosPorGrupo){
        super(nombre, disciplina, fecha, formato);
        this.numGrupos = numGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
    }

    // Getters por si los necesitas
    public int getNumGrupos() {
        return numGrupos;
    }

    public int getClasificadosPorGrupo() {
        return clasificadosPorGrupo;
    }

    @Override
    public void generarCalendario() {
        System.out.println("Generando calendario para el torneo: " + getNombre());
        GenerarCalendario<Equipo> generarCalEquipo = new GenerarCalendario<>(getEquipos(), getFormato());

        if (getFormato() == Formato.GRUPOS_CON_ELIMINATORIA) {
            if (numGrupos <= 0 || clasificadosPorGrupo <= 0) {
                System.out.println("Error: numGrupos y clasificadosPorGrupo deben ser mayores que 0");
                return;
            }
            generarCalEquipo.generarGrupoYEliminatoria(numGrupos, clasificadosPorGrupo);

            // Imprimir enfrentamientos de grupos
            System.out.println("=== Fase de grupos ===\n");
            int partidoNum = 1;
            for (Enfrentamiento e : generarCalEquipo.getEnfrentamientos()) {
                System.out.println("Partido " + partidoNum + ": " + e);
                partidoNum++;
            }

            // Imprimir brackets eliminatorios
            System.out.println("\n=== Bracket de Eliminatoria ===\n");
            int rondaNum = 1;
            for (List<Enfrentamiento> ronda : generarCalEquipo.getRondasEliminatorias()) {
                System.out.println("Ronda " + rondaNum);
                int partidoRonda = 1;
                for (Enfrentamiento e : ronda) {
                    System.out.println("Partido " + partidoRonda + ": " + e);
                    partidoRonda++;
                }
                rondaNum++;
                System.out.println();
            }
        } else {
            generarCalEquipo.generar();
            generarCalEquipo.imprimirCalendario();
        }
    }
}
