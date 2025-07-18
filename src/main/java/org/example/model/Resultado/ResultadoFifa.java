package org.example.model.Resultado;

import org.example.interfaces.Resultado;
import org.example.model.Participante.Participante;

/**
 * Representa el resultado de un partido de fútbol bajo reglas similares a FIFA.
 * Implementa la interfaz {@link Resultado}.
 *
 * Contiene información de los goles anotados por el equipo local y visitante,
 * así como los participantes correspondientes.
 *
 * Valida que los goles no sean negativos y que los participantes no sean nulos.
 *
 * @see Resultado
 * @see Participante
 */
public class ResultadoFifa implements Resultado {

    /**
     * Goles anotados por el equipo local.
     */
    private int golesLocal;

    /**
     * Goles anotados por el equipo visitante.
     */
    private int golesVisitante;

    /**
     * Participante que actúa como equipo local.
     */
    private Participante local;

    /**
     * Participante que actúa como equipo visitante.
     */
    private Participante visitante;
    private Participante ganador;

    /**
     * Constructor que crea un resultado de partido FIFA.
     *
     * @param local        Participante local.
     * @param visitante    Participante visitante.
     * @param golesLocal   Goles anotados por el equipo local (no negativos).
     * @param golesVisitante Goles anotados por el equipo visitante (no negativos).
     * @param ganador       Ganador del encuentro
     * @throws IllegalArgumentException si los goles son negativos.
     */
    public ResultadoFifa(Participante local, Participante visitante, int golesLocal, int golesVisitante,Participante ganador) {
        if (golesLocal < 0 || golesVisitante < 0) {
            throw new IllegalArgumentException("Los goles no pueden ser negativos");
        }
        this.local = local;
        this.visitante = visitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.ganador=ganador;
    }

    /**
     * Devuelve la cantidad de goles anotados por el equipo local.
     * @return goles del equipo local.
     */
    public int getGolesLocal() {
        return golesLocal;
    }

    /**
     * Devuelve la cantidad de goles anotados por el equipo visitante.
     * @return goles del equipo visitante.
     */
    public int getGolesVisitante() {
        return golesVisitante;
    }

    /**
     * Determina el ganador del partido según los goles anotados.
     * @return Participante ganador o {@code null} si hay empate.
     */
    @Override
    public Participante getGanador() {
        if (golesLocal > golesVisitante) return local;
        else if (golesVisitante > golesLocal) return visitante;
        else return null; // Empate
    }

    /**
     * Valida que el resultado sea consistente.
     * @return {@code true} si los goles son coherentes con el ganador, {@code false} en caso contrario.
     */
    @Override
    public boolean esValido() {

        if (golesLocal < 0 || golesVisitante < 0) {
            return false;
        }




        if (ganador != null) {
            if (golesLocal > golesVisitante && !ganador.equals(local)) {
                return false;
            }
            if (golesVisitante > golesLocal && !ganador.equals(visitante)) {
                return false;
            }
            if (golesLocal == golesVisitante) {
                return false;
            }
        } else {
            if (golesLocal != golesVisitante) {
                return false;
            }
        }

        return true;
    }

    /**
     * Devuelve un resumen del resultado en formato "Local GolesLocal - GolesVisitante Visitante".
     * @return resumen del resultado.
     */
    @Override
    public String getResumen() {
        return String.format("%s %d - %d %s", local.toString(), golesLocal, golesVisitante, visitante.toString());
    }
}
