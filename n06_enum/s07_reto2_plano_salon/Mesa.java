public class Mesa {
    private final int numero;
    private final int capacidad;
    private EstadoMesa estado;

    public Mesa(int numero, int capacidad) {
        if (numero <= 0 || capacidad <= 0) {
            throw new IllegalArgumentException("Numero y capacidad deben ser positivos");
        }
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = EstadoMesa.LIBRE;
    }

    public int getNumero() { return numero; }
    public int getCapacidad() { return capacidad; }
    public EstadoMesa getEstado() { return estado; }

    public void setEstado(EstadoMesa nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void avanzarEstado() {
        this.estado = this.estado.siguiente();
    }

    public String resumen() {
        return "Mesa " + numero + " (capacidad " + capacidad + ") · " + estado + " [" + estado.getAviso() + "]";
    }
}
