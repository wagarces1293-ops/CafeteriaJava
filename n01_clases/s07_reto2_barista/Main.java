public class Main {
    public static void main(String[] args) {
        Barista b1 = new Barista();
        b1.nombre = "Martin";
        b1.turno = "manana";

        Barista b2 = new Barista();
        b2.nombre = "Ana";
        b2.turno = "tarde";

        // Martin prepara 3 bebidas
        for (int i = 0; i < 3; i++) {
            b1.preparar("Latte");
        }

        // Ana prepara 21 bebidas
        for (int i = 0; i < 21; i++) {
            b2.preparar("Capuchino");
        }

        IO.println(b1.resumen());
        IO.println("¿Martin merece pausa? " + b1.merecePausa());

        IO.println(b2.resumen());
        IO.println("¿Ana merece pausa? " + b2.merecePausa());

        // Caso limite: Barista con exactamente 20 bebidas
        Barista bLimite = new Barista();
        bLimite.nombre = "Julian";
        bLimite.turno = "noche";
        for (int i = 0; i < 20; i++) {
            bLimite.preparar("Espresso");
        }
        IO.println(bLimite.resumen());
        IO.println("¿Caso limite (20 bebidas) merece pausa? " + bLimite.merecePausa());

        // Extra propinas
        b1.recibirPropina(5000);
        b1.recibirPropina(-2000); // Se debe ignorar
        IO.println("Propinas acumuladas de Martin: $" + b1.propinas);
    }
}
