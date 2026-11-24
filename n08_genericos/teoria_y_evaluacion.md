# Nivel 08: Genericos - Un Repositorio para Todo

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como escribo un repositorio reutilizable para cualquier entidad (productos, clientes, insumos) sin duplicar clases y sin perder la seguridad de tipos en tiempo de compilacion?
* **Mi Meta Personal:** Comprender el mecanismo de parametrizacion de tipos (`Generics`) en Java, dominando la declaracion de clases y metodos genericos (`<T>`), el uso de limites superiores acotados (`<T extends Identificable>`), la lectura segura con comodines (`List<? extends Producto>`) y la eliminacion definitiva de conversiones explicitas de tipos (`casts`).

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification (JLS SE 25)
* **Clase Generica (JLS §8.1.2):** Una clase generica es una declaracion de clase que define uno o mas parametros de tipo formales (`<T>`). Introduce una familia de tipos parametrizados que comparten la misma logica ejecutable pero operan sobre argumentos de tipo disimiles.
* **Borrado de Tipos / Type Erasure (JLS §4.6):** Proceso del compilador de Java por el cual la informacion sobre los argumentos de tipo generico es verificada estrictamente en tiempo de compilacion y luego eliminada (borrada) en el bytecode resultante, reemplazandola por sus limites acotados u `Object`, garantizando plena compatibilidad retroactiva con versiones previas de la JVM.
* **Parametro Acotado / Bounded Type Parameter (JLS §4.4):** Limita los tipos que pueden sustituir a una variable de tipo mediante la sintaxis `<T extends Interfaz>`. Asegura que los metodos declarados en el limite esten disponibles dentro del cuerpo generico.

### 2. Sintesis Personal del Dominio
Entiendo que antes de los genericos (Java 1.4), los contenedores almacenaban `Object`. Eso obligaba al desarrollador a realizar "casts" manuales peligrosos (`Producto p = (Producto) lista.get(0);`). Si alguien insertaba por error un `Cliente` en esa lista, el programa compilaba sin problemas pero estallaba de forma catastrofica en produccion con `ClassCastException`. Con los genericos, el compilador se convierte en un vigilante implacable: en `Repositorio<Producto>`, cualquier intento de guardar un `Cliente` es rechazado de inmediato antes de generar el binario, y al recuperar un item (`productos.porId(2)`), el tipo de retorno es garantizado como `Producto` sin requerir ningun cast.

### 3. Vocabulario Tecnico Dominado
* **Parametro de Tipo (`<T>`):** Variable formal de tipo utilizada como comodin dentro de la clase generica.
* **Diamante (`<>`):** Inferencia automatica de tipos en el operador `new` (`new ArrayList<>()`).
* **Limite Superior (`<T extends Identificable>`):** Restriccion que exige que el tipo concreto herede o implemente la interfaz dada.
* **Comodin Covariante (`? extends SuperTipo`):** Permite lectura polimorfica segura de colecciones de subtipos.
* **Seguridad de Tipos (Type Safety):** Garantia de que ninguna operacion intentara tratar a un objeto como una instancia incompatible en memoria.

---

## Sesion 02: Comprende (Ampliacion del Tema y el Ciclo del Borrado)

### 1. Cuadro Comparativo: List sin Genericos vs. List Parametrizada
| Criterio Tecnico | Lista Cruda (`List`) | Lista Generica (`List<T>`) |
| :--- | :--- | :--- |
| **Verificacion de Tipos** | En tiempo de ejecucion (tardia). | En tiempo de compilacion (temprana). |
| **Casting Manual** | Obligatorio: `(Producto) lista.get(i)`. | Innecesario: el compilador conoce el tipo. |
| **Riesgo de Excepcion** | Alto: propenso a `ClassCastException`. | Nulo: imposible almacenar tipos anomalos. |
| **Claridad de la API** | Opaca (no revela que contiene la lista). | Auto-documentada (`List<Producto>`). |

