package modelo;

public class Bebida extends Producto {
    private final Tamano tamano;
    private final boolean conLeche;

    public Bebida(int id, String nombre, Categoria categoria, int precioBase, Tamano tamano, boolean conLeche) {
        super(id, nombre, categoria, precioBase);
        if (tamano == null) throw new IllegalArgumentException("Tamano requerido");
        this.tamano = tamano;
        this.conLeche = conLeche;
    }

    @Override
    public int calcularPrecio() {
        return getPrecioBase() + tamano.getRecargo();
    }

    @Override
    public String descripcion() {
        String desc = getNombre() + " (" + tamano + " " + tamano.getMl() + "ml) · $" + calcularPrecio();
        if (conLeche) desc += " [con leche]";
        return desc;
    }
}
