import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final int id;
    private final List<LineaPedido> lineas;

    public Pedido(int id) {
        this.id = id;
        this.lineas = new ArrayList<>();
    }

    public void agregar(Producto p, int cantidad) {
        lineas.add(new LineaPedido(p, cantidad));
    }

    public int total() {
        int suma = 0;
        for (LineaPedido l : lineas) {
            suma += l.subtotal();
        }
        return suma;
    }

    public void mostrar() {
        IO.println("Pedido #" + id + ":");
        for (LineaPedido l : lineas) {
            IO.println(" - " + l.resumen());
        }
        IO.println("Total: $" + total());
    }
}
