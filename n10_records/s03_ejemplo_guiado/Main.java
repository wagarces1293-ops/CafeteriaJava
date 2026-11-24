import java.util.List;

public class Main {
    public static void main(String[] args) {
        var l1 = new LineaRecibo("Latte", 9500, 2);
        var l2 = new LineaRecibo("Brownie", 7000, 1);
        IO.println(l1);
        IO.println(l1.producto() + " → $" + l1.subtotal());

        var otra = new LineaRecibo("Latte", 9500, 2);
        IO.println("¿Iguales? " + l1.equals(otra));

        var r = new Recibo(1001, List.of(l1, l2));
        IO.println("Recibo " + r.numero() + ": $" + r.total());

        if (r.lineas().get(0) instanceof LineaRecibo l) {
            IO.println(l.cantidad() + " × " + l.producto() + " a $" + l.precio());
        }
    }
}
