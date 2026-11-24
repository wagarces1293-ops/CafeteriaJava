public class Main {
    public static void main(String[] args) {
        TarjetaRegalo t = new TarjetaRegalo();
        t.recargar(30000);
        IO.println("¿Pagó latte? " + t.pagar(9500));
        IO.println("¿Pagó torta? " + t.pagar(25000));
        IO.println("Saldo: $" + t.getSaldo());
    }
}
