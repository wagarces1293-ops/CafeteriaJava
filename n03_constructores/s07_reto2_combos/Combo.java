public class Combo {
    private final String bebida;
    private final String postre;
    private final int precio;

    // Constructor completo que valida las entradas
    public Combo(String bebida, String postre, int precio) {
        if (bebida.isBlank() || postre.isBlank()) {
            throw new IllegalArgumentException("Los nombres de bebida y postre son obligatorios");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio del combo debe ser mayor a cero: " + precio);
        }
        this.bebida = bebida;
        this.postre = postre;
        this.precio = precio;
    }

    // Constructor corto con precio estandar promocional de 12000
    public Combo(String bebida, String postre) {
        this(bebida, postre, 12000);
    }

    // Metodo de descripcion estandar
    public String descripcion() {
        return "Combo: " + bebida + " + " + postre + " · $" + precio;
    }

    // Sobrecarga de descripcion con descuento por porcentaje
    public String descripcion(int porcentajeDescuento) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100) {
            throw new IllegalArgumentException("Descuento invalido: " + porcentajeDescuento);
        }
        int precioFinal = precio * (100 - porcentajeDescuento) / 100;
        return descripcion() + " (Promo " + porcentajeDescuento + "%: $" + precioFinal + ")";
    }

    public String getBebida() { return bebida; }
    public String getPostre() { return postre; }
    public int getPrecio() { return precio; }
}
