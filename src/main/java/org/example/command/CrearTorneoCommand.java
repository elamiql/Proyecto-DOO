
package org.example.command;

import org.example.enums.Formato;
import org.example.interfaces.Disciplina;
import org.example.model.Torneo;
import org.example.model.TorneoIndividual;
import org.example.model.TorneoEquipo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Comando para crear un nuevo torneo en el sistema.
 * <p>
 * Esta clase implementa la interfaz {@link Command} y encapsula la información necesaria
 * para crear un torneo, ya sea individual o por equipos, con sus respectivos atributos
 * como nombre, fecha, disciplina, formato y contraseña.
 * </p>
 */
public class CrearTorneoCommand implements Command {

    private final String nombre;
    private final String fecha;
    private final Disciplina disciplina;
    private final Formato formato;
    private final boolean esIndividual;
    private final String contraseña;

    private Torneo torneoCreado;

    /**
     * Construye un comando para crear un torneo con los datos proporcionados.
     * @param nombre      Nombre del torneo.
     * @param fecha       Fecha y hora del torneo en formato "dd-MM-yyyy HH:mm".
     * @param disciplina  Disciplina del torneo (deporte o videojuego).
     * @param formato     Formato de competencia (liga, eliminación, etc.).
     * @param esIndividual Indica si el torneo es individual (true) o por equipos (false).
     * @param contraseña  Contraseña para proteger el acceso al torneo.
     */
    public CrearTorneoCommand(String nombre, String fecha, Disciplina disciplina, Formato formato, boolean esIndividual,String contraseña) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.disciplina = disciplina;
        this.formato = formato;
        this.esIndividual = esIndividual;
        this.contraseña=contraseña;
    }

    /**
     * Ejecuta la creación del torneo según los parámetros especificados en el constructor.
     * <p>
     * Valida el formato de la fecha y crea una instancia de {@link TorneoIndividual} o
     * {@link TorneoEquipo} según corresponda.
     * </p>
     */
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

    /**
     * Obtiene el torneo creado luego de ejecutar el comando.
     * @return El torneo creado, o {@code null} si no se ha ejecutado el comando.
     */
    public Torneo getTorneoCreado() {
        return torneoCreado;
    }
}
