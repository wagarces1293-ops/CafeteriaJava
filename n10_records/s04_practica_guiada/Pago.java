public sealed interface Pago permits Pago.Efectivo, Pago.Tarjeta, Pago.Bono {
    record Efectivo(int recibido) implements Pago {}
    record Tarjeta(String franquicia, int monto) implements Pago {}
    record Bono(int valor) implements Pago {}
}
