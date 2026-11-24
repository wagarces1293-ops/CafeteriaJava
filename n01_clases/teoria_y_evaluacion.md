# Nivel 01: Clases, Metodos y Atributos - El Menu Cobra Vida

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como convierte Java la ficha de un producto en objetos que saben su precio y se venden solos?
* **Mi Meta Personal:** Comprender la distincion formal entre una clase como abstraccion estructural (molde) y un objeto como instancia en memoria (entidad viva con estado propio), dominando el uso de metodos con retorno para resolver reglas de negocio sin violar el encapsulamiento conceptual.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification (JLS SE 25)
* **Clase (JLS Cap. 8):** Una declaracion de clase define una clase nueva y describe de forma exhaustiva como esta implementada, especificando sus campos de datos y sus comportamientos ejecutables.
* **Atributo o Campo (JLS §8.3):** Las variables de una clase se introducen mediante declaraciones de campo; estas residen en la memoria reservada para cada instancia y almacenan su estado particular.
* **Metodo (JLS Cap. 8):** Las declaraciones de metodos describen fragmentos de codigo ejecutable que pueden invocarse mediante expresiones de invocacion de metodos, transformando o consultando el estado del objeto.

### 2. Sintesis Personal del Dominio
Entiendo que una clase funciona como el molde o plano arquitectonico: define que datos estructurales recordara cada elemento (`nombre`, `precio`, `stock`) y que acciones operativas puede desempeñar (`descripcion()`, `vender()`). Al invocar el operador `new`, la Maquina Virtual de Java (JVM) reserva un bloque independiente en el Heap para crear un objeto particular. Por ende, dos objetos originados en la clase `Producto`, como un "Latte" y un "Brownie", comparten exactamente la misma estructura de metodos y nombres de atributos, pero sus valores almacenados residen en direcciones de memoria aisladas.

### 3. Vocabulario Tecnico Dominado
* **Clase:** Molde declarativo que agrupa estado y comportamiento.
* **Objeto:** Instancia materializada en memoria a traves del operador `new`.
* **Atributo (Campo):** Variable que reside dentro del objeto y preserva su estado.
* **Metodo:** Subrutina asociada a la clase que implementa un comportamiento o calculo.
* **Parametro:** Variable receptora definida en la firma del metodo para recibir argumentos.
* **Retorno:** Tipo y valor que el metodo entrega al punto de invocacion.
* **Operador Punto (`.`):** Mecanismo de desreferenciacion para acceder a los miembros de la instancia.
* **Firma de un metodo:** Identificador unico compuesto por el nombre del metodo y el orden estricto de los tipos de sus parametros.
* **Estado:** Conjunto actual de valores alojados en los atributos de un objeto en un instante determinado.

---

## Sesion 02: Comprende (Ampliacion del Tema y Anatomia)

### 1. Cuadro Comparativo: Clase vs. Objeto
| Criterio Tecnico | Clase | Objeto (Instancia) |
| :--- | :--- | :--- |
| **Naturaleza** | Estructura conceptual abstracta (codigo fuente). | Entidad fisica en memoria Heap. |
| **Ciclo de vida** | Definida en tiempo de compilacion (`.java` a `.class`). | Creada dinamicamente en tiempo de ejecucion con `new`. |
| **Multiplicidad** | Unica por archivo fuente publico. | Arbitraria; tantas como requiera la logica de negocio. |
| **Almacenamiento** | Metadatos de la clase en el Metaspace. | Bloque de bytes con los campos en el Heap. |
| **Ejemplo en el Cafe** | `Producto` | `latte`, `brownie` |

