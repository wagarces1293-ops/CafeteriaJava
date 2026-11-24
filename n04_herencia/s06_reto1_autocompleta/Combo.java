public class Combo {
    private final Producto principal;
    private final Producto acompanante;

    public Combo(Producto principal, Producto acompanante) {
        this.principal = principal;
        this.acompanante = acompanante;
    }

    public int precio() {
        int suma = principal.getPrecio() + acompanante.getPrecio();
        return suma * 90 / 100; // 10% menos
    }
}
