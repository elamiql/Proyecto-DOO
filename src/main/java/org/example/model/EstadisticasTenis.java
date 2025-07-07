package org.example.model;

/**
 * Clase que representa las estadísticas detalladas de un participante en un torneo de tenis.
 * Incluye conteo de sets y juegos ganados/perdidos, rachas de victorias/derrotas, eficiencia en tiebreaks
 * y métodos para obtener datos en distintos formatos de presentación.
 */
public class EstadisticasTenis extends EstadisticasParticipante<Participante, ResultadoTenis> {

    private int setsGanados;
    private int setsPerdidos;
    private int juegosGanados;
    private int juegosPerdidos;
    private int partidosGanadosEnTiebreak;
    private int partidosPerdidosEnTiebreak;
    private int setsGanadosEnTiebreak;
    private int setsPerdidosEnTiebreak;
    private int rachaVictoriasActual;
    private int rachaVictoriasMaxima;
    private int rachaDerrotasActual;
    private int rachaDerrotasMaxima;
    private boolean ultimaFueVictoria;

    /**
     * Constructor que inicializa las estadísticas para un participante dado.
     *
     * @param participante Participante al que se le asociarán estas estadísticas.
     */
    public EstadisticasTenis(Participante participante) {
        super(participante);
        this.setsGanados = 0;
        this.setsPerdidos = 0;
        this.juegosGanados = 0;
        this.juegosPerdidos = 0;
        this.partidosGanadosEnTiebreak = 0;
        this.partidosPerdidosEnTiebreak = 0;
        this.setsGanadosEnTiebreak = 0;
        this.setsPerdidosEnTiebreak = 0;
        this.rachaVictoriasActual = 0;
        this.rachaVictoriasMaxima = 0;
        this.rachaDerrotasActual = 0;
        this.rachaDerrotasMaxima = 0;
        this.ultimaFueVictoria = false;
    }

    /**
     * Registra el resultado de un partido y actualiza todas las estadísticas correspondientes.
     *
     * @param resultado  Resultado del partido de tenis.
     * @param participante Participante cuyo resultado se va a registrar.
     * @param esLocal Indica si el participante jugó como local (no usado aquí).
     */
    @Override
    public void registrarResultado(ResultadoTenis resultado, Participante participante, boolean esLocal) {
        if (!resultado.esValido()) return;

        Participante ganador = resultado.getGanador();
        if (ganador == null) return;

        boolean victoria = ganador.equals(getParticipante());
        if (victoria) {
            registrarVictoria();
        } else {
            registrarDerrota();
        }

        calcularEstadisticasDetalladas(resultado);
        actualizarRachas(victoria, resultado);
    }

    /**
     * Calcula y actualiza sets, juegos, y estadísticas relacionadas al tiebreak basadas en el resultado.
     *
     * @param resultado Resultado del partido de tenis.
     */
    private void calcularEstadisticasDetalladas(ResultadoTenis resultado) {
        boolean esJugador1 = resultado.getJugador1().equals(getParticipante());

        int[] juegosPropio = esJugador1 ? resultado.getJuegosSetsJugador1() : resultado.getJuegosSetsJugador2();
        int[] juegosOponente = esJugador1 ? resultado.getJuegosSetsJugador2() : resultado.getJuegosSetsJugador1();

        boolean tiebreakEnPartido = false;

        for (int i = 0; i < juegosPropio.length; i++) {
            int jPropio = juegosPropio[i];
            int jOponente = juegosOponente[i];

            if (jPropio == 0 && jOponente == 0) continue;

            juegosGanados += jPropio;
            juegosPerdidos += jOponente;

            if (esSetGanado(jPropio, jOponente)) {
                setsGanados++;
                if (esTiebreak(jPropio, jOponente)) {
                    setsGanadosEnTiebreak++;
                    tiebreakEnPartido = true;
                }
            } else if (esSetGanado(jOponente, jPropio)) {
                setsPerdidos++;
                if (esTiebreak(jOponente, jPropio)) {
                    setsPerdidosEnTiebreak++;
                    tiebreakEnPartido = true;
                }
            }
        }

        if (tiebreakEnPartido) {
            if (resultado.getGanador().equals(getParticipante())) {
                partidosGanadosEnTiebreak++;
            } else {
                partidosPerdidosEnTiebreak++;
            }
        }
    }

