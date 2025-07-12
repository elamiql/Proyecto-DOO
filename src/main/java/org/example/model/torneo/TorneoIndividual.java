package org.example.model.torneo;

import org.example.model.Formatos.*;
import org.example.model.Participante.*;
import org.example.model.Estadisticas.*;
import org.example.model.Enfrentamientos.*;
import org.example.model.Resultado.*;
import org.example.enums.*;
import org.example.interfaces.*;
import org.example.exceptions.*;

import java.util.ArrayList;

/**
 * Representa un torneo individual para jugadores que puede tener distintos formatos.
 * Esta clase extiende la clase genérica {@link Torneo} especializada para participantes del tipo {@link Jugador}.
 * <p>
 * Permite configurar torneos individuales con o sin fase de grupos y elimina participantes insuficientes.
 * </p>
 * <p>
 * Soporta los formatos:
 * <ul>
 * <li>Liga: todos contra todos con doble vuelta</li>
 * <li>Eliminatoria: llaves de eliminación directa</li>
 * <li>Grupos con eliminatoria: fase de grupos seguida por fase eliminatoria</li>
 * </ul>
 * </p>
 *
 * @see Torneo
 * @see Jugador
 * @see org.example.enums.Formato
 * @see org.example.interfaces.Disciplina
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
     * Constructor para torneo individual con configuración estándar.
     * Inicializa con valores por defecto de 8 grupos y 2 clasificados por grupo.
     *
     * @param nombre     Nombre del torneo.
     * @param disciplina Disciplina o deporte del torneo.
     * @param fecha      Fecha del torneo en formato "dd-MM-yyyy HH:mm".
     * @param formato    Formato de competencia (liga, eliminatoria, etc).
     * @param contraseña Contraseña de acceso o inscripción al torneo.
     */
    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato, String contraseña) {
        super(nombre, disciplina, fecha, formato, contraseña);
        this.numGrupos = 8;
        this.clasificadosPorGrupo = 2;
    }

    /**
     * Constructor para torneo individual con configuración personalizada de grupos y clasificados por grupo.
     *
     * @param nombre                Nombre del torneo.
     * @param disciplina            Disciplina o deporte del torneo.
     * @param fecha                 Fecha del torneo en formato "dd-MM-yyyy HH:mm".
     * @param formato               Formato de competencia.
     * @param numGrupos             Número de grupos en la fase de grupos.
     * @param clasificadosPorGrupo  Número de jugadores que clasifican por grupo.
     * @param contraseña            Contraseña de acceso o inscripción al torneo.
     */
    public TorneoIndividual(String nombre, Disciplina disciplina, String fecha, Formato formato,
                            int numGrupos, int clasificadosPorGrupo, String contraseña) {
        super(nombre, disciplina, fecha, formato, contraseña);
        this.numGrupos = numGrupos;
        this.clasificadosPorGrupo = clasificadosPorGrupo;
    }

    /**
     * Obtiene el número de grupos configurados para la fase de grupos.
     * @return número de grupos.
     */
    public int getNumGrupos() {
        return numGrupos;
    }

    /**
     * Obtiene el número de jugadores que clasifican por cada grupo en la fase de grupos.
     * @return número de clasificados por grupo.
     */
    public int getClasificadosPorGrupo() {
        return clasificadosPorGrupo;
    }

    /**
     * Genera el calendario de enfrentamientos del torneo según el formato seleccionado.
     * <p>
     * Valida que haya al menos 2 participantes para poder iniciar el torneo.
     * </p>
     * <p>
     * Dependiendo del formato, se utiliza un generador específico para crear la estructura de partidos:
     * <ul>
     * <li>Liga: todos contra todos con doble vuelta</li>
     * <li>Eliminatoria: llaves de eliminación directa</li>
     * <li>Grupos con eliminatoria: fase de grupos seguida por fase eliminatoria con configuraciones personalizadas</li>
     * </ul>
     * </p>
     * <p>
     * Lanza excepciones si no hay suficientes participantes, si los parámetros para el formato de grupos
     * no son válidos o si el formato no es soportado.
     * </p>
     *
     * @throws ParticipantesInsuficientesException si hay menos de 2 participantes o si numGrupos
     *                                            o clasificadosPorGrupo son menores o iguales a cero.
     * @throws FormatoInvalidoException           si el formato seleccionado no es soportado.
     */
    @Override
    public void generarCalendario() {
        // Validar que hay suficientes participantes
        if (getParticipantes().size() < 2) {
            throw new ParticipantesInsuficientesException("No se puede generar calendario: menos de 2 participantes");
        }

        switch (getFormato()) {
            case LIGA -> {
                EstadisticasTenis e = new EstadisticasTenis();
                generadorActivo = new Liga<>(getParticipantes(), true, e);
            }
            case ELIMINATORIA -> {
                generadorActivo = new Eliminatoria<>(getParticipantes(), true);
            }
            case GRUPOS_CON_ELIMINATORIA -> {
                if (numGrupos <= 0 || clasificadosPorGrupo <= 0) {
                    throw new ParticipantesInsuficientesException("Error: numGrupos y clasificadosPorGrupo deben ser mayores que 0");
                }
                generadorActivo = new GruposEliminatoria<>(getParticipantes(), numGrupos, clasificadosPorGrupo);
            }
            default -> throw new FormatoInvalidoException("Formato no soportado aún");
        }

        generadorActivo.generarCalendario();
        this.enfrentamientos = generadorActivo.getEnfrentamientos();
    }
}