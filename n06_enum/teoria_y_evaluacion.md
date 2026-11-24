# Nivel 06: Enum: Valores con Nombre - Tamanos y Estados

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como evito tamanos y estados mal escritos, restringiendo las entradas del negocio a un conjunto fijo y seguro de constantes con datos y metodos asociados?
* **Mi Meta Personal:** Comprender el tipo `enum` en Java como una clase especializada con un conjunto cerrado de instancias inmutables, dominando constructores privados, atributos finales, el metodo de transicion con expresiones `switch` exhaustivas, el orden ordinal y la utilizacion de `EnumMap` para operaciones de agrupamiento y conteo.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification (JLS SE 25)
* **Declaracion de Tipo Enum (JLS §8.9):** Una clase enum es una clase especial que define un conjunto finito y predeterminado de constantes. No puede ser instanciada con el operador `new` desde el exterior; la JVM crea una unica instancia inmutable por cada constante declarada al cargar la clase en memoria.
* **Constantes de Enum (JLS §8.9.1):** Son los identificadores que representan cada valor del conjunto cerrado. Son implicitamente `public static final`.
* **Metodos Sinteticos de Enum (JLS §8.9.3):** El compilador provee automaticamente en todo enum el metodo `values()` (que retorna un arreglo con todas las constantes en el orden exacto de su declaracion) y `valueOf(String)` (que convierte un texto en la constante correspondiente, distinguiendo estrictamente mayusculas y minusculas).

### 2. Sintesis Personal del Dominio
Entiendo que usar cadenas de texto libre (`String`) para variables clave como el tamano de un cafe (`"grande"`, `"Grande"`, `"GRANDE"`, `"mediano"`) o el estado de una orden (`"preparando"`, `"en barra"`) es una fuente critica de defectos de software. Un error tipografico no es detectado por el compilador y genera fallos silenciosos en tiempo de ejecucion. Un `enum` erradica este problema: restringe las opciones a un catalogo tipado e inalterable. Ademas, en Java los enums no son simples enteros (como en C/C++); son clases completas que pueden albergar atributos (`ml`, `recargo`), constructores y metodos de negocio.

### 3. Vocabulario Tecnico Dominado
* **`enum`:** Clase especial de Java para conjuntos finitos de constantes.
* **Constante de Enum:** Identificador en mayusculas sostenidas que representa una instancia unica.
* **`values()`:** Metodo estandar que entrega un arreglo con todas las constantes en orden de declaracion.
* **`ordinal()`:** Posicion entera indexada en cero (`0, 1, 2...`) que ocupa la constante en el enum.
* **`name()`:** Representacion textual exacta del nombre de la constante.
* **`valueOf(String)`:** Parseador tipado que busca la constante correspondiente o lanza `IllegalArgumentException`.
* **Expresion `switch` con Flechas (`->`):** Estructura selectiva exhaustiva que produce un resultado sin requerir clausula `break`.
* **`EnumMap`:** Implementacion de mapa de alto rendimiento basada internamente en un arreglo indexado por ordinales de enum.

---

## Sesion 02: Comprende (Ampliacion del Tema y Ciclos de Transicion)

### 1. Cuadro Comparativo: String Arbitrario vs. Tipo Enum
| Factor de Calidad | Texto Libre (`String`) | Tipo Enumerado (`enum`) |
| :--- | :--- | :--- |
| **Seguridad de Tipos** | Nula (acepta cualquier cadena, incluso vacias). | Total (solo admite las constantes declaradas). |
| **Deteccion de Errores** | Tardia (en ejecucion, comparando textos). | Inmediata (en tiempo de compilacion). |
| **Rendimiento de Comparacion** | `equals()` compara caracter por caracter. | `==` compara direcciones unicas de memoria. |
| **Asociacion de Datos** | Requiere estructuras paralelas o clases extra. | Nativa (cada constante guarda sus atributos). |
| **Control en Switch** | Riesgo de ramas faltantes sin advertencia. | Compilador valida exhaustividad total. |

