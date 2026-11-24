public class ExportadorCSV implements Exportador {
    @Override
    public void exportar(String fecha, int ventas, int total, int propinas) {
        IO.println("fecha;ventas;total;propinas");
        IO.println(fecha + ";" + ventas + ";" + total + ";" + propinas);
    }
}
