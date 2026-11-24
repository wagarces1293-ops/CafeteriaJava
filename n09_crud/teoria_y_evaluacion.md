# Nivel 09: CRUD Paso a Paso con ArrayList - El Inventario de la Barra

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como creo, consulto, actualizo y elimino insumos y reservas en memoria de forma segura sin retornar nunca `null`?
* **Mi Meta Personal:** Dominar las cuatro operaciones fundamentales de persistencia en memoria (Create, Read, Update, Delete) utilizando colecciones dinamicas `ArrayList`, gestionando llaves autoincrementales, protegiendo colecciones mediante copias defensivas inmutables y modelando ausencias mediante la envoltura `Optional<T>`.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification y API SE 25
* **CRUD:** Acronimo que sintetiza las cuatro operaciones esenciales sobre cualquier modelo de datos:
  * **C (Create):** Creacion e insercion de una nueva entidad (`List.add`).
  * **R (Read):** Lectura masiva (`listar()`) o puntual por identificador unico (`buscar(id)`).
  * **U (Update):** Actualizacion selectiva del estado de un registro preexistente (`setCantidad()`).
  * **D (Delete):** Remocion fisica o logica de un registro (`List.remove`).
* **`java.util.Optional<T>` (Java API Docs):** Objeto contenedor que puede o no albergar un valor no nulo. Su fin primordial es suministrar un tipo de retorno que exprese con claridad la posibilidad de ausencia de un dato, forzando al invocador a evaluar si el valor esta presente (`isPresent()`, `orElseThrow()`) antes de consumirlo, mitigando de raiz el riesgo de `NullPointerException`.
* **Copia Defensiva (`List.copyOf` / `new ArrayList<>()`):** Estrategia que entrega una replica desacoplada de la estructura interna para evitar que clientes externos alteren la lista original sin mediacion de los metodos de la clase administradora.

### 2. Sintesis Personal del Dominio
Entiendo que en la bodega de Cafe Sintaxis, el cuaderno de papel generaba borrones y tachones cuando se acababa la leche o se reponia el cafe. Implementar un CRUD estructurado en Java permite que cada insumo reciba un identificador numerico unico autoincremental (`siguienteId++`), de modo que jamas se confunda un lote con otro. Al consultar un insumo, desterramos la mala practica de retornar `null`: envolver el resultado en `Optional<Insumo>` obliga a quien consulte a contemplar explicitamente el caso donde el insumo no exista.

### 3. Vocabulario Tecnico Dominado
* **CRUD:** Ciclo de vida operacional sobre registros.
* **`ArrayList<T>`:** Arreglo redimensionable de acceso aleatorio indexado en tiempo constante $O(1)$.
* **Autoincremento (`id++`):** Generador secuencial de claves primarias.
* **`Optional.of(valor)`:** Empaqueta una referencia existente en una caja segura.
* **`Optional.empty()`:** Representa la ausencia legitima de un valor sin recurrir a `null`.
* **`orElseThrow()`:** Extrae el valor envuelto o dispara una excepcion si la caja esta vacia.
* **Copia Inmutable (`List.copyOf`):** Vista de solo lectura que arroja `UnsupportedOperationException` ante cualquier intento de modificacion externa.

---

## Sesion 02: Comprende (Ampliacion del Tema y Complejidad Algoritmica)

### 1. Rendimiento y Complejidad en ArrayList
| Operacion | Metodo en Java | Complejidad Temporal | Justificacion Tecnica |
| :--- | :--- | :--- | :--- |
| **Acceso por Indice** | `list.get(i)` | $O(1)$ Constante | Salto directo en memoria: direccion base + (i * tamano). |
| **Insercion al Final** | `list.add(item)` | $O(1)$ Amortizado | Inserta en la siguiente casilla libre del arreglo interno. |
| **Busqueda Lineal por ID** | Bucle `for` con `.getId() == id` | $O(n)$ Lineal | En el peor caso, debe comparar contra todos los elementos. |
| **Eliminacion por Posicion** | `list.remove(i)` | $O(n)$ Lineal | Debe desplazar todos los elementos posteriores una posicion a la izquierda. |

