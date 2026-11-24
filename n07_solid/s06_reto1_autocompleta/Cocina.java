public class Cocina {
    private final Impresora impresora;

    public Cocina(Impresora impresora) {
        this.impresora = impresora;
    }

    public void enviarComanda(String plato, int mesa) {
        impresora.imprimir("Mesa " + mesa + ": " + plato);
    }
}
