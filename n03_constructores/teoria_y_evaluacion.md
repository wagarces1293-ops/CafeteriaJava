# Nivel 03: Constructores y Sobrecarga - Bebidas Bien Nacidas

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como nace cada bebida con todos sus datos y como evito que existan productos en un estado inconsistente?
* **Mi Meta Personal:** Comprender el ciclo de instanciacion en Java, implementando constructores con inicializacion de atributos inmutables (`final`), delegacion de constructores mediante `this(...)`, sobrecarga de metodos y el analisis comparativo del prologo de constructores entre Java 21 y Java 25.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification (JLS SE 25)
* **Constructor (JLS §8.8):** Un constructor se invoca en una expresion de creacion de instancias de clase (`new`) para inicializar un objeto recien creado antes de que cualquier referencia al mismo sea expuesta. No posee tipo de retorno y comparte obligatoriamente el identificador exacto de la clase.
* **Constructor por Defecto (JLS §8.8.9):** Si una clase no declara ningun constructor de forma explicita, el compilador de Java suministra de forma automatica un constructor sin parametros (`default constructor`) con visibilidad correspondiente a la de la clase.
* **Sobrecarga de Constructores y Metodos (JLS §8.4.9):** Ocurre cuando dos o mas miembros de una clase comparten el mismo nombre pero difieren en la cantidad o en los tipos de sus parametros formales.

### 2. Sintesis Personal del Dominio
Comprendo que un constructor es el acta de nacimiento de un objeto: garantiza que una bebida en Cafe Sintaxis no entre en operacion en un estado incompleto o nulo. En lugar de permitir que alguien cree una `Bebida` vacia y luego intente asignarle atributos con setters (con el riesgo latente de olvidar el precio o el nombre), el constructor exige todos los datos vitales en el instante mismo de invocar `new`. Ademas, mediante la delegacion con `this(...)`, evito duplicar codigo de validacion, concentrando las reglas en un unico constructor canonico.

### 3. Vocabulario Tecnico Dominado
* **Constructor:** Subrutina de inicializacion de instancias sin tipo de retorno.
* **Modificador `final` en Atributos:** Asegura que una variable de instancia reciba un valor una unica vez y no pueda ser reasignada con posterioridad (inmutabilidad a nivel de referencia o primitivo).
* **Sobrecarga (Overloading):** Definicion de multiples versiones de un constructor o metodo con diferente lista de parametros.
* **Invocacion `this(...)`:** Llamada explicita a otro constructor alternativo dentro de la misma clase.
* **Prologo de Constructor:** Bloque de sentencias que validan parametros antes de invocar a `this(...)` o `super(...)` (estandarizado en Java 25 JEP 512; resuelto en Java 21 LTS mediante metodos validadores estaticos en la expresion del argumento).
* **Firma de Sobrecarga:** Diferenciacion por aridad o tipo de datos de entrada (`precioCon(int)` frente a `precioCon(String)`).

---

## Sesion 02: Comprende (Ampliacion del Tema y Mecanica de Delegacion)

### 1. Cuadro Comparativo: Constructor vs. Metodo
| Criterio Tecnico | Constructor | Metodo |
| :--- | :--- | :--- |
| **Identificador** | Coincide exactamente con el nombre de la clase. | Cualquier identificador valido (habitualmente un verbo). |
| **Tipo de Retorno** | Ninguno (ni siquiera `void`). | Obligatorio (`void` o tipo concreto). |
| **Momento de Invocacion** | Exclusivamente al ejecutar `new` o mediante `this(...)`. | En cualquier momento sobre una instancia ya creada. |
| **Herencia** | No se hereda a las subclases. | Se hereda y puede ser sobrescrito (`@Override`). |
| **Proposito** | Inicializar y asegurar invariantes de nacimiento. | Ejecutar operaciones de computo o transformar estado. |

