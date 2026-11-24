public class Cobro {
    private final Notificador notificador;

    public Cobro(Notificador notificador) {
        this.notificador = notificador;
    }

    public void registrar(String cliente, int monto) {
        notificador.enviar(cliente, "Recibo por $" + monto);
    }
}
