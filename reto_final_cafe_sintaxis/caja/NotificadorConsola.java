package caja;

public class NotificadorConsola implements Notificador {
    @Override
    public void notificar(String destinatario, String mensaje) {
        System.out.println("[NOTIFICACION A " + destinatario + "]: " + mensaje);
    }
}
