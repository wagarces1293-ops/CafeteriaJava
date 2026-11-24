public class Caja {
    private int saldo; // pesos en la caja
    private int ventas;

    public void cobrar(int valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Cobro inválido: " + valor);
        }
        saldo += valor;
        ventas++;
    }

    public void retirar(int valor) {
        if (valor > saldo) {
            throw new IllegalArgumentException("No hay $" + valor + " en caja");
        }
        saldo -= valor;
    }

    public int getSaldo() {
        return saldo;
    }

    public int getVentas() {
        return ventas;
    }

    public String resumen() {
        return ventas + " ventas · saldo $" + saldo;
    }
}
