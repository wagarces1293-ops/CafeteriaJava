public class Main {
    public static void main(String[] args) {
        Producto ponque = new Temporada("Ponqué de calabaza", 8500, "octubre");
        Combo c = new Combo(new Bebida("Latte", 9500, 350), ponque);
        IO.println(ponque.descripcion());
        IO.println("Combo: $" + c.precio());
    }
}
