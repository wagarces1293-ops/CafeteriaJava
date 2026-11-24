import java.io.Serializable;

public record Reserva(int id, String cliente, int personas, String hora) implements Serializable {
    public Reserva {
        if (id <= 0) {
            throw new IllegalArgumentException("Id invalido");
        }
        if (cliente == null || cliente.isBlank()) {
            throw new IllegalArgumentException("Cliente no puede estar vacio");
        }
        if (personas < 1 || personas > 8) {
            throw new IllegalArgumentException("Personas debe ser entre 1 y 8");
        }
        if (hora == null || hora.isBlank()) {
            throw new IllegalArgumentException("Hora invalida");
        }
    }
}
