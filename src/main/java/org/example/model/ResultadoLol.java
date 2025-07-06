package org.example.model;

import org.example.interfaces.Resultado;

/**
 * Representa el resultado de un enfrentamiento de League of Legends (LoL) entre dos participantes.
 * Incluye estadísticas detalladas como kills, torres, dragones y barones para cada equipo,
 * así como el participante ganador.
 */
public class ResultadoLol implements Resultado {

    /**
     * equipo 1.
     */
    private Participante equipo1;

    /**
     * equipo 2.
     */
    private Participante equipo2;

    /**
     * Número total de kills del equipo 1.
     */
    private int killsEquipo1;

    /**
     * Número total de kills del equipo 2.
     */
    private int killsEquipo2;

    /**
     * Número total de torres destruidas por el equipo 1.
     */
    private int torresEquipo1;

    /**
     * Número total de torres destruidas por el equipo 2.
     */
    private int torresEquipo2;

    /**
     * Número total de dragones asesinados por el equipo 1.
     */
    private int dragonesEquipo1;

    /**
     * Número total de dragones asesinados por el equipo 2.
     */
    private int dragonesEquipo2;

    /**
     * Número total de barones asesinados por el equipo 1.
     */
    private int baronesEquipo1;

    /**
     * Número total de barones asesinados por el equipo 2.
     */
    private int baronesEquipo2;

    /**
     * Equipo ganador del enfrentamiento.
     */
    private Participante ganador;

    /**
     * Constructor que inicializa los equipos del enfrentamiento.
     * @param equipo1 Participante o equipo 1.
     * @param equipo2 Participante o equipo 2.
     */
    public ResultadoLol(Participante equipo1, Participante equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }

    /**
     * Registra las estadísticas del enfrentamiento y el ganador.
     *
     * @param kills1 Kills del equipo 1.
     * @param kills2 Kills del equipo 2.
     * @param torres1 Torres destruidas por el equipo 1.
     * @param torres2 Torres destruidas por el equipo 2.
     * @param dragones1 Dragones asesinados por el equipo 1.
     * @param dragones2 Dragones asesinados por el equipo 2.
     * @param barones1 Barones asesinados por el equipo 1.
     * @param barones2 Barones asesinados por el equipo 2.
     * @param ganador Participante ganador del enfrentamiento.
     */
    public void registrarEstadisticas(int kills1, int kills2, int torres1, int torres2,
                                      int dragones1, int dragones2, int barones1, int barones2,
                                      Participante ganador) {
        this.killsEquipo1 = kills1;
        this.killsEquipo2 = kills2;
        this.torresEquipo1 = torres1;
        this.torresEquipo2 = torres2;
        this.dragonesEquipo1 = dragones1;
        this.dragonesEquipo2 = dragones2;
        this.baronesEquipo1 = barones1;
        this.baronesEquipo2 = barones2;
        this.ganador = ganador;
    }

    /**
     * Obtiene un resumen en formato texto con las estadísticas del enfrentamiento y el ganador.
     * @return resumen de las estadísticas y resultado.
     */
    @Override
    public String getResumen() {
        return "Kills: " + killsEquipo1 + " - " + killsEquipo2 + "\n" +
                "Torres: " + torresEquipo1 + " - " + torresEquipo2 + "\n" +
                "Dragones: " + dragonesEquipo1 + " - " + dragonesEquipo2 + "\n" +
                "Barones: " + baronesEquipo1 + " - " + baronesEquipo2 + "\n" +
                "Ganador: " + (ganador != null ? ganador.getNombre() : "Sin definir");
    }

    /**
     * Obtiene el participante ganador del enfrentamiento.
     * @return participante ganador, o null si no está definido.
     */
    @Override
    public Participante getGanador() {
        return ganador;
    }

    /**
     * Verifica si el resultado es válido, es decir, que exista un ganador definido.
     * @return true si hay un ganador, false en caso contrario.
     */
    @Override
    public boolean esValido() {
        return ganador != null;
    }

    //getters

    /**
     * Obtiene la cantidad de kills para un participante específico.
     * @param p participante cuyo número de kills se desea obtener.
     * @return kills del participante.
     */
    public int getKills(Participante p) {
        return p.equals(equipo1) ? killsEquipo1 : killsEquipo2;
    }

    /**
     * Obtiene la cantidad de torres destruidas para un participante específico.
     * @param p participante cuyo número de torres destruidas se desea obtener.
     * @return torres destruidas por el participante.
     */
    public int getTorres(Participante p) {
        return p.equals(equipo1) ? torresEquipo1 : torresEquipo2;
    }

    /**
     * Obtiene la cantidad de dragones asesinados para un participante específico.
     * @param p participante cuyo número de dragones asesinados se desea obtener.
     * @return dragones asesinados por el participante.
     */
    public int getDragones(Participante p) {
        return p.equals(equipo1) ? dragonesEquipo1 : dragonesEquipo2;
    }

    /**
     * Obtiene la cantidad de barones asesinados para un participante específico.
     * @param p participante cuyo número de barones asesinados se desea obtener.
     * @return barones asesinados por el participante.
     */
    public int getBarones(Participante p) {
        return p.equals(equipo1) ? baronesEquipo1 : baronesEquipo2;
    }
}
