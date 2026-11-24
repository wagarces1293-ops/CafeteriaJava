public class Barista {
    String nombre;
    String turno;
    int bebidas;
    int propinas;

    void preparar(String bebida) {
        IO.println(nombre + " prepara un " + bebida);
        bebidas = bebidas + 1;
    }

    String resumen() {
        return nombre + " (" + turno + "): " + bebidas + " bebidas";
    }

    boolean merecePausa() {
        return bebidas >= 20;
    }

    void recibirPropina(int valor) {
        if (valor > 0) {
            propinas = propinas + valor;
        }
    }
}
