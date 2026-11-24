public class LineaPedido {
    private final Producto producto;
    private final int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser positiva");
        }
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int subtotal() {
        return producto.getPrecio() * cantidad;
    }

    public String resumen() {
        return cantidad + "x " + producto.getNombre() + " ($ " + subtotal() + ")";
    }
}
