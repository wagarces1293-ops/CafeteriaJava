import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bebida latte = new Bebida("Latte", 9500, 350);
        Postre torta = new Postre("Torta de zanahoria", 8000, true);
        IO.println(latte.getNombre() + " cuesta " + latte.getPrecio());

        List<Producto> vitrina = List.of(
            latte,
            torta,
            new Producto("Galleta", 3000)
        );

        for (Producto p : vitrina) {
            IO.println(p.descripcion());
        }
    }
}
