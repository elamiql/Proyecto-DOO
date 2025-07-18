package org.example.gui.Paneles;

import org.example.command.CambiarPanelCommand;
import org.example.gui.Otros.Builder;
import org.example.gui.Otros.Imagen;


import javax.swing.*;
import java.awt.*;


/**
 * Panel principal de la aplicación que sirve como menú de inicio para los usuarios.
 *
 * <p>Desde este panel se permite navegar a dos funcionalidades principales:</p>
 * <ul>
 *     <li>Crear un nuevo torneo (rol de organizador)</li>
 *     <li>Ver e inscribirse en torneos existentes (rol de participante)</li>
 * </ul>
 *
 * <p>Extiende {@link PanelFondo} para mostrar una imagen de fondo.</p>
 */
public class PanelPrincipal extends PanelFondo {

    /**
     * Construye el panel principal con botones de navegación.
     * @param frame La ventana principal de la aplicación.
     */
    public PanelPrincipal(JFrame frame) {
        super(Imagen.cargarImagen("/Fondos/Fondo2.jpg"));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.gridx = 0;


        JButton btnCrearTorneo = Builder.crearBoton("➕ Crear Torneo", new Color(30, 144, 255),
                () -> new CambiarPanelCommand(frame, new PanelOrganizador(frame)).execute());

        JButton btnInscribirse = Builder.crearBoton("🏆 Ver Torneos", new Color(50, 205, 50),
                () -> new CambiarPanelCommand(frame, new PanelParticipante(frame)).execute());

        gbc.gridy = 0;
        add(btnCrearTorneo, gbc);

        gbc.gridy = 1;
        add(btnInscribirse, gbc);

        setPreferredSize(new Dimension(500, 300));
    }
}


