package org.example.model.Resultado;

import org.example.interfaces.Resultado;
import org.example.model.Participante.Participante;

/**
 * Representa el resultado de un partido de fútbol entre dos participantes.
 * Contiene la cantidad de goles de cada participante y determina el ganador o si hubo empate.
 */
public class ResultadoFutbol implements Resultado {

    /**
     * Goles anotados por el participante local.
     */
    private int golesLocal;

    /**
     * Goles anotados por el participante visitante.
     */
    private int golesVisitante;

    /**
     * Participante local.
     */
    private Participante local;

    /**
     * Participante visitante.
     */
    private Participante visitante;

    private Participante ganador;

    /**
     * Constructor que inicializa un resultado de fútbol con los participantes y los goles anotados.
     * @param local participante local
     * @param visitante participante visitante
     * @param golesLocal goles anotados por el participante local (no negativo)
     * @param golesVisitante goles anotados por el participante visitante (no negativo)
     * @param ganador       Ganador del encuentro
     */
    public ResultadoFutbol(Participante local, Participante visitante, int golesLocal, int golesVisitante,Participante ganador){
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.local = local;
        this.visitante = visitante;
        this.ganador=ganador;
    }

    /**
     * Obtiene un resumen en formato texto del resultado.
     * @return cadena con el formato "NombreLocal golesLocal - golesVisitante NombreVisitante"
     */
    @Override
    public String getResumen() {
        return local.getNombre() + " " + golesLocal + " - " + golesVisitante + " " + visitante.getNombre();
    }

    /**
     * Verifica que el resultado sea válido .
     * @return true si los goles son coherentes con el ganador del partido.
     */
    @Override
    public boolean esValido() {
        // Los goles no pueden ser negativos
        if (golesLocal < 0 || golesVisitante < 0) {
            return false;
        }

        // Validar que los goles sean números razonables (opcional)
        if (golesLocal > 50 || golesVisitante > 50) {
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

    public boolean esValidoLiga(Participante ganador) {
        // Los goles no pueden ser negativos
        if (golesLocal < 0 || golesVisitante < 0) {
            return false;
        }

        // Validar que los goles sean números razonables (opcional)
        if (golesLocal > 50 || golesVisitante > 50) {
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
     * Obtiene el participante ganador según la cantidad de goles anotados.
     * @return participante ganador, o null si el resultado fue empate.
     */
    @Override
    public Participante getGanador(){
        if (golesLocal > golesVisitante){
            return local;
        }
        else if (golesVisitante > golesLocal){
            return visitante;
        }
        else{
            return null; //empate
        }
    }

    /**
     * Obtiene los goles anotados por el participante local.
     * @return goles del participante local.
     */
    public int getGolesLocal() {
        return golesLocal;
    }

    /**
     * Obtiene los goles anotados por el participante visitante.
     * @return goles del participante visitante.
     */
    public int getGolesVisitante() {
        return golesVisitante;
    }

    /**
     * Obtiene el participante local.
     * @return participante local.
     */
    public Participante getLocal() {
        return local;
    }

    /**
     * Obtiene el participante visitante.
     * @return participante visitante.
     */
    public Participante getVisitante() {
        return visitante;
    }

}
