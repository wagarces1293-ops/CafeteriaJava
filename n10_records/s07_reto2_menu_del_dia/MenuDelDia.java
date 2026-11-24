import java.util.List;

public record MenuDelDia(String fecha, List<ItemMenu> items) {
    public MenuDelDia {
        if (fecha == null || fecha.isBlank()) {
            throw new IllegalArgumentException("Fecha obligatoria");
        }
        items = List.copyOf(items); // inmutabilidad defensiva
    }

    public int promedioPrecio() {
        if (items.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (ItemMenu item : items) {
            total += item.precio();
        }
        return total / items.size();
    }
}
