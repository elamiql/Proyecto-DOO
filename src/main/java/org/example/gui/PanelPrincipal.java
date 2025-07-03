package org.example.gui;

import org.example.command.CambiarPanelCommand;


import javax.swing.*;
import java.awt.*;


public class PanelPrincipal extends PanelFondo {

    public PanelPrincipal(JFrame frame) {
        super(Imagen.cargarImagen("/Fondos/Fondo.jpg"));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.gridx = 0;

        JButton btnCrearTorneo = BotonBuilder.crearBoton("➕ Crear Torneo", new Color(30, 144, 255),
                () -> new CambiarPanelCommand(frame, new PanelOrganizador(frame)).execute());

        JButton btnInscribirse = BotonBuilder.crearBoton("🏆 Ver Torneos", new Color(50, 205, 50),
                () -> new CambiarPanelCommand(frame, new PanelParticipante(frame)).execute());

        gbc.gridy = 0;
        add(btnCrearTorneo, gbc);

        gbc.gridy = 1;
        add(btnInscribirse, gbc);

        setPreferredSize(new Dimension(500, 300));
    }


}


