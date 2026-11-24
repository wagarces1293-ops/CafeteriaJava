public class Bebida extends Producto {
    private final int ml;

    public Bebida(String nombre, int precio, int ml) {
        super(nombre, precio);
        this.ml = ml;
    }
}
