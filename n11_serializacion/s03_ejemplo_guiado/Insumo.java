import java.io.Serializable;

public record Insumo(int id, String nombre, int cantidad) implements Serializable {
    public Insumo {
        if (cantidad < 0) {
            throw new IllegalArgumentException("Cantidad negativa");
        }
    }
}
