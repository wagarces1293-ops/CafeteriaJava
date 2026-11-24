public class Main {
    public static void main(String[] args) {
        Cocina c = new Cocina(new ImpresoraConsola());
        c.enviarComanda("Sándwich de pollo", 4);
    }
}
