package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.command.CrearTorneoCommand;
import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.enums.Videojuegos;
import org.example.interfaces.Disciplina;
import org.example.model.GestorTorneos;
import org.example.exceptions.DatosInvalidosException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PanelOrganizador extends PanelFondo {

    private final JFrame frame;
    private JTextField txtNombre;
    private JTextField txtFecha;
    private JComboBox<Disciplina> cmbDisciplina;
    private JComboBox<Formato> cmbFormato;
    private JRadioButton radioIndividual;
    private JRadioButton radioEquipos;
    private JPasswordField txtPassword;

    public PanelOrganizador(JFrame frame) {
        super(Imagen.cargarImagen("/Fondos/Fondo1.jpg"));
        this.frame = frame;

        setLayout(new BorderLayout(10, 10));
        initTitulo();
        initFormulario();
        initBotones();
    }

    private void initTitulo() {
        JLabel labelTitulo = new JLabel("Crear Nuevo Torneo", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setOpaque(false);
        add(labelTitulo, BorderLayout.NORTH);
    }

    private void initFormulario() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        agregarCampo(panelFormulario, gbc, 0, "Nombre:", txtNombre = new JTextField(20));
        agregarCampo(panelFormulario, gbc, 1, "Fecha (dd-MM-yyyy HH:mm):", txtFecha = new JTextField(20));

        cmbDisciplina = BotonBuilder.crearComboBox(getTodasDisciplinas().toArray(new Disciplina[0]));
        agregarCampo(panelFormulario, gbc, 2, "Disciplina:", cmbDisciplina);

        cmbFormato = BotonBuilder.crearComboBox(Formato.values());
        agregarCampo(panelFormulario, gbc, 3, "Formato:", cmbFormato);

        radioIndividual = new JRadioButton("Individual", true);
        radioEquipos = new JRadioButton("Por equipos");
        radioIndividual.setOpaque(false);
        radioEquipos.setOpaque(false);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioIndividual);
        grupo.add(radioEquipos);

        JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoPanel.setOpaque(false);
        tipoPanel.add(radioIndividual);
        tipoPanel.add(radioEquipos);
        agregarCampo(panelFormulario, gbc, 4, "Tipo de Torneo:", tipoPanel);

        agregarCampo(panelFormulario, gbc, 5, "Contraseña:", txtPassword = new JPasswordField(20));

        add(panelFormulario, BorderLayout.CENTER);
    }

    private void initBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);

        JButton btnVolver = BotonBuilder.crearBotonVolver(frame, new PanelPrincipal(frame));
        JButton btnConfirmar = BotonBuilder.crearBoton("Confirmar", new Color(0, 153, 76), this::confirmarTorneo);

        panelBotones.add(btnVolver);
        panelBotones.add(btnConfirmar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent campo) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        JLabel label = new JLabel(etiqueta);
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private List<Disciplina> getTodasDisciplinas() {
        List<Disciplina> lista = new ArrayList<>();
        lista.addAll(List.of(Deporte.values()));
        lista.addAll(List.of(Videojuegos.values()));
        return lista;
    }

    private void confirmarTorneo() {
        try {
            String nombre = txtNombre.getText().trim();
            String fecha = txtFecha.getText().trim();
            Disciplina disciplina = (Disciplina) cmbDisciplina.getSelectedItem();
            Formato formato = (Formato) cmbFormato.getSelectedItem();
            String password = new String(txtPassword.getPassword()).trim();
            boolean esIndividual = radioIndividual.isSelected();

            validarNombre(nombre);
            validarFecha(fecha);
            validarPassword(password);
            if (disciplina == null) throw new DatosInvalidosException("Debes seleccionar una disciplina");
            if (formato == null) throw new DatosInvalidosException("Debes seleccionar un formato");

            CrearTorneoCommand comando = new CrearTorneoCommand(nombre, fecha, disciplina, formato, esIndividual, password);
            comando.execute();
            GestorTorneos.agregarTorneo(comando.getTorneoCreado());

            JOptionPane.showMessageDialog(this, "Torneo creado:\n" + comando.getTorneoCreado());
            new CambiarPanelCommand(frame, new PanelPrincipal(frame)).execute();

        } catch (DatosInvalidosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en datos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida o error al crear el torneo.\nFormato esperado: dd-MM-yyyy HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarPassword(String password) {
        if (password == null || password.length() < 4) {
            throw new DatosInvalidosException("La contraseña debe tener al menos 4 caracteres");
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty())
            throw new DatosInvalidosException("El nombre no puede estar vacío");


        long countLetras = nombre.chars()
                .filter(c -> Character.isLetter(c))
                .count();

        if (countLetras < 4)
            throw new DatosInvalidosException("El nombre debe tener al menos 4 letras");
    }

    private void validarFecha(String fecha) {
        if (fecha == null || fecha.isEmpty())
            throw new DatosInvalidosException("La fecha no puede estar vacía");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime fechaParseada;
        try {
            fechaParseada = LocalDateTime.parse(fecha, formatter);
        } catch (Exception e) {
            throw new DatosInvalidosException("Formato de fecha inválido. Debe ser dd-MM-yyyy HH:mm");
        }

        if (fechaParseada.isBefore(LocalDateTime.now())) {
            throw new DatosInvalidosException("La fecha no puede estar en el pasado");
        }
    }
}
