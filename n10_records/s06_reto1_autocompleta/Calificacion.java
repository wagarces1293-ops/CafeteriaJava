public record Calificacion(String cliente, int estrellas) {
    public Calificacion {
        if (estrellas < 1 || estrellas > 5) {
            throw new IllegalArgumentException("De 1 a 5");
        }
    }

    public boolean esBuena() {
        return estrellas >= 4;
    }
}