### 2. Diagrama de Flujo de Delegacion de Constructores
```text
Invocacion en Main:
new Bebida("Tinto", 3500)
        |
        v
Constructor Corto: Bebida(String nombre, int precio)
        |
        +--> Validacion previa: nombre no blanco
        |
        +--> Delega con this(nombre, "mediano", precio, false)
                    |
                    v
            Constructor Completo: Bebida(String, String, int, boolean)
                    |
                    +--> Valida regla: precio > 0
                    +--> Asigna atributos final:
                         this.nombre = nombre;
                         this.tamano = "mediano";
                         this.precio = 3500;
                         this.leche = false;
```

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Atributos del tinto en linea 9 (`new Bebida("Tinto", 3500)`):** Al usar el constructor corto, la delegacion con `this(...)` le asigna de manera predeterminada el tamano `"mediano"` y `leche = false`. Salida: `"Tinto mediano $3500"`.
2. **Version de `precioCon` en linea 10:** La expresion `a.precioCon(2)` pasa el literal entero `2`. Java ejecuta la sobrecarga `precioCon(int extras)`. El calculo es: `11000 + 2 * 1500 = 14000`. Cobro: `$14000`.
3. **Calculo del cupon `CAFE10`:** Ejecuta la sobrecarga `precioCon(String cupon)`. Al coincidir el texto mediante `.equals()`, computa el 10% de descuento mediante division entera: `11000 * 90 / 100 = 9900`. Cobro: `$9900`.
4. **Instanciacion con nombre en blanco (linea 12):** La validacion `nombre.isBlank()` detecta que el texto `" "` solo contiene espacios en blanco y lanza `IllegalArgumentException("Sin nombre")`. La delegacion `this(...)` nunca llega a completarse y el objeto jamas nace en memoria.

### 2. Pregunta de Control: Sobrecarga con Tipos Primitivos Amplios
* **Pregunta:** Si se agregara `public int precioCon(long extras)`, ¿cual se ejecuta al invocar `precioCon(2)`?
* **Respuesta:** Se ejecuta la version `precioCon(int extras)`. En Java, el compilador resuelve la sobrecarga buscando la coincidencia mas especifica. Como el literal `2` es de tipo `int`, coincide de manera exacta con el parametro `int` sin requerir una promocion primitiva a `long`.

### 3. Registro de Experimentos de 2 Minutos
* **Experimento A (Intentar invocar `new Bebida()` sin parametros):** El compilador arroja: `error: no suitable constructor found for Bebida(no arguments)`. Al haber declarado constructores con parametros, Java retira el constructor por defecto sin argumentos.
* **Experimento B (Cambiar atributos a no-final y no inicializarlos en el constructor):** El compilador lo permite, pero se pierde la garantia de inmutabilidad del menu. Dejar los atributos como `final` fuerza al compilador a comprobar que cada campo quede inicializado obligatoriamente.

---

## Sesion 04: Practica Guiada (Sistema de Reservas)

### 1. Analisis de la Solucion de Ana (`Reserva.java`)
Ana implemento `Reserva` con tres atributos inmutables (`final`): `cliente`, `personas` y `hora`.
* El constructor principal valida que la cantidad de comensales este entre 1 y 8 personas (`personas < 1 || personas > 8`), arrojando excepcion en caso contrario.
* El constructor secundario recibe unicamente `cliente` y `personas`, delegando a `this(cliente, personas, "19:00")` para asignar la hora pico por defecto.
* Metodo sobrecargado `aviso()` y `aviso(int minutosAntes)` para emitir recordatorios diferenciados.

### 2. Resolucion de la Practica Guiada "Ahora lo hacemos juntos"
1. **Tercer constructor `Reserva(String cliente)`:**
   Se puede anadir una sobrecarga para reservas individuales:
   ```java
   public Reserva(String cliente) {
       this(cliente, 1, "19:00");
   }
   ```
   Reutiliza la cadena de constructores fijando 1 persona como capacidad minima por defecto.
