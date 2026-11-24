public record ItemMenu(String nombre, Categoria categoria, int precio) {
    public ItemMenu {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre no puede estar en blanco");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("Precio debe ser positivo: " + precio);
        }
    }

    public ItemMenu withPrecio(int nuevoPrecio) {
        return new ItemMenu(nombre, categoria, nuevoPrecio);
    }
}
