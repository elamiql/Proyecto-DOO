package org.example.model;

import org.example.interfaces.Resultado;

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
    private Participante p1;

    /**
     * Participante visitante.
     */
    private Participante p2;

    /**
     * Constructor que inicializa un resultado de fútbol con los participantes y los goles anotados.
     * @param p1 participante local
     * @param p2 participante visitante
     * @param golesLocal goles anotados por el participante local (no negativo)
     * @param golesVisitante goles anotados por el participante visitante (no negativo)
     */
    public ResultadoFutbol(Participante p1, Participante p2, int golesLocal, int golesVisitante){
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Obtiene un resumen en formato texto del resultado.
     * @return cadena con el formato "NombreLocal golesLocal - golesVisitante NombreVisitante"
     */
    @Override
    public String getResumen() {
        return p1.getNombre() + " " + golesLocal + " - " + golesVisitante + " " + p2.getNombre();
    }

    /**
     * Verifica que el resultado sea válido (los goles no pueden ser negativos).
     * @return true si ambos goles son mayores o iguales a cero, false en caso contrario.
     */
    @Override
    public boolean esValido() {
        return golesLocal >= 0 && golesVisitante >=0;
    }

    /**
     * Obtiene el participante ganador según la cantidad de goles anotados.
     * @return participante ganador, o null si el resultado fue empate.
     */
    @Override
    public Participante getGanador(){
        if (golesLocal > golesVisitante){
            return p1;
        }
        else if (golesVisitante > golesLocal){
            return p2;
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
        return p1;
    }

    /**
     * Obtiene el participante visitante.
     * @return participante visitante.
     */
    public Participante getVisitante() {
        return p2;
    }
}
