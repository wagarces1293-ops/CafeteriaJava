# Nivel 10: Records: Datos Inmutables - Recibos que no se Alteran

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como represento comprobantes, recibos y elementos del menu que no puedan ser alterados una vez emitidos, sin escribir decenas de lineas repetitivas de codigo?
* **Mi Meta Personal:** Comprender el tipo `record` en Java como un portador transparente e inmutable de datos, dominando el constructor compacto para validaciones de negocio, los metodos accesores generados, la igualdad estructural por valor (`equals`/`hashCode`), la inmutabilidad defensiva en colecciones agregadas y la deconstruccion mediante patrones de record.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification (JLS SE 25)
* **Clase Record (JLS §8.10):** Un record es una declaracion de clase concisa y restringida disenada prioritariamente para actuar como un mero transportador de datos ("data carrier"). Todos sus campos son implicitamente `private final`.
* **Constructor Canonico y Compacto (JLS §8.10.4):** Un record posee un constructor canonico cuya firma coincide exactamente con la lista de componentes declarada en el encabezado. Se puede declarar en formato compacto (`public NombreRecord { ... }`), suprimiendo la lista formal de parametros y las asignaciones manuales, las cuales son inyectadas automaticamente por el compilador al final del bloque.
* **Metodos Accesores de Componentes (JLS §8.10.3):** Para cada componente declarado en el encabezado, el compilador genera un metodo publico de lectura que comparte exactamente el nombre del componente (ej. `precio()`, no `getPrecio()`).

### 2. Sintesis Personal del Dominio
Entiendo que en una cafeteria, una vez que la caja registradora emite un recibo impreso a un cliente, ese recibo se vuelve un documento historico sagrado: ni el cajero ni el cliente pueden cambiarle el precio al cafe o alterar la cantidad de lattes retroactivamente. Si modelamos un recibo con una clase tradicional, habria que escribir 30 lineas de codigo repetitivo (atributos privados, getters, constructores, `equals`, `hashCode`, `toString`) y aun existiria el riesgo de olvidar `final` o dejar un setter publico. Con un `record`, Java sintetiza todo ese andamiaje en 3 lineas garantizando inmutabilidad matematica y semantica estricta.

### 3. Vocabulario Tecnico Dominado
* **`record`:** Clase especial portadora inmutable de datos.
* **Componente de Record:** Campo declarado formalmente en el encabezado del record.
* **Constructor Compacto:** Bloque de validacion sin parametros ni asignaciones redundantes.
* **Accesor (`componente()`):** Metodo inspector sin prefijo `get`.
* **Igualdad por Valor:** Dos records son `equals` si todos sus componentes son iguales entre si, independientemente de residir en casillas de memoria distintas.
* **Inmutabilidad Superficial:** Los atributos primitivos del record no pueden cambiar, pero si contiene una referencia a un objeto mutable (como una `List`), la lista debe sellarse defensivamente con `List.copyOf()`.

---

## Sesion 02: Comprende (Ampliacion del Tema y Comparacion de Sintaxis)

### 1. Cuadro Comparativo: Clase Tradicional vs. Record
| Dimension Tecnica | Clase Tradicional (`class`) | Registro (`record`) |
| :--- | :--- | :--- |
| **Proposito** | Entidad con estado mutable y logica compleja. | Transportador inmutable de datos puros. |
| **Modificador de Campos** | Se deben marcar explicitamente como `private final`. | Son obligatoria e implicitamente `private final`. |
| **Constructores** | Escribir constructor canonico linea por linea. | Constructor compacto conciso para validar. |
| **Metodos Accesores** | Convencion JavaBean: `getNombre()`. | Directo con el nombre del campo: `nombre()`. |
| **Metodos de Object** | Escribir `equals`, `hashCode` y `toString` a mano. | Generados automaticamente por valor por el JDK. |
| **Herencia** | Puede extender otra clase (`extends`). | Hereda de `java.lang.Record`; no puede extender clases. |
| **Boilerplate** | Aprox. 28 a 35 lineas de codigo base. | 3 lineas concisas. |

### 2. Anatomia del Constructor Compacto
En `LineaRecibo.java`:
```java
public record LineaRecibo(String producto, int precio, int cantidad) {
    public LineaRecibo { // Sin parentesis ni tipos
        if (cantidad < 1) {
            throw new IllegalArgumentException("Cantidad minima: 1");
        }
        // Java inserta aqui de manera invisible:
        // this.producto = producto;
        // this.precio = precio;
        // this.cantidad = cantidad;
    }
}
```
El desarrollador solo se enfoca en las reglas del negocio: validar que la cantidad facturada sea al menos 1. Todo el cableado repetitivo de asignaciones es resuelto por la plataforma.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Salida de `IO.println(l1)` sin implementar `toString`:**
   Imprime `LineaRecibo[producto=Latte, precio=9500, cantidad=2]`. Java genera automaticamente un `toString` legible que expone el nombre del record y el par clave-valor de cada componente.
