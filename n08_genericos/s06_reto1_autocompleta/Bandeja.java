import java.util.ArrayList;
import java.util.List;

public class Bandeja<T> {
    private final List<T> cosas = new ArrayList<>();
    private final int capacidad;

    public Bandeja(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean poner(T cosa) {
        if (cosas.size() == capacidad) {
            return false;
        }
        return cosas.add(cosa);
    }

    public T primera() {
        return cosas.get(0);
    }

    public int cantidad() {
        return cosas.size();
    }
}
