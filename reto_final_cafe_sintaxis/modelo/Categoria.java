package modelo;

public enum Categoria {
    CALIENTE("Bebidas Calientes"),
    FRIA("Bebidas Frías"),
    REPOSTERIA("Repostería y Panadería");

    private final String etiqueta;

    Categoria(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() { return etiqueta; }
}
