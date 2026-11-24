import java.util.EnumMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Mesa m1 = new Mesa(1, 4);
        Mesa m2 = new Mesa(2, 2);
        Mesa m3 = new Mesa(3, 6);
        Mesa m4 = new Mesa(4, 4);

        m2.setEstado(EstadoMesa.RESERVADA);
        m3.setEstado(EstadoMesa.OCUPADA);
        m4.setEstado(EstadoMesa.POR_LIMPIAR);

        List<Mesa> salon = List.of(m1, m2, m3, m4);

        IO.println("--- Estado Inicial del Salon ---");
        for (Mesa m : salon) {
            IO.println(m.resumen());
        }

        // Simulamos transicion de estado en mesa 1 y mesa 3
        m1.avanzarEstado(); // De LIBRE a OCUPADA
        m3.avanzarEstado(); // De OCUPADA a POR_LIMPIAR

        IO.println();
        IO.println("--- Estado tras Transiciones ---");
        for (Mesa m : salon) {
            IO.println(m.resumen());
        }

        // Conteo con EnumMap
        var censo = new EnumMap<EstadoMesa, Integer>(EstadoMesa.class);
        for (EstadoMesa est : EstadoMesa.values()) {
            censo.put(est, 0);
        }
        for (Mesa m : salon) {
            censo.put(m.getEstado(), censo.get(m.getEstado()) + 1);
        }

        IO.println();
        IO.println("--- Conteo del Tablero de Salon (EnumMap) ---");
        for (EstadoMesa est : EstadoMesa.values()) {
            IO.println(est + ": " + censo.get(est) + " mesas");
        }
    }
}
