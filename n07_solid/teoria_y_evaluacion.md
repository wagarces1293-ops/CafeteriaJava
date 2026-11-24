# Nivel 07: Principios SOLID - La Caja Registradora Limpia

## Presentacion del Nivel
* **Pregunta Guia:** ¿Como cambio la caja registradora para aceptar nuevas promociones e impresoras sin romper el codigo que ya funciona?
* **Mi Meta Personal:** Comprender y aplicar los cinco principios de diseno SOLID (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation y Dependency Inversion) en el refactor del sistema de facturacion de Cafe Sintaxis, demostrando inyeccion de dependencias y pruebas con dobles en memoria.

---

## Sesion 01: Descubre (Definiciones y Vocabulario)

### 1. Definiciones Oficiales de la Ingenieria de Software (Robert C. Martin / SOLID)
* **S - Single Responsibility Principle (SRP):** Una clase debe tener una, y solo una, razon para cambiar. Cada modulo debe concentrarse en una unica responsabilidad del negocio.
* **O - Open/Closed Principle (OCP):** Las entidades de software deben estar abiertas para su extension, pero cerradas para su modificacion. Debemos poder anadir nuevos comportamientos sin alterar el codigo existente.
* **L - Liskov Substitution Principle (LSP):** Los objetos de un programa deben ser reemplazables por instancias de sus subtipos sin alterar la correccion del programa ni quebrar contratos preexistentes.
* **I - Interface Segregation Principle (ISP):** Muchas interfaces especificas para cada cliente son mejores que una sola interfaz general. Ninguna clase debe ser forzada a depender de metodos que no utiliza.
* **D - Dependency Inversion Principle (DIP):** Los modulos de alto nivel no deben depender de modulos de bajo nivel; ambos deben depender de abstracciones. Las abstracciones no deben depender de los detalles; los detalles deben depender de las abstracciones.

### 2. Sintesis Personal del Dominio
Entiendo que en una cafeteria real, la caja registradora original era una "clase Dios": calculaba descuentos con cascadas de `if/else`, formateaba tickets para la pantalla, enviaba mensajes SMS conectandose a modems y guardaba registros financieros. Cuando Valeria pedia una nueva promocion ("2x1 en repostería"), habia que editar la clase `Caja`, corriendo el grave riesgo de desconfigurar el cobro de bebidas ordinarias. Aplicando SOLID, la `Caja` solo coordina transacciones: el formateo se delega a `Ticket` (SRP), las promociones son contratos `Descuento` (OCP/LSP) y el envio se inyecta como `Notificador` (ISP/DIP).

### 3. Vocabulario Tecnico Dominado
* **Refactorizacion:** Transformacion controlada de la estructura interna del codigo sin alterar su comportamiento observable externo.
* **Inyeccion de Dependencias:** Patron arquitectonico en el cual un objeto recibe sus colaboradores necesarios (habitualmente a traves del constructor) en lugar de instanciarlos internamente con `new`.
* **Doble de Prueba (Test Double / Mock):** Implementacion ligera en memoria de una interfaz que registra eventos para validacion sin interactuar con recursos lentos o costosos (pantallas, SMS, archivos).
* **Contrato de Sustitucion:** Clausula logica donde las subclases o implementaciones respetan el rango, invariantes y excepciones del tipo base.

---

## Sesion 02: Comprende (Ampliacion del Tema y los Cinco Principios en la Caja)

### 1. Mapeo de SOLID en Cafe Sintaxis
| Principio | Manifestacion en el Codigo Antiguo (Problema) | Solucion Refactorizada con SOLID |
| :--- | :--- | :--- |
| **S** | `Caja` sumaba, aplicaba porcentajes, imprimia tickets y enviaba SMS. | `Ticket` solo formatea; `Caja` solo totaliza y cobra. |
| **O** | Para anadir un descuento se agregaba un `else if` en `Caja`. | Interfaz `Descuento`. Nuevas ofertas son clases nuevas (`Porcentaje`, `Fidelidad`). |
| **L** | Un descuento defectuoso devolvia valores negativos ($-500$). | `Fidelidad` protege el contrato: `Math.max(0, total - 2000)` nunca arroja negativos. |
| **I** | Interfaces sobrecargadas obligaban a implementar metodos vacios. | `Notificador` declara unicamente `void enviar(...)`. |
| **D** | `Caja` creaba directamente `new SmsSimulado()`. | `Caja` recibe `Notificador` por constructor (`this.notificador = notificador`). |

