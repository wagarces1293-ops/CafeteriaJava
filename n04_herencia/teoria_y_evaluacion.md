# Nivel 04: Herencia y Composicion - Familias del Menu

## Presentacion del Nivel
* **Pregunta Guia:** ¿Que comparten los productos del menu y como se arma un pedido estructurado sin incurrir en acoplamiento rigido?
* **Mi Meta Personal:** Comprender la distincion arquitectonica entre la relacion de herencia ("es un", `extends`) y la relacion de composicion ("tiene un"), dominando la sobrescritura de metodos mediante `@Override`, la invocacion al constructor y comportamiento del padre con `super`, y el polimorfismo dinámico en listas heterogeneas.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification (JLS SE 25)
* **Herencia de Clases (JLS §8.1.4):** Una declaracion de clase puede especificar una superclase directa mediante la clausula `extends`. Una clase hereda de su superclase todos los miembros accesibles (campos y metodos), salvo constructores e inicializadores de instancia.
* **Sobrescritura de Metodos (JLS §8.4.8.1):** Un metodo de instancia declarado en una subclase sobrescribe un metodo accesible de la superclase si ambos comparten exactamente la misma firma y tipo de retorno compatible.
* **Composicion de Clases (Gamma et al. / Bloch Effective Java):** Tecnica de diseno en la cual una clase contiene referencias a objetos de otras clases como atributos privados, delegando responsabilidades en lugar de heredar su implementacion.

### 2. Sintesis Personal del Dominio
Entiendo que la herencia debe reservarse estrictamente para jerarquias conceptuales autenticas donde se cumple el principio "es un": una `Bebida` es un `Producto` y un `Postre` es un `Producto`. Ambos comparten un nombre y un precio, pero la bebida anade mililitros y el postre anade su condicion vegana. En contraste, un `Pedido` no es un producto ni una linea de venta: un pedido **tiene** lineas de pedido, y cada linea **tiene** un producto asociado. Intentar hacer que `Pedido` herede de `Producto` destruiria la semantica del software; la composicion modela adecuadamente relaciones de posesion y agregacion.

### 3. Vocabulario Tecnico Dominado
* **Superclase (Padre):** Clase base que centraliza atributos y metodos comunes.
* **Subclase (Hija):** Clase derivada que extiende la funcionalidad base mediante `extends`.
* **`super(...)`:** Instruccion que invoca al constructor de la superclase directa.
* **`super.metodo()`:** Llamado al comportamiento implementado en la superclase para reutilizar su logica.
* **Anotacion `@Override`:** Metadato de compilacion que valida que el metodo anotado efectivamente sobrescribe un miembro del padre.
* **Polimorfismo:** Capacidad de una referencia de tipo padre (`Producto`) de invocar el metodo especifico de la subclase real alojada en el Heap en tiempo de ejecucion.
* **Composicion ("Tiene un"):** Enlace estructural donde una clase engloba a otras piezas para conformar un todo.

---

## Sesion 02: Comprende (Ampliacion del Tema y Principio de Sustitucion)

### 1. Cuadro Comparativo: Herencia vs. Composicion
| Dimension de Diseno | Herencia (`extends`) | Composicion (Atributos de Referencia) |
| :--- | :--- | :--- |
| **Relacion Semantica** | "Es un" (Especializacion de concepto). | "Tiene un" (Contenedor / Colaborador). |
| **Acoplamiento** | Alto (caja blanca; cambios en el padre impactan a la hija). | Bajo (caja negra; interactuan solo por interfaz publica). |
| **Flexibilidad en Ejecucion** | Estatica (fijada al compilar). | Dinamica (los componentes pueden inyectarse o sustituirse). |
| **Regla de Oro (Bloch Item 18)** | Usar solo si la subclase es verdaderamente un subtipo. | Preferir composicion sobre herencia. |
| **Ejemplo en Cafe Sintaxis** | `Bebida extends Producto` | `Pedido` compuesto de `List<LineaPedido>` |

