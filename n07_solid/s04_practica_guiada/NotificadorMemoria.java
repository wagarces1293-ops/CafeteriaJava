import java.util.ArrayList;
import java.util.List;

public class NotificadorMemoria implements Notificador {
    private final List<String> mensajes = new ArrayList<>();

    @Override
    public void enviar(String destino, String mensaje) {
        mensajes.add(destino + ": " + mensaje);
    }

    public List<String> enviados() {
        return new ArrayList<>(mensajes);
    }
}
