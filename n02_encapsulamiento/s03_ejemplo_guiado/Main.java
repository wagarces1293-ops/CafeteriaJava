public class Main {
    public static void main(String[] args) {
        Caja caja = new Caja();
        caja.cobrar(19000);
        caja.cobrar(7000);
        IO.println(caja.resumen());

        try {
            caja.cobrar(-500);
        } catch (IllegalArgumentException e) {
            IO.println("Rechazado: " + e.getMessage());
        }

        // caja.saldo = 0; // Error de compilacion: saldo tiene acceso privado en Caja
        IO.println("Saldo: $" + caja.getSaldo());
    }
}