### 2. Maquina de Estados y Transicion con Switch
En `EstadoPedido.java`:
```java
public EstadoPedido siguiente() {
    return switch (this) {
        case RECIBIDO -> EN_PREPARACION;
        case EN_PREPARACION -> LISTO;
        case LISTO, ENTREGADO -> ENTREGADO;
    };
}
```
* **Exhaustividad Garantizada:** Dado que el enum solo tiene cuatro constantes (`RECIBIDO`, `EN_PREPARACION`, `LISTO`, `ENTREGADO`) y todos los casos estan explicitamente cubiertos en las flechas, el compilador aprueba el switch sin necesidad de una clausula `default`.
* Si un desarrollador anade manana el estado `CANCELADO` al enum, el switch fallara inmediatamente al compilar, advirtiendo que falta definir que ocurre con el pedido cancelado.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Orden de impresion de `Tamano.values()` y `EnumMap`:**
   `Tamano.values()` imprime estrictamente en el orden de declaracion: `PEQUENO`, `MEDIANO`, `GRANDE`. En el `EnumMap`, aunque se inserte primero `LISTO` (3) y luego `RECIBIDO` (5), el mapa imprime `{RECIBIDO=5, LISTO=3}` porque internamente organiza las entradas segun el orden ordinal del enum, garantizando predictibilidad determinista.
2. **Vueltas del bucle `while` y valor de `ordinal()` para `LISTO`:**
   El bucle arranca en `RECIBIDO` y avanza en 3 transiciones:
   * Vuelta 1: Pasa a `EN_PREPARACION` (ordinal 1).
   * Vuelta 2: Pasa a `LISTO` (ordinal 2). Imprime `2 LISTO → ¡Listo para recoger!`.
   * Vuelta 3: Pasa a `ENTREGADO` (ordinal 3). Finaliza la condicion `e != ENTREGADO`.
3. **Comportamiento de `Tamano.valueOf("Grande")`:**
   Lanza inmediatamente `IllegalArgumentException: No enum constant Tamano.Grande`. El metodo es sensible a mayusculas y minusculas y exige concordancia textual exacta (`"GRANDE"`).

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Anadir `FAMILIAR(600, 4000)` a `Tamano`):**
  Al anadir la constante al enum, `Tamano.values()` la incorpora de inmediato al final del arreglo con volumen 600 ml y recargo $4000 sin modificar una sola linea del bucle impresor.
* **Experimento B (Intentar ejecutar `new Tamano(100, 0)` desde `Main`):**
  El compilador arroja: `enum types may not be instantiated`. Los constructores de enum son obligatoriamente privados para preservar la unicidad de las instancias.

---

## Sesion 04: Practica Guiada (Categorizacion y EnumMap)

### 1. Analisis de la Solucion de Categoria (`Categoria.java`)
Se definio el enum `Categoria` con `CALIENTE`, `FRIA` y `POSTRE`, asociando una descripcion legible.
* En `Main.java`, se configuro `EnumMap<Categoria, Integer>` para consolidar metricas de ventas.
* Eficiencia de `EnumMap`: A diferencia de un `HashMap` ordinario que requiere hashing, resolucion de colisiones y envoltura de nodos, un `EnumMap` utiliza internamente un arreglo plano de tamano identico a la cantidad de constantes, logrando accesos a memoria en tiempo constante $O(1)$ sin sobrecarga.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **Rendimiento comparativo (20 millones de lecturas: 14 ms en `EnumMap` frente a 54 ms en `HashMap`):**
   La diferencia radica en que las claves de un enum poseen un ordinal entero consecutivo garantizado. `EnumMap` indexa un arreglo unidimensional directamente mediante `ordinal()`, prescindiendo del calculo de `hashCode()` y de la comparacion `equals()`. Es la estructura optima para agrupar inventario y transacciones en sistemas de alto trafico.
2. **¿Por que guardar `ordinal()` en una base de datos o archivo es una mala practica?**
   Porque `ordinal()` depende exclusivamente de la posicion fisica en el codigo fuente. Si en el futuro un programador reordena las constantes o inserta una nueva al principio (por ejemplo, anteponer `EXTRA_PEQUENO` antes de `PEQUENO`), todos los registros historicos de la base de datos quedaran corruptos y desplazados. Se debe persistir siempre el nombre inmutable (`name()`).

---

