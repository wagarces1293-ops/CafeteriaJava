package caja;

public class DescuentoPorcentaje implements Descuento {
    private final int porcentaje;

    public DescuentoPorcentaje(int porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) throw new IllegalArgumentException("Porcentaje invalido: " + porcentaje);
        this.porcentaje = porcentaje;
    }

    @Override
    public int aplicar(int total) {
        return total * (100 - porcentaje) / 100;
    }
}
