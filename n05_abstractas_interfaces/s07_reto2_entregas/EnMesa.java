public class EnMesa extends Entrega {
    private final int mesa;

    public EnMesa(int pedidoId, int mesa) {
        super(pedidoId);
        this.mesa = mesa;
    }

    @Override
    public int costo() {
        return 0; // Servicio en mesa sin recargo
    }

    @Override
    public String resumen() {
        return super.resumen() + " (Servido en Mesa " + mesa + ")";
    }
}
