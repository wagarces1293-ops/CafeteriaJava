public class Empleado {
    private final String nombre;
    private final int salarioBase;

    public Empleado(String nombre, int salarioBase) {
        if (nombre.isBlank() || salarioBase <= 0) {
            throw new IllegalArgumentException("Datos de empleado invalidos");
        }
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public String getNombre() { return nombre; }
    public int getSalarioBase() { return salarioBase; }

    public int pago() {
        return salarioBase;
    }

    public String resumen() {
        return nombre + " · Salario base: $" + salarioBase + " · Total pago: $" + pago();
    }
}
