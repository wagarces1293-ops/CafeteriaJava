# Nivel 02: Encapsulamiento - La Caja que Nadie Descuadra

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como evita una clase que alguien, desde afuera, deje el saldo de la caja en un valor imposible?
* **Mi Meta Personal:** Dominar el principio de encapsulamiento mediante el uso estricto del modificador de acceso `private`, comprendiendo el concepto de invariante de clase y la aplicacion de metodos mutadores seguros que lanzan y capturan `IllegalArgumentException` ante entradas inconsistentes.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification (JLS SE 25)
* **Encapsulamiento y Ocultamiento de Informacion (JLS §6.6):** El control de acceso previene que el codigo externo haga uso indebido de los detalles de implementacion internos de una clase o acceda directamente a variables cuyo estado deba mantenerse coherente.
* **Modificador `private` (JLS §6.6.1):** Un miembro declarado como `private` es accesible unica y exclusivamente dentro del cuerpo de la declaracion de la clase en la cual fue introducido.
* **Invariante de Clase (Definicion de Meyer / Java SE Best Practices):** Es una condicion o regla logica de integridad sobre el estado de un objeto que debe cumplirse en todo momento: antes y despues de la ejecucion de cualquier metodo publico.

### 2. Sintesis Personal del Dominio
Entiendo el encapsulamiento no como una simple regla de ocultar variables, sino como la creacion de una barrera defensiva alrededor del estado del objeto. En Cafe Sintaxis, el saldo de la caja o el bote de propinas representan dinero fisico. Si el saldo fuera `public`, cualquier linea de codigo externa podria ejecutar `caja.saldo = -500;` o `caja.saldo = 0;` por error, rompiendo la congruencia del negocio. Al marcar `saldo` como `private`, obligo a todo el sistema a interactuar a traves de los metodos publicos `cobrar()` y `retirar()`, los cuales auditan las reglas antes de aplicar cualquier suma o resta.

### 3. Vocabulario Tecnico Dominado
* **Encapsulamiento:** Agrupacion y proteccion del estado interno y la logica asociada en una misma unidad de abstraccion.
* **`private`:** Modificador de visibilidad restrictivo a nivel de clase.
* **Getter (Metodo Inspector):** Funcion publica que expone el valor de un atributo privado sin otorgar facultades de escritura.
* **Setter (Metodo Mutador):** Procedimiento publico que actualiza un atributo previo filtro estricto de validacion.
* **Invariante:** Regla inquebrantable del negocio (ej. `saldo >= 0`).
* **`throw new IllegalArgumentException(...)`:** Instruccion que interrumpe el flujo normal para notificar que un argumento recibido es invalido.
* **Bloque `try/catch`:** Mecanismo de control para interceptar excepciones y evitar la caida abrupta del programa.
* **Referencia `this`:** Puntero autorreferencial a la instancia actual que resuelve ambiguedades de identificador.

---

## Sesion 02: Comprende (Ampliacion del Tema y Mecanismos de Acceso)

### 1. Matriz de Modificadores de Acceso en Java
| Modificador | Misma Clase | Mismo Paquete | Subclase (Herencia) | Mundo Exterior |
| :--- | :--- | :--- | :--- | :--- |
| `private` | Si | No | No | No |
| *(package-private)* | Si | Si | No | No |
| `protected` | Si | Si | Si | No |
| `public` | Si | Si | Si | Si |

