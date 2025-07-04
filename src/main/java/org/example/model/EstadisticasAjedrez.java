package org.example.model;

public class EstadisticasAjedrez extends EstadisticasParticipante<Participante, ResultadoAjedrez> {

    public EstadisticasAjedrez(Participante participante) {
        super(participante);
    }

    @Override
    public void registrarResultado(ResultadoAjedrez resultado, Participante participante, boolean esLocal) {
        // Verifica si el participante es jugador1 o jugador2 en el resultado
        Double puntos = null;
        if (participante.equals(resultado.getGanador())) {
            registrarVictoria();
            return;
        } else if (resultado.getGanador() == null) { // empate
            registrarEmpate();
            return;
        } else {
            registrarDerrota();
            return;
        }
    }

    @Override
    public int getPuntos() {
        // Puntuación típica ajedrez: victoria = 1, empate = 0.5, derrota = 0
        // Como los métodos incrementan conteos, calculamos puntos totales:
        return (int)(getGanados() + getEmpatados() * 0.5);
    }

    @Override
    public String toTablaString() {
        return String.format("%s - PJ: %d, G: %d, E: %d, P: %d, Puntos: %.1f",
                getParticipante(),
                getPartidosJugados(),
                getGanados(),
                getEmpatados(),
                getPerdidos(),
                getGanados() * 1.0 + getEmpatados() * 0.5);
    }
}
