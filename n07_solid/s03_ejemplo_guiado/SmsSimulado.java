public class SmsSimulado implements Notificador {
    @Override
    public void enviar(String destino, String mensaje) {
        IO.println("SMS a " + destino + ": " + mensaje);
    }
}
