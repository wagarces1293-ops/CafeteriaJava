import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Entrega> entregas = List.of(
            new EnMesa(201, 4),
            new ParaLlevar(202, 1200),
            new Domicilio(203, "Carrera 7 # 45-20", "3001234567")
        );

        for (Entrega e : entregas) {
            IO.println(e.resumen());
            if (e instanceof Rastreable r) {
                IO.println(" -> Rastreo en tiempo real: " + r.rastrear());
            }
        }
    }
}
