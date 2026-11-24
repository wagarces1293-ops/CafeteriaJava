public class Main {
    public static void main(String[] args) {
        Producto latte = new Producto();
        latte.setNombre("Latte");
        latte.setPrecio(9500);
        IO.println(latte.getNombre() + " $" + latte.getPrecio());

        try {
            latte.setPrecio(-9500);
        } catch (IllegalArgumentException e) {
            IO.println(e.getMessage());
        }
        IO.println("Sigue en $" + latte.getPrecio());

        // Prueba de practica guiada
        latte.reponer(5);
        IO.println("Stock actual: " + latte.getStock());
        try {
            latte.reponer(0);
        } catch (IllegalArgumentException e) {
            IO.println("Rechazado reponer: " + e.getMessage());
        }
    }
}
