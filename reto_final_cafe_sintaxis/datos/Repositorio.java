package datos;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T extends Identificable> {
    void guardar(T item);
    Optional<T> porId(int id);
    List<T> listar();
    boolean eliminar(int id);
    int cantidad();
}