2. **Resultado de `l1.equals(otra)` con dos instancias independientes:**
   Devuelve `true`. En una clase comun sin sobreescribir `equals`, devolveria `false` porque compararia punteros en memoria; en un record, compara los valores de sus componentes (`"Latte" == "Latte"`, `9500 == 9500`, `2 == 2`).
3. **Total del recibo 1001:**
   `l1` subtotal: `9500 * 2 = 19000`.
   `l2` subtotal: `7000 * 1 = 7000`.
   Total sumado: `19000 + 7000 = $26000`.
4. **Valores en el patron `instanceof LineaRecibo(var p, var precio, var c)`:**
   `p = "Latte"`, `precio = 9500`, `c = 2`. El patron deconstructivo desarma la estructura atomica sin llamar a los accesores uno a uno.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Intentar reasignar un campo con `cantidad = cantidad * 2;`):**
  El compilador arroja: `error: cannot assign a value to final variable cantidad`. Demuestra la inmutabilidad de los componentes.
* **Experimento B (Instanciar con cantidad cero):**
  Lanza inmediatamente `IllegalArgumentException: Cantidad minima: 1`. El constructor compacto intercepta el dato anomalos en el acto.

---

## Sesion 04: Practica Guiada (Medios de Pago con Sealed Interfaces y Records)

### 1. Analisis de la Arquitectura
Se estructuro el esquema de pagos combinando una interfaz sellada (`sealed interface Pago`) con tres implementaciones como records: `Efectivo(int recibido)`, `Tarjeta(String franquicia, int monto)` y `Bono(int valor)`.
* Demuestra como un record puede implementar interfaces limpiamente.
* Facilita el procesamiento mediante `instanceof` tipado, permitiendo que cada forma de pago viaje con sus datos precisos e inalterables.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Que significa que un record sea "superficialmente inmutable"?**
   Significa que los campos inmediatos del record no pueden apuntar a otra direccion de memoria (son `final`). Sin embargo, si uno de esos campos es una referencia a un objeto internamente mutable (como un `ArrayList`), alguien con acceso a la lista podria ejecutar `lineas.add(...)` o `lineas.clear()`, alterando el contenido del recibo. Por eso en `Recibo`, el constructor compacto ejecuta obligatoriamente `lineas = List.copyOf(lineas);`, sellando la coleccion con una copia inmutable que garantiza inmutabilidad profunda.
2. **¿Deberia `Insumo` del Nivel 9 ser convertido a record?**
   No. Un insumo de bodega tiene una cantidad en stock que cambia docenas de veces durante el dia a medida que se preparan lattes y se reciben proveedores. Si fuera un record inmutable, cada descuento de 1 litro de leche obligaria a destruir el record actual y crear uno nuevo en memoria, complicando las actualizaciones en colecciones. Los records deben reservarse para datos que representen valores o fotografias fijas en el tiempo (recibos, items de menu, calificaciones).

---

## Sesion 06: Completa (Reto 1 de 2: Calificacion)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `record` (`public record Calificacion(...)` define la estructura inmutable).
* **Espacio 2:** `Calificacion` (`public Calificacion {` declara el constructor compacto).
* **Espacio 3:** `estrellas` (`return estrellas >= 4;` acceso al componente directo).
* **Espacio 4:** `cliente` (`a.cliente()` accesor estandar sin prefijo get).
* **Espacio 5:** `esBuena` (`a.esBuena()` llamado al metodo calificador).
* **Espacio 6:** `equals` (`a.equals(b)` comparacion estructural por valor).
* **Espacio 7:** `b` (`IO.println(b);` impresion de la instancia con toString generado).

### 2. Respuestas a las Preguntas de Control
1. **¿Por que el espacio 4 no es `getCliente`?**
   Porque en la especificacion oficial de records en Java, los metodos accesores generados adoptan exactamente el mismo identificador que el componente declarado en el encabezado (`cliente()`), distanciandose deliberadamente de la antigua convencion de JavaBeans.
2. **Si en el espacio 6 escribes `==` en lugar de `equals`, ¿que imprime?**
   Imprime `false`. El operador `==` compara la igualdad referencial de direcciones de memoria en el Heap. Al ser dos objetos creados con sentencias `new` distintas, residen en bloques de memoria diferentes. Para comparar su contenido por valor, es obligatorio usar `.equals()`.

---

## Sesion 07: Crea (Reto 2 de 2: El Menu del Dia como Datos)

### 1. Diseno e Implementacion del Subsistema de Menu Inmutable
* **Record `ItemMenu(String nombre, Categoria categoria, int precio)`:**
  * Constructor compacto: Valida `nombre` no blanco y `precio > 0`.
  * Metodo `withPrecio(int nuevoPrecio)`: Retorna un nuevo record con el precio actualizado, demostrando el patron funcional de actualizacion no destructiva.
* **Record `MenuDelDia(String fecha, List<ItemMenu> items)`:**
  * Constructor compacto: Sella la lista mediante `items = List.copyOf(items);`.
  * Metodo `promedioPrecio()`: Computa el promedio aritmetico de la carta del dia.
* **Reto Extra (+10 XP):** Intento intencional de mutar el menu emitido mediante `menu.items().add(...)`:
  Se intercepta y comprueba la excepcion `java.lang.UnsupportedOperationException`, validando que el menu publicado es incorruptible.