### 2. Anatomia del Flujo de Actualizacion Segura
En `Inventario.java`:
```java
public boolean actualizar(int id, int cantidad) {
    Optional<Insumo> o = buscar(id);
    if (o.isEmpty()) {
        return false;
    }
    o.get().setCantidad(cantidad); // Reutiliza validacion interna del objeto
    return true;
}
```
* **Busqueda previa:** Delega en el metodo `buscar(id)` para centralizar la localizacion.
* **Comprobacion de presencia:** Si `o.isEmpty()`, retorna `false` inmediatamente sin lanzar excepciones inesperadas.
* **Mutacion controlada:** Al extraer la referencia con `o.get()`, invoca a `setCantidad(cantidad)`, asegurando que si la nueva cantidad es negativa, sea la propia entidad `Insumo` quien lance `IllegalArgumentException("Cantidad negativa")`.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Asignacion de IDs y eliminacion de la Avena:**
   * La Leche recibe `id = 1`.
   * El Cafe en grano recibe `id = 2`.
   * La Avena recibe `id = 3`.
   * Al invocar `inv.eliminar(3)` y anadir posteriormente un nuevo insumo, este no reciclara el 3; recibira `id = 4` gracias al operador `siguienteId++`, garantizando unicidad cronologica e inmutabilidad de llaves.
2. **Salida de la linea 3 (`inv.buscar(9).isPresent()`):**
   Imprime `false`. Como el id 9 no existe, `buscar` devuelve `Optional.empty()`. No se lanza ninguna excepcion porque el `Optional` neutraliza el riesgo de desreferenciacion nula.
3. **Intento de mutar la lista devuelta por `listar()`:**
   Si se intentara ejecutar `inv.listar().add(...)`, la JVM detonaria `java.lang.UnsupportedOperationException` porque `List.copyOf()` entrega una lista inmutable.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Buscar con `orElseThrow()` sobre id inexistente):**
  Al invocar `inv.buscar(9).orElseThrow()`, se dispara de forma controlada `java.util.NoSuchElementException: No value present`.
* **Experimento B (Remover un elemento mientras se itera con bucle indexado):**
  Se verifica que al eliminar un elemento desplazando casillas, un bucle tradicional requiere control riguroso de indices para no saltarse el elemento adyacente que toma su lugar.

---

## Sesion 04: Practica Guiada (Menu de Consola para Inventario)

### 1. Analisis de la Arquitectura de Menu
Se estructuro la interfaz de linea de comandos en torno a un menu numérico estandarizado:
`[1] Crear [2] Listar [3] Cantidad [0] Salir`
* Permite al operador en caja registrar insumos y verificar el stock en tiempo real.
* El diseno desacopla completamente la interfaz de usuario por consola de la clase de logica de datos `Inventario`.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Por que una operacion `remove(0)` o `removeIf` es mas costosa en colecciones masivas?**
   En un `ArrayList`, los datos residen contiguos en memoria. Remover el primer elemento obliga a copiar y desplazar los $n-1$ elementos restantes una celda hacia atras mediante `System.arraycopy`. Si la coleccion tiene 1 000 000 de registros, borrar la mitad inicial toma milisegundos medibles; para 60 insumos de cafeteria, la operacion es imperceptible (fracciones de microsegundo) y el `ArrayList` es la estructura ideal por su bajo consumo de memoria.
2. **¿Con 60 insumos vale la pena migrar a un `HashMap`?**
   No se justifica. La sobrecarga de calculo de hashes, asignacion de nodos y consumo de memoria de un `HashMap` no compensa para 60 items. La simplicidad, iteracion lineal y orden determinista de `ArrayList` satisfacen con creces la operacion de la barra.

---

