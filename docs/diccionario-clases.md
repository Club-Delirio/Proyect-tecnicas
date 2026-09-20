# Diccionario de clases

Módulo `semana5` — Sistema de gestión de matrícula y control de asistencia.

Este documento corresponde a la sección 11 del Informe de Proyecto Final.

---

## Clase `Persona`

Representa a una persona registrada en el sistema. Concentra la validación de los datos de identidad, de modo que ningún objeto pueda existir con un documento inválido.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|---|---|---|---|
| `DNI` | String | `public static final` | Constante con el valor "DNI" |
| `CARNET_EXTRANJERIA` | String | `public static final` | Constante con el valor "CE" |
| `PASAPORTE` | String | `public static final` | Constante con el valor "PASAPORTE" |
| `RUC` | String | `public static final` | Constante con el valor "RUC" |
| `FORMATO_FECHA` | DateTimeFormatter | `private static final` | Formato de fecha dd/MM/yyyy |
| `totalCreadas` | int | `private static` | Contador de objetos Persona creados |
| `tipoDocumento` | String | `private` | Tipo de documento de identidad |
| `nroDocumento` | String | `private` | Número de documento, único por persona |
| `nombre` | String | `private` | Nombres de la persona |
| `paterno` | String | `private` | Apellido paterno |
| `materno` | String | `private` | Apellido materno |
| `fechaNacimiento` | LocalDate | `private` | Fecha de nacimiento |

### Constructores

| Firma | Descripción |
|---|---|
| `Persona()` | Constructor vacío. Incrementa el contador de objetos creados. |
| `Persona(String tipoDocumento)` | Recibe solo el tipo de documento. Encadena con `this()`. |
| `Persona(String tipoDocumento, String nroDocumento)` | Recibe tipo y número. Encadena con `this(tipoDocumento)`. |
| `Persona(String, String, String, String, String, LocalDate)` | Constructor completo. Encadena con `this(tipoDocumento, nroDocumento)`. |

Los cuatro constructores están encadenados mediante `this()`, de modo que la validación del documento se ejecuta una sola vez y no se duplica código entre firmas.

### Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `longitudEsperada(String)` | `int` | Devuelve la longitud exigida por cada tipo de documento: 8 para DNI, 9 para CE, 11 para RUC y 12 para pasaporte. Método estático. |
| `validarDocumento(String, String)` | `void` | Valida tipo y número. Lanza `DocumentoInvalidoException` si el tipo no se reconoce, si el número está vacío, si la longitud no coincide o si contiene caracteres no numéricos donde no corresponde. Método estático. |
| `verDatos()` | `void` | Muestra los datos con el encabezado por defecto "PERSONA". |
| `verDatos(String titulo)` | `void` | Sobrecarga. Muestra los datos con un encabezado personalizado. |
| `getNombreCompleto()` | `String` | Devuelve "Nombre Paterno Materno". |
| `getNombreCompleto(boolean apellidosPrimero)` | `String` | Sobrecarga. Si es `true` devuelve "Paterno Materno, Nombre". |
| `calcularEdad()` | `int` | Edad a la fecha actual. |
| `calcularEdad(LocalDate fechaReferencia)` | `int` | Sobrecarga. Edad a una fecha de referencia. Devuelve 0 si la fecha de nacimiento es posterior. |
| `getTotalCreadas()` | `int` | Cantidad de objetos Persona creados. Método estático. |
| `toString()` | `String` | Representación tabular de la persona. Sobrescribe el método de `Object`. |

Además cuenta con los getters y setters de cada atributo. Los setters `setTipoDocumento()` y `setNroDocumento()` validan antes de asignar y pueden lanzar `DocumentoInvalidoException`.

---

## Clase `PersonaController`

Gestiona la colección de personas en memoria. Mantiene sincronizadas dos estructuras complementarias.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|---|---|---|---|
| `lista` | `ArrayList<Persona>` | `private final` | Conserva el orden de registro. Permite recorrer, filtrar y ordenar. |
| `indicePorDocumento` | `HashMap<String, Persona>` | `private final` | Índice por número de documento. Permite buscar en tiempo constante e impide duplicados. |

Ambas estructuras se mantienen sincronizadas: todo objeto que ingresa a la lista se indexa también en el mapa, y toda baja se aplica sobre las dos.

### Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `agregarPersona(Persona)` | `boolean` | Agrega una persona. Devuelve `false` si el documento ya existe o si el objeto llega nulo. |
| `agregarPersona(Persona...)` | `int` | Sobrecarga con parámetros variables. Agrega varias y devuelve cuántas se agregaron. |
| `buscarPorDocumento(String)` | `Persona` | Busca por clave en el mapa. Lanza `PersonaNoEncontradaException` si no existe. |
| `obtenerPorIndice(int)` | `Persona` | Acceso por posición. Captura `IndexOutOfBoundsException` e informa el tamaño real de la lista. |
| `eliminarPorDocumento(String)` | `void` | Elimina de la lista y del índice. Lanza `PersonaNoEncontradaException` si no existe. |
| `listarPersonas()` | `void` | Lista todos los registros numerados. |
| `listarPersonas(String tipoDocumento)` | `void` | Sobrecarga. Lista solo los de un tipo de documento. |
| `listarPersonas(int edadMinima, int edadMaxima)` | `void` | Sobrecarga. Lista solo los de un rango de edad. |
| `ordenarPorApellido()` | `void` | Ordena por apellido paterno y luego por nombre, con `Comparator` encadenado. |
| `contarPorTipoDocumento()` | `Map<String, Integer>` | Devuelve el conteo por tipo en un `LinkedHashMap`, que conserva el orden de inserción. |
| `getCantidad()` | `int` | Número de registros. |
| `estaVacia()` | `boolean` | Indica si la colección está vacía. |
| `getLista()` | `List<Persona>` | Devuelve una copia de la lista, para proteger la colección interna de modificaciones externas. |

---

## Clase `Calculadora`

Demuestra la sobrecarga de métodos en sus tres formas: por cantidad de parámetros, por tipo de parámetros y mediante parámetros variables.

### Métodos

| Método | Retorno | Descripción |
|---|---|---|
| `calcular(int, int)` | `int` | Suma de dos enteros. |
| `calcular(int, int, int)` | `int` | Sobrecarga por cantidad. Suma de tres enteros. |
| `calcular(double, double)` | `double` | Sobrecarga por tipo. Suma de dos decimales. |
| `calcular(String, String)` | `String` | Sobrecarga por tipo. Concatena dos cadenas. |
| `calcular(int...)` | `int` | Sobrecarga con varargs. Suma una cantidad variable de enteros. |
| `dividir(int, int)` | `double` | División. Lanza `ArithmeticException` si el divisor es cero. |
| `convertirAEntero(String)` | `int` | Conversión de texto a entero. Lanza `NumberFormatException` si el texto no es numérico. Método estático. |

No tiene atributos de instancia: todas sus operaciones dependen únicamente de sus parámetros.

---

## Clase `Semana5`

Clase principal del módulo. Integra los tres temas evaluados y ofrece dos modos de ejecución.

### Atributos

| Atributo | Tipo | Visibilidad | Descripción |
|---|---|---|---|
| `FORMATO` | DateTimeFormatter | `private static final` | Formato de fecha para la entrada del usuario |
| `controlador` | PersonaController | `private static final` | Instancia única del controlador |
| `sc` | Scanner | `private static final` | Lector de la entrada estándar |

### Métodos

| Método | Descripción |
|---|---|
| `main(String[] args)` | Punto de entrada. Si recibe el argumento `demo` ejecuta la demostración automática; de lo contrario abre el menú. |
| `menuPrincipal()` | Menú de ocho opciones. Emplea `try/catch/finally`: captura `NumberFormatException` en la lectura de la opción y el bloque `finally` cierra cada operación. |
| `registrarPersona()` | Captura los datos y construye una Persona. Captura `DocumentoInvalidoException` y `DateTimeParseException`. |
| `listarPorTipo()` | Solicita un tipo de documento e invoca la sobrecarga correspondiente. |
| `buscarPersona()` | Busca por documento y muestra el resultado. |
| `eliminarPersona()` | Elimina por documento e informa los registros restantes. |
| `mostrarResumen()` | Muestra el conteo por tipo de documento y el total de objetos creados. |
| `probarCalculadora()` | Demuestra las cinco sobrecargas y el manejo de errores aritméticos. |
| `ejecutarDemostracion()` | Recorrido automático de los tres temas, sin intervención del usuario. Pensado para capturar evidencia. |

---

## Excepciones propias

| Clase | Hereda de | Se lanza cuando |
|---|---|---|
| `DocumentoInvalidoException` | `Exception` | El tipo o el número de documento no cumplen las reglas de validación. |
| `PersonaNoEncontradaException` | `Exception` | Se busca o se elimina un documento que no está registrado. |

Ambas son excepciones verificadas, de modo que el compilador obliga a tratarlas en los puntos donde pueden ocurrir. Residen en el paquete `semana5.excepciones`, separadas del modelo y del controlador.

---

## Relaciones entre clases

| Origen | Destino | Tipo de relación |
|---|---|---|
| `PersonaController` | `Persona` | Agregación (1 a 0..*) |
| `Semana5` | `PersonaController` | Dependencia de uso |
| `Semana5` | `Calculadora` | Dependencia de uso |
| `Semana5` | `Persona` | Dependencia de creación |
| `Persona` | `DocumentoInvalidoException` | Dependencia (lanza) |
| `PersonaController` | `PersonaNoEncontradaException` | Dependencia (lanza) |
| `DocumentoInvalidoException` | `Exception` | Herencia |
| `PersonaNoEncontradaException` | `Exception` | Herencia |

El diagrama de clases correspondiente se encuentra en `docs/diagrama_clases.png`.
