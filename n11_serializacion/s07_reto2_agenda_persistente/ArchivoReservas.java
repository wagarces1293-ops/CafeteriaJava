import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoReservas {
    private final Path archivoBinario;
    private static final String FILTRO_LISTA_BLANCA = "Reserva;java.util.ArrayList;java.lang.Object;!*";

    public ArchivoReservas(Path archivoBinario) {
        this.archivoBinario = archivoBinario;
    }

    public void guardar(List<Reserva> reservas) throws IOException {
        try (var out = new ObjectOutputStream(Files.newOutputStream(archivoBinario))) {
            out.writeObject(new ArrayList<>(reservas));
        }
    }

    @SuppressWarnings("unchecked")
    public List<Reserva> cargar() throws IOException, ClassNotFoundException {
        if (Files.notExists(archivoBinario)) {
            return new ArrayList<>();
        }
        try (var in = new ObjectInputStream(Files.newInputStream(archivoBinario))) {
            in.setObjectInputFilter(ObjectInputFilter.Config.createFilter(FILTRO_LISTA_BLANCA));
            return (List<Reserva>) in.readObject();
        }
    }

    public void exportarCSV(List<Reserva> reservas, Path archivoCSV) throws IOException {
        List<String> lineas = new ArrayList<>();
        lineas.add("id;cliente;personas;hora"); // Encabezado para contabilidad
        for (Reserva r : reservas) {
            lineas.add(r.id() + ";" + r.cliente() + ";" + r.personas() + ";" + r.hora());
        }
        Files.write(archivoCSV, lineas);
    }
}
