public class Main {
    public static void main(String[] args) {
        Producto latte = new Producto("Latte", 9500);
        Producto muffin = new Producto("Muffin", 5000);

        Pedido pedido = new Pedido(101);
        pedido.agregar(latte, 2);
        pedido.agregar(muffin, 1);

        pedido.mostrar();
    }
}
