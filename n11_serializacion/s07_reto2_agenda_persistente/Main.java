import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Path rutaBin = Path.of("n11_serializacion/s07_reto2_agenda_persistente/reservas.ser");
        Path rutaCSV = Path.of("n11_serializacion/s07_reto2_agenda_persistente/reservas.csv");

        ArchivoReservas gestor = new ArchivoReservas(rutaBin);

        try {
            // 1. Cargar al inicio (si no existe, inicia vacio)
            List<Reserva> activas = gestor.cargar();
            IO.println("Reservas al iniciar la jornada: " + activas.size());

            // 2. Registrar reservas del dia
            List<Reserva> hoy = List.of(
                new Reserva(1, "Valeria", 4, "18:00"),
                new Reserva(2, "Martin", 2, "19:30"),
                new Reserva(3, "Ana", 6, "20:15")
            );

            // 3. Guardar al cerrar
            gestor.guardar(hoy);
            IO.println("Persistencia binaria completada: " + Files.size(rutaBin) + " bytes");

            // 4. Exportar a CSV para administracion
            gestor.exportarCSV(hoy, rutaCSV);
            IO.println("CSV exportado exitosamente:");
            IO.println(Files.readString(rutaCSV));

            // 5. Simular reanudacion y lectura tras reinicio
            List<Reserva> recuperadas = gestor.cargar();
            IO.println("Reservas recuperadas al reiniciar: " + recuperadas.size());
            IO.println("¿Integridad total garantizada? " + hoy.equals(recuperadas));

        } catch (IOException | ClassNotFoundException e) {
            IO.println("Error en operacion de archivos: " + e.getMessage());
        }
    }
}
