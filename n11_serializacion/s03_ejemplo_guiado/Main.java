import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Path p = Path.of("n11_serializacion/s03_ejemplo_guiado/bodega.ser");
        Files.deleteIfExists(p); // Limpieza inicial

        var almacen = new Almacen(p);
        IO.println("Al abrir: " + almacen.cargar());

        List<Insumo> hoy = List.of(
            new Insumo(1, "Leche entera (L)", 20),
            new Insumo(2, "Café en grano (kg)", 5)
        );
        almacen.guardar(hoy);

        long bytes = Files.size(p);
        IO.println("Guardado: " + bytes + " bytes");

        List<Insumo> leidos = almacen.cargar();
        IO.println("Leído: " + leidos);
        IO.println("¿Iguales? " + hoy.equals(leidos));
    }
}
