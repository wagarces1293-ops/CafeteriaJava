public class ExportadorTexto implements Exportador {
    @Override
    public void exportar(String fecha, int ventas, int total, int propinas) {
        IO.println("=== CIERRE DEL DIA: " + fecha + " ===");
        IO.println("Numero de ventas: " + ventas);
        IO.println("Total facturado: $" + total);
        IO.println("Propinas consolidadas: $" + propinas);
        IO.println("====================================");
    }
}
