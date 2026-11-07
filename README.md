# Técnicas de Programación Orientada a Objetos — SIST1202A

Repositorio compartido del equipo para las actividades de práctica de campo del curso
**Técnicas de Programación Orientada a Objetos** (Ingeniería de Sistemas Computacionales,
UPN Cajamarca, periodo 2026-2).

**Proyecto:** *Academia Preuniversitaria San Fernando* — sistema de gestión de matrícula y control de asistencia.

## Integrantes

| Etiqueta | Apellidos y nombres | Usuario de GitHub | Correo de `git config user.email` |
|---|---|---|---|
| Integrante 1 | Chuquimango Verastegui Jhocep Jhanpier | JhocepGab | snkje123@gmail.com |
| Integrante 2 | CACHI TANTALEAN ANDERSON ALEXIS | Anderson-Cachi | n00461903@upn.pe |
| Integrante 3 | ROJAS VALDIVIA JHANN FRANK | Jhann-999 | rojasjhann7@gmail.com |
| Integrante 4 | RODRIGO ORTIZ SUMIL ARTURI | init57 | rubick042@gmail.com |

## Contenido del repositorio

| Carpeta / archivo | Descripción |
|---|---|
| `src/academia/` | Sistema de matrícula y asistencia: 25 archivos con el dominio, los controladores, la versión de consola y la interfaz gráfica Swing (semanas 7 a 13) |
| `docs/restricciones-y-alternativas.md` | 12 restricciones del proyecto y sus alternativas de solución (semana 7) |
| `docs/objetivos-y-alcance.md` | Objetivos del proyecto y alcance de la solución (semana 7) |
| `docs/requerimientos-funcionales.md` | 45 requerimientos funcionales, 22 implementados, con 192 criterios de aceptación y la evidencia de cada uno (semanas 8, 10 y 13) |
| `docs/historias-usuario.md` | 22 historias de usuario con sus escenarios (semanas 8 y 12) |
| `docs/Historias_Usuario_Academia.xlsx` | Matriz de historias de usuario en la plantilla del curso |
| `docs/manejo-archivos-java.md` | Investigación y práctica de manejo de archivos en Java (semana 9) |
| `docs/diagrama_clases.png` | Diagrama de clases UML del dominio, en imagen (semana 9) |
| `docs/diagrama_clases.svg` | El mismo diagrama en vectorial, para imprimir o ampliar sin perder nitidez |
| `docs/diagrama_clases.drawio` | El mismo diagrama en formato draw.io, para abrirlo y editarlo en app.diagrams.net. Muestra las clases de dominio, la clase `Cuota` (composición con `Matricula`) y la interfaz `Registrable`; la Figura 1 del informe de la semana 11 incluye además los controladores, `ArchivoUtil` y la clase principal |
| `docs/ishikawa.png`, `docs/ishikawa.svg` | Diagrama de Ishikawa del problema |
| `docs/Informe_Academia_APA7.docx` | Informe del proyecto en formato APA 7 |
| `docs/Informe_Responsabilidad_Social.docx` | Informe de la actividad de responsabilidad social (semana 11) |
| `docs/reparto-de-tareas.md` | Qué hizo cada integrante |
| `docs/GUIA_GIT_SEMANAS7-11.md` | Comandos de Git y GitHub que ejecuta cada integrante |
| `docs/CHECKLIST_ENTREGA.md` | Lista de comprobación antes de presentar: la T3 de las semanas 12 y 13, con la rúbrica y los valores que debe mostrar el programa, y la entrega de las semanas 7 a 11 |
| `docs/` de las semanas 1 a 6 | `objetivos.md`, `restricciones-alternativas.md`, `diccionario-clases.md`, `manual-usuario.md`, `Historias_Usuario_Semana5.xlsx`, `Informe_Secciones_1y2_APA7.docx`, `GUIA_GIT_GITHUB.md` y `evidencias.zip`: los documentos de la primera versión del sistema (paquete `semana5`) y las primeras versiones de los objetivos y las restricciones, que se conservan como registro |
| `evidencias/` | Capturas que prueban cada criterio de aceptación y registros de ejecución (semana 13). El índice está en `evidencias/README.md` |
| `.gitignore` | Excluye los compilados y la carpeta de datos generada en ejecución |

## Requisitos

- JDK 17 o superior
- Apache NetBeans 21 o superior

## Cómo ejecutarlo

El sistema tiene dos clases principales:

