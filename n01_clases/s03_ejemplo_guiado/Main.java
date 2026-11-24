public class Main {
    public static void main(String[] args) {
        Producto latte = new Producto();
        latte.nombre = "Latte";
        latte.precio = 9500;
        latte.stock = 3;

        Producto brownie = new Producto();
        brownie.nombre = "Brownie";
        brownie.precio = 7000;

        IO.println(latte.descripcion());
        IO.println("¿Hay brownie? " + brownie.hayStock());
        int cobro = latte.vender(2);
        IO.println("Cobrar: $" + cobro);
        IO.println(latte.descripcion());
        latte.vender(5);
    }
}
