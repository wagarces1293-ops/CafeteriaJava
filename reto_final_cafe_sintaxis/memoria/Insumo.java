package memoria;

import datos.Identificable;
import java.io.Serializable;

public record Insumo(int id, String nombre, int cantidad, String unidad) implements Serializable, Identificable {
    public Insumo {
        if (id <= 0) throw new IllegalArgumentException("Id invalido: " + id);
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido");
        if (cantidad < 0) throw new IllegalArgumentException("Cantidad negativa no permitida");
        if (unidad == null || unidad.isBlank()) throw new IllegalArgumentException("Unidad requerida");
    }
}