### 2. Anatomia de la Firma de un Metodo
En la expresion:
```java
int vender(int cantidad) { ... }
```
* **Tipo de retorno (`int`):** Especifica el contrato de devolucion. El metodo promete entregar un entero (el monto cobrado).
* **Identificador (`vender`):** Verbo en infinitivo o imperativo que describe la accion operativa.
* **Parametro formal (`int cantidad`):** Entrada requerida para que el metodo pueda computar la disminucion de stock.
* **Firma formal:** `vender(int)`.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Invocacion `brownie.hayStock()`:** Predije que devolveria `false`. Al instanciar `brownie` sin asignar explicitamente `brownie.stock`, la JVM inicializa los enteros primitivos en su valor por defecto `0`. La condicion `stock > 0` resulta en `0 > 0`, devolviendo `false`.
2. **Valor de `cobro` en linea 11:** Al vender 2 unidades de Latte con precio 9500 y stock inicial 3: `2 * 9500 = 19000`. Como el stock (3) es mayor o igual a la cantidad pedida (2), el metodo descuenta las unidades y devuelve 19000.
3. **Estado del stock del Latte tras las ventas:**
   * Stock inicial: 3.
   * Tras `latte.vender(2)`: Queda 1 unidad.
   * Al ejecutar `latte.vender(5)`: Como 5 > 1, se activa el bloque defensivo `if (cantidad > stock)`, imprimiendo "No alcanza: hay 1" y devolviendo `0`, dejando el stock intacto en 1.
4. **Instanciacion sin inicializar:** Si se instancia `Producto vacio = new Producto();`, invocar `vacio.descripcion()` devuelve `"null · $0 · stock 0"`, reflejando los valores nulos y ceros por defecto en Java.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Omitir `latte.stock = 3`):** El atributo queda en 0. Al invocar `latte.vender(2)`, la comprobacion `2 > 0` se cumple de inmediato, reportando "No alcanza: hay 0" y retornando `0`.
* **Experimento B (Cambiar `int vender` por `void vender`):** El compilador genera un error de tipo `incompatible types: unexpected return value` en las instrucciones `return 0;` y `return cantidad * precio;`, ya que un metodo `void` no puede emitir expresiones de retorno con datos.
* **Experimento C (Crear tercer objeto `muffin`):** Compruebo experimentalmente que alterar los valores o el stock de `muffin` no afecta en absoluto las variables `latte` ni `brownie`, evidenciando el aislamiento en memoria de cada instancia.

---

## Sesion 04: Practica Guiada (Sistema de Fidelizacion)

### 1. Analisis de la Solucion de Fidelizacion (`Cliente.java`)
El diseno implementado por Ana asocia a cada cliente su acumulado de puntos.
* Division entera en Java: En la regla `puntos = puntos + compra / 1000;`, al evaluar una compra de `$12500`, la operacion `12500 / 1000` arroja exactamente `12` debido al truncamiento entero de tipos primitivos `int`, lo cual modela perfectamente la politica comercial de no otorgar puntos por fracciones de mil pesos.

### 2. Resolucion de la Practica Guiada "Ahora lo hacemos juntos"
1. **Metodo `canjear()`:**
   Implemente en `Cliente.java` el metodo:
   ```java
   void canjear() {
       if (puedeCanjear()) {
           puntos = puntos - 50;
       }
   }
   ```
   Al aplicarlo sobre Sofia (quien alcanzo 53 puntos tras su compra), sus puntos disminuyen en 50, quedando con 3 puntos.
2. **Segundo cliente con compra de `$9999`:**
   Al evaluar un cliente nuevo con 0 puntos que efectua una compra de `$9999`:
   `0 + 9999 / 1000 = 9` puntos.
   El cliente obtiene 9 puntos y no puede canjear cafe gratis (`9 >= 50` es `false`).

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Que ganaria Valeria si cada producto fuera un objeto y no una fila en el cuaderno?**
   Gana atomicidad, consistencia y proteccion de datos. En un cuaderno, las operaciones de descuento de inventario y cobro dependen del criterio manual del cajero, lo que facilita errores humanos. Con objetos en Java, el comportamiento esta encapsulado: un producto se niega a venderse si no dispone de existencias suficientes, garantizando que el stock jamas caiga en valores negativos.
2. **Propuesta de una clase adicional para Cafe Sintaxis:**
   Propongo la clase `Mesa`:
   * **Atributos:** `int numero`, `int capacidad`, `boolean ocupada`.
   * **Metodos:** `void ocupar(int personas)` y `void liberar()`.
3. **¿Por que una empresa elige una version LTS de Java?**
   Una organizacion de base tecnologica prioriza versiones LTS (Long-Term Support) debido a que garantizan soporte de parches de seguridad, estabilidad binaria y correcciones de fallos criticos durante un periodo prolongado (minimo 5 a 8 anos), reduciendo los costos de mantenimiento y el riesgo de incompatibilidades en entornos de produccion.
