public class Main {
    public static void main(String[] args) {
        Empleado e1 = new Empleado("Carlos (Mesero)", 1300000);
        Barista b1 = new Barista("Martin (Barista)", 1400000, 10);
        Encargado enc = new Encargado("Valeria (Encargada)", 2000000, 300000);

        Turno turno = new Turno("2026-11-23");
        turno.registrarEmpleado(e1);
        turno.registrarEmpleado(b1);
        turno.registrarEmpleado(enc);

        turno.reporteTurno();

        // Caso limite: Barista sin horas extra
        Barista bCero = new Barista("Ana", 1400000, 0);
        IO.println("Barista sin extras: $" + bCero.pago());
    }
}
