public class Cliente implements Identificable {
    private final int id;
    private final String nombre;

    public Cliente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public int id() { return id; }
    public String nombre() { return nombre; }
}
