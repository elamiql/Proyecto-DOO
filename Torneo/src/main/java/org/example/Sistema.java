
package org.example;
import java.time.LocalDateTime;
public class Sistema {

    public Sistema() {
    }

    public static void iniciarTorneo(Torneo torneo) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaTorneo = torneo.getFecha();

        if (!torneo.isActivo() && (ahora.isEqual(fechaTorneo) || ahora.isAfter(fechaTorneo))) {
            torneo.setActivo(true);
            System.out.println("El torneo " + torneo.getNombre() + " ha comenzado.");
        } else if (torneo.isActivo()) {
            System.out.println("El torneo ya está activo.");
        } else {
            System.out.println("El torneo todavía no puede iniciar. Fecha programada: " + fechaTorneo);
        }
    }
}

