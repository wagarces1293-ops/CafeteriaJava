package datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioMemoria<T extends Identificable> implements Repositorio<T> {
    private final List<T> elementos = new ArrayList<>();

    @Override
    public void guardar(T item) {
        if (item == null) throw new IllegalArgumentException("Item no puede ser nulo");
        for (int i = 0; i < elementos.size(); i++) {
            if (elementos.get(i).id() == item.id()) {
                elementos.set(i, item); // Actualiza si ya existe
                return;
            }
        }
        elementos.add(item);
    }

    @Override
    public Optional<T> porId(int id) {
        for (T e : elementos) {
            if (e.id() == id) return Optional.of(e);
        }
        return Optional.empty();
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(elementos); // Copia defensiva
    }

    @Override
    public boolean eliminar(int id) {
        for (int i = 0; i < elementos.size(); i++) {
            if (elementos.get(i).id() == id) {
                elementos.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int cantidad() {
        return elementos.size();
    }
}
