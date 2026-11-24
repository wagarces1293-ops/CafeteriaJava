public class Domicilio extends Entrega implements Rastreable {
    private final String direccion;
    private final String telefono;
    private static final int COSTO_DOMICILIO = 5000;

    public Domicilio(int pedidoId, String direccion, String telefono) {
        super(pedidoId);
        if (direccion.isBlank() || telefono.isBlank()) {
            throw new IllegalArgumentException("Direccion y telefono son obligatorios para domicilio");
        }
        this.direccion = direccion;
        this.telefono = telefono;
    }

    @Override
    public int costo() {
        return COSTO_DOMICILIO;
    }

    @Override
    public String rastrear() {
        return "Repartidor en camino a " + direccion + " (Tel: " + telefono + ")";
    }

    @Override
    public String resumen() {
        return super.resumen() + " (Domicilio a " + direccion + ")";
    }
}
