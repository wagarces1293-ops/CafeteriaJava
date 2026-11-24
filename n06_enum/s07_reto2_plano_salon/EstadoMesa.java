public enum EstadoMesa {
    LIBRE("Disponible para clientes"),
    OCUPADA("Comensales en servicio"),
    POR_LIMPIAR("En proceso de aseo y desinfeccion"),
    RESERVADA("Apartada con antelacion");

    private final String aviso;

    EstadoMesa(String aviso) {
        this.aviso = aviso;
    }

    public String getAviso() {
        return aviso;
    }

    public EstadoMesa siguiente() {
        return switch (this) {
            case LIBRE -> OCUPADA;
            case OCUPADA -> POR_LIMPIAR;
            case POR_LIMPIAR -> LIBRE;
            case RESERVADA -> OCUPADA;
        };
    }
}
