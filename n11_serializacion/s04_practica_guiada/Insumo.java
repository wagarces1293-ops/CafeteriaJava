public record Insumo(int id, String nombre, int cantidad) {
    public Insumo {
        if (cantidad < 0) {
            throw new IllegalArgumentException("Cantidad negativa: " + cantidad);
        }
    }
}