## Sesion 06: Completa (Reto 1 de 2: Leche)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `enum` (`public enum Leche` declara el tipo enumerado).
* **Espacio 2:** `final` (`private final int recargo;` inmutabilidad del sobrecosto).
* **Espacio 3:** `Leche` (`Leche(int recargo)` constructor privado del enum).
* **Espacio 4:** `switch` (`return switch (this)` evaluacion de constantes).
* **Espacio 5:** `default` (`default -> false;` caso para las leches de origen animal).
* **Espacio 6:** `values` (`Leche.values()` arreglo iterable de leches).
* **Espacio 7:** `recargo` (`l.recargo()` metodo inspector del valor adicional).

### 2. Respuestas a las Preguntas de Control
1. **¿Por que el switch del espacio 4 necesita `default`?**
   Porque solo agrupa explicitamente dos constantes (`case AVENA, ALMENDRAS -> true;`). Si no se incluye `default -> false;`, el compilador rechaza el switch por no ser exhaustivo, dado que faltan por definir los casos de `ENTERA` y `DESLACTOSADA`.
2. **Si en el espacio 6 escribes `valueOf`, ¿que error arroja?**
   Arroja `cannot find symbol: method valueOf()`, dado que `valueOf` exige obligatoriamente un argumento de tipo `String` con el nombre de la constante deseada.

---

## Sesion 07: Crea (Reto 2 de 2: El Plano del Salon)

### 1. Diseno e Implementacion del Subsistema de Salon
* **Enum `EstadoMesa`:**
  * Constantes: `LIBRE`, `OCUPADA`, `POR_LIMPIAR`, `RESERVADA`.
  * Atributo: `String aviso` que describe la situacion operativa.
  * Metodo `siguiente()`: Maquina de transicion finita:
    * `LIBRE -> OCUPADA` (al sentarse clientes).
    * `OCUPADA -> POR_LIMPIAR` (al pagar y desocupar).
    * `POR_LIMPIAR -> LIBRE` (tras desinfeccion de barra).
    * `RESERVADA -> OCUPADA` (al presentarse la reserva).
* **Clase `Mesa`:** Encapsula `numero`, `capacidad` y `EstadoMesa`. Incluye metodo `avanzarEstado()` que transiciona segun la regla del enum.
* **Control con `EnumMap`:** Conteo consolidado del estado de ocupacion del salon en tiempo real.

**Salida comprobada en consola:**
```text
--- Estado Inicial del Salon ---
Mesa 1 (capacidad 4) · LIBRE [Disponible para clientes]
Mesa 2 (capacidad 2) · RESERVADA [Apartada con antelacion]
Mesa 3 (capacidad 6) · OCUPADA [Comensales en servicio]
Mesa 4 (capacidad 4) · POR_LIMPIAR [En proceso de aseo y desinfeccion]

--- Estado tras Transiciones ---
Mesa 1 (capacidad 4) · OCUPADA [Comensales en servicio]
Mesa 2 (capacidad 2) · RESERVADA [Apartada con antelacion]
Mesa 3 (capacidad 6) · POR_LIMPIAR [En proceso de aseo y desinfeccion]
Mesa 4 (capacidad 4) · POR_LIMPIAR [En proceso de aseo y desinfeccion]

--- Conteo del Tablero de Salon (EnumMap) ---
LIBRE: 0 mesas
OCUPADA: 1 mesas
POR_LIMPIAR: 2 mesas
RESERVADA: 1 mesas
```

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Actua como tutor de Java. Explica como modelar el ciclo de vida de una mesa de cafeteria mediante un enum EstadoMesa con transicion siguiente() exhaustiva sin clausula default".
* **Respuesta evaluada:** Presento un switch de flechas mapeando cada estado a su sucesor logico directo.
* **Verificacion tecnica:** Confirme que al cubrir los 4 estados individualmente, el compilador garantiza la completitud del ciclo de vida del negocio sin admitir transiciones invalidas.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 06)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que metodo devuelve todas las constantes de un enum en orden?
* **Opcion Correcta:** **b) `values()`**
* **Justificacion:** Metodo sintetico inyectado por el compilador en todas las clases enum (JLS §8.9.3).

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** `Tamano.valueOf("Grande")` lanza una excepcion. ¿Por que?
* **Opcion Correcta:** **b) El nombre debe coincidir exacto: `GRANDE`**
* **Justificacion:** `valueOf` exige coincidencia literal sensible a mayusculas.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Desde otra clase se puede crear un tamano nuevo con `new Tamano(600, 4000)`.
* **Respuesta:** **Falso (F)**
* **Justificacion:** Los constructores de enum son privados y no pueden ser invocados con `new` desde el exterior; las unicas instancias que existen son las constantes definidas en su cuerpo.

