# Objetivos del proyecto y alcance de la solución

**Proyecto:** Sistema de gestión de matrícula y control de asistencia — Academia Preuniversitaria San Fernando
**Curso:** Técnicas de Programación Orientada a Objetos (SIST1202A) — UPN Cajamarca, 2026-2
**Semana del sílabo:** 7

---

## 1. Problema que origina el proyecto

La Academia Preuniversitaria San Fernando ofrece ciclos regulares de varios meses y cursos intensivos de pocas semanas, en modalidad presencial y virtual, con campañas de descuento que cambian cada periodo y pago fraccionado en cuotas. Toda esa operación se lleva en cuadernos de admisión y hojas de cálculo que cada persona mantiene por su cuenta. De ese modo de trabajo se desprenden cuatro consecuencias concretas:

1. **Se matricula a más estudiantes de los que caben en el aula.** Como el número de vacantes ocupadas solo está en la cabeza de quien atendió, dos asesores pueden comprometer el mismo cupo y el primer día de clases hay gente de pie.
2. **Cada asesor cotiza un precio distinto.** El descuento de campaña, el descuento por modalidad virtual o por curso corto y el recargo por fraccionamiento se calculan a mano, de modo que dos apoderados reciben dos precios diferentes por el mismo ciclo el mismo día.
3. **El estudiante que vuelve se registra de nuevo.** El padrón acumula a la misma persona varias veces con grafías distintas de su nombre, y se pierde el historial de los ciclos que ya llevó.
4. **La inasistencia se detecta tarde.** Las listas de asistencia se archivan sin consolidarse. Cuando se advierte que un estudiante dejó de venir, ya acumuló semanas de ausencia y la deserción es un hecho.

El problema central no es la falta de registros: es que los registros están dispersos, no se consolidan y no imponen una sola regla de precio ni un solo control de aforo.

---

## 2. Objetivo general

Desarrollar un sistema de escritorio en Java, bajo el paradigma orientado a objetos, que centralice la oferta académica, el padrón de estudiantes y docentes, las matrículas y el control de asistencia de la Academia Preuniversitaria San Fernando, aplicando una sola regla de precio para todo el personal de admisión, impidiendo que se matricule a más estudiantes de los que caben en el aula y conservando la información en archivos de texto entre una ejecución y otra.

---

## 3. Objetivos específicos

Cada objetivo específico es verificable: se cumple o no se cumple, y su cumplimiento se comprueba ejecutando el programa.

| Código | Objetivo específico | Verificación | Estado |
|---|---|---|---|
| OE-01 | Modelar el dominio con clases abstractas, de modo que los datos y las validaciones comunes se escriban una sola vez. | `Persona` y `Ciclo` son abstractas; `Estudiante`, `Docente`, `CicloRegular` y `CursoIntensivo` las extienden y reutilizan sus validaciones mediante `super`. | Cumplido |
| OE-02 | Aplicar polimorfismo para que cada objeto responda según su tipo real, sin que el programa pregunte de qué clase es. | `verDatos()`, `calcularCategoria()`, `calcularDescuento()`, `getTipo()` y `convertirALinea()` son abstractos en la clase base y se sobrescriben en cada clase hija. | Cumplido |
| OE-03 | Validar la integridad de los datos en el momento de asignarlos, de modo que ningún objeto llegue a la colección incompleto. | Las validaciones residen en los métodos `set`; el bucle `while` del flujo vuelve a pedir el dato mientras el atributo siga sin asignar. | Cumplido |
| OE-04 | Impedir que un ciclo reciba más matriculados que vacantes y que un estudiante se matricule dos veces en el mismo ciclo. | Criterios 25.1 a 25.4 de la matriz de requerimientos. | Cumplido |
| OE-05 | Aplicar una sola regla de precio: campaña de descuento, descuento propio del tipo de ciclo y recargo por fraccionamiento. | Criterios 22.1 a 22.7 y 23.1 a 23.6. | Cumplido |
| OE-06 | Reconocer al estudiante que ya está registrado y conservar su historial de matrículas. | Criterios 16.1 a 16.4 y 17.1 a 17.4. | Cumplido |
| OE-07 | Registrar la asistencia diaria con cuatro estados y señalar a los estudiantes en riesgo de deserción. | Criterios 31.1 a 31.8 y 36.8 a 36.10. | Cumplido |
| OE-08 | Exigir el dato del apoderado cuando el postulante es menor de edad. | Criterio 15.8. | Cumplido |
| OE-09 | Calcular la planilla docente a partir de las horas dictadas y la bonificación por tutoría. | Criterios 9.7 a 9.10 y 36.7. | Cumplido |
| OE-10 | Conservar la información entre ejecuciones, con la entrada y la salida de archivos encapsuladas en una sola clase. | La clase `ArchivoUtil` y los criterios 39.1 a 39.17. | Cumplido |
| OE-11 | Garantizar que ningún error de formato en la entrada termine la ejecución del programa. | `leerEntero()`, `leerDecimal()` y `leerFecha()` capturan `NumberFormatException` y `DateTimeParseException`; `ArchivoUtil` captura `IOException`. | Cumplido |
| OE-12 | Documentar al menos cuarenta requerimientos funcionales con sus criterios de aceptación. | `docs/requerimientos-funcionales.md` documenta 45 requerimientos con 179 criterios de aceptación. | Cumplido |
| OE-13 | Separar el dominio de la colección, de modo que las reglas de alta se escriban una sola vez. | Cuatro controladores (`PersonaController`, `CicloController`, `MatriculaController`, `AsistenciaController`) son las únicas clases que tocan los `ArrayList`; cada uno ofrece tres sobrecargas de su método de listado. | Cumplido |
| OE-14 | Dejar registro auditable de la participación de cada integrante. | Historial de commits y pull requests del repositorio `Club-Delirio/Proyect-tecnicas`. | Cumplido |

