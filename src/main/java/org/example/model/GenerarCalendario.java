package org.example.model;

import org.example.enums.*;
import java.util.ArrayList;
import java.util.Collections;

public class GenerarCalendario<T extends Participante> {

    private ArrayList<T> participantes;
    private Formato formato;
    private ArrayList<Enfrentamiento> enfrentamientos;

    public GenerarCalendario(ArrayList<T> participantes, Formato formato){
        this.participantes = participantes;
        this.formato = formato;
        this.enfrentamientos = new ArrayList<>();
    }

    public void generar(){
        if (participantes.size() < 2){
            System.out.println("No hay participantes suficientes");
        }

        switch (formato){
            case LIGA -> generarLiga();
            case ELIMINATORIA -> generarEliminatoria();
            default -> throw new UnsupportedOperationException("Formato no soportado: " + formato);
        }
    }

    private void generarLiga(){
        for (int i=0; i<participantes.size(); i++){
            for (int j=i+1; j< participantes.size(); j++){
                enfrentamientos.add(new Enfrentamiento(participantes.get(i), participantes.get(j)));
            }
        }
    }

    private void generarEliminatoria(){
        ArrayList<T> copia = new ArrayList<>(participantes);
        Collections.shuffle(copia);

        for (int i=0; i<copia.size() - 1; i+=2){
            enfrentamientos.add(new Enfrentamiento(copia.get(i), copia.get(i+1)));
        }

        if (copia.size() % 2 != 0){
            T libre = copia.get(copia.size() - 1);
            System.out.println("El jugador " + libre.getNombre() + " pasa automaticamente a la sgte ronda");
        }
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos(){
        return enfrentamientos;
    }

    public void imprimirCalendario(){
        System.out.println("=== Calendario de Enfrentamientos ===");
        if (enfrentamientos.isEmpty()){
            System.out.println("No se han generado aun");
        }
        int num = 1;
        for (Enfrentamiento e : enfrentamientos){
            System.out.println("Partido " + num++ + ": " + e);
        }
    }

    public void limpiarCalendario(){
        enfrentamientos.clear();
    }
}
