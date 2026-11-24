public abstract class Entrega {
    private final int pedidoId;

    protected Entrega(int pedidoId) {
        if (pedidoId <= 0) {
            throw new IllegalArgumentException("Id de pedido invalido: " + pedidoId);
        }
        this.pedidoId = pedidoId;
    }

    public int getPedidoId() { return pedidoId; }

    public abstract int costo();

    public String resumen() {
        return "Pedido #" + pedidoId + " · Costo entrega: $" + costo();
    }
}
