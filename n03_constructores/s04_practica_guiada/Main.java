public class Main {
    public static void main(String[] args) {
        Reserva r1 = new Reserva("Valeria", 4, "20:30");
        Reserva r2 = new Reserva("Martin", 2);

        IO.println(r1.aviso());
        IO.println(r2.aviso(15));

        try {
            new Reserva("Grupo Grande", 12, "18:00");
        } catch (IllegalArgumentException e) {
            IO.println("Rechazada: " + e.getMessage());
        }
    }
}
