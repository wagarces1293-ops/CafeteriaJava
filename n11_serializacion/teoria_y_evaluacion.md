# Nivel 11: Serializacion Moderna con Archivos - La Cafeteria Recuerda

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como guarda Java la bodega y las reservas en un archivo al cerrar y las recupera intactas al dia siguiente, sin abrir la puerta a vulnerabilidades de seguridad ni datos corruptos?
* **Mi Meta Personal:** Comprender el mecanismo de serializacion binaria de Java (`java.io.Serializable`), dominando la gestion segura de flujos mediante sentencias `try-with-resources`, la proteccion obligatoria de deserializacion con filtros de lista blanca (`ObjectInputFilter`), la reconstruccion canónica de records que revalida invariantes y la exportacion en texto plano CSV para interoperabilidad empresarial.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Java Language Specification y API SE 25
* **Serializacion de Objetos (Java Object Serialization Specification):** Proceso mediante el cual el grafo de objetos en memoria de una aplicacion Java se codifica en una secuencia lineal de bytes para ser almacenada en un soporte permanente (disco) o transmitida por red.
* **Interfaz Marcadora `Serializable` (JLS §9.6.1):** Interfaz sin metodos ni constantes utilizada como etiqueta de tipo para autorizar a la Maquina Virtual a inspeccionar y serializar los campos de la clase.
* **Filtro de Deserializacion (`ObjectInputFilter` - JEP 290):** Mecanismo de seguridad obligatorio en tiempo de ejecucion que intercepta el flujo de bytes antes de instanciar cualquier objeto, contrastando la clase entrante contra una lista blanca (`whitelist`) de tipos autorizados y rechazando cualquier clase no declarada (`!*`).

### 2. Sintesis Personal del Dominio
Entiendo que si la cafeteria apaga el computador al finalizar la noche, todo el inventario de la bodega y las reservas del sabado que residian en la memoria RAM desaparecerian. Para persistir los datos, Java provee dos vias complementarias:
1. **Persistencia Binaria Interna (`.ser`):** Guarda grafos de objetos exactos con `ObjectOutputStream`. Es veloz y compacta, pero peligrosa si no se usa un filtro de lista blanca que bloquee inyecciones de objetos maliciosos.
2. **Exportacion Textual Interoperable (`.csv`):** Guarda lineas delimitadas por punto y coma con `java.nio.file.Files`. Es universal, legible por seres humanos y apta para que la contadora la procese en hojas de calculo sin depender de Java.

### 3. Vocabulario Tecnico Dominado
* **Serializar:** Convertir objetos del Heap en una corriente binaria de bytes (`writeObject`).
* **Deserializar:** Reconstruir objetos vivos a partir de una secuencia de bytes (`readObject`).
* **`try-with-resources`:** Estructura de control que garantiza el cierre automatico determinista de flujos (`AutoCloseable`), incluso si ocurren excepciones.
* **Filtro de Lista Blanca (`"Clase;!*"`):** Regla de seguridad estricta donde solo las clases explicitadas pasan y todo lo demas es rechazado con `InvalidClassException`.
* **Reconstructibilidad de Records:** A diferencia de las clases tradicionales que reconstruyen objetos burlando constructores mediante reflexion interna, la deserializacion de un `record` invoca forzosamente su constructor canonico, revalidando todas las reglas e invariantes del constructor compacto.

---

## Sesion 02: Comprende (Ampliacion del Tema: Binario vs. Texto Plano)

### 1. Cuadro Comparativo: Serializacion Binaria (`.ser`) vs. CSV (`.csv`)
| Criterio Tecnico | Archivo Binario Java (`.ser`) | Archivo de Texto Delimitado (`.csv`) |
| :--- | :--- | :--- |
| **Destinatario** | Exclusivamente la propia aplicacion Java. | Personas, contadores, hojas de calculo u otros lenguajes. |
| **Preservacion de Tipos** | Exacta: preserva la jerarquia y tipos de Java. | Basica: todo es texto; exige conversiones manuales (`parseInt`). |
| **Velocidad y Tamano** | Rapido y comprimido para estructuras densas. | Texto plano UTF-8; tamano predecible y editable. |
| **Riesgo de Seguridad** | Critico si se usa sin filtro (RCE / Gadgets). | Nulo a nivel de ejecucion de codigo en Java. |
| **Filtro Requerido** | Obligatorio: `ObjectInputFilter` con `!*`. | No aplica. |

