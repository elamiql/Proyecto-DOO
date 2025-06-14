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
            return;
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

    private void generarEliminatoria(){
        enfrentamientos.clear();
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

    public List<List<Enfrentamiento>> generarBracket(ArrayList<T> participantes) {
        // Validamos que sea potencia de 2
        int total = participantes.size();
        if ((total & (total - 1)) != 0) {
            throw new IllegalArgumentException("La cantidad de participantes debe ser potencia de 2");
        }

        // Dividir en dos lados
        ArrayList<T> ladoIzquierdo = new ArrayList<>(participantes.subList(0, total / 2));
        ArrayList<T> ladoDerecho = new ArrayList<>(participantes.subList(total / 2, total));

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

            // Preparar ganadores
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

    public void generarGrupoYEliminatoria(int numGrupos, int clasificadosPorGrupo) {
        limpiarCalendario();
        if (participantes.size() < numGrupos * clasificadosPorGrupo) {
            System.out.println("No hay suficientes equipos");
            return;
        }

        List<List<T>> grupos = new ArrayList<>();
        for (int i = 0; i < numGrupos; i++) {
            grupos.add(new ArrayList<>());
        }

        for (int i = 0; i < participantes.size(); i++) {
            grupos.get(i / (participantes.size() / numGrupos)).add(participantes.get(i));
        }

        List<Enfrentamiento> enfrentamientoGrupos = new ArrayList<>();
        for (List<T> grupo : grupos) {
            for (int i = 0; i < grupo.size(); i++) {
                for (int j = i + 1; j < grupo.size(); j++) {
                    Enfrentamiento e = new Enfrentamiento(grupo.get(i), grupo.get(j));
                    enfrentamientoGrupos.add(e);
                }
            }
        }

        this.enfrentamientos = new ArrayList<>(enfrentamientoGrupos);

        List<List<T>> clasificadosPorGrupoList = new ArrayList<>();
        for (List<T> grupo : grupos) {
            List<T> clasificados = new ArrayList<>();
            for (int i = 0; i < clasificadosPorGrupo; i++) {
                clasificados.add(grupo.get(i));
            }
            clasificadosPorGrupoList.add(clasificados);
        }

        List<T> primeros = new ArrayList<>();
        List<T> segundos = new ArrayList<>();

        for (List<T> grupoClasificado : clasificadosPorGrupoList) {
            if (grupoClasificado.size() >= 2) {
                primeros.add(grupoClasificado.get(0));
                segundos.add(grupoClasificado.get(1));
            }
        }

        List<Enfrentamiento> octavos = new ArrayList<>();
        for (int i = 0; i < primeros.size(); i += 2) {
            if (i + 1 < segundos.size()) {
                octavos.add(new Enfrentamiento(primeros.get(i), segundos.get(i + 1)));
                octavos.add(new Enfrentamiento(primeros.get(i + 1), segundos.get(i)));
            }
        }

        // Continuar bracket desde octavos
        this.rondasEliminatorias = new ArrayList<>();
        this.rondasEliminatorias.add(octavos);

        List<T> ganadoresPlaceholder = new ArrayList<>();
        for (int i = 0; i < octavos.size(); i++) {
            Equipo placeholder = new Equipo("Ganador Octavos P" + (i + 1), "TEMP", new ArrayList<>());
            ganadoresPlaceholder.add((T) placeholder);
        }

        // Crear siguientes rondas automáticamente
        List<List<Enfrentamiento>> siguientesRondas = generarRondasPorLado(new ArrayList<>(ganadoresPlaceholder), "");
        this.rondasEliminatorias.addAll(siguientesRondas);

        if (this.rondasEliminatorias.size() >= 2) {
            List<Enfrentamiento> semifinales = this.rondasEliminatorias.get(this.rondasEliminatorias.size() - 2);
            if (semifinales.size() == 2) {
                // Los "perdedores" son los placeholders de participante1 de cada semifinal
                Participante perdedor1 = semifinales.get(0).getParticipante1();
                Participante perdedor2 = semifinales.get(1).getParticipante1();

                Enfrentamiento partidoTercerLugar = new Enfrentamiento(perdedor1, perdedor2);
                List<Enfrentamiento> rondaTercerLugar = new ArrayList<>();
                rondaTercerLugar.add(partidoTercerLugar);

                // Insertar el partido de tercer lugar antes de la final (última ronda)
                this.rondasEliminatorias.add(this.rondasEliminatorias.size() - 1, rondaTercerLugar);
            }
        }
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

    public void limpiarCalendario(){
        enfrentamientos.clear();
        if (rondasEliminatorias!=null){
            rondasEliminatorias.clear();
        }
    }

    public ArrayList<Enfrentamiento> getEnfrentamientos(){
        return enfrentamientos;
    }

    public void crearYGuardarBracket(){
        this.rondasEliminatorias = generarBracket(this.participantes);
    }

    public List<List<Enfrentamiento>> getRondasEliminatorias() {
        return rondasEliminatorias;
    }
}