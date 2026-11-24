import java.util.List;

public class Main {
    public static <T> T ultimo(List<T> lista) {
        return lista.get(lista.size() - 1);
    }

    public static int sumarPrecios(List<? extends Producto> lista) {
        int s = 0;
        for (Producto p : lista) s += p.getPrecio();
        return s;
    }

    public static void main(String[] args) {
        var productos = new Repositorio<Producto>();
        productos.guardar(new Producto(1, "Latte", 9500));
        productos.guardar(new Producto(2, "Brownie", 7000));

        var clientes = new Repositorio<Cliente>();
        clientes.guardar(new Cliente(7, "Sofía"));

        Producto p = productos.porId(2);
        IO.println(p.getNombre() + " $" + p.getPrecio());
        IO.println(clientes.porId(7).nombre());
        IO.println(ultimo(List.of("tinto", "latte", "moka")));

        var mas = List.of(new Producto(3, "Té", 4000), new Producto(4, "Moka", 10500));
        IO.println("Total: $" + sumarPrecios(mas));
    }
}