### 2. Mecanica del Limite Acotado (`T extends Identificable`)
En `Repositorio.java`:
```java
public class Repositorio<T extends Identificable> {
    public T porId(int id) {
        for (T item : items) {
            if (item.id() == id) { // El compilador aprueba llamar a .id()
                return item;
            }
        }
        throw new IllegalArgumentException("No existe");
    }
}
```
Si la declaracion fuera simplemente `public class Repositorio<T>`, el metodo `item.id()` no compilaria, porque el compilador asume que `T` es un `Object` generico y `Object` no posee ningun metodo `id()`. Al acotar con `extends Identificable`, Java sabe que cualquier objeto valido que ocupe el lugar de `T` tendra implementado contractualmente el metodo `int id()`.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **¿Por que `Producto p = productos.porId(2);` no requiere cast?**
   Porque la instancia fue parametrizada como `Repositorio<Producto>`. El metodo `porId` sustituye formalmente `T` por `Producto` en la firma de retorno.
2. **Tipo de `T` en `ultimo(List.of("tinto", "latte", "moka"))`:**
   El compilador infiere que `T` es `String`. Devuelve `"moka"`.
3. **Paso de `List<Cliente>` a `sumarPrecios`:**
   Falla en tiempo de compilacion. La firma exige `List<? extends Producto>`. Como `Cliente` no hereda de `Producto`, el compilador bloquea la llamada con `argument mismatch`.
4. **Requisitos de `Producto.java` y `Cliente.java`:**
   Ambas clases deben obligatoriamente declarar `implements Identificable` y suministrar el metodo `public int id()`.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Intentar compilar `new Repositorio<String>()`):**
  Falla con: `type argument String is not within bounds of type-variable T`. La clase `String` no implementa `Identificable`.
* **Experimento B (Intentar guardar un `Cliente` en `productos.guardar(...)`):**
  Falla con: `method guardar in class Repositorio<T> cannot be applied to given types; required: Producto, found: Cliente`. La barrera de tipos del repositorio es inquebrantable.

---

## Sesion 04: Practica Guiada (Clase Generica Multi-Tipo `Par<A, B>`)

### 1. Analisis de la Solucion `Par.java`
Se diseno una tupla inmutable parametrizada con dos variables de tipo: `A` y `B`.
* Permite modelar asociaciones arbitrarias sin crear clases especificas descartables:
  * `Par<String, Integer>` para asociar nombre de bebida y precio.
  * `Par<Integer, String>` para asociar numero de mesa y nombre del comensal.
* Demuestra que una clase generica puede recibir multiples parametros de tipo delimitados por comas (`<A, B>`), conservando tipado estricto en cada getter.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Por que 46 de las 109 clases publicas de `java.util` son genericas?**
   Porque las estructuras de datos fundamentales (listas, mapas, conjuntos, colas) deben ser agnosticas al tipo de dato que almacenan. Si no fueran genericas, la biblioteca estandar de Java tendria que haber duplicado cientos de clases (`IntList`, `StringList`, `ProductoList`), generando un caos de mantenimiento y reduciendo drasticamente la reutilizacion de codigo.
2. **Impacto en el Negocio: ¿Por que un error de tipos al compilar es una victoria financiera?**
   Un error en compilacion le toma al desarrollador 10 segundos en ser corregido en su estacion de trabajo. Un error de tipos en produccion (por ejemplo, cobrar un cliente tratandolo como producto) tumba la caja registradora en plena hora pico del cafe, detiene las ventas, genera quejas y produce perdidas monetarias directas. El compilador generico es la primera linea de defensa del negocio.

---

## Sesion 06: Completa (Reto 1 de 2: Bandeja)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `<T>` (`public class Bandeja<T>` declara el parametro de tipo).
* **Espacio 2:** `T` (`private final List<T> cosas` coleccion interna tipada).
* **Espacio 3:** `<>` (`new ArrayList<>()` operador diamante de inferencia).
* **Espacio 4:** `T` (`public boolean poner(T cosa)` acepta solo el tipo de la bandeja).
* **Espacio 5:** `T` (`public T primera()` retorna el elemento sin casting).
* **Espacio 6:** `String` (`Bandeja<String> b = new Bandeja<>(2);` parametrizacion con texto).
* **Espacio 7:** `cantidad` (`b.cantidad()` consulta el tamano actual).

