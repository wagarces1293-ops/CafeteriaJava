import java.util.List;

public class Caja {
    private final Notificador notificador;
    private final Ticket ticket = new Ticket();

    public Caja(Notificador notificador) {
        this.notificador = notificador;
    }

    public int cobrar(List<Integer> precios, Descuento d, String tel) {
        int total = 0;
        for (int p : precios) total += p;
        total = d.aplicar(total);

        IO.println(ticket.formatear(total));
        notificador.enviar(tel, "gracias");
        return total;
    }
}
