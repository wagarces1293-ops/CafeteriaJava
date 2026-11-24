public enum Categoria {
    CALIENTE("Bebida caliente"),
    FRIA("Bebida fría"),
    POSTRE("Repostería");

    private final String descripcion;

    Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
