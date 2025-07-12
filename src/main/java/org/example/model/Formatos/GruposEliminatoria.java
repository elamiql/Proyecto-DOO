package org.example.model.Formatos;

import org.example.interfaces.Resultado;
import org.example.model.Enfrentamientos.*;
import org.example.model.Estadisticas.*;
import org.example.model.Participante.*;

import java.util.*;
import java.util.function.Function;

/**
 * Clase que representa un sistema de torneo que combina una fase de grupos con una fase eliminatoria.
 * Los participantes se dividen en grupos donde juegan en formato liga,
 * y luego los mejores de cada grupo avanzan a una eliminatoria.
 *
 * @param <T> Tipo de participante que extiende {@link Participante}.
 * @param <R> Tipo de resultado que extiende {@link Resultado}.
 * @param <E> Tipo de estadísticas que extiende {@link EstadisticasParticipante}.
 */
public class GruposEliminatoria<T extends Participante, R extends Resultado, E extends EstadisticasParticipante<T, R>> extends GenerarCalendario<T> {

    private final int numeroGrupos;
    private final int clasificadosPorGrupo;
    private final List<List<T>> grupos;
    private final List<Liga<T, R, E>> generadoresGrupos;
    private Eliminatoria<T> generadorEliminatorias;
    private final E estadisticasCreador; // Objeto que puede crear estadísticas

    /**
     * Constructor que requiere una función para crear estadísticas.
     * @param participantes Lista de participantes
     * @param numeroGrupos Número de grupos
     * @param clasificadosPorGrupo Número de clasificados por grupo
     * @param estadisticasCreador Objeto que puede crear estadísticas para cada participante
     */
    public GruposEliminatoria(ArrayList<T> participantes, int numeroGrupos, int clasificadosPorGrupo, E estadisticasCreador) {
        super(participantes);
        this.numeroGrupos = numeroGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
        this.grupos = new ArrayList<>();
        this.generadoresGrupos = new ArrayList<>();
        this.estadisticasCreador = estadisticasCreador;
    }

    /**
     * Constructor alternativo que no requiere estadísticas (para casos donde no se necesiten).
     * @param participantes Lista de participantes
     * @param numeroGrupos Número de grupos
     * @param clasificadosPorGrupo Número de clasificados por grupo
     */
    public GruposEliminatoria(ArrayList<T> participantes, int numeroGrupos, int clasificadosPorGrupo) {
        this(participantes, numeroGrupos, clasificadosPorGrupo, null);
    }

    @Override
    protected void validarParticipantes() {
        super.validarParticipantes();
        if (participantes.size() < numeroGrupos * 2) {
            throw new IllegalArgumentException("No hay suficientes participantes para: " + numeroGrupos);
        }
        if (clasificadosPorGrupo * numeroGrupos < 4) {
            throw new IllegalArgumentException("Deben clasificar al menos 4 participantes para la fase eliminatoria");
        }
    }

