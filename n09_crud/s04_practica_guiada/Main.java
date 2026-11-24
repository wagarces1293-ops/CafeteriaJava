import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> inventario = new ArrayList<>();
        inventario.add("Café tostado (5 kg)");
        inventario.add("Canela en rama (250 g)");

        IO.println("--- Sistema de Barra - Opciones Similares a Menu ---");
        IO.println("[1] Crear [2] Listar [3] Cantidad [0] Salir");

        // Simula la ejecucion de listar
        IO.println("Listado actual: " + inventario);
        IO.println("Cantidad de items: " + inventario.size());
    }
}