4. **¿Que metodo de `Producto` deberia devolver `void` y por que?**
   Un metodo mutador puro que solo actualice el estado sin computar un resultado para el emisor debe ser `void`. Por ejemplo, un metodo `void reabastecer(int unidades)` que simplemente ejecute `stock = stock + unidades;`. No requiere entregar un calculo de cobro a la caja registradora, sino unicamente modificar el estado interno.

---

## Sesion 06: Completa (Reto 1 de 2: Autocompleta Mesa)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `class` (Declara la estructura de la clase `Mesa`).
* **Espacio 2:** `boolean` (Tipo de dato idoneo para la bandera binaria `ocupada`).
* **Espacio 3:** `void` (La accion `liberar()` restablece el estado a cero y falso sin entregar datos).
* **Espacio 4:** `return` (Palabra reservada para devolver la cadena construida cuando la mesa esta ocupada).
* **Espacio 5:** `new` (Operador obligatorio para instanciar en memoria el objeto `Mesa`).
* **Espacio 6:** `ocupar` (Llamado al metodo que recibe la cantidad de comensales sentados).
* **Espacio 7:** `estado` (Invocacion del metodo inspector para reportar la situacion de la mesa tras liberarla).

### 2. Respuestas a las Preguntas de Control
1. **¿Por que el espacio 3 no puede ser `boolean`?**
   Porque la implementacion del metodo `liberar()` dentro de sus llaves unicamente ejecuta asignaciones (`personas = 0; ocupada = false;`) y carece de una clausula `return` con un valor de verdad. Para que fuese `boolean`, requeriria devolver explicitamente `true` o `false` informando si la liberacion fue exitosa.
2. **Si en el espacio 5 olvidas `new`, ¿que error arroja el compilador?**
   Al escribir `Mesa m4 = Mesa();`, el compilador emite un error indicando `cannot find symbol: method Mesa()`, dado que sin `new` la sintaxis es interpretada como la invocacion a un metodo ordinario no existente en el ambito de ejecucion.

---

## Sesion 07: Crea (Reto 2 de 2: Clase Barista)

### 1. Diseno e Implementacion de la Clase `Barista`
Se desarrollo la clase con atributos para `nombre`, `turno`, `bebidas` preparadas y `propinas` acumuladas.

**Casos de prueba implementados en `Main.java`:**
* Barista Martin: Prepara 3 Lattes. Su resumen arroja `"Martin (manana): 3 bebidas"` y `merecePausa()` resulta `false`.
* Barista Ana: Prepara 21 Capuchinos. Su resumen arroja `"Ana (tarde): 21 bebidas"` y `merecePausa()` resulta `true`.
* **Caso Limite (Exactamente 20 bebidas):** Probado con el barista Julian. Al llegar exactamente a 20 preparaciones, la evaluacion `20 >= 20` retorna `true`, confirmando que el operador relacional `>=` cubre adecuadamente la cota minima requerida.
* **Reto Extra de Propinas:** Metodo `recibirPropina(int valor)` probado ingresando un valor valido (`5000`) y un valor negativo (`-2000`). Se verifico que el saldo final permanecio en `$5000`, ignorando entradas incoherentes.

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Actua como tutor academico de Java para Programacion 1. Explica la evaluacion booleana del caso limite para un metodo que define si un trabajador merece pausa al alcanzar 20 bebidas, sin escribir codigo avanzado ni constructores".
* **Respuesta evaluada:** El asistente sugirio la expresion `bebidas >= 20`.
* **Verificacion tecnica:** Valide que el uso del operador mayor o igual (`>=`) es la opcion correcta sobre el operador estricto (`>`), puesto que el requerimiento estipula que a partir de la bebida 20 el trabajador tiene derecho al receso.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 01)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que palabra crea un objeto nuevo a partir de una clase?
* **Opcion Correcta:** **b) `new`**
* **Justificacion:** El operador `new` es la instruccion que solicita al recolector de basura y al administrador de memoria de la JVM espacio en el Heap para inicializar una nueva instancia.

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** El compilador dice: `cannot find symbol ... variable precioo`. ¿Que paso?
* **Opcion Correcta:** **b) El atributo no existe con ese nombre**
* **Justificacion:** El compilador de Java realiza comprobacion estricta de tipos e identificadores. Al existir un error tipografico (`precioo` con doble o), el simbolo no concuerda con ninguno de los campos definidos en la clase.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Dos objetos creados con la misma clase comparten siempre los mismos valores en sus atributos.
* **Respuesta:** **Falso (F)**
* **Justificacion:** Una clase solo define la estructura y el tipo de los atributos. Cada objeto instanciado mediante `new` posee su propio espacio independiente en memoria Heap, lo que le permite mantener valores de estado completamente diferentes.

