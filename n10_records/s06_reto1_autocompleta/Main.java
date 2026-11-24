public class Main {
    public static void main(String[] args) {
        var a = new Calificacion("Sofía", 5);
        var b = new Calificacion("Sofía", 5);
        IO.println(a.cliente() + ": " + a.estrellas());
        IO.println("¿Buena? " + a.esBuena());
        IO.println("¿Iguales? " + a.equals(b));
        IO.println(b);
    }
}
