import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Path f = Path.of("n11_serializacion/s06_reto1_autocompleta/turno.ser");
        Files.deleteIfExists(f);

        try (var out = new ObjectOutputStream(Files.newOutputStream(f))) {
            out.writeObject(new Turno("Martín", 42));
        }

        try (var in = new ObjectInputStream(Files.newInputStream(f))) {
            in.setObjectInputFilter(ObjectInputFilter.Config.createFilter("Turno;java.lang.Object;!*"));
            Turno t = (Turno) in.readObject();
            IO.println(t + " · " + Files.size(f) + " bytes");
        }
    }
}
