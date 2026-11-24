public record LineaRecibo(String producto, int precio, int cantidad) {
    public LineaRecibo {
        if (cantidad < 1) {
            throw new IllegalArgumentException("Cantidad mínima: 1");
        }
    }

    public int subtotal() {
        return precio * cantidad;
    }
}
