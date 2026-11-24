import java.util.List;

public class Main {
    public static void main(String[] args) {
        Caja caja = new Caja(new SmsSimulado());
        var pedido = List.of(9500, 7000, 3500);
        caja.cobrar(pedido, new Porcentaje(10), "300 555 0101");
        caja.cobrar(List.of(1500), new Fidelidad(), "300 555 0103");
    }
}
