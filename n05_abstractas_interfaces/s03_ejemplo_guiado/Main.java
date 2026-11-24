import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Producto> menu = List.of(
            new Bebida("Capuchino", 9000),
            new Postre("Brownie", 7000)
        );

        for (Producto p : menu) {
            IO.println(p.ficha());
            if (p instanceof Personalizable per) {
                IO.println("  opciones: " + per.opciones());
                IO.println("  avena: +$" + per.extra("avena"));
            }
        }
    }
}
