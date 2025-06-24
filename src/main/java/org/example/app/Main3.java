package org.example.app;

import org.example.enums.*;
import org.example.model.*;

import java.lang.reflect.Array;
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

        GruposEliminatoria<Equipo> generador = new GruposEliminatoria<>(mundialEquipos, 8, 2);

        generador.generarCalendario();
        System.out.println("=== Fase de Grupos ===");
        generador.imprimirCalendario();
        ArrayList<Enfrentamiento> enfrentamientos = generador.getEnfrentamientos();
    }
}