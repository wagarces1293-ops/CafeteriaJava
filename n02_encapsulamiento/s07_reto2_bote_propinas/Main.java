public class Main {
    public static void main(String[] args) {
        BotePropinas bote = new BotePropinas();

        // Aportes validos
        bote.aportar(5000);
        bote.aportar(7000);
        bote.aportar(3500);

        IO.println("Total en el bote: $" + bote.getTotal());
        IO.println("Numero de aportes: " + bote.getAportes());

        // Reparto equitativo entre 3 baristas
        int porPersona = bote.repartir(3);
        int remanente = bote.sobrante(3);
        IO.println("Corresponde por persona: $" + porPersona);
        IO.println("Sobrante en el bote: $" + remanente);
        IO.println("Comprobacion invariante (3 * " + porPersona + " + " + remanente + "): $" + (3 * porPersona + remanente));

        // Caso de prueba: aporte invalido
        try {
            bote.aportar(-1000);
        } catch (IllegalArgumentException e) {
            IO.println("Capturado aporte invalido: " + e.getMessage());
        }

        // Caso de prueba: repartir con 0 personas
        try {
            bote.repartir(0);
        } catch (IllegalArgumentException e) {
            IO.println("Capturado reparto invalido: " + e.getMessage());
        }
    }
}