2. **Evaluacion del prologo y delegacion:**
   Si una validacion se repite en cada constructor sobrecargado, se viola el principio DRY (Don't Repeat Yourself). Centralizar la asignacion en el constructor completo y delegar mediante `this(...)` garantiza que si la capacidad maxima cambia a 10 personas en el futuro, solo se requiera modificar una sola linea de codigo en todo el proyecto.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Por que una clase no deberia tener un unico constructor que reciba "cualquier cosa"?**
   Porque rompe el tipado estricto y la intencion de diseno. Si una clase aceptara parametros genericos sin estructura, se trasladaria la carga de comprobaciones complejas al tiempo de ejecucion, incrementando exponencialmente los errores de software. La sobrecarga tipada permite a la API expresar con precision que combinaciones de datos son validas para el negocio.
2. **¿Que problema resolvio el prologo de constructores en Java 25 frente a versiones previas?**
   Historicamente, Java exigia que `this(...)` o `super(...)` fuera estrictamente la primera linea ejecutable del constructor. Si se deseaba validar o transformar un argumento antes de delegar, los desarrolladores se veian obligados a usar metodos estaticos auxiliares en la expresion o a duplicar asignaciones. Java 25 permitio ejecutar sentencias previas al llamado del constructor (siempre que no lean campos de la instancia aun no inicializada), otorgando maxima claridad sintactica.
3. **Propuesta de dos sobrecargas utiles para `cobrar`:**
   * `cobrar(int monto)`: Para transacciones ordinarias en efectivo.
   * `cobrar(int monto, String codigoPromocional)`: Para aplicar cupones de descuento institucionales calculando la reduccion antes del registro final.

---

## Sesion 06: Completa (Reto 1 de 2: Postre)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `Postre` (`public Postre(...)` nombre exacto de la clase para el constructor).
* **Espacio 2:** `this` (`this.nombre = nombre;` califica el atributo frente al parametro).
* **Espacio 3:** `this` (`this(nombre, precio, false);` invoca al constructor completo).
* **Espacio 4:** `false` (Valor booleano por defecto indicando que el postre contiene azucar tradicional).
* **Espacio 5:** `boolean` (`public String etiqueta(boolean detallada)` tipo del parametro de sobrecarga).
* **Espacio 6:** `etiqueta` (`return etiqueta();` reutiliza la etiqueta base sin duplicar codigo).
* **Espacio 7:** `new` (`Postre a = new Postre("Brownie", 7000);` operador de instanciacion).

### 2. Respuestas a las Preguntas de Control
1. **¿Por que el espacio 1 no lleva tipo de retorno?**
   Porque en Java, los constructores no son metodos; son inicializadores de estructura. Si se le antepone `void` (`public void Postre(...)`), el compilador lo interpreta como un metodo ordinario que casualmente comparte el nombre de la clase, y la clase quedaria carente de constructor explicito.
2. **Si en el espacio 4 colocas `true`, ¿que imprime la primera linea?**
   Al invocar `a.etiqueta(true)` sobre el Brownie, la condicion `detallada && sinAzucar` se evaluaria como `true && true`, imprimiendo falsamente `"Brownie $7000 · sin azucar"`, rotulando incorrectamente un producto de reposteria regular.

---

## Sesion 07: Crea (Reto 2 de 2: Los Combos de la Tarde)

### 1. Diseno e Implementacion de la Clase `Combo`
* Atributos inmutables: `private final String bebida;`, `private final String postre;`, `private final int precio;`.
* Constructor completo: Valida que ningun texto este en blanco mediante `.isBlank()` y que el `precio > 0`.
* Constructor corto: `public Combo(String bebida, String postre)`, que delega mediante `this(bebida, postre, 12000)` aplicando la tarifa promocional de la tarde de Cafe Sintaxis.
* Metodo `descripcion()` sobrecargado:
  * `descripcion()`: Retorna formato estandar `"Combo: Bebida + Postre · $Precio"`.
  * `descripcion(int porcentajeDescuento)`: Computa el descuento y muestra el valor final liquidado.
* Casos limite probados: Verificacion de rechazo ante precio igual a cero y ante cadenas de texto compuestas unicamente por espacios en blanco.

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Como estudiante universitario de Java, explica el beneficio de marcar los atributos de la clase Combo como private final cuando se cuenta con dos constructores sobrecargados".
* **Respuesta evaluada:** Destaco que `final` garantiza inmutabilidad, obligando a que cualquier camino de construccion (`this` completo o delegado) deje los campos inicializados sin posibilidad de modificaciones posteriores.
* **Verificacion tecnica:** Comprobe que si se omite inicializar un campo `final` en el constructor completo, el compilador emite un error de compilacion inmediato: `variable precio might not have been initialized`.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 03)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que distingue a un constructor de un metodo?
* **Opcion Correcta:** **b) Usa el nombre de la clase y no tiene tipo de retorno**
* **Justificacion:** Son las dos reglas sintacticas fundamentales exigidas por la especificacion JLS §8.8.

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** Escribiste un constructor con parametros y ahora `new Bebida()` no compila. ¿Por que?
* **Opcion Correcta:** **b) El constructor por defecto solo existe si no declaras ninguno**
* **Justificacion:** De acuerdo con la JLS §8.8.9, en el momento en que el desarrollador define manualmente cualquier constructor, el compilador anula la creacion automatica del constructor por defecto.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Dos metodos con el mismo nombre y los mismos parametros, pero distinto tipo de retorno, son una sobrecarga valida.
* **Respuesta:** **Falso (F)**
* **Justificacion:** La firma de un metodo en Java esta compuesta unicamente por su nombre y la lista ordenada de tipos de sus parametros. El tipo de retorno no forma parte de la firma y no permite desempatar llamadas ambiguas.