### 2. Anatomia del Cierre Seguro y el Filtro
En `Almacen.java`:
```java
try (var in = new ObjectInputStream(Files.newInputStream(archivo))) {
    in.setObjectInputFilter(
        ObjectInputFilter.Config.createFilter("Insumo;java.util.ArrayList;java.lang.Object;!*")
    );
    return (List<Insumo>) in.readObject();
}
```
* **Apertura protegida:** El bloque `try (...)` asegura que tanto el flujo de entrada a bajo nivel como el `ObjectInputStream` se cierren al salir del bloque.
* **Inyeccion de filtro:** Antes de invocar a `readObject()`, se fija el filtro. La cadena `"Insumo;java.util.ArrayList;java.lang.Object;!*"` autoriza unicamente los registros de la bodega y listas, mientras que el sufijo final `!*` rechaza categoricamente cualquier otra clase del classpath.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Salida de "Al abrir" en la primera ejecucion:**
   Imprime `Al abrir: []` (lista vacia). El metodo `cargar()` verifica `Files.notExists(archivo)` y retorna una lista vacia sin arrojar errores de archivo ausente.
2. **Evaluacion de `hoy.equals(leidos)` tras la lectura:**
   Devuelve `true`. Aunque los objetos reconstruidos residen en nuevas posiciones de memoria fisica, los records implementan igualdad estructural por valor; sus componentes coinciden dato a dato.
3. **Causa del rechazo ante un archivo con cantidad `-3`:**
   Al deserializar un record, Java utiliza obligatoriamente su constructor canonico. El constructor compacto de `Insumo` ejecuta `if (cantidad < 0) throw new IllegalArgumentException(...)`, abortando de inmediato la deserializacion del archivo corrupto.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Retirar `implements Serializable` de `Insumo`):**
  Al ejecutar `guardar()`, la JVM arroja: `java.io.NotSerializableException: Insumo`. Java prohibe serializar clases que no hayan otorgado consentimiento explicito.
* **Experimento B (Cambiar el filtro a `!*` rechazando todo):**
  Arroja: `java.io.InvalidClassException: filter status: REJECTED`. Demuestra que el filtro actua como aduana antes de procesar el flujo.

---

## Sesion 04: Practica Guiada (Persistencia en Formato CSV)

### 1. Analisis de la Solucion CSV
Se implemento el ciclo completo de exportacion y lectura de texto:
* `aLinea(Insumo i)`: Formatea el registro concatenando sus campos con punto y coma: `id;nombre;cantidad`.
* `deLinea(String linea)`: Divide la linea mediante `split(";")` y recrea el record invocando `Integer.parseInt()` sobre las partes numericas.
* Se utilizo `Files.write` y `Files.readAllLines` de `java.nio.file`, garantizando codificacion uniforme en UTF-8.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Que formato enviarias a la contadora y cual usarias para el estado interno?**
   * **A la contadora:** Envio `.csv`. La contadora no tiene una JVM instalada para deserializar binarios; necesita abrir el archivo en Microsoft Excel o LibreOffice Calc para auditar numeros y cruzar impuestos.
   * **Estado interno:** Uso `.ser` con filtro. Permite restaurar el inventario completo y el estado del software al encender la maquina sin escribir algoritmos manuales de parsing.
2. **¿Que pasaria si se quita `!*` del filtro de deserializacion?**
   Si se omite `!*`, las clases no listadas no seran rechazadas por defecto, dejando la puerta abierta para que un atacante que altere el archivo binario inyecte clases maliciosas presentes en el JDK o dependencias (vulnerabilidad de deserializacion insegura / ejecucion remota de codigo). El sufijo `!*` es el pestillo que sella la lista blanca.

