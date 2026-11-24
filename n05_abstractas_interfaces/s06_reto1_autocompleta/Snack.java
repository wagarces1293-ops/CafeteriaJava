public abstract class Snack implements Descontable {
    private final int precio;

    protected Snack(int precio) {
        this.precio = precio;
    }

    @Override
    public int precioBase() {
        return precio;
    }

    public abstract String nombre();
}
