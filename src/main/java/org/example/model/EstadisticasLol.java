package org.example.model;

public class EstadisticasLol extends EstadisticasParticipante<Participante, ResultadoLol> {

    private int killsTotales;
    private int torresTotales;
    private int dragonesTotales;
    private int baronesTotales;

    public EstadisticasLol(Participante participante) {
        super(participante);
    }

    @Override
    public void registrarResultado(ResultadoLol resultado, Participante participante, boolean esLocal) {
        if (!resultado.esValido()) return;

        if (resultado.getGanador().equals(participante)) {
            registrarVictoria();
        } else {
            registrarDerrota();
        }

        killsTotales += resultado.getKills(participante);
        torresTotales += resultado.getTorres(participante);
        dragonesTotales += resultado.getDragones(participante);
        baronesTotales += resultado.getBarones(participante);
    }

    @Override
    public int getPuntos() {
        return getGanados() * 3; // Por ejemplo, 3 puntos por victoria
    }

    @Override
    public String toTablaString() {
        return getParticipante().getNombre() +
                " - PJ: " + getPartidosJugados() +
                ", G: " + getGanados() +
                ", P: " + getPerdidos() +
                ", Puntos: " + getPuntos() +
                ", Kills: " + killsTotales +
                ", Torres: " + torresTotales +
                ", Dragones: " + dragonesTotales +
                ", Barones: " + baronesTotales;
    }
}
