# Nivel 05: Clases Abstractas e Interfaces - Contratos de la Barra

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como trato igual a medios de pago y tipos de entrega distintos sin conocer los detalles particulares de su implementacion?
* **Mi Meta Personal:** Diferenciar formalmente una clase abstracta (plantilla con estado y metodos incompletos) de una interfaz (contrato puro de comportamiento), dominando la declaracion de metodos abstractos, metodos `default`, la coincidencia de patrones con `instanceof` y la programacion contra abstracciones.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification (JLS SE 25)
* **Clase Abstracta (JLS §8.1.1.1):** Una clase abstracta es una clase declarada con el modificador `abstract`. No puede ser instanciada directamente con el operador `new`; su proposito primordial es servir como superclase incompleta para que sus subclases concretas completen sus metodos abstractos.
* **Interfaz (JLS §9.1):** Una declaracion de interfaz introduce un tipo de referencia cuyos miembros son constantes, metodos abstractos, metodos `default` y metodos estaticos. Establece un contrato formal de comportamiento que puede ser implementado por cualquier clase independiente de su arbol genealogico.
* **Metodo Default (JLS §9.4):** Un metodo declarado en una interfaz con el modificador `default` incluye un cuerpo de implementacion predeterminado, permitiendo evolucionar contratos sin quebrar las clases que ya los implementaban.

### 2. Sintesis Personal del Dominio
Entiendo que una clase abstracta es una **plantilla a medio llenar**: por ejemplo, `Producto` tiene atributos de instancia privados (`nombre`, `precio`) y un constructor protegido que las hijas reutilizan, pero declara `public abstract int minutosPreparacion();` porque un producto generico no sabe cuanto tarda en prepararse; solo la bebida concreta (4 min) o el postre (1 min) lo saben. Por su parte, una interfaz es un **contrato de capacidad**: `Personalizable` o `Rastreable` no guardan estado de un producto ni tienen constructor; simplemente prometen que cualquier clase que firme el contrato ofrecera esas acciones.

### 3. Vocabulario Tecnico Dominado
* **`abstract`:** Modificador que denota incompletitud a nivel de clase o metodo.
* **`interface`:** Tipo de referencia contractual en Java.
* **`implements`:** Palabra clave con la cual una clase se compromete a cumplir el contrato de una interfaz.
* **Metodo `default`:** Comportamiento estandar opcional suministrado dentro de una interfaz.
* **Polimorfismo de Interfaz:** Capacidad de interactuar con objetos heterogeneos a traves del tipo contractual comun (`MedioPago`).
* **Coincidencia de Patrones con `instanceof` (`p instanceof Rastreable r`):** Comprobacion y casteo seguro en una sola sentencia introducido en Java moderno.

---

## Sesion 02: Comprende (Ampliacion del Tema: Plantilla vs. Contrato)

### 1. Cuadro Comparativo Exhaustivo: Clase Abstracta vs. Interfaz
| Criterio de Arquitectura | Clase Abstracta (`abstract class`) | Interfaz (`interface`) |
| :--- | :--- | :--- |
| **Rol en el Diseno** | Plantilla comun con codigo compartido ("es un"). | Contrato de comportamiento / Capacidad ("puede hacer"). |
| **Herencia Multiple** | Prohibida (solo un `extends`). | Permitida (puede usar `implements A, B, C`). |
| **Atributos de Instancia** | Si puede tener campos `private final`, mutables, etc. | No (solo constantes `public static final`). |
| **Constructores** | Si (invocados mediante `super(...)` por las hijas). | No tiene constructores. |
| **Metodos Concretos** | Metodos de instancia ordinarios. | Metodos con palabra clave `default` o `static`. |
| **Instanciacion Directa** | Imposible con `new`. | Imposible con `new`. |
| **Ejemplo en el Cafe** | `Producto`, `Entrega` | `Personalizable`, `MedioPago`, `Rastreable` |

