package org.example.model;

/**
 * Representa una apuesta realizada sobre un {@link Enfrentamiento}.
 * La apuesta se hace por un {@link Participante} específico y tiene un monto asociado.
 *
 * Si el participante apostado gana el enfrentamiento, el retorno es el doble del monto.
 * En caso de empate o pérdida, no se recupera el monto apostado.
 */
public class Apuesta {
    private final Enfrentamiento enfrentamiento;
    private final Participante participanteApostado;
    private final int monto;

    /**
     * Crea una nueva apuesta.
     * @param enfrentamiento el enfrentamiento en el que se realiza la apuesta
     * @param participanteApostado el participante por el que se apuesta
     * @param monto el monto de la apuesta (debe ser positivo)
     */
    public Apuesta(Enfrentamiento enfrentamiento, Participante participanteApostado, int monto) {
        this.enfrentamiento = enfrentamiento;
        this.participanteApostado = participanteApostado;
        this.monto = monto;
    }

    /**
     * Devuelve el enfrentamiento asociado a esta apuesta.
     * @return el {@link Enfrentamiento} apostado
     */
    public Enfrentamiento getEnfrentamiento() {
        return enfrentamiento;
    }

    /**
     * Resuelve la apuesta en función del participante que realmente ganó.
     * @param ganadorReal el participante que ganó el enfrentamiento,
     * o {@code null} si fue un empate
     * @return el monto ganado: doble del monto si acertó, 0 en otro caso
     */
    public int resolver(Participante ganadorReal) {
        if (ganadorReal == null) return 0; // empate
        if (ganadorReal.equals(participanteApostado)) {
            return monto * 2; // ganó
        } else {
            return 0; // perdió
        }
    }

    /**
     * Devuelve el participante por el cual se realizó la apuesta.
     * @return el participante apostado
     */
    public Participante getParticipanteApostado() {
        return participanteApostado;
    }

    /**
     * Devuelve el monto apostado.
     * @return el monto de la apuesta
     */
    public int getMonto() {
        return monto;
    }
}
