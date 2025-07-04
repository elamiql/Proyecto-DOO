package org.example.model;


public class EstadisticasFifa extends EstadisticasParticipante<Participante, ResultadoFifa> {
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;
    private int golesFavor;
    private int golesContra;

    public EstadisticasFifa(Participante participante) {
        super(participante);
        partidosJugados = 0;
        ganados = 0;
        empatados = 0;
        perdidos = 0;
        golesFavor = 0;
        golesContra = 0;
    }

    @Override
    public void registrarResultado(ResultadoFifa resultado, Participante participante, boolean esLocal) {
        if (resultado == null) return;

        partidosJugados++;
        int golesMarcados = esLocal ? resultado.getGolesLocal() : resultado.getGolesVisitante();
        int golesRecibidos = esLocal ? resultado.getGolesVisitante() : resultado.getGolesLocal();

        golesFavor += golesMarcados;
        golesContra += golesRecibidos;

        Participante ganador = resultado.getGanador();
        if (ganador == null) { // empate
            empatados++;
        } else if (ganador.equals(participante)) {
            ganados++;
        } else {
            perdidos++;
        }
    }

    @Override
    public int getPuntos() {
        return ganados * 3 + empatados;
    }

    @Override
    public String toTablaString() {
        int diferenciaGoles = golesFavor - golesContra;
        return String.format(
                "%s - PJ: %d, G: %d, E: %d, P: %d, GF: %d, GC: %d, DG: %d, Puntos: %d",
                getParticipante().toString(), partidosJugados, ganados, empatados, perdidos, golesFavor, golesContra, diferenciaGoles, getPuntos()
        );
    }

    // Getters útiles (opcional)
    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getGanados() {
        return ganados;
    }

    public int getEmpatados() {
        return empatados;
    }

    public int getPerdidos() {
        return perdidos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

}
