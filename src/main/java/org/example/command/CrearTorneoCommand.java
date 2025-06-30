
package org.example.command;

import org.example.enums.Formato;
import org.example.model.Disciplina;
import org.example.model.Torneo;
import org.example.model.TorneoIndividual;
import org.example.model.TorneoEquipo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CrearTorneoCommand implements Command {

    private final String nombre;
    private final String fecha;
    private final Disciplina disciplina;
    private final Formato formato;
    private final boolean esIndividual;
    private final String contraseña;

    private Torneo torneoCreado;

    public CrearTorneoCommand(String nombre, String fecha, Disciplina disciplina, Formato formato, boolean esIndividual,String contraseña) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.disciplina = disciplina;
        this.formato = formato;
        this.esIndividual = esIndividual;
        this.contraseña=contraseña;
    }

    @Override
    public void execute() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime.parse(fecha, formatter); // valida

        if (esIndividual) {
            torneoCreado = new TorneoIndividual(nombre, disciplina, fecha, formato,contraseña);
        } else {
            torneoCreado = new TorneoEquipo(nombre, disciplina, fecha, formato,contraseña);
        }
    }

    public Torneo getTorneoCreado() {
        return torneoCreado;
    }
}
