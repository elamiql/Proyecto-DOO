package org.example.model;

public class EstadisticaTenisDeMesa extends EstadisticasParticipante<Participante, ResultadoTenisDeMesa> {

    public EstadisticaTenisDeMesa(Participante participante) {
        super(participante);
    }

    @Override
    public void registrarResultado(ResultadoTenisDeMesa resultado, Participante participante, boolean esLocal) {
        Participante ganador = resultado.getGanador();
        if (ganador == null) {
            return;
        }
        if (ganador.equals(participante)) {
            registrarVictoria();
        } else {
            registrarDerrota();
        }
    }

    @Override
    public int getPuntos() {
        return getGanados() * 3;
    }

    @Override
    public String toTablaString() {
        return getParticipante() +
                " - PJ: " + getPartidosJugados() +
                ", G: " + getGanados() +
                ", P: " + getPerdidos() +
                ", Ptos: " + getPuntos();
    }
}