    @Override
    protected void generarEnfrentamientos() {
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

    private void generarFaseGrupos() {
        generadoresGrupos.clear();

        for (List<T> grupo : grupos) {
            if (grupo.size() > 1) {
                Liga<T, R, E> ligaGrupo;

                // Crear liga dependiendo si tenemos estadísticas o no
                if (estadisticasCreador != null) {
                    ligaGrupo = new Liga<>(new ArrayList<>(grupo), false, estadisticasCreador);
                } else {
                    // Si no tenemos estadísticas, necesitamos crear una implementación dummy
                    // o modificar Liga para que acepte null
                    throw new IllegalStateException("No se pueden crear grupos sin estadísticas válidas");
                }

                ligaGrupo.generarCalendario();

                enfrentamientos.addAll(ligaGrupo.getEnfrentamientos());
                generadoresGrupos.add(ligaGrupo);
            }
        }
    }

    private void prepararFaseEliminatoria() {
        ArrayList<T> clasificadosOrdenados = new ArrayList<>();

        // Obtener los mejores clasificados de cada grupo basándose en las estadísticas
        for (int i = 0; i < grupos.size(); i++) {
            List<T> grupo = grupos.get(i);

            if (grupo.size() > 0 && i < generadoresGrupos.size()) {
                Liga<T, R, E> ligaGrupo = generadoresGrupos.get(i);

                // Ordenar participantes del grupo por sus estadísticas
                List<T> participantesOrdenados = new ArrayList<>(grupo);
                participantesOrdenados.sort((p1, p2) -> {
                    E stats1 = ligaGrupo.getTablaEstadisticas().get(p1);
                    E stats2 = ligaGrupo.getTablaEstadisticas().get(p2);

                    // Aquí necesitarías implementar la lógica de comparación
                    // Por ejemplo, comparar por puntos, diferencia de goles, etc.
                    // Este es un ejemplo básico que asume que las estadísticas
                    // tienen un método comparable o que implementas tu propia lógica
                    return Integer.compare(stats2.getPuntos(), stats1.getPuntos());
                });

                // Tomar los mejores clasificados
                for (int j = 0; j < clasificadosPorGrupo && j < participantesOrdenados.size(); j++) {
                    clasificadosOrdenados.add(participantesOrdenados.get(j));
                }
            }
        }

        if (clasificadosOrdenados.size() >= 2) {
            generadorEliminatorias = new Eliminatoria<>(clasificadosOrdenados, false);
            generadorEliminatorias.generarCalendario();
            enfrentamientos.addAll(generadorEliminatorias.getEnfrentamientos());
        }
    }

    /**
     * Método para actualizar estadísticas después de que se jueguen los partidos
     */
    public void actualizarEstadisticasGrupos() {
        for (Liga<T, R, E> ligaGrupo : generadoresGrupos) {
            ligaGrupo.actualizarEstadisticasDesdeResultados();
        }
    }

    /**
     * Obtiene la tabla de estadísticas de un grupo específico
     * @param numeroGrupo Número del grupo (0-indexed)
     * @return Tabla de estadísticas del grupo
     */
    public Map<T, E> getEstadisticasGrupo(int numeroGrupo) {
        if (numeroGrupo >= 0 && numeroGrupo < generadoresGrupos.size()) {
            return generadoresGrupos.get(numeroGrupo).getTablaEstadisticas();
        }
        return new HashMap<>();
    }

    /**
     * Obtiene los clasificados de un grupo específico ordenados por posición
     * @param numeroGrupo Número del grupo (0-indexed)
     * @return Lista de participantes ordenados por posición
     */
    public List<T> getClasificacionGrupo(int numeroGrupo) {
        if (numeroGrupo >= 0 && numeroGrupo < grupos.size() && numeroGrupo < generadoresGrupos.size()) {
            List<T> grupo = grupos.get(numeroGrupo);
            Liga<T, R, E> ligaGrupo = generadoresGrupos.get(numeroGrupo);

            List<T> clasificacion = new ArrayList<>(grupo);
            clasificacion.sort((p1, p2) -> {
                E stats1 = ligaGrupo.getTablaEstadisticas().get(p1);
                E stats2 = ligaGrupo.getTablaEstadisticas().get(p2);
                return Integer.compare(stats2.getPuntos(), stats1.getPuntos());
            });

            return clasificacion;
        }
        return new ArrayList<>();
    }

    @Override
    public void imprimirCalendario() {
        System.out.println("=== Calendario Grupos + Eliminatoria ===");

        System.out.println("\n--- FASE DE GRUPOS ---");
        for (int i = 0; i < grupos.size(); i++) {
            System.out.println("\nGrupo " + (char) ('A' + i) + ":");
            for (T participante : grupos.get(i)) {
                System.out.println("  - " + participante.getNombre());
            }
        }

        System.out.println("\n--- ENFRENTAMIENTOS DE GRUPOS ---");
        for (int i = 0; i < generadoresGrupos.size(); i++) {
            System.out.println("\nGrupo " + (char) ('A' + i) + ":");
            Liga<T, R, E> ligaGrupo = generadoresGrupos.get(i);
            for (Enfrentamiento e : ligaGrupo.getEnfrentamientos()) {
                System.out.println("  " + e);
            }
        }

        if (generadorEliminatorias != null) {
            System.out.println("\n--- FASE ELIMINATORIA ---");
            generadorEliminatorias.imprimirBracket();
        }
    }

    /**
     * Imprime las tablas de posiciones de todos los grupos
     */
    public void imprimirTablasGrupos() {
        System.out.println("\n=== TABLAS DE POSICIONES ===");
        for (int i = 0; i < grupos.size(); i++) {
            System.out.println("\nGrupo " + (char) ('A' + i) + ":");
            List<T> clasificacion = getClasificacionGrupo(i);
            Map<T, E> estadisticas = getEstadisticasGrupo(i);

            for (int pos = 0; pos < clasificacion.size(); pos++) {
                T participante = clasificacion.get(pos);
                E stats = estadisticas.get(participante);
                System.out.println((pos + 1) + ". " + participante.getNombre() +
                        " - Puntos: " + stats.getPuntos());
            }
        }
    }

    // Getters existentes
    public Eliminatoria<T> getGeneradorEliminatorias() {
        return generadorEliminatorias;
    }

    public List<List<T>> getGrupos() {
        return grupos;
    }

    public List<Liga<T, R, E>> getGeneradoresGrupos() {
        return generadoresGrupos;
    }

    public int getNumeroGrupos() {
        return numeroGrupos;
    }
}