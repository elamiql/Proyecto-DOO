package org.example.interfaces;

public interface Estadisticas<T, R> {
    void registrarResultado(R resultado, T participante, boolean esLocal);
    int getPuntos();
    String toTablaString();
}
