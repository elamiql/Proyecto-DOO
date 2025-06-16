package org.example.model;

import java.util.*;

public class GruposEliminatoria<T extends Participante> extends GenerarCalendario<T> {

    private int numeroGrupos;
    private int clasificadosPorGrupo;
    private List<List<T>> grupos;
    private List<Liga<T>> generadoresGrupos;
    private Eliminatoria<T> generadorEliminatorias;

    public GruposEliminatoria(ArrayList<T> participantes, int numeroGrupos, int clasificadosPorGrupo){
        super(participantes);
        this.numeroGrupos = numeroGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
        this.grupos = new ArrayList<>();
        this.generadoresGrupos = new ArrayList<>();
    }

    @Override
    protected void validarParticipantes(){
        super.validarParticipantes();
        if (participantes.size() < numeroGrupos * 2){
            throw new IllegalArgumentException("No hay suficientes participantes para: "+ numeroGrupos);
        }
        if (clasificadosPorGrupo * numeroGrupos < 4){
            throw new IllegalArgumentException("Deben clasificar al menos 4 participantes para la fase eliminatoria");
        }
    }

    @Override
    protected void generarEnfrentamientos(){
        enfrentamientos.clear();

        dividirEnGrupos();
        generarFaseGrupos();
        prepararFaseEliminatoria();
    }

    private void dividirEnGrupos(){
        grupos.clear();

        for (int i=0; i<numeroGrupos; i++){
            grupos.add(new ArrayList<>());
        }

        for (int i=0; i<participantes.size(); i++){
            int grupoIndex = i%numeroGrupos;
            grupos.get(grupoIndex).add(participantes.get(i));
        }
    }

    private void generarFaseGrupos(){
        generadoresGrupos.clear();

        for (int i=0; i<grupos.size(); i++){
            List<T> grupo = grupos.get(i);
            if (grupo.size() > 1){
                Liga<T> generadorGrupo = new Liga<>(new ArrayList<>(grupo), false);
                generadorGrupo.generarCalendario();

                enfrentamientos.addAll(generadorGrupo.getEnfrentamientos());
                generadoresGrupos.add(generadorGrupo);
            }
        }
    }

    private void prepararFaseEliminatoria(){
        ArrayList<T> clasificados = new ArrayList<>();

        for (List<T> grupo : grupos){
            for (int i=0; i<Math.min(clasificadosPorGrupo, grupo.size()); i++){
                clasificados.add(grupo.get(i));
            }
        }

        if (clasificados.size() > 1){
            generadorEliminatorias = new Eliminatoria<>(clasificados, false);
            generadorEliminatorias.generarCalendario();

            enfrentamientos.addAll(generadorEliminatorias.getEnfrentamientos());
        }
    }

    @Override
    public void imprimirCalendario() {
        System.out.println("=== Calendario Grupos + Eliminatoria ===");

        // Imprimir fase de grupos
        System.out.println("\n--- FASE DE GRUPOS ---");
        for (int i = 0; i < grupos.size(); i++) {
            System.out.println("\nGrupo " + (char)('A' + i) + ":");
            for (T participante : grupos.get(i)) {
                System.out.println("  - " + participante.getNombre());
            }
        }

        // Imprimir enfrentamientos de grupos
        System.out.println("\n--- ENFRENTAMIENTOS DE GRUPOS ---");
        for (int i = 0; i < generadoresGrupos.size(); i++) {
            System.out.println("\nGrupo " + (char)('A' + i) + ":");
            Liga<T> generador = generadoresGrupos.get(i);
            for (Enfrentamiento e : generador.getEnfrentamientos()) {
                System.out.println("  " + e);
            }
        }

        // Imprimir fase eliminatoria
        if (generadorEliminatorias != null) {
            System.out.println("\n--- FASE ELIMINATORIA ---");
            generadorEliminatorias.imprimirBracket();
        }
    }
}
