public class Main {
    public static void main(String[] args) {
        Cliente sofia = new Cliente();
        sofia.nombre = "Sofia";
        sofia.puntos = 41;
        sofia.sumarPuntos(12500);

        IO.println(sofia.tarjeta());
        IO.println("¿Cafe gratis? " + sofia.puedeCanjear());

        // Ejercicio practico guiado adicional
        sofia.canjear();
        IO.println("Despues del canje: " + sofia.tarjeta());

        Cliente carlos = new Cliente();
        carlos.nombre = "Carlos";
        carlos.puntos = 0;
        carlos.sumarPuntos(9999);
        IO.println(carlos.tarjeta() + " (compra de 999)");
    }
}
