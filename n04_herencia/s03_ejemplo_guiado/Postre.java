public class Postre extends Producto {
    private final boolean vegano;

    public Postre(String nombre, int precio, boolean vegano) {
        super(nombre, precio);
        this.vegano = vegano;
    }

    @Override
    public String descripcion() {
        if (vegano) {
            return super.descripcion() + " · vegano";
        }
        return super.descripcion();
    }
}
