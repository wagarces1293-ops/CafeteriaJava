public class Producto {
    // Atributos: lo que cada producto SABE (su estado)
    String nombre;
    int precio; // en pesos
    int stock;  // unidades disponibles

    // Metodos: lo que cada producto HACE (su comportamiento)
    String descripcion() {
        return nombre + " · $" + precio + " · stock " + stock;
    }

    boolean hayStock() {
        return stock > 0;
    }

    int vender(int cantidad) {
        if (cantidad > stock) {
            IO.println("No alcanza: hay " + stock);
            return 0;
        }
        stock = stock - cantidad;
        return cantidad * precio; // total a cobrar
    }
}