### 2. Anatomia del Despacho por Contrato
En `Caja.java`:
```java
public void procesarCobro(MedioPago medio, int monto) {
    IO.println("Cobrando $" + monto + " mediante " + medio.nombre());
    boolean exito = medio.pagar(monto);
    if (!exito) {
        throw new IllegalStateException("Transaccion rechazada por el medio");
    }
}
```
La caja no tiene sentencias `if (medio == "Efectivo") ... else if (medio == "Tarjeta")`. La caja programa contra la interfaz `MedioPago`. Si manana Valeria incorpora `BilleteraDigital` o `Criptomoneda`, basta crear la clase que implemente `MedioPago` y la caja operara de inmediato sin modificar una sola linea de su codigo.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **¿Por que `ficha()` puede invocar a `minutosPreparacion()` si en `Producto` no tiene cuerpo?**
   Porque Java confia en el contrato de clases abstractas: como `Producto` no puede ser instanciada directamente, cualquier objeto real que ejecute `ficha()` pertenecera forzosamente a una subclase concreta (`Bebida` o `Postre`) que habra proporcionado una implementacion obligatoria del metodo abstracto.
2. **Productos que imprimen la linea "opciones":**
   Unicamente el `Capuchino` (instancia de `Bebida`). El `Brownie` (instancia de `Postre`) no implementa la interfaz `Personalizable`, por lo cual la comprobacion `p instanceof Personalizable per` se evalua como `false` y omite el bloque.
3. **Causa del error al descomentar `new Producto("Te", 3000)`:**
   El compilador arroja: `Producto is abstract; cannot be instantiated`. La regla de Java impide instanciar abstracciones incompletas.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Retirar la implementacion de `minutosPreparacion()` en `Postre`):**
  El compilador se detiene con: `Postre is not abstract and does not override abstract method minutosPreparacion() in Producto`.
* **Experimento B (Sobrescribir el metodo `default opciones()` en `Bebida`):**
  Se verifica que la clase concreta puede optar por reemplazar el metodo default para retornar opciones particulares (ej. "leche de almendras, deslactosada"), conservando la flexibilidad del diseno.

---

## Sesion 04: Practica Guiada (Medios de Pago Desacoplados)

### 1. Analisis de la Solucion de Medios de Pago (`MedioPago.java`)
Se definio la interfaz `MedioPago` con dos operaciones esenciales: `String nombre()` y `boolean pagar(int monto)`.
* `Efectivo`: Siempre retorna `true` ya que el dinero se valida en mano.
* `Tarjeta`: Mantiene un saldo disponible privado. Si el cobro supera el saldo, reporta fondos insuficientes y devuelve `false`.
* El metodo general `cobrar(MedioPago medio, int monto)` demuestra el desacoplamiento total: la operacion no sabe ni le interesa como procesa el cobro cada entidad fisica; solo delega el mandato.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Por que en `java.*` hay 276 interfaces, 444 metodos default y 158 clases abstractas?**
   Las cifras reflejan que la arquitectura moderna de software orientada a objetos privilegia los contratos polimorficos ligeros e interoperables sobre jerarquias de herencia rigidas y pesadas. Los metodos default permitieron que Java evolucionara agregando funciones a interfaces historicas (como `Iterable.forEach`) sin romper librerias legadas.
2. **¿Que ganaria y perderia Ana si convirtiera `Producto` en una interfaz pura?**
   * **Ganaria:** Flexibilidad para que cualquier clase externa (incluso registros o enums) pudiera proclamarse como producto.
   * **Perderia:** Todo el estado comun encapsulado (`nombre`, `precio`), obligando a que cada implementacion (`Bebida`, `Postre`, `Snack`) tuviera que redeclarar atributos y reescribir manualmente los getters `getNombre()` y `getPrecio()`, duplicando codigo y violando el principio DRY. La decision correcta es mantener `Producto` como clase abstracta.

