public class Main {
    public static void main(String[] args) {
        Bebida a = new Bebida("Latte", "grande", 11000, true);
        Bebida b = new Bebida("Tinto", 3500);

        IO.println(a.descripcion());
        IO.println(b.descripcion());
        IO.println("Latte + 2 extras: $" + a.precioCon(2));
        IO.println("Con cupón: $" + a.precioCon("CAFE10"));

        try {
            new Bebida(" ", 3000);
        } catch (IllegalArgumentException e) {
            IO.println("Rechazada: " + e.getMessage());
        }
    }
}
