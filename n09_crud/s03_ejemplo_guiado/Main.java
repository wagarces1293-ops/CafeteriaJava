public class Main {
    public static void main(String[] args) {
        Inventario inv = new Inventario();
        inv.crear("Leche entera (L)", 12);
        inv.crear("Café en grano (kg)", 5);
        inv.crear("Avena (L)", 3);
        IO.println("1 " + inv.listar());

        IO.println("2 " + inv.buscar(2).orElseThrow());
        IO.println("3 ¿Existe #9? " + inv.buscar(9).isPresent());

        inv.actualizar(1, 20);
        inv.eliminar(3);

        var lista = inv.listar();
        IO.println("4 " + lista);
        IO.println("5 Primero: " + lista.get(0));
        IO.println("6 Último: " + lista.get(lista.size() - 1));
    }
}
