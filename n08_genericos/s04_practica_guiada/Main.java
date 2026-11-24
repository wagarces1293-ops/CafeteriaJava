public class Main {
    public static void main(String[] args) {
        Par<String, Integer> productoPrecio = new Par<>("Capuchino", 9000);
        Par<Integer, String> mesaCliente = new Par<>(4, "Valeria");

        IO.println("Producto y precio: " + productoPrecio.getPrimero() + " cuesta $" + productoPrecio.getSegundo());
        IO.println("Mesa asignada: " + mesaCliente);
    }
}