## Sesion 06: Completa (Reto 1 de 2: Lista de Espera)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `ArrayList` (`new ArrayList<>()` instanciacion de la lista).
* **Espacio 2:** `add` (`espera.add("Rojas (4)");` operacion de insercion Create).
* **Espacio 3:** `size` (`espera.size()` conteo de comensales en espera).
* **Espacio 4:** `getFirst` (`espera.getFirst()` inspeccion del primer turno en la cola).
* **Espacio 5:** `set` (`espera.set(1, "Gómez (5)");` actualizacion en posicion Update).
* **Espacio 6:** `removeIf` (o remocion controlada Delete del grupo asignado).
* **Espacio 7:** `contains` (`espera.contains(...)` comprobacion de presencia Read).

### 2. Respuestas a las Preguntas de Control
1. **¿Por que conviene buscar por condicion en vez de por indice fijo?**
   Porque en un entorno dinamico, las listas sufren cancelaciones o reordenamientos continuos. Si un comensal cancela su turno y se borra la posicion 0, la persona de la posicion 1 pasa a ser la 0. Si se usaran indices fijos codificados a mano, se eliminaria al cliente equivocado.
2. **Si en el espacio 4 usas `getLast`, ¿que cambia?**
   Cambia la linea de la salida; en lugar de atender al cliente mas antiguo en la cola (`"Rojas (4)"`), se imprimiria al ultimo que acaba de llegar (`"Perez (3)"`), violando la politica de atencion justa por orden de llegada.

---

## Sesion 07: Crea (Reto 2 de 2: La Agenda de Reservas)

### 1. Diseno e Implementacion de la Clase `Agenda`
* **Entidad `Reserva`:** Encapsula `id`, `cliente`, `personas` y `hora` (formato "18:30"). Valida en su constructor que el numero de comensales oscile estrictamente entre 1 y 8 personas.
* **Administrador `Agenda`:**
  * `crear(cliente, personas, hora)`: Asigna id autoincremental, crea la entidad y la registra.
  * `listar()`: Retorna una copia defensiva desvinculada para proteger el estado interno.
  * `buscar(id)`: Retorna `Optional<Reserva>` garantizando que ningun escenario devuelva `null`.
  * `actualizarHora(id, nuevaHora)`: Localiza la reserva por id y actualiza su horario.
  * `eliminar(id)`: Remueve la reserva correspondiente por id.
* **Reto Extra (+10 XP):** Metodo `listarOrdenadoPorHora()`:
  Implementado mediante ordenamiento estandar de colecciones con un comparador de cadenas sobre el atributo `hora`, asegurando presentacion cronologica para el barista y el maitre.

**Salida comprobada en terminal:**
```text
--- Reservas en la Agenda (Listar) ---
Reserva #1 · Valeria (4p) a las 20:00
Reserva #2 · Martin (2p) a las 18:30
Reserva #3 · Ana (6p) a las 19:15
Encontrada #2: Reserva #2 · Martin (2p) a las 18:30
¿Existe id 99? false
Tras actualizar hora: Reserva #2 · Martin (2p) a las 18:45
Tras eliminar #1, total activas: 2
--- Reservas Ordenadas por Hora ---
 * Reserva #2 · Martin (2p) a las 18:45
 * Reserva #3 · Ana (6p) a las 19:15
Capturado rechazo de personas: Capacidad de mesa permitida: 1 a 8 personas
```

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Actua como tutor de Java de Programacion 1. Revisa si un metodo buscar(id) en un CRUD sobre ArrayList debe retornar null o Optional<T>, justificando la eleccion segun estandares de software".
* **Respuesta evaluada:** Destaco que el uso de `Optional<T>` hace visible en la firma del metodo la contingencia de ausencia, erradicando fallos por `NullPointerException`.
* **Verificacion tecnica:** Comprobe que el flujo de control en `Main` evalua `isPresent()` con total limpieza y legibilidad sintactica.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 09)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que significa la U de CRUD?
* **Opcion Correcta:** **b) Update: actualizar**
* **Justificacion:** Representa la modificacion del estado de registros existentes.

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** `inv.listar().add(...)` lanza `UnsupportedOperationException`. ¿Por que?
* **Opcion Correcta:** **b) `listar()` devuelve una copia que no se puede modificar**
* **Justificacion:** Al utilizar `List.copyOf()`, Java genera una coleccion inmutable defensiva para blindar el repositorio contra modificaciones externas desautorizadas.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** `get(i)` en un `ArrayList` tarda mas cuanto mas grande es la lista.
* **Respuesta:** **Falso (F)**
* **Justificacion:** El acceso por indice en un `ArrayList` es de tiempo constante $O(1)$; la direccion fisica en memoria se calcula instantaneamente mediante aritmetica de punteros sin importar si la lista tiene 10 o 1 000 000 de casillas.

