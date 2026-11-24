public abstract class Producto {
    private final String nombre;
    private final int precio;

    protected Producto(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; }

    public abstract int minutosPreparacion();

    public String ficha() {
        return nombre + " $" + precio + " · " + minutosPreparacion() + " min";
    }
}