### 2. Ensamblaje en `Main`
Al desacoplar las dependencias:
```java
void main() {
    Caja caja = new Caja(new SmsSimulado());
    caja.cobrar(pedido, new Porcentaje(10), "300 555 0101");
}
```
`Main` asume el rol de ensamblador o inyector de dependencias: es el unico archivo que conoce las implementaciones concretas, mientras que los modulos del nucleo (`Caja`) operan exclusivamente contra interfaces limpias.

---

## Sesion 03: Observa y Analiza (Codigo Guiado y Metodologia PRIMM)

### 1. Predicciones Previas a la Ejecucion (PRIMM)
1. **Total impreso con fidelidad para pedido de `$1500`:**
   En el codigo anterior sin SOLID, `1500 - 2000` devolvia `$-500` (la cafeteria le quedaba debiendo plata al cliente). En el codigo refactorizado con LSP, `Math.max(0, 1500 - 2000)` devuelve `$0`. El invariante financiero "ningun ticket es negativo" se preserva.
2. **Llegada de una promocion "2x1 en postres":**
   Se crea unicamente el archivo `DosPorUno.java implements Descuento`. No se modifica ni una sola coma de `Caja.java`, `Ticket.java` ni `Notificador.java`, cumpliendo a cabalidad el principio Open/Closed.

### 2. Registro de Experimentos de 2 Minutos
* **Experimento A (Inyectar `NotificadorMemoria` en lugar de `SmsSimulado`):**
  La caja ejecuta exactamente las mismas operaciones de cobro y calculo financiero, pero ningun texto ensucia la terminal; todos los mensajes quedan capturados en una lista en memoria. Demuestra el poder de DIP para pruebas automatizadas.
* **Experimento B (Crear `DescuentoFijo(5000)` sin validar y pasarle un total de `$3000`):**
  Si devuelve `-2000`, viola LSP y rompe el contrato acordado en la interfaz `Descuento`. La implementacion debe garantizar siempre `Math.max(0, total - valor)`.

---

## Sesion 04: Practica Guiada (Dobles de Prueba en Memoria)

### 1. Analisis de `NotificadorMemoria.java`
Para verificar la caja sin depender de la consola o pasarelas de mensajeria:
* Se implementa `NotificadorMemoria implements Notificador` alojando una lista privada `List<String> mensajes`.
* `Cobro` recibe el notificador simulado por constructor.
* La verificacion inspecciona la lista mediante `mock.enviados()`.
* Evaluacion: Se garantiza que el software pueda someterse a pruebas unitarias rigurosas y deterministas en entornos de integracion continua sin efectos colaterales.

---

## Sesion 05: Conecta (Impacto Tecnico y de Negocio - DOK 3)

### Respuestas a las Preguntas de Evaluacion y Analisis
1. **¿Por que una clase de 20 lineas no siempre debe aplicar los 5 principios SOLID?**
   Porque la ingenieria de software exige evaluar la relacion costo-beneficio. Si un modulo de 20 lineas es completamente estable, no cambia nunca y realiza una operacion matematica trivial, introducir 4 interfaces, inyeccion por constructor y multiples archivos agregaria complejidad accidental y sobreingenieria injustificada. SOLID se aplica donde existe volatilidad, colaboracion entre modulos y probabilidad real de cambio.
2. **¿Cual es el riesgo de hacer `new SmsSimulado()` dentro del constructor de `Caja`?**
   Se arruina el principio de Inversion de Dependencias. Aunque la variable se declare como `Notificador`, si el constructor instancia la clase concreta, la caja queda atada y acoplada permanentemente al envio de SMS. Si luego se desea notificar por WhatsApp o realizar pruebas unitarias con dobles en memoria, seria imposible sin reescribir la clase.

---

## Sesion 06: Completa (Reto 1 de 2: Cocina e Impresora)