    /**
     * Actualiza las rachas de victorias o derrotas consecutivas.
     *
     * @param victoria true si el partido fue ganado, false si fue perdido.
     * @param resultado Resultado del partido (no utilizado internamente).
     */
    private void actualizarRachas(boolean victoria, ResultadoTenis resultado) {
        if (victoria) {
            rachaVictoriasActual++;
            rachaDerrotasActual = 0;
            ultimaFueVictoria = true;
            if (rachaVictoriasActual > rachaVictoriasMaxima) {
                rachaVictoriasMaxima = rachaVictoriasActual;
            }
        } else {
            rachaDerrotasActual++;
            rachaVictoriasActual = 0;
            ultimaFueVictoria = false;
            if (rachaDerrotasActual > rachaDerrotasMaxima) {
                rachaDerrotasMaxima = rachaDerrotasActual;
            }
        }
    }

    /**
     * Determina si un set fue ganado según las reglas oficiales.
     *
     * @param ganados Juegos ganados por el jugador.
     * @param perdidos Juegos ganados por el oponente.
     * @return true si el set fue ganado.
     */
    private boolean esSetGanado(int ganados, int perdidos) {
        if (ganados >= 6 && ganados <= 7 && ganados - perdidos >= 2) return true;
        if (ganados == 7 && (perdidos == 5 || perdidos == 6)) return true;
        return false;
    }

    /**
     * Determina si un set fue ganado mediante tiebreak (7-6).
     *
     * @param ganados Juegos ganados por el jugador.
     * @param perdidos Juegos ganados por el oponente.
     * @return true si se ganó mediante tiebreak.
     */
    private boolean esTiebreak(int ganados, int perdidos) {
        return ganados == 7 && perdidos == 6;
    }

    /**
     * Devuelve los puntos totales del participante.
     * En tenis, se asigna 1 punto por victoria.
     *
     * @return Puntos totales.
     */
    @Override
    public int getPuntos() {
        return getGanados();
    }

    /**
     * Devuelve un resumen de estadísticas en formato de tabla.
     *
     * @return Cadena con datos resumidos.
     */
    @Override
    public String toTablaString() {
        return getParticipante().toString() +
                " - PJ: " + getPartidosJugados() +
                ", G: " + getGanados() +
                ", P: " + getPerdidos() +
                ", Pts: " + getPuntos() +
                ", Sets: " + setsGanados + "/" + (setsGanados + setsPerdidos) +
                ", Juegos: " + juegosGanados + "/" + (juegosGanados + juegosPerdidos) +
                ", %V: " + String.format("%.1f", getPorcentajeVictorias()) +
                ", TB: " + setsGanadosEnTiebreak + "/" + (setsGanadosEnTiebreak + setsPerdidosEnTiebreak);
    }

    /**
     * @return Porcentaje de partidos ganados.
     */
    public double getPorcentajeVictorias() {
        return getPartidosJugados() > 0 ? (double) getGanados() / getPartidosJugados() * 100 : 0;
    }

    /**
     * @return Porcentaje de sets ganados.
     */
    public double getPorcentajeSetsGanados() {
        int totalSets = setsGanados + setsPerdidos;
        return totalSets > 0 ? (double) setsGanados / totalSets * 100 : 0;
    }

    /**
     * @return Porcentaje de juegos ganados.
     */
    public double getPorcentajeJuegosGanados() {
        int totalJuegos = juegosGanados + juegosPerdidos;
        return totalJuegos > 0 ? (double) juegosGanados / totalJuegos * 100 : 0;
    }