### 2. Mecanica del Enlace Dinamico (Polimorfismo)
Cuando el programa ejecuta:
```java
List<Producto> vitrina = List.of(latte, torta, new Producto("Galleta", 3000));
for (Producto p : vitrina) {
    IO.println(p.descripcion());
}
```
La variable iteradora `p` es estaticamente de tipo `Producto`. Sin embargo, la JVM no ejecuta ciegamente la implementacion de `Producto.descripcion()`. En cada iteracion, consulta la tabla de metodos virtuales del objeto real en memoria:
* Para `latte`, ejecuta `Bebida.descripcion()`, imprimiendo mililitros.
* Para `torta`, ejecuta `Postre.descripcion()`, agregando el rotulo vegano si aplica.
* Para `Galleta`, ejecuta la version base de `Producto.descripcion()`.
Este desacoplamiento permite que la vitrina exponga cualquier producto nuevo sin tocar el codigo del bucle.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Compilacion de `latte.getNombre()`:** Compila perfectamente debido a que `Bebida` extiende `Producto`, heredando todos sus metodos publicos sin necesidad de reescribirlos.
2. **Salida esperada en el bucle polimorfico:**
   * Linea 1: `"Latte $9500 · 350 ml"` (version sobrescrita en `Bebida`).
   * Linea 2: `"Torta de zanahoria $8000 · vegano"` (version sobrescrita en `Postre`).
   * Linea 3: `"Galleta $3000"` (version base de `Producto`).
3. **Causa del error al omitir `super(nombre, precio)` en la subclase:**
   Como `Producto` no posee constructor vacio por defecto, el compilador exige que el constructor de `Bebida` invoque explicitamente el constructor existente del padre con los argumentos requeridos. Si se omite, Java intenta insertar de manera invisible `super();`, fallando la compilacion con `constructor Producto in class Producto cannot be applied to given types`.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Retirar `@Override` y escribir intencionalmente `descripcon()`):**
  El programa compila sin error si no tiene `@Override`, pero la sobreescritura jamas ocurre; en la vitrina se imprime la descripcion basica del padre, perdiendo los mililitros. Con `@Override`, el compilador detecta inmediatamente el error tipografico reportando: `method does not override or implement a method from a supertype`.
* **Experimento B (Cambiar visibilidad de `Producto.nombre` a `private`):**
  Confirmo que la subclase `Bebida` no puede acceder directamente a `nombre`, viendose obligada a emplear el getter publico o `super.descripcion()`, preservando el encapsulamiento del padre.

---

## Sesion 04: Practica Guiada (Composicion en Pedidos)

### 1. Analisis de la Solucion de Ana (`LineaPedido.java` y `Pedido.java`)
Ana estructuro el pedido mediante composicion en dos niveles:
* `LineaPedido`: Encapsula un objeto `Producto` y un entero `cantidad`. Delega el calculo del subtotal multiplicando `producto.getPrecio() * cantidad`.
* `Pedido`: Administra una coleccion `List<LineaPedido>`. Al computar el total, recorre las lineas y suma cada subtotal.
* Evaluacion: Ninguna de estas clases necesita usar `extends`. El diseno refleja que un pedido es un contenedor dinamico de transacciones.

### 2. Resolucion de la Practica Guiada "Ahora lo hacemos juntos"
1. **¿Por que un Pedido no debe heredar de Producto?**
   Porque viola la prueba conceptual "es un". Un pedido no tiene un precio unitario fijo de carta, no se exhibe en una vitrina ni posee fecha de caducidad. Un pedido es un comprobante de compra transaccional que agrupa productos seleccionados por un cliente.
2. **Delecion y encapsulamiento:**
   Si el precio de un producto cambia en la carta, las lineas de pedido ya emitidas preservan su coherencia a traves del calculo registrado al momento de la venta.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Por que la herencia multiple de clases esta prohibida en Java?**
   Java no permite `class C extends A, B` para eludir el clasico "problema del diamante" (ambiguedad mortal). Si ambas superclases implementan un metodo con la misma firma pero comportamiento diferente, el compilador no podria determinar deterministicamente cual version debe ejecutar la subclase.
2. **¿Que relacion existe entre la cadena de herencia de `ArrayList` y `Object`?**
   `ArrayList` extiende `AbstractList`, la cual extiende `AbstractCollection`, y esta a su vez desciende de `java.lang.Object`. Toda clase en Java es descendiente de `Object`, heredando metodos fundamentales como `equals()`, `hashCode()` y `toString()`.