### 4. Relacion de Conceptos (DOK 2)
* **`int stock;`** -> Atributo (campo de la clase).
* **`latte`** -> Objeto (referencia a una instancia).
* **`int cantidad`** -> Parametro (entrada de un metodo).
* **`boolean hayStock()`** -> Metodo (comportamiento con retorno booleano).

### 5. Ordenamiento Logico (DOK 2)
Pasos ordenados cronologicamente para vender 2 lattes e imprimir el cobro:
1. Escribir la clase `Producto`.
2. Crear el objeto con `new`.
3. Asignar nombre, precio y stock.
4. Llamar a `latte.vender(2)`.
5. Imprimir el resultado con `IO.println`.

### 6. Respuesta Abierta: Analisis de Metodos (DOK 3)
* **Pregunta:** ¿Por que `vender` devuelve `int` y no `void`? ¿Que perderia el programa de caja si fuera `void`?
* **Respuesta:** Devuelve `int` porque la caja registradora necesita conocer el monto exacto facturado por la transaccion para sumarlo al balance financiero del dia. Si fuera `void`, el metodo solo podria descontar el inventario, obligando al programa principal a recalcular el cobro externamente (`cantidad * precio`), lo cual duplicaria la logica de negocio y generaria riesgo de inconsistencias si la politica de cobro llega a cambiar.

### 7. Respuesta Abierta: Evaluacion de Tipos de Datos (DOK 3)
* **Pregunta:** Julian propone guardar el precio como texto (`String`). ¿Estas de acuerdo? Justifica con un ejemplo de cobro.
* **Respuesta:** No estoy de acuerdo. Los precios son valores cuantitativos sobre los cuales el sistema debe realizar operaciones aritmeticas obligatorias (multiplicacion por cantidad vendida, sumatorias de tickets, aplicacion de descuentos). Si se almacena como `String` (por ejemplo `"9500"`), no es posible multiplicar directamente `"9500" * 2`, forzando conversiones manuales continuas mediante `Integer.parseInt()`, lo que introduce riesgo de errores de ejecucion (`NumberFormatException`) y degrada el rendimiento.

### 8. Respuesta Abierta: Creacion de Clase (DOK 3)
* **Pregunta:** Escribe una clase `Domicilio` con 2 atributos y un metodo `despachar()` que cambie su estado.
* **Solucion:**
```java
public class Domicilio {
    String direccion;
    boolean entregado;

    void despachar() {
        entregado = true;
    }
}
```

---

## Cierre del Nivel 01: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Domino con claridad la sintaxis declarativa de clases, la instanciacion mediante `new`, la invocacion de metodos mediante el operador punto y la interpretacion de errores de compilacion relacionados con simbolos no encontrados o incompatibilidad de retornos.

### 2. Explicacion Sintetica en 60 Segundos
Un objeto `latte` y un objeto `brownie` provienen del mismo molde arquitectonico `Producto`. Sin embargo, cuando se ejecuta `new Producto()`, la JVM asigna una direccion de memoria unica para cada uno. El nombre, precio y stock de `latte` se guardan en el bloque de memoria de `latte`, mientras que los de `brownie` residen en un bloque enteramente independiente. El molde estipula la forma de la ficha, pero cada instancia es duena exclusiva de sus propios datos.

### 3. Mapa Conceptual Completado
```text
[Clase Producto]
       |
       +--> Se guarda en ------> [Archivo .java]
       +--> Se define con -----> [Atributos (Tipos de datos) y Metodos (Firma y Return)]
       +--> Se crean con ------> [Operador new] ----> Crean [Objetos en Memoria Heap]
       +--> Se accede con -----> [Operador punto .] -> Manipula [Estado y Comportamiento]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** No me limito a escribir atributos y metodos aislados; comprendo e implemento la interaccion entre el estado interno de la clase y los metodos de negocio que lo modifican de forma controlada (como proteger el stock ante solicitudes que exceden la disponibilidad), estructurando pruebas formales que validan las condiciones de borde del sistema.
