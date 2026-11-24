public class Reserva {
    private final String cliente;
    private final int personas;
    private final String hora;

    public Reserva(String cliente, int personas, String hora) {
        if (personas < 1 || personas > 8) {
            throw new IllegalArgumentException("Capacidad por mesa: 1 a 8 personas");
        }
        this.cliente = cliente;
        this.personas = personas;
        this.hora = hora;
    }

    public Reserva(String cliente, int personas) {
        this(cliente, personas, "19:00");
    }

    public String aviso() {
        return "Mesa reservada para " + cliente + " (" + personas + " personas) a las " + hora;
    }

    public String aviso(int minutosAntes) {
        return aviso() + " [Llegar " + minutosAntes + " min antes]";
    }
}
