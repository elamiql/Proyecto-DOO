package org.example.model;
import org.example.enums.*;

import java.util.ArrayList;
public class Liga<T extends Participante> extends GenerarCalendario<T>{

    private boolean dobleVuelta;

    public Liga(ArrayList<T> participantes, boolean dobleVuelta){
        super(participantes);
    }

    public Liga(ArrayList<T> participantes){
        this(participantes, true);
    }

    @Override
    public void generarEnfrentamientos(){
        enfrentamientos.clear();
        //IDA
        for (int i=0; i<participantes.size(); i++){
            for (int j=i+1; j< participantes.size(); j++){
                enfrentamientos.add(new Enfrentamiento(participantes.get(i), participantes.get(j)));
            }
        }
        //VUELTA
        for (int i=0; i<participantes.size(); i++){
            for (int j=i+1; j< participantes.size(); j++){
                enfrentamientos.add(new Enfrentamiento(participantes.get(j), participantes.get(i)));
            }
        }
    }

    @Override
    public void imprimirCalendario() {
        System.out.println("=== Calendario Liga " + (dobleVuelta ? "(Doble Vuelta)" : "(Vuelta Simple)") + " ===");
        super.imprimirCalendario();
    }
}