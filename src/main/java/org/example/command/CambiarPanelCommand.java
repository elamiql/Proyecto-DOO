package org.example.command;

import javax.swing.*;

public class CambiarPanelCommand implements Command {
    private final JFrame frame;
    private final JPanel nuevoPanel;

    public CambiarPanelCommand(JFrame frame, JPanel nuevoPanel) {
        this.frame = frame;
        this.nuevoPanel = nuevoPanel;
    }

    @Override
    public void execute() {
        frame.setContentPane(nuevoPanel);
        frame.revalidate();
        frame.repaint();
    }
}
