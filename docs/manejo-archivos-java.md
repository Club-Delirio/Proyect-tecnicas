# Manejo de archivos en Java: investigación y práctica

**Proyecto:** Sistema de gestión de matrícula y control de asistencia — Academia Preuniversitaria San Fernando
**Curso:** Técnicas de Programación Orientada a Objetos (SIST1202A) — UPN Cajamarca, 2026-2
**Semana del sílabo:** 9
**Aplicación práctica:** clase `ArchivoUtil` del paquete `academia`

---

## 1. Por qué el proyecto necesita archivos

Hasta la semana 8, el sistema guardaba la oferta académica, el padrón de personas, las matrículas y la asistencia en objetos `ArrayList` dentro de la memoria del programa. Esa memoria pertenece al proceso de Java: cuando el programa termina, el sistema operativo la libera y los datos desaparecen.

Para una academia eso es inaceptable. Significa que al día siguiente hay que volver a cargar los cuatro ciclos de la oferta, que se pierde el dato de cuántas vacantes ya están ocupadas —con lo que se vuelve al problema original de matricular a más estudiantes de los que caben en el aula— y que el historial de asistencia y la bonificación acumulada de cada docente se borran cada vez que alguien cierra la ventana.

La persistencia es la capacidad de que un dato sobreviva al proceso que lo creó. Java ofrece tres caminos: archivos, bases de datos y servicios remotos. Por la restricción R-02 documentada en `docs/restricciones-y-alternativas.md`, este proyecto usa archivos de texto.

---

## 2. Marco teórico

### 2.1 El concepto de flujo

Java no lee un archivo "de golpe": lo trata como un **flujo** (*stream*), una secuencia ordenada de datos que entra o sale del programa. El flujo tiene una sola dirección: o se lee de él o se escribe en él, nunca las dos cosas con el mismo objeto.

Las clases de flujo viven en el paquete `java.io` y se dividen en dos familias según lo que transportan:

| Familia | Unidad que transporta | Clases base | Para qué sirve |
|---|---|---|---|
| Flujos de bytes | Un byte (8 bits) | `InputStream`, `OutputStream` | Imágenes, audio, archivos comprimidos, cualquier contenido binario |
| Flujos de caracteres | Un carácter | `Reader`, `Writer` | Texto: archivos `.txt`, `.csv`, `.json` |

Este proyecto guarda texto, de modo que usa la familia de caracteres. Escoger la familia equivocada es el error más común al empezar: leer un archivo de texto con flujos de bytes obliga a convertir la codificación a mano y rompe las tildes y la letra ñ. Esto importa aquí, porque el padrón tiene apellidos como Saldaña y nombres de campañas como `SIN CAMPAÑA`.

### 2.2 Las clases que usa el proyecto

| Clase | Qué hace | Dónde se usa en el proyecto |
|---|---|---|
| `File` | Representa la ruta de un archivo o de una carpeta. No abre nada: solo consulta y manipula la ruta. | `crearCarpeta()`, `existeArchivo()`, `tamanioArchivo()`, `eliminarArchivo()` |
| `FileWriter` | Escribe caracteres en un archivo. Si el archivo no existe, lo crea. | Dentro de `guardarLineas()` y `agregarLinea()` |
| `BufferedWriter` | Envuelve a un `FileWriter` y acumula la escritura en memoria antes de volcarla al disco. Aporta además `newLine()`, que usa el salto de línea propio del sistema operativo. | `guardarLineas()`, `agregarLinea()` |
| `FileReader` | Lee caracteres de un archivo. | Dentro de `leerLineas()` |
| `BufferedReader` | Envuelve a un `FileReader` y aporta `readLine()`, que devuelve una línea completa o `null` al llegar al final. | `leerLineas()` |
| `IOException` | Excepción comprobada que Java obliga a tratar en toda operación de entrada y salida. | Bloques `catch` de `ArchivoUtil` |

### 2.3 Por qué se usa un buffer

Escribir carácter por carácter directamente en el disco obliga al sistema operativo a una operación física por cada carácter. Con un buffer, Java acumula los caracteres en memoria y hace una sola escritura física cuando el buffer se llena o cuando se cierra el flujo. La diferencia de rendimiento en archivos grandes es de órdenes de magnitud.

