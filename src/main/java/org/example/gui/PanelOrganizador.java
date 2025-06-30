package org.example.gui;

import org.example.command.CambiarPanelCommand;
import org.example.command.CrearTorneoCommand;
import org.example.enums.Deporte;
import org.example.enums.Formato;
import org.example.enums.Videojuegos;
import org.example.model.Disciplina;
import org.example.model.GestorTorneos;
import org.example.model.Torneo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PanelOrganizador extends JPanel {

    private final JFrame frame;

    private JTextField txtNombre;
    private JTextField txtFecha;
    private JComboBox<Disciplina> cmbDisciplina;
    private JComboBox<Formato> cmbFormato;
    private JRadioButton radioIndividual;
    private JRadioButton radioEquipos;
    private JPasswordField txtPassword;

    public PanelOrganizador(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout(10, 10));

        JLabel labelTitulo = new JLabel("Crear Nuevo Torneo", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(labelTitulo, BorderLayout.NORTH);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);

        // Fecha
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Fecha (dd-MM-yyyy HH:mm):"), gbc);
        txtFecha = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtFecha, gbc);

        // Disciplina
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Disciplina:"), gbc);
        cmbDisciplina = new JComboBox<>(getTodasDisciplinas().toArray(new Disciplina[0]));
        gbc.gridx = 1;
        panelFormulario.add(cmbDisciplina, gbc);

        // Formato
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Formato:"), gbc);
        cmbFormato = new JComboBox<>(Formato.values());
        gbc.gridx = 1;
        panelFormulario.add(cmbFormato, gbc);

        // Tipo de torneo: Individual o Equipos
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Tipo de Torneo:"), gbc);

        JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioIndividual = new JRadioButton("Individual", true);
        radioEquipos = new JRadioButton("Por equipos");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radioIndividual);
        grupo.add(radioEquipos);
        panelTipo.add(radioIndividual);
        panelTipo.add(radioEquipos);
        gbc.gridx = 1;
        panelFormulario.add(panelTipo, gbc);

        // Contraseña (opcional)
        gbc.gridx = 0;
        gbc.gridy = 5;
        panelFormulario.add(new JLabel("Contraseña :"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtPassword, gbc);

        add(panelFormulario, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConfirmar = new JButton("Confirmar");
        JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnVolver);
        panelBotones.add(btnConfirmar);
        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnVolver.addActionListener(e -> new CambiarPanelCommand(frame, new PanelPrincipal(frame)).execute());

        btnConfirmar.addActionListener(e -> confirmarTorneo());
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

            if (nombre.isEmpty() || fecha.isEmpty() || disciplina == null || formato == null) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime fechaParseada = LocalDateTime.parse(fecha, formatter);

            if (fechaParseada.isBefore(LocalDateTime.now())) {
                JOptionPane.showMessageDialog(this, "La fecha no puede estar en el pasado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear torneo
            CrearTorneoCommand comando = new CrearTorneoCommand(nombre, fecha, disciplina, formato, esIndividual, password);
            comando.execute();
            Torneo torneo = comando.getTorneoCreado();
            GestorTorneos.agregarTorneo(torneo);

            JOptionPane.showMessageDialog(this, "Torneo creado:\n" + torneo);
            new CambiarPanelCommand(frame, new PanelPrincipal(frame)).execute();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fecha inválida o error al crear el torneo.\nFormato esperado: dd-MM-yyyy HH:mm", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
