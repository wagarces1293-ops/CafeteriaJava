import java.util.ArrayList;
import java.util.List;

public class Historial<T> {
    private final int capacidad;
    private final List<T> elementos = new ArrayList<>();

    public Historial(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("Capacidad debe ser mayor a 0: " + capacidad);
        }
        this.capacidad = capacidad;
    }

    public void agregar(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException("No se permite agregar elementos nulos al historial");
        }
        if (elementos.size() == capacidad) {
            elementos.remove(0); // Elimina el mas antiguo (FIFO)
        }
        elementos.add(elemento);
    }

    public List<T> ultimos() {
        return new ArrayList<>(elementos); // Copia defensiva
    }

    public int cantidad() {
        return elementos.size();
    }
}
