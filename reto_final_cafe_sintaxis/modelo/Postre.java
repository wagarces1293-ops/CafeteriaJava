package modelo;

public class Postre extends Producto {
    private final boolean vegano;

    public Postre(int id, String nombre, int precioBase, boolean vegano) {
        super(id, nombre, Categoria.REPOSTERIA, precioBase);
        this.vegano = vegano;
    }

    @Override
    public int calcularPrecio() {
        return getPrecioBase();
    }

    @Override
    public String descripcion() {
        String desc = getNombre() + " · $" + calcularPrecio();
        if (vegano) desc += " [Vegano]";
        return desc;
    }
}
