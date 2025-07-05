package org.example.model;

import org.example.interfaces.Resultado;

public class ResultadoLol implements Resultado {
    private Participante equipo1;
    private Participante equipo2;

    private int killsEquipo1;
    private int killsEquipo2;
    private int torresEquipo1;
    private int torresEquipo2;
    private int dragonesEquipo1;
    private int dragonesEquipo2;
    private int baronesEquipo1;
    private int baronesEquipo2;

    private Participante ganador;

    public ResultadoLol(Participante equipo1, Participante equipo2) {
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
    }

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

    @Override
    public String getResumen() {
        return "Kills: " + killsEquipo1 + " - " + killsEquipo2 + "\n" +
                "Torres: " + torresEquipo1 + " - " + torresEquipo2 + "\n" +
                "Dragones: " + dragonesEquipo1 + " - " + dragonesEquipo2 + "\n" +
                "Barones: " + baronesEquipo1 + " - " + baronesEquipo2 + "\n" +
                "Ganador: " + (ganador != null ? ganador.getNombre() : "Sin definir");
    }

    @Override
    public Participante getGanador() {
        return ganador;
    }

    @Override
    public boolean esValido() {
        return ganador != null;
    }

    // Getters si necesitas para estadísticas
    public int getKills(Participante p) {
        return p.equals(equipo1) ? killsEquipo1 : killsEquipo2;
    }

    public int getTorres(Participante p) {
        return p.equals(equipo1) ? torresEquipo1 : torresEquipo2;
    }

    public int getDragones(Participante p) {
        return p.equals(equipo1) ? dragonesEquipo1 : dragonesEquipo2;
    }

    public int getBarones(Participante p) {
        return p.equals(equipo1) ? baronesEquipo1 : baronesEquipo2;
    }
}
