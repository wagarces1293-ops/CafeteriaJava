public class Cliente {
    String nombre;
    int puntos;

    // 1 punto por cada 000 de compra
    void sumarPuntos(int compra) {
        puntos = puntos + compra / 1000;
    }

    boolean puedeCanjear() {
        return puntos >= 50;
    }

    void canjear() {
        if (puedeCanjear()) {
            puntos = puntos - 50;
        }
    }

    String tarjeta() {
        return nombre + ": " + puntos + " puntos";
    }
}
