package pedidos;

import java.io.Serializable;
import java.util.List;

public record Recibo(int numero, String fecha, String cliente, List<LineaRecibo> lineas, int totalBruto, int totalNeto) implements Serializable {
    public Recibo {
        lineas = List.copyOf(lineas); // Inmutabilidad profunda
    }
}
