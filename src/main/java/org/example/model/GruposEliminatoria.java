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
        ArrayList<T> primeros = new ArrayList<>();
        ArrayList<T> segundos = new ArrayList<>();


        for (List<T> grupo : grupos){
            if (grupo.size() > 0){
                primeros.add(grupo.get(0));
            }
            if (grupo.size() > 1){
                segundos.add(grupo.get(1));
            }
        }

        List<Enfrentamiento> octavos = new ArrayList<>();

        int[][] cruces = {
                {0, 1}, // A1 vs B2
                {2, 3}, // C1 vs D2
                {4, 5}, // E1 vs F2
                {6, 7}, // G1 vs H2
                {1, 0}, // B1 vs A2
                {3, 2}, // D1 vs C2
                {5, 4}, // F1 vs E2
                {7, 6}, // H1 vs G2
        };

        ArrayList<T> clasificados = new ArrayList<>();
        clasificados.addAll(primeros);
        clasificados.addAll(segundos);

        generadorEliminatorias = new Eliminatoria<>(clasificados);

        List<T> sgteRonda = new ArrayList<>();
        List<Enfrentamiento> rondaInicial = new ArrayList<>();
        for (int[] cruce : cruces) {
            T primero = primeros.get(cruce[0]);
            T segundo = segundos.get(cruce[1]);

            Enfrentamiento enf = new Enfrentamiento(primero, segundo);
            rondaInicial.add(enf);
            enfrentamientos.add(enf);

            String nombre = "Ganador (" + primero.getNombre() + " vs " + segundo.getNombre() + ")";
            T placeHolder = (T) new Participante(nombre, "-1"){
                @Override
                public void inscribirse(Torneo <?> torneo){}
            };

            sgteRonda.add(placeHolder);
        }
        List<List<Enfrentamiento>> rondas = new ArrayList<>();
        rondas.add(rondaInicial);

        // ✅ Seteamos la ronda inicial a la eliminatoria generada
        generadorEliminatorias.setBracket(rondas);

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