### 1. Solucion de los 7 Espacios con el Banco de Palabras
* **Espacio 1:** `interface` (`public interface Impresora` contrato de impresion).
* **Espacio 2:** `implements` (`implements Impresora` firma del contrato en consola).
* **Espacio 3:** `final` (`private final Impresora impresora;` referencia inmutable).
* **Espacio 4:** `Impresora` (`public Cocina(Impresora impresora)` parametro abstracto).
* **Espacio 5:** `impresora` (`this.impresora = impresora;` asignacion de dependencia).
* **Espacio 6:** `imprimir` (`impresora.imprimir(...)` despacho polimorfico).
* **Espacio 7:** `ImpresoraConsola` (`new Cocina(new ImpresoraConsola());` inyeccion del detalle).

### 2. Respuestas a las Preguntas de Control
1. **¿Que principio SOLID aplica `Cocina` al recibir la impresora por constructor?**
   Aplica el Principio de Inversion de Dependencias (DIP). Se gana que para probar la cocina no se requiere una impresora termica real de papel; se le puede inyectar una `ImpresoraMemoria` que verifique si la comanda de la mesa 4 llego intacta en una prueba unitaria sin gastar papel ni generar demoras.
2. **Si en el espacio 3 quitas `final`, ¿que proteccion pierdes?**
   Se pierde la garantia de inmutabilidad estructural: la variable `impresora` podria ser reasignada accidentalmente a `null` o sustituida a mitad de un turno, provocando fallos por puntero nulo en operaciones concurrentes de cocina.

---

## Sesion 07: Crea (Reto 2 de 2: El Reporte del Cierre)

### 1. Diseno e Implementacion del Subsistema de Reportes
* **Responsabilidad Unica (SRP):**
  * `ReporteCierre`: Agrega ventas, acumula facturacion neta y consolida propinas. No sabe nada de formatos de archivo ni consolas.
  * `Exportador`: Interfaz que declara el contrato `void exportar(String fecha, int ventas, int total, int propinas);`.
* **Abierto / Cerrado (OCP):**
  * `ExportadorTexto`: Formatea un resumen estetico para revision administrativa de Valeria.
  * `ExportadorCSV`: Genera lineas estructuradas separadas por punto y coma para la contabilidad oficial.
  * Si manana se requiere exportar en JSON o XML, solo se anade una clase mas; `ReporteCierre` jamas se altera.
* **Inversion de Dependencias (DIP):** El metodo `reporte.emitir(exportador)` recibe la interfaz abstracta.

**Salida comprobada en consola:**
```text
--- Reporte para Valeria (Texto) ---
=== CIERRE DEL DIA: 2026-11-23 ===
Numero de ventas: 3
Total facturado: $38500
Propinas consolidadas: $4500
====================================

--- Reporte para Contadora (CSV) ---
fecha;ventas;total;propinas
2026-11-23;3;38500;4500
```

### 2. Bitacora del Copiloto de Inteligencia Artificial
* **Prompt formulado:** "Actua como revisor de arquitectura Java. Revisa como disenar la exportacion de un ReporteCierre respetando SRP y OCP sin utilizar streams ni lambdas".
* **Respuesta evaluada:** Propuso separar el acumulador financiero del contrato exportador, inyectando la estrategia de formateo en el metodo emitir.
* **Verificacion tecnica:** Confirme que la clase acumuladora preserva la integridad de sus sumatorias y que cada formato responde a su propia implementacion de interfaz.

---

## Sesion 08: Comprueba (Evaluacion del Nivel 07)

### 1. Opcion Multiple (DOK 1)
* **Pregunta:** ¿Que principio dice "abierto a la extension, cerrado a la modificacion"?
* **Opcion Correcta:** **b) O (Open/Closed Principle)**
* **Justificacion:** Formulacion clasica de Bertrand Meyer adoptada por Robert C. Martin.

### 2. Opcion Multiple (DOK 2)
* **Pregunta:** Fidelidad devolvia $-500. ¿Que principio violaba?
* **Opcion Correcta:** **b) Sustitucion de Liskov: rompia el contrato "nunca negativo"**
* **Justificacion:** El contrato de `Descuento` estipula que un total nunca puede quedar negativo. Una subclase o implementacion que devuelva saldos en contra del negocio viola las precondiciones y postcondiciones del contrato base.

### 3. Verdadero o Falso con Justificacion (DOK 1)
* **Enunciado:** Inyectar una dependencia por el constructor ayuda a probar una clase sin usar recursos reales.
* **Respuesta:** **Verdadero (V)**
* **Justificacion:** Permite suministrar dobles de prueba (como `NotificadorMemoria`) en los tests, verificando la logica sin depender de servidores de correo, modems SMS o hardware fisico.

