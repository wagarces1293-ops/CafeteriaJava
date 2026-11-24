# Bitacora de Preparacion y Fundamentos del Sistema Cafe Sintaxis

Como estudiante de Ingenieria de Software, inicio este proyecto documentando la fase preparatoria y de configuracion inicial del entorno de desarrollo para el sistema de gestion de Cafe Sintaxis, con base en las directrices establecidas en la guia "Java 25 en la cafeteria".

---

## 1. Contexto del Negocio y Equipo de Trabajo

Cafe Sintaxis es una cafeteria de barrio orientada a modernizar sus procesos operativos mediante la transicion de un registro manual en papel hacia una aplicacion estructurada con Programacion Orientada a Objetos en Java. 

### Personajes del Sistema y Necesidades Operativas
* **Valeria (Duena):** Define las reglas del negocio, politicas de inventario, cajas registradoras, esquemas de fidelizacion y margenes de rentabilidad.
* **Martin (Barista y Cajero):** Usuario operativo principal. Requiere agilidad en la atencion en barra, control del stock disponible, orden en las comandas y claridad en el cuadre de propinas y caja.
* **Ana (Desarrolladora de Software):** Propone la arquitectura de clases, diagramas de dominio y patrones de diseno adecuados para garantizar la robustez del sistema.
* **Julian (Desarrollador de Software):** Acompana la implementacion practica de las reglas de negocio, identificando excepciones y casos limites en la logica.

### Roles de Ingenieria Asumidos
En mi rol individual como estudiante de desarrollo, integro de manera secuencial los siguientes perfiles de trabajo:
1. **Navegante:** Consulta rigurosa de las especificaciones tecnicas oficiales de Java (JLS, Java Platform Standard Edition API Specification) y lineamientos de arquitectura limpia.
2. **Programador:** Escritura de codigo fuente limpio, modular, indentado y sujeto exclusivamente a conceptos de Programacion 1.
3. **Verificador:** Diseno de casos de prueba unitarios manuales, validacion de invariantes de negocio, manejo de excepciones y contraste con salidas esperadas en consola.
4. **Cronista:** Registro sistematico de decisiones de diseno, justificaciones tecnicas, errores de compilacion resueltos y bitacoras de soporte con herramientas de desarrollo.

---

## 2. Verificacion y Diagnostico del Entorno de Desarrollo

De acuerdo con las instrucciones de la Seccion "Antes de empezar" (Ruta 3/5), realice la verificacion de las herramientas del sistema desde la terminal de Linux.

### Tabla de Diagnostico de Herramientas
| Componente | Comando Ejecutado | Version Detectada en Sistema | Estado y Compatibilidad |
| :--- | :--- | :--- | :--- |
| Entorno de Ejecucion (JRE) | `java --version` | OpenJDK Runtime Environment 21.0.13.1 | Conforme. Compatible con compilacion y ejecucion estandar. |
| Compilador de Java (JDK) | `javac --version` | OpenJDK 21.0.13.1 | Conforme. Lenguaje nivel Java 21 LTS sin librerias externas. |
| Sintesis de E/S (`IO.java`) | Compilacion local | Implementacion interna basada en `System.out` y `Scanner` | Conforme. Garantiza portabilidad y cumplimiento estricto de signaturas. |
| IDE / Editor | IntelliJ IDEA / Terminal | Entorno Linux x86_64 con bash shell | Conforme. Gestion estructurada de archivos y fuentes. |

### Primer Programa de Verificacion (Main Inicial)
Se implemento y ejecuto el saludo de verificacion:

```java
public class Main {
    public static void main(String[] args) {
        IO.println("Bienvenida a Cafe Sintaxis");
        String nombre = "Sara";
        IO.println("Hola, " + nombre + ". Tu primer cafe va por cuenta del sistema.");
    }
}
```

**Salida obtenida:**
```text
Bienvenida a Cafe Sintaxis
Hola, Sara. Tu primer cafe va por cuenta del sistema.
```

---

