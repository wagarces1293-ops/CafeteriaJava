public class ParaLlevar extends Entrega {
    private final int empaque;

    public ParaLlevar(int pedidoId, int empaque) {
        super(pedidoId);
        if (empaque < 0) {
            throw new IllegalArgumentException("Costo de empaque no puede ser negativo");
        }
        this.empaque = empaque;
    }

    @Override
    public int costo() {
        return empaque;
    }

    @Override
    public String resumen() {
        return super.resumen() + " (Para llevar con empaque)";
    }
}