**Salida comprobada en consola:**
```text
--- Menu del Dia: 2026-11-23 ---
 - Espresso Doble (BEBIDA): $6500
 - Cheesecake de Frutos Rojos (POSTRE): $9000
 - Sandwich Artesanal (SNACK): $12500
Precio promedio del menu: $9333
¿Igualdad por valor en records? true
Excepcion capturada al intentar alterar el menu: UnsupportedOperationException
```

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Actua como tutor de Java SE. Explica como garantizar que un record MenuDelDia que almacena una lista de ItemMenu sea profundamente inmutable y como validar campos en el constructor compacto".
* **Respuesta evaluada:** Confirmo el uso de `List.copyOf` dentro del constructor compacto para crear una barrera defensiva.
* **Verificacion tecnica:** Comprobe que cualquier intento externo de mutar la lista posterior a la construccion arroja error en tiempo de ejecucion.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 10)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Como se llama el acceso al componente precio de un record?
* **Opcion Correcta:** **b) `precio()`**
* **Justificacion:** Los records generan accesores con el nombre directo del componente (JLS §8.10.3).

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** Dentro del record, `cantidad = cantidad * 2;` no compila. ¿Por que?
* **Opcion Correcta:** **b) Los campos de un record son final**
* **Justificacion:** Todo componente de un record es implicitamente inmutable (`final`).

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Dos records con los mismos valores en sus componentes son iguales segun `equals`.
* **Respuesta:** **Verdadero (V)**
* **Justificacion:** Java genera una implementacion automatica de `equals` y `hashCode` basada en la igualdad de valor de todos sus componentes.

### 4. Relacion de Conceptos (DOK 2)
* **Encabezado** -> Declara los componentes.
* **Constructor compacto** -> Valida antes de asignar.
* **`List.copyOf`** -> Protege la lista interna.
* **Patron de record** -> Extrae los componentes.

### 5. Ordenamiento Logico (DOK 2)
Pasos al ejecutar `new LineaRecibo("Latte", 9500, 2)`:
1. Java elige el constructor canonico.
2. Corre el cuerpo del compacto.
3. Se valida la cantidad.
4. Se asignan los tres campos.
5. La variable recibe el record.

### 6. Respuesta Abierta: Inmutabilidad Defensiva (DOK 3)
* **Pregunta:** Explica por que `Recibo` copia la lista en su constructor compacto y que podria pasar si no lo hiciera.
* **Respuesta:** Copia la lista mediante `List.copyOf(lineas)` para proteger la inmutabilidad profunda del recibo. Si el record guardara directamente la referencia de la lista recibida por parametro, el programa que lo instancio conservaria el puntero original y podria anadir o borrar productos despues de emitido el recibo (`listaOriginal.clear()`), adulterando el historial contable del negocio.

### 7. Respuesta Abierta: Criterio de Eleccion entre Record y Clase (DOK 3)
* **Pregunta:** Julian quiere convertir `Insumo` del nivel 9 en record. ¿Es buena idea? Justifica.
* **Respuesta:** No es una buena idea. Un insumo es una entidad transaccional viva cuyo stock varia continuamente durante el servicio de la cafeteria. Un record no permite setters; cada cambio de stock exigiria crear un nuevo objeto record y reemplazarlo en el inventario, aumentando la recoleccion de basura y complicando la logica. `Insumo` debe permanecer como una clase ordinaria con atributos privados mutables controlados por setters defensivos.

### 8. Respuesta Abierta: Creacion de Record Defensivo (DOK 3)
* **Pregunta:** Escribe `record Propina(String barista, int valor)` que rechace valores negativos.
* **Solucion:**
```java
public record Propina(String barista, int valor) {
    public Propina {
        if (barista == null || barista.isBlank()) {
            throw new IllegalArgumentException("Barista requerido");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("Propina negativa no permitida: " + valor);
        }
    }
}
```

---

## Cierre del Nivel 10: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo la generacion automatica de metodos en records, el valor del constructor compacto y la aplicacion de inmutabilidad profunda para auditoria de comprobantes.

### 2. Explicacion Sintetica en 60 Segundos
El total del recibo 1001 ya no puede cambiar despues de emitido porque fue construido como un `record` con componentes inmutables y una lista sellada con `List.copyOf`. Ninguna funcion del programa puede alterar sus campos primitivos ni inyectar productos a la lista; cualquier comprobante generado es un documento digital inalterable.

### 3. Mapa Conceptual Completado
```text
[Tipo record]
     |
     +--> Se declara en el -----------> [Encabezado: componentes inmutables]
     +--> Se valida en el ------------> [Constructor compacto: sin asignaciones manuales]
     +--> Se compara estructuralmente > [equals / hashCode automaticos por valor]
     +--> Se deconstruye con ---------> [Patrones de record en instanceof]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Distingo con precision los escenarios que requieren entidades mutables (clases) de aquellos que demandan inmutabilidad estricta (records), aplicando sellado de colecciones y validacion compacta en arquitecturas transaccionales.