---

## 4. Alcance de la solución

### 4.1 Lo que el sistema hace

| Proceso | Operaciones incluidas |
|---|---|
| **Oferta académica** | Registrar ciclos regulares y cursos intensivos con los datos propios de cada tipo. Validar el código, las fechas, el costo, la modalidad, las vacantes según el aforo y la coherencia de los simulacros. Impedir el registro de un código repetido. Clasificar cada ciclo en categoría básica, intermedia o premium. |
| **Plana docente** | Registrar docentes con su código `DOC-000`, su especialidad, su tarifa por hora y su porcentaje de bonificación. Acumular las horas dictadas y las matrículas tutoreadas, y calcular su pago del periodo. |
| **Estudiantes** | Registrar postulantes con sus datos de contacto, colegio, carrera objetivo y apoderado. Validar que la edad esté en el rango del postulante y exigir apoderado cuando es menor de edad. Reconocer al estudiante que vuelve y actualizar su última visita. Guardar en su ficha el historial de los ciclos que llevó. |
| **Matrícula** | Configurar la campaña de descuento vigente. Calcular el precio final con el desglose completo: costo base, descuento de campaña, descuento del tipo de ciclo, subtotal, recargo por fraccionamiento y cuota mensual. Cotizar sin registrar. Registrar la matrícula asociándola al estudiante, al docente tutor y al ciclo. Descontar la vacante, cerrar el ciclo al llenarse e impedir la matrícula duplicada. |
| **Control de asistencia** | Registrar la asistencia de un estudiante a una sesión de un ciclo en el que está matriculado, con cuatro estados y una observación opcional. Impedir el doble registro de la misma sesión y el registro de fechas futuras. |
| **Reportes** | Oferta agrupada por categoría con vacantes y estado. Listado de estudiantes, docentes y ciclos aplicando polimorfismo. Historial de matrículas. Estadísticas por tipo, categoría y forma de pago. Reporte financiero con descuentos, recargos, ingreso total, planilla docente e ingreso neto. Reporte de asistencia con el porcentaje de cada estudiante y la alerta de riesgo de deserción. |
| **Persistencia** | Guardar ciclos, personas, matrículas y asistencias en archivos de texto. Recuperarlos al iniciar, reconstruyendo las vacantes ocupadas, el historial del estudiante y el acumulado del docente. Exportar el historial de matrículas a CSV. Mostrar el contenido de un archivo. |

### 4.2 Lo que el sistema no hace en esta entrega

Las siguientes funciones están documentadas con sus criterios de aceptación, pero no forman parte de lo entregado. Se declaran aquí para que el alcance quede sin ambigüedad.

