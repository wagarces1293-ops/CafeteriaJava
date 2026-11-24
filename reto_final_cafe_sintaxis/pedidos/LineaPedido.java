package pedidos;

import modelo.Producto;

public class LineaPedido {
    private final Producto producto;
    private final int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("Producto requerido");
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser mayor a 0: " + cantidad);
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public int subtotal() {
        return producto.calcularPrecio() * cantidad;
    }

    public String resumen() {
        return cantidad + "x " + producto.getNombre() + " ($ " + subtotal() + ")";
    }
}