    /**
     * @return Porcentaje de tiebreaks ganados.
     */
    public double getEficienciaEnTiebreaks() {
        int totalTiebreaks = setsGanadosEnTiebreak + setsPerdidosEnTiebreak;
        return totalTiebreaks > 0 ? (double) setsGanadosEnTiebreak / totalTiebreaks * 100 : 0;
    }

    /**
     * @return Promedio de juegos ganados por cada set ganado.
     */
    public double getPromedioJuegosPorSet() {
        return setsGanados > 0 ? (double) juegosGanados / setsGanados : 0;
    }

    /**
     * @return Cadena que representa el estado actual de la racha del jugador.
     */
    public String getEstadoRacha() {
        if (ultimaFueVictoria) {
            return "Racha de " + rachaVictoriasActual + " victoria(s)";
        } else {
            return "Racha de " + rachaDerrotasActual + " derrota(s)";
        }
    }

    /**
     * @return Cadena con todas las estadísticas detalladas del participante.
     */
    public String getEstadisticasCompletas() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADÍSTICAS: ").append(getParticipante()).append(" ===\n");
        sb.append("Partidos: ").append(getPartidosJugados()).append(" (G: ").append(getGanados()).append(" - P: ").append(getPerdidos()).append(")\n");
        sb.append("Porcentaje de victorias: ").append(String.format("%.1f", getPorcentajeVictorias())).append("%\n");
        sb.append("Sets: ").append(setsGanados).append(" - ").append(setsPerdidos).append(" (").append(String.format("%.1f", getPorcentajeSetsGanados())).append("%)\n");
        sb.append("Juegos: ").append(juegosGanados).append(" - ").append(juegosPerdidos).append(" (").append(String.format("%.1f", getPorcentajeJuegosGanados())).append("%)\n");
        sb.append("Partidos con tiebreak: ").append(partidosGanadosEnTiebreak + partidosPerdidosEnTiebreak).append("\n");
        sb.append("Tiebreaks: ").append(setsGanadosEnTiebreak).append(" - ").append(setsPerdidosEnTiebreak).append(" (").append(String.format("%.1f", getEficienciaEnTiebreaks())).append("%)\n");
        sb.append("Promedio juegos por set ganado: ").append(String.format("%.1f", getPromedioJuegosPorSet())).append("\n");
        sb.append("Estado actual: ").append(getEstadoRacha()).append("\n");
        sb.append("Mayor racha de victorias: ").append(rachaVictoriasMaxima).append("\n");
        sb.append("Mayor racha de derrotas: ").append(rachaDerrotasMaxima).append("\n");
        return sb.toString();
    }

    public int getSetsGanados() {
        return setsGanados;
    }

    public int getSetsPerdidos() {
        return setsPerdidos;
    }

    public int getJuegosGanados() {
        return juegosGanados;
    }

    public int getJuegosPerdidos() {
        return juegosPerdidos;
    }

    public int getPartidosGanadosEnTiebreak() {
        return partidosGanadosEnTiebreak;
    }

    public int getPartidosPerdidosEnTiebreak() {
        return partidosPerdidosEnTiebreak;
    }

    public int getSetsGanadosEnTiebreak() {
        return setsGanadosEnTiebreak;
    }

    public int getSetsPerdidosEnTiebreak() {
        return setsPerdidosEnTiebreak;
    }

    public int getRachaVictoriasActual() {
        return rachaVictoriasActual;
    }

    public int getRachaVictoriasMaxima() {
        return rachaVictoriasMaxima;
    }

    public int getRachaDerrotasActual() {
        return rachaDerrotasActual;
    }

    public int getRachaDerrotasMaxima() {
        return rachaDerrotasMaxima;
    }

    public boolean isUltimaFueVictoria() {
        return ultimaFueVictoria;
    }
}