De ahí viene una consecuencia importante: **si el flujo no se cierra, lo que quedó en el buffer nunca llega al disco**. Un archivo puede quedar vacío o incompleto no porque la escritura fallara, sino porque nadie cerró el flujo.

### 2.4 El modo de escritura: sobrescribir o agregar

El segundo parámetro del constructor de `FileWriter` decide el comportamiento:

```java
new FileWriter(ruta, false)   // sobrescribe: el archivo queda solo con lo nuevo
new FileWriter(ruta, true)    // agrega: lo nuevo se añade al final de lo existente
```

Olvidar ese parámetro equivale a pasar `false`, es decir, a borrar el contenido anterior. En el proyecto, `guardarLineas()` usa `false` porque vuelca el estado completo de la colección, y `agregarLinea()` usa `true` porque añade un registro suelto.

### 2.5 El bloque try / catch / finally aplicado a archivos

Toda operación con archivos puede fallar por causas ajenas al programa: el disco se llena, el archivo está abierto por otro proceso, la carpeta no tiene permiso de escritura. Java obliga a tratar la `IOException`.

La estructura correcta tiene tres partes:

| Bloque | Responsabilidad |
|---|---|
| `try` | Abrir el flujo y operar sobre él |
| `catch (IOException e)` | Informar el problema sin que el programa termine de forma abrupta |
| `finally` | Cerrar el flujo, se haya producido el error o no |

El `finally` es obligatorio porque es el único bloque que se ejecuta en los dos caminos. Si el cierre se escribiera al final del `try`, un error a mitad de la escritura saltaría al `catch` y el flujo quedaría abierto, con el buffer sin volcar.

El cierre también puede fallar, de modo que `close()` va dentro de su propio `try`/`catch` anidado en el `finally`.

### 2.6 El formato de línea elegido

Para guardar un objeto en una línea de texto hay que decidir cómo separar sus atributos. El proyecto usa el punto y coma:

```
CICLO;CIC-001;Ciclo Anual 2026-II;2026-08-03;2026-12-18;1450.0;PRESENCIAL;30;3;ABIERTO;MANANA;5;10
```

| Decisión | Motivo |
|---|---|
| Separador `;` en vez de `,` | Los nombres de los ciclos y las observaciones de asistencia contienen comas con frecuencia. Con coma como separador, `Repaso Intensivo Ciencias, turno manana` se partiría en dos campos |
| Primer campo como discriminador (`CICLO`, `CURSO`, `ESTUDIANTE`, `DOCENTE`) | Permite saber qué clase reconstruir al leer, y deja guardar dos clases hermanas en un mismo archivo |
| Fechas en formato `aaaa-mm-dd` | Es el formato que `LocalDate.toString()` produce y que `LocalDate.parse()` acepta sin configuración adicional |
| Una línea por objeto | `readLine()` devuelve exactamente un objeto por llamada, lo que simplifica la lectura |
| Las vacantes ocupadas y el estado se guardan | Sin ellos, al recuperar el archivo todos los ciclos volverían a estar vacíos y se reproduciría el problema de sobrepasar el aforo que el sistema resuelve |

La operación inversa, convertir la línea en un arreglo de campos, se hace con `split(";")`.

---

## 3. Práctica: la clase ArchivoUtil

Toda la lectura y escritura del sistema está concentrada en una sola clase. Ninguna otra clase del proyecto importa `java.io`. Esa decisión tiene una consecuencia práctica: cuando el proyecto migre a base de datos (RF-42), solo habrá que reemplazar `ArchivoUtil`, sin tocar las trece clases restantes.

### 3.1 Escritura con BufferedWriter

```java
public boolean guardarLineas(String nombre_archivo, ArrayList<String> lineas) {
    if (lineas == null) {
        System.out.println("Error: no hay lineas que guardar");
        return false;
    }
    this.crearCarpeta();
    BufferedWriter escritor = null;
    boolean resultado = false;
    try {
        escritor = new BufferedWriter(new FileWriter(this.rutaDe(nombre_archivo), false));
        for (int i = 0; i < lineas.size(); i++) {
            escritor.write(lineas.get(i));
            escritor.newLine();
        }
        resultado = true;
        System.out.println("Se guardaron " + lineas.size() + " registro(s) en "
                + this.rutaDe(nombre_archivo));
    } catch (IOException e) {
        System.out.println("Error de escritura: " + e.getMessage());
    } finally {
        try {
            if (escritor != null) {
                escritor.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo: " + e.getMessage());
        }
        System.out.println("[Operacion de escritura finalizada]");
    }
    return resultado;
}
```

