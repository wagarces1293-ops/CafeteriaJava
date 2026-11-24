public class Reserva {
    private final int id;
    private final String cliente;
    private final int personas;
    private String hora; // formato "18:30"

    public Reserva(int id, String cliente, int personas, String hora) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id invalido: " + id);
        }
        if (cliente == null || cliente.isBlank()) {
            throw new IllegalArgumentException("Cliente no puede ser nulo ni vacio");
        }
        if (personas < 1 || personas > 8) {
            throw new IllegalArgumentException("Capacidad de mesa permitida: 1 a 8 personas");
        }
        if (hora == null || hora.isBlank()) {
            throw new IllegalArgumentException("Hora obligatoria");
        }
        this.id = id;
        this.cliente = cliente;
        this.personas = personas;
        this.hora = hora;
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }
    public int getPersonas() { return personas; }
    public String getHora() { return hora; }

    public void setHora(String hora) {
        if (hora == null || hora.isBlank()) {
            throw new IllegalArgumentException("Hora invalida");
        }
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Reserva #" + id + " · " + cliente + " (" + personas + "p) a las " + hora;
    }
}
