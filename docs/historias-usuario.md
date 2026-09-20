# Historias de usuario

Sistema de gestión de matrícula y control de asistencia — módulo de la semana 5.

Cada historia sigue el formato «Como [rol], quiero [funcionalidad], para [beneficio]», con sus criterios de aceptación expresados en contexto, evento y resultado esperado.

La versión en hoja de cálculo, con la trazabilidad completa hacia las clases Java, se encuentra en `docs/Historias_Usuario_Semana5_Completadas.xlsx`.

---

## HU-01 — Registro de estudiante

**Como** secretaria de la academia
**quiero** registrar a un estudiante con su documento de identidad y datos personales
**para** incorporarlo al padrón de matrícula sin depender de los cuadernos.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Documento con longitud válida | El tipo es DNI y el número tiene 8 dígitos | Se invoca `validarDocumento()` desde el constructor | El registro se acepta y se muestran los datos | `Persona` |
| 2 | Documento con longitud incorrecta | El tipo es DNI y el número tiene menos de 8 dígitos | Se invoca `validarDocumento()` | Se lanza `DocumentoInvalidoException` con la longitud esperada | `Persona` |
| 3 | Tipo no reconocido | El tipo no es DNI, CE, RUC ni PASAPORTE | `longitudEsperada()` devuelve 0 | Se lanza `DocumentoInvalidoException` con los tipos válidos | `Persona` |
| 4 | Documento duplicado | El número ya existe en la colección | `containsKey()` devuelve `true` | No se agrega y se informa que ya está registrado | `PersonaController` |

**Implementado:** Sí

---

## HU-02 — Búsqueda de estudiante

**Como** secretaria de la academia
**quiero** buscar a un estudiante por su número de documento
**para** consultar sus datos durante la atención al apoderado.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Búsqueda exitosa | El documento está registrado | Se invoca `buscarPorDocumento()` | Se devuelven los datos completos | `PersonaController` |
| 2 | Documento inexistente | El documento no está registrado | El `HashMap` devuelve `null` | Se lanza `PersonaNoEncontradaException` y el programa continúa | `PersonaController` |
| 3 | Búsqueda sin dato | No se ingresa ningún número | Se invoca con cadena vacía | Se solicita indicar un número de documento | `PersonaController` |
| 4 | Posición fuera de rango | Se pide un registro que no existe | Se captura `IndexOutOfBoundsException` | Se informa el tamaño real de la lista | `PersonaController` |

**Implementado:** Sí

---

## HU-03 — Listado y filtrado

**Como** coordinador académico
**quiero** listar a los estudiantes y filtrarlos por tipo de documento o por edad
**para** preparar las listas de asistencia de cada aula.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Listado completo | Existen registros | `listarPersonas()` sin parámetros | Se muestran numerados con el total | `PersonaController` |
| 2 | Listado vacío | No existen registros | `listarPersonas()` | Se informa que no hay registros, sin excepción | `PersonaController` |
| 3 | Filtro por tipo | Hay registros de distintos tipos | `listarPersonas(String)` | Se muestran solo los del tipo indicado | `PersonaController` |
| 4 | Filtro por edad | Se indica una edad mínima y una máxima | `listarPersonas(int, int)` | Se muestran solo los del rango | `PersonaController` |

**Implementado:** Sí

---

## HU-04 — Ordenamiento y resumen

**Como** coordinador académico
**quiero** ordenar el listado por apellido y ver un resumen por tipo de documento
**para** entregar nóminas ordenadas y conocer la composición del padrón.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Ordenamiento alfabético | Existen varios registros | `ordenarPorApellido()` con `Comparator` encadenado | La lista se reordena por apellido paterno y luego por nombre | `PersonaController` |
| 2 | Apellido nulo | Algún registro no tiene apellido | El `Comparator` sustituye el nulo por cadena vacía | El ordenamiento se completa sin `NullPointerException` | `PersonaController` |
| 3 | Resumen por tipo | Hay registros de distintos tipos | `contarPorTipoDocumento()` sobre `LinkedHashMap` | Se devuelve el conteo conservando el orden | `PersonaController` |
| 4 | Resumen sin registros | La colección está vacía | Se consulta `estaVacia()` antes de calcular | Se informa que no hay datos que resumir | `Semana5` |

**Implementado:** Sí

---

## HU-05 — Eliminación de registro

**Como** secretaria de la academia
**quiero** eliminar el registro de un estudiante por su documento
**para** mantener el padrón depurado cuando alguien se retira.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Eliminación exitosa | El documento está registrado | `eliminarPorDocumento()` da de baja en lista e índice | Se elimina y se informa el total restante | `PersonaController` |
| 2 | Documento inexistente | El documento no está registrado | `buscarPorDocumento()` falla dentro de la eliminación | Se lanza `PersonaNoEncontradaException` sin alterar la colección | `PersonaController` |
| 3 | Integridad tras la baja | Se elimina un registro existente | Se compara `getCantidad()` antes y después | Lista e índice quedan sincronizados | `PersonaController` |
| 4 | Protección de la colección | Un módulo externo pide la lista | `getLista()` devuelve una copia | Las modificaciones externas no afectan la colección interna | `PersonaController` |

**Implementado:** Sí

---

## HU-06 — Operaciones sobrecargadas y manejo de errores

**Como** estudiante del curso
**quiero** contar con operaciones sobrecargadas y con control de errores aritméticos y de formato
**para** demostrar la sobrecarga de métodos y el manejo de excepciones exigidos en la semana 5.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Sobrecarga por cantidad | Se invoca `calcular()` con dos o tres enteros | Java resuelve la firma en compilación | Devuelve la suma correspondiente | `Calculadora` |
| 2 | Sobrecarga por tipo y varargs | Se invoca con decimales, cadenas o varios enteros | Java selecciona la firma según los tipos | Devuelve suma decimal, concatenación o suma variable | `Calculadora` |
| 3 | División entre cero | El divisor es cero | `dividir()` lanza `ArithmeticException` | Se informa el error y el programa continúa | `Calculadora` |
| 4 | Entrada no numérica | Se ingresa texto donde se espera un número | `convertirAEntero()` lanza `NumberFormatException` | Se captura y el programa continúa | `Calculadora` |

**Implementado:** Sí

---

## HU-07 — Control de asistencia

**Como** coordinador académico
**quiero** registrar la asistencia diaria de cada estudiante por aula
**para** llevar el control sin usar hojas sueltas que se extravían.

**Implementado:** No — corresponde a la semana 9 del sílabo, cuando se incorpore la persistencia en archivos.

---

## HU-08 — Reporte de asistencia

**Como** coordinador académico
**quiero** generar un reporte del porcentaje de asistencia por estudiante y por aula
**para** detectar a tiempo a los estudiantes en riesgo de deserción.

**Implementado:** No — corresponde a las semanas finales del proyecto.

---

## Trazabilidad con los requerimientos funcionales

| Historia | Requerimientos que cubre |
|---|---|
| HU-01 | RF-01, RF-02, RF-03, RF-04, RF-05 |
| HU-02 | RF-06, RF-07 |
| HU-03 | RF-08, RF-09, RF-10 |
| HU-04 | RF-11, RF-12 |
| HU-05 | RF-13, RF-14 |
| HU-06 | RF-15, RF-16, RF-17, RF-18 |
| HU-07 | RF-20 |
| HU-08 | RF-23 |

La especificación completa de los requerimientos se encuentra en `docs/requerimientos-funcionales.md`.
