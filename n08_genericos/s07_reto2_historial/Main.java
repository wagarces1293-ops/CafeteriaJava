import java.util.List;

public class Main {
    public static void main(String[] args) {
        Historial<String> pedidosRecientes = new Historial<>(3);

        pedidosRecientes.agregar("Latte grande");
        pedidosRecientes.agregar("Tinto mediano");
        pedidosRecientes.agregar("Capuchino");

        IO.println("Historial lleno (3 pedidos): " + pedidosRecientes.ultimos());

        // Al agregar un cuarto pedido, debe descartar 'Latte grande'
        pedidosRecientes.agregar("Moka especial");
        IO.println("Historial tras nuevo ingreso: " + pedidosRecientes.ultimos());

        // Prueba con otro tipo (Historial de montos de cobro Integer)
        Historial<Integer> montos = new Historial<>(2);
        montos.agregar(12000);
        montos.agregar(7500);
        montos.agregar(24000); // Descarta 12000
        IO.println("Ultimos montos cobrados: " + montos.ultimos());
    }
}