---

## Sesion 06: Completa (Reto 1 de 2: Turno)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `Serializable` (`implements Serializable` habilita la serializacion).
* **Espacio 2:** `Path` (`Path f = Path.of("turno.ser");` definicion de la ruta).
* **Espacio 3:** `try` (`try (var out = ...)` apertura del bloque seguro con recursos).
* **Espacio 4:** `ObjectOutputStream` (Envoltura para emitir grafos de objetos).
* **Espacio 5:** `writeObject` (`out.writeObject(...)` serializacion a disco).
* **Espacio 6:** `readObject` (`in.readObject()` recuperacion tipada desde bytes).
* **Espacio 7:** `size` (`Files.size(f)` tamano real del archivo en disco).

### 2. Respuestas a las Preguntas de Control
1. **¿Que hace el `try` con el flujo al terminar el bloque?**
   Garantiza la invocacion automatica del metodo `close()`, liberando los descriptores de archivo del sistema operativo y vaciando los buffers de escritura (flush). Sin el, si ocurre un error o el programa finaliza abruptamente, el archivo podria quedar bloqueado o con bytes truncados.
2. **Si cambias el filtro a `"!*"`, ¿que excepcion esperas al leer?**
   Se espera `java.io.InvalidClassException: filter status: REJECTED`, ya que el comodin excluyente `!*` niega la admision a cualquier clase sin excepciones.

---

## Sesion 07: Crea (Reto 2 de 2: La Agenda que no Olvida)

### 1. Diseno e Implementacion de la Agenda Persistente
* **Record `Reserva` Serializable:**
  * Componentes: `int id`, `String cliente`, `int personas`, `String hora`.
  * Implementa `java.io.Serializable`.
  * Constructor compacto: Valida invariantes (`personas >= 1 && personas <= 8`).
* **Clase `ArchivoReservas`:**
  * `guardar(List<Reserva>)`: Escribe la lista en `reservas.ser` mediante `ObjectOutputStream` y `try-with-resources`.
  * `cargar()`: Lee el archivo binario aplicando `setObjectInputFilter` con la lista blanca estricta `"Reserva;java.util.ArrayList;java.lang.Object;!*"`. Si el archivo no existe, arranca resilientemente con lista vacia.
  * `exportarCSV(List<Reserva>, Path)`: Genera el archivo `reservas.csv` con encabezado institucional `id;cliente;personas;hora`.

**Salida comprobada en consola:**
```text
Reservas al iniciar la jornada: 0
Persistencia binaria completada: 226 bytes
CSV exportado exitosamente:
id;cliente;personas;hora
1;Valeria;4;18:00
2;Martin;2;19:30
3;Ana;6;20:15

Reservas recuperadas al reiniciar: 3
¿Integridad total garantizada? true
```

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Actua como auditor de ciberseguridad en Java. Explica como configurar un ObjectInputFilter en Java 21 para una lista de records Reserva asegurando rechazo categorico de cualquier otra clase".
* **Respuesta evaluada:** Suministro el patron `"Reserva;java.util.ArrayList;java.lang.Object;!*"`.
* **Verificacion tecnica:** Confirme que `ObjectInputFilter.Config.createFilter` opera de forma nativa en el JDK sin librerias externas, protegiendo la agenda contra ataques de inyeccion binaria.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 11)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que interfaz debe implementar un record para guardarse con `ObjectOutputStream`?
* **Opcion Correcta:** **b) `Serializable`**
* **Justificacion:** Es la interfaz marcadora obligatoria exigida por el mecanismo de serializacion de Java.

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** Aparece `InvalidClassException: filter status: REJECTED`. ¿Que paso?
* **Opcion Correcta:** **b) El archivo trae una clase que no esta en la lista blanca**
* **Justificacion:** El filtro de deserializacion detecto un tipo no autorizado en la cabecera del stream y aborto la operacion preventivamente.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Al deserializar un record, Java usa su constructor canonico, asi que la validacion vuelve a correr.
* **Respuesta:** **Verdadero (V)**
* **Justificacion:** A diferencia de las clases tradicionales, la especificacion de records en Java garantiza que la deserializacion siempre invoca el constructor canonico, revalidando todas las condiciones del constructor compacto.

