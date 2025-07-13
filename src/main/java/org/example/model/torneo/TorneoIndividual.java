package org.example.model.torneo;

import org.example.enums.*;
import org.example.exceptions.*;
import org.example.interfaces.Disciplina;
import org.example.model.Formatos.Eliminatoria;
import org.example.model.Formatos.GruposEliminatoria;
import org.example.model.Formatos.Liga;
import org.example.model.Participante.Jugador;

/**
 * Representa un torneo individual para jugadores que puede tener distintos formatos.
 * Esta clase extiende la clase genérica {@link Torneo} especializada para participantes del tipo {@link Jugador}.
 * <p>
 * Permite configurar torneos individuales con o sin fase de grupos y elimina participantes insuficientes.
 * </p>
 *
 * <p>
 * Soporta los formatos:
 * <p>
 * Liga
 * </p>
 * Eliminatoria
 * <p>
 * Grupos con eliminatoria
 * </p>
 * </p>
 */
public class TorneoIndividual extends Torneo<Jugador> {

    /**
     * Número de grupos en la fase de grupos.
     */
    private int numGrupos;

    /**
     * Número de jugadores que clasifican por grupo.
     */
    private int clasificadosPorGrupo;



    /**
     * Constructor para torneo individual sin configurar grupos/clasificados.
     *
     * @param nombre     Nombre del torneo.
     * @param disciplina Disciplina o deporte del torneo.
     * @param fecha      Fecha del torneo.
     * @param formato    Formato de competencia (liga, eliminatoria, etc).
     * @param c          Código o identificador del torneo.
     */
    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato, String c) {
        super(nombre, disciplina, fecha, formato, c);
    }

    /**
     * Constructor para torneo individual con configuración personalizada de grupos y clasificados por grupo.
     *
     * @param nombre             Nombre del torneo.
     * @param disciplina         Disciplina o deporte del torneo.
     * @param fecha              Fecha del torneo.
     * @param formato            Formato de competencia.
     * @param numGrupos          Número de grupos en la fase de grupos.
     * @param clasificadosPorGrupo Número de jugadores que clasifican por grupo.
     * @param c                  Código o identificador del torneo.
     */
    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato,
                        int numGrupos, int clasificadosPorGrupo, String c) {
        super(nombre, disciplina, fecha, formato, c);
        this.numGrupos = numGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
    }

    /**
     * Genera el calendario de enfrentamientos del torneo según el formato seleccionado.
     * <p>
     * Valida que haya al menos 2 participantes para poder iniciar el torneo.
     * </p>
     * <p>
     * Dependiendo del formato, se utiliza un generador específico para crear la estructura de partidos:
     * <p>
     * Liga: todos contra todos con doble vuelta.
     * </p>
     * Eliminatoria: llaves de eliminación directa.
     * <p>
     * Grupos con eliminatoria: fase de grupos seguida por fase eliminatoria con configuraciones personalizadas.
     * </p>
     * </p>
     * <p>
     * Lanza excepciones si no hay suficientes participantes o si el formato no es soportado.
     * </p>
     *
     * @throws ParticipantesInsuficientesException si hay menos de 2 participantes.
     * @throws FormatoInvalidoException            si el formato seleccionado no es soportado.
     */
    @Override
    public void generarCalendario() {
        if (participantes.size() < 2){
            throw new ParticipantesInsuficientesException("Se requieren 2 participantes");
        }

        switch (getFormato()) {
            case LIGA:
                generadorActivo = new Liga<>(getParticipantes(), true);
                break;

            case ELIMINATORIA:
                generadorActivo = new Eliminatoria<>(getParticipantes(), true);
                break;

            case GRUPOS_CON_ELIMINATORIA:
                generadorActivo = new GruposEliminatoria<>(getParticipantes(), numGrupos, clasificadosPorGrupo);
                break;

            default:
                throw new FormatoInvalidoException("Formato no incluido / soportado");
        }

        generadorActivo.generarCalendario();
        this.enfrentamientos = generadorActivo.getEnfrentamientos();
        generadorActivo.imprimirCalendario();

    }


}