**Puntos a observar:**

1. El `escritor` se declara **fuera** del `try`. Si se declarara dentro, el `finally` no podría verlo.
2. Se inicializa en `null` y el `finally` comprueba `if (escritor != null)`. Si el constructor de `FileWriter` falla, el `finally` se ejecuta igual y sin esa comprobación lanzaría `NullPointerException`.
3. El `close()` del `finally` tiene su propio `try`/`catch`, porque cerrar también puede fallar.
4. `newLine()` se prefiere a escribir `"\n"` porque usa el salto de línea del sistema operativo: `\r\n` en Windows y `\n` en Linux.
5. El método devuelve `boolean`, de modo que quien lo llama puede saber si la operación tuvo éxito.

### 3.2 Lectura con BufferedReader

```java
public ArrayList<String> leerLineas(String nombre_archivo) {
    ArrayList<String> lineas = new ArrayList();
    if (!this.existeArchivo(nombre_archivo)) {
        System.out.println("Aviso: el archivo " + this.rutaDe(nombre_archivo)
                + " todavia no existe");
        return lineas;
    }
    BufferedReader lector = null;
    try {
        lector = new BufferedReader(new FileReader(this.rutaDe(nombre_archivo)));
        String linea = lector.readLine();
        while (linea != null) {
            if (linea.trim().length() > 0) {
                lineas.add(linea);
            }
            linea = lector.readLine();
        }
    } catch (IOException e) {
        System.out.println("Error de lectura: " + e.getMessage());
    } finally {
        try {
            if (lector != null) {
                lector.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo: " + e.getMessage());
        }
        System.out.println("[Operacion de lectura finalizada]");
    }
    return lineas;
}
```

**Puntos a observar:**

1. Se comprueba la existencia del archivo **antes** de abrirlo. Así, la primera ejecución del programa —cuando todavía no hay archivos— muestra un aviso en lugar de una excepción.
2. El patrón de lectura es: una llamada a `readLine()` antes del `while`, y otra al final de cada vuelta. La condición es `linea != null`, porque `readLine()` devuelve `null` al llegar al final del archivo, no una cadena vacía.
3. Las líneas en blanco se descartan, de modo que un salto de línea al final del archivo no produzca un objeto vacío al cargar.
4. El método siempre devuelve una lista, nunca `null`. Quien lo llama puede recorrerla sin comprobar nada.

### 3.3 Creación de la carpeta con File

```java
public boolean crearCarpeta() {
    File directorio = new File(this.carpeta);
    if (directorio.exists()) {
        return true;
    }
    boolean creada = directorio.mkdirs();
    if (creada) {
        System.out.println("Carpeta creada: " + directorio.getAbsolutePath());
    } else {
        System.out.println("Error: no se pudo crear la carpeta " + this.carpeta);
    }
    return creada;
}
```

Se usa `mkdirs()` y no `mkdir()`: el primero crea también las carpetas intermedias que falten en la ruta. La ruta se construye con `File.separator` en vez de escribir `/` o `\`, para que el mismo código funcione en Windows y en Linux:

```java
public String rutaDe(String nombre_archivo) {
    return this.carpeta + File.separator + nombre_archivo;
}
```

### 3.4 Serialización: herencia y polimorfismo aplicados

Cada clase del dominio sabe convertirse a sí misma en una línea de texto. La clase abstracta declara el método como obligatorio y arma la parte común; cada clase hija lo completa con sus propios campos y con un discriminador que permitirá reconstruirla:

```java
// En la clase abstracta Ciclo
public abstract String convertirALinea();

protected String datosComunesEnLinea() {
    return this.codigo + ";" + this.nombre + ";" + this.fec_inicio + ";" + this.fec_fin + ";"
            + this.costo_base + ";" + this.modalidad + ";" + this.vacantes + ";"
            + this.matriculados + ";" + this.estado;
}

