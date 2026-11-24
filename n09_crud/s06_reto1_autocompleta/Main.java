import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> espera = new ArrayList<>();
        espera.add("Rojas (4)");
        espera.add("Gómez (2)");
        espera.add("Pérez (3)");

        IO.println("En espera: " + espera.size());
        IO.println("Sigue: " + espera.get(0));

        espera.set(1, "Gómez (5)");
        for (int i = 0; i < espera.size(); i++) {
            if (espera.get(i).startsWith("Rojas")) {
                espera.remove(i);
                break;
            }
        }

        IO.println(espera);
        IO.println("¿Pérez? " + espera.contains("Pérez (3)"));
    }
}
