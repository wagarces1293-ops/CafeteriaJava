public interface Descontable {
    int precioBase();

    default int conDescuento(int pct) {
        return precioBase() * (100 - pct) / 100;
    }
}
