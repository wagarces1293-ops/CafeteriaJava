public class Main {
    public static void main(String[] args) {
        ReporteCierre reporte = new ReporteCierre("2026-11-23");
        reporte.agregarVenta(19000, 2000);
        reporte.agregarVenta(7000, 1000);
        reporte.agregarVenta(12500, 1500);

        IO.println("--- Reporte para Valeria (Texto) ---");
        reporte.emitir(new ExportadorTexto());

        IO.println();
        IO.println("--- Reporte para Contadora (CSV) ---");
        reporte.emitir(new ExportadorCSV());
    }
}
