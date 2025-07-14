package org.example.model.Formatos;

import org.example.interfaces.Disciplina;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Enfrentamientos.GenerarCalendario;
import org.example.model.Participante.Participante;

import java.util.*;

/**
 * Clase que representa un sistema de torneo que combina una fase de grupos con una fase eliminatoria.
 * <p>
 * Los participantes se dividen en grupos donde juegan en formato liga,
 * y luego los mejores de cada grupo avanzan a una eliminatoria.
 * </p>
 *
 * @param <T> Tipo de participante que extiende {@link Participante}.
 */
public class GruposEliminatoria<T extends Participante> extends GenerarCalendario<T> {

    /**
     * Número de grupos en la fase de grupos.
     */
    private final int numeroGrupos;

    /**
     * Número de participantes que clasifican por grupo a la fase eliminatoria.
     */
    private final int clasificadosPorGrupo;

    /**
     * Lista con los grupos creados, cada grupo es una lista de participantes.
     */
    private final List<List<T>> grupos;

    /**
     * Lista con los generadores de calendario para cada grupo (fase de liga).
     */
    private final List<Liga<T>> generadoresGrupos;

    /**
     * Generador del calendario para la fase eliminatoria posterior a la fase de grupos.
     */
    private Eliminatoria<T, ?, ?> generadorEliminatorias;

    private Disciplina disciplina;

    /**
     * Constructor que inicializa los grupos y parámetros del torneo.
     *
     * @param participantes       Lista de participantes.
     * @param numeroGrupos        Cantidad de grupos en la fase de grupos.
     * @param clasificadosPorGrupo Número de participantes que clasifican por grupo.
     */
    public GruposEliminatoria(ArrayList<T> participantes, int numeroGrupos, int clasificadosPorGrupo, Disciplina disciplina){
        super(participantes);
        this.numeroGrupos = numeroGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
        this.grupos = new ArrayList<>();
        this.generadoresGrupos = new ArrayList<>();
        this.disciplina = disciplina;
    }

    /**
     * Valida que haya suficientes participantes para formar los grupos y que el número
     * de clasificados sea al menos 4 para poder hacer la fase eliminatoria.
     * Llama a la validación base para verificar al menos 2 participantes.
     */
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

    /**
     * Genera los enfrentamientos para la fase de grupos y la fase eliminatoria.
     * Primero divide participantes en grupos, genera la fase de grupos y luego prepara la eliminatoria.
     */
    @Override
    protected void generarEnfrentamientos(){
        enfrentamientos.clear();

        dividirEnGrupos();
        generarFaseGrupos();
        prepararFaseEliminatoria();
    }

    /**
     * Divide a los participantes en grupos equilibrados.
     */
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

    /**
     * Genera la fase de grupos creando calendarios de tipo Liga para cada grupo y agregando sus enfrentamientos.
     */
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

    /**
     * Prepara la fase eliminatoria tomando a los mejores clasificados de cada grupo
     * y generando un calendario eliminatorio con ellos.
     */
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
             generadorEliminatorias = new Eliminatoria<>(clasificadosOrdenados, disciplina);
             generadorEliminatorias.generarCalendario();
             enfrentamientos.addAll(generadorEliminatorias.getEnfrentamientos());
         }
    }

    /**
     * Imprime el calendario completo incluyendo la fase de grupos y la fase eliminatoria.
     * Muestra los participantes por grupo, los enfrentamientos de cada grupo y el bracket eliminatorio.
     */
    @Override
    public void imprimirCalendario() {
        System.out.println("=== Calendario Grupos + Eliminatoria ===");


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
    //Getters
    public Eliminatoria<?, ?, ?> getGeneradorEliminatorias(){
        return generadorEliminatorias;
    }


    public List<List<T>> getGrupos(){
        return grupos;
    }

    public List<Liga<T>> getGeneradoresGrupos(){
        return generadoresGrupos;
    }

    public int getNumeroGrupos(){
        return numeroGrupos;
    }

}