public class Producto {
    private String nombre;
    private int precio;
    private int stock;

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(int precio) {
        if (precio < 0 || precio > 50000) {
            throw new IllegalArgumentException("Precio fuera de rango: " + precio);
        }
        this.precio = precio;
    }

    // Metodo de practica guiada: reponer stock con regla de negocio
    public void reponer(int unidades) {
        if (unidades <= 0) {
            throw new IllegalArgumentException("Unidades para reponer deben ser mayores a 0: " + unidades);
        }
        this.stock += unidades;
    }
}
