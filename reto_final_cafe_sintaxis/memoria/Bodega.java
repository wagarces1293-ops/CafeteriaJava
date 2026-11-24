package memoria;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Bodega {
    private final Path archivo;
    private static final String WHITELIST = "memoria.Insumo;java.util.ArrayList;java.lang.Object;!*";

    public Bodega(Path archivo) {
        this.archivo = archivo;
    }

    public void guardar(List<Insumo> inventario) throws IOException {
        try (var out = new ObjectOutputStream(Files.newOutputStream(archivo))) {
            out.writeObject(new ArrayList<>(inventario));
        }
    }

    @SuppressWarnings("unchecked")
    public List<Insumo> cargar() throws IOException, ClassNotFoundException {
        if (Files.notExists(archivo)) {
            return new ArrayList<>();
        }
        try (var in = new ObjectInputStream(Files.newInputStream(archivo))) {
            in.setObjectInputFilter(ObjectInputFilter.Config.createFilter(WHITELIST));
            return (List<Insumo>) in.readObject();
        }
    }
}
