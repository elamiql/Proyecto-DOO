package org.example.gui;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

public class Imagen {

    public static Image cargarImagen(String ruta) {
        try {
            return ImageIO.read(Imagen.class.getResource(ruta));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("No se pudo cargar la imagen: " + ruta);
            return null;
        }
    }
}
