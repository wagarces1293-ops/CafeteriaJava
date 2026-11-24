public class Main {
    public static void procesar(Pago p) {
        if (p instanceof Pago.Efectivo e) {
            IO.println("Efectivo entregado: $" + e.recibido());
        } else if (p instanceof Pago.Tarjeta t) {
            IO.println("Tarjeta " + t.franquicia() + " por $" + t.monto());
        } else if (p instanceof Pago.Bono b) {
            IO.println("Bono de regalo por $" + b.valor());
        }
    }

    public static void main(String[] args) {
        procesar(new Pago.Efectivo(20000));
        procesar(new Pago.Tarjeta("Visa", 14500));
        procesar(new Pago.Bono(5000));
    }
}
