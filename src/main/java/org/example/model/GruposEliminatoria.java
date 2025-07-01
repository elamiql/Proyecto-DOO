package org.example.model;

import java.util.*;

public class GruposEliminatoria<T extends Participante> extends GenerarCalendario<T> {

    private final int numeroGrupos;
    private final int clasificadosPorGrupo;
    private final List<List<T>> grupos;
    private final List<Liga<T>> generadoresGrupos;
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

        int z=0;
        for (int i=0; i<numeroGrupos; i++){
            for (int j=0; j<participantes.size()/numeroGrupos; j++){
                grupos.get(i).add(participantes.get(z));
                z++;
            }
        }
    }

    private void generarFaseGrupos(){
        generadoresGrupos.clear();

        for (List<T> grupo : grupos) {
            if (grupo.size() > 1) {
                Liga<T> generadorGrupo = new Liga<>(new ArrayList<>(grupo), false);
                generadorGrupo.generarCalendario();

                enfrentamientos.addAll(generadorGrupo.getEnfrentamientos());
                generadoresGrupos.add(generadorGrupo);
            }
        }
    }

    private void prepararFaseEliminatoria() {
        ArrayList<T> clasificadosOrdenados = new ArrayList<>();

        for (int i=0; i<numeroGrupos; i+=2){
            List<T> grupo1 = grupos.get(i);
            List<T> grupo2 = grupos.get(i+1);

            clasificadosOrdenados.add(grupo1.get(0));
            clasificadosOrdenados.add(grupo2.get(1));

            clasificadosOrdenados.add(grupo2.get(0));
            clasificadosOrdenados.add(grupo1.get(1));

        }

        if (clasificadosOrdenados.size() > 1) {
            generadorEliminatorias = new Eliminatoria<>(clasificadosOrdenados, false);
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