### 2. Anatomia de una Regla Defensiva
En `Caja.java`:
```java
public void cobrar(int valor) {
    if (valor <= 0) {
        throw new IllegalArgumentException("Cobro invalido: " + valor);
    }
    saldo += valor;
    ventas++;
}
```
* **Guardia (Clausula de Escape):** La condicion `if (valor <= 0)` verifica que el dato de entrada sea matematicamente sensato.
* **Lanzamiento:** Si el valor viola la regla, la JVM crea y arroja un objeto `IllegalArgumentException`.
* **Detencion del flujo:** Al ejecutarse el `throw`, el metodo finaliza de inmediato. Las instrucciones `saldo += valor;` y `ventas++;` nunca llegan a ejecutarse, preservando el invariante intacto.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Salida de la linea 9 (`caja.resumen()`):** Se ejecutaron dos cobros validos (`19000` y `7000`). La suma arroja `26000` y el contador de ventas es 2. Salida esperada: `"2 ventas · saldo $26000"`.
2. **Ejecucion de `saldo += valor` ante cobro de `-500`:** No se ejecuta. La clausula de guardia intercepta el valor `-500` antes de modificar la variable de saldo, derivando el control al bloque `catch`.
3. **Saldo final impreso:** Permanece en `$26000`. El cobro invalido fue rechazado sin causar afectacion economica a la caja.
4. **Descomentar linea 12 (`caja.saldo = 0;`):** El programa falla en tiempo de compilacion, impidiendo la generacion del `.class`.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Descomentar `caja.saldo = 0;`):**
  Mensaje emitido por el compilador:
  `Main.java:error: saldo has private access in Caja`.
  Demuestra que la seguridad de Java opera en tiempo de compilacion.
* **Experimento B (Llamar `caja.retirar(30000)`):**
  Lanza `IllegalArgumentException: No hay $30000 en caja` en `Caja.java:18`. Al haber solo 26000, el saldo no puede quedar negativo.
* **Experimento C (Cambiar `valor <= 0` por `valor < 0`):**
  Aceptaria cobros de `$0`. Esto distorsionaria la metrica de `ventas`, sumando transacciones que no aportan ingresos monetarios a la cafeteria. La condicion `<= 0` es la formulacion correcta.

---

## Sesion 04: Practica Guiada (Producto Encapsulado)

### 1. Analisis de la Solucion de Julian (`Producto.java`)
Julian convirtio los atributos a `private`, expuso getters y un setter con validacion de rango: `0 <= precio <= 50000`.
* Uso de `this`: En `this.precio = precio;`, el prefijo `this.` califica inequívocamente al campo de la instancia frente al parametro local del metodo que posee el mismo identificador.

### 2. Resolucion de la Practica Guiada "Ahora lo hacemos juntos"
1. **Atributo `stock` y metodo `reponer(int unidades)`:**
   Implemente en `Producto.java`:
   ```java
   public void reponer(int unidades) {
       if (unidades <= 0) {
           throw new IllegalArgumentException("Unidades para reponer deben ser mayores a 0: " + unidades);
       }
       this.stock += unidades;
   }
   ```
   Al invocar `latte.reponer(0)`, la excepcion es capturada con el mensaje correspondiente, y el stock inicial no sufre modificaciones.
2. **¿Deberia existir `setStock`? Discusion tecnica:**
   No debe existir `setStock(int)`. Un metodo `setStock` permitiria sobreescribir arbitrariamente la cantidad fisica (por ejemplo, cambiar de 10 a 2 unidades sin justificar la perdida de 8). En un sistema comercial, el stock solo debe mutar por eventos trazables del negocio: reposicion (`reponer()`) o despacho (`vender()`).

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Por que en las clases publicas de `java.util` el 65.8% de los campos son `private`?**
   Porque las librerias base de Java deben garantizar retrocompatibilidad e invariantes rigurosos. Si los campos fueran publicos, cualquier cambio interno en futuras versiones de Java romperia millones de aplicaciones en el mundo. El encapsulamiento desacopla la interfaz publica de la estructura interna.
2. **¿Es preferible detectar un error al compilar o al cerrar la caja?**
   Detectar un error al compilar es infinitamente superior. Un fallo en compilacion se soluciona en segundos sin costo financiero. Un descuadre descubierto al cerrar la caja significa dinero extraviado, auditorias manuales complejas y desconfianza operativa.
3. **Invariante para la clase `Reserva` de mesas:**
   El invariante estipula que `personas >= 1 && personas <= 8` y que la reserva solo puede confirmarse si la mesa asociada tiene `ocupada == false`.