### 4. Relacion de Conceptos (DOK 2)
* **S** -> ¿Cuantas razones tiene para cambiar?
* **L** -> ¿La hija cumple lo que promete el padre?
* **I** -> ¿Obligo a escribir metodos que no se usan?
* **D** -> ¿Dependo de interfaces o de detalles?

### 5. Ordenamiento Logico (DOK 2)
Pasos metologicos de una refactorizacion segura:
1. Guardar la salida actual.
2. Separar una responsabilidad.
3. Ejecutar y comparar la salida.
4. Recien entonces mejorar comportamiento.
5. Repetir con la siguiente.

### 6. Respuesta Abierta: Rol de Main (DOK 3)
* **Pregunta:** Explica por que `Main` es el unico lugar que conoce `SmsSimulado` y que ventaja tiene eso.
* **Respuesta:** Porque `Main` actua como el modulo raiz de configuracion y composicion del sistema. Todas las clases de logica del negocio (`Caja`) interactuan unicamente con la abstraccion `Notificador`. La ventaja radical es que si manana se cambia el envio de SMS por notificaciones push o por un doble de prueba, solo se altera la linea de instanciacion en `Main` (`new Caja(new PushNotificador())`), permaneciendo todas las demas clases de la aplicacion completamente intactas y ajenas al cambio.

### 7. Respuesta Abierta: Criterio de Aplicabilidad (DOK 3)
* **Pregunta:** Para una calculadora de propinas de 20 lineas, ¿aplicarias los cinco principios? Justifica tu decision.
* **Respuesta:** No. Aplicar los cinco principios en una funcion utilitaria de 20 lineas implicaria crear al menos 3 interfaces, 4 clases accesorias y un inyector, transformando un algoritmo sencillo en una estructura innecesariamente engorrosa (sobreingenieria). SOLID es un medio para mitigar la complejidad y el acoplamiento en sistemas extensibles, no una imposicion dogmatica que deba aplicarse a cada fragmento elemental de codigo.

### 8. Respuesta Abierta: Creacion de Descuento (DOK 3)
* **Pregunta:** Escribe un `Descuento` nuevo, `HoraFeliz`, que reste 30% y nunca devuelva negativo.
* **Solucion:**
```java
public class HoraFeliz implements Descuento {
    @Override
    public int aplicar(int total) {
        if (total <= 0) {
            return 0;
        }
        int totalConDescuento = total * 70 / 100;
        return Math.max(0, totalConDescuento);
    }
}
```

---

## Cierre del Nivel 07: Metacognicion y Evaluacion SOLO

### 1. Semaforo de Seguridad Conceptual
* **Estado:** **Verde.**
* **Justificacion:** Comprendo el impacto del desacoplamiento, la segregacion de interfaces y la inyeccion de dependencias para producir software mantenible y facilmente verificable.

### 2. Explicacion Sintetica en 60 Segundos
Para agregar la promocion "2x1" en Cafe Sintaxis sin tocar una sola linea de la clase `Caja`, basta con crear una nueva clase `DosPorUno` que implemente la interfaz `Descuento`. Como la caja delega el computo en la abstraccion mediante polimorfismo, recibe la nueva promocion como argumento y ejecuta su calculo limpiamente, manteniendose cerrada a modificaciones pero abierta a la extension.

### 3. Mapa Conceptual Completado
```text
[Principios SOLID]
         |
         +--> Se logra con Separar -----> [S: Single Responsibility e I: Interface Segregation]
         +--> Se logra con Extender ----> [O: Open/Closed mediante contratos de interfaz]
         +--> Exige Sustituir -----------> [L: Liskov Substitution respetando invariantes]
         +--> Se logra con Desacoplar --> [D: Dependency Inversion inyectando colaboradores]
```

### 4. Nivel en la Escala SOLO
* **Nivel alcanzado:** **Relacional.**
* **Justificacion:** Reorganizo sistemas monoliticos en componentes de responsabilidad unica que interactuan a traves de contratos abstractos, preservando el comportamiento funcional comprobado y facilitando su prueba unitaria mediante dobles en memoria.
