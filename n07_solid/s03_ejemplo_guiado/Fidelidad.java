public class Fidelidad implements Descuento {
    @Override
    public int aplicar(int total) {
        return Math.max(0, total - 2000);
    }
}