| Función excluida | Requerimiento | Motivo de la exclusión |
|---|---|---|
| Interfaz gráfica de escritorio | RF-43 | Restricción R-09: el tema no corresponde a este bloque del sílabo |
| Almacenamiento en base de datos | RF-42 | Restricción R-02: no hay servidor disponible |
| Control de acceso por usuario y perfil | RF-44 | Restricción R-04: queda fuera del plazo de esta entrega |
| Comprobante de matrícula en PDF | RF-26 | Restricción R-09 |
| Cronograma de cuotas y control de pagos | RF-27 | Restricción R-04: excede el plazo |
| Anulación de matrículas y traslados | RF-28, RF-29 | Restricción R-04 |
| Horarios de clase y detección de cruces del docente | RF-07, RF-11 | Restricción R-04 |
| Plan de estudios y asignación de aulas físicas | RF-06, RF-08 | Restricción R-04 |
| Registro de simulacros y resultados de admisión | RF-18, RF-20 | Fuera del problema central: el proyecto atiende matrícula y asistencia |
| Notificación al apoderado por mensaje de texto | RF-34 | Requiere un servicio de mensajería externo y su costo asociado |
| Operación simultánea de varios usuarios | — | Consecuencia de la restricción R-08: la aplicación es de un solo puesto |

### 4.3 Límites técnicos de lo entregado

| Aspecto | Límite |
|---|---|
| Entorno de ejecución | JDK 17 o superior; se abre como proyecto de Apache NetBeans |
| Interfaz | Consola, con el flujo de trabajo dividido en nueve secciones |
| Almacenamiento | Archivos de texto delimitados por punto y coma, en la carpeta `datos` del proyecto |
| Recuperación | A solicitud del usuario al iniciar; la carga automática queda como RF-40 |
| Concurrencia | Un solo usuario a la vez; no hay control de acceso simultáneo |
| Volumen | Colecciones en memoria con `ArrayList`, adecuadas para el orden de magnitud de una academia (decenas de ciclos y centenares de estudiantes) |
| Moneda | Soles |
| Idioma | Español, sin tildes en las cadenas de código para evitar problemas de codificación entre equipos |

---

## 5. Entregables

| Entregable | Archivo | Semana |
|---|---|---|
| Restricciones y alternativas de solución | `docs/restricciones-y-alternativas.md` | 7 |
| Objetivos y alcance | `docs/objetivos-y-alcance.md` | 7 |
| Matriz de 45 requerimientos con criterios de aceptación | `docs/requerimientos-funcionales.md` | 8 y 10 |
| Historias de usuario en formato narrativo | `docs/historias-usuario.md` | 8 |
| Matriz de historias de usuario en hoja de cálculo | `docs/Historias_Usuario_Academia.xlsx` | 8 y 10 |
| Diagrama de clases UML | `docs/diagrama_clases.png`, `docs/diagrama_clases.svg`, `docs/clases.dot`, `docs/diagrama_clases.drawio` | 9 |
| Investigación y práctica de manejo de archivos en Java | `docs/manejo-archivos-java.md` | 9 |
| Código fuente del sistema | `src/academia/` (catorce clases) | 10 |
| Informe del proyecto en formato APA | `docs/Informe_Academia_APA7.docx` | 7 a 11 |
| Guía documentada de Git y GitHub | `docs/GUIA_GIT_SEMANAS7-11.md` | 7 a 11 |
| Reparto de tareas por integrante | `docs/reparto-de-tareas.md` | 7 a 11 |
| Informe de responsabilidad social | Elaborado por el equipo aparte de este documento | 11 |

---

## 6. Criterio de éxito del proyecto

El proyecto se considera exitoso si, al terminar la semana 11, se cumplen simultáneamente estas cinco condiciones:

1. El código compila sin errores con `javac -encoding UTF-8` y se ejecuta sin interrumpirse ante entradas inválidas.
2. Las quince operaciones implementadas funcionan y cada una satisface los criterios de aceptación de su requerimiento.
3. La información persiste entre ejecuciones: lo guardado en una sesión se recupera en la siguiente con las vacantes ocupadas, el historial de matrículas y la bonificación acumulada.
4. Los 45 requerimientos están documentados con criterios de aceptación verificables.
5. El historial del repositorio muestra la participación de los cuatro integrantes.

Las cinco condiciones son objetivas y se verifican sin depender del juicio de quien revisa.
