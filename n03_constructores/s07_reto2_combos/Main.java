public class Main {
    public static void main(String[] args) {
        // Creacion con constructor completo
        Combo c1 = new Combo("Capuchino", "Brownie", 13500);
        IO.println(c1.descripcion());
        IO.println(c1.descripcion(10)); // 10% descuento

        // Creacion con constructor corto delegando con this(...)
        Combo c2 = new Combo("Americano", "Galleta");
        IO.println(c2.descripcion());

        // Caso limite: validacion de precio invalido
        try {
            new Combo("Latte", "Muffin", 0);
        } catch (IllegalArgumentException e) {
            IO.println("Capturado error de precio: " + e.getMessage());
        }

        // Caso limite: nombres vacios
        try {
            new Combo("   ", "Croissant", 11000);
        } catch (IllegalArgumentException e) {
            IO.println("Capturado error de nombre: " + e.getMessage());
        }
    }
}
