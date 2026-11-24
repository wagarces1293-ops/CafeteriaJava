public class ReporteCierre {
    private final String fecha;
    private int ventas;
    private int totalFacturado;
    private int propinas;

    public ReporteCierre(String fecha) {
        this.fecha = fecha;
    }

    public void agregarVenta(int valor, int propina) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de venta invalido: " + valor);
        }
        if (propina < 0) {
            throw new IllegalArgumentException("Propina no puede ser negativa: " + propina);
        }
        this.ventas++;
        this.totalFacturado += valor;
        this.propinas += propina;
    }

    // Inyeccion de dependencia para exportar
    public void emitir(Exportador exportador) {
        exportador.exportar(fecha, ventas, totalFacturado, propinas);
    }
}