// En CicloRegular
@Override
public String convertirALinea() {
    return "CICLO;" + this.datosComunesEnLinea() + ";" + this.turno + ";"
            + this.meses_duracion + ";" + this.nro_simulacros;
}

// En CursoIntensivo
@Override
public String convertirALinea() {
    return "CURSO;" + this.datosComunesEnLinea() + ";" + this.area + ";"
            + this.semanas_duracion + ";" + this.horas_diarias;
}
```

El resultado es que el método que guarda no necesita saber qué hay en la colección:

```java
for (Ciclo c : ciclos) {
    lineas.add(c.convertirALinea());
}
```

Ese bucle recorre ciclos regulares y cursos intensivos mezclados y cada objeto arma su propia línea. Es el mismo polimorfismo que ya usaban `verDatos()`, `calcularCategoria()` y `calcularDescuento()`, aplicado ahora a la persistencia. Si mañana se agrega una clase `TallerReforzamiento`, el bucle no cambia.

La misma estructura se repite en la otra jerarquía: `Persona` declara `convertirALinea()` como abstracto y arma `datosComunesEnLinea()` con el documento, los nombres y el teléfono; `Estudiante` y `Docente` lo completan con lo suyo y con el discriminador `ESTUDIANTE` o `DOCENTE`.

### 3.5 Lectura defensiva al reconstruir los objetos

```java
public static void cargarCiclos(ArchivoUtil archivo, ArrayList<Ciclo> ciclos) {
    ArrayList<String> lineas = archivo.leerLineas("ciclos.txt");
    int cargados = 0;
    for (int i = 0; i < lineas.size(); i++) {
        String[] campo = lineas.get(i).split(";");
        if (campo.length < 13) {
            System.out.println("Aviso: la linea " + (i + 1)
                    + " de ciclos.txt esta incompleta y se omite");
            continue;
        }
        try {
            Ciclo c;
            if (campo[0].equals("CICLO")) {
                c = new CicloRegular(campo[1], campo[2], LocalDate.parse(campo[3]),
                        LocalDate.parse(campo[4]), Double.parseDouble(campo[5]), campo[6],
                        Integer.parseInt(campo[7]), campo[10], Integer.parseInt(campo[11]),
                        Integer.parseInt(campo[12]));
            } else {
                c = new CursoIntensivo(campo[1], campo[2], LocalDate.parse(campo[3]),
                        LocalDate.parse(campo[4]), Double.parseDouble(campo[5]), campo[6],
                        Integer.parseInt(campo[7]), campo[10], Integer.parseInt(campo[11]),
                        Integer.parseInt(campo[12]));
            }
            c.setMatriculados(Integer.parseInt(campo[8]));
            c.setEstado(campo[9]);
            if (c.getCodigo() != null) {
                ciclos.add(c);
                cargados = cargados + 1;
            }
        } catch (Exception e) {
            System.out.println("Aviso: no se pudo leer la linea " + (i + 1) + " de ciclos.txt");
        }
    }
    System.out.println("Ciclos recuperados: " + cargados);
}
```

Un archivo de texto puede ser editado a mano por cualquiera, de modo que no se puede confiar en su contenido. La carga aplica cuatro defensas:

1. **Comprobación del número de campos** antes de acceder al arreglo, lo que evita `ArrayIndexOutOfBoundsException`.
2. **`try`/`catch` por línea**: una línea con una fecha mal escrita se descarta con un aviso y la carga sigue con las demás. Un solo registro corrupto no impide recuperar los otros tres.
3. **Las validaciones siguen vigentes**: los objetos se reconstruyen con sus constructores, que invocan los mismos `set` que el registro manual. Un archivo editado a mano no puede introducir un ciclo presencial con 300 vacantes ni un costo de 10 soles.
4. **Comprobación del resultado**: solo se agrega el objeto si su código quedó asignado, es decir, si las validaciones lo aceptaron.

### 3.6 Reconstrucción de las relaciones

El caso más delicado es el de las matrículas, porque una matrícula no guarda copias de su estudiante, su tutor y su ciclo: guarda referencias. En el archivo solo se escriben sus identificadores:

```
MAT-1;2026-10-26;EST-001;DOC-001;CIC-001;CONTADO;0;SIN CAMPANA;0.0;1450.0
```

Al cargar, hay que volver a enlazar los tres objetos buscándolos en las colecciones ya cargadas, y volver a aplicar los efectos que la matrícula tuvo sobre ellos:

```java
Estudiante e = buscarEstudiante(personas, campo[2]);
Docente d = buscarDocente(personas, campo[3]);
Ciclo c = buscarCiclo(ciclos, campo[4]);
if (e == null || d == null || c == null) {
    System.out.println("Aviso: la matricula " + campo[0]
            + " referencia datos que ya no existen y se omite");
    continue;
}
...
e.agregarMatricula(c);
d.registrarMatricula(m.getPrecio_final());
```

De ahí se desprende el orden obligatorio de la carga: **primero los ciclos, después las personas, luego las matrículas y al final las asistencias**. Si se cargaran las matrículas primero, no habría a qué apuntar.

Hay un detalle que conviene explicar, porque es el tipo de error que no se ve hasta que los números no cuadran: el contador de matriculados de cada ciclo **ya viene guardado en el archivo**, de modo que al cargar las matrículas **no** se vuelve a invocar `registrarMatriculado()`. Si se hiciera, las vacantes se descontarían dos veces y un ciclo con tres matriculados aparecería con seis.

---

## 4. Prueba de funcionamiento

### 4.1 Procedimiento

1. Ejecutar el programa y responder `n` a recuperar los datos guardados y `s` a cargar los datos de prueba.
2. Responder `n` a todas las secciones de registro, matrícula y asistencia.
3. Responder `s` a guardar la información en los archivos.
4. Responder `s` a ver el contenido de un archivo e indicar `personas.txt`.
5. Cerrar el programa y volver a ejecutarlo, respondiendo `s` a recuperar los datos guardados.

### 4.2 Resultado de la escritura

```
----- GUARDAR LA INFORMACION -----
Desea guardar la informacion en los archivos s/n
Carpeta creada: /ruta/del/proyecto/datos
Se guardaron 4 registro(s) en datos/ciclos.txt
[Operacion de escritura finalizada]
Se guardaron 8 registro(s) en datos/personas.txt
[Operacion de escritura finalizada]
Se guardaron 6 registro(s) en datos/matriculas.txt
[Operacion de escritura finalizada]
Se guardaron 9 registro(s) en datos/asistencias.txt
[Operacion de escritura finalizada]
Se guardaron 7 registro(s) en datos/reporte_matriculas.csv
[Operacion de escritura finalizada]
```

### 4.3 Contenido de los archivos

```
--- Contenido de datos/personas.txt (897 bytes) ---
1: ESTUDIANTE;DNI;75236914;Karla Yamile;Chilon;Bautista;941258963;EST-005;2008-01-12;Cristo Rey;Contabilidad;Pedro Chilon Mego
2: ESTUDIANTE;CE;001245789;Ana Sofia;Cruz;Marin;933221144;EST-003;2007-02-14;Santa Teresita;Derecho;Rosa Marin Flores
3: ESTUDIANTE;DNI;73695214;Diego Alonso;Huaman;Paredes;912345678;EST-002;2006-11-08;Juan XXIII;Ingenieria de Sistemas;Jose Huaman Diaz
4: ESTUDIANTE;DNI;76398521;Marco Antonio;Llanos;Saldana;932147856;EST-006;2005-09-25;San Marcelino;Ingenieria Civil;-
5: DOCENTE;DNI;41258963;Raul;Mendoza;Chavez;976543210;DOC-001;MATEMATICA;35.0;3.0;80
6: ESTUDIANTE;DNI;72581496;Lucia Fernanda;Quispe;Tanta;987654321;EST-001;2008-05-20;San Ramon;Medicina;Maria Tanta Lopez
7: ESTUDIANTE;DNI;74125896;Bruno Cesar;Terrones;Vigo;956871234;EST-004;2009-07-30;Dos de Mayo;Medicina;Elena Vigo Alcantara
8: DOCENTE;DNI;43698521;Carmen Rosa;Vasquez;Rojas;965321478;DOC-002;COMUNICACION;28.0;2.5;64
--- Fin del archivo ---
```

Se observa que el discriminador del primer campo permite distinguir a los dos docentes de los seis estudiantes dentro de un mismo archivo, y que cada clase escribe los campos que solo ella tiene. Las ocho personas salen ordenadas por apellido paterno porque el listado de la sección 7 invoca antes `personas.ordenarPorApellido()`, que ordena la colección en el propio controlador con el método de la burbuja; al guardar, el archivo conserva ese orden.

```
datos/ciclos.txt
CICLO;CIC-001;Ciclo Anual 2026-II;2026-08-03;2026-12-18;1450.0;PRESENCIAL;30;3;ABIERTO;MANANA;5;10
CICLO;CIC-002;Ciclo Semestral Virtual;2026-09-01;2026-12-20;850.0;VIRTUAL;120;2;ABIERTO;NOCHE;4;8
CURSO;CUR-001;Repaso Intensivo Ciencias;2026-10-05;2026-11-28;620.0;PRESENCIAL;25;1;ABIERTO;CIENCIAS;8;5
CURSO;CUR-002;Vacacional Letras;2026-12-28;2027-01-22;380.0;PRESENCIAL;20;0;ABIERTO;LETRAS;4;4
```

El noveno campo guarda los matriculados y el décimo el estado: ahí está la información que impide volver a llenar un aula ya ocupada.

```
datos/reporte_matriculas.csv
CODIGO;FECHA;ESTUDIANTE;TUTOR;CICLO;TIPO;CATEGORIA;FORMA_PAGO;PRECIO_FINAL
MAT-1;2026-10-26;Lucia Fernanda Quispe Tanta;Raul Mendoza Chavez;Ciclo Anual 2026-II;CICLO REGULAR;PREMIUM;CONTADO;1450.00
MAT-2;2026-10-26;Bruno Cesar Terrones Vigo;Raul Mendoza Chavez;Ciclo Anual 2026-II;CICLO REGULAR;PREMIUM;FRACCIONADO;1551.50
```

### 4.4 Resultado de la recuperación

```
----- RECUPERAR LOS DATOS GUARDADOS -----
Desea recuperar los datos guardados en los archivos s/n
[Operacion de lectura finalizada]
Ciclos recuperados: 4
[Operacion de lectura finalizada]
Personas recuperadas: 8
[Operacion de lectura finalizada]
Matriculas recuperadas: 6
[Operacion de lectura finalizada]
Asistencias recuperadas: 9
Se recuperaron 8 personas, 4 ciclos, 6 matriculas y 9 asistencias de los archivos
```

El catálogo recuperado conserva las vacantes ya ocupadas:

```
[PREMIUM]
   CICLO REGULAR  CIC-001  Ciclo Anual 2026-II  PRESENCIAL  S/ 1450.00  Vacantes 3/30  ABIERTO
