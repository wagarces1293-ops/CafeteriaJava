public class Main {
    public static void cobrar(MedioPago medio, int monto) {
        IO.println("Iniciando cobro con " + medio.nombre() + " por $" + monto);
        medio.pagar(monto);
    }

    public static void main(String[] args) {
        MedioPago m1 = new Efectivo();
        MedioPago m2 = new Tarjeta(20000);

        cobrar(m1, 9500);
        cobrar(m2, 12000);
        cobrar(m2, 15000); // Falla por saldo
    }
}
