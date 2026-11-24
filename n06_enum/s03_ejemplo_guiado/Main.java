import java.util.EnumMap;

public class Main {
    public static void main(String[] args) {
        for (Tamano t : Tamano.values()) {
            IO.println(t + ": " + t.ml() + " ml, +$" + t.recargo());
        }

        EstadoPedido e = EstadoPedido.RECIBIDO;
        while (e != EstadoPedido.ENTREGADO) {
            e = e.siguiente();
            String aviso = switch (e) {
                case EN_PREPARACION -> "En barra";
                case LISTO -> "¡Listo para recoger!";
                default -> "Entregado";
            };
            IO.println(e.ordinal() + " " + e + " → " + aviso);
        }

        var conteo = new EnumMap<EstadoPedido, Integer>(EstadoPedido.class);
        conteo.put(EstadoPedido.LISTO, 3);
        conteo.put(EstadoPedido.RECIBIDO, 5);
        IO.println(conteo);

        IO.println(Tamano.valueOf("GRANDE").ml());
    }
}
