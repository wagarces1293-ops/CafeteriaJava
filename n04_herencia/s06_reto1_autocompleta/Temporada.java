public class Temporada extends Producto {
    private final String mes;

    public Temporada(String nombre, int precio, String mes) {
        super(nombre, precio);
        this.mes = mes;
    }

    @Override
    public String descripcion() {
        return super.descripcion() + " (solo " + mes + ")";
    }
}
