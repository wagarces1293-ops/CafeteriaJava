import java.util.List;

public record Recibo(int numero, List<LineaRecibo> lineas) {
    public Recibo {
        lineas = List.copyOf(lineas); // copia inmutable
    }

    public int total() {
        int t = 0;
        for (LineaRecibo l : lineas) t += l.subtotal();
        return t;
    }
}
