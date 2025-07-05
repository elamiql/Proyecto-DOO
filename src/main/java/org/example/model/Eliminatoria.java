package org.example.model;

import org.example.exceptions.ParticipanteNullException;

import java.util.*;

/**
 * Clase que representa un generador de calendario para torneos en formato de eliminación directa.
 * Genera un bracket eliminatorio completo a partir de una lista de participantes.
 * Puede configurarse para requerir que el número de participantes sea potencia de dos.
 * @param <T> Tipo de participante (jugador o equipo) que extiende de {@link Participante}.
 */
public class Eliminatoria<T extends Participante> extends GenerarCalendario<T> {

    /** Lista de rondas, cada una con sus respectivos enfrentamientos, que conforman el bracket del torneo */
    private final List<List<Enfrentamiento>> bracket;

    /** Indica si el torneo requiere una cantidad de participantes igual a una potencia de 2 */
    private final boolean requierePotenciaDeDos;

    /**
     * Constructor que permite especificar si se requiere una cantidad de participantes que sea potencia de dos.
     * @param participantes Lista de participantes.
     * @param requierePotenciaDeDos {@code true} si se debe verificar que la cantidad sea potencia de dos.
     */
    public Eliminatoria(ArrayList<T> participantes, boolean requierePotenciaDeDos){
        super(participantes);
        this.requierePotenciaDeDos = requierePotenciaDeDos;
        this.bracket = new ArrayList<>();
    }

    /**
     * Constructor que permite crear una eliminatoria sin requerir que la cantidad de participantes sea potencia de dos.
     * @param participantes Lista de participantes.
     */
    public Eliminatoria(ArrayList<T> participantes){
        this(participantes, false);
    }

    /**
     * Valida la cantidad de participantes antes de generar el calendario.
     * Si {@code requierePotenciaDeDos} es true, lanza excepción si el número no es potencia de 2.
     * @throws IllegalArgumentException si la cantidad no es válida.
     */
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

    /**
     * Genera el calendario de enfrentamientos en forma de bracket eliminatorio.
     * Los participantes son emparejados en rondas sucesivas hasta quedar un solo ganador.
     */
    @Override
    protected void generarEnfrentamientos(){
        enfrentamientos.clear();
        bracket.clear();

        ArrayList<T> participantesMezclados = new ArrayList<>(participantes);
        generarBracketCompleto(participantesMezclados);
        rondasEliminatorias = bracket;
    }

    /**
     * Genera el bracket completo a partir de los participantes dados.
     * Crea enfrentamientos y placeholders para las siguientes rondas.
     * @param participantes Lista inicial de participantes.
     * @throws ParticipanteNullException si se intenta enfrentar un jugador con un equipo.
     */
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

    /**
     * Imprime el bracket del torneo por consola, mostrando enfrentamientos por ronda.
     */
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

    /**
     * Devuelve la estructura del bracket completo generado.
     * @return Lista de rondas, cada una con sus enfrentamientos.
     */
    public List<List<Enfrentamiento>> getBracket() {
        return bracket;
    }

    /**
     * Devuelve la lista de rondas eliminatorias (bracket).
     * @return Lista de rondas eliminatorias.
     */
    public List<List<Enfrentamiento>> getRondas() {
        return rondasEliminatorias;
    }
}
