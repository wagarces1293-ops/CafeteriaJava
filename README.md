# Cafe Sintaxis: Solucion del Taller de Programacion 1

Este repositorio contiene la resolucion completa, rigurosa y verificada de la guia de laboratorio/taller de Programacion 1 titulada **"Java 25 en la cafeteria"** (`Java completo _p1_Cafeteria.pdf`).


---

## 1. Estructura General del Proyecto

El proyecto se encuentra organizado en carpetas modulares que reflejan con total exactitud la division pedagogica de la guia:

```text
CafeteriaJava/
├── ruta_preparatoria/
│   └── preparacion_y_fundamentos.md
├── n01_clases/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_barista/
├── n02_encapsulamiento/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_bote_propinas/
├── n03_constructores/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_combos/
├── n04_herencia/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_nomina/
├── n05_abstractas_interfaces/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_entregas/
├── n06_enum/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_plano_salon/
├── n07_solid/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_reporte_cierre/
├── n08_genericos/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_historial/
├── n09_crud/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_agenda_reservas/
├── n10_records/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_menu_del_dia/
├── n11_serializacion/
│   ├── teoria_y_evaluacion.md
│   ├── s03_ejemplo_guiado/
│   ├── s04_practica_guiada/
│   ├── s06_reto1_autocompleta/
│   └── s07_reto2_agenda_persistente/
└── reto_final_cafe_sintaxis/
    ├── sistema_completo.md
    ├── modelo/
    ├── pedidos/
    ├── caja/
    ├── datos/
    ├── memoria/
    └── Main.java
```

---

## 2. Indice de Documentos de Teoria y Evaluacion

Cada carpeta de nivel contiene un archivo Markdown exhaustivo que resuelve todas las preguntas de la guia (Sesiones S01 a S05, Reto S06 con justificaciones y preguntas de control, Reto S07 con analisis de diseno y bitacora de IA, Evaluacion formal S08 con sus 8 preguntas argumentadas, y Cierre con semaforo, explicacion en 60 segundos, mapa conceptual y nivel SOLO):

* [preparacion_y_fundamentos.md](file:///home/willmar/IdeaProjects/CafeteriaJava/ruta_preparatoria/preparacion_y_fundamentos.md): Configuracion inicial del entorno, diagnostico del JDK 21, roles del equipo y reglas metodologicas PRIMM.
* [teoria_y_evaluacion.md (Nivel 01: Clases)](file:///home/willmar/IdeaProjects/CafeteriaJava/n01_clases/teoria_y_evaluacion.md): Clases, objetos, atributos, metodos, analisis de retornos y diseno de la clase Barista.
* [teoria_y_evaluacion.md (Nivel 02: Encapsulamiento)](file:///home/willmar/IdeaProjects/CafeteriaJava/n02_encapsulamiento/teoria_y_evaluacion.md): Visibilidad `private`, getters/setters, invariantes financieros y gestion del Bote de Propinas.
* [teoria_y_evaluacion.md (Nivel 03: Constructores)](file:///home/willmar/IdeaProjects/CafeteriaJava/n03_constructores/teoria_y_evaluacion.md): Inicializacion inmutable con `final`, delegacion `this(...)`, sobrecarga y analisis del prologo.
* [teoria_y_evaluacion.md (Nivel 04: Herencia y Composicion)](file:///home/willmar/IdeaProjects/CafeteriaJava/n04_herencia/teoria_y_evaluacion.md): Especializacion con `extends`, `super`, `@Override`, polimorfismo y composicion en pedidos y nomina.
* [teoria_y_evaluacion.md (Nivel 05: Abstractas e Interfaces)](file:///home/willmar/IdeaProjects/CafeteriaJava/n05_abstractas_interfaces/teoria_y_evaluacion.md): Plantillas abstractas vs. contratos puros de interfaz, metodos `default`, desacoplamiento de medios de pago y tipos de entrega.
* [teoria_y_evaluacion.md (Nivel 06: Enum)](file:///home/willmar/IdeaProjects/CafeteriaJava/n06_enum/teoria_y_evaluacion.md): Constantes con atributos y constructores, maquinas de estado con switch exhaustivo y optimizacion con `EnumMap`.
* [teoria_y_evaluacion.md (Nivel 07: Principios SOLID)](file:///home/willmar/IdeaProjects/CafeteriaJava/n07_solid/teoria_y_evaluacion.md): Refactorizacion de la caja registradora aplicando SRP, OCP, LSP, ISP y DIP, con inyeccion de dependencias y dobles de prueba.
* [teoria_y_evaluacion.md (Nivel 08: Genericos)](file:///home/willmar/IdeaProjects/CafeteriaJava/n08_genericos/teoria_y_evaluacion.md): Clases y metodos genericos `<T>`, limites acotados `<T extends Identificable>`, comodines y erradicacion de casts.
* [teoria_y_evaluacion.md (Nivel 09: CRUD con ArrayList)](file:///home/willmar/IdeaProjects/CafeteriaJava/n09_crud/teoria_y_evaluacion.md): Operaciones Create, Read, Update y Delete sobre colecciones indexadas, llaves autoincrementales, copias defensivas y envoltura con `Optional<T>`.
* [teoria_y_evaluacion.md (Nivel 10: Records)](file:///home/willmar/IdeaProjects/CafeteriaJava/n10_records/teoria_y_evaluacion.md): Portadores inmutables de datos, constructores compactos, igualdad estructural por valor, inmutabilidad profunda y deconstruccion con patrones.
* [teoria_y_evaluacion.md (Nivel 11: Serializacion y Archivos)](file:///home/willmar/IdeaProjects/CafeteriaJava/n11_serializacion/teoria_y_evaluacion.md): Persistencia en archivos binarios `.ser`, `try-with-resources`, filtros de lista blanca `ObjectInputFilter`, reconstruccion canónica y exportacion en formato CSV.
* [sistema_completo.md (Reto Final: Cafe Sintaxis 1.0)](file:///home/willmar/IdeaProjects/CafeteriaJava/reto_final_cafe_sintaxis/sistema_completo.md): Sustentacion integral de la arquitectura Capstone, diagrama UML, justificacion SOLID, bitacora de IA y evaluacion en nivel Abstracto Ampliado de la escala SOLO.

---

## 3. Guia de Compilacion y Ejecucion

Cada carpeta de ejercicio practico es completamente autocontenida y dispone de su propia clase ejecutable `Main.java` y su adaptador de entrada/salida `IO.java`. Para compilar y ejecutar cualquier ejercicio directamente desde la terminal de comandos:

### Ejemplo 1: Ejecutar el Reto 2 del Nivel 1 (Barista)
```bash
cd n01_clases/s07_reto2_barista
javac *.java
java Main
```

### Ejemplo 2: Ejecutar el Reto 2 del Nivel 7 (Reporte del Cierre SOLID)
```bash
cd n07_solid/s07_reto2_reporte_cierre
javac *.java
java Main
```

### Ejemplo 3: Ejecutar el Reto 2 del Nivel 11 (Agenda Persistente)
```bash
cd n11_serializacion/s07_reto2_agenda_persistente
javac *.java
java Main
```

### Ejemplo 4: Ejecutar el Sistema Integrado Cafe Sintaxis 1.0 (Reto Final)
```bash
cd reto_final_cafe_sintaxis
javac modelo/*.java pedidos/*.java caja/*.java datos/*.java memoria/*.java Main.java IO.java
java Main
```
# CafeteriaJava
