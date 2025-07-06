package org.example.gui;
import javax.swing.*;
import java.awt.*;

/**
 * Panel personalizado que permite mostrar una imagen de fondo redimensionada
 * al tamaño del panel.
 *
 * <p>Este panel puede utilizarse como contenedor para otros componentes Swing
 * y se encargará de renderizar una imagen de fondo adaptada al tamaño del panel.</p>
 */
public class PanelFondo extends JPanel {

    /** Imagen que se dibujará como fondo del panel. */
    private Image imagenFondo;

    /**
     * Crea un nuevo panel con una imagen de fondo.
     * @param imagenFondo la imagen que se utilizará como fondo del panel.
     */
    public PanelFondo(Image imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    /**
     * Dibuja la imagen de fondo escalada al tamaño actual del panel.
     * @param g el contexto gráfico en el que se dibujará el fondo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