---

## Sesion 06: Completa (Reto 1 de 2: Descontable)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `interface` (`public interface Descontable` define el contrato de descuentos).
* **Espacio 2:** `default` (`default int conDescuento(int pct)` provee la logica matematica comun).
* **Espacio 3:** `abstract` (`public abstract class Snack` plantilla para tentempies).
* **Espacio 4:** `implements` (`implements Descontable` adopta el contrato).
* **Espacio 5:** `abstract` (`public abstract String nombre();` metodo diferido a las golosinas concretas).
* **Espacio 6:** `extends` (`public class Galleta extends Snack` herencia concreta).
* **Espacio 7:** `Descontable` (`Descontable d = s;` asignacion polimorfica al tipo interfaz).

### 2. Respuestas a las Preguntas de Control
1. **¿Por que `Snack` puede firmar `Descontable` sin escribir `conDescuento`?**
   Porque `conDescuento` es un metodo `default`: ya cuenta con una implementacion concreta dentro de la interfaz. En contraste, `precioBase` es un metodo puramente abstracto, por lo cual `Snack` esta obligado a implementarlo o delegarlo.
2. **Si en el espacio 5 quitas `abstract`, ¿que solicita el compilador?**
   El compilador arroja `missing method body, or declare abstract`, ya que en una clase un metodo ordinario no puede terminar en punto y coma sin llevar un bloque de codigo `{ ... }`.

---

## Sesion 07: Crea (Reto 2 de 2: Formas de Entrega)

### 1. Diseno e Implementacion del Subsistema de Entregas
* **Clase Abstracta `Entrega`:** Encapsula `pedidoId`, provee constructor protegido y el metodo concreto `resumen()`. Declara `public abstract int costo();`.
* **Subclase `EnMesa extends Entrega`:** Incorpora `int mesa` y define costo en `$0`.
* **Subclase `ParaLlevar extends Entrega`:** Incorpora `int empaque` y define costo igual al valor del contenedor.
* **Interfaz `Rastreable`:** Declara el contrato `String rastrear();`.
* **Subclase `Domicilio extends Entrega implements Rastreable`:** Incorpora `direccion`, `telefono`, fija el costo en `$5000` e implementa la geolocalizacion del repartidor.

**Salida comprobada en terminal:**
```text
Pedido #201 · Costo entrega: $0 (Servido en Mesa 4)
Pedido #202 · Costo entrega: $1200 (Para llevar con empaque)
Pedido #203 · Costo entrega: $5000 (Domicilio a Carrera 7 # 45-20)
 -> Rastreo en tiempo real: Repartidor en camino a Carrera 7 # 45-20 (Tel: 3001234567)
```

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Como estudiante de ingenieria, requiero evaluar si una entrega a domicilio debe heredar de Entrega e implementar Rastreable, y como procesar una coleccion polimorfica diferenciando las rastreables".
* **Respuesta evaluada:** Confirmo que solo los domicilios son rastreables en tiempo real, validando el uso de `instanceof Rastreable r` en el bucle principal.
* **Verificacion tecnica:** Comprobe que las entregas en mesa y para llevar no implementan la interfaz, demostrando la segregacion de responsabilidades.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 05)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que no se puede hacer con una clase abstracta?
* **Opcion Correcta:** **b) Crear objetos con new**
* **Justificacion:** Las clases abstractas estan conceptualmente incompletas y la JVM prohibe su instanciacion directa (JLS §8.1.1.1).

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** El compilador dice: `Postre is not abstract and does not override abstract method...`. ¿Que falta?
* **Opcion Correcta:** **b) Escribir el metodo abstracto en Postre**
* **Justificacion:** Toda subclase concreta esta obligada a proporcionar cuerpo a todos los metodos abstractos heredados de su superclase.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Una clase puede implementar varias interfaces a la vez.
* **Respuesta:** **Verdadero (V)**
* **Justificacion:** Java permite implementar multiples contratos mediante la sintaxis `implements A, B, C`, permitiendo que un objeto asuma multiples roles operativos.