### 4. Relacion de Conceptos (DOK 2)
* **`Tamano.GRANDE.ml()`** -> `470`
* **`Tamano.MEDIANO.ordinal()`** -> `1`
* **`Tamano.PEQUENO.name()`** -> `"PEQUENO"`
* **`Tamano.values().length`** -> `3`

### 5. Ordenamiento Logico (DOK 2)
Secuencia del ciclo de vida de un pedido:
1. `RECIBIDO`
2. `EN_PREPARACION`
3. `LISTO`
4. `ENTREGADO`

### 6. Respuesta Abierta: Analisis de Exhaustividad en Switch (DOK 3)
* **Pregunta:** Explica por que agregar el estado `CANCELADO` hace que el switch de `siguiente()` deje de compilar, y por que eso es bueno.
* **Respuesta:** Deja de compilar porque una expresion `switch` sobre un enum sin clausula `default` debe ser obligatoriamente exhaustiva: debe saber que devolver para cada constante existente. Esto es sumamente beneficioso para la ingenieria de software porque transforma un potencial olvido o error logico en un error de compilacion inmediato, obligando al programador a decidir explicitamente que transicion sigue un pedido cuando es cancelado.

### 7. Respuesta Abierta: Evaluacion de Persistencia (DOK 3)
* **Pregunta:** Martin quiere guardar los tamanos como 1, 2 y 3 en un archivo. ¿Que le recomiendas? Justifica con `ordinal()`.
* **Respuesta:** Le desaconsejo totalmente esa practica. Los ordinales corresponden a la posicion fisica del codigo. Si en una futura version se anade `TAMANO_PERSONAL` o se reordenan las constantes para satisfacer un menu digital, el numero 1 pasara a representar una bebida distinta, corrompiendo los historicos contables. La recomendacion profesional es guardar la constante en texto usando `name()` (ej. `"MEDIANO"`).

### 8. Respuesta Abierta: Creacion de Enum con Calculo (DOK 3)
* **Pregunta:** Escribe un enum `MedioPago` con un atributo `comisionPct` y un metodo `neto(int valor)`.
* **Solucion:**
```java
public enum MedioPago {
    EFECTIVO(0),
    TARJETA(3),
    APP_MOVIL(2);

    private final int comisionPct;

    MedioPago(int comisionPct) {
        this.comisionPct = comisionPct;
    }

    public int neto(int valor) {
        return valor * (100 - comisionPct) / 100;
    }
}
```

---

## Cierre del Nivel 06: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo el uso de tipos enumerados con atributos, metodos y transiciones controladas mediante switch exhaustivo, asi como el beneficio estructural de `EnumMap`.

### 2. Explicacion Sintetica en 60 Segundos
Utilizar `Tamano.GRANDE` en lugar del texto `"grande"` protege al sistema desde el momento de la escritura: el compilador valida la existencia de la constante, impidiendo errores tipograficos accidentales (`"grnade"`), optimiza las comparaciones mediante igualdad directa de punteros (`==`) y vincula directamente las propiedades fisicas (mililitros y sobreprecio) al propio valor.

### 3. Mapa Conceptual Completado
```text
[Tipo enum]
     |
     +--> Se listan como ------> [Valores: constantes inmutables]
     +--> Se guardan en -------> [Datos: atributos final y constructor privado]
     +--> Se toma con ---------> [Decision: switch exhaustivo sin default]
     +--> Se lleva en ---------> [Conteo: coleccion especializada EnumMap]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Modelo procesos y catalogos finitos del negocio mediante enums enriquecidos, orquestando maquinas de estados consistentes y aprovechando el despacho estatico de tipos para blindar el software contra inconsistencias.
