package org.example.gui;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

/**
 * Clase utilitaria para la carga de imágenes desde el sistema de recursos.
 * <p>Permite cargar imágenes ubicadas dentro del classpathh
 * utilizando su ruta relativa.</p>
 */
public class Imagen {

    /**
     * Carga una imagen desde una ruta específica dentro del classpath.
     * @param ruta la ruta relativa al recurso de la imagen (por ejemplo, <code>"/imagenes/logo.png"</code>).
     * @return la imagen cargada como un objeto {@link Image}, o {@code null} si ocurre un error durante la carga.
     */
    public static Image cargarImagen(String ruta) {
        try {
            return ImageIO.read(Imagen.class.getResource(ruta));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("No se pudo cargar la imagen: " + ruta);
            return null;
        }
    }

}
