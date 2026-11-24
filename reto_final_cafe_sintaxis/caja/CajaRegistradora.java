package caja;

import java.util.ArrayList;
import java.util.List;
import pedidos.LineaPedido;
import pedidos.LineaRecibo;
import pedidos.Pedido;
import pedidos.Recibo;

public class CajaRegistradora {
    private final Notificador notificador;
    private int saldo;
    private int reciboSecuencia = 1001;

    public CajaRegistradora(Notificador notificador) {
        this.notificador = notificador;
        this.saldo = 0;
    }

    public Recibo facturar(Pedido pedido, Descuento descuento, String fecha) {
        int totalBruto = pedido.total();
        int totalNeto = descuento.aplicar(totalBruto);

        List<LineaRecibo> lineasRecibo = new ArrayList<>();
        for (LineaPedido lp : pedido.getLineas()) {
            lineasRecibo.add(new LineaRecibo(
                lp.getProducto().getNombre(),
                lp.getProducto().calcularPrecio(),
                lp.getCantidad(),
                lp.subtotal()
            ));
        }

        Recibo recibo = new Recibo(reciboSecuencia++, fecha, pedido.getCliente(), lineasRecibo, totalBruto, totalNeto);
        this.saldo += totalNeto;

        notificador.notificar(pedido.getCliente(), "Facturado Recibo #" + recibo.numero() + " por $" + totalNeto);
        return recibo;
    }

    public int getSaldo() { return saldo; }
}
