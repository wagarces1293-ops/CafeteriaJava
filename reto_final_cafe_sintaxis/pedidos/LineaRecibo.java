package pedidos;

import java.io.Serializable;

public record LineaRecibo(String producto, int precioUnitario, int cantidad, int subtotal) implements Serializable {
    public LineaRecibo {
        if (producto == null || producto.isBlank()) throw new IllegalArgumentException("Producto invalido");
        if (cantidad < 1) throw new IllegalArgumentException("Cantidad minima: 1");
    }
}
