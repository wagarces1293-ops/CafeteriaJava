package modelo;

import datos.Identificable;

public abstract class Producto implements Identificable {
    private final int id;
    private final String nombre;
    private final Categoria categoria;
    private final int precioBase;

    protected Producto(int id, String nombre, Categoria categoria, int precioBase) {
        if (id <= 0) throw new IllegalArgumentException("Id invalido: " + id);
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre requerido");
        if (categoria == null) throw new IllegalArgumentException("Categoria requerida");
        if (precioBase <= 0) throw new IllegalArgumentException("Precio base debe ser mayor a 0: " + precioBase);

        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioBase = precioBase;
    }

    @Override
    public int id() { return id; }
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Categoria getCategoria() { return categoria; }
    public int getPrecioBase() { return precioBase; }

    public abstract int calcularPrecio();
    public abstract String descripcion();
}
