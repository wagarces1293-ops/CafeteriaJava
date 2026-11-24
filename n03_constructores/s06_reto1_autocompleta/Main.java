public class Main {
    public static void main(String[] args) {
        Postre a = new Postre("Brownie", 7000);
        Postre b = new Postre("Cheesecake", 9000, true);
        IO.println(a.etiqueta(true));
        IO.println(b.etiqueta(true));
    }
}
