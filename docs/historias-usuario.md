# Historias de usuario

Paquete `semana5` — Sistema de gestión de matrícula y control de asistencia.

Cada historia sigue el formato «Como [rol], quiero [funcionalidad], para [beneficio]», con sus criterios de aceptación expresados en contexto, evento y resultado esperado.

La versión en hoja de cálculo, con la trazabilidad completa hacia las clases Java, se encuentra en `docs/Historias_Usuario_Semana5_Completadas.xlsx`.

---

## HU-01 — Registro de estudiante

**Como** secretaria de la academia
**quiero** registrar a un estudiante con su documento de identidad y datos personales
**para** incorporarlo al padrón de matrícula sin depender de los cuadernos.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Documento con longitud válida | El tipo es DNI y el número tiene 8 dígitos | Se invoca `setNro_documento()` | El número se asigna y la persona se registra | `Persona` |
| 2 | Documento con longitud incorrecta | El tipo es DNI y el número tiene menos de 8 dígitos | Se invoca `setNro_documento()` | Se muestra el error con la longitud esperada y no se asigna | `Persona` |
| 3 | Tipo no reconocido | El tipo no es DNI ni CE | Se invoca `setTipo_doc()` | Se muestra el error y el atributo queda sin asignar | `Persona` |
| 4 | Documento duplicado | El número ya existe en la lista | `buscarPersona()` devuelve un objeto distinto de null | No se agrega y se informa que ya está registrado | `PersonaController` |

**Implementado:** Sí

---

## HU-02 — Búsqueda de estudiante

**Como** secretaria de la academia
**quiero** buscar a un estudiante por su número de documento
**para** consultar sus datos durante la atención al apoderado.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Búsqueda exitosa | El documento está registrado | Se invoca `buscarPersona()` | Se devuelve el objeto y se muestran sus datos, nombre completo y edad | `PersonaController` |
| 2 | Documento inexistente | El documento no está registrado | El recorrido de la lista termina sin coincidencias | Devuelve null y se informa que no se encontró | `PersonaController` |
| 3 | Posición fuera de rango | Se pide un registro en una posición que no existe | `obtenerPersona()` captura `IndexOutOfBoundsException` | Se informa el tamaño real de la lista y el programa continúa | `PersonaController` |
| 4 | Nombre con apellidos primero | Se consulta el nombre en formato de nómina | Se invoca `NombreCompleto(true)` | Devuelve "Paterno Materno, Nombre" | `Persona` |

**Implementado:** Sí

---

## HU-03 — Listado y filtrado

**Como** coordinador académico
**quiero** listar a los estudiantes y filtrarlos por tipo de documento o por edad
**para** preparar las listas de asistencia de cada aula.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Listado completo | Existen registros | `listarPersonas()` sin parámetros | Se recorre la lista y se muestran todos | `PersonaController` |
| 2 | Listado vacío | No existen registros | `listarPersonas()` con la lista vacía | Se informa que no hay personas registradas | `PersonaController` |
| 3 | Filtro por tipo | Hay registros de DNI y de CE | `listarPersonas(String)` | Se muestran solo los del tipo indicado | `PersonaController` |
| 4 | Filtro por edad | Se indica una edad mínima y una máxima | `listarPersonas(int, int)` | Se muestran solo los del rango, calculando la edad de cada uno | `PersonaController` |

**Implementado:** Sí

---

## HU-04 — Ordenamiento y conteo

**Como** coordinador académico
**quiero** ordenar el listado por apellido y conocer cuántos estudiantes hay de cada tipo de documento
**para** entregar nóminas ordenadas y conocer la composición del padrón.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Ordenamiento alfabético | Existen varios registros | `ordenarPorApellido()` compara e intercambia posiciones con `set()` | La lista queda ordenada por apellido paterno | `PersonaController` |
| 2 | Apellido nulo | Algún registro no tiene apellido paterno | La comparación descarta los valores nulos | El ordenamiento se completa sin `NullPointerException` | `PersonaController` |
| 3 | Conteo por tipo | Hay registros de distintos tipos | `contarPorTipo()` recorre la lista | Devuelve la cantidad de ese tipo | `PersonaController` |
| 4 | Total de registros | Se consulta el tamaño de la colección | `getCantidad()` invoca `size()` | Devuelve el número de elementos | `PersonaController` |

**Implementado:** Sí

---

## HU-05 — Eliminación de registro

**Como** secretaria de la academia
**quiero** eliminar el registro de un estudiante por su documento
**para** mantener el padrón depurado cuando alguien se retira.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Eliminación exitosa | El documento está registrado | `eliminarPersona()` invoca `remove()` | Se elimina y se informa el total restante | `PersonaController` |
| 2 | Documento inexistente | El documento no está registrado | `buscarPersona()` devuelve null | Se informa el error y no se altera la lista | `PersonaController` |
| 3 | Integridad tras la baja | Se elimina un registro existente | Se compara `getCantidad()` antes y después | La lista queda con un elemento menos | `PersonaController` |
| 4 | Búsqueda posterior | Se busca el documento ya eliminado | `buscarPersona()` recorre la lista | Devuelve null | `PersonaController` |

**Implementado:** Sí

---

## HU-06 — Sobrecarga y manejo de errores

**Como** estudiante del curso
**quiero** contar con métodos sobrecargados y con control de errores de formato
**para** demostrar los tres temas exigidos en la práctica de la semana 5.

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Sobrecarga de constructores | Se crea una Persona con distinta cantidad de datos | Java resuelve la firma en compilación | Se construye el objeto con los atributos entregados | `Persona` |
| 2 | Sobrecarga de métodos | Se invoca `VerDatos`, `NombreCompleto` o `CalcularEdad` con y sin parámetros | Java selecciona la versión correspondiente | Devuelve el resultado en el formato pedido | `Persona` |
| 3 | Entrada no numérica | Se escribe texto donde se espera un número | Se lanza `NumberFormatException` | Se captura en el `catch` y el programa continúa | `Semana5` |
| 4 | Fecha mal escrita | La fecha no tiene el formato aaaa-mm-dd | Se lanza `DateTimeParseException` | Se captura, se avisa el formato correcto y el programa continúa | `Semana5` |

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
| HU-01 | RF-01, RF-02, RF-03, RF-04 |
| HU-02 | RF-06, RF-07 |
| HU-03 | RF-08, RF-09, RF-10 |
| HU-04 | RF-11, RF-12 |
| HU-05 | RF-13, RF-14 |
| HU-06 | RF-05, RF-15, RF-16, RF-17, RF-18 |
| HU-07 | RF-20 |
| HU-08 | RF-23 |

La especificación completa de los requerimientos se encuentra en `docs/requerimientos-funcionales.md`.
