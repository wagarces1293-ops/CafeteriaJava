public class BotePropinas {
    private int total;
    private int aportes;

    public void aportar(int valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El aporte debe ser mayor a 0: " + valor);
        }
        this.total += valor;
        this.aportes++;
    }

    public int repartir(int personas) {
        if (personas < 1) {
            throw new IllegalArgumentException("La cantidad de personas debe ser al menos 1: " + personas);
        }
        return this.total / personas;
    }

    public int sobrante(int personas) {
        if (personas < 1) {
            throw new IllegalArgumentException("La cantidad de personas debe ser al menos 1: " + personas);
        }
        return this.total % personas;
    }

    public int getTotal() {
        return total;
    }

    public int getAportes() {
        return aportes;
    }
}
