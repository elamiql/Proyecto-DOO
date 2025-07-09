package org.example.model.Enfrentamientos;

import org.example.exceptions.ParticipantesInsuficientesException;
import org.example.model.Participante.Participante;

import java.util.ArrayList;
import java.util.*;

/**
 * Clase abstracta para generar un calendario de enfrentamientos entre participantes.
 * Sirve como base para distintas implementaciones de formatos de torneo (liga, eliminación, etc.).
 * @param <T> Tipo de los participantes, que debe extender {@link Participante}.
 */
public abstract class GenerarCalendario<T extends Participante> {

    /** Lista de participantes del torneo */
    protected ArrayList<T> participantes;

    /** Lista de enfrentamientos generados para el calendario */
    protected ArrayList<Enfrentamiento> enfrentamientos;

    /** Lista de rondas eliminatorias (solo usada en torneos por eliminación) */
    protected List<List<Enfrentamiento>> rondasEliminatorias;

    /**
     * Constructor de la clase. Inicializa la lista de participantes y enfrentamientos.
     * @param participantes Lista de participantes del torneo.
     */
    public GenerarCalendario(ArrayList<T> participantes){
        this.participantes = participantes;
        this.enfrentamientos = new ArrayList<>();
        this.rondasEliminatorias = new ArrayList<>();
    }

    /**
     * Método final que genera el calendario, validando previamente los participantes.
     */
    public final void generarCalendario(){
        validarParticipantes();
        generarEnfrentamientos();
    }

    /**
     * Valida que exista un número mínimo de participantes.
     * Lanza una excepción si hay menos de 2.
     * @throws ParticipantesInsuficientesException si hay menos de 2 participantes.
     */
    protected void validarParticipantes(){
        if (participantes.size() < 2){
            throw new ParticipantesInsuficientesException("Se requieren al menos 2 participantes");
        }
    }

    /**
     * Método abstracto que debe ser implementado por las subclases para generar los enfrentamientos específicos.
     */
    protected abstract void generarEnfrentamientos();

    /**
     * Imprime por consola la lista de enfrentamientos generados.
     * Si no hay enfrentamientos generados, informa al usuario.
     */
    public void imprimirCalendario(){
        System.out.println("\n=== Calendario de Enfrentamientos ===");

        if (enfrentamientos.isEmpty()){
            System.out.println("No se han generado aun");
            return;
        }
        int num = 1;
        for (Enfrentamiento e : enfrentamientos){
            System.out.println("Partido " + (num++) + ": " + e);
        }
    }

    /**
     * Filtra los enfrentamientos en los que participa un participante específico.
     * @param nombreParticipante Nombre del participante a buscar.
     * @return Lista de enfrentamientos donde aparece el participante.
     */
    public ArrayList<Enfrentamiento> filtrarPorParticipante(String nombreParticipante) {
        ArrayList<Enfrentamiento> filtrados = new ArrayList<>();

        for (Enfrentamiento e : enfrentamientos) {
            if (e.getParticipante1().getNombre().equalsIgnoreCase(nombreParticipante) ||
                    e.getParticipante2().getNombre().equalsIgnoreCase(nombreParticipante)) {
                filtrados.add(e);
            }
        }
        return filtrados;
    }

    /**
     * Devuelve la lista de enfrentamientos generados.
     * @return Lista de enfrentamientos.
     */
    public ArrayList<Enfrentamiento> getEnfrentamientos(){
        return enfrentamientos;
    }

    /**
     * Establece la lista de enfrentamientos.
     * @param enfrentamientos Nueva lista de enfrentamientos.
     */
    public void setEnfrentamientos(ArrayList<Enfrentamiento> enfrentamientos){
        this.enfrentamientos = enfrentamientos;
    }

    /**
     * Elimina todos los enfrentamientos actuales del calendario.
     */
    public void limpiarCalendario(){
        enfrentamientos.clear();
    }

    /**
     * Devuelve el nombre de la ronda eliminatoria según el índice.
     * @param rondaIndex Índice de la ronda.
     * @param totalRondas Número total de rondas.
     * @return Nombre descriptivo de la ronda.
     */
    private String getNombreRonda(int rondaIndex, int totalRondas){
        int restante = totalRondas - rondaIndex;

        return switch (restante) {
            case 1 -> "Final";
            case 2 -> "Semifinal";
            case 3 -> "Cuartos De Final";
            case 4 -> "Octavos de Final";
            case 5 -> "Dieciseisavos de Final";
            default -> "Ronda " + (rondaIndex - 1);
        };
    }

    /**
     * Imprime el bracket de rondas eliminatorias.
     * Si no se ha generado, muestra un mensaje indicándolo.
     */
    public void imprimirBracket(){
        if (rondasEliminatorias == null || rondasEliminatorias.isEmpty()){
            System.out.println("No se ha generado el bracket");
            return;
        }

        int rondaNum = 1;
        for (List<Enfrentamiento> ronda : rondasEliminatorias){
            String nombreRonda = getNombreRonda(rondaNum - 1, rondasEliminatorias.size());
            System.out.println("=== " + nombreRonda + " ===");
            rondaNum++;
            int numPartido = 1;
            for (Enfrentamiento e : ronda){
                String p1 = (e.getParticipante1() == null) ? "BYE" : e.getParticipante1().getNombre();
                String p2 = (e.getParticipante2() == null) ? "BYE" : e.getParticipante2().getNombre();
                System.out.println("Partido " + (numPartido++) + ": " + p1 + " vs " + p2);
            }
            System.out.println();
        }
    }

    /**
     * Devuelve las rondas eliminatorias generadas.
     * @return Lista de listas de enfrentamientos por ronda.
     */
    public List<List<Enfrentamiento>> getRondasEliminatorias() {
        return rondasEliminatorias;
    }
}