3. **¿Cuando se justifica plenamente el uso de herencia en Cafe Sintaxis?**
   Se justifica cuando existe una verdadera identidad de subtipo taxonomico inalterable. Por ejemplo, en el menu, todo item vendible responde a las caracteristicas de `Producto`, pero una bebida requiere registrar su volumen en centimetros cubicos o mililitros. La herencia unifica la gestion contable y la vitrina permitiendo especializaciones fisicas reales.

---

## Sesion 06: Completa (Reto 1 de 2: Temporada y Combo)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `extends` (`public class Temporada extends Producto` establece la relacion de herencia).
* **Espacio 2:** `super` (`super(nombre, precio);` invoca al constructor de `Producto`).
* **Espacio 3:** `@Override` (Valida formalmente la sobrescritura de `descripcion()`).
* **Espacio 4:** `super` (`return super.descripcion() + " (solo " + mes + ")";` reutiliza el texto base).
* **Espacio 5:** `Producto` (`private final Producto principal;` tipo polimorfico para composicion).
* **Espacio 6:** `getPrecio` (`+ acompanante.getPrecio();` invoca el getter publico).
* **Espacio 7:** `Temporada` (`new Temporada(...)` instanciacion del producto de estacion).

### 2. Respuestas a las Preguntas de Control
1. **¿Por que `Combo` no hereda de `Producto`?**
   Aplicando la prueba "es un / tiene un": Un combo no es una variacion fisica de un unico producto simple; un combo **tiene** un producto principal y **tiene** un acompanante. Su comportamiento reside en coordinar dos productos preexistentes para aplicar un 10% de descuento sobre la suma de sus valores.
2. **Si en el espacio 4 escribes `this` en vez de `super`, ¿que ocurre?**
   Se generaria una llamada recursiva infinita: `this.descripcion()` se invocaria a si mismo sin condicion de parada hasta desbordar la pila de llamadas de la JVM con un error catastrofico `java.lang.StackOverflowError`.

---

## Sesion 07: Crea (Reto 2 de 2: La Nomina del Turno)

### 1. Diseno e Implementacion de la Jerarquia de Nomina
* **Superclase `Empleado`:** Atributos inmutables `nombre` y `salarioBase`. Metodo `pago()` que retorna la remuneracion base.
* **Subclase `Barista extends Empleado`:** Incorpora `horasExtra` y sobrescribe `@Override public int pago()` sumando `super.pago() + (horasExtra * 15000)`.
* **Subclase `Encargado extends Empleado`:** Incorpora `bono` de responsabilidad y sobrescribe `@Override public int pago()` sumando `super.pago() + bono`.
* **Clase Compuesta `Turno`:** Posee una lista de empleados `List<Empleado>` y calcula la liquidacion total del turno invocando polimorficamente `e.pago()`.

**Resultados obtenidos de la corrida en consola:**
* Empleado base Carlos (Mesero): `$1300000`.
* Barista Martin (10 horas extra): `$1400000 + 150000 = $1550000`.
* Encargada Valeria (Bono de `$300000`): `$2000000 + 300000 = $2300000`.
* Total de Nomina liquidada en el turno: `$5150000`.
* Caso Limite probado: Barista Ana con 0 horas extra cobra exactamente su salario base (`$1400000`).

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Actua como tutor de Java. Explica como calcular la nomina consolidada de empleados polimorficos mediante una clase compuesta Turno que no conozca las subclases concretas".
* **Respuesta evaluada:** Sugirio iterar sobre una coleccion generica `List<Empleado>` llamando unicamente a `e.pago()`.
* **Verificacion tecnica:** Comprobe que el patron de polimorfismo dinamico de Java ejecuta el metodo sobrescrito correspondiente en cada iteracion, desacoplando totalmente a `Turno` de saber si el empleado es barista o encargado.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 04)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que palabra declara que una clase hereda de otra?
* **Opcion Correcta:** **b) `extends`**
* **Justificacion:** Es la palabra clave de Java para denotar herencia de clases (JLS §8.1.4).

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** Al recorrer `List<Producto>`, el latte imprime "... · 350 ml". ¿Por que?
* **Opcion Correcta:** **b) Cada objeto usa su propia version sobrescrita**
* **Justificacion:** Gracias al polimorfismo y al despacho dinamico de metodos de la JVM, la llamada se enlaza con la implementacion de la clase real en tiempo de ejecucion.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** En Java una clase puede tener dos superclases directas.
* **Respuesta:** **Falso (F)**
* **Justificacion:** Java implementa exclusivamente herencia simple para clases para prevenir conflictos de resolucion de nombres e implementaciones ambiguas (problema del diamante).