## 3. Mapa de Dominio y Progresion Pedagogica

El proyecto se estructura en 11 niveles progresivos, cada uno abordando una pregunta central del negocio de cafeteria:

1. **Nivel 1 (Clases):** Como convertir la ficha de un producto en un objeto con estado (atributos) y comportamiento (metodos).
2. **Nivel 2 (Encapsulamiento):** Como proteger el saldo de la caja registradora mediante visibilidad privada y metodos mutadores que rechazan valores invalidos con excepciones.
3. **Nivel 3 (Constructores):** Como garantizar que los objetos nazcan en un estado coherente y como sobrecargar constructores para evitar duplicidad de logica (`this(...)`).
4. **Nivel 4 (Herencia y Composicion):** Cuando reutilizar codigo mediante jerarquias de clases (`extends`) y cuando estructurar pedidos agregando lineas de detalle ("tiene un").
5. **Nivel 5 (Clases Abstractas e Interfaces):** Como establecer contratos formales de comportamiento para medios de pago y esquemas de entrega sin comprometer la implementacion.
6. **Nivel 6 (Enumeraciones - Enum):** Como restringir valores criticos a conjuntos finitos tipados (tamanos, categorias, tipos de leche) con atributos y logica asociada.
7. **Nivel 7 (Principios SOLID):** Como aislar responsabilidades (SRP) e inyectar dependencias (DIP) en la caja registradora para facilitar mantenimiento y extension sin modificaciones invasivas.
8. **Nivel 8 (Genericos):** Como construir estructuras de almacenamiento reutilizables (`Repositorio<T>`, `Historial<T>`) manteniendo seguridad estricta de tipos en tiempo de compilacion.
9. **Nivel 9 (Operaciones CRUD con ArrayList):** Como gestionar colecciones dinamicas en memoria para crear, leer, actualizar y eliminar registros de inventario y reservas.
10. **Nivel 10 (Records):** Como representar datos inmutables y de solo lectura (recibos, calificaciones, componentes del menu) con igualdad por valor garantizada por Java.
11. **Nivel 11 (Serializacion y Manejo de Archivos):** Como persistir el estado del sistema en archivos binarios (`.ser`) con filtros estrictos de deserializacion y en texto plano (`.csv`) para interoperabilidad contable.
12. **Reto Final (Cafe Sintaxis 1.0):** Integracion de todos los subsistemas en un aplicativo unificado, evaluado bajo la rubrica SOLO.

---

## 4. Marco Metodologico: PRIMM y Uso Responsable de Herramientas Asistivas

Para la resolucion de cada sesion de trabajo, adopto el ciclo pedagogico **PRIMM**:
* **Predecir (P):** Analizar el codigo antes de su ejecucion e inferir su comportamiento o mensajes de error esperados.
* **Revisar/Inspeccionar (R):** Estudiar las clases, tipos de retorno y flujo de control linea por linea.
* **Investigar/Modificar (I):** Alterar parametros o condiciones limites para observar la respuesta del compilador o la maquina virtual.
* **Mapear/Completar (M):** Resolver ejercicios guiados a mano antes de consultar cualquier autocompletado.
* **Crear (M):** Desarrollar la solucion definitiva aplicando exclusivamente los conceptos aprobados para Programacion 1.

### Reglas Eticas y Tecnicas de Desarrollo
1. **Completado manual prioritario:** Todo ejercicio se resuelve primero mediante analisis logico conceptual.
2. **Cero delegacion de diseno:** La eleccion de nombres de metodos, atributos y tipos de datos responde al criterio del estudiante y a los requerimientos de la guia.
3. **Verificacion empirica:** Todo fragmento de codigo es compilado y probado en terminal para contrastar su resultado con la salida real de la guia.
4. **Ausencia absoluta de librerias externas o sintaxis prohibida:** Queda estrictamente vedado el uso de Streams, Lambdas, paquetes ajenos al JDK base o patrones ajenos al nivel universitario inicial de Programacion 1.