### 2. Respuestas a las Preguntas de Control
1. **¿Por que los espacios 2, 4 y 5 llevan la misma letra `T`?**
   Porque deben compartir exactamente el mismo tipo homogeneo. Lo que se almacena en la lista (`List<T>`), lo que entra como argumento (`poner(T)`) y lo que sale devuelto (`T primera()`) forman un contrato unificado. Si en el espacio 5 se escribiera `Object`, quien invoque el metodo estaria obligado a hacer un cast manual, destruyendo la ventaja fundamental de la generificacion.
2. **Si en el espacio 6 escribes `Integer`, ¿que linea deja de compilar?**
   Dejan de compilar las lineas `b.poner("latte")` y `b.poner("moka")`, puesto que el compilador exigiria enteros primitivos envueltos (`Integer`) y rechazaria cadenas literales `String`.

---

## Sesion 07: Crea (Reto 2 de 2: El Historial de la Barra)

### 1. Diseno e Implementacion de la Clase `Historial<T>`
* **Clase Generica `Historial<T>`:**
  * Atributo: `private final int capacidad;`.
  * Coleccion interna: `private final List<T> elementos = new ArrayList<>();`.
  * Metodo `agregar(T elemento)`: Valida no nulos; si el tamano alcanza la capacidad maxima, descarta el registro mas antiguo mediante `elementos.remove(0)` (politica FIFO) y agrega el nuevo elemento.
  * Metodo `ultimos()`: Devuelve una copia defensiva `new ArrayList<>(elementos)` preservando la inmutabilidad de la coleccion original.
  * Metodo `cantidad()`: Retorna `elementos.size()`.

**Salida comprobada en consola:**
```text
Historial lleno (3 pedidos): [Latte grande, Tinto mediano, Capuchino]
Historial tras nuevo ingreso: [Tinto mediano, Capuchino, Moka especial]
Ultimos montos cobrados: [7500, 24000]
```
* Se comprobo que la misma clase almacena indistintamente cadenas (`String`) o enteros (`Integer`) con total seguridad de tipos y sin casts.

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Como estudiante de Java, revisa si la implementacion de Historial<T> con limite circular FIFO usando List.remove(0) requiere comodines o si basta con el parametro T".
* **Respuesta evaluada:** Aclaro que para almacenamiento homogeneo directo, basta con `<T>`, reservando comodines (`? extends T`) solo si se recibieran colecciones masivas de entrada.
* **Verificacion tecnica:** Confirme que el diseno resultante es minimalista, limpio y no incurre en conceptos innecesarios para Programacion 1.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 08)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que simbolo se llama comodin en los genericos?
* **Opcion Correcta:** **c) `?`**
* **Justificacion:** El caracter de interrogacion representa el comodin (wildcard) en la sintaxis de tipos genericos de Java (JLS §4.5.1).

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** `new Repositorio<String>()` no compila. ¿Por que?
* **Opcion Correcta:** **b) `String` no implementa `Identificable`**
* **Justificacion:** El parametro formal esta acotado por `<T extends Identificable>`. Como `String` no cumple dicha cota, el compilador aborta.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Con `Repositorio<Producto>`, el compilador impide guardar un `Cliente`.
* **Respuesta:** **Verdadero (V)**
* **Justificacion:** La parametrizacion vincula los metodos de la instancia al tipo `Producto`, produciendo un error de tipos si se pasa una referencia de `Cliente`.

### 4. Relacion de Conceptos (DOK 2)
* **`<T>`** -> Parametro de tipo.
* **`new ArrayList<>()`** -> Diamante.
* **`T extends Identificable`** -> Parametro acotado.
* **`? extends Producto`** -> Comodin acotado.

