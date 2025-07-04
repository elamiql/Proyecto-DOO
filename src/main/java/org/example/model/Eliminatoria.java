package org.example.model;

import org.example.exceptions.ParticipanteNullException;

import java.util.*;

public class Eliminatoria<T extends Participante> extends GenerarCalendario<T> {

    private final List<List<Enfrentamiento>> bracket;
    private final boolean requierePotenciaDeDos;

    public Eliminatoria(ArrayList<T> participantes, boolean requierePotenciaDeDos){
        super(participantes);
        this.requierePotenciaDeDos = requierePotenciaDeDos;
        this.bracket = new ArrayList<>();
    }

    public Eliminatoria(ArrayList<T> participantes){
        this(participantes, false);
    }

    @Override
    protected void validarParticipantes(){
        super.validarParticipantes();

        if (requierePotenciaDeDos){
            int total = participantes.size();
            if ((total & (total - 1)) != 0){
                throw new IllegalArgumentException("Para una eliminatoria se requiere que el total sea potencia de 2");
            }
        }
    }

    @Override
    protected void generarEnfrentamientos(){
        enfrentamientos.clear();
        bracket.clear();

        ArrayList<T> participantesMezclados = new ArrayList<>(participantes);
        generarBracketCompleto(participantesMezclados);
        rondasEliminatorias = bracket;
    }

    private void generarBracketCompleto(ArrayList<T> participantes){
        ArrayList<T> rondaActual = new ArrayList<>(participantes);

        while (rondaActual.size() > 1){
            List<Enfrentamiento> enfrentamientosRonda = new ArrayList<>();
            ArrayList<T> sgteRonda = new ArrayList<>();

            for (int i = 0; i < rondaActual.size() - 1; i += 2) {
                T p1 = rondaActual.get(i);
                T p2 = rondaActual.get(i + 1);

                Enfrentamiento enfrentamiento = new Enfrentamiento(p1, p2);
                enfrentamientosRonda.add(enfrentamiento);
                enfrentamientos.add(enfrentamiento);

                String nombreGanador = "Ganador " + p1.getNombre() + " vs " + p2.getNombre();
                Participante placeholder;

                if (p1 instanceof Jugador && p2 instanceof Jugador) {
                    placeholder = new Jugador(nombreGanador, "-1");
                } else if (p1 instanceof Equipo && p2 instanceof Equipo) {
                    placeholder = new Equipo(nombreGanador, "-1", new ArrayList<>());
                } else {
                    throw new ParticipanteNullException("No se puede enfrentar un jugador con un equipo "
                            + p1.getClass() + " " + p2.getClass());
                }

                sgteRonda.add((T) placeholder);
            }

            if (rondaActual.size() % 2 != 0) {
                T participanteLibre = rondaActual.get(rondaActual.size() - 1);
                sgteRonda.add(participanteLibre);
                System.out.println("[BYE] " + participanteLibre.getNombre() + " avanza automáticamente");
            }

            bracket.add(enfrentamientosRonda);
            rondaActual = sgteRonda;
        }
    }

    public void imprimirBracket() {
        System.out.println("=== Bracket Eliminatorio ===");

        for (int i = 0; i < bracket.size(); i++) {
            System.out.println("\n--- Ronda " + (i + 1) + " ---");
            List<Enfrentamiento> ronda = bracket.get(i);

            for (int j = 0; j < ronda.size(); j++) {
                Enfrentamiento e = ronda.get(j);
                String p1 = (e.getParticipante1() == null) ? "BYE" : e.getParticipante1().getNombre();
                String p2 = (e.getParticipante2() == null) ? "BYE" : e.getParticipante2().getNombre();
                System.out.println("  Partido " + (j + 1) + ": " + p1 + " vs " + p2);
            }
        }
    }

    public List<List<Enfrentamiento>> getBracket() {
        return bracket;
    }

    public List<List<Enfrentamiento>> getRondas() {
        return rondasEliminatorias;
    }
}
