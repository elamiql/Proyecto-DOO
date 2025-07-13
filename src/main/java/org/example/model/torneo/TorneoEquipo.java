package org.example.model.torneo;

import org.example.enums.*;
import org.example.exceptions.FormatoInvalidoException;
import org.example.exceptions.ParticipantesInsuficientesException;
import org.example.interfaces.Disciplina;
import org.example.model.Formatos.Eliminatoria;
import org.example.model.Formatos.GruposEliminatoria;
import org.example.model.Formatos.Liga;
import org.example.model.Participante.Equipo;
import org.example.model.Participante.Jugador;

import java.util.ArrayList;

/**
 * Representa un torneo por equipos que puede utilizar diferentes formatos de competencia.
 * Esta clase extiende la clase genérica {@link Torneo} especializada para equipos ({@link Equipo}).
 * <p>
 * Permite crear torneos con diferentes configuraciones de número de grupos y cantidad de clasificados por grupo,
 * especialmente útil para formatos con fase de grupos y eliminación.
 */
public class TorneoEquipo extends Torneo<Equipo> {


    /**
     * Número de grupos en la fase de grupos.
     */
    private int numGrupos;

    /**
     * Número de equipos que clasifican por cada grupo.
     */
    private int clasificadosPorGrupo;

    /**
     * Constructor para crear un torneo de equipo con configuración estándar de 32 equipos,
     * 8 grupos y 2 clasificados por grupo (formato mundialista).
     * @param nombre    Nombre del torneo.
     * @param disciplina Disciplina o deporte del torneo.
     * @param fecha     Fecha del torneo.
     * @param formato   Formato de competencia (liga, eliminatoria, etc).
     * @param c         Cadena identificadora o código del torneo.
     */
    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato, String c) {
        super(nombre, disciplina, fecha, formato, c);
        this.numGrupos = 8;
        this.clasificadosPorGrupo = 2;
    }

    /**
     * Constructor para crear un torneo de equipo con configuración personalizada
     * del número de grupos y clasificados por grupo.
     * @param nombre             Nombre del torneo.
     * @param disciplina         Disciplina o deporte del torneo.
     * @param fecha              Fecha del torneo.
     * @param formato            Formato de competencia (liga, eliminatoria, etc).
     * @param numGrupos          Número de grupos en la fase de grupos.
     * @param clasificadosPorGrupo Número de equipos que clasifican por grupo.
     * @param c                  Cadena identificadora o código del torneo.
     */
    public TorneoEquipo(String nombre, Disciplina disciplina, String fecha, Formato formato,
                        int numGrupos, int clasificadosPorGrupo, String c) {
        super(nombre, disciplina, fecha, formato, c);
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
     * Obtiene el número de equipos que clasifican por cada grupo en la fase de grupos.
     * @return número de clasificados por grupo.
     */
    public int getClasificadosPorGrupo() {
        return clasificadosPorGrupo;
    }

    /**
     * Genera el calendario de enfrentamientos del torneo según el formato seleccionado.
     * Dependiendo del formato, se utiliza un generador específico para crear la estructura de partidos:
     * <p>
     * Liga: todos contra todos con doble vuelta.
     * </p>
     * Eliminatoria: llaves de eliminación directa.
     * <p>
     * Grupos con eliminatoria: fase de grupos seguida por fase eliminatoria con configuraciones personalizadas.
     * </p>
     * Lanza excepciones si los parámetros para el formato de grupos no son válidos
     * o si se selecciona un formato no soportado.
     *
     * @throws ParticipantesInsuficientesException si numGrupos o clasificadosPorGrupo son menores o iguales a cero.
     * @throws FormatoInvalidoException            si el formato seleccionado no es soportado.
     */
    @Override
    public void generarCalendario() {
        switch (getFormato()) {
            case LIGA -> {
                generadorActivo = new Liga<>(getParticipantes(), true);
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