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

/**
 * Clase que implementa el formato de torneo por eliminación directa.
 *
 * <p>Esta clase extiende {@link GenerarCalendario} y organiza a los participantes
 * en un sistema de rondas eliminatorias, donde los perdedores quedan
 * fuera en cada ronda.</p>
 *
 * @param <T> tipo de participante
 * @param <E> tipo de estadísticas asociadas a los participantes
 * @param <R> tipo de resultado usado en los enfrentamientos
 */
public class Eliminatoria<T extends Participante, E extends Estadisticas<T, R>, R extends Resultado> extends GenerarCalendario<T> {

    private final List<List<Enfrentamiento>> bracket;
    private final boolean requierePotenciaDeDos;
    private final Map<T, E> tablaEstadisticas;
    private Disciplina disciplina;

    /**
     * Constructor principal para el formato eliminatorio.
     *
     * @param participantes lista de participantes
     * @param requierePotenciaDeDos indica si la cantidad de participantes debe ser potencia de 2
     * @param disciplina disciplina del torneo (usada para generar estadísticas)
     */
    public Eliminatoria(ArrayList<T> participantes, boolean requierePotenciaDeDos, Disciplina disciplina) {
        super(participantes);
        this.requierePotenciaDeDos = requierePotenciaDeDos;
        this.bracket = new ArrayList<>();
        this.tablaEstadisticas = new HashMap<>();
        this.disciplina=disciplina;

        for (T participante : participantes) {
            tablaEstadisticas.put(participante, crearEstadistica(participante));
        }
    }

    /**
     * Constructor por defecto sin requerimiento de potencia de dos.
     *
     * @param participantes lista de participantes
     * @param disciplina disciplina del torneo
     */
    public Eliminatoria(ArrayList<T> participantes, Disciplina disciplina) {
        this(participantes, false, disciplina);
    }

    /**
     * Crea la instancia de estadísticas adecuada según la disciplina del torneo.
     *
     * @param participante el participante al que se asignarán las estadísticas
     * @return instancia de estadísticas correspondiente a la disciplina
     * @throws UnsupportedOperationException si la disciplina no está soportada
     */
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

    /**
     * Valida que los participantes cumplan con las condiciones necesarias.
     * Verifica que la cantidad sea potencia de dos si se requiere.
     *
     * @throws IllegalArgumentException si la cantidad no es válida
     */
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

    /**
     * Genera todos los enfrentamientos del torneo en formato de eliminación directa,
     * construyendo el bracket completo.
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
     * Genera el árbol de eliminatorias (bracket) completo a partir de los participantes.
     * Se insertan placeholders para los ganadores en rondas futuras.
     *
     * @param participantes lista de participantes para la primera ronda
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

    /**
     * Imprime por consola el bracket generado con rondas y enfrentamientos.
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
     * Devuelve el bracket (lista de rondas de enfrentamientos).
     * @return lista de rondas
     */
    public List<List<Enfrentamiento>> getBracket() {
        return bracket;
    }

    /**
     * Devuelve el mapa de estadísticas por participante.
     * @return mapa de estadísticas
     */
    public Map<T, E> getTablaEstadisticas() {
        return tablaEstadisticas;
    }

    /**
     * Actualiza las estadísticas de todos los participantes a partir de los resultados
     * actuales de los enfrentamientos.
     * Reinicia previamente las estadísticas.
     */
    public void actualizarEstadisticasDesdeResultados() {
        for (E estadistica : tablaEstadisticas.values()) {
            estadistica.reiniciarEstadisticas();
        }

        for (Enfrentamiento enf : enfrentamientos) {
            Resultado r = enf.getResultado();
            System.out.println("Resultado del enfrentamiento: " + r);

            if (r != null && getResultadoClass().isInstance(r)) {
                R resultado = (R) r;
                Participante p1 = enf.getParticipante1();
                Participante p2 = enf.getParticipante2();

                if (tablaEstadisticas.containsKey(p1)) {
                    tablaEstadisticas.get(p1).registrarResultado(resultado, p1, true);
                }
                if (tablaEstadisticas.containsKey(p2)) {
                    tablaEstadisticas.get(p2).registrarResultado(resultado, p2, false);
                }
            }
        }
    }

    /**
     * Devuelve la clase esperada para los resultados. Por defecto, {@link Resultado}.
     * @return clase del tipo de resultado
     */
    protected Class<?> getResultadoClass() {
        return Resultado.class;
    }

    public List<List<Enfrentamiento>> getRondas(){
        return bracket;
    }

}
