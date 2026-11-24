import java.util.EnumMap;

public class Main {
    public static void main(String[] args) {
        var ventasPorCategoria = new EnumMap<Categoria, Integer>(Categoria.class);

        // Inicializamos o acumulamos ventas
        ventasPorCategoria.put(Categoria.CALIENTE, 15);
        ventasPorCategoria.put(Categoria.FRIA, 8);
        ventasPorCategoria.put(Categoria.POSTRE, 12);

        for (Categoria cat : Categoria.values()) {
            int total = ventasPorCategoria.getOrDefault(cat, 0);
            IO.println(cat + " (" + cat.getDescripcion() + "): " + total + " vendidos");
        }
    }
}
