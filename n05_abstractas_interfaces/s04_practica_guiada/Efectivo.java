public class Efectivo implements MedioPago {
    @Override
    public String nombre() {
        return "Efectivo";
    }

    @Override
    public boolean pagar(int monto) {
        IO.println("Pago de $" + monto + " recibido en caja en efectivo");
        return true;
    }
}
