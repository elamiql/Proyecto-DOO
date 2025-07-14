package org.example.gui.Otros;

import org.example.command.CambiarPanelCommand;

import javax.swing.*;
import java.awt.*;

/**
 * Clase utilitaria para la creación de componentes gráficos personalizados, como botones y comboboxes.
 *
 * <p>Proporciona métodos estáticos que permiten construir botones estilizados y comboboxes
 * con una apariencia y comportamiento predefinidos, facilitando la reutilización y la consistencia
 * en la interfaz gráfica de usuario.</p>
 */
public class Builder {

    /**
     * Crea un botón personalizado con estilo y una acción asociada.
     * @param texto       el texto que se mostrará en el botón.
     * @param colorFondo  el color de fondo del botón.
     * @param accion      la acción que se ejecutará al hacer clic en el botón.
     * @return un {@link JButton} configurado con los estilos y acción especificados.
     */
    public static JButton crearBoton(String texto, Color colorFondo, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        boton.setPreferredSize(new Dimension(240, 60));
        boton.setFocusPainted(false);
        boton.setForeground(Color.WHITE);
        boton.setBackground(colorFondo);
        boton.setBorder(BorderFactory.createLineBorder(colorFondo.darker(), 2, true));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
            }
        });

        boton.addActionListener(e -> accion.run());

        return boton;
    }

    /**
     * Crea un botón de "volver" que cambia el panel actual por otro en una ventana dada.
     * @param frame   la ventana principal donde se realiza el cambio de panel.
     * @param destino el panel al que se desea volver.
     * @return un {@link JButton} con estilo y funcionalidad de retorno.
     */
    public static JButton crearBotonVolver(JFrame frame, JPanel destino) {
        return crearBoton("<- Volver", new Color(244, 62, 77), () ->
                new CambiarPanelCommand(frame, destino).execute()
        );
    }

    /**
     * Crea un combo box personalizado con estilo para una lista de elementos.
     * @param items los elementos que se mostrarán en el combo box.
     * @param <T>   el tipo de los elementos del combo box.
     * @return un {@link JComboBox} estilizado que contiene los elementos especificados.
     */
    public static <T> JComboBox<T> crearComboBox(T[] items) {
        JComboBox<T> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.DARK_GRAY);
        comboBox.setFocusable(false);
        comboBox.setPreferredSize(new Dimension(200, 30));
        return comboBox;
    }
    /**
     * Crea un JLabel estilizado para ser usado como título destacado en la interfaz.
     *
     * @param texto el texto del título.
     * @return un {@link JLabel} centrado, con fuente grande, color destacado y borde inferior decorativo.
     */
    public static JLabel crearTitulo(String texto) {
        JLabel titulo = new JLabel(texto.toUpperCase(), SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        titulo.setForeground(new Color(0x2E86C1));
        titulo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0xAED6F1)));
        return titulo;
    }


}

