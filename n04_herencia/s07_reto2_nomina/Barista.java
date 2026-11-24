public class Barista extends Empleado {
    private final int horasExtra;
    private static final int VALOR_HORA_EXTRA = 15000;

    public Barista(String nombre, int salarioBase, int horasExtra) {
        super(nombre, salarioBase);
        if (horasExtra < 0) {
            throw new IllegalArgumentException("Horas extra no pueden ser negativas");
        }
        this.horasExtra = horasExtra;
    }

    @Override
    public int pago() {
        return super.pago() + (horasExtra * VALOR_HORA_EXTRA);
    }
}
