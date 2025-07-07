package org.example.model;

import java.util.*;

/**
 * Clase que representa un calendario de liga para un torneo con participantes de tipo {@link Participante}.
 * Permite generar enfrentamientos en formato de liga, ya sea de vuelta simple o doble vuelta.
 * @param <T> Tipo de participante que extiende de {@link Participante}.
 */
public class Liga<T extends Participante> extends GenerarCalendario<T>{

    /**
     * Indica si la liga es de doble vuelta (cada par de participantes juega dos veces, una como local y otra como visitante).
     */
    private boolean dobleVuelta;

    /**
     * Tabla de estadísticas para los participantes, usando {@link EstadisticasFutbol}.
     */
    private Map<T, EstadisticasFutbol> tablaEstadisticas;

    /**
     * Constructor que inicializa la liga con participantes y opción de doble vuelta.
     *
     * @param participantes Lista de participantes.
     * @param dobleVuelta   True para liga de doble vuelta, false para vuelta simple.
     */
    public Liga(ArrayList<T> participantes, boolean dobleVuelta){
        super(participantes);
        this.dobleVuelta = dobleVuelta;
        this.tablaEstadisticas = new HashMap<>();
        for (T participante: participantes){
            tablaEstadisticas.put(participante, new EstadisticasFutbol(participante));
        }
    }

    /**
     * Constructor que inicializa la liga con participantes y asume doble vuelta.
     *
     * @param participantes Lista de participantes.
     */
    public Liga(ArrayList<T> participantes){
        this(participantes, true);
    }

    /**
     * Genera los enfrentamientos para la liga, creando partidos entre todos los participantes.
     * Si es doble vuelta, se crean dos partidos por cada par (local y visitante).
     */
    @Override
    public void generarEnfrentamientos(){
        enfrentamientos.clear();

        if (dobleVuelta){
            for (int i=0; i<participantes.size(); i++){
                for (int j=i+1; j< participantes.size(); j++){
                    enfrentamientos.add(new Enfrentamiento(participantes.get(i), participantes.get(j)));
                }
            }
            for (int i=0; i<participantes.size(); i++){
                for (int j=i+1; j< participantes.size(); j++){
                    enfrentamientos.add(new Enfrentamiento(participantes.get(j), participantes.get(i)));
                }
            }
        } else {
            for (int i=0; i<participantes.size(); i++){
                for (int j=i+1; j< participantes.size(); j++){
                    enfrentamientos.add(new Enfrentamiento(participantes.get(i), participantes.get(j)));
                }
            }
        }
    }

    /**
     * Imprime el calendario de la liga con información si es de doble o simple vuelta.
     */
    @Override
    public void imprimirCalendario() {
        System.out.println("=== Calendario Liga " + (dobleVuelta ? "(Doble Vuelta)" : "(Vuelta Simple)") + " ===");
        super.imprimirCalendario();
    }

    /**
     * Obtiene la tabla de estadísticas de la liga.
     * @return Mapa con participantes y sus estadísticas de fútbol.
     */
    public Map<T, EstadisticasFutbol> getTablaEstadisticas(){
        return tablaEstadisticas;
    }

    /**
     * Establece la lista de enfrentamientos de la liga.
     * @param enfrentamientos Lista de enfrentamientos a establecer.
     */
    @Override
    public void setEnfrentamientos(ArrayList<Enfrentamiento> enfrentamientos) {
        super.setEnfrentamientos(enfrentamientos);
    }

    /**
     * Establece si la liga es de doble vuelta.
     * @param dobleVuelta True para doble vuelta, false para simple.
     */
    public void setDobleVuelta(boolean dobleVuelta) {
        this.dobleVuelta = dobleVuelta;
    }

    /**
     * Establece la tabla de estadísticas para los participantes.
     * @param tablaEstadisticas Mapa con estadísticas de fútbol por participante.
     */
    public void setTablaEstadisticas(Map<T, EstadisticasFutbol> tablaEstadisticas) {
        this.tablaEstadisticas = tablaEstadisticas;
    }
    public void actualizarEstadisticasDesdeResultados() {
        for (Enfrentamiento enf : enfrentamientos) {
            if (enf.getResultado() instanceof ResultadoFutbol resultado) {
                Participante p1 = enf.getParticipante1();
                Participante p2 = enf.getParticipante2();

                // Registrar para ambos
                if (tablaEstadisticas.containsKey(p1)) {
                    tablaEstadisticas.get(p1).registrarResultado(resultado, p1, true);
                }
                if (tablaEstadisticas.containsKey(p2)) {
                    tablaEstadisticas.get(p2).registrarResultado(resultado, p2, false);
                }
            }
        }
    }

}