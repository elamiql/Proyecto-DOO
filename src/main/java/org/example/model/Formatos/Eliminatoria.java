package org.example.model.Formatos;

import org.example.exceptions.ParticipanteNullException;
import org.example.interfaces.Disciplina;
import org.example.interfaces.Estadisticas;
import org.example.interfaces.Resultado;
import org.example.model.Enfrentamientos.Enfrentamiento;
import org.example.model.Enfrentamientos.GenerarCalendario;
import org.example.model.Estadisticas.*;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;
import org.example.model.Participante.Participante;
import java.util.*;


public class Eliminatoria<T extends Participante, E extends Estadisticas<T, R>, R extends Resultado> extends GenerarCalendario<T> {

    private final List<List<Enfrentamiento>> bracket;
    private final boolean requierePotenciaDeDos;
    private final Map<T, E> tablaEstadisticas;
    private Disciplina disciplina;

    public Eliminatoria(ArrayList<T> participantes, boolean requierePotenciaDeDos,Disciplina disciplina) {
        super(participantes);
        this.requierePotenciaDeDos = requierePotenciaDeDos;
        this.bracket = new ArrayList<>();
        this.tablaEstadisticas = new HashMap<>();
        this.disciplina=disciplina;

        for (T participante : participantes) {
            tablaEstadisticas.put(participante, crearEstadistica(participante));
        }
    }

    public Eliminatoria(ArrayList<T> participantes,Disciplina disciplina) {
        this(participantes, false,disciplina);
    }

    private E crearEstadistica(T participante) {

        switch (disciplina.getNombre()) {
            case "FUTBOL":
                return (E) new EstadisticasFutbol(participante);
            case "TENIS":
                return (E) new EstadisticasTenis(participante);
            case "TENIS_DE_MESA":
                return (E) new EstadisticaTenisDeMesa(participante);
            case "AJEDREZ":
                return (E) new EstadisticasAjedrez(participante);
            case "FIFA":
                return (E) new EstadisticasFifa(participante);
            case "LOL":
                return (E) new EstadisticasLol(participante);
            default:
                throw new UnsupportedOperationException("Disciplina no soportada: " + disciplina);
        }
    }

    @Override
    protected void validarParticipantes(){
        super.validarParticipantes();
        if (requierePotenciaDeDos){
            int total = participantes.size();
            if ((total & (total - 1)) != 0){
                throw new IllegalArgumentException("La cantidad de participantes debe ser potencia de 2");
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
                    throw new ParticipanteNullException("No se puede enfrentar un jugador con un equipo: "
                            + p1.getClass() + " vs " + p2.getClass());
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

    public Map<T, E> getTablaEstadisticas() {
        return tablaEstadisticas;
    }

    public void actualizarEstadisticasDesdeResultados() {

        for (E estadistica : tablaEstadisticas.values()) {
            estadistica.reiniciarEstadisticas();
        }

        for (Enfrentamiento enf : enfrentamientos) {
                Resultado r = enf.getResultado();
                System.out.println("Resultado del enfrentamiento: " + r);

            if (r != null && getResultadoClass().isInstance(r)) {
                R resultado = (R) r;
                T p1 = (T) enf.getParticipante1();
                T p2 = (T) enf.getParticipante2();

                if (tablaEstadisticas.containsKey(p1)) {
                    tablaEstadisticas.get(p1).registrarResultado(resultado, p1, true);
                }
                if (tablaEstadisticas.containsKey(p2)) {
                    tablaEstadisticas.get(p2).registrarResultado(resultado, p2, false);
                }
            }
        }
    }



    protected Class<?> getResultadoClass() {
        return Resultado.class;
    }
}
