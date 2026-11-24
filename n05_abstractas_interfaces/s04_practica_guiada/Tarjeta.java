public class Tarjeta implements MedioPago {
    private int saldoDisponible;

    public Tarjeta(int saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    @Override
    public String nombre() {
        return "Tarjeta";
    }

    @Override
    public boolean pagar(int monto) {
        if (monto > saldoDisponible) {
            IO.println("Fondos insuficientes en tarjeta");
            return false;
        }
        saldoDisponible -= monto;
        IO.println("Cobro electronico exitoso. Saldo restante: $" + saldoDisponible);
        return true;
    }
}
