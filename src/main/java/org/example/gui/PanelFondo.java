import javax.swing.*;
import java.awt.*;

public class PanelConFondo extends JPanel {

    private Image imagenFondo;

    public PanelConFondo(Image imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibuja la imagen ajustada al tamaño del panel
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

