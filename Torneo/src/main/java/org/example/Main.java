package org.example;

public class Main {

    public static void main(String[] args) {
        Torneo torneo = new Torneo("Los insanos cup", "pelota", null, "10-03-2025 13:14", "brackets");
        Sistema.iniciarTorneo(torneo);
    }
}