| Clase | Qué abre |
|---|---|
| `academia.VentanaPrincipal` | La interfaz gráfica (semanas 12 y 13) |
| `academia.AcademiaSanFernando` | La versión de consola (semanas 7 a 11) |

### Desde Apache NetBeans

1. `File > Open Project...` y seleccionar la carpeta del proyecto.
2. Clic derecho sobre `VentanaPrincipal.java` > **Run File** (o **Shift + F6**).
3. Para que **F6** abra siempre la interfaz gráfica: clic derecho sobre el proyecto > **Properties** > **Run** > **Main Class** > `academia.VentanaPrincipal`.

### Desde la consola

```bash
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build academia.VentanaPrincipal
java -cp build academia.AcademiaSanFernando
```

### Primer paso recomendado

La primera vez que se abre la interfaz gráfica, como todavía no hay archivos, pregunta si se cargan
los datos de prueba: responder **Sí**. Se crean 2 docentes, 6 estudiantes, 4 ciclos, 6 matrículas
con sus cuotas y 9 registros de asistencia. Desde la segunda vez, los datos guardados se recuperan
solos al abrir.

En la consola, responder `n` a recuperar los datos y `s` a cargar los datos de prueba.

## La interfaz gráfica

Ventana principal con barra de menú y un escritorio (`JDesktopPane`) donde se abren las ventanas
internas, como en la clase 12.

| Menú | Ventana | Requerimientos |
|---|---|---|
| Datos básicos | Ciclos y cursos | RF-01, RF-02, RF-03, RF-35 |
| Datos básicos | Estudiantes | RF-15, RF-16, RF-17, RF-21 |
| Datos básicos | Docentes | RF-09 |
| Procesos | Matrícula y cotización | RF-22, RF-23, RF-24, RF-25, RF-27 |
| Procesos | Pagos y anulaciones | RF-27, RF-28 |
| Procesos | Control de asistencia | RF-31, RF-36 |
| Reportes | Tablero del periodo | RF-36, RF-37, RF-21 |
| Archivo | Guardar, copia de respaldo, exportar a CSV, salir | RF-39, RF-41 |
| — | Al abrir: recuperación automática de los archivos | RF-40 |

## La versión de consola

| Sección | Operación |
|---|---|
| 0 | Recuperar los datos guardados en los archivos |
| — | Cargar datos de prueba |
| 1 | Registro de ciclos regulares y cursos intensivos, con control de código duplicado |
| — | Catálogo de la oferta agrupado por categoría |
| 2 | Registro de docentes y horas dictadas |
| 3 | Registro de estudiantes, con reconocimiento del que ya existe |
| 4 | Configuración de la campaña de descuento del periodo |
| 5 | Cotización y registro de matrículas |
| 6 | Control de asistencia diaria |
| 7 | Listados, historial, estadísticas, reporte financiero y reporte de asistencia |
| 8 | Guardar la información y exportar el historial a CSV |

## Las clases del paquete `academia`

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `Registrable` | interfaz | Contrato `verDatos()` y `convertirALinea()` de todo lo que se guarda en archivos |
| `Persona` | abstracta, implementa Registrable | Datos y validaciones comunes del estudiante y del docente |
| `Estudiante` | extiende Persona | Código, fecha de nacimiento, colegio, carrera objetivo, apoderado e historial de ciclos (agregación) |
| `Docente` | extiende Persona | Código, especialidad, tarifa por hora, bonificación y acumulados |
| `Ciclo` | abstracta, implementa Registrable | Datos y validaciones comunes de la oferta, y el control de vacantes |
| `CicloRegular` | extiende Ciclo | Turno, meses de duración y número de simulacros |
| `CursoIntensivo` | extiende Ciclo | Área, semanas de duración y horas diarias |
| `Matricula` | asociación, implementa Registrable | Relaciona estudiante, docente tutor y ciclo, calcula el precio final y crea sus cuotas |
| `Cuota` | parte de Matricula (composición) | Monto, vencimiento y estado de pago de cada cuota |
| `Asistencia` | asociación, implementa Registrable | Relaciona estudiante y ciclo en una fecha, con su estado y observación |
| `PersonaController` | controlador | Guarda la colección de personas y aplica las reglas de alta, búsqueda y conteo |
| `CicloController` | controlador | Lo mismo para la oferta académica |
| `MatriculaController` | controlador | Control de aforo y de matrícula duplicada, anulación, totales y lo recaudado |
| `AsistenciaController` | controlador | Impide el doble registro y calcula el porcentaje de asistencia |
| `ArchivoUtil` | utilitaria | Única clase que importa `java.io`: lectura, escritura y copia de respaldo |
| `AcademiaSanFernando` | principal (consola) | Flujo de la versión de consola y métodos de carga y guardado que también usa la interfaz |
| `VentanaPrincipal` | principal (Swing) | Menú, escritorio, carga automática y archivos |
| `FormularioBase` | abstracta, extiende JInternalFrame e implementa ActionListener | Lo que comparten las siete ventanas internas |
| `FrmCiclos`, `FrmEstudiantes`, `FrmDocentes` | extienden FormularioBase | Datos básicos |
| `FrmMatricula`, `FrmPagos`, `FrmAsistencia` | extienden FormularioBase | Procesos |
| `FrmTablero` | extiende FormularioBase | Reportes |