### 4. Relacion de Conceptos (DOK 2)
* **Crear** -> `add`
* **Leer uno** -> `buscar(id)`
* **Actualizar** -> `setCantidad` (o `setHora`)
* **Eliminar** -> `removeIf` (o `remove`)

### 5. Ordenamiento Logico (DOK 2)
Pasos de `actualizar(id, cantidad)`:
1. Buscar el insumo por id.
2. Si la caja esta vacia, devolver false.
3. Sacar el insumo del Optional.
4. Cambiar la cantidad.
5. Devolver true.

### 6. Respuesta Abierta: Retorno de Optional vs. Null (DOK 3)
* **Pregunta:** Explica que devuelve `buscar(9)` cuando no existe y por que eso es mejor que `null`.
* **Respuesta:** Devuelve `Optional.empty()`. Es infinitamente superior a retornar `null` porque `null` es un valor ciego: si el programador olvida escribir `if (resultado != null)`, cualquier llamada como `resultado.getNombre()` detona de inmediato un `NullPointerException` que tumba el programa. `Optional` obliga explicitamente al compilador y al desarrollador a comprobar la presencia mediante `.isPresent()` o manejar un valor por defecto con `.orElse(...)`, garantizando robustez operativa.

### 7. Respuesta Abierta: Escalabilidad y Complejidad (DOK 3)
* **Pregunta:** Valeria tendra 50 000 productos. ¿Seguirias con `ArrayList` para buscar por id? Justifica.
* **Respuesta:** No. Con 50 000 productos, una busqueda por id en `ArrayList` requiere un recorrido secuencial de complejidad lineal $O(n)$, teniendo que inspeccionar en promedio 25 000 comparaciones por consulta. Para ese volumen, la arquitectura debe migrar a un mapa asociativo por clave (`HashMap<Integer, Producto>`) que ofrece busquedas casi instantaneas en tiempo constante $O(1)$.

### 8. Respuesta Abierta: Creacion de Metodo de Comprobacion (DOK 3)
* **Pregunta:** Escribe el metodo `boolean existe(String nombre)` para `Inventario`.
* **Solucion:**
```java
public boolean existe(String nombre) {
    if (nombre == null) {
        return false;
    }
    for (Insumo i : insumos) {
        if (i.getNombre().equalsIgnoreCase(nombre)) {
            return true;
        }
    }
    return false;
}
```

---

## Cierre del Nivel 09: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo la gestion integral del ciclo CRUD, la autogeneracion de claves unicas, el encapsulamiento defensivo con copias inmutables y la manipulacion segura con `Optional`.

### 2. Explicacion Sintetica en 60 Segundos
El ciclo CRUD de Cafe Sintaxis administra la bodega de cafe en grano de forma ordenada: cuando llega un saco nuevo de cafe se crea con ID unico y stock inicial (`Create`); el barista consulta la existencia en pantalla mediante copias defensivas u `Optional` (`Read`); al preparar bebidas, descuenta las unidades (`Update`); y si el lote expira, se purga del inventario (`Delete`).

### 3. Mapa Conceptual Completado
```text
[Operaciones CRUD]
        |
        +--> Crear (C) ------> [Invocacion add con autoincremento de ID]
        +--> Leer (R) -------> [Listado seguro con copyOf y busqueda con Optional]
        +--> Actualizar (U) -> [Metodos mutadores defensivos set...]
        +--> Eliminar (D) ---> [Remocion controlada con remove / removeIf]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Integro colecciones dinamicas, validaciones de rango y envoltorios de nulidad para implementar un sistema de reservas y bodega completamente resiliente ante entradas inconsistentes o registros ausentes.
