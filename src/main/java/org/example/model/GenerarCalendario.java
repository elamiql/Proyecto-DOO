package org.example.model;

import org.example.enums.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class GenerarCalendario<T extends Participante> {

    private ArrayList<T> participantes;
    private Formato formato;
    private ArrayList<Enfrentamiento> enfrentamientos;
    private List<List<Enfrentamiento>> rondasEliminatorias;

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
            case ELIMINATORIA ->{
                    generarEliminatoria();
                    crearYGuardarBracket();
            }

            default -> throw new UnsupportedOperationException("Formato no soportado: " + formato);
        }
    }

    private void generarLiga(){
        enfrentamientos.clear();
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

    public List<List<Enfrentamiento>> generarBracket(ArrayList<T> participantes) {
        // Validamos que sea potencia de 2
        int total = participantes.size();
        if ((total & (total - 1)) != 0) {
            throw new IllegalArgumentException("La cantidad de participantes debe ser potencia de 2");
        }

        // Dividir en dos lados
        ArrayList<T> ladoIzquierdo = new ArrayList<>(participantes.subList(0, total / 2));
        ArrayList<T> ladoDerecho = new ArrayList<>(participantes.subList(total / 2, total));

        // Generar rondas para cada lado
        List<List<Enfrentamiento>> rondasIzquierda = generarRondasPorLado(ladoIzquierdo, "IZQ");
        List<List<Enfrentamiento>> rondasDerecha = generarRondasPorLado(ladoDerecho, "DER");

        // Vamos a combinar los dos lados en un bracket final,
        // pero solo las rondas iniciales, la final la armamos aparte

        List<List<Enfrentamiento>> bracketCompleto = new ArrayList<>();

        // Aquí agregamos las rondas menos la final (última ronda) de cada lado alternando o concatenando
        // Mejor las juntamos por ronda: ronda 1 de cada lado, ronda 2 de cada lado, etc.

        int numRondas = rondasIzquierda.size();
        for (int i = 0; i < numRondas; i++) {
            List<Enfrentamiento> ronda = new ArrayList<>();
            ronda.addAll(rondasIzquierda.get(i));
            ronda.addAll(rondasDerecha.get(i));
            bracketCompleto.add(ronda);
        }

        // Finalmente, la ronda final: enfrentamos ganadores de la última ronda de cada lado
        List<Enfrentamiento> rondaFinal = new ArrayList<>();
        rondaFinal.add(new Enfrentamiento(
                new Equipo("Ganador lado IZQ", "TEMP", new ArrayList<>()),
                new Equipo("Ganador lado DER", "TEMP", new ArrayList<>())
        ));
        bracketCompleto.add(rondaFinal);

        return bracketCompleto;
    }

    private List<List<Enfrentamiento>> generarRondasPorLado(ArrayList<T> lado, String prefijoLado) {
        List<List<Enfrentamiento>> rondas = new ArrayList<>();
        ArrayList<T> participantesActuales = new ArrayList<>(lado);

        int rondaNum = 1;

        while (participantesActuales.size() > 1) {
            List<Enfrentamiento> ronda = new ArrayList<>();
            for (int i = 0; i < participantesActuales.size(); i += 2) {
                ronda.add(new Enfrentamiento(participantesActuales.get(i), participantesActuales.get(i + 1)));
            }
            rondas.add(ronda);

            // Preparar ganadores (placeholder null por ahora)
            int numGanadores = ronda.size();
            participantesActuales = new ArrayList<>();
            for (int i=0; i<numGanadores; i++){
                String nombreGanador = prefijoLado + " Ganador R" + rondaNum + "P" + (i+1);
                Equipo ganadorPlaceholder = new Equipo(nombreGanador, "TEMP", new ArrayList<>());
                participantesActuales.add((T) ganadorPlaceholder);
            }
            rondaNum++;
        }
        return rondas;
    }


    public ArrayList<Enfrentamiento> filtrarPorEquipo(String nombreEquipo) {
        ArrayList<Enfrentamiento> filtrados = new ArrayList<>();

        for (Enfrentamiento e : enfrentamientos) {
            if (e.getParticipante1().getNombre().equalsIgnoreCase(nombreEquipo) ||
                    e.getParticipante2().getNombre().equalsIgnoreCase(nombreEquipo)) {
                filtrados.add(e);
            }
        }
        return filtrados;
    }


    public void imprimirBracket(){
        if (rondasEliminatorias == null || rondasEliminatorias.isEmpty()){
            System.out.println("No se ha generado el bracket");
            return;
        }

        int rondaNum = 1;
        for (List<Enfrentamiento> ronda : rondasEliminatorias){
            System.out.println("=== Ronda "+ rondaNum++ + " ===");
            int numPartido = 1;
            for (Enfrentamiento e : ronda){
                String p1 = (e.getParticipante1() == null) ? "BYE" : e.getParticipante1().getNombre();
                String p2 = (e.getParticipante2() == null) ? "BYE" : e.getParticipante2().getNombre();
                System.out.println("Partido " + (numPartido++) + ": " + p1 + " vs " + p2);
            }
            System.out.println();
        }
    }

    public void limpiarCalendario(){
        enfrentamientos.clear();
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos(){
        return enfrentamientos;
    }

    public void crearYGuardarBracket(){
        this.rondasEliminatorias = generarBracket(this.participantes);
    }
}
