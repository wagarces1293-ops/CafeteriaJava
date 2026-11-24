package memoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExportadorCSV {
    public static void exportar(List<Insumo> insumos, Path destino) throws IOException {
        List<String> lineas = new ArrayList<>();
        lineas.add("id;nombre;cantidad;unidad");
        for (Insumo i : insumos) {
            lineas.add(i.id() + ";" + i.nombre() + ";" + i.cantidad() + ";" + i.unidad());
        }
        Files.write(destino, lineas);
    }
}
