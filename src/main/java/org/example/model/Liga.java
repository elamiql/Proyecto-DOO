package org.example.model;

import java.util.*;

public class Liga<T extends Participante> extends GenerarCalendario<T>{

    private boolean dobleVuelta;
    public Map<T, EstadisticasFutbol> tablaEstadisticas;

    public Liga(ArrayList<T> participantes, boolean dobleVuelta){
        super(participantes);
        this.dobleVuelta = dobleVuelta;
        this.tablaEstadisticas = new HashMap<>();
        for (T participante: participantes){
            tablaEstadisticas.put(participante, new EstadisticasFutbol(participante));
        }
    }

    public Liga(ArrayList<T> participantes){
        this(participantes, true);
    }

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

    @Override
    public void imprimirCalendario() {
        System.out.println("=== Calendario Liga " + (dobleVuelta ? "(Doble Vuelta)" : "(Vuelta Simple)") + " ===");
        super.imprimirCalendario();
    }
}