```

Y tanto el reporte financiero como el de asistencia vuelven a dar los mismos números que antes de cerrar el programa, lo que demuestra que no solo se recuperaron los objetos, sino también las relaciones entre ellos:

```
INGRESO TOTAL             : S/ 6715.04
Planilla docente          : S/ 4785.36
Ingreso neto              : S/ 1929.68

EST-002  Huaman Paredes, Diego Alonso  Asistencia: 33%  (1 de 3)   <-- RIESGO DE DESERCION
EST-004  Terrones Vigo, Bruno Cesar  Asistencia: 67%  (2 de 3)   <-- RIESGO DE DESERCION
```

### 4.5 Archivos que genera el sistema

| Archivo | Contenido | Registros del juego de prueba |
|---|---|---|
| `datos/ciclos.txt` | Un ciclo por línea, con el discriminador `CICLO` o `CURSO`, sus vacantes ocupadas y su estado | 4 |
| `datos/personas.txt` | Una persona por línea, con el discriminador `ESTUDIANTE` o `DOCENTE` | 8 |
| `datos/matriculas.txt` | Una matrícula por línea, con los identificadores de las tres entidades que relaciona | 6 |
| `datos/asistencias.txt` | Un registro de asistencia por línea, con su estado y su observación | 9 |
| `datos/reporte_matriculas.csv` | Historial exportado, con fila de encabezados, para abrir en una hoja de cálculo | 6 más el encabezado |

---

## 5. Errores frecuentes y cómo se evitaron

| Error | Qué provoca | Cómo lo evita el proyecto |
|---|---|---|
| No cerrar el flujo | El archivo queda vacío o incompleto porque el buffer no se volcó | `close()` dentro del bloque `finally` |
| Declarar el flujo dentro del `try` | El `finally` no puede cerrarlo porque está fuera de su ámbito | El flujo se declara antes del `try` e inicializado en `null` |
| Cerrar sin comprobar `null` | `NullPointerException` cuando el archivo no pudo abrirse | `if (escritor != null)` antes de `close()` |
| Abrir un archivo inexistente | `FileNotFoundException` en la primera ejecución | `existeArchivo()` se consulta antes de abrir |
| Olvidar el segundo parámetro de `FileWriter` | Se borra el contenido anterior sin aviso | El parámetro se escribe siempre de forma explícita |
| Usar `"\n"` como salto de línea | El archivo se ve en una sola línea al abrirlo en Windows | `newLine()` del `BufferedWriter` |
| Separar los campos con coma | Los nombres de ciclos y las observaciones con coma rompen el `split` | Separador punto y coma |
| Confiar en el contenido del archivo | `ArrayIndexOutOfBoundsException` o datos inválidos en memoria | Comprobación del número de campos y `try`/`catch` por línea |
| Comparar `readLine()` con `""` | El bucle no termina nunca | La condición es `linea != null` |
| Cargar las matrículas antes que las entidades | Las referencias quedan en `null` y los reportes fallan | Orden obligatorio: ciclos, personas, matrículas y asistencias |
| No guardar las vacantes ocupadas | Al recuperar, todos los ciclos vuelven a estar vacíos | Los campos `matriculados` y `estado` se serializan y se restituyen |
| Volver a descontar la vacante al cargar | Las vacantes se descuentan dos veces y los números no cuadran | El contador se restituye del archivo; al cargar no se invoca `registrarMatriculado()` |

---

## 6. Alternativas que no se usaron y por qué

| Alternativa | Qué aporta | Por qué no se usó aquí |
|---|---|---|
| `try`-with-resources | Cierra el flujo de forma automática, sin `finally` | El curso exige demostrar el bloque `finally` de forma explícita |
| `ObjectOutputStream` (serialización binaria) | Guarda el objeto completo sin escribir el formato a mano | El archivo resultante no es legible ni editable, lo que impide inspeccionarlo durante las pruebas |
| `java.nio.file.Files` | API más moderna y con métodos de una sola línea | `java.io` es lo que corresponde a este punto del sílabo |
| Formato JSON o XML | Estructura jerárquica y estándar | Exige una biblioteca externa, y el modelo de datos del proyecto es plano |

---

## 7. Conclusiones

1. La persistencia en archivos de texto resuelve el problema central del sistema —que las vacantes ocupadas y el historial sobrevivan al cierre del programa— sin agregar ninguna dependencia externa.
2. Concentrar toda la entrada y salida en la clase `ArchivoUtil` mantiene a las trece clases restantes libres de `java.io`, de modo que una futura migración a base de datos afecta a un solo archivo.
3. El bloque `finally` no es una formalidad: es lo único que garantiza que el buffer se vuelque al disco cuando la operación falla a mitad de camino.
4. La lectura debe ser defensiva. Un archivo de texto es editable por cualquiera, y una sola línea corrupta no debe impedir recuperar las demás.
5. El método `convertirALinea()` demuestra que el polimorfismo no es un adorno académico: el bucle que guarda la colección no pregunta si el objeto es un ciclo regular o un curso intensivo, y agregar un tercer tipo de oferta no obligaría a tocarlo.
6. Guardar objetos que se referencian entre sí exige guardar identificadores y volver a enlazarlos al cargar, en un orden que respete las dependencias. Esa es la misma idea que después se formaliza con las claves foráneas de una base de datos.
7. Restituir un contador desde el archivo y volver a calcularlo son cosas distintas. Confundirlas fue el error que más costó detectar, porque el programa no falla: simplemente entrega números que no cuadran.

---

## 8. Referencias

Oracle. (2024). *Java Platform, Standard Edition & Java Development Kit API specification: Package java.io*. https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/package-summary.html

Oracle. (2024). *The Java tutorials: Basic I/O*. https://docs.oracle.com/javase/tutorial/essential/io/

Deitel, P., & Deitel, H. (2022). *Java: How to program* (12.ª ed.). Pearson.
