public class Mesa {
    int numero;
    int personas;
    boolean ocupada;

    void ocupar(int cuantas) {
        personas = cuantas;
        ocupada = true;
    }

    void liberar() {
        personas = 0;
        ocupada = false;
    }

    String estado() {
        if (ocupada) {
            return "Mesa " + numero + " ocupada por " + personas;
        }
        return "Mesa " + numero + " libre";
    }
}
