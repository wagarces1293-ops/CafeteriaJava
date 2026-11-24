public class Postre {
    private final String nombre;
    private final int precio;
    private final boolean sinAzucar;

    public Postre(String nombre, int precio, boolean sinAzucar) {
        this.nombre = nombre;
        this.precio = precio;
        this.sinAzucar = sinAzucar;
    }

    public Postre(String nombre, int precio) {
        this(nombre, precio, false);
    }

    public String etiqueta() {
        return nombre + " $" + precio;
    }

    public String etiqueta(boolean detallada) {
        if (detallada && sinAzucar) {
            return etiqueta() + " · sin azúcar";
        }
        return etiqueta();
    }
}
