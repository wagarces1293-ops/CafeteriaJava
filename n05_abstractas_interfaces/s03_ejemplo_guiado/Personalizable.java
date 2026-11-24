public interface Personalizable {
    int extra(String opcion);

    default String opciones() {
        return "leche de avena, shot extra";
    }
}
