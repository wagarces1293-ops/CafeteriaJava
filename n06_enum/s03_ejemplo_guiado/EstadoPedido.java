public enum EstadoPedido {
    RECIBIDO, EN_PREPARACION, LISTO, ENTREGADO;

    public EstadoPedido siguiente() {
        return switch (this) {
            case RECIBIDO -> EN_PREPARACION;
            case EN_PREPARACION -> LISTO;
            case LISTO, ENTREGADO -> ENTREGADO;
        };
    }
}