### 4. Relacion de Conceptos (DOK 2)
* **`abstract`** -> Plantilla o metodo sin cuerpo.
* **`interface`** -> Declara un contrato.
* **`implements`** -> Firma un contrato.
* **`default`** -> Metodo con cuerpo en una interfaz.

### 5. Ordenamiento Logico (DOK 2)
Pasos para incorporar un nuevo medio de pago:
1. Leer la interfaz `MedioDePago`.
2. Crear la clase que la implementa.
3. Escribir `nombre()` y `pagar()`.
4. Agregar un objeto a la lista de medios.
5. Ejecutar y comparar la salida.

### 6. Respuesta Abierta: Analisis de Desacoplamiento (DOK 3)
* **Pregunta:** Explica por que la caja funciona con un medio de pago nuevo sin cambiar el metodo `cobrar`.
* **Respuesta:** Funciona porque la caja aplica el Principio de Inversion de Dependencias: depende exclusivamente de la abstraccion `MedioPago` y no de ninguna clase concreta. La caja unicamente invoca los metodos `nombre()` y `pagar()` estipulados en el contrato. Al anadir un nuevo medio de pago, la implementacion viaja encapsulada dentro del nuevo objeto; la caja permanece inmutable y cerrada a modificaciones, pero abierta a extensiones.

### 7. Respuesta Abierta: Evaluacion de Diseno (DOK 3)
* **Pregunta:** Ana quiere convertir `Producto` en interfaz. ¿Que perderia? ¿Que ganaria? Decide y justifica.
* **Respuesta:** Perderia la capacidad de almacenar y proteger atributos comunes (`nombre` y `precio`), forzando a que `Bebida`, `Postre` y demas productos tengan que redeclarar esos campos privadamente en cada archivo, duplicando codigo de getters y constructores. Ganaria la posibilidad de que clases con arboles genealogicos ajenos pudieran firmar `Producto`. Como en Cafe Sintaxis todo producto de venta comparte identidad fisica y financiera identica, la eleccion correcta y profesional es mantener `Producto` como **clase abstracta**.

### 8. Respuesta Abierta: Creacion de Interfaz (DOK 3)
* **Pregunta:** Escribe la interfaz `Imprimible` con `String texto()` y un `default void imprimir()` que lo muestre.
* **Solucion:**
```java
public interface Imprimible {
    String texto();

    default void imprimir() {
        IO.println(texto());
    }
}
```

---

## Cierre del Nivel 05: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo cuando una clase debe ser abstracta para fungir como plantilla estructural y cuando una interfaz es la herramienta optima para desacoplar sistemas mediante contratos.

### 2. Explicacion Sintetica en 60 Segundos
El efectivo y la tarjeta de credito comparten la interfaz `MedioPago` porque ambos satisfacen el mismo contrato conductual: saben decir su nombre y saben liquidar un valor monetario. Sin embargo, no comparten una clase padre comun porque ontologicamente no provienen del mismo arbol: el efectivo es papel moneda fisico custodiado en un cajon, mientras que la tarjeta es un instrumento financiero electronico que consulta saldos bancarios y redes externas.

### 3. Mapa Conceptual Completado
```text
[Abstraccion]
      |
      +--> Se declara como Plantilla -> [Clase abstract: estado y metodos sin cuerpo]
      +--> Se declara como Contrato -> [interface: capacidad pura y metodos default]
      +--> Se materializa con ---------> [implements: cumplimiento estricto]
      +--> Se inspecciona con ---------> [instanceof con patron: seguridad en runtime]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Articulo disenos extensibles basados en contratos formales, estructurando el procesamiento polimorfico de ordenes y metodos de pago sin acoplar los modulos consumidores a detalles de bajo nivel.