4. **¿Un getter deberia devolver siempre el atributo tal cual?**
   En tipos primitivos inmutables como `int` o clases inmutables como `String`, devolver el valor es completamente seguro porque se entrega una copia. Sin embargo, si el atributo fuera una estructura mutable (como una lista o arreglo), el getter deberia retornar una copia defensiva o una vista de solo lectura para evitar que el emisor modifique los datos internos sin supervision.

---

## Sesion 06: Completa (Reto 1 de 2: TarjetaRegalo)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `private` (`private int saldo;` protege el balance contra manipulacion externa).
* **Espacio 2:** `getSaldo` (Firma del getter `public int getSaldo()`).
* **Espacio 3:** `<=` (`if (valor <= 0)` previene recargas vacias o negativas).
* **Espacio 4:** `IllegalArgumentException` (Tipo de excepcion estandar arrojada ante argumentos ilegales).
* **Espacio 5:** `boolean` (`public boolean pagar(int valor)` retorna el exito o fallo de la transaccion).
* **Espacio 6:** `return` (`return true;` confirma que el debito se realizo exitosamente).
* **Espacio 7:** `getSaldo` (Consulta final de saldo sobre la tarjeta).

### 2. Respuestas a las Preguntas de Control
1. **¿Que pasaria si en el espacio 1 escribes `public`?**
   Se destruiria el invariante de solvencia de la tarjeta. Cualquier cliente o modulo podria ejecutar `t.saldo = 1000000;` para autoacreditarse fondos ficticios o `t.saldo = -500;` para dejar la tarjeta con balance negativo sin pasar por las validaciones de `pagar()` ni `recargar()`.
2. **Si en el espacio 3 usas `<` en lugar de `<=`, ¿que se acepta?**
   Se aceptaria una recarga de `$0`. Aunque no incremente el saldo, consumiria procesamiento y generaria un registro contable de recarga inutil en el sistema.

---

## Sesion 07: Crea (Reto 2 de 2: El Bote de Propinas)

### 1. Diseno e Implementacion de la Clase `BotePropinas`
* Atributos privados: `int total` e `int aportes`.
* Regla en `aportar(int valor)`: Valida `valor > 0` arrojando `IllegalArgumentException` si no se cumple; actualiza el total e incrementa en 1 el contador de transacciones.
* Regla en `repartir(int personas)`: Computa la division entera `total / personas` exigiendo `personas >= 1`.
* Metodo extra `sobrante(int personas)`: Calcula `total % personas` mediante el operador modulo.
* **Ecuacion de Invariante Financiero:**
  Se demostro empiricamente en `Main.java` que:
  `total == (personas * repartir(personas)) + sobrante(personas)`.
  Con un bote de `$15500` repartido entre 3 personas:
  `3 * 5166 + 2 = 15498 + 2 = 15500`. No se extravia un solo peso.

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Como estudiante de ingenieria, requiero formular las pruebas unitarias en consola para verificar los invariantes de un BotePropinas en Java con visibilidad privada, probando excepciones en aportes y repartos sin personas".
* **Respuesta evaluada:** Sugirio bloques `try/catch` independientes alrededor de `bote.aportar(-1000)` y `bote.repartir(0)`.
* **Verificacion tecnica:** Confirme que agrupar cada prueba en su propio bloque `try/catch` garantiza que el primer fallo capturado no aborte la evaluacion de la segunda excepcion.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 02)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que modificador deja un atributo visible solo dentro de su clase?
* **Opcion Correcta:** **c) `private`**
* **Justificacion:** Por definicion del JLS §6.6, `private` restringe el acceso al codigo delimitado entre las llaves de la clase declarante.

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** El compilador dice: `saldo has private access in Caja`. ¿Que arreglo respeta el encapsulamiento?
* **Opcion Correcta:** **b) Usar `caja.cobrar(...)` o `getSaldo()`**
* **Justificacion:** Cambiar el atributo a publico violaria el encapsulamiento. La solucion profesional es interactuar a traves de los canales publicos provistos por el diseno de la clase.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Un getter permite que otra clase cambie el valor del atributo privado.
* **Respuesta:** **Falso (F)**
* **Justificacion:** Un getter es una operacion de solo lectura que devuelve una copia del valor de un tipo primitivo. No provee ninguna via para asignar o mutar el estado en memoria del objeto emisor.

