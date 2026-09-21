# Diccionario de clases

Paquete `semana5` — Sistema de gestión de matrícula y control de asistencia.

Este documento corresponde a la sección 11 del Informe de Proyecto Final.

---

## Clase `Persona`

Representa a una persona registrada en el sistema. Concentra la validación de los datos de identidad dentro de sus propios setters, de modo que ningún objeto quede con un documento inválido.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|---|---|---|---|
| `tipo_doc` | String | `private` | Tipo de documento de identidad. Solo acepta DNI o CE. |
| `nro_documento` | String | `private` | Número de documento. 8 dígitos para DNI, 10 para CE. |
| `nombre` | String | `private` | Nombres de la persona |
| `ape_paterno` | String | `private` | Apellido paterno |
| `ape_materno` | String | `private` | Apellido materno |
| `fecha_nacimiento` | LocalDate | `private` | Fecha de nacimiento |

### Constructores

| Firma | Descripción |
|---|---|
| `Persona()` | Constructor vacío. Deja el objeto listo para usar los setters. |
| `Persona(String tipo_doc)` | Recibe solo el tipo de documento y lo valida. |
| `Persona(String tipo_doc, String nro_documento)` | Recibe tipo y número, y valida ambos. |
| `Persona(String, String, String, String, String, LocalDate)` | Constructor completo con los seis atributos. |

Los cuatro constructores demuestran la sobrecarga por cantidad de parámetros. Todos delegan la validación en los setters, de modo que las reglas se escriben una sola vez.

### Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `getTipo_doc()` | `String` | Devuelve el tipo de documento. |
| `setTipo_doc(String)` | `void` | Asigna el tipo solo si es DNI o CE. En caso contrario muestra un mensaje de error y no asigna. |
| `getNro_documento()` | `String` | Devuelve el número de documento. |
| `setNro_documento(String)` | `void` | Exige que el tipo de documento se haya asignado antes. Valida la longitud según el tipo: 8 para DNI, 10 para CE. |
| `getNombre()` / `setNombre(String)` | `String` / `void` | Acceso al nombre. |
| `getApe_paterno()` / `setApe_paterno(String)` | `String` / `void` | Acceso al apellido paterno. |
| `getApe_materno()` / `setApe_materno(String)` | `String` / `void` | Acceso al apellido materno. |
| `getFecha_nacimiento()` | `LocalDate` | Devuelve la fecha de nacimiento. |
| `setFecha_nacimiento(LocalDate)` | `void` | Rechaza fechas nulas y fechas posteriores a la fecha actual. |
| `VerDatos()` | `void` | Muestra los datos de la persona en una línea. |
| `VerDatos(String titulo)` | `void` | Sobrecarga. Muestra los datos precedidos por un encabezado. |
| `NombreCompleto()` | `String` | Devuelve "Nombre Paterno Materno". |
| `NombreCompleto(boolean apellidos_primero)` | `String` | Sobrecarga. Si recibe `true`, devuelve "Paterno Materno, Nombre". |
| `CalcularEdad()` | `int` | Edad a la fecha actual. |
| `CalcularEdad(LocalDate fecha_referencia)` | `int` | Sobrecarga. Edad a una fecha de referencia. Devuelve 0 si falta la fecha de nacimiento. |

---

## Clase `PersonaController`

Gestiona la colección de personas en memoria. Es la clase que concentra el uso de colecciones exigido por la práctica de la semana 5.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|---|---|---|---|
| `lista` | `ArrayList<Persona>` | por defecto | Almacena los objetos Persona registrados. Conserva el orden de ingreso y crece de forma dinámica. |

### Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `AgregarPersona(Persona)` | `boolean` | Agrega el objeto con `add()`. Antes verifica que tenga número de documento y que este no esté repetido. Devuelve `true` si el registro se realizó. |
| `listarPersonas()` | `void` | Recorre la lista con un ciclo `for` y muestra los datos de cada persona. Avisa si la lista está vacía. |
| `listarPersonas(String tipo_doc)` | `void` | Sobrecarga. Muestra solo las personas del tipo de documento indicado. |
| `listarPersonas(int edad_minima, int edad_maxima)` | `void` | Sobrecarga. Muestra solo las personas dentro del rango de edad. |
| `buscarPersona(String nro_documento)` | `Persona` | Recorre la lista y devuelve el objeto cuyo documento coincide, o `null` si no existe. |
| `obtenerPersona(int posicion)` | `Persona` | Devuelve el objeto en una posición mediante `get()`, capturando `IndexOutOfBoundsException` e informando el tamaño real de la lista. |
| `eliminarPersona(String nro_documento)` | `void` | Ubica el objeto y lo retira con `remove()`. Avisa si el documento no existe. |
| `ordenarPorApellido()` | `void` | Ordena la lista por apellido paterno mediante comparaciones sucesivas e intercambio de posiciones con `set()`. |
| `contarPorTipo(String tipo_doc)` | `int` | Recorre la lista y devuelve cuántas personas tienen ese tipo de documento. |
| `getCantidad()` | `int` | Devuelve el número de elementos mediante `size()`. |

Los tres métodos `listarPersonas` demuestran la sobrecarga por cantidad y por tipo de parámetros dentro de una misma clase.

---

## Clase `Semana5`

Clase principal del módulo. Contiene el método `main` y el menú de consola desde el que se invocan todas las operaciones.

### Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `main(String[] args)` | `void` | Punto de entrada. Muestra el menú dentro de un ciclo `while` que se repite hasta que el usuario elige la opción 0. Las operaciones se ejecutan dentro de un bloque `try/catch/finally`. |
| `registrarPersona(Scanner, PersonaController)` | `void` | Solicita los datos al usuario, construye el objeto Persona y lo entrega al controlador. Verifica que los datos críticos se hayan asignado antes de registrar. |

### Manejo de errores

| Excepción capturada | Situación | Respuesta del sistema |
|---|---|---|
| `NumberFormatException` | El usuario escribe texto donde se espera un número, por ejemplo en el rango de edad | "Error: debe ingresar solo numeros" |
| `DateTimeParseException` | La fecha de nacimiento no tiene el formato aaaa-mm-dd | "Error: la fecha debe tener el formato aaaa-mm-dd" |
| `IndexOutOfBoundsException` | Se solicita un registro en una posición inexistente | Se informa el tamaño real de la lista. Capturada dentro de `PersonaController`. |

El bloque `finally` del menú garantiza que cada operación cierre con el mensaje "[Operacion finalizada]", se haya producido un error o no.

---

## Relaciones entre clases

| Origen | Destino | Tipo de relación | Multiplicidad |
|---|---|---|---|
| `PersonaController` | `Persona` | Agregación | 1 a 0..* |
| `Semana5` | `PersonaController` | Dependencia de uso | — |
| `Semana5` | `Persona` | Dependencia de creación | — |

El diagrama de clases correspondiente se encuentra en `docs/diagrama_clases.png`.

---

## Temas de la práctica cubiertos por el módulo

| Tema | Dónde se demuestra |
|---|---|
| Sobrecarga de métodos | Cuatro constructores de `Persona`; métodos `VerDatos`, `NombreCompleto` y `CalcularEdad`; tres versiones de `listarPersonas` en `PersonaController` |
| Manejo de errores | Validaciones en los setters de `Persona`; `try/catch/finally` en `Semana5`; captura de `IndexOutOfBoundsException` en `PersonaController` |
| Colecciones | `ArrayList<Persona>` en `PersonaController`, con las operaciones `add`, `get`, `set`, `remove`, `size` e `isEmpty` |
