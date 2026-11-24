import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String aLinea(Insumo i) {
        return i.id() + ";" + i.nombre() + ";" + i.cantidad();
    }

    public static Insumo deLinea(String linea) {
        String[] p = linea.split(";");
        return new Insumo(Integer.parseInt(p[0]), p[1], Integer.parseInt(p[2]));
    }

    public static void main(String[] args) throws Exception {
        List<Insumo> hoy = List.of(
            new Insumo(1, "Leche entera (L)", 20),
            new Insumo(2, "Café en grano (kg)", 5)
        );
        Path csv = Path.of("n11_serializacion/s04_practica_guiada/bodega.csv");
        List<String> lineas = new ArrayList<>();
        for (Insumo i : hoy) lineas.add(aLinea(i));
        Files.write(csv, lineas);

        IO.print(Files.readString(csv));
        List<Insumo> leidos = new ArrayList<>();
        for (String l : Files.readAllLines(csv)) {
            leidos.add(deLinea(l));
        }
        IO.println(Files.size(csv) + " bytes · ¿iguales? " + hoy.equals(leidos));
    }
}
