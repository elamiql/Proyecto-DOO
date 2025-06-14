package org.example.app;

import org.example.enums.*;
import org.example.model.*;
import java.util.ArrayList;

public class Main3 {
    public static void main(String[] args) {
        ArrayList<Equipo> mundialEquipos = new ArrayList<>();

        // Grupos del Mundial de Qatar 2022
        String[][] gruposQatar = {
                {"Paises Bajos", "Senegal", "Ecuador", "Qatar"},        // Grupo A
                {"Inglaterra", "EEUU", "Irán", "Gales"},                // Grupo B
                {"Argentina", "Polonia", "México", "Arabia Saudita"},   // Grupo C
                {"Francia", "Australia", "Túnez", "Dinamarca"},         // Grupo D
                {"Japón", "España", "Alemania", "Costa Rica"},          // Grupo E
                {"Marruecos", "Croacia", "Bélgica", "Canadá"},          // Grupo F
                {"Brasil", "Suiza", "Camerún", "Serbia"},               // Grupo G
                {"Portugal", "Corea del Sur", "Uruguay", "Ghana"}       // Grupo H
        };

        for (String[] grupo : gruposQatar) {
            for (String nombre : grupo) {
                mundialEquipos.add(new Equipo(nombre, "Seleccion", new ArrayList<>()));
            }
        }

        // Crear un torneo custom con formato grupos + eliminatoria
        TorneoEquipo torneoMundial = new TorneoEquipo(
                "Mundial Qatar 2022",
                Deporte.FUTBOL,              // asumiendo tienes un enum Disciplina con FUTBOL
                "12-06-2025 18:00",
                Formato.GRUPOS_CON_ELIMINATORIA,
                8,                             // número de grupos
                2                              // clasificados por grupo
        );

        // Añadir equipos al torneo
        for (Equipo e : mundialEquipos) {
            torneoMundial.addEquipo(e);
        }

        // Generar calendario completo (fase grupos + eliminatorias)
        torneoMundial.generarCalendario();

        // Aquí podrías agregar metodo en TorneoEquipo para imprimir brackets o resultados si quieres
        // Pero como ejemplo podrías hacer:
        System.out.println("Calendario generado para el torneo: " + torneoMundial.getNombre());
    }
}