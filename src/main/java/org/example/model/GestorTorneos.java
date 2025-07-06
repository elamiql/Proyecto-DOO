package org.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase singleton que gestiona la colección de torneos disponibles.
 * Proporciona métodos estáticos para agregar y obtener torneos.
 */
public class GestorTorneos {

    /**
     * Lista estática que almacena todos los torneos registrados.
     */
    private static final List<Torneo> torneos = new ArrayList<>();

    /**
     * Agrega un nuevo torneo a la lista de torneos gestionados.
     * @param torneo Torneo a agregar.
     */
    public static void agregarTorneo(Torneo torneo) {
        torneos.add(torneo);
    }

    /**
     * Obtiene una copia de la lista de torneos registrados.
     * Se devuelve una nueva lista para evitar modificaciones externas sobre la lista original.
     *
     * @return Lista con todos los torneos registrados.
     */
    public static List<Torneo> obtenerTorneos() {
        return new ArrayList<>(torneos); //
    }

}

