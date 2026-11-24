package pedidos;

import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

public class Pedido {
    private final int id;
    private final String cliente;
    private final List<LineaPedido> lineas;

    public Pedido(int id, String cliente) {
        if (id <= 0) throw new IllegalArgumentException("Id invalido: " + id);
        if (cliente == null || cliente.isBlank()) throw new IllegalArgumentException("Cliente requerido");
        this.id = id;
        this.cliente = cliente;
        this.lineas = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getCliente() { return cliente; }

    public void agregar(Producto p, int cantidad) {
        lineas.add(new LineaPedido(p, cantidad));
    }

    public List<LineaPedido> getLineas() {
        return new ArrayList<>(lineas);
    }

    public int total() {
        int suma = 0;
        for (LineaPedido l : lineas) suma += l.subtotal();
        return suma;
    }
}
