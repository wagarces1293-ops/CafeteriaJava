import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Agenda {
    private final List<Reserva> reservas = new ArrayList<>();
    private int siguienteId = 1;

    // C - Crear
    public Reserva crear(String cliente, int personas, String hora) {
        Reserva r = new Reserva(siguienteId++, cliente, personas, hora);
        reservas.add(r);
        return r;
    }

    // R - Listar copia defensiva
    public List<Reserva> listar() {
        return new ArrayList<>(reservas);
    }

    // R - Buscar con Optional (cero retornos null)
    public Optional<Reserva> buscar(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return Optional.of(r);
            }
        }
        return Optional.empty();
    }

    // U - Actualizar hora
    public boolean actualizarHora(int id, String nuevaHora) {
        Optional<Reserva> opt = buscar(id);
        if (opt.isEmpty()) {
            return false;
        }
        opt.get().setHora(nuevaHora);
        return true;
    }

    // D - Eliminar
    public boolean eliminar(int id) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId() == id) {
                reservas.remove(i);
                return true;
            }
        }
        return false;
    }

    // Extra +10 XP: Ordenar por hora con Comparator estandar sin lambdas complejas
    public List<Reserva> listarOrdenadoPorHora() {
        List<Reserva> copia = new ArrayList<>(reservas);
        Collections.sort(copia, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                return r1.getHora().compareTo(r2.getHora());
            }
        });
        return copia;
    }
}
