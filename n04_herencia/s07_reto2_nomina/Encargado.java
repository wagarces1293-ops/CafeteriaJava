public class Encargado extends Empleado {
    private final int bono;

    public Encargado(String nombre, int salarioBase, int bono) {
        super(nombre, salarioBase);
        if (bono < 0) {
            throw new IllegalArgumentException("Bono no puede ser negativo");
        }
        this.bono = bono;
    }

    @Override
    public int pago() {
        return super.pago() + bono;
    }
}
