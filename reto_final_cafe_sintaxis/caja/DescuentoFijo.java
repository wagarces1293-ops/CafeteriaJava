package caja;

public class DescuentoFijo implements Descuento {
    private final int rebaja;

    public DescuentoFijo(int rebaja) {
        if (rebaja < 0) throw new IllegalArgumentException("Rebaja no puede ser negativa");
        this.rebaja = rebaja;
    }

    @Override
    public int aplicar(int total) {
        return Math.max(0, total - rebaja);
    }
}
