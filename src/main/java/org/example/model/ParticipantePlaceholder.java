package org.example.model;

public class ParticipantePlaceholder extends Participante {
    public ParticipantePlaceholder(String nombre) {
        super(nombre, "N/A"); // El número no importa para placeholders
    }

    @Override
    public void inscribirse(Torneo<?> torneo) {
        // No hace nada, ya que es un placeholder
    }
}
