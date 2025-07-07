package org.example.command;

import javax.swing.*;

/**
 * Comando para cambiar el panel visible en un {@link JFrame}.
 * <p>
 * Permite reemplazar el contenido actual del frame por un nuevo panel
 * y refrescar la interfaz gráfica.
 * </p>
 */
public class CambiarPanelCommand implements Command {
    private final JFrame frame;
    private final JPanel nuevoPanel;

    /**
     * Construye un comando que cambiará el contenido del frame al nuevo panel especificado.
     * @param frame     El {@link JFrame} cuyo contenido será reemplazado.
     * @param nuevoPanel El nuevo {@link JPanel} que se mostrará en el frame.
     */
    public CambiarPanelCommand(JFrame frame, JPanel nuevoPanel) {
        this.frame = frame;
        this.nuevoPanel = nuevoPanel;
    }

    /**
     * Ejecuta el cambio de panel en el frame.
     * Reemplaza el contenido actual por el nuevo panel y actualiza la interfaz.
     */
    @Override
    public void execute() {
        frame.setContentPane(nuevoPanel);
        frame.revalidate();
        frame.repaint();
    }
}