### 5. Ordenamiento Logico (DOK 2)
Pasos para volver generica una clase repetida:
1. Detectar dos clases que solo cambian de tipo.
2. Cambiar el tipo concreto por `T`.
3. Agregar `<T>` a la declaracion.
4. Acotar `T` si se llaman metodos suyos.
5. Borrar la copia y probar con ambos tipos.

### 6. Respuesta Abierta: Falla en Compilacion vs. Ejecucion (DOK 3)
* **Pregunta:** Explica por que el programa sin genericos falla al ejecutarse y el generico falla al compilar. ¿Que cambia para el negocio?
* **Respuesta:** Sin genericos, la coleccion almacena referencias `Object`; el compilador no puede saber que objeto real se inserta y aprueba el codigo ciegamente. El error solo se manifiesta cuando un cliente ejecuta la aplicacion y un cast invalido detona un `ClassCastException`. Con genericos, el compilador comprueba la coherencia matematica de los tipos antes de compilar. Para el negocio cambia todo: se reemplazan caidas en produccion e interrupciones del servicio en la cafeteria por advertencias instantaneas y gratuitas en la terminal del desarrollador.

### 7. Respuesta Abierta: Evaluacion de `List<Object>` (DOK 3)
* **Pregunta:** Julian quiere usar `List<Object>` para todo "y asi no pelear con tipos". ¿Que le respondes?
* **Respuesta:** Le responderia que su propuesta es un retroceso tecnico a la era previa a Java 5. Usar `List<Object>` renuncia voluntariamente a la seguridad del compilador, obliga a plagar el codigo de comprobaciones manuales y casts propensos a errores, y oculta la intencion del diseno. Los tipos no son un obstaculo contra el cual pelear; son herramientas de diseno que aseguran que una comanda contenga comida y un arqueo contenga dinero.

### 8. Respuesta Abierta: Creacion de Metodo Generico (DOK 3)
* **Pregunta:** Escribe un metodo generico `<T> List<T> primeros(List<T> l, int n)` y una llamada con nombres de bebidas.
* **Solucion:**
```java
public static <T> List<T> primeros(List<T> lista, int n) {
    List<T> resultado = new ArrayList<>();
    int limite = Math.min(n, lista.size());
    for (int i = 0; i < limite; i++) {
        resultado.add(lista.get(i));
    }
    return resultado;
}

// Ejemplo de invocacion:
List<String> bebidas = List.of("Latte", "Tinto", "Capuchino", "Moka");
List<String> top2 = primeros(bebidas, 2); // Devuelve ["Latte", "Tinto"]
```

---

## Cierre del Nivel 08: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo la parametrizacion de tipos, los limites acotados con interfaces, la inferencia de tipo con diamante y la ventaja de erradicar los casts manuales.

### 2. Explicacion Sintetica en 60 Segundos
Un unico `Repositorio<T extends Identificable>` reemplaza a `RepositorioProducto`, `RepositorioCliente` y `RepositorioInsumo`. El algoritmo de busqueda por ID (`item.id() == id`) y de almacenamiento es identico en los tres casos; parametrizando con `T` escribimos la logica una sola vez y el compilador genera vistas seguras para cada entidad, garantizando cero duplicidad de codigo y total proteccion de tipos.

### 3. Mapa Conceptual Completado
```text
[Genericos]
     |
     +--> Se declara con Clase/Metodo --> [Parametros formales <T>]
     +--> Se acota con Limite ----------> [extends: garantiza presencia de metodos]
     +--> Se flexibiliza para Lectura --> [Comodin covariante ? extends]
     +--> Se gana en el Sistema --------> [Seguridad estricta en compilacion sin casts]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Integro abstracciones genericas acotadas para disenar estructuras de persistencia y buffers de historial reutilizables, contrastando la robustez en compilacion frente a tecnicas obsoletas basadas en `Object`.