### 4. Relacion de Conceptos (DOK 2)
* **`writeObject`** -> Objetos a bytes.
* **`readObject`** -> Bytes a objetos.
* **`try (var ...)`** -> Cierra el flujo solo.
* **`!*` en el filtro** -> Rechaza todo lo demas.

### 5. Ordenamiento Logico (DOK 2)
Pasos metologicos de `cargar()`:
1. Revisar si el archivo existe.
2. Abrir el `ObjectInputStream` en un try.
3. Poner el filtro de clases.
4. Leer el objeto.
5. Devolver la lista.

### 6. Respuesta Abierta: Invariantes al Deserializar Records (DOK 3)
* **Pregunta:** Explica por que el archivo con cantidad -3 se rechazo al leer, aunque nadie llamo a `new Insumo` en ese programa.
* **Respuesta:** Porque la arquitectura de los `records` en Java reimplemento la deserializacion de raiz. En una clase comun, Java recrea el objeto asignando campos directamente en memoria sin ejecutar ningun constructor, permitiendo colar valores corruptos. En un record, el flujo de deserializacion extrae los componentes del flujo y los pasa como argumentos al constructor canonico. Al ejecutarse este, se dispara de nuevo el constructor compacto que evalua `cantidad < 0`, abortando con excepcion antes de entregar el objeto en memoria.

### 7. Respuesta Abierta: Evaluacion de Formato segun Cliente (DOK 3)
* **Pregunta:** Valeria quiere mandar la bodega a su proveedor, que usa otro lenguaje. ¿.ser o .csv? Justifica.
* **Respuesta:** Debe mandar `.csv`. El formato `.ser` es una codificacion binaria propietaria y exclusiva de la plataforma Java. Un proveedor que trabaje con Python, C#, Node.js o sistemas contables ERP no podra interpretar el flujo `.ser`. El formato `.csv` es un estandar textual abierto y universal que cualquier plataforma del mundo puede importar con total transparencia.

### 8. Respuesta Abierta: Creacion de Filtro y Lectura Segura (DOK 3)
* **Pregunta:** Escribe el filtro y la lectura para un archivo que solo debe traer un record `Turno`.
* **Solucion:**
```java
try (var in = new ObjectInputStream(Files.newInputStream(Path.of("turno.ser")))) {
    in.setObjectInputFilter(ObjectInputFilter.Config.createFilter("Turno;java.lang.Object;!*"));
    Turno t = (Turno) in.readObject();
    IO.println("Turno leido: " + t);
}
```

---

## Cierre del Nivel 11: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo la persistencia en disco mediante serializacion binaria segura, la aplicacion ineludible de filtros de lista blanca y la exportacion estandarizada en CSV.

### 2. Explicacion Sintetica en 60 Segundos
Cafe Sintaxis recuerda su bodega y agenda entre dias operativos gracias a la persistencia en disco con `ObjectOutputStream`. Al reiniciar, el sistema lee el archivo aplicando una lista blanca con `!*` que rechaza cualquier clase extrana, y confia en los `records`, cuyos constructores vuelven a validar cada registro, impidiendo que datos alterados o negativos puedan ingresar a la operacion.

### 3. Mapa Conceptual Completado
```text
[Serializacion]
       |
       +--> Guardar estado ------------> [writeObject en ObjectOutputStream con try-with-resources]
       +--> Leer estado ---------------> [readObject en ObjectInputStream]
       +--> Proteger la lectura -------> [ObjectInputFilter con lista blanca y terminacion !*]
       +--> Revalidar integridad ------> [Constructor canonico de records]
       +--> Compartir con terceros ----> [Texto plano universal CSV mediante Files.write]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Integro persistencia binaria protegida contra vulnerabilidades de deserializacion con exportacion interoperable en texto plano, garantizando la supervivencia de los datos del negocio y su compatibilidad con sistemas administrativos externos.
