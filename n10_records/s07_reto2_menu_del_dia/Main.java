import java.util.List;

public class Main {
    public static void main(String[] args) {
        ItemMenu item1 = new ItemMenu("Espresso Doble", Categoria.BEBIDA, 6500);
        ItemMenu item2 = new ItemMenu("Cheesecake de Frutos Rojos", Categoria.POSTRE, 9000);
        ItemMenu item3 = new ItemMenu("Sandwich Artesanal", Categoria.SNACK, 12500);

        MenuDelDia menu = new MenuDelDia("2026-11-23", List.of(item1, item2, item3));

        IO.println("--- Menu del Dia: " + menu.fecha() + " ---");
        for (ItemMenu it : menu.items()) {
            IO.println(" - " + it.nombre() + " (" + it.categoria() + "): $" + it.precio());
        }
        IO.println("Precio promedio del menu: $" + menu.promedioPrecio());

        // Demostracion de igualdad por valor
        ItemMenu itemCopia = new ItemMenu("Espresso Doble", Categoria.BEBIDA, 6500);
        IO.println("¿Igualdad por valor en records? " + item1.equals(itemCopia));

        // Reto Extra: intento de modificar lista inmutable
        try {
            menu.items().add(new ItemMenu("Galleta", Categoria.SNACK, 2500));
        } catch (UnsupportedOperationException e) {
            IO.println("Excepcion capturada al intentar alterar el menu: " + e.getClass().getSimpleName());
        }
    }
}
