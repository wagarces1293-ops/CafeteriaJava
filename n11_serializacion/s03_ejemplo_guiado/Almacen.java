import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Almacen {
    private final Path archivo;
    private static final String PERMITIDOS = "Insumo;java.util.ArrayList;java.lang.Object;!*";

    public Almacen(Path archivo) {
        this.archivo = archivo;
    }

    public void guardar(List<Insumo> insumos) throws IOException {
        try (var out = new ObjectOutputStream(Files.newOutputStream(archivo))) {
            out.writeObject(new ArrayList<>(insumos));
        }
    }

    @SuppressWarnings("unchecked")
    public List<Insumo> cargar() throws IOException, ClassNotFoundException {
        if (Files.notExists(archivo)) {
            return List.of();
        }
        try (var in = new ObjectInputStream(Files.newInputStream(archivo))) {
            in.setObjectInputFilter(ObjectInputFilter.Config.createFilter(PERMITIDOS));
            return (List<Insumo>) in.readObject();
        }
    }
}
