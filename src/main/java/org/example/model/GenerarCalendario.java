package org.example.model;

import java.util.ArrayList;
import java.util.*;

public abstract class GenerarCalendario<T extends Participante> {

    protected ArrayList<T> participantes;
    protected ArrayList<Enfrentamiento> enfrentamientos;
    protected List<List<Enfrentamiento>> rondasEliminatorias;

    public GenerarCalendario(ArrayList<T> participantes){
        this.participantes = participantes;
        this.enfrentamientos = new ArrayList<>();
    }

    public final void generarCalendario(){
        validarParticipantes();
        generarEnfrentamientos();
    }

    protected void validarParticipantes(){
        if (participantes.size() < 2){
            throw new IllegalArgumentException("Se requieren al menos 2 participantes");
        }
    }

    protected abstract void generarEnfrentamientos();

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

    public ArrayList<Enfrentamiento> getEnfrentamientos(){
        return enfrentamientos;
    }

    public void limpiarCalendario(){
        enfrentamientos.clear();
    }

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

    public List<List<Enfrentamiento>> getRondasEliminatorias() {
        return rondasEliminatorias;
    }
}
