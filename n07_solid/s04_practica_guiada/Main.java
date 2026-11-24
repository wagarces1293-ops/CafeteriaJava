public class Main {
    public static void main(String[] args) {
        NotificadorMemoria mock = new NotificadorMemoria();
        Cobro cobro = new Cobro(mock);

        cobro.registrar("Martin", 14000);
        cobro.registrar("Ana", 9500);

        IO.println("Mensajes registrados en memoria para pruebas:");
        for (String msg : mock.enviados()) {
            IO.println(" - " + msg);
        }
    }
}