### 4. Relacion de Conceptos (DOK 2)
* **`super(...)`** -> Llama al constructor del padre.
* **`@Override`** -> Confirma la sobrescritura.
* **`super.descripcion()`** -> Reusa el metodo del padre.
* **`List<LineaPedido>`** -> Composicion.

### 5. Ordenamiento Logico (DOK 2)
Pasos cronologicos de `new Bebida("Latte", 9500, 350)`:
1. Java elige el constructor de `Bebida`.
2. `super(...)` ejecuta el constructor de `Producto`.
3. Se asignan nombre y precio.
4. Se asigna ml.
5. La variable recibe el objeto.

### 6. Respuesta Abierta: Evaluacion de Herencia vs. Composicion (DOK 3)
* **Pregunta:** Explica con la prueba "es un / tiene un" por que `Pedido` no debe heredar de `LineaPedido`.
* **Respuesta:** Porque un pedido no es una linea de pedido. Una linea de pedido representa una unidad transaccional atomica (ej. 2 lattes a $19000). Si `Pedido` heredara de `LineaPedido`, estaria obligado a comportarse como si fuera un producto individual con cantidad y subtotal unico, lo cual contradice la naturaleza de un pedido, que es un contenedor de multiples compras. La relacion legitima es que un pedido **tiene** muchas lineas de pedido (composicion).

### 7. Respuesta Abierta: Justificacion de Herencia (DOK 3)
* **Pregunta:** Bloch y Gamma recomiendan preferir composicion. Da un caso de la cafeteria en que la herencia si es la mejor opcion y justificalo.
* **Respuesta:** La jerarquia de productos vendibles: `Bebida extends Producto` y `Postre extends Producto`. En este escenario, la sustitucion de Liskov se cumple al 100%: cualquier modulo del sistema (caja, inventario, vitrina digital) que consuma un `Producto` puede operar de forma identica con una `Bebida` o un `Postre`, compartiendo nombre, precio y descripcion comercial comun.

### 8. Respuesta Abierta: Creacion de Subclase (DOK 3)
* **Pregunta:** Escribe `class Te extends Bebida` con el atributo `origen` y su `descripcion()` sobrescrita.
* **Solucion:**
```java
public class Te extends Bebida {
    private final String origen;

    public Te(String nombre, int precio, int ml, String origen) {
        super(nombre, precio, ml);
        this.origen = origen;
    }

    @Override
    public String descripcion() {
        return super.descripcion() + " · origen: " + origen;
    }
}
```

---

## Cierre del Nivel 04: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo cuando estructurar jerarquias de tipos mediante `extends` y cuando modularizar sistemas usando colecciones y composicion, asegurando cero duplicidad y maxima legibilidad.

### 2. Explicacion Sintetica en 60 Segundos
Un latte es ontologicamente un producto: comparte precio, nombre y codigo de barras con todo lo que se vende en la barra, por lo que hereda de `Producto`. Por el contrario, un pedido no es un producto; es un documento transaccional que agrupa una o mas lineas de compra. Forzar herencia donde debe haber composicion genera sistemas rigidos e incoherentes.

### 3. Mapa Conceptual Completado
```text
[Reutilizar Codigo]
        |
        +--> Se declara con ----> [Herencia: extends y super]
        +--> Se marca con ------> [Sobrescribir: anotacion @Override]
        +--> Se arma con -------> [Composicion: atributos y List de partes]
        +--> Se ve en ----------> [Polimorfismo: invocacion dinamica en List padre]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Distingo y aplico con solvencia tecnica los dos pilares de reutilizacion en POO, implementando despacho polimorfico en calculos de nomina y estructuracion de pedidos sin acoplamientos innecesarios.