### 4. Relacion de Conceptos (DOK 2)
* **`this.precio`** -> El atributo del objeto.
* **`this(...)`** -> Llama a otro constructor.
* **`final`** -> Se asigna una sola vez.
* **Prologo** -> Validar antes de delegar.

### 5. Ordenamiento Logico (DOK 2)
Secuencia exacta de ejecucion para `new Bebida("Tinto", 3500)`:
1. Java elige el constructor de 2 parametros.
2. El prologo valida el nombre.
3. `this(...)` llama al constructor completo.
4. Se valida el precio.
5. Se asignan los 4 campos.

### 6. Respuesta Abierta: Delegacion con `this(...)` (DOK 3)
* **Pregunta:** Explica por que conviene que el constructor corto delegue con `this(...)` en vez de copiar las asignaciones.
* **Respuesta:** Conviene porque centraliza las reglas de negocio y las asignaciones de campos en un solo punto del codigo. Si se duplicaran las lineas `this.nombre = nombre; this.precio = precio;` en cada constructor, cualquier ajuste futuro (como anadir una auditoria o validar un rango maximo) deberia replicarse en multiples sitios, aumentando el riesgo de inconsistencias y fallas de mantenimiento.

### 7. Respuesta Abierta: Evaluacion de Constructores frente a Setters (DOK 3)
* **Pregunta:** Julian prefiere setters a constructores "porque es mas flexible". ¿Que riesgo vimos en este nivel?
* **Respuesta:** El riesgo primordial es la instanciacion de objetos en estado temporalmente corrupto o incompleto. Con setters, existe un lapso de tiempo donde el objeto ya nacio con valores en cero o nulos, y si un hilo de ejecucion o metodo lo consume antes de llamar a los setters requeridos, el sistema falla con `NullPointerException` o genera transacciones ilegales. Los constructores obligan a que el objeto nazca completo y valido desde su primer milisegundo de existencia.

### 8. Respuesta Abierta: Creacion de Constructores (DOK 3)
* **Pregunta:** Escribe dos constructores para `Domicilio(direccion, valor)` y `Domicilio(direccion)` con valor 4000 por defecto.
* **Solucion:**
```java
public class Domicilio {
    private final String direccion;
    private final int valor;

    public Domicilio(String direccion, int valor) {
        if (direccion.isBlank() || valor <= 0) {
            throw new IllegalArgumentException("Datos de domicilio invalidos");
        }
        this.direccion = direccion;
        this.valor = valor;
    }

    public Domicilio(String direccion) {
        this(direccion, 4000);
    }
}
```

---

## Cierre del Nivel 03: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo la inicializacion inmutable, el enlace dinamico de firmas en sobrecarga y la delegacion interna con `this(...)`.

### 2. Explicacion Sintetica en 60 Segundos
Un capuchino con precio `$0` ya no puede existir en Cafe Sintaxis porque la unica compuerta de fabricacion es el constructor de `Bebida`. Este constructor somete el valor ingresado a una compuerta logica ineludible: si el precio es menor o igual a cero, aborta inmediatamente con una excepcion y jamas entrega una referencia en memoria, garantizando que todo producto en el sistema tenga respaldo financiero real.

### 3. Mapa Conceptual Completado
```text
[Constructor]
     |
     +--> Se activa con ------> [Crear: operador new]
     +--> Se protege con -----> [Validar: prologo, reglas y throw]
     +--> Se encadena con ----> [Reutilizar: invocacion this(...)]
     +--> Se ofrecen con -----> [Variantes: sobrecarga de firmas]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Articulo el diseno de clases inmutables asegurando que las dependencias requeridas se proporcionen en el momento de la instanciacion, eliminando estados transitorios invalidos y optimizando la jerarquia de constructores mediante delegacion estricta.
