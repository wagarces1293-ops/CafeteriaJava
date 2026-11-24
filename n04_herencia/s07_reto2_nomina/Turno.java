import java.util.ArrayList;
import java.util.List;

public class Turno {
    private final String fecha;
    private final List<Empleado> empleados;

    public Turno(String fecha) {
        this.fecha = fecha;
        this.empleados = new ArrayList<>();
    }

    public void registrarEmpleado(Empleado e) {
        empleados.add(e);
    }

    public int nominaTotal() {
        int total = 0;
        for (Empleado e : empleados) {
            total += e.pago(); // Invocacion polimorfica
        }
        return total;
    }

    public void reporteTurno() {
        IO.println("Reporte de Nomina de Turno - " + fecha);
        for (Empleado e : empleados) {
            IO.println(" - " + e.resumen());
        }
        IO.println("Total de Nomina del Turno: $" + nominaTotal());
    }
}
