package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class GestorTorneos {
    private static final List<Torneo> torneos = new ArrayList<>();

    public static void agregarTorneo(Torneo torneo) {
        torneos.add(torneo);
    }

    public static List<Torneo> obtenerTorneos() {
        return new ArrayList<>(torneos); // copia para evitar modificaciones externas
    }
}