## Relaciones entre clases

| Relación | Dónde está |
|---|---|
| Herencia | `Estudiante` y `Docente` extienden `Persona`; `CicloRegular` y `CursoIntensivo` extienden `Ciclo`; las siete ventanas extienden `FormularioBase` |
| Interfaz | `Persona`, `Ciclo`, `Matricula` y `Asistencia` implementan `Registrable`; `ArchivoUtil.guardarRegistros()` guarda cualquier lista de `Registrable` |
| Asociación | `Matricula` guarda referencias a `Estudiante`, `Docente` y `Ciclo`; `Asistencia`, a `Estudiante` y `Ciclo` |
| Agregación | `Estudiante` agrega a su historial ciclos que ya existían (`agregarMatricula(ciclo)`); cada controlador agrega a su lista objetos creados fuera |
| Composición | `Matricula` crea sus `Cuota` dentro de `generarCronograma()`; si la matrícula se anula, sus cuotas desaparecen con ella |

## La primera versión del sistema (semanas 1 a 6)

En las semanas 1 a 6 el sistema estaba en el paquete `semana5`: una clase `Persona`, su `PersonaController` y un menú de consola. Ese mismo código creció hasta lo que hoy es `academia`:

| En `semana5` (semanas 1 a 6) | En `academia` (semanas 7 a 13) |
|---|---|
| `Persona`, clase simple | `Persona` abstracta, con las hijas `Estudiante` y `Docente`, que implementa `Registrable` |
| `PersonaController`: agregar, listar con tres sobrecargas, buscar, obtener, eliminar, ordenar por apellido y contar | `PersonaController` con las mismas operaciones, más la búsqueda por código y la planilla docente |
| `Semana5`, menú de consola | `AcademiaSanFernando` (consola) y `VentanaPrincipal` (interfaz gráfica) |

Como `academia` ya contiene todo lo de `semana5`, en la semana 13 se retiró ese paquete para que el sistema quede en un solo lugar y sin clases repetidas. Su código sigue en el historial del repositorio y se puede ver con `git log -- src/semana5`.

## Persistencia

Al guardar se crea la carpeta `datos/` con un archivo de texto por colección, delimitado por punto
y coma: `ciclos.txt`, `personas.txt`, `matriculas.txt`, `cuotas.txt`, `asistencias.txt` y
`reporte_matriculas.csv`. La copia de respaldo crea `datos/respaldo_aaaa-mm-dd_hh-mm-ss/`. Esa
carpeta se genera en tiempo de ejecución y está excluida del control de versiones.

El orden de carga es obligatorio: **ciclos → personas → matrículas → cuotas → asistencias**,
porque las matrículas y las asistencias guardan identificadores que deben volver a enlazarse con
objetos ya cargados. Las cuotas no se leen como objetos nuevos: cada matrícula las vuelve a crear al
calcular su precio, y `cuotas.txt` solo devuelve el estado de pago.

## Convención de trabajo

- Ramas: `main` (estable) y `develop` (integración).
- Ramas de tarea con los prefijos `feat/`, `fix/` y `docs/`.
- Mensajes de commit convencionales: `feat:`, `fix:`, `docs:`, `refactor:`.
- Todo cambio entra por **pull request con base en `develop`**, nunca directo a `main`.
- Atributos en minúsculas con guion bajo; `protected` en las clases abstractas y `private` en las
  hijas; métodos en minúscula inicial; validación dentro de los `set` con mensajes que empiezan
  por `Error:`; el comentario Javadoc de cada clase firmado por los cuatro integrantes.
