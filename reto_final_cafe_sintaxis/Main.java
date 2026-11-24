import caja.*;
import datos.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import memoria.*;
import modelo.*;
import pedidos.*;

public class Main {
    public static void main(String[] args) {
        IO.println("==========================================================");
        IO.println("SISTEMA INTEGRADO CAFE SINTAXIS 1.0 (RETO FINAL CAPSTONE)");
        IO.println("==========================================================");

        // 1. Catalogo de Productos y Repositorio Generico (N01, N04-N06, N08)
        Repositorio<Producto> catalogo = new RepositorioMemoria<>();
        Producto espresso = new Bebida(1, "Espresso Clasico", Categoria.CALIENTE, 4500, Tamano.PEQUENO, false);
        Producto latteGrande = new Bebida(2, "Latte Cremoso", Categoria.CALIENTE, 8000, Tamano.GRANDE, true);
        Producto cheesecake = new Postre(3, "Cheesecake de Arandanos", 9500, false);
        Producto muffinVeg = new Postre(4, "Muffin de Platano", 6000, true);

        catalogo.guardar(espresso);
        catalogo.guardar(latteGrande);
        catalogo.guardar(cheesecake);
        catalogo.guardar(muffinVeg);

        IO.println();
        IO.println("--- 1. CARTA DE PRODUCTOS (POLIMORFISMO Y CATALOGO) ---");
        for (Producto p : catalogo.listar()) {
            IO.println(" [Cat: " + p.getCategoria().getEtiqueta() + "] " + p.descripcion());
        }

        // 2. Pedidos y Facturacion con Principios SOLID (N02-N05, N07, N10)
        IO.println();
        IO.println("--- 2. PROCESAMIENTO DE PEDIDOS Y CAJA SOLID ---");
        Pedido pedido1 = new Pedido(1, "Valeria Hurtado");
        pedido1.agregar(latteGrande, 2);  // 2 x (8000 + 2800) = 21600
        pedido1.agregar(cheesecake, 1);   // 1 x 9500 = 9500
        // Total bruto: 31100

        CajaRegistradora caja = new CajaRegistradora(new NotificadorConsola());

        // Aplicamos Descuento del 10% sin condicionales en Caja (OCP/DIP)
        Descuento promoApertura = new DescuentoPorcentaje(10);
        Recibo recibo1 = caja.facturar(pedido1, promoApertura, "2026-11-23");

        IO.println();
        IO.println("--- RECIBO GENERADO (RECORD INMUTABLE) ---");
        IO.println("Recibo No: " + recibo1.numero() + " · Cliente: " + recibo1.cliente() + " · Fecha: " + recibo1.fecha());
        for (LineaRecibo lr : recibo1.lineas()) {
            IO.println("  * " + lr.cantidad() + "x " + lr.producto() + " ($ " + lr.subtotal() + ")");
        }
        IO.println("Total Bruto: $" + recibo1.totalBruto());
        IO.println("Total Neto a Pagar (Promo 10%): $" + recibo1.totalNeto());
        IO.println("Saldo acumulado en Caja: $" + caja.getSaldo());

        // 3. Bodega, Persistencia con Filtro y Exportacion CSV (N09, N11)
        IO.println();
        IO.println("--- 3. BODEGA, SERIALIZACION SEGURA Y EXPORTACION ---");
        Path rutaSer = Path.of("reto_final_cafe_sintaxis/bodega_final.ser");
        Path rutaCSV = Path.of("reto_final_cafe_sintaxis/bodega_final.csv");

        Bodega bodega = new Bodega(rutaSer);
        List<Insumo> insumosHoy = List.of(
            new Insumo(1, "Cafe Especial Huila (kg)", 15, "kg"),
            new Insumo(2, "Leche Entera (L)", 30, "litros"),
            new Insumo(3, "Leche de Avena (L)", 12, "litros"),
            new Insumo(4, "Vasos Biodegradables 12oz", 150, "unidades")
        );

        try {
            // Guardar al cierre
            bodega.guardar(insumosHoy);
            IO.println("Bodega serializada en disco: " + Files.size(rutaSer) + " bytes");

            // Cargar con filtro
            List<Insumo> insumosRecuperados = bodega.cargar();
            IO.println("Insumos cargados tras reinicio: " + insumosRecuperados.size());
            IO.println("¿Coincidencia estructural exacta? " + insumosHoy.equals(insumosRecuperados));

            // Exportar a CSV para administracion contable
            ExportadorCSV.exportar(insumosRecuperados, rutaCSV);
            IO.println("Exportacion a CSV realizada. Contenido:");
            IO.println(Files.readString(rutaCSV));

        } catch (Exception e) {
            IO.println("Fallo en gestion de bodega: " + e.getMessage());
        }

        IO.println("==========================================================");
        IO.println("DEMOSTRACION FINAL CONCLUIDA EXITOSAMENTE CON CERO ERRORES");
        IO.println("==========================================================");
    }
}
