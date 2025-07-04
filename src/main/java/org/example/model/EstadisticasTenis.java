package org.example.model;

public class EstadisticasTenis extends EstadisticasParticipante<Participante, ResultadoTenis> {

    public EstadisticasTenis(Participante participante) {
        super(participante);
    }

    @Override
    public void registrarResultado(ResultadoTenis resultado, Participante participante, boolean esLocal) {
        if (!resultado.esValido()) return;

        Participante ganador = resultado.getGanador();

        if (ganador == null) return; // No debería pasar en tenis, pero por seguridad.

        if (ganador.equals(getParticipante())) {
            registrarVictoria();
        } else {
            registrarDerrota();
        }
    }

    @Override
    public int getPuntos() {
        return getGanados(); // En tenis, 1 punto por victoria, 0 por derrota
    }

    @Override
    public String toTablaString() {
        return getParticipante().toString() +
                " - PJ: " + getPartidosJugados() +
                ", G: " + getGanados() +
                ", P: " + getPerdidos() +
                ", Pts: " + getPuntos();
    }
}