### 4. Relacion de Conceptos (DOK 2)
* **`private`** -> Esconde el dato.
* **`getSaldo()`** -> Consulta sin cambiar.
* **`throw`** -> Rechaza un dato invalido.
* **`catch`** -> Responde al error.

### 5. Ordenamiento Logico (DOK 2)
Pasos ordenados del metodo `cobrar`:
1. Recibir el valor.
2. Revisar si es 0 o menos.
3. Lanzar la excepcion si lo es.
4. Sumar al saldo.
5. Contar la venta.

### 6. Respuesta Abierta: Analisis de Asignaciones (DOK 3)
* **Pregunta:** En el setter, ¿que pasa si escribes `precio = precio;` en lugar de `this.precio = precio;`?
* **Respuesta:** Ocurre una asignacion del parametro local sobre si mismo (ocultamiento de campo / shadowing). La variable local `precio` sobreescribe el alcance del atributo. El atributo de la instancia no recibe ningun valor y retiene su estado anterior o por defecto (`0`), inutilizando la funcion del setter.

### 7. Respuesta Abierta: Evaluacion de Diseno (DOK 3)
* **Pregunta:** Martin propone un `setSaldo(int)` publico "por si acaso". ¿Lo aceptas? Justifica con el invariante de la caja.
* **Respuesta:** Rechazo categoricamente la propuesta. Un `setSaldo(int)` expone a la caja a manipulaciones arbitrarias sin auditoria. El saldo solo debe crecer por cobros certificados (`cobrar()`) o disminuir por egresos comprobados (`retirar()`). Permitir `setSaldo` rompe la coherencia entre el dinero acumulado y el historial de transacciones.

### 8. Respuesta Abierta: Creacion de Clase (DOK 3)
* **Pregunta:** Escribe la clase `Termo` con litros privados, `servir(int ml)` que rechace pedir mas de lo que hay, y su getter.
* **Solucion:**
```java
public class Termo {
    private int mililitros;

    public Termo(int mililitrosIniciales) {
        this.mililitros = mililitrosIniciales;
    }

    public void servir(int ml) {
        if (ml <= 0 || ml > mililitros) {
            throw new IllegalArgumentException("Cantidad a servir invalida o superior al contenido: " + ml);
        }
        mililitros -= ml;
    }

    public int getMililitros() {
        return mililitros;
    }
}
```

---

## Cierre del Nivel 02: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo como salvaguardar los datos criticos con `private`, como aplicar clausulas de guardia que lanzan excepciones informativas y como disenar invariantes matematicos robustos.

### 2. Explicacion Sintetica en 60 Segundos
Si el sistema de Cafe Sintaxis hubiese tenido el saldo de la caja declarado como `private`, el descuadre del viernes habria sido imposible: cualquier linea del programa que intentara alterar el saldo directamente habria sido rechazada de inmediato por el compilador de Java, impidiendo que el software entrara a operar con errores logicos.

### 3. Mapa Conceptual Completado
```text
[Encapsular]
     |
     +--> Se logra con ----> [Ocultar: modificador private]
     +--> Se hace con -----> [Exponer: getters y metodos publicos de negocio]
     +--> Se protege con --> [Validar: reglas defensivas e Invariantes]
     +--> Se comunica con -> [Avisar: throw new IllegalArgumentException y catch]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Integro el control de acceso, la preservacion de invariantes y la gestion de excepciones en una arquitectura donde ninguna transaccion financiera queda sin validar y el estado del sistema jamas alcanza una condicion indeterminada.
