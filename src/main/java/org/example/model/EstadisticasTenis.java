package org.example.model;

import org.example.interfaces.Resultado;

/**
 * Clase que representa las estadísticas de un participante en un torneo de tenis.
 * <p>
 * Extiende {@link EstadisticasParticipante} con {@link Participante} como tipo de participante
 * y {@link ResultadoTenis} como tipo de resultado.
 * </p>
 */
public class EstadisticasTenis extends EstadisticasParticipante<Participante, ResultadoTenis>{

    /**
     * Puntos o sets ganados por el participante.
     */
    private int golesFavor;

    /**
     * Puntos o sets recibidos en contra por el participante.
     */
    private int golesContra;

    /**
     * Constructor que crea una instancia de estadísticas para un participante de tenis.
     *
     * @param participante Participante del torneo.
     */
    public EstadisticasTenis(Participante participante){
        super(participante);
    }

    /**
     * Agrega puntos/sets ganados y perdidos de forma manual.
     *
     * @param golesFavor   Puntos/sets ganados.
     * @param golesContra  Puntos/sets perdidos.
     */
    public void agregarGoles(int golesFavor, int golesContra){
        this.golesFavor += golesFavor;
        this.golesContra += golesContra;
    }

    /**
     * Calcula la diferencia entre puntos/sets a favor y en contra.
     * @return Diferencia de puntos o sets.
     */
    public int getDiferenciaGoles(){
        return golesFavor - golesContra;
    }

    /**
     * Devuelve una representación textual de las estadísticas incluyendo goles/sets y diferencia.
     * @return Cadena con datos resumidos.
     */
    @Override
    public String toString() {
        return super.toString() +
                ", GF: " + golesFavor +
                ", GC: " + golesContra +
                ", DIF: " + getDiferenciaGoles();
    }

    /**
     * Registra el resultado de un enfrentamiento de tenis.
     *
     * @param resultado     Resultado del partido.
     * @param participante  Participante cuyas estadísticas se registran.
     * @param esLocal       Indica si el participante fue local (no se usa en esta implementación).
     * @throws IllegalArgumentException si el resultado no es válido.
     */
    @Override
    public void registrarResultado(ResultadoTenis resultado, Participante participante, boolean esLocal) {
        if (!resultado.esValido()) throw new IllegalArgumentException("Resultado no valido");



    }

    /**
     * Calcula los puntos del participante según el sistema estándar:
     * 3 puntos por victoria, 1 por empate.
     * @return Puntos totales.
     */
    @Override
    public int getPuntos() {

        return 3*getGanados() + getEmpatados();
    }

    /**
     * Representación de estadísticas del participante en formato de tabla.
     *
     * @return Cadena con columnas.
     */
    @Override
    public String toTablaString() {
        return String.format("%-20s | %2d | %2d | %2d | %2d | %3d | %3d | %4d | %3d",
                getParticipante().toString(),
                getPartidosJugados(),
                getGanados(),
                getEmpatados(),
                getPerdidos(),
                golesFavor,
                golesContra,
                getDiferenciaGoles(),
                getPuntos());
    }

    /**
     * @return Total de puntos/sets ganados.
     */
    public int getGolesFavor(){
        return golesFavor;
    }

    /**
     * @return Total de puntos/sets perdidos.
     */
    public int getGolesContra(){
        return golesContra;
    }



}
