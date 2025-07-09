package org.example.model.Enfrentamientos;

import org.example.exceptions.ParticipanteNullException;
import org.example.interfaces.Resultado;
import org.example.model.Participante.Participante;

/**
 * Clase que representa un enfrentamiento entre dos participantes dentro de un torneo.
 * Permite registrar resultados y determinar el ganador.
 */
public class Enfrentamiento {

    /** Primer participante del enfrentamiento */
    private Participante participante1;

    /** Segundo participante del enfrentamiento */
    private Participante participante2;

    /** Indica si el enfrentamiento ha finalizado */
    private boolean finalizado;

    /** Participante ganador (null en caso de empate) */
    private Participante ganador;

    /** Resultado del enfrentamiento */
    private Resultado resultado;

    /**
     * Constructor del enfrentamiento entre dos participantes.
     * Verifica que ambos sean del mismo tipo (jugador o equipo) y no nulos.
     * @param p1 Primer participante.
     * @param p2 Segundo participante.
     * @throws ParticipanteNullException si algún participante es null o si los tipos no coinciden.
     */
    public Enfrentamiento(Participante p1, Participante p2){

        if (p1 == null || p2 == null){
            throw new ParticipanteNullException("Los participantes no pueden ser null");
        }
        if (!p1.getClass().equals(p2.getClass())){
            throw new ParticipanteNullException("No se puede enfrentar un jugador con un equipo " + p1.getClass() + " " + p2.getClass());
        }

        this.participante1 = p1;
        this.participante2 = p2;
        this.finalizado = false;
    }

    /**
     * Registra el resultado del enfrentamiento. Marca el enfrentamiento como finalizado.
     * @param resultado Resultado del enfrentamiento.
     * @throws IllegalArgumentException si el resultado no es válido o el ganador no corresponde.
     */
    public void registrarResultado(Resultado resultado){
        if (!resultado.esValido()){
            throw new IllegalArgumentException("Resultado no valido");
        }
        this.resultado = resultado;

        Participante ganador = resultado.getGanador();
        if (ganador != null){
            setGanador(ganador);
        }
        else{
            this.finalizado = true;
            this.ganador = null;
        }
    }

    /**
     * Devuelve el resultado registrado.
     * @return Resultado del enfrentamiento.
     */
    public Resultado getResultado(){
        return resultado;
    }

    /**
     * Devuelve el primer participante.
     * @return Participante 1.
     */
    public Participante getParticipante1(){
        return participante1;
    }

    /**
     * Devuelve el segundo participante.
     * @return Participante 2.
     */
    public Participante getParticipante2(){
        return participante2;
    }

    /**
     * Indica si el enfrentamiento ha finalizado.
     * @return {@code true} si ya se jugó, {@code false} en caso contrario.
     */
    public Boolean isFinalizado(){
        return finalizado;
    }

    /**
     * Devuelve el ganador del enfrentamiento.
     * @return Ganador, o {@code null} si fue empate.
     */
    public Participante getGanador(){
        return ganador;
    }

    /**
     * Establece directamente el resultado del enfrentamiento.
     * @param resultado Resultado a registrar.
     */
    public void setResultado(Resultado resultado){
        this.resultado = resultado;
    }

    /**
     * Establece el primer participante.
     * @param p1 Participante 1.
     */
    public void setParticipante1(Participante p1){
        this.participante1 = p1;
    }

    /**
     * Establece el segundo participante.
     * @param p2 Participante 2.
     */
    public void setParticipante2(Participante p2){
        this.participante2 = p2;
    }

    /**
     * Establece si el enfrentamiento está finalizado.
     * @param finalizado {@code true} si está finalizado.
     */
    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    /**
     * Establece el ganador del enfrentamiento y lo marca como finalizado.
     * @param ganador Participante ganador.
     * @throws IllegalArgumentException si el ganador no es uno de los participantes.
     */
    public void setGanador(Participante ganador){
        if (ganador == null) {
            // Empate
            this.ganador = null;
            this.finalizado = true;
            return;
        }
        if (!ganador.equals(participante1) && !ganador.equals(participante2)){
            throw new IllegalArgumentException("El ganador debe ser uno de los participantes");
        }
        this.ganador = ganador;
        this.finalizado = true;
    }

    /**
     * Representación en texto del enfrentamiento.
     * @return Cadena con formato "participante1 vs participante2".
     */
    @Override
    public String toString(){
        return participante1.getNombre() + " vs " + participante2.getNombre();
    }